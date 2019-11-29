package com.cd.mytestdemo.exoplayer;

import android.content.Context;
import android.util.AttributeSet;

import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.util.RepeatModeUtil;

/** @deprecated Use {@link PlayerControlView}. */
public class CustomPlaybackControlView extends PlayerControlView {

  /** @deprecated Use {@link com.google.android.exoplayer2.ControlDispatcher}. */
  @Deprecated
  public interface ControlDispatcher extends com.google.android.exoplayer2.ControlDispatcher {}

  /**
   * @deprecated Use {@link com.google.android.exoplayer2.ui.PlayerControlView.VisibilityListener}.
   */
  @Deprecated
  public interface VisibilityListener
      extends com.google.android.exoplayer2.ui.PlayerControlView.VisibilityListener {}

  private static final class DefaultControlDispatcher
      extends com.google.android.exoplayer2.DefaultControlDispatcher implements ControlDispatcher {}
  /** @deprecated Use {@link com.google.android.exoplayer2.DefaultControlDispatcher}. */
  @Deprecated
  public static final ControlDispatcher DEFAULT_CONTROL_DISPATCHER = new DefaultControlDispatcher();

  /** The default fast forward increment, in milliseconds. */
  public static final int DEFAULT_FAST_FORWARD_MS = PlayerControlView.DEFAULT_FAST_FORWARD_MS;
  /** The default rewind increment, in milliseconds. */
  public static final int DEFAULT_REWIND_MS = PlayerControlView.DEFAULT_REWIND_MS;
  /** The default show timeout, in milliseconds. */
  public static final int DEFAULT_SHOW_TIMEOUT_MS = PlayerControlView.DEFAULT_SHOW_TIMEOUT_MS;
  /** The default repeat toggle modes. */
  public static final @RepeatModeUtil.RepeatToggleModes int DEFAULT_REPEAT_TOGGLE_MODES =
      PlayerControlView.DEFAULT_REPEAT_TOGGLE_MODES;

  /** The maximum number of windows that can be shown in a multi-window time bar. */
  public static final int MAX_WINDOWS_FOR_MULTI_WINDOW_TIME_BAR =
      PlayerControlView.MAX_WINDOWS_FOR_MULTI_WINDOW_TIME_BAR;

  public CustomPlaybackControlView(Context context) {
    super(context);
  }

  public CustomPlaybackControlView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public CustomPlaybackControlView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public CustomPlaybackControlView(
      Context context, AttributeSet attrs, int defStyleAttr, AttributeSet playbackAttrs) {
    super(context, attrs, defStyleAttr, playbackAttrs);
  }

}