package com.github.mccxj.igo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.github.mccxj.igo.R;

public class MainActivity extends Activity implements OnClickListener {
    private Button button_start;
    private Button button_test;
    private Button button_setting;
    private Button button_about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_start = (Button) findViewById(R.id.button_start);
        button_start.setOnClickListener(this);
        button_test = (Button) findViewById(R.id.button_test);
        button_test.setOnClickListener(this);
        button_setting = (Button) findViewById(R.id.button_setting);
        button_setting.setOnClickListener(this);
        button_about = (Button) findViewById(R.id.button_about);
        button_about.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.button_start:
            startActivity(new Intent(this, GameActivity.class));
            break;
        case R.id.button_test:
            break;
        case R.id.button_setting:
            startActivity(new Intent(this, SettingActivity.class));
            break;
        case R.id.button_about:
            break;
        }
    }

}
