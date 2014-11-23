package tu.com.example.mypikachu;

import tu.com.example.mypikachu.config.Config;
import tu.com.example.mypikachu.dialog.DialogExit;
import tu.com.example.mypikachu.sound.Sound;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Menu extends MainMyapp implements OnClickListener {
 
    Button play, help, setting,  exit, highscore;
 
    public static Sound mSound;
 
    MySharedPreferences mySharedPreferences;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
 
        mySharedPreferences = new MySharedPreferences(this);
        mySharedPreferences.getIsMusic();
        mySharedPreferences.getIsSound();
        mySharedPreferences.getThemes();
 
        mSound = new Sound();
        mSound.loadSound(this);
 
        Config.getDisplayScreen(this);
 
        play = (Button) findViewById(R.id.btnplay);
        play.setOnClickListener(this);
 
        help = (Button) findViewById(R.id.btnhelp);
        help.setOnClickListener(this);
 
        setting = (Button) findViewById(R.id.btnoption);
        setting.setOnClickListener(this);
 
        
 
        exit = (Button) findViewById(R.id.exit);
        exit.setOnClickListener(this);
 
        highscore = (Button) findViewById(R.id.btnhighcore);
        highscore.setOnClickListener(this);
    }
 
    // ------------------------------------------------------
    @Override
    public void onClick(View v) {
        // Add sound click
        mSound.playClick();
 
        switch (v.getId()) {
        case R.id.btnplay:
            clickPlay();
            break;
        case R.id.btnhelp:
            clickHelp();
            break;
        case R.id.btnoption:
            clickSetting();
            break;
        
        case R.id.exit:
            clickExit();
            break;
        case R.id.btnhighcore:
            clickTop10();
            break;
        default:
            break;
        }
    }
 
    // ------------------------------------------------------
    public void clickPlay() {
        try {
            Intent intent = new Intent(this, Play.class);
            this.startActivity(intent);
        } catch (Exception e) {
        }
    }
 
    // ------------------------------------------------------
    public void clickHelp() {
        try {
            Intent intent = new Intent(this, Help.class);
            this.startActivity(intent);
        } catch (Exception e) {
        }
    }
 
    // ------------------------------------------------------
    public void clickExit() {
        try {
            DialogExit dialogExit = new DialogExit(this);
            dialogExit.show();
        } catch (Exception e) {
        }
    }
 
    // ------------------------------------------------------
    public void clickSetting() {
        try {
            Intent intent = new Intent(this, Setting.class);
            this.startActivity(intent);
        } catch (Exception e) {
        }
    }
 
    // ------------------------------------------------------
    public void clickTop10() {
        try {
            Intent intent = new Intent(this, HeightDollar.class);
            this.startActivity(intent);
        } catch (Exception e) {
        }
    }
    // ------------------------------------------------------
    
    // ------------------------------------------------------
    @Override
    public void onBackPressed() {
        mSound.playClick();
        clickExit();
    }
}

