package com.bytedance.i18n.youtube;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.internal.aa;
import com.google.android.youtube.player.internal.ab;
import com.google.android.youtube.player.internal.d;
import com.google.android.youtube.player.internal.n;
import com.google.android.youtube.player.internal.s;
import com.google.android.youtube.player.internal.t;
import com.google.android.youtube.player.internal.w;
import com.google.android.youtube.player.internal.y;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public final class YouTubePlayerView extends ViewGroup {
    private GlobalFocusChangeListenerImpl focusChangeListener;
    private Set<View> viewSets;
    private com.google.android.youtube.player.internal.b d;
    private s player;// player
    private View view;
    private n g;
    private YouTubePlayer.Provider provider;
    private Bundle bundle;
    private YouTubePlayer.OnInitializedListener initializedListener;
    private boolean playerStart;
    private boolean playerPause;

    public YouTubePlayerView(Context context) {
        this(context, null);
    }

    public YouTubePlayerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public YouTubePlayerView(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(ab.a(context, "context cannot be null"), attributeSet, defStyleAttr);
        if (this.getBackground() == null) {
            this.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        this.setClipToPadding(false);
        this.g = new n(context);
        this.requestTransparentRegion(this.g);
        this.addView(this.g);
        this.viewSets = new HashSet<>();
        this.focusChangeListener = new GlobalFocusChangeListenerImpl();
    }

    final void onStart(boolean start) {
        if (start && Build.VERSION.SDK_INT < 14) {
            y.a("Could not enable TextureView because API level is lower than 14", 0);
            this.playerStart = false;
            return;
        }
        this.playerStart = start;
    }

    final void initializeFailure(final Activity activity, YouTubePlayer.Provider provider, String string, YouTubePlayer.OnInitializedListener initializedListener, Bundle bundle) {
        if (this.player != null || this.initializedListener != null) {
            return;
        }
        ab.a(activity, "activity cannot be null");
        this.provider = ab.a(provider, "provider cannot be null");
        this.initializedListener = ab.a(initializedListener, "listener cannot be null");
        this.bundle = bundle;
        this.g.b();
        this.d = aa.a().a(this.getContext(), string, new t.a() {

            @Override
            public final void a() {
                // initial success
                if (YouTubePlayerView.this.d != null) {
                    YouTubePlayerView.initializeFailure(YouTubePlayerView.this, activity);
                }
                YouTubePlayerView.this.d = null;
            }

            @Override
            public final void b() {
                if (!YouTubePlayerView.this.playerPause && YouTubePlayerView.this.player != null) {
                    YouTubePlayerView.this.player.f();
                }
                YouTubePlayerView.this.g.a();
                if (YouTubePlayerView.this.indexOfChild(YouTubePlayerView.this.g) < 0) {
                    YouTubePlayerView.this.addView(YouTubePlayerView.this.g);
                    YouTubePlayerView.this.removeView(YouTubePlayerView.this.view);
                }
                YouTubePlayerView.this.view = null;
                YouTubePlayerView.this.player = null;
                YouTubePlayerView.this.d = null;
            }
        }, new t.b() {

            @Override
            public final void a(YouTubeInitializationResult youTubeInitializationResult) {
                // initial failure
                YouTubePlayerView.this.initializeFailure(youTubeInitializationResult);
                YouTubePlayerView.this.d = null;
            }
        });
        this.d.e();
    }

    private void initializeFailure(YouTubeInitializationResult youTubeInitializationResult) {
        this.player = null;
        this.g.c();
        if (this.initializedListener != null) {
            this.initializedListener.onInitializationFailure(this.provider, youTubeInitializationResult);
            this.initializedListener = null;
        }
    }

    final void onStart() {
        if (this.player != null) {
            this.player.b();
        }
    }

    final void onResume() {
        if (this.player != null) {
            this.player.c();
        }
    }

    final void onPause() {
        if (this.player != null) {
            this.player.d();
        }
    }

    final void onStop() {
        if (this.player != null) {
            this.player.e();
        }
    }

    final void onResume(boolean bl) {
        if (this.player != null) {
            this.player.b(bl);
            this.onPause(bl);
        }
    }

    final void onPause(boolean bl) {
        this.playerPause = true;
        if (this.player != null) {
            this.player.a(bl);
        }
    }

    private void addViewBefore(View view) {
//        if (!(view == this.g || this.player != null && view == this.view)) {
//            throw new UnsupportedOperationException("No views can be added on top of the player");
//        }
    }

    public final void setPadding(int n2, int n3, int n4, int n5) {
    }

    public final void setClipToPadding(boolean bl) {
    }

    public final void addView(View view) {
        this.addViewBefore(view);
        super.addView(view);
    }

    public final void addView(View view, int n2) {
        this.addViewBefore(view);
        super.addView(view, n2);
    }

    public final void addView(View view, int n2, ViewGroup.LayoutParams layoutParams) {
        this.addViewBefore(view);
        super.addView(view, n2, layoutParams);
    }

    public final void addView(View view, int n2, int n3) {
        this.addViewBefore(view);
        super.addView(view, n2, n3);
    }

    public final void addView(View view, ViewGroup.LayoutParams layoutParams) {
        this.addViewBefore(view);
        super.addView(view, layoutParams);
    }

    protected final void onMeasure(int n2, int n3) {
        if (this.getChildCount() > 0) {
            View view = this.getChildAt(0);
            view.measure(n2, n3);
            this.setMeasuredDimension(view.getMeasuredWidth(), view.getMeasuredHeight());
            return;
        }
        this.setMeasuredDimension(0, 0);
    }

    protected final void onLayout(boolean bl, int n2, int n3, int n4, int n5) {
        if (this.getChildCount() > 0) {
            this.getChildAt(0).layout(0, 0, n4 - n2, n5 - n3);
        }
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.player != null) {
            this.player.a(configuration);
        }
    }

    protected final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.getViewTreeObserver().addOnGlobalFocusChangeListener(this.focusChangeListener);
    }

    protected final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.getViewTreeObserver().removeOnGlobalFocusChangeListener(this.focusChangeListener);
    }

    public final void clearChildFocus(View view) {
        if (this.hasFocusable()) {
            this.requestFocus();
            return;
        }
        super.clearChildFocus(view);
    }

    public final void requestChildFocus(View view, View view2) {
        super.requestChildFocus(view, view2);
        this.viewSets.add(view2);
    }

    public final void focusableViewAvailable(View view) {
        super.focusableViewAvailable(view);
        this.viewSets.add(view);
    }

    public final void addFocusables(ArrayList<View> arrayList, int n2) {
        ArrayList arrayList2 = new ArrayList();
        super.addFocusables(arrayList2, n2);
        arrayList.addAll(arrayList2);
        this.viewSets.clear();
        this.viewSets.addAll(arrayList2);
    }

    public final void addFocusables(ArrayList<View> arrayList, int n2, int n3) {
        ArrayList arrayList2 = new ArrayList();
        super.addFocusables(arrayList2, n2, n3);
        arrayList.addAll(arrayList2);
        this.viewSets.clear();
        this.viewSets.addAll(arrayList2);
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        return true;
    }

    private int lastX;
    private int lastY;

    public boolean onInterceptTouchEvent(MotionEvent event) {
        //获取到手指处的横坐标和纵坐标
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                //计算移动的距离
                int offX = x - lastX;
                offX = 0;
                int offY = y - lastY;
                //调用layout方法来重新放置它的位置
                layout(getLeft() + offX, getTop() + offY,
                        getRight() + offX, getBottom() + offY);
                break;
        }
        return false;
    }

    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (this.player != null) {
            if (keyEvent.getAction() == 0) {
                if (this.player.a(keyEvent.getKeyCode(), keyEvent) || super.dispatchKeyEvent(keyEvent)) {
                    return true;
                }
                return false;
            }
            if (keyEvent.getAction() == 1) {
                if (this.player.b(keyEvent.getKeyCode(), keyEvent) || super.dispatchKeyEvent(keyEvent)) {
                    return true;
                }
                return false;
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    final Bundle getBundle() {
        if (this.player == null) {
            return this.bundle;
        }
        return this.player.h();
    }

    static /* synthetic */ void initializeFailure(YouTubePlayerView youTubePlayerView, Activity object) {
        Object obj;
        try {
            obj = aa.a().a(object, youTubePlayerView.d, youTubePlayerView.playerStart);
        } catch (w.a var1_2) {
            y.a("Error creating YouTubePlayerView", var1_2);
            youTubePlayerView.initializeFailure(YouTubeInitializationResult.INTERNAL_ERROR);
            return;
        }
        youTubePlayerView.player = new s(youTubePlayerView.d, (d) obj);
        youTubePlayerView.view = youTubePlayerView.player.a();
        youTubePlayerView.addView(youTubePlayerView.view);
        youTubePlayerView.removeView(youTubePlayerView.g);
        if (youTubePlayerView.initializedListener != null) {
            boolean wasRestored = false;
            if (youTubePlayerView.bundle != null) {
                wasRestored = youTubePlayerView.player.a(youTubePlayerView.bundle);
                youTubePlayerView.bundle = null;
            }
            youTubePlayerView.initializedListener.onInitializationSuccess(youTubePlayerView.provider, youTubePlayerView.player, wasRestored);
            youTubePlayerView.initializedListener = null;
        }
    }

    private final class GlobalFocusChangeListenerImpl
            implements ViewTreeObserver.OnGlobalFocusChangeListener {
        private GlobalFocusChangeListenerImpl() {
        }

        public final void onGlobalFocusChanged(View view, View view2) {
            if (YouTubePlayerView.this.player != null && YouTubePlayerView.this.viewSets.contains(view2) && !YouTubePlayerView.this.viewSets.contains(view)) {
                YouTubePlayerView.this.player.g();
            }
        }
    }

}