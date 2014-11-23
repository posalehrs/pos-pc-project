package tu.com.example.mypikachu;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Help extends MainMyapp implements OnClickListener {
 
    Button back;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
     
        back = (Button)findViewById(R.id.back);
        back.setOnClickListener(this);
    }
 
    @Override
    public void onClick(View v) {
        Menu.mSound.playClick();
     
        switch (v.getId()) {
        case R.id.back:
            this.finish();
            break;
 
        default:
            break;
        }
     
    }
 
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Menu.mSound.playClick();
        this.finish();
    }
}

