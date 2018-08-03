package com.zs.demo.wanandroid.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.zs.demo.wanandroid.Constant;
import com.zs.demo.wanandroid.request.RequestApi;
import com.zs.demo.wanandroid.utils.SpUtil;

import java.lang.reflect.Field;

import io.reactivex.Observable;

/**
 * @author Administrator
 */
public class BaseFragment extends BaseRxFragment {
	private View contentView;
	private ViewGroup container;
	protected LayoutInflater inflater;
	protected RequestApi mRequestApi = null;
	protected Gson mGson = new Gson();
	protected String mUserId , mUserName;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.inflater = inflater;
		this.container = container;
		mRequestApi = RequestApi.getInstance();
		mUserId = SpUtil.getString(Constant.APP_USER_ID,"");
		mUserName = SpUtil.getString(Constant.APP_USER_NAME,"");
		onCreateView(savedInstanceState);
		if (contentView == null)
			return super.onCreateView(inflater, container, savedInstanceState);
		return contentView;
	}

	protected void onCreateView(Bundle savedInstanceState) {

	}


	protected void initView() {

	}

	protected void initData() {

	}

	/**
	 * requestData
	 * @param request
	 * @param type
	 */
	protected void requestData(Observable request, int type){

	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		contentView = null;
		container = null;
		inflater = null;
	}

	public void setContentView(int layoutResID) {
		setContentView(inflater.inflate(layoutResID, container, false));
	}

	public void setContentView(View view) {
		contentView = view;
	}

	public View getContentView() {
		return contentView;
	}

//	public View findViewById(int id) {
//		if (contentView != null)
//			return contentView.findViewById(id);
//		return null;
//	}

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
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

}
