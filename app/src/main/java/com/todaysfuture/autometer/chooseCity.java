package com.todaysfuture.autometer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by rishabh on 17/5/16.
 */
public class chooseCity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.citychooser);
        Button delhincr=(Button)findViewById(R.id.btnDelhiNCR);
        delhincr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(chooseCity.this, MainActivity.class);
                intent.putExtra("number", 1);
                startActivity(intent);
            }
        });
        Button hyderabad=(Button)findViewById(R.id.btnHyd);
        hyderabad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "This feature hasn't been released yet", Toast.LENGTH_LONG).show();
            }
        });


        Button pune=(Button)findViewById(R.id.btnPun);
        pune.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"This feature hasn't been released yet",Toast.LENGTH_LONG).show();
            }
        });

        Button chennai=(Button)findViewById(R.id.btnChen);
        chennai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"This feature hasn't been released yet",Toast.LENGTH_LONG).show();
            }
        });
    }
}
