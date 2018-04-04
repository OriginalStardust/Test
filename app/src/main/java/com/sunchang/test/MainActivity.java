package com.sunchang.test;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private int number = 0;

    private TextView textView;

    private RetainedFragment dataFragment;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = this.getFragmentManager();
        dataFragment = (RetainedFragment) fm.findFragmentByTag("data");

        if (dataFragment == null) {
            dataFragment = new RetainedFragment();
            fm.beginTransaction().add(dataFragment, "data").commit();
            dataFragment.setNumber(number);
        }

        textView = this.findViewById(R.id.text_view);
        button = (Button) this.findViewById(R.id.button);

        number = dataFragment.getNumber();
        this.updateNumb();
//        final float curTranslationX = button.getTranslationX();
//        final Wrapper wrapper = new Wrapper(button);
        this.testAnimationSet();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataFragment.setNumber(number);
    }

    private void updateNumb() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                textView.setText(String.valueOf(number));
                number++;
                handler.postDelayed(this, 1000);
            }
        });
    }

    private void testValueAnim() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(button.getLayoutParams().width, 700);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int currentValue = (Integer) valueAnimator.getAnimatedValue();
                button.getLayoutParams().width = currentValue;
                button.requestLayout();
            }
        });
        valueAnimator.start();
    }

    private void testObjectAnim() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(button, "rotationX", 0f, 360f);
        objectAnimator.setDuration(3000);
        objectAnimator.setRepeatMode(ObjectAnimator.REVERSE);
        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        objectAnimator.start();
    }

    private void testObjectAnim2() {
        TestView2 testView2 = (TestView2) this.findViewById(R.id.my_view_2);
        ObjectAnimator objectAnimator = ObjectAnimator.ofObject(testView2, "color", new ColorEvaluator(),
                "#0000FF", "#FF0000");
        objectAnimator.setDuration(5000);
        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        objectAnimator.setRepeatMode(ObjectAnimator.REVERSE);
        objectAnimator.start();
    }

    private void testAnimatorSet() {
        final AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.property_animator);
        set.setTarget(button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set.start();
            }
        });
    }

    private void testTweenAnim() {
        Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.view_animation);
        button.startAnimation(rotateAnimation);
    }

    private void testAnimationSet() {
        AnimationSet set = new AnimationSet(true);

        Animation translate = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -0.5f, Animation.RELATIVE_TO_PARENT, 0.5f,
                Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0);
        translate.setDuration(10000);

        Animation rotate = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1000);
        rotate.setRepeatMode(Animation.RESTART);
        rotate.setRepeatCount(Animation.INFINITE);

        Animation alpha = new AlphaAnimation(1.0f, 0.0f);
        alpha.setDuration(2000);
        alpha.setStartOffset(7000);

        Animation scale1 = new ScaleAnimation(1.0f, 0.5f, 1.0f, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale1.setDuration(1000);
        scale1.setStartOffset(4000);

        Animation scale2 = new ScaleAnimation(1.0f, 2.0f, 1.0f, 2.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale2.setDuration(1000);
        scale2.setStartOffset(5000);

        set.addAnimation(rotate);
        set.addAnimation(translate);
        set.addAnimation(alpha);
        set.addAnimation(scale1);
        set.addAnimation(scale2);

        button.startAnimation(set);
    }
}
