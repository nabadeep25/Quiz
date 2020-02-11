package com.nabadeep.quiz2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class StartingActivity extends AppCompatActivity {
    private Button start;

    private EditText name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);
        start=findViewById(R.id.startbutton);



   start.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           Intent intent=new Intent(StartingActivity.this,MainActivity.class);
           startActivity(intent);
       }
   });




    }

}
