package model;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import insomniac.insomniacv112.R;


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
        if(text1.length() != 0) btn1.setText(text1);
        else setInvisible(btn1);
        if(text2.length() != 0) btn2.setText(text2);
        else setInvisible(btn2);
        if(text3.length() != 0) btn3.setText(text3);
        else setInvisible(btn3);
        if(text4.length() != 0) btn4.setText(text4);
        else setInvisible(btn4);
        feedbackBox.setText(feedback);
    }

    private void setInvisible(Button btn){
        ViewGroup.LayoutParams params = btn.getLayoutParams();
        params.width = 0;
        btn.setLayoutParams(params);
    }
    public void onClick(View v){

    }
}
