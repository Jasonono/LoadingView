package com.jason.loadingview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

public class LoadingView extends RelativeLayout {

    private CircleView mLeftView, mCenterView, mRightView;
    private int mTranslationDistance = dp2px(24);
    private static final int ANIMATION_TIME = 400;
    private ObjectAnimator outterLeftAnimator;
    private ObjectAnimator outterRightAnimator;
    private ObjectAnimator innerLeftAnimator;
    private ObjectAnimator outterRightAnimator1;
    private int leftColor;
    private AnimatorSet outterAnimatorSet;
    private AnimatorSet innerAnimatorSet;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mLeftView = getCircleView(context);
        mLeftView.exChangeColor(Color.BLUE);

        mCenterView = getCircleView(context);
        mCenterView.exChangeColor(Color.RED);

        mRightView = getCircleView(context);
        mRightView.exChangeColor(Color.GREEN);

        addView(mLeftView);
        addView(mRightView);
        addView(mCenterView);

        post(new Runnable() {
            @Override
            public void run() {
                outterAnimation();
            }
        });
    }

    private void outterAnimation() {
        if (outterLeftAnimator == null)
            outterLeftAnimator = ObjectAnimator.ofFloat(mLeftView, "translationX", 0,
                    -mTranslationDistance);
        if (outterRightAnimator == null)
            outterRightAnimator = ObjectAnimator.ofFloat(mRightView, "translationX", 0,
                    mTranslationDistance);
        outterAnimatorSet = new AnimatorSet();
        outterAnimatorSet.cancel();
        outterAnimatorSet.setDuration(ANIMATION_TIME);
        outterAnimatorSet.setInterpolator(new DecelerateInterpolator(1.2f));
        outterAnimatorSet.playTogether(outterLeftAnimator, outterRightAnimator);
        outterAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                innerAnimation();
            }
        });
        outterAnimatorSet.start();
    }

    private void innerAnimation() {
        if (innerLeftAnimator == null)
            innerLeftAnimator = ObjectAnimator.ofFloat(mLeftView, "translationX",
                    -mTranslationDistance, 0);
        if (outterRightAnimator1 == null)
            outterRightAnimator1 = ObjectAnimator.ofFloat(mRightView, "translationX",
                    mTranslationDistance, 0);

        innerAnimatorSet = new AnimatorSet();
        innerAnimatorSet.setDuration(ANIMATION_TIME);
        innerAnimatorSet.setInterpolator(new AccelerateInterpolator(1.2f));
        innerAnimatorSet.playTogether(innerLeftAnimator, outterRightAnimator1);
        innerAnimatorSet.addListener(new AnimatorListenerAdapter() {

            private int rightColor;
            private int centerColor;

            @Override
            public void onAnimationEnd(Animator animation) {
                leftColor = mLeftView.getColor();
                centerColor = mCenterView.getColor();
                rightColor = mRightView.getColor();

                mLeftView.exChangeColor(rightColor);
                mCenterView.exChangeColor(leftColor);
                mRightView.exChangeColor(centerColor);

                outterAnimation();
            }
        });
        innerAnimatorSet.start();
    }

    private CircleView getCircleView(Context context) {
        CircleView circleView = new CircleView(context);
        LayoutParams layoutParams = new LayoutParams(dp2px(12), dp2px(12));
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        circleView.setLayoutParams(layoutParams);
        return circleView;
    }

    private int dp2px(int dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources()
                .getDisplayMetrics());
    }


}
