package com.gzlygl.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gzlygl.R;
import com.gzlygl.util.GalleryUtil;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.finalteam.galleryfinal.widget.GFImageView;

/**
 * Created by DengJf on 2017/11/17.
 */

public class PhotopAdapter extends RecyclerView.Adapter<PhotopAdapter.ViewHolder> {
    private List<String>data;
    private Context context;
    private Animation anim;
    public PhotopAdapter(Context context,List<String> data) {
        this.data = data;
        this.context = context;
        anim= AnimationUtils.loadAnimation(context,R.anim.alpha);
        GalleryUtil util=new GalleryUtil(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.photo_item,null);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (position!=0){
            holder.del.setVisibility(View.VISIBLE);
            Glide.with(context).load(data.get(position))
                    .diskCacheStrategy(DiskCacheStrategy.NONE).
                    skipMemoryCache(true)
                    .into(holder.photo);
            holder.photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data.remove(position);
                    notifyDataSetChanged();
                }
            });
        }else {
            holder.del.setVisibility(View.GONE);
            Glide.with(context).load(R.drawable.add_photo)
                    .diskCacheStrategy(DiskCacheStrategy.NONE).
                    skipMemoryCache(true)
                    .into(holder.photo);
            holder.photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.photo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            holder.photo.startAnimation(anim);
                            FunctionConfig config=new FunctionConfig.Builder().
                                    setMutiSelectMaxSize(9)
                                    .build();
                            GalleryFinal.openGalleryMuti(0, config, new GalleryFinal.OnHanlderResultCallback() {
                                @Override
                                public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                                    data=new ArrayList<String>();
                                    data.add("");
                                    for (int i=0;i<resultList.size();i++){
                                        data.add(resultList.get(i).getPhotoPath());
                                    }
                                    notifyDataSetChanged();
                                }

                                @Override
                                public void onHanlderFailure(int requestCode, String errorMsg) {

                                }
                            });
                        }
                    });
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView photo;
        private ImageView del;
        public ViewHolder(View itemView) {
            super(itemView);
            photo=(ImageView)itemView.findViewById(R.id.photo_img);
            del=(ImageView)itemView.findViewById(R.id.delete);
        }
    }

    public List<String>getData(){
        return data;
    }
}
