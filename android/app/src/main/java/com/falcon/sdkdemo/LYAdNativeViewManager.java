package com.falcon.sdkdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;

import java.util.Map;

public class LYAdNativeViewManager extends ViewGroupManager<LYAdNativeView> {
    private ReactApplicationContext applicationContext;
    public static final String EVENT_SIZE_CHANGE = "onNativeSizeChange";

    LYAdNativeViewManager(ReactApplicationContext reactContext){
        this.applicationContext = reactContext;

    }
    @NonNull
    @Override
    public String getName() {
        return "LYAdNativeView";
    }

    @NonNull
    @Override
    protected LYAdNativeView createViewInstance(@NonNull ThemedReactContext reactContext) {
        LYAdNativeView lyAdNativeView = new LYAdNativeView(reactContext);
        lyAdNativeView.createAdView(applicationContext);
        return lyAdNativeView;
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
