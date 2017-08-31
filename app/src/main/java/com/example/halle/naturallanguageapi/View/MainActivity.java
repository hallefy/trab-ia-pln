package com.example.halle.naturallanguageapi.View;

import android.content.Intent;
import android.graphics.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.halle.naturallanguageapi.R;

public class MainActivity extends AppCompatActivity {


    Button viewTxt,viewCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        viewTxt = (Button) findViewById(R.id.btnViewTxt);
        viewCamera = (Button) findViewById(R.id.btnViewCamera);

        viewTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,NaturalLanguage.class));
            }
        });


        viewCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,CameraActivity.class));
            }
        });

    }
}
