package com.ox.chengystudio.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ox.mylibrary.util.ManageSharedPre;
import com.ox.mylibrary.util.EnumSpName;

import butterknife.ButterKnife;

/**
 * Created by admin on 2016/12/20.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected BaseActivity mActivity;
    public boolean isDestroyed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        view(savedInstanceState);
        initData(savedInstanceState);
        initOther(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isDestroyed = true;
    }

    /**
     * 需要给子类一个控制的方法，否则，子类需要自己自定义加载控件的过程时，麻烦就大了。。
     */
    private void view(@Nullable Bundle savedInstanceState) {
        if (initView(savedInstanceState)) {
            ButterKnife.inject(this);
        }
        afterViewInject(savedInstanceState);
    }

    /**
     * @return  true 使用父类（使用ButterKnife）的方法加载控件
     *          false 如果需要自己加载控件，传false，父类将越过加载控件的过程，执行下一步命令
     */
    protected abstract boolean initView(Bundle savedInstanceState);

    protected abstract void afterViewInject(Bundle savedInstanceState);

    protected abstract void initData(Bundle savedInstanceState);

    protected abstract void initOther(Bundle savedInstanceState);

    protected ManageSharedPre getManageSP(EnumSpName fileName){
        return new ManageSharedPre(this, fileName);
    }


}
