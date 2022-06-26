package com.iman.mymaterialsharingcard;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MySharingCard extends ConstraintLayout implements View.OnClickListener {

    private static final long REVEAL_DURATION = 400;

    int centerX;
    int centerY;
    float radius;

    View rootView;
    private AppCompatImageView profileImage;
    private AppCompatImageView socialIcon;
    private LinearLayout layoutBtns;
    private ConstraintLayout layoutReveal;
    private AppCompatImageView coverImage;
    private AppCompatButton socialBtn1, socialBtn2, socialBtn3;

    public MySharingCard(Context context) {
        super(context);
        init(context);
    }

    public MySharingCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        rootView = inflate(context, R.layout.layout_sharing_card, this);
        profileImage = rootView.findViewById(R.id.img_profile);
        socialIcon = rootView.findViewById(R.id.social_icon);
        layoutBtns = rootView.findViewById(R.id.layout_btns);
        layoutReveal = rootView.findViewById(R.id.layout_reveal);
        coverImage = rootView.findViewById(R.id.cover_image);
        socialBtn1 = rootView.findViewById(R.id.social_btn_1);
        socialBtn2 = rootView.findViewById(R.id.social_btn_2);
        socialBtn3 = rootView.findViewById(R.id.social_btn_3);
        socialIcon.setOnClickListener(this);

//        centerX = ((socialIcon.getRight()-socialIcon.getLeft())/2)+socialIcon.getLeft();;
//        centerY = coverImage.getBottom();
//        radius = (float) Math.hypot(coverImage.getWidth(), coverImage.getHeight());

//        centerX = ((socialIcon.getRight()-socialIcon.getLeft())/2)+socialIcon.getLeft();
//        centerY = coverImage.getBottom();
//        radius = (float) Math.hypot(coverImage.getWidth(), coverImage.getHeight());

    }



    public AppCompatImageView getProfileImage() {
        return profileImage;
    }

    public AppCompatImageView getCoverImage() {
        return coverImage;
    }

    public AppCompatButton getSocialBtn1() {
        return socialBtn1;
    }

    public AppCompatButton getSocialBtn2() {
        return socialBtn2;
    }

    public AppCompatButton getSocialBtn3() {
        return socialBtn3;
    }

    @Override
    public void onClick(View v) {

        centerX = (socialIcon.getRight()+socialIcon.getLeft())/2;
        centerY = coverImage.getBottom();
        radius = (float) Math.hypot(centerX-coverImage.getLeft(), coverImage.getHeight());

        if (layoutReveal.getVisibility() == VISIBLE){
            hide(centerX, centerY, radius);
        }else {
            show(centerX, centerY, radius);
        }
    }

    private void show(int centerX, int centerY, float radius) {
        Animator animator = ViewAnimationUtils.createCircularReveal(layoutReveal, centerX, centerY, 0, radius);
        animator.setDuration(REVEAL_DURATION);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                layoutReveal.setVisibility(VISIBLE);
                socialIcon.setImageResource(R.drawable.image_cancel);
                socialIcon.setBackgroundResource(R.drawable.social_icon_cancel_bg);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                layoutBtns.setVisibility(VISIBLE);
                Animation fadein = AnimationUtils.loadAnimation(rootView.getContext(), R.anim.fadein);
                layoutBtns.startAnimation(fadein);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    private void hide(int centerX, int centerY, float radius) {
        Animator animator = ViewAnimationUtils.createCircularReveal(layoutReveal, centerX, centerY, radius, 0);
        animator.setDuration(REVEAL_DURATION);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                socialIcon.setImageResource(R.drawable.twitter_logo);
                socialIcon.setBackgroundResource(R.drawable.social_icon_normal_bg);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                layoutReveal.setVisibility(GONE);
                layoutBtns.setVisibility(GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }
}
