package com.zs.demo.commonlib.view.banner;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zs.demo.commonlib.R;
import com.zs.demo.commonlib.utils.ImageUtil;
import com.zs.demo.commonlib.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by shijian
 * version: 1.1
 * updatetime: 2015-12-2
 */
public class BannerPager extends RelativeLayout {

    LinearLayout pointGroup;
    TextView banner_title;
    RelativeLayout rl_banner_title;
    RelativeLayout banner_item_number;
    TextView number;
    ViewPager mViewPager;

    int currentItem;
    int time = 3000;
    int mBannertype;
    int width;//image宽
    int height;//高
    boolean candelete = false;
    Context mContext;
    Handler mHandler = new Handler();
    BannerAdapter mAdapter;
    List<BannerViewData> mRealList;
    OnBannerListener onBannerListener;

    public BannerPager(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public BannerPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public void setWH(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setCanDelete(boolean value) {
        candelete = value;
    }

    private void init() {
        View rootView = inflate(mContext, R.layout.banner_view_layout, this);
        mViewPager = (ViewPager) rootView.findViewById(R.id.banner);
        banner_title = (TextView) rootView.findViewById(R.id.banner_title);
        pointGroup = (LinearLayout) rootView.findViewById(R.id.banner_dot);
        rl_banner_title = (RelativeLayout) rootView.findViewById(R.id.rl_banner_title);
        banner_item_number = (RelativeLayout) rootView.findViewById(R.id.banner_item_number);
        number = (TextView) rootView.findViewById(R.id.number);
        mViewPager.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mAdapter = new BannerAdapter();
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentItem = position;
                number.setText(currentItem + "/" + mRealList.size());
            }

            @Override
            public void onPageScrolled(int position, float arg1, int arg2) {
                if (mRealList != null && mRealList.size() > 0) {
                    int realIndex = position - 1;
                    if (realIndex >= mRealList.size()) {
                        realIndex = 0;
                    } else if (realIndex < 0) {
                        realIndex = mRealList.size() - 1;
                    }
                    if (realIndex == mRealList.size() - 1 && arg1 != 0) {
                    } else {
                        for (int i = 0; i < mRealList.size(); i++) {
                            if (i == realIndex) {
                                if (pointGroup.getChildCount() > 1)
                                    pointGroup.getChildAt(realIndex).setEnabled(true);
                            } else {
                                if (pointGroup.getChildCount() > 1)
                                    pointGroup.getChildAt(i).setEnabled(false);
                            }
                        }
                        banner_title.setText(mRealList.get(realIndex).getBannerTitle());
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {//1：开始滑动 2：滑动结束 0：什么都没做
                if (mRealList != null && mRealList.size() > 0) {
                    switch (arg0) {
                        case 0:
                            int pageIndex = currentItem;
                            if (currentItem == 0) {
                                pageIndex = mRealList.size();
                            } else if (currentItem == mRealList.size() + 1) {
                                pageIndex = 1;
                            }

                            if (currentItem != pageIndex) {
                                mViewPager.setCurrentItem(pageIndex, false);
                            }
                            mHandler.removeCallbacks(autoNextRunnable);
                            mHandler.postDelayed(autoNextRunnable, time);
                            break;
                        case 1:
                            mHandler.removeCallbacks(autoNextRunnable);
                            break;
                    }
                }
            }
        });
    }

    public void setOnBannerListener(OnBannerListener listener) {
        onBannerListener = listener;
    }

    public void initData(List<BannerViewData> list){
        setData(list);
    }
    
    public void initData(List<BannerViewData> list, int type){
        mBannertype = type;
        setData(list);
    }

    public void setData(final List<BannerViewData> list) {
        if (mBannertype == 0) {

        } else if (mBannertype == 2) {
            rl_banner_title.setVisibility(View.GONE);
            banner_item_number.setVisibility(View.VISIBLE);
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            params.leftMargin = ScreenUtil.dp2px(40);
            params.rightMargin = ScreenUtil.dp2px(40);
            mViewPager.setLayoutParams(params);
            number.setText((currentItem == 0 ? 1 : currentItem) + "/" + list.size());
        } else if (mBannertype == 3){
            banner_title.setVisibility(View.GONE);
            //点改为居中
            RelativeLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            pointGroup.setLayoutParams(params);
        } else{
            
        }
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setRealIndex(i);
            }
            if (mRealList != null && mRealList.size() > 0) {
                mRealList.clear();
            }
            mRealList = list;
            List<BannerViewData> newList = new ArrayList<>();
            if (list.size() > 1) {
                newList.add(list.get(list.size() - 1));
                newList.addAll(list);
                newList.add(list.get(0));
            } else {
                newList.addAll(list);
            }
            pointGroup.removeAllViews();
            if (list.size() > 1) {
                for (int i = 0; i < list.size(); i++) {
                    // 添加指示点
                    ImageView point = new ImageView(mContext);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.rightMargin = 20;
                    point.setLayoutParams(params);
                    point.setBackgroundResource(R.drawable.point_bg);
                    if (i == 0) {
                        point.setEnabled(true);
                    } else {
                        point.setEnabled(false);
                    }
                    pointGroup.addView(point);
                }
            }
            mAdapter.setList(newList);
            mHandler.removeCallbacks(autoNextRunnable);
            if (list.size() > 1) {
                mViewPager.setCurrentItem(1, false);
                mHandler.postDelayed(autoNextRunnable, time);
            }

        }
    }

    Runnable autoNextRunnable = new Runnable() {
        @Override
        public void run() {
            currentItem++;
            mViewPager.setCurrentItem(currentItem);
            if (currentItem >= mRealList.size() + 1) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        currentItem = 1;
                        mViewPager.setCurrentItem(currentItem, false);
                    }
                }, 100);
            }
            mHandler.removeCallbacks(autoNextRunnable);
            mHandler.postDelayed(autoNextRunnable, time);
            number.setText(currentItem + "/" + mRealList.size());
        }
    };

    private class BannerAdapter extends PagerAdapter {

        List<ImageView> viewlist = new ArrayList<>();

        public void setList(List<BannerViewData> list) {
            viewlist.clear();
            for (int i = 0;i< list.size() ; i++){
                final int index = i;
                final BannerViewData banner = list.get(i);
                ImageView imageView = new ImageView(mContext);
                imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                imageView.setScaleType(ScaleType.CENTER_CROP);
                imageView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onBannerListener != null) {
                            onBannerListener.onClick(index, banner);
                        }
                    }
                });
                String url = banner.getBannerImage();
                ImageUtil.load(getContext(),url,imageView);
                viewlist.add(imageView);
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return viewlist.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView(viewlist.get(position % viewlist.size()));
        }

        public Object instantiateItem(View container, int position) {
            View view = viewlist.get(position % viewlist.size());
            ((ViewPager) container).addView(view, 0);
            return view;
        }
    }


    public void onPause() {
        mHandler.removeCallbacks(autoNextRunnable);
    }

    public void onResume() {
        if (mRealList != null && mRealList.size() > 0) {
            mHandler.removeCallbacks(autoNextRunnable);
            mHandler.postDelayed(autoNextRunnable, time);
        }
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        super.setOnTouchListener(l);
        mViewPager.setOnTouchListener(l);
    }

    public interface OnBannerListener {
        void onClick(int position, BannerViewData obj);
    }

}
