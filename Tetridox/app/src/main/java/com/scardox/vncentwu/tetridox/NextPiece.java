package com.scardox.vncentwu.tetridox;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class NextPiece extends View {

    Paint paint;
    TGrid grid;
    Tetromino nextPiece;

    public NextPiece(Context context) {
        super(context);
        init();
    }

    public NextPiece(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NextPiece(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        grid = new TGrid(5, 3);
        newNextPiece();
    }

    public Tetromino newNextPiece()
        {
        if(nextPiece!=null)
            nextPiece.removeFromGrid();
        Tetromino returnPiece = nextPiece;
        nextPiece = TetrominoBuilder.Random();
        nextPiece.insertIntoGrid(1, 0, grid);
        this.invalidate();
        return returnPiece;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        float blockWidth = width/5.0f;
        float blockHeight = height/3.0f;
        Log.d("hello", "DRAWINGGGGGGG");

        //this.paint.setColor(Color.WHITE);
        this.paint.setStyle(Paint.Style.FILL);
        //canvas.drawPaint(this.paint);
        canvas.drawColor(0x00000000);

        this.paint.setColor(Color.BLACK);
        //canvas.drawRect(0, 0, 100, 100, this.paint);


        for(int i = 0; i < 5; i++)
            for(int j = 0; j<3; j++){
                TCell cell = grid.getCellAt(i, j);
                if(cell!=null){
                    this.paint.setColor(cell.getColor());
                    canvas.drawRect(i*blockWidth, j*blockHeight, i*blockWidth + blockWidth, j*blockHeight + blockHeight, this.paint);

                    this.paint.setColor(Color.BLACK);
                    this.paint.setStrokeWidth(4);
                    canvas.drawLine(i*blockWidth, j*blockHeight, i*blockWidth, j*blockHeight + blockHeight, this.paint);
                    canvas.drawLine(i*blockWidth, j*blockHeight, i*blockWidth + blockWidth, j*blockHeight, this.paint);
                    canvas.drawLine(i*blockWidth + blockWidth, j*blockHeight, i*blockWidth + blockWidth, j*blockHeight + blockHeight, this.paint);
                    canvas.drawLine(i*blockWidth, j*blockHeight + blockHeight, i*blockWidth + blockWidth, j*blockHeight + blockHeight, this.paint);

                    Log.d("hello", "drawing REDUX " + i*blockWidth + "and" + j*blockHeight);
                }
            }



    }

}
