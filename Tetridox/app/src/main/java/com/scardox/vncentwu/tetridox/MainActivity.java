package com.scardox.vncentwu.tetridox;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    PaintActivity game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton left = (ImageButton) findViewById(R.id.leftButton);
        ImageButton right = (ImageButton) findViewById(R.id.rightButton);
        ImageButton cw = (ImageButton) findViewById(R.id.cwButton);
        ImageButton ccw = (ImageButton) findViewById(R.id.ccwButton);
        ImageButton down = (ImageButton) findViewById(R.id.downButton);
        Button reset = (Button) findViewById(R.id.resetButton);
        game = (PaintActivity) findViewById(R.id.gamePanel);
        game.nextPiecePanel = (NextPiece) findViewById(R.id.nextPiecePanel);
        game.levelView = (TextView) findViewById(R.id.level);
        game.rowView = (TextView) findViewById(R.id.rows);
        game.scoreView = (TextView) findViewById(R.id.score);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.reset();
            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(game.currentPiece!=null)
                    game.currentPiece.shiftLeft();
                game.invalidate();
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(game.currentPiece!=null)
                    game.currentPiece.shiftRight();
                game.invalidate();
            }
        });
        cw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(game.currentPiece!=null)
                    game.currentPiece.rotateClockwise();
                game.invalidate();
            }
        });
        ccw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(game.currentPiece!=null)
                    game.currentPiece.rotateCounterClockwise();
                game.invalidate();
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(game.currentPiece!=null)
                    game.currentPiece.zoomDown();
                //game.invalidate();
                game.touchDown();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.action_name){
            game.levelUp();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }


}
