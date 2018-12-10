package com.carles.teamworktechtest.common.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    protected abstract void initViews();
    protected abstract void initComponents();
    protected abstract BasePresenter getPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initComponents();
        if (getPresenter() != null) {
            getPresenter().onViewCreated(this);
        }
    }

    @Override
    protected void onDestroy() {
        if (getPresenter() != null) {
            getPresenter().onViewDestroyed();
        }
        super.onDestroy();
    }
}
