package com.zs.demo.commonlib.view.banner;

import java.io.Serializable;

public class BannerViewData implements Serializable {

	private int realIndex;
	private String bannerTitle;
	private String bannerImage;
	private String bannerUrl;

	public BannerViewData(String bannerTitle, String bannerImage, String bannerUrl) {
		this.bannerTitle = bannerTitle;
		this.bannerImage = bannerImage;
		this.bannerUrl = bannerUrl;
	}

	public int getRealIndex() {
		return realIndex;
	}

	public void setRealIndex(int realIndex) {
		this.realIndex = realIndex;
	}

	public String getBannerTitle() {
		return bannerTitle;
	}

	public void setBannerTitle(String bannerTitle) {
		this.bannerTitle = bannerTitle;
	}

	public String getBannerImage() {
		return bannerImage;
	}

	public void setBannerImage(String bannerImage) {
		this.bannerImage = bannerImage;
	}

	public String getBannerUrl() {
		return bannerUrl;
	}

	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}

}