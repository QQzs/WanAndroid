package com.zs.demo.wanandroid.view.banner.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Size;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.TextView;

import com.zs.demo.wanandroid.R;
import com.zs.demo.wanandroid.view.banner.BannerEntry;
import com.zs.demo.wanandroid.view.banner.page.Pageable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 描述 用来显示轮播图的控件。
 * 创建人 kelin
 * 创建时间 2017/7/27  下午3:22
 * 版本 v 1.0.0
 */

public class BannerView extends ViewPager {

    /**
     * 没有(不显示)指示器。
     */
    public static final int NO_INDICATOR = 0x0000_0001;
    /**
     * 不可以也不能翻页。
     */
    public static final int CAN_NOT_PAGING = NO_INDICATOR << 1;

    private BannerHelper mBH;
    /**
     * 用来记录指示器控件的ID。
     */
    private int mPointIndicatorId;
    /**
     * 用来记录标题控件的ID。
     */
    private int mTitleViewId;
    /**
     * 用来记录子标题控件的ID。
     */
    private int mSubTitleViewId;


    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs == null) {
            mBH = new BannerHelper(this, 0);
        } else {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BannerView);
            int interpolatorId = typedArray.getResourceId(R.styleable.BannerView_interpolator, NO_ID);
            Interpolator interpolator = null;
            if (interpolatorId != NO_ID) {
                interpolator = AnimationUtils.loadInterpolator(getContext(), interpolatorId);
            }

            mBH = new BannerHelper(this,
                    typedArray.getInt(R.styleable.BannerView_singlePageMode, 0),
                    interpolator,
                    typedArray.getInt(R.styleable.BannerView_pagingIntervalTime, 0),
                    typedArray.getInt(R.styleable.BannerView_decelerateMultiple, 0));

            mPointIndicatorId = typedArray.getResourceId(R.styleable.BannerView_bannerIndicator, NO_ID);
            mTitleViewId = typedArray.getResourceId(R.styleable.BannerView_titleView, NO_ID);
            mSubTitleViewId = typedArray.getResourceId(R.styleable.BannerView_subTitleView, NO_ID);
            typedArray.recycle();
        }
    }

    /**
     * 由于我需要监听BannerView的触摸事件，通过该事件来处理什么时候需要暂停和启动轮播图，所以我禁用了这个方法。其实你也并不需要
     * 对Banner的触摸事件进行监听。
     *
     * @param l {@link OnTouchListener}对象。
     */
    @Override
    @Deprecated
    public void setOnTouchListener(OnTouchListener l) {
        throw new RuntimeException("This method has been disabled");
    }

    /**
     * 该方法进制调用，如果你非要调用将会导致Banner有严重的Bug。
     *
     * @param l {@link OnTouchListener}对象。
     */
    void listenerOnTouch(OnTouchListener l) {
        super.setOnTouchListener(l);
    }

    @Override
    public void removeView(View view) {
        super.removeView(view);
        //下面的代码是解决View复用导致的view层级关系错乱的问题。重新对view的布局参数进行初始化。
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp != null) {
            try {
                Field positionField = BannerHelper.getField(LayoutParams.class, "position");
                if (positionField != null) {
                    positionField.setInt(lp, 0);
                }
                Field widthFactorField = BannerHelper.getField(LayoutParams.class, "widthFactor");
                if (widthFactorField != null) {
                    widthFactorField.setFloat(lp, 0.f);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params) {
        boolean addSuccess = super.addViewInLayout(child, index, params);
        ViewGroup parent = (ViewGroup) getParent();
        View view;
        if (parent != null) {
            if (mPointIndicatorId != NO_ID) {
                view = findView(parent, mPointIndicatorId, "PointIndicator");
                mBH.setIndicatorView(view);
                mPointIndicatorId = NO_ID;
            }
            if (mTitleViewId != NO_ID) {
                view = findView(parent, mTitleViewId, "TitleView");
                if (view instanceof TextView) {
                    setTitleView((TextView) view);
                } else {
                    throw new ClassCastException("The bannerIndicator attribute in XML must be the resource id of the TextView！");
                }
                mTitleViewId = NO_ID;
            }
            if (mSubTitleViewId != NO_ID) {
                view = findView(parent, mSubTitleViewId, "SubTitleView");
                if (view instanceof TextView) {
                    setSubTitleView((TextView) view);
                } else {
                    throw new ClassCastException("The bannerIndicator attribute in XML must be the resource id of the TextView！");
                }
                mSubTitleViewId = NO_ID;
            }
        }
        return addSuccess;
    }

    private View findView(ViewGroup view, int viewId, String desc) {
        View v = view.findViewById(viewId);
        if (v == null) {
            ViewParent parent = view.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                return findView((ViewGroup) parent, viewId, desc);
            } else {
                throw new Resources.NotFoundException("the " + desc + " view id is not found!");
            }
        }
        return v;
    }

    /**
     * 设置条目数据并开始轮播。如果不希望启动轮播则调用两个参数的方法{@link #setEntries(List, boolean)}。
     *
     * @param items {@link BannerEntry} 集合。
     * @see #setEntries(List, boolean)
     */
    public void setEntries(List<? extends BannerEntry> items) {
        setEntries(items, true);
    }

    /**
     * 设置条目数据。
     *
     * @param items {@link BannerEntry} 集合。
     * @param start 是否开始轮播。
     */
    public void setEntries(@NonNull List<? extends BannerEntry> items, boolean start) {
        mBH.setEntries(items, start);
    }

    /**
     * 获取数据源集合。
     *
     * @return 返回上一次调用 {@link #setEntries(List)} 或 {@link #setEntries(List, boolean)} 方法成功时的参数。
     */
    public List<? extends BannerEntry> getEntries() {
        return mBH.getEntries();
    }

    /**
     * 设置翻页的间隔时间，单位：毫秒。
     *
     * @param pagingIntervalTime 要设置的时长。
     */
    public void setPagingIntervalTime(@Size(min = 1000) int pagingIntervalTime) {
        mBH.setPagingIntervalTime(pagingIntervalTime);
    }

    /**
     * 设置翻页动画减速倍数。
     *
     * @param multiple 要减速的倍数。默认为ViewPage的6倍。
     */
    public void setDecelerateMultiple(@Size(min = 2) int multiple) {
        mBH.setMultiple(multiple);
    }

    /**
     * 设置点击事件监听。
     *
     * @param listener Banner页面的点击事件监听对象。
     */
    public void setOnPageClickListener(OnPageClickListener listener) {
        mBH.setOnPageClickListener(listener);
    }

    /**
     * 设置页面长按的监听。
     *
     * @param listener Banner页面的长按事件监听对象。
     */
    public void setOnPageLongClickListener(OnPageLongClickListener listener) {
        mBH.setOnPageLongClickListener(listener);
    }

    /**
     * 设置页面改变监听。
     *
     * @param listener Banner页面改变的监听对象。
     */
    public void setOnPageChangedListener(OnPageChangeListener listener) {
        mBH.setOnPageChangedListener(listener);
    }

    /**
     * 如果你想监听页面的改变，应当使用 {@link #setOnPageChangedListener(OnPageChangeListener)} 方法，
     * 因为 {@link OnPageChangeListener} 的回调方法中会把页面中的数据模型回调给你。
     *
     * @param listener {@link ViewPager.OnPageChangeListener}的子类对象。
     * @see #setOnPageChangedListener(OnPageChangeListener)
     */
    @Deprecated
    @Override
    public void addOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        super.addOnPageChangeListener(listener);
    }

    void addPageChangeListener(ViewPager.OnPageChangeListener listener) {
        super.addOnPageChangeListener(listener);
    }

    /**
     * 设置当Banner只有一张图片时的处理模式。该方法并不推荐使用，建议在XML中通过app:singlePageMode自定义属性配置。
     *
     * @param singlePageMode 要设置的处理模式，可以是{@link #NO_INDICATOR} 或者是 {@link #CAN_NOT_PAGING}。
     *                       也可是同时设置两个参数，同时设置两个参数是中间用"|"符号链接。
     *                       例如："bannerView.setSinglePageMode(BannerView.NO_INDICATOR|BannerView.CAN_NOT_PAGING)"。
     *                       如果同时设置了两个参数则表示如果只有一张图片则既不会轮播而且无论你是否设置了指示器则都不会显示。
     * @see #NO_INDICATOR
     * @see #CAN_NOT_PAGING
     */
    public void setSinglePageMode(int singlePageMode) {
        mBH.setSinglePageMode(singlePageMode);
    }

    /**
     * 设置页面指示器控件。
     *
     * @param indicatorView {@link Pageable} 对象。
     */
    public <V extends View & Pageable> void setIndicatorView(@NonNull V indicatorView) {
        mBH.setIndicatorView(indicatorView);
    }

    /**
     * 设置标题显示控件。
     *
     * @param titleView 用来显示标题的TextView。
     */
    public void setTitleView(TextView titleView) {
        mBH.setTitleView(titleView);
    }

    /**
     * 设置副标题显示控件。
     *
     * @param subTitleView 用来显示副标题的TextView。
     */
    public void setSubTitleView(TextView subTitleView) {
        mBH.setSubTitleView(subTitleView);
    }

    /**
     * 开始轮播。
     */
    public void start() {
        mBH.start();
    }

    /**
     * 停止轮播。调用此方法可以轮播图从正在轮播的状态改变到停止轮播的状态。该方法一般
     * 情况下你是不需要调用的，假如你是配合可滚动的控件(如ScrollView、ListView或则RecyclerView)使用时希望通过调用
     * 该方法在轮播图不可见时停止轮播的话，那就不必了。因为我已经做好了这件事情。
     */
    public void stop() {
        mBH.stop();
    }

    /**
     * 是否已经启动轮播。
     *
     * @return 如果已经启动播返回true，否则返回false。
     */
    public boolean isStarted() {
        return mBH.isStarted();
    }

    /**
     * 选择中间页，如果你想移动到中间则需要调用这个方法。
     */
    public void selectCenterPage() {
        selectCenterPage(0);
    }

    /**
     * 选择中间页，如果你想移动到中间则需要调用这个方法。
     *
     * @param offset 向右偏移的页数。
     */
    public void selectCenterPage(int offset) {
        mBH.selectCenterPage(offset);
    }

    /**
     * 设置显示左右两边的页面，调用该方法前你必须在你的布局文件中为 {@link BannerView} 包裹一层布局。而这个布局的触摸事件默认
     * 会传递给 {@link BannerView}。
     */
    public void setShowLeftAndRightPage() {
        setShowLeftAndRightPage(0);
    }

    /**
     * 设置显示左右两边的页面，调用该方法前你必须在你的布局文件中为 {@link BannerView} 包裹一层布局。而这个布局的触摸事件默认
     * 会传递给 {@link BannerView}。
     *
     * @param showWidthDp 两边页面的宽度。单位dp。
     */
    public void setShowLeftAndRightPage(int showWidthDp) {
        setShowLeftAndRightPage(showWidthDp, true, null);
    }

    /**
     * 设置显示左右两边的页面，调用该方法前你必须在你的布局文件中为 {@link BannerView} 包裹一层布局。而这个布局的触摸事件默认
     * 会传递给 {@link BannerView}。
     *
     * @param reverseDrawingOrder 是否翻转动画。
     * @param pageTransformer     {@link PageTransformer} 对象。
     * @see BannerView#setPageTransformer(boolean, PageTransformer)
     */
    public void setShowLeftAndRightPage(boolean reverseDrawingOrder, PageTransformer pageTransformer) {
        setShowLeftAndRightPage(0, reverseDrawingOrder, pageTransformer);
    }

    /**
     * 设置显示左右两边的页面，调用该方法前你必须在你的布局文件中为 {@link BannerView} 包裹一层布局。而这个布局的触摸事件默认
     * 会传递给 {@link BannerView}。
     *
     * @param showWidthDp         两边页面的宽度。单位dp。
     * @param reverseDrawingOrder 是否翻转动画。
     * @param pageTransformer     {@link PageTransformer} 对象。
     * @see ViewPager#setPageTransformer(boolean, PageTransformer)
     */
    public void setShowLeftAndRightPage(int showWidthDp, boolean reverseDrawingOrder, PageTransformer pageTransformer) {
        mBH.setShowLeftAndRightPage(showWidthDp, reverseDrawingOrder, pageTransformer);
    }

    int determineTargetPage(int currentPage, float pageOffset) {
        try {
            Field lastMotionX = BannerHelper.getField(ViewPager.class, "mLastMotionX");
            Field initialMotionX = BannerHelper.getField(ViewPager.class, "mInitialMotionX");
            int deltaX = (int) (lastMotionX.getFloat(this) - initialMotionX.getFloat(this));
            Method method = ViewPager.class.getDeclaredMethod("determineTargetPage", int.class, float.class, int.class, int.class);
            method.setAccessible(true);
            return (int) method.invoke(this, currentPage, pageOffset, 0, deltaX);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mBH.reStart();
        try {
            Method method = ViewPager.class.getDeclaredMethod("scrollToItem", int.class, boolean.class, int.class, boolean.class);
            method.setAccessible(true);
            method.invoke(this, getCurrentItem(), false, 0, false);
            Field mFirstLayout = BannerHelper.getField(ViewPager.class, "mFirstLayout");
            mFirstLayout.setBoolean(this, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mBH.pause();
    }

    /**
     * 轮播图的所有事件的监听类。
     */
    public static abstract class OnPageClickListener {

        /**
         * 页面被点击的时候执行。
         *
         * @param entry 当前页面的 {@link BannerEntry} 对象。
         * @param index 当前页面的索引。这个索引永远会在你的集合的size范围内。
         */
        protected abstract void onPageClick(BannerEntry entry, int index);
    }

    /**
     * 页面长按的监听。
     */
    public interface OnPageLongClickListener {
        /**
         * 页面被长按的时候执行。
         *
         * @param entry 当前页面的 {@link BannerEntry} 对象。
         * @param index 当前页面的索引。这个索引永远会在你的集合的size范围内。
         */
        void onPageLongClick(BannerEntry entry, int index);
    }

    /**
     * 页面改变的监听。
     */
    public interface OnPageChangeListener {


        /**
         * 当页面被选中的时候调用。
         *
         * @param entry 当前页面的 {@link BannerEntry} 对象。
         * @param index 当前页面的索引。这个索引永远会在你的集合的size范围内。
         */
        void onPageSelected(BannerEntry entry, int index);

        /**
         * 当页面正在滚动中的时候执行。
         *
         * @param index                当前页面的索引。这个索引永远会在你的集合的size范围内。
         * @param positionOffset       值为(0,1)表示页面位置的偏移。
         * @param positionOffsetPixels 页面偏移的像素值。
         */
        void onPageScrolled(int index, float positionOffset, int positionOffsetPixels);

        /**
         * 当Banner中的页面的滚动状态改变的时候被执行。
         *
         * @param state 当前的滚动状态。
         * @see BannerView#SCROLL_STATE_IDLE
         * @see BannerView#SCROLL_STATE_DRAGGING
         * @see BannerView#SCROLL_STATE_SETTLING
         */
        void onPageScrollStateChanged(int state);
    }
}
