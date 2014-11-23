package tu.com.example.mypikachu.dialog;

import tu.com.example.mypikachu.Menu;
import tu.com.example.mypikachu.Play;
import tu.com.example.mypikachu.R;
import tu.com.example.mypikachu.components.Level;
import tu.com.example.mypikachu.util.Util;
import tu.com.example.mypikachu.util.UtilDialog;
import tu.com.example.mypikachu.util.UtilFormat;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DialogPlay extends Dialog{
    Activity activity;
    /**
    *
    * @param context
    * @param playnext: Xác định là reset game hay là bắt đầu chơi mới
    */
    public DialogPlay(Context context, final int playnext) {
        super(context);
        UtilDialog.iniDialog(this);
        activity = (Activity) context;
     
        setContentView(R.layout.dialog_play);
     
        //resize dialog
        LinearLayout linearLayout1 = (LinearLayout)findViewById(R.id.linearLayout1);
        Util.resizeDialog(linearLayout1);
     
        Button play = (Button)findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {         
            @Override
            public void onClick(View v) {
                Menu.mSound.playClick();
                if(playnext == 0)
                    Play.mPlay.startGame();
                else Play.mPlay.resetGame();
                DialogPlay.this.dismiss();
            }
        });     
     
        TextView textView_level = (TextView)findViewById(R.id.textView_level);
        textView_level.setText("Level " + String.valueOf(Level.levelCurrent));
     
        TextView textView_time = (TextView)findViewById(R.id.textView_time);
        textView_time.setText(UtilFormat.getTime(Level.getTimeLevel()));
    }
 
}

