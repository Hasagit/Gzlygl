package com.gzlygl.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gzlygl.R;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.widget.GFImageView;

/**
 * Created by DengJf on 2017/11/17.
 */

public class GalleryUtil {
    private Context context;

    public GalleryUtil(Context context) {
        this.context = context;
        int org=context.getResources().getColor(R.color.colorPrimary);
        ThemeConfig theme=new ThemeConfig.Builder()
                .setCheckSelectedColor(org)
                .setTitleBarBgColor(org)
                .setFabNornalColor(org)
                .setFabPressedColor(org)
                .setTitleBarTextColor(Color.WHITE)
                .build();
        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .build();

        //配置imageloader
        CoreConfig coreConfig = new CoreConfig.Builder(context, new ImageLoader() {
            @Override
            public void displayImage(Activity activity, String path, GFImageView imageView, Drawable defaultDrawable, int width, int height) {
                Glide.with(activity).load("file://" + path)
                        .diskCacheStrategy(DiskCacheStrategy.NONE).
                        skipMemoryCache(true)
                        .into(imageView);
            }

            @Override
            public void clearMemoryCache() {

            }
        },theme)
                .build();
        GalleryFinal.init(coreConfig);
    }


}
