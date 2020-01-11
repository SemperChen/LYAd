package com.falcon.sdkdemo;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

public class LYAdPackage implements ReactPackage {
    @Nonnull
    @Override
    public List<NativeModule> createNativeModules(@Nonnull ReactApplicationContext reactContext) {
        return Arrays.<NativeModule>asList(
//                new GDTADSplashModule(reactContext),
//                new GDTADInterstitialModule(reactContext),
//                new GDTADRewardVideoModule(reactContext)
        );
    }

    @Nonnull
    @Override
    public List<ViewManager> createViewManagers(@Nonnull ReactApplicationContext reactContext) {
        return Arrays.<ViewManager>asList(
                new LYAdBannerViewManager(reactContext)
        );
    }
}
