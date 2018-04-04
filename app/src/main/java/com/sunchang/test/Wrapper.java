package com.sunchang.test;

import android.view.View;

/**
 * Created by Administrator on 2018/4/3.
 */

public class Wrapper {

    private View mTarget;

    public Wrapper(View mTarget) {
        this.mTarget = mTarget;
    }

    public int getWidth() {
        return mTarget.getLayoutParams().width;
    }

    public void setWidth(int width) {
        mTarget.getLayoutParams().width = width;
        mTarget.requestLayout();
    }
}
