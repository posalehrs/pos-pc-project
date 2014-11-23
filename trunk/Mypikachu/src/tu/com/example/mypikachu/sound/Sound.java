package tu.com.example.mypikachu.sound;

import tu.com.example.mypikachu.R;
import tu.com.example.mypikachu.Setting;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class Sound {
	SoundPool mSoundPool;
    Context context;
    int bad = -1, click = -1, finish = -1, good = -1, random = -1;
    int gameover = -1;
    float volume = 0.5f;
 
    /**
    * Load dữ liệu
    * @param context
    */
    public void loadSound(Context context) {
        this.context = context;
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 100);
        bad = mSoundPool.load(this.context, R.raw.bad, 1);     
        click = mSoundPool.load(this.context, R.raw.click, 1);     
        finish = mSoundPool.load(this.context, R.raw.finish, 1);     
        good = mSoundPool.load(this.context, R.raw.good, 1);     
        random = mSoundPool.load(this.context, R.raw.random, 1);     
     
        gameover = mSoundPool.load(this.context, R.raw.gameover, 1);
    }
 
    public void offSound() {
        volume = 0;
    }
 
    /**
    * Không ăn được
    */
    public void playBad() {
        if (Setting.isSound) {
            new Thread(new Runnable() {             
                @Override
                public void run() {
                    mSoundPool.play(bad, volume, volume, 1, 0, 1f);
                }
            }).start();         
        }
    }
 
    /**
    * Ăn được
    */
    public void playGood() {
        if (Setting.isSound) {
            new Thread(new Runnable() {             
                @Override
                public void run() {
                    mSoundPool.play(good, volume, volume, 1, 0, 1f);
                }
            }).start();         
        }
    }
 
    /**
    * Game over
    */
    public void playGameOver() {
        if (Setting.isSound) {
            new Thread(new Runnable() {             
                @Override
                public void run() {
                    mSoundPool.play(gameover, volume, volume, 1, 0, 1f);
                }
            }).start();         
        }
    }
 
    /**
    * Random
    */
    public void playRandom() {
        if (Setting.isSound) {
            new Thread(new Runnable() {             
                @Override
                public void run() {
                    mSoundPool.play(random, volume, volume, 1, 0, 1f);
                }
            }).start();         
        }
    }
 
    /**
    * Click
    */
    public void playClick() {
        if (Setting.isSound) {
            new Thread(new Runnable() {             
                @Override
                public void run() {
                    mSoundPool.play(click, volume, volume, 1, 0, 1f);
                }
            }).start();         
        }
    }
 
    /**
    * Finish
    */
    public void playFinish() {
        if (Setting.isSound) {
            new Thread(new Runnable() {             
                @Override
                public void run() {
                    mSoundPool.play(finish, volume, volume, 1, 0, 1f);
                }
            }).start();         
        }
    }

}
