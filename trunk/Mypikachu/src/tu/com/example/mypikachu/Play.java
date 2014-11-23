package tu.com.example.mypikachu;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.scene.Scene;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import tu.com.example.mypikachu.components.Background;
import tu.com.example.mypikachu.components.ButtonHint;
import tu.com.example.mypikachu.components.ButtonPause;
import tu.com.example.mypikachu.components.ControllOnclick;
import tu.com.example.mypikachu.components.Dollar;
import tu.com.example.mypikachu.components.DrawLine;
import tu.com.example.mypikachu.components.Hint;
import tu.com.example.mypikachu.components.Level;
import tu.com.example.mypikachu.components.MT;
import tu.com.example.mypikachu.components.ManagerItemPikachu;
import tu.com.example.mypikachu.components.Timer;
import tu.com.example.mypikachu.components.TextLoading;
import tu.com.example.mypikachu.dialog.DialogCompleted;
import tu.com.example.mypikachu.dialog.DialogGameOver;
import tu.com.example.mypikachu.dialog.DialogPause;
import tu.com.example.mypikachu.dialog.DialogPlay;
import tu.com.example.mypikachu.dialog.DialogSaveDollar;
import tu.com.example.mypikachu.dialog.DialogWin;
import tu.com.example.mypikachu.sound.MusicBackground;

public class Play extends Game {
    /**
    * Khai báo các thuộc tính
    */
    public static Play mPlay;
 
    Background mBackground;
    TextLoading mTextLoading;
    Timer mTimer;
    ButtonPause mButtonPause;
    ButtonHint mButtonHint;
    public Dollar mDollar;
    public Hint mHint;
    public DrawLine mDrawLine;
    public ManagerItemPikachu mManagerItemPikachu;
 
    public boolean GameOver = false;
    /**
    * Số dollar của người chơi có
    */
    public int total_dollar = 0;
    /**
    * Số dollar hiện tại của người chơi
    */
    public int dollar_current = 0;
    /**
    * Quản lý nhạc nền
    */
    public MusicBackground musicBackground;
 
    @Override
    protected void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
 
 
        musicBackground = new MusicBackground();
        musicBackground.loadMusic(this);
 
        if (Setting.isMusic)
            musicBackground.play();
        else{
            musicBackground.play();
            musicBackground.pause();
        }
        //Ban đầu level = 1
        Level.levelCurrent = 1;
        ControllOnclick.isOnClickItem = true;
 
        //Khởi tạo các đối tượng
        mPlay = this;
 
        mBackground = new Background();
        mBackground.onCreate(mContext, mEngine, mCamera);
 
        mTextLoading = new TextLoading();
        mTextLoading.onCreate(mPlay, mEngine, mCamera);
 
        mButtonPause = new ButtonPause();
        mButtonPause.onCreate(mContext, mEngine, mCamera);
 
        mButtonHint = new ButtonHint();
        mButtonHint.onCreate(mPlay, mEngine, mCamera);
 
        mDollar = new Dollar();
        mDollar.onCreate(mContext, mEngine, mCamera);
 
        mTimer = new Timer();
        mTimer.onCreate(mContext, mEngine, mCamera);
 
        mHint = new Hint();
        mHint.onCreate(mContext, mEngine, mCamera);
 
        mManagerItemPikachu = new ManagerItemPikachu(mContext, mEngine, mCamera);
 
        mDrawLine = new DrawLine();
        mDrawLine.onCreate(mPlay, mEngine, mCamera);
    }
 
    @Override
    public Engine onLoadEngine() {
        super.onLoadEngine();
        return mEngine;
    }
 
    @Override
    public void onLoadResources() {
        super.onLoadResources();
        mBackground.onLoadResources();
        mTextLoading.onLoadResources();
 
    }
 
    @Override
    public Scene onLoadScene() {
        super.onLoadScene();
        mBackground.onLoadScene(mScene);
        mTextLoading.onLoadScene(mScene);
        load();
        return mScene;
    }
 
    // ======================================================================
    /**
    * Load và hiện thị các đối tượng
    */
    public void load() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mTimer.onLoadResources();
                mButtonPause.onLoadResources();
                mButtonHint.onLoadResources();
                mDollar.onLoadResources();
                mHint.onLoadResources();
 
                // ----------------------
                mButtonPause.onLoadScene(mScene);
                mButtonHint.onLoadScene(mScene);
                mDollar.onLoadScene(mScene);
 
                mTimer.setXStartEnd(mDollar.getXendDollar(),
                        mButtonPause.getStartX());
                mTimer.setMidYButtonPause(mButtonPause.getMidY());
                mTimer.onLoadScene(mScene);
 
                MT.getTotalRowColumn(mDollar.getEndY());
                mManagerItemPikachu.setScene(mScene);
                mManagerItemPikachu.addItem();
 
                mHint.onLoadScene(mScene);
                mDrawLine.onLoadScene(mScene);
 
                // Load ok
                mTextLoading.hideTextLoading();
                // Show dialog play
                showDialogPlay(0);
                ControllOnclick.searchHint();
            }
        }).start();
    }
    /**
    * Hiện thị dialog play
    * @param playnext
    */
    public void showDialogPlay(int playnext) {
        mHandler_showDialogPlay.sendEmptyMessage(playnext);
    }
 
    Handler mHandler_showDialogPlay = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            DialogPlay mDialogPlay = new DialogPlay(Play.this, msg.what);
            mDialogPlay.show();
        }
    };
 
    /**
    * Bắt đầu game
    */
    public void startGame() {
        ControllOnclick.isOnClickItem = true;
        GameOver = false;
        mTimer.setTotalTime(Level.getTimeLevel());
        mTimer.start();
        mManagerItemPikachu.showItemEffect(Level.levelCurrent % 8, 1);
    }
    /**
    * Tạm dừng game
    */
    public void pauseGame() {
        mTimer.setPause(true);
        DialogPause mDialogPause = new DialogPause(this);
        mDialogPause.show();
    }
    /**
    * tiếp tục game
    */
    public void resumeGame() {
        mTimer.setPause(false);
    }
    /**
    * Reset lại game
    */
    public void resetGame() {
        GameOver = false;
        mTimer.setStop(true);
        mTextLoading.showTextLoading();
        mHint.setVisiable(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                ControllOnclick.isOnClickItem = true;
                mBackground.resetBackground();
 
                mManagerItemPikachu.reset();
                MT.reRandomMT();
                mManagerItemPikachu.addItem();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mTextLoading.hideTextLoading();
                mDollar.reset();
                mManagerItemPikachu.showItemEffect(Level.levelCurrent % 8, 1);
                mTimer.setTotalTime(Level.getTimeLevel());
                mTimer.start();
                sortChildren();
                ControllOnclick.searchHint();
            }
        }).start();
    }
    /**
    * Hiện thị gợi ý
    * @param i1
    * @param j1
    * @param i2
    * @param j2
    */
    public void setHint(int i1, int j1, int i2, int j2) {
        mHint.setHint(i1, j1, i2, j2);
    }
    /**
    * Xóa bỏ 1 đối tượng pikachu
    * @param i
    * @param j
    */
    public void removeItem(int i, int j) {
        try {
            mManagerItemPikachu.removeItem(i, j);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    /**
    * Chuyển vị trí
    */
    public void swapItem() {
        mManagerItemPikachu.swapItem();
    }
    /**
    * Sắp xếp
    */
    public void sortChildren() {
        try {
            mScene.sortChildren();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    /**
    * Hiện thị dialog khi hoàn thành 1 level
    */
    public void showDialogCompleted() {
        Menu.mSound.playFinish();
 
        final int timeEnd = mTimer.getTimeEnd();
        mTimer.setStop(true);
        Play.mPlay.total_dollar = Play.mPlay.dollar_current;
 
        Level.levelCurrent = Level.levelCurrent + 1;
        if(Level.levelCurrent>=10){
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                DialogWin mDialogWin = new DialogWin(
                        Play.this);
                mDialogWin.show();
            }
        }, 800);
        }
        else {
        	Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    DialogCompleted mDialogCompleted = new DialogCompleted(
                            Play.this, timeEnd);
                    mDialogCompleted.show();
                }
            }, 800);
     
		}
        
    }
    /**
    * Kết thúc game
    */
    public void gameOver() {
        mHandler_gameover.sendEmptyMessage(0);
    }
 
    Handler mHandler_gameover = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
 
            DialogGameOver mDialogGameOver = new DialogGameOver(Play.this);
            mDialogGameOver.show();
        }
    };
    /**
    * Thoát game hiện thị dialog pause
    */
    @Override
    public void onBackPressed() {
        Menu.mSound.playClick();
        Play.mPlay.pauseGame();
    }
 
    // ======================================================================
 
    @Override
    public void onLoadComplete() {
        // TODO Auto-generated method stub
    }
 
    @Override
    protected void onStop() {
        super.onStop();
    }
    /**
    * Hủy các đối tượng
    */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            mTimer.onDestroy();
            mHint.onDestroy();
            musicBackground.release();
        } catch (Exception e) {
        }
    }
}

