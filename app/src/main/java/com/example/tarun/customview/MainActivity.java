package com.example.tarun.customview;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final SignatureView sv = (SignatureView)  findViewById(R.id.sign_view);
        sv.setSignatureSaveListener(new SignatureView.OnSignatureSaveListener() {
            @Override
            public void onError(String errorMsg) {
                Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String filePath) {
                Toast.makeText(MainActivity.this, filePath, Toast.LENGTH_SHORT).show();
            }
        });

        Button bSave = (Button) findViewById(R.id.btn_save);
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sv.saveSignature();
            }
        });


        final Button bClear = (Button) findViewById(R.id.btn_clear);
        bClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sv.clear();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
