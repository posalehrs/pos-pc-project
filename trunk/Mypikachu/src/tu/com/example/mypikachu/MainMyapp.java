package tu.com.example.mypikachu;

import tu.com.example.mypikachu.util.UtilActivity;
import android.os.Bundle;
import android.app.Activity;

public class MainMyapp extends Activity {

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UtilActivity.requestWindowFeature(this);
        this.getWindow().getAttributes().windowAnimations = R.style.Animations_Activity;
    }

}
