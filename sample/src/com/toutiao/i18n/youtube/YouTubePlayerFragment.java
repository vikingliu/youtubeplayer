package com.toutiao.i18n.youtube;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.internal.ab;

public class YouTubePlayerFragment
        extends Fragment
        implements YouTubePlayer.Provider {
    private Bundle bundle;
    private YouTubePlayerView youTubePlayerView;
    private String developerKey;
    private YouTubePlayer.OnInitializedListener initializedListener;
    private boolean start = true;


    public YouTubePlayerFragment() {
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
            this.youTubePlayerView.initializeFailure(this.getActivity(), this, this.developerKey, this.initializedListener, this.bundle);
            this.bundle = null;
            this.initializedListener = null;
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.bundle = bundle != null ? bundle.getBundle("YouTubePlayerFragment.KEY_PLAYER_VIEW_STATE") : null;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.youTubePlayerView = new YouTubePlayerView(this.getActivity(), null, 0);
        this.initialize();
        return this.youTubePlayerView;
    }

    public void onStart() {
        super.onStart();
        this.youTubePlayerView.onStart();
    }

    public void onResume() {
        super.onResume();
        this.youTubePlayerView.onResume();
    }

    public void onPause() {
        this.youTubePlayerView.onPause();
        super.onPause();
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        Bundle bundle2 = this.youTubePlayerView != null ? this.youTubePlayerView.getBundle() : this.bundle;
        bundle.putBundle("YouTubePlayerFragment.KEY_PLAYER_VIEW_STATE", bundle2);
    }

    public void onStop() {
        this.youTubePlayerView.onStop();
        super.onStop();
    }

    public void onDestroyView() {
        this.youTubePlayerView.onPause(this.getActivity().isFinishing());
        this.youTubePlayerView = null;
        super.onDestroyView();
    }

    public void onDestroy() {
        if (this.youTubePlayerView != null) {
            Activity activity = this.getActivity();
            this.youTubePlayerView.onResume(activity == null || activity.isFinishing());
        }
        super.onDestroy();
    }

}