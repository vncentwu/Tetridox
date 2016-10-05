package com.scardox.vncentwu.tetridox;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class PaintActivity extends View {

    Paint paint;
    Path path;
    TGrid grid;
    Tetromino currentPiece;
    NextPiece nextPiecePanel;

    TextView levelView;
    TextView rowView;
    TextView scoreView;
    static int level =1;
    static int rows;
    static int score;
    static int time = 1000;
    static boolean gameOver = false;

    public PaintActivity(Context context) {
        super(context);
        init();
    }

    public PaintActivity(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PaintActivity(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        grid = new TGrid(10, 23);
        //nextPiece = TetrominoBuilder.Random();
        //touchDown();
        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                while(true){
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    PaintActivity.this.post(new Runnable(){
                        @Override
                        public void run(){
                            if(currentPiece==null)
                                touchDown();
                            else
                            {
                                boolean result = currentPiece.shiftDown();
                                if(!result)
                                    touchDown();
                            }
                            PaintActivity.this.invalidate();
                        }
                    });
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();

    }

    public void levelUp()
    {
        if(gameOver)
            return;
        level++;
        time *= 0.8;
        updateViews();

    }

    public void updateViews()
    {
        if(levelView!=null)
            levelView.setText("" + level);
        if(rowView!=null)
            rowView.setText(""+rows);
        if(scoreView!=null)
            scoreView.setText(""+score);
    }

    public void reset()
    {
        level = 1;
        rows = 0;
        score = 0;
        grid.clear();
        time = 1000;
        gameOver = false;
        updateViews();
    }

    public void touchDown(){
        if(gameOver)
        {
            PaintActivity.this.invalidate();
            return;
        }
        currentPiece = nextPiecePanel.newNextPiece();
        //updateNextPanel();
        gameOver = !currentPiece.insertIntoGrid(4, 0, grid);
        if(gameOver)
        {
            Toast.makeText(getContext(), "GAME OVER",
                    Toast.LENGTH_LONG).show();
        }


        int fullRow = grid.getFirstFullRow();
        while(fullRow >= 0)
        {
            grid.deleteRow(fullRow);
            rows++;
            score += level;
            if(rows%5==0)
            {
                level++;
                time *= 0.8;
            }

            fullRow = grid.getFirstFullRow();
        }
        updateViews();
        PaintActivity.this.invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        float blockWidth = width/10.0f;
        float blockHeight = height/20.0f;
        //Log.d("hello", "parameters " + blockWidth + "and" + blockHeight);

        //this.paint.setColor(Color.WHITE);
        this.paint.setStyle(Paint.Style.FILL);
        //canvas.drawPaint(this.paint);
        canvas.drawColor(0x9F000000);


       for(int i = 0; i < 10; i++)
           for(int j2 = 3; j2<23; j2++){
               TCell cell = grid.getCellAt(i, j2);
               int j = j2-3;
               if(cell!=null){
                   this.paint.setColor(cell.getColor());
                   canvas.drawRect(i*blockWidth, j*blockHeight, i*blockWidth + blockWidth, j*blockHeight + blockHeight, this.paint);

                   this.paint.setColor(Color.BLACK);
                   this.paint.setStrokeWidth(4);
                   canvas.drawLine(i*blockWidth, j*blockHeight, i*blockWidth, j*blockHeight + blockHeight, this.paint);
                   canvas.drawLine(i*blockWidth, j*blockHeight, i*blockWidth + blockWidth, j*blockHeight, this.paint);
                   canvas.drawLine(i*blockWidth + blockWidth, j*blockHeight, i*blockWidth + blockWidth, j*blockHeight + blockHeight, this.paint);
                   canvas.drawLine(i*blockWidth, j*blockHeight + blockHeight, i*blockWidth + blockWidth, j*blockHeight + blockHeight, this.paint);

                   //Log.d("hello", "drawing a piece at " + i*blockWidth + "and" + j*blockHeight);
               }
           }
        //this.paint.setColor(Color.BLACK);
        //canvas.drawRect(100, 100, 100, 100, this.paint);
    }

}
