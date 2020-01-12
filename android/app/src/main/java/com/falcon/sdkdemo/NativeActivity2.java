package com.falcon.sdkdemo;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ly.adpoymer.interfaces.NativeListener;
import com.ly.adpoymer.manager.NativeManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 信息流广告示例
 */
public class NativeActivity2 extends AppCompatActivity implements View.OnClickListener {
    private static final String SPACE_ID = "10522";
    public static final int MAX_ITEMS = 50;
    // 加载广告的条数，取值范围为[1, 10]
    public static int FIRST_AD_POSITION = 1;
    // 第一条广告的位置
    public static final int AD_COUNT = 10;
    // 每间隔10个条目插入一条广告
    public static int ITEMS_PER_AD = 5;

    private RecyclerView mRecyclerView;
    private CustomAdapter mAdapter;
    private List<NormalItem> mNormalDataList = new ArrayList<>();
    private List<? extends View> mAdViewList;
    private HashMap<View, Integer> mAdViewPositionMap = new HashMap<>();
    private NativeManager nativeManager;
    private EditText mEdtId;
    private String mSpaceId;
    private Button mBtnRequested;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("信息流广告示例");
        }
        setContentView(R.layout.activity_native);
        mRecyclerView = findViewById(R.id.recycler_view);
        mBtnRequested = findViewById(R.id.btn_requested);
        mBtnRequested.setOnClickListener(this);
        mEdtId = findViewById(R.id.edt_spaceId);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false));
    }

    @Override
    protected void onResume() {
        super.onResume();
        //必须要在onResume()时通知到广告数据，以便重置广告恢复状态
        if (mAdViewList != null) {
            for (View view : mAdViewList) {
                nativeManager.NativeResume(view);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 使用完了每一个LyAdView之后都要释放掉资源。
        if (mAdViewList != null) {
            for (View view : mAdViewList) {
                nativeManager.NativeDestory(view);
            }
        }
    }

    private void initData() {
        nativeManager = NativeManager.getInstance(this);
        initNativeAD();
        for (int i = 0; i < MAX_ITEMS; ++i) {
            mNormalDataList.add(new NormalItem("No." + i + " Normal Data"));
        }
        mAdapter = new CustomAdapter(mNormalDataList, nativeManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 请求信息流广告并展示
     */
    private void initNativeAD() {
        //获取信息流广告
        nativeManager.requestAd(this, mSpaceId, AD_COUNT, new NativeListener() {
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
                Log.i("NativeActivity", "OnAdViewReceived");
                findViewById(R.id.ly_container).setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                mAdViewList = adViews;
                int size = mAdViewList.size();
                for (int i = 0; i < size; i++) {
                    int position = FIRST_AD_POSITION + ITEMS_PER_AD * i;
                    if (position < mNormalDataList.size()) {
                        View view = mAdViewList.get(i);
                        mAdViewPositionMap.put(view, position); // 把每个广告在列表中位置记录下来
                        mAdapter.addADViewToPosition(position, mAdViewList.get(i));
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View v) {
        //用户输入的广告位id
        String spaceId = mEdtId.getText().toString().trim();
        if (spaceId.length() < 0 || TextUtils.isEmpty(spaceId)) {
            mSpaceId = SPACE_ID;
        }else {
            mSpaceId = spaceId;
        }
            initData();
    }
}
