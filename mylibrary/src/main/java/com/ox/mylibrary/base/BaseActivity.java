package com.ox.mylibrary.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ox.mylibrary.util.ManageAcAnim;

import butterknife.ButterKnife;

/**
 * Created by admin on 2016/12/20.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        ManageAcAnim.leftInRightOut(this);
    }
}
