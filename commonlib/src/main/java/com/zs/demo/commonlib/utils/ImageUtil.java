package com.zs.demo.commonlib.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.zs.demo.commonlib.R;

public class ImageUtil {

    public static RequestOptions mDefaultOptions = new RequestOptions()
            .placeholder(R.mipmap.img_empty)
            .error(R.mipmap.img_empty)
            .fallback(R.mipmap.img_empty)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

    public static void load(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .apply(mDefaultOptions)
                .into(imageView);
    }

}
