package ru.mikhalev.vladimir.mvpshop.flow;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import java.util.Collections;
import java.util.Map;

import flow.Direction;
import flow.Dispatcher;
import flow.KeyChanger;
import flow.MultiKey;
import flow.State;
import flow.Traversal;
import flow.TraversalCallback;
import flow.TreeKey;
import ru.mikhalev.vladimir.mvpshop.R;
import ru.mikhalev.vladimir.mvpshop.core.BaseScreen;
import ru.mikhalev.vladimir.mvpshop.mortar.ScreenScoper;
import ru.mikhalev.vladimir.mvpshop.utils.UIHelper;
import timber.log.Timber;

/**
 * Developer Vladimir Mikhalev, 27.11.2016.
 */

public class TreeKeyDispatcher implements Dispatcher, KeyChanger {
    private Activity mActivity;
    private Object inKey;
    @Nullable
    private Object outKey;
    private FrameLayout mRootFrame;

    public TreeKeyDispatcher(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void dispatch(@NonNull Traversal traversal, @NonNull TraversalCallback callback) {
        Map<Object, Context> contexts;
        State inState = traversal.getState(traversal.destination.top());
        inKey = inState.getKey();
        State outState = traversal.origin == null ? null : traversal.getState(traversal.origin.top());
        outKey = outState == null ? null : outState.getKey();

        mRootFrame = (FrameLayout) mActivity.findViewById(R.id.root_frame);

        if (inKey.equals(outKey)) {
            callback.onTraversalCompleted();
            return;
        }

        if (inKey instanceof MultiKey) {

        }

        Context flowContext = traversal.createContext(inKey, mActivity);
        Context mortarContext = ScreenScoper.getScreenScope((BaseScreen) inKey).createContext(flowContext);
        contexts = Collections.singletonMap(inKey, mortarContext);
        changeKey(outState, inState, traversal.direction, contexts, callback);
    }

    @Override
    public void changeKey(@Nullable State outgoingState, @NonNull State incomingState, @NonNull Direction direction,
                          @NonNull Map<Object, Context> incomingContexts, @NonNull TraversalCallback callback) {
        Timber.e("changeKey: %s &s", incomingState.toString(), direction.toString());
        Context context = incomingContexts.get(inKey);

        if (outgoingState != null) {
            outgoingState.save(mRootFrame.getChildAt(0));
        }

        Screen screen;
        screen = inKey.getClass().getAnnotation(Screen.class);
        if (screen == null) {
            throw new IllegalStateException("@Screen annotation is missing on screen " +
                    ((BaseScreen) inKey).getScopeName());
        } else {
            int layout = screen.value();
            LayoutInflater inflater = LayoutInflater.from(context);
            View newView = inflater.inflate(layout, mRootFrame, false);
            View oldView = mRootFrame.getChildAt(0);

            incomingState.restore(newView);


            mRootFrame.addView(newView);
            UIHelper.waitForMeasure(newView, new UIHelper.OnMeasureCallback() {
                @Override
                public void onMeasure(View view, int width, int height) {
                    runAnimation(mRootFrame, oldView, newView, direction, new TraversalCallback() {
                        @Override
                        public void onTraversalCompleted() {
                            if (outKey != null && !(inKey instanceof TreeKey)) {
                                ((BaseScreen) outKey).unregisterScope();
                            }
                            callback.onTraversalCompleted();
                        }
                    });
                }
            });
        }
    }

    private void runAnimation(FrameLayout container, View from, View to, Direction direction, TraversalCallback callback) {
        Animator animator = createAnimation(from, to, direction);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (from != null) {
                    container.removeView(from);
                }
                callback.onTraversalCompleted();
            }
        });

        animator.setInterpolator(new FastOutSlowInInterpolator());
        animator.start();
    }

    @NonNull
    private Animator createAnimation(View from, View to, Direction direction) {
        boolean backward = direction == Direction.BACKWARD;

        AnimatorSet set = new AnimatorSet();

        int fromTransition;
        if (from != null) {
            fromTransition = backward ? from.getWidth() : -from.getWidth();
            ObjectAnimator outAnimation = ObjectAnimator.ofFloat(from, "translationX", fromTransition);
            set.play(outAnimation);
        }
            int toTransition = backward ? -to.getWidth() : to.getWidth();
            ObjectAnimator inAnimation = ObjectAnimator.ofFloat(to, "translationX", toTransition);
            set.play(inAnimation);

        return set;
    }
}
