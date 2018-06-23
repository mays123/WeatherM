package com.madfooat.task.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.madfooat.task.R;

/**
 * Created by MaysAtari on 6/22/2018.
 */

public abstract class BaseActivity extends AppCompatActivity{

    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getActivityLayout());

        initBaseViews();
        initChildViews();
        initToolBar();
        showBackBtn();
        initUI();

    }


    private void initToolBar() {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);

            mToolbar.setNavigationOnClickListener(v -> onBackPressed());

            if (getActivityTitle() != null) {
                setUpTitle(getActivityTitle());
            }
        }

    }

    private void initBaseViews() {
        mToolbar = findViewById(R.id.toolbar);
    }

    public void showBackBtn() {
        //to prevent showing the back button on the MainActivity
        if (!(this instanceof MainActivity)) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);

            }
        }

    }


    public void setUpTitle(String title) {
        setTitle(title);
    }

    protected abstract void initUI();

    protected abstract void initChildViews();

    protected abstract int getActivityLayout();

    protected abstract String getActivityTitle();

}
