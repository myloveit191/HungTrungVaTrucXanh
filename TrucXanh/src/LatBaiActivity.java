package vn.tdmu.nghia.trucxanh;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class LatBaiActivity extends AppCompatActivity {
    TextView txtScore;
    ImageView imgView;
    int rowQty = 4;
    int colQty = 4;
    int bitmapHeight = 400;
    int bitmapWidth = 400;
    Board board;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lat_bai);
        txtScore = findViewById(R.id.txtScore);
        imgView = findViewById(R.id.imageView);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        bitmapWidth = displayMetrics.widthPixels;
        bitmapHeight = bitmapWidth;
        // Khoi tao ban choi
        board = new Board(this,rowQty,colQty,bitmapWidth,bitmapHeight);
        board.Init();
        board.setGame();
        Board.luotchoi = 5;
        imgView.setImageBitmap(board.drawBoard());
        txtScore.setText(Board.luotchoi+"");
        imgView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction()== MotionEvent.ACTION_UP){
                    int xTouch = (int)event.getX();
                    int yTouch = (int)event.getY();
                    imgView.setImageBitmap(board.drawBoard(xTouch,yTouch));
                    txtScore.setText(Board.luotchoi+"");
                    return true;
                }
                return true;
            }
        });
    }
//    public void getDialog(){
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("Ban da thua!")
//                .setCancelable(false)
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        Intent intent = new Intent(LatBaiActivity.this, MainActivity.class);
//                        startActivity(intent);
//                    }
//                });
//        AlertDialog alert = builder.create();
//        alert.show();
//    }
}
