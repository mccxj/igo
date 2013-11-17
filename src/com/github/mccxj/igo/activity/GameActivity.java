package com.github.mccxj.igo.activity;

import java.io.InputStream;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.github.mccxj.go.sgf.SGFGame;
import com.github.mccxj.go.sgf.SGFGameTree;
import com.github.mccxj.go.sgf.parser.SGF;
import com.github.mccxj.igo.R;
import com.github.mccxj.igo.view.GameView;

public class GameActivity extends Activity {
    private GameView gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //final View v = View.inflate(this, R.layout.activity_game, null);
        //gv = (GameView) v.findViewById(R.id.game);
        gv = new GameView(this);
        setContentView(gv);

        loadSGF(1);

//        Button b1 = (Button) v.findViewById(R.id.button1);
//        b1.setOnClickListener(new OnClickListener() {
//            int idx = 1;
//
//            @Override
//            public void onClick(View v) {
//                idx++;
//                if (idx > 8)
//                    idx = 1;
//
//                loadSGF(idx);
//
//            }
//        });

        //setContentView(v);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gv.onTouchEvent(event);
    }

    private void loadSGF(int idx) {
        InputStream is;
        try {
            is = getAssets().open("00" + idx + ".sgf");
            SGFGameTree gameTree = new SGF(is, "GBK").GameTree();
            SGFGame game = new SGFGame(gameTree);
            gv.setSGF(game);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
