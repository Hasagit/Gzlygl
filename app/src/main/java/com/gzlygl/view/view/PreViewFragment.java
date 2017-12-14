package com.gzlygl.view.view;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.gzlygl.R;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class PreViewFragment extends Fragment {
    private ScaleImagView img;
    private int imgResources;
    private String url_file;

    public PreViewFragment(int imgResources) {
        this.imgResources=imgResources;
    }

    public PreViewFragment(String url_file) {
        this.url_file=url_file;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_pre_view, container, false);
        img=(ScaleImagView)view.findViewById(R.id.scale_img);
        if (url_file==null){
            Glide.with(getContext()).load(imgResources).into(img);
        }else {
            Glide.with(getContext()).load(url_file).into(img);
        }
        return view;
    }

}
