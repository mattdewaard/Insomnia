package insomniac.insomniacv112;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class NavigationActivity extends TransitionActivity implements View.OnClickListener {
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        btn1 = (Button) findViewById(R.id.btn_1);
        btn2 = (Button) findViewById(R.id.btn_2);
        btn3 = (Button) findViewById(R.id.btn_3);
        btn4 = (Button) findViewById(R.id.btn_4);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
    }

    public void setText(String feedback, String text1, String text2, String text3, String text4){
        TextView feedbackBox = (TextView) findViewById(R.id.textView_feedback);
        Button b1 = (Button) findViewById(R.id.btn_1);
        Button b2 = (Button) findViewById(R.id.btn_2);
        Button b3 = (Button) findViewById(R.id.btn_3);
        Button b4 = (Button) findViewById(R.id.btn_4);
        b1.setText(text1);
        b2.setText(text2);
        b3.setText(text3);
        b4.setText(text4);
        feedbackBox.setText(feedback);
    }

    public void onClick(View v){

    }
}
