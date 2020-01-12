package com.falcon.sdkdemo;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.ly.adpoymer.interfaces.BannerListener;
import com.ly.adpoymer.interfaces.NativeListener;
import com.ly.adpoymer.manager.BannerManager;
import com.ly.adpoymer.manager.NativeManager;

import java.util.List;

public class LYAdNativeView extends FrameLayout {
    private FrameLayout mContainer;
    private NativeManager nativeManager;

    public LYAdNativeView(@NonNull Context context) {
        super(context);
        // 创建布局

    }

    public void createAdView(ReactApplicationContext applicationContext) {
        mContainer = new FrameLayout(applicationContext.getCurrentActivity());
        // 设置Frame的信息包 为铺满全屏 new xxx.LayoutParams(x,y)
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        // 将信息包设置给view
        mContainer.setLayoutParams(params);
        // 将View添加给父布局
        addView(mContainer);
        nativeManager = NativeManager.getInstance(applicationContext.getCurrentActivity());

        initNativeAD(applicationContext);
    }

    /**
     * 请求信息流广告并展示
     */
    private void initNativeAD(ReactApplicationContext applicationContext) {
        //获取信息流广告
        nativeManager.requestAd(applicationContext.getCurrentActivity(), "10522", 1, new NativeListener() {
            @Override
            public void onAdFailed(String msg) {
                Log.i("NativeActivity", "----"+msg);
            }

            @Override
            public void onAdClick() {
                Log.i("NativeActivity", "onAdClick ");
            }

            @Override
            public void onAdDisplay() {
                Log.i("NativeActivity", "onAdDisplay ");
            }

            @Override
            public void onADClosed(View adView) {
                Log.i("NativeActivity", "onADClosed");
            }

            @Override
            public void onAdReceived(List arg0) {
                Log.i("NativeActivity", "onAdReceived ");
            }

            @Override
            public void OnAdViewReceived(List<? extends View> adViews) {
                Log.i("NativeActivity", String.valueOf(adViews.size()));
                View view = adViews.get(0);
                mContainer.addView(view);
                nativeManager.NativeRender(view);

                int width = View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED);
                int height = View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED);
                view.measure(width, height);
                applicationContext.getResources().getDisplayMetrics();

                WritableMap event = Arguments.createMap();
                event.putInt("width", view.getMeasuredWidth());
                event.putInt("height", view.getMeasuredHeight());
//                Log.i("NativeActivity", String.valueOf(view.getMeasuredWidth()+", "+view.getMeasuredHeight()));

                applicationContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                        LYAdNativeView.this.getId(),
                        LYAdNativeViewManager.EVENT_SIZE_CHANGE,
                        event);

            }
        });
    }
}
