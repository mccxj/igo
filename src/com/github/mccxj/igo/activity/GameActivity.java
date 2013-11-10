package com.github.mccxj.igo.activity;

import com.github.mccxj.igo.R;
import com.github.mccxj.igo.view.GameView;

import android.app.Activity;
import android.os.Bundle;

public class GameActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }
}
