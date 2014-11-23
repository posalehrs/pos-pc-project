package tu.com.example.mypikachu.util;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

public class UtilActivity {
	/**
	    * @param ac
	    */
	    public static void requestWindowFeature(Activity ac) {
	        ac.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        ac.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	                WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    }

}
