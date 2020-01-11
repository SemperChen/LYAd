package com.falcon.sdkdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;

import java.util.Map;

public class LYAdBannerViewManager extends ViewGroupManager<LYAdBannerView> {
    private ReactApplicationContext applicationContext;
    public static final String EVENT_SIZE_CHANGE = "onSizeChange";

    LYAdBannerViewManager(ReactApplicationContext reactContext){
        this.applicationContext = reactContext;

    }
    @NonNull
    @Override
    public String getName() {
        return "LYAdBannerView";
    }

    @NonNull
    @Override
    protected LYAdBannerView createViewInstance(@NonNull ThemedReactContext reactContext) {
        LYAdBannerView lyAdBannerView = new LYAdBannerView(reactContext);
        lyAdBannerView.createAdView(applicationContext);
        return lyAdBannerView;
    }

    @Override
    @Nullable
    public Map getExportedCustomBubblingEventTypeConstants() {
        MapBuilder.Builder<String, Object> builder = MapBuilder.builder();
        String[] events = {EVENT_SIZE_CHANGE};
        for (String event : events) {
            builder.put(
                    event,
                    MapBuilder.of(
                            "phasedRegistrationNames",
                            MapBuilder.of("bubbled", event)));
        }
        return builder.build();
    }
}
