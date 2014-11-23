package tu.com.example.mypikachu;

import java.util.ArrayList;

import tu.com.example.mypikachu.config.Config;
import tu.com.example.mypikachu.database.ClassDollar;
import tu.com.example.mypikachu.database.Database;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HeightDollar extends MainMyapp {
 
    LinearLayout content;
 
    MySharedPreferences mySharedPreferences;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        setContentView(R.layout.activity_heightscore);
   
        mySharedPreferences = new MySharedPreferences(this);
        mySharedPreferences.getThemes();
 
        content = (LinearLayout) findViewById(R.id.content);
   
        Database mDatabase = new Database(this);
        mDatabase.openDatabase();
        //Lấy danh sách 10 người điểm cao nhất them theme
        ArrayList<ClassDollar> listDollar = mDatabase.getListDollar(Config.THEMES);
        mDatabase.closeDatabase();
 
        for (int i = 0; i < listDollar.size(); i++) {
            content.addView(getView(i, listDollar.get(i)));
        }
 
        Button button_yes = (Button) findViewById(R.id.button_yes);
        button_yes.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View v) {
                Menu.mSound.playClick();
                HeightDollar.this.finish();
            }
        });
   
        TextView txt_theme = (TextView)findViewById(R.id.txt_theme);
        txt_theme.setText("Theme: " + Setting.nameTheme[Config.THEMES - 1]);
    }
 
    public View getView(int stt, ClassDollar mClassDollar) {
        View v = View.inflate(this, R.layout.item_top10, null);
        TextView txt_stt = (TextView) v.findViewById(R.id.txt_stt);
        TextView txt_name = (TextView) v.findViewById(R.id.txt_name);
        TextView txt_dollar = (TextView) v.findViewById(R.id.txt_dollar);
 
        txt_stt.setText("" + (stt + 1));
        txt_name.setText(mClassDollar.getName());
        txt_dollar.setText(String.valueOf(mClassDollar.getDollar()));
        return v;
    }
 
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Menu.mSound.playClick();
        HeightDollar.this.finish();
    }
}

