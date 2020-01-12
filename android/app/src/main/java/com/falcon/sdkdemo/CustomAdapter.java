package com.falcon.sdkdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.recyclerview.widget.RecyclerView;

import com.falcon.sdkdemo.holder.CustomViewHolder;
import com.ly.adpoymer.manager.NativeManager;

import java.util.List;

/**
 * Created by Administrator on 2018/11/15.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    private static final int TYPE_DATA = 0;
    private static final int TYPE_AD = 1;
    private List<Object> mData;
    private NativeManager mNativeManager;

    public CustomAdapter(List list, NativeManager nativeManager) {
        this.mData = list;
        this.mNativeManager = nativeManager;
    }

    // 把返回的NativeExpressADView添加到数据集里面去
    public void addADViewToPosition(int position, View adView) {
        if (position >= 0 && position < mData.size() && adView != null) {
            mData.add(position, adView);
        }
    }

    // 移除NativeExpressADView的时候是一条一条移除的
    public void removeADView(int position, View adView) {
        mData.remove(position);
        notifyItemRemoved(position); // position为adView在当前列表中的位置
        notifyItemRangeChanged(0, mData.size() - 1);
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position) instanceof View ? TYPE_AD : TYPE_DATA;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {
        int type = getItemViewType(position);
        switch (type) {
            case TYPE_AD:
                final View adView = (View) mData.get(position);
                if (holder.container.getChildCount() > 0 && holder.container.getChildAt(0) == adView) {
                    return;
                }

                if (holder.container.getChildCount() > 0) {
                    holder.container.removeAllViews();
                }

                if (adView.getParent() != null) {
                    ((ViewGroup) adView.getParent()).removeView(adView);
                }
                holder.container.addView(adView);
                mNativeManager.NativeRender(adView); // 调用render方法后sdk才会开始展示广告
                break;
            case TYPE_DATA:
                holder.title.setText(((NormalItem) mData.get(position)).getTitle());
                break;
            default:
                break;
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        int layoutId = (viewType == TYPE_AD) ? R.layout.item_native_ad : R.layout.item_normal_data;
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, null);
        return new CustomViewHolder(view);
    }
}
