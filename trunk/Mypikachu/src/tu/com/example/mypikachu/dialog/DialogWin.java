package tu.com.example.mypikachu.dialog;

import tu.com.example.mypikachu.Menu;
import tu.com.example.mypikachu.Play;
import tu.com.example.mypikachu.R;
import tu.com.example.mypikachu.config.Config;
import tu.com.example.mypikachu.database.ClassDollar;
import tu.com.example.mypikachu.database.Database;
import tu.com.example.mypikachu.util.Util;
import tu.com.example.mypikachu.util.UtilDialog;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DialogWin extends Dialog {
    Activity activity;
 
    public DialogWin(final Context context) {
        super(context);
        UtilDialog.iniDialog(this);
        activity = (Activity) context;
 
        setContentView(R.layout.dialog_win);
 
        Database mDatabase = new Database(context);
        mDatabase.openDatabase();
        /*Kiểm tra nếu được insert tức là người chơi có điểm trong top 10, nếu không
        * được insert thì sẽ quay lại menu
        */
        final int checkInsert = mDatabase.checkIsInsert(new ClassDollar(" ", Play.mPlay.dollar_current, Config.THEMES));
        mDatabase.closeDatabase();
 
        //resize dialog
        RelativeLayout linearLayout = (RelativeLayout)findViewById(R.id.linearLayout);
        Util.resizeDialog(linearLayout);
 
        Button button_yes = (Button) findViewById(R.id.button_yes);
        button_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Menu.mSound.playClick();
                DialogWin.this.dismiss();
                if(checkInsert != -1){
                    showDialogSaveDollar(context);
                }else{
                    Play.mPlay.finish();
                }
            }
        });
 
        TextView textView_dollar = (TextView)findViewById(R.id.textView_dollar);
        textView_dollar.setText("" + Play.mPlay.dollar_current);
    }
 
    public void showDialogSaveDollar(Context context){
        DialogSaveDollar mDialogSaveDollar = new DialogSaveDollar(context);
        mDialogSaveDollar.show();
    }
 
}

