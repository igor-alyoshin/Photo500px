package com.example.photo500px;

import android.animation.Animator;
import android.view.View;
import android.view.ViewPropertyAnimator;

import rx.Observable;

/**
 * Created by igor on 23.12.15.
 */
public class AnimateObservable<T> extends Observable<T> {

    protected AnimateObservable(View v, ViewPropertyAnimator viewPropertyAnimator, OnSubscribe<T> onSubscribe) {
        super(onSubscribe);
        this.v = v;
        this.viewPropertyAnimator = viewPropertyAnimator;
    }

    private ViewPropertyAnimator viewPropertyAnimator;
    private View v;

    public AnimateObservable from(int rotation) {
        v.setRotation(rotation);
        return this;
    }

    public AnimateObservable to(int rotation) {
        viewPropertyAnimator.rotation(rotation);
        return this;
    }

    public AnimateObservable duration(int duration) {
        viewPropertyAnimator.setDuration(duration);
        return this;
    }

    public static <T> AnimateObservable<T> animate(View v) {
        ViewPropertyAnimator viewPropertyAnimator = v.animate();
        OnSubscribe<T> onSubscribe = subscriber -> {
            viewPropertyAnimator.setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    subscriber.onStart();
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    subscriber.onCompleted();
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    subscriber.onCompleted();
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
        };
        return new AnimateObservable<>(v, viewPropertyAnimator, onSubscribe);
    }
}
