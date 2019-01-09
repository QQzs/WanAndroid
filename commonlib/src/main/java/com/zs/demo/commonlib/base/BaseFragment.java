package com.zs.demo.commonlib.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zs.demo.commonlib.app.Constant;
import com.zs.demo.commonlib.utils.SpUtil;

import java.lang.reflect.Field;

/**
 * @author Administrator
 */
public abstract class BaseFragment extends BaseRxFragment {

	private View contentView;
	private ViewGroup container;
	protected LayoutInflater inflater;
	protected String mUserId , mUserName;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.inflater = inflater;
		this.container = container;
		mUserId = SpUtil.getString(Constant.APP_USER_ID,"");
		mUserName = SpUtil.getString(Constant.APP_USER_NAME,"");
		onCreateView(savedInstanceState);
		if (contentView == null)
			return super.onCreateView(inflater, container, savedInstanceState);
		return contentView;
	}

	protected void onCreateView(Bundle savedInstanceState) {
		setContentView();
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initView();
		initData();
	}

	public abstract void initView();

	public abstract void initData();

	public void setContentView() {
		setContentView(inflater.inflate(setLayoutId(), container, false));
	}

	public void setContentView(View view) {
		contentView = view;
	}

	public View getContentView() {
		return contentView;
	}

	/**
	 *
	 * @return 返回布局
	 */
	protected abstract int setLayoutId();

	//获得fragment中的控件
	public <T extends View> T findViewById(int id) {
		if (contentView != null)
		 return (T) getContentView().findViewById(id);
		return null;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		try {
			Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
			childFragmentManager.setAccessible(true);
			childFragmentManager.set(this, null);

		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		contentView = null;
		container = null;
		inflater = null;
	}

}
