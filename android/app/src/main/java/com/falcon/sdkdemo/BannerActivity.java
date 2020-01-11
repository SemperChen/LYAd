package com.falcon.sdkdemo;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.ly.adpoymer.interfaces.BannerListener;
import com.ly.adpoymer.manager.BannerManager;

/**
 * Banner广告示例
 */
public class BannerActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String SPACE_ID = "10519";
    private FrameLayout mContainer;
    private EditText mEdtId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Banner广告示例");
        }
        setContentView(R.layout.activity_banner);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mContainer = findViewById(R.id.adLayout);
        findViewById(R.id.btn_show).setOnClickListener(this);
        findViewById(R.id.btn_close).setOnClickListener(this);
        mEdtId = findViewById(R.id.edt_spaceId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_show:
                //用户输入的广告位id
                String spaceId = mEdtId.getText().toString().trim();
                if (spaceId.length() < 0 || TextUtils.isEmpty(spaceId)) {
                   spaceId = SPACE_ID;
                }
                    //请求Banner广告
                    BannerManager.getInstance(this).requestAd(this, spaceId,
                            new BannerListener() {
                                @Override
                                public void onAdClick(String s) {
                                    Log.i("BannerActivity", "onAdClick " + s);
                                }

                                @Override
                                public void onAdDisplay(String s) {
                                    Log.i("BannerActivity", "onAdDisplay " + s);
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
                                    Log.i("BannerActivity", "onAdReady " + s);
                                }
                            }, mContainer, 3);
                break;
            case R.id.btn_close:
                mContainer.removeAllViews();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mContainer) {
            mContainer.removeAllViews();
        }
    }
}
