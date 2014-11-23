package tu.com.example.mypikachu.dialog;

import tu.com.example.mypikachu.Menu;
import tu.com.example.mypikachu.Play;
import tu.com.example.mypikachu.R;
import tu.com.example.mypikachu.components.Level;
import tu.com.example.mypikachu.util.Util;
import tu.com.example.mypikachu.util.UtilDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DialogGameOver extends Dialog implements
		android.view.View.OnClickListener {

	public DialogGameOver(Context context) {
		super(context);
		UtilDialog.iniDialog(this);
		Menu.mSound.playGameOver();
		setContentView(R.layout.dialog_gameover);

		// resize dialog
		RelativeLayout linearLayout = (RelativeLayout) findViewById(R.id.linearLayout);
		Util.resizeDialog(linearLayout);

		// Hiện thị tổng số dollar và level
		TextView textView_dollar = (TextView) findViewById(R.id.textView_dollar);
		textView_dollar.setText(Play.mPlay.dollar_current + " - L."
				+ Level.levelCurrent);

		Button button_yes = (Button) findViewById(R.id.button_yes);
		button_yes.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Menu.mSound.playClick();
		switch (v.getId()) {
		case R.id.button_yes:
			this.dismiss();
			//Handler mHandler = new Handler();
	       // mHandler.postDelayed(new Runnable() {
	            //@Override
	            //public void run() {
			if(Level.levelCurrent>=2){
	                DialogSaveDollar mDialogSaveDollar = new DialogSaveDollar(
	                        Play.mPlay);
	                mDialogSaveDollar.show();
			}else {
				Play.mPlay.finish();

			}
	          //  }
	      //  }, 800);
			break;
		default:
			break;
		}

	}

}
