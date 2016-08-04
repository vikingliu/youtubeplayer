package com.toutiao.i18n.youtube;


import android.app.Activity;
import android.os.Bundle;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.internal.ab;

public class YouTubeBaseActivity
        extends Activity implements YouTubePlayer.Provider {
    private YouTubePlayerView youTubePlayerView;
    private Bundle bundle;
    private String developerKey;
    private YouTubePlayer.OnInitializedListener initializedListener;
    private boolean start = true;


    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.bundle = bundle != null ? bundle.getBundle("YouTubeBaseActivity.KEY_PLAYER_VIEW_STATE") : null;
    }

    public void initialize(String developerKey, YouTubePlayerView youTubePlayerView, YouTubePlayer.OnInitializedListener initializedListener) {
        this.youTubePlayerView = youTubePlayerView;
        initialize(developerKey, initializedListener);
    }

    @Override
    public void initialize(String developerKey, YouTubePlayer.OnInitializedListener initializedListener) {

        this.developerKey = ab.a(developerKey, "Developer key cannot be null or empty");
        this.initializedListener = initializedListener;
        this.initialize();
    }

    private void initialize() {
        if (this.youTubePlayerView != null && this.initializedListener != null) {
            this.youTubePlayerView.onStart(this.start);
            this.youTubePlayerView.initializeFailure(this, this, this.developerKey, this.initializedListener, this.bundle);
            this.bundle = null;
            this.initializedListener = null;
        }
    }

    protected void onStart() {
        super.onStart();
        if (this.youTubePlayerView != null) {
            this.youTubePlayerView.onStart();
        }
    }

    protected void onResume() {
        super.onResume();
        if (this.youTubePlayerView != null) {
            this.youTubePlayerView.onResume();
        }
    }

    protected void onPause() {
        if (this.youTubePlayerView != null) {
            this.youTubePlayerView.onPause();
        }
        super.onPause();
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        Bundle bundle2 = this.youTubePlayerView != null ? this.youTubePlayerView.getBundle() : this.bundle;
        bundle.putBundle("YouTubePlayerFragment.KEY_PLAYER_VIEW_STATE", bundle2);
    }

    protected void onStop() {
        if (this.youTubePlayerView != null) {
            this.youTubePlayerView.onStop();
        }
        super.onStop();
    }

    protected void onDestroy() {
        if (this.youTubePlayerView != null) {
            this.youTubePlayerView.onPause(this.isFinishing());
        }
        super.onDestroy();
    }
}