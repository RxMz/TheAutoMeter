package com.todaysfuture.autometer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by rishabh on 2/2/16.
 */
public class withDistance extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.withdist);
        final EditText distance=(EditText)findViewById(R.id.etInput);
        Button enter=(Button)findViewById(R.id.btnEnter);
        final TextView displayer=(TextView)findViewById(R.id.tvDisplay);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = distance.getText().toString();
                Double nn = Double.parseDouble(input);
                if (nn <= 2)
                    displayer.setText("₹ 25 Only");
                else {
                    nn = nn - 2;
                    Double lol = nn * 8 + 25.0;
                    displayer.setText("The cost will be ₹ " + lol);
                }

            }
        });
    }
}
