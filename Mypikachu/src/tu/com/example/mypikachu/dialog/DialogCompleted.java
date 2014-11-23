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
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DialogCompleted extends Dialog {
    Activity activity;
    /**
    *
    * @param context
    * @param timeend : Dựa vào time kết thúc ta tính được thời gian còn lại và số sao đạt được
    */
    public DialogCompleted(final Context context, int timeend) {
        super(context);
        UtilDialog.iniDialog(this);
        activity = (Activity) context;
 
        setContentView(R.layout.dialog_completed);
   
        //resize dialog
        RelativeLayout linearLayout = (RelativeLayout)findViewById(R.id.linearLayout);
        Util.resizeDialog(linearLayout);
 
        Button button_next = (Button) findViewById(R.id.button_next);
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Menu.mSound.playClick();
                Play.mPlay.showDialogPlay(1);
                DialogCompleted.this.dismiss();
            }
        });
 
        Button button_level = (Button) findViewById(R.id.button_level);
        button_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Menu.mSound.playClick();
                DialogCompleted.this.dismiss();
                activity.finish();
            }
        });
 
        TextView textView_time = (TextView) findViewById(R.id.textView_time);
        textView_time.setText(UtilFormat.getTime(timeend));
 
        ImageView imageView_star1 = (ImageView) findViewById(R.id.imageView_star1);
        ImageView imageView_star2 = (ImageView) findViewById(R.id.imageView_star2);
        ImageView imageView_star3 = (ImageView) findViewById(R.id.imageView_star3);
   
        //Xác định số sao đạt được với số thời gian còn lại của người chơi
        int numberstar = Level.getStarByLevel(Level.levelCurrent, timeend);
 
        if (numberstar > 0) {
            imageView_star1.setVisibility(View.VISIBLE);
        } else {
            imageView_star1.setVisibility(View.GONE);
        }
 
        if (numberstar > 1) {
            imageView_star2.setVisibility(View.VISIBLE);
        } else {
            imageView_star2.setVisibility(View.GONE);
        }
 
        if (numberstar > 2) {
            imageView_star3.setVisibility(View.VISIBLE);
        } else {
            imageView_star3.setVisibility(View.GONE);
        }
 
        TextView textView_bonus = (TextView) findViewById(R.id.textView_bonus);
        //Tính điểm thưởng
        /**
        * Điểm thưởng được tính khá đơn giản: Ban đầu sẽ tính là level hiện tại * 1000.
        * Nếu 1 sao thì + thêm 10000
        * ----2----------------15000
        * ----3----------------30000
        *
        * Không có sao nào thì không cộng.
        */
        int total_bonus = 0;   
        total_bonus = Level.levelCurrent * 1000;
   
        switch (numberstar) {
        case 0:       
            break;
        case 1:
            total_bonus = total_bonus + 10000;
            break;
        case 2:
            total_bonus = total_bonus + 15000;
            break;
        case 3:
            total_bonus = total_bonus + 30000;
            break;
        default:
            break;
        }
 
        Play.mPlay.total_dollar = Play.mPlay.total_dollar + total_bonus;
        Play.mPlay.dollar_current = Play.mPlay.total_dollar;
        Play.mPlay.mDollar.updateDollar();
   
        textView_bonus.setText("+" + total_bonus);
   
        /*Dựa vào level vừa hoàn thành ta xác định xem đó có phải là level cuối cùng chưa
        * Nếu là level cuối cùng thì người chơi là người thắng cuộc.
        */   
        if(Level.levelCurrent > Level.totalLevel){
       
            button_next.setVisibility(View.GONE);
            button_level.setVisibility(View.GONE);
       
            Button button_yes = (Button)findViewById(R.id.button_yes);
            button_yes.setVisibility(View.VISIBLE);
            button_yes.setOnClickListener(new View.OnClickListener() {           
                @Override
                public void onClick(View arg0) {
                    Menu.mSound.playClick();
                    DialogCompleted.this.dismiss();
               
                    //Hiện thị dialog win. Vượt qua mọi level
                    Handler mHandler = new Handler();
                    mHandler.postDelayed(new Runnable() {       
                        @Override
                        public void run() {
                            //Hiện thị dialog người chơi thắng cuộc
                            DialogWin mDialogWin = new DialogWin(context);
                            mDialogWin.show();
                        }
                    }, 0);
                }
            });
        }
    }
}

