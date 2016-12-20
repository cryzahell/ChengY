package com.ox.chengystudio.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ox.mylibrary.util.ManageAcAnim;

/**
 * Created by admin on 2016/12/20.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected BaseActivity baseActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseActivity = this;
        initView(savedInstanceState);

        initData(savedInstanceState);
        initOther(savedInstanceState);
    }

    protected abstract void initView(Bundle savedInstanceState);
    protected abstract void initData(Bundle savedInstanceState);
    protected abstract void initOther(Bundle savedInstanceState);

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        ManageAcAnim.leftInRightOut(this);
    }
}
