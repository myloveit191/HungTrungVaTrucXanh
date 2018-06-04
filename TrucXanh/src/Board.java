package vn.tdmu.nghia.trucxanh;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.content.ContentValues.TAG;

public class Board {
    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint;
    private Context context;
    private List<Line> lstLine;
    private int[][] boardArray;
    private int[][] danhdau;
    private int rowQty; // So luong hang
    private int colQty; // So luong cot
    private int bitmapWidth; // Chieu rong ban co
    private int bitmapHeight; // Chieu cao ban co
    private int cellWidth;
    private int cellHeight;
    private List<Bitmap> listCard;
    private int posColTouch;
    private int posRowTouch;
    private int state1;
    private int clickCard = 1;
    public static int luotchoi = 5;
    Bitmap cardDefault;
    public Board(Context context, int rowQty, int colQty, int bitmapHeight, int bitmapWidth) {
        this.context = context;
        this.rowQty = rowQty;
        this.colQty = colQty;
        this.bitmapHeight = bitmapHeight;
        this.bitmapWidth = bitmapWidth;
        cardDefault = BitmapFactory.decodeResource(context.getResources(),R.drawable.crown);
    }
    /*
        Ham Init():
            - Tinh chieu dai va chieu rong cua 1 o
            - Ve ban co bang cac duong thang
            - Tao bitmap
            - Dua bitmap vao canvas
            - Set mot so thuoc tinh cho paint
     */
    public void Init() {
        lstLine = new ArrayList<>();
        cellWidth = bitmapWidth / colQty; // Do rong cua mot o
        cellHeight = bitmapHeight / rowQty; // Chieu cao cua mot o
        // Lay duong
        for (int i = 0; i <= colQty; i++) {
            lstLine.add(new Line(i * cellWidth, 0, i * cellWidth, bitmapHeight));
        }
        for (int i = 0; i <= rowQty; i++) {
            lstLine.add(new Line(0, i * cellHeight, bitmapWidth, i * cellHeight));
        }
        // Tao bitmap
        bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_4444); // Tra ve mot mutable bitmap
        canvas = new Canvas(bitmap);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(2); // Do rong net ve
        boardArray = new int[rowQty][colQty];
        danhdau = new int[rowQty][colQty];
        cardDefault  = Bitmap.createScaledBitmap(cardDefault, cellWidth, cellHeight, false);
        listCard = new ArrayList<Bitmap>();
        Bitmap horse = BitmapFactory.decodeResource(context.getResources(), R.drawable.horse);
        listCard.add(Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(context.getResources(),R.drawable.horse),cellWidth, cellHeight, false)
        );
        listCard.add(Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(context.getResources(),R.drawable.cat),cellWidth, cellHeight, false)
        );
        listCard.add(Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(context.getResources(),R.drawable.dog),cellWidth, cellHeight, false)
        );
        listCard.add(Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(context.getResources(),R.drawable.bear),cellWidth, cellHeight, false)
        );
    }
    public void setGame(){
        Random r = new Random();

        for (int i = 0 ; i < colQty; i ++){
            for (int j = 0; j < rowQty; j++){
                int r1 = r.nextInt(3);
                Log.d(TAG, "drawBoard: " + r1);
                boardArray[i][j] = r1;
            }
        }
    }
    public Bitmap drawBoard() {
        canvas.drawARGB(0, 0, 0, 0);
        for (Line line : lstLine) {
            canvas.drawLine(line.getStartX(),line.getStartY(), line.getFinishX(),line.getFinishY(),paint);
        }
        for (int i = 0 ; i < colQty; i ++){
            for (int j = 0; j < rowQty; j++){
                canvas.drawBitmap(cardDefault,i* cellWidth, j*cellHeight,null);

            }
        }

        return bitmap;
    }
    public Bitmap drawBoard(int xTouch , int yTouch) {
        posColTouch = xTouch/cellWidth;
        posRowTouch = yTouch/cellHeight;
        canvas.drawARGB(0, 0, 0, 0);
        if (clickCard == 1){
            canvas.drawBitmap(listCard.get(boardArray[posColTouch][posRowTouch]),posColTouch* cellWidth, posRowTouch*cellHeight,null);
            state1 = boardArray[posColTouch][posRowTouch];
            Log.d(TAG, "clickCard == 1");
            danhdau[posColTouch][posRowTouch] = 1;
            clickCard = 2;
        }
        if (clickCard == 2){
            Log.d(TAG, "clickCard == 2");
            if (checkPostionClick()){ // Kiem tra da check chua
                if (state1 == boardArray[posColTouch][posRowTouch] ){
                    canvas.drawBitmap(listCard.get(boardArray[posColTouch][posRowTouch]),posColTouch* cellWidth, posRowTouch*cellHeight,null);
                    danhdau[posColTouch][posRowTouch] = 1;
                    clickCard = 1;
                    Log.d(TAG, "clickCard == Hinh giong");
                }else {
                    Toast.makeText(context,"Chon ko dung hinh", Toast.LENGTH_SHORT).show();
                    luotchoi --;
                    if (luotchoi < 0){
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Ban da thua!")
                                .setCancelable(false)
                                .setPositiveButton("Choi lai", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                }
            }
        }
        return bitmap;
    }
    public boolean checkPostionClick(){
        if (danhdau[posColTouch][posRowTouch] == 1){
            return false;
        }
        return true;
    }
}
