package com.primecloud.huafenghuang.video;

import android.content.Context;
import android.media.AudioManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.WindowManager;

import com.primecloud.library.baselibrary.log.XLog;

import cn.jzvd.JZMediaManager;
import cn.jzvd.JZUtils;
import cn.jzvd.JzvdStd;

/**
 * 全屏状态播放完成，不退出全屏
 * Created by Nathen on 2016/11/26.
 */

public class JzvdStdAutoCompleteAfterFullscreen extends JzvdStd {
    public JzvdStdAutoCompleteAfterFullscreen(Context context) {
        super(context);
    }

    public JzvdStdAutoCompleteAfterFullscreen(Context context, AttributeSet attrs) {
        super(context, attrs);
    }




    @Override
    public void startVideo() {

        if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
            Log.d(TAG, " SCREEN_WINDOW_FULLSCREEN   startVideo [" + this.hashCode() + "] "+SCREEN_WINDOW_FULLSCREEN);
            initTextureView();
            addTextureView();
            AudioManager mAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
            mAudioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
            JZUtils.scanForActivity(getContext()).getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

            JZMediaManager.setDataSource(jzDataSource);
            JZMediaManager.instance().positionInList = positionInList;
            onStatePreparing();
        } else {
            super.startVideo();
        }
    }

    @Override
    public void onAutoCompletion() {
        XLog.i("WO LAI LE ..onAutoCompletion.");
        if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
            onStateAutoComplete();

            JZMediaManager.instance().releaseMediaPlayer();
            JZUtils.scanForActivity(getContext()).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            JZUtils.saveProgress(getContext(), jzDataSource.getCurrentUrl(), 0);

        } else {
            super.onAutoCompletion();
        }

    }
}

