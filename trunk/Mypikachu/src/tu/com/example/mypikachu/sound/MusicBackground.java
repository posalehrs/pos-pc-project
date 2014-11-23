package tu.com.example.mypikachu.sound;

import tu.com.example.mypikachu.R;
import android.content.Context;
import android.media.MediaPlayer;

public class MusicBackground {
	MediaPlayer mediaPlayer;
	 
    /**
    * Load dữ liệu
    * @param mContext
    */
    public void loadMusic(Context mContext){
        mediaPlayer = MediaPlayer.create(mContext, R.raw.backgroundmusic);
        mediaPlayer.setVolume(0.2f, 0.2f);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //Nhạc nền lặp đi lặp lại, play xong thì lại play lại
                play();
            }
        });
    }
    /**
    * Play music
    */
    public void play(){
        mediaPlayer.seekTo(0);
        mediaPlayer.start();
    }
    /**
    * Pause music
    */
    public void pause(){
        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
    }
    /**
    * Resume music
    */
    public void resume(){
        if(!mediaPlayer.isPlaying())
            mediaPlayer.start();
    }
    /**
    * Xóa bỏ
    */
    public void release(){
        mediaPlayer.release();
    }

}
