package tu.com.example.mypikachu.components;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import tu.com.example.mypikachu.Menu;
import tu.com.example.mypikachu.Play;
import tu.com.example.mypikachu.config.Config;

import android.content.Context;

public class ButtonHint extends MySprite{
    BitmapTextureAtlas buttonhint_BTA;
    Sprite buttonhint_SP;
    TextureRegion buttonhint_TR;
 
    @Override
    public void onCreate(Context mContext, Engine mEngine, Camera mCamera) {
        this.ini(mContext, mEngine, mCamera);
    }
 
    @Override
    public void onLoadResources() {
        this.buttonhint_BTA = new BitmapTextureAtlas(128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.buttonhint_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.buttonhint_BTA, mContext, "icon_question.png", 0, 0);
        this.mEngine.getTextureManager().loadTextures(this.buttonhint_BTA);
    }
 
    @Override
    public void onLoadScene(Scene mScene) {
        this.mScene = mScene;
        //Tính toán chiều rộng, cao, vị trí x, y
        int w = (int)(this.buttonhint_TR.getWidth() * Config.getRaceWidth());
        int h = (this.buttonhint_TR.getHeight() * w / this.buttonhint_TR.getWidth());   
        int x = Config.SCREENWIDTH - w;
        int y = Config.SCREENHIEGHT - h - 5;
   
        this.buttonhint_SP = new Sprite(x, y, w, h, this.buttonhint_TR){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
                    float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN){
                    Menu.mSound.playClick();
                    //Nếu click vào thì sẽ phóng to lên 1 chút để có hiệu ứng
                    buttonhint_SP.setScale(1.3f);
                }else if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP){
                    //Thu về trạng thái bình thường
                    buttonhint_SP.setScale(1f);
                    onClickButtonHint();
                }
                return true;
            }
        };
        this.mScene.registerTouchArea(buttonhint_SP);
        this.mScene.attachChild(buttonhint_SP);
    }
 
    public void onClickButtonHint(){
        ControllOnclick.activeSearhHint();
   
        if(!Play.mPlay.mHint.visiable()){
 
            Play.mPlay.mDollar.addDollar(-500);   
            Play.mPlay.mDollar.addTextSubDollar("-500 $");
        }
        Play.mPlay.mHint.setVisiable(true);
   
    }
 
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
   
    }
}

