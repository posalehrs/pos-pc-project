package tu.com.example.mypikachu.util;

import tu.com.example.mypikachu.config.Config;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

public class Util {
	/**
	    * @param min
	    * @param max
	    * @return
	    */
	    public static int getRandomIndex(int min, int max) {
	        return (int) (Math.random() * (max - min + 1)) + min;
	    }
	    /**
	    * @param mContext
	    * @param txt
	    */
	    public static void showToast(Context mContext, String txt) {
	        Toast.makeText(mContext, txt, Toast.LENGTH_SHORT).show();
	    }
	    /**
	    * Resize dialog
	    * @param v
	    */
	    public static void resizeDialog(View v) {
	        if (Config.SCREENWIDTH < 800) {
	            int h_new = v.getLayoutParams().height * (Config.SCREENWIDTH - 50) / v.getLayoutParams().width;
	            v.getLayoutParams().height = h_new;
	            v.getLayoutParams().width = Config.SCREENWIDTH - 50;
	        }
	    }
	

}
