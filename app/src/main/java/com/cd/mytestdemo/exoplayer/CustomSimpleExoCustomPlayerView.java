package com.cd.mytestdemo.exoplayer;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.google.android.exoplayer2.SimpleExoPlayer;

/** @deprecated Use {@link CustomPlayerView}. */
@TargetApi(16)
public final class CustomSimpleExoCustomPlayerView extends CustomPlayerView {

  public CustomSimpleExoCustomPlayerView(Context context) {
    super(context);
  }

  public CustomSimpleExoCustomPlayerView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public CustomSimpleExoCustomPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  /**
   * Switches the view targeted by a given {@link SimpleExoPlayer}.
   *
   * @param player The player whose target view is being switched.
   * @param oldPlayerView The old view to detach from the player.
   * @param newPlayerView The new view to attach to the player.
   */
  public static void switchTargetView(
      @NonNull SimpleExoPlayer player,
      @Nullable CustomSimpleExoCustomPlayerView oldPlayerView,
      @Nullable CustomSimpleExoCustomPlayerView newPlayerView) {
    CustomPlayerView.switchTargetView(player, oldPlayerView, newPlayerView);
  }

}