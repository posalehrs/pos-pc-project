package tu.com.example.mypikachu.util;

import tu.com.example.mypikachu.R;
import android.app.Dialog;
import android.view.Window;

public class UtilDialog {
	/**
	    * @param dialog
	    */
	    public static void iniDialog(Dialog dialog){
	        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_null);
	        dialog.setCancelable(false);
	        dialog.getWindow().getAttributes().windowAnimations = R.style.Animations_Dialog;
	    }

}
