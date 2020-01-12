package com.falcon.sdkdemo.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.falcon.sdkdemo.R;

/**
 * Created by Administrator on 2018/11/15.
 */

public class CustomViewHolder extends RecyclerView.ViewHolder{
    public TextView title;
    public ViewGroup container;

    public CustomViewHolder(View view) {
        super(view);
        title = view.findViewById(R.id.title);
        container = view.findViewById(R.id.native_ad_container);
    }
}
