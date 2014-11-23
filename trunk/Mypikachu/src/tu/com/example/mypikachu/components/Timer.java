package tu.com.example.mypikachu.components;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import tu.com.example.mypikachu.Play;
import tu.com.example.mypikachu.config.Config;
import tu.com.example.mypikachu.util.UtilFormat;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;

public class Timer extends MySprite {
 
   
	BitmapTextureAtlas fonttimer;
	Font mfont;
	int heightfont=30;
	ChangeableText txtTimer;
 
    BitmapTextureAtlas prb_BTA;
    Sprite prb_SP;
    TextureRegion prb_TR;
 
    /**
    */
    int total_time = 0;
 
    /**
    */
    int current_time = 0;
 
    /**
    */
    boolean isPause = false;
    boolean isStop = false;
    float pX_start = 0, pX_end = 0, midYButtonPause = 0;
 
    // -----------------------------------------------
    @Override
    public void onCreate(Context mContext, Engine mEngine, Camera mCamera) {
        this.ini(mContext, mEngine, mCamera);
    }
 
    // -----------------------------------------------
    @Override
    public void onLoadResources() {
        heightfont=(int)(Config.getRaceHeight()*heightfont);

        this.prb_BTA = new BitmapTextureAtlas(64, 64,
                TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.prb_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
                this.prb_BTA, mContext, "icon_timer.png", 0, 0);
        this.mEngine.getTextureManager().loadTextures(this.prb_BTA);
        prb_BTA = new BitmapTextureAtlas(512, 512,
                TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        mfont = new Font(this.prb_BTA, Typeface.createFromAsset(
                mContext.getAssets(), "font/cambriaz_0.ttf"), heightfont,
                true, Color.WHITE);
        mEngine.getTextureManager().loadTexture(this.prb_BTA);
        mEngine.getFontManager().loadFont(this.mfont);
    }
 
    // -----------------------------------------------
    @Override
    public void onLoadScene(Scene mScene) {
        this.mScene = mScene;
        int h = (int)(prb_TR.getHeight() * Config.getRaceHeight());
        int y = 5 ;
        int w= (int)(prb_TR.getWidth()*Config.getRaceWidth());
       
        this.prb_SP = new Sprite(pX_start, y, w, h, this.prb_TR);
        this.mScene.attachChild(prb_SP);
        txtTimer=new ChangeableText(pX_start+w, y+midYButtonPause/2, this.mfont, UtilFormat.getTime(Level.getTimeLevel()),30);
        updateTimer(current_time);
        this.mScene.attachChild(txtTimer);
 
        
    }
 
    // -----------------------------------------------
    /**
    * Bắt đầu chạy thời gian đếm ngược
    */
    public void start() {
        if (total_time < 0)
            return;
 
        current_time = total_time;
        
        isStop = false;
        isPause = false;
 
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isStop && current_time > 0) {
                    if (!isPause) {
                        try {
                            Thread.sleep(1000);
                            current_time--;
                            updateTimer(current_time);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
                // 
                if (current_time <= 0) {
                    Play.mPlay.gameOver();
                }
            }
        }).start();
 
    }
 
    // -----------------------------------------------
    /**
    * 
    * @param w
    */
    public void updateTimer(int s) {
        if (current_time > 0 && !this.isStop) {
           // mRectangle.setWidth(mRectangle.getWidth() - w);
        	txtTimer.setText(UtilFormat.getTime(current_time));
        } else {
           // mRectangle.setVisible(false);
        }
    }
 
    // -----------------------------------------------
    public void setTotalTime(int total_time) {
        this.total_time = total_time;
    }
 
    public void setPause(boolean isPause) {
        this.isPause = isPause;
    }
 
    public void setStop(boolean isStop) {
        this.isStop = isStop;
    }
 
    /**
    * Thời gian được tính bẳng tổng thời gian trừ đi thời gian đã trôi qua
    *
    * @return
    */
    public int getTimeEnd() {
        return Math.abs(this.current_time - this.total_time);
    }
 
    public void setXStartEnd(float pX_start, float pX_end) {
        this.pX_start = pX_start;
        this.pX_end = pX_end;
    }
 
    public void setMidYButtonPause(float midYButtonPause){
        this.midYButtonPause = midYButtonPause;
    }
    // -----------------------------------------------
    @Override
    public void onDestroy() {
        this.isStop = true;
    }
}


