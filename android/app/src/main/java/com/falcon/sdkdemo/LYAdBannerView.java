package com.falcon.sdkdemo;

import android.content.Context;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.ly.adpoymer.interfaces.BannerListener;
import com.ly.adpoymer.manager.BannerManager;

public class LYAdBannerView extends FrameLayout {
    private FrameLayout mContainer;

    public LYAdBannerView(@NonNull Context context) {
        super(context);
        // 创建布局

    }
    public void createAdView(ReactApplicationContext applicationContext) {
        mContainer = new FrameLayout(applicationContext.getCurrentActivity());
        // 设置Frame的信息包 为铺满全屏 new xxx.LayoutParams(x,y)
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        // 将信息包设置给view
        mContainer.setLayoutParams(params);
        // 将View添加给父布局
        addView(mContainer);
        //请求Banner广告
        BannerManager.getInstance(applicationContext.getCurrentActivity()).requestAd(applicationContext.getCurrentActivity(), "10519",
                new BannerListener() {
                    @Override
                    public void onAdClick(String s) {
                        Log.i("BannerActivity", "onAdClick " + s);
                    }

                    @Override
                    public void onAdDisplay(String s) {
                        Log.i("BannerActivity", "onAdDisplay " + s);
//                        WritableMap event = Arguments.createMap();
//                        event.putInt("width", 1080);
//                        event.putInt("height", 1920);
//                        applicationContext.getJSModule(RCTEventEmitter.class).receiveEvent(
//                                LYAdBannerView.this.getId(),
//                                "onSizeChange",
//                                event);
                    }

                    @Override
                    public void onAdFailed(String s) {
                        Log.i("BannerActivity", "onAdFailed " + s);
                    }

                    @Override
                    public void onAdClose(String s) {
                        Log.i("BannerActivity", "onAdClose " + s);
                    }

                    @Override
                    public void onAdReady(String s) {
//                        WritableMap event = Arguments.createMap();
//                        event.putInt("width", 1080);
//                        event.putInt("height", 1920);
//                        applicationContext.getJSModule(RCTEventEmitter.class).receiveEvent(
//                                LYAdBannerView.this.getId(),
//                                "onSizeChange",
//                                event);
                        Log.i("BannerActivity", "onAdReady " + s);
                    }
                }, mContainer, 3);
    }
}
