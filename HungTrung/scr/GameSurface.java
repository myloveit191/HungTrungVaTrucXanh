package vn.tdmu.nghia.collecteggs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.content.ContentValues.TAG;


public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread gameThread;
    private Basket basket;
    private List<Egg> lsEgg;
    private Bitmap background;
    private DisplayMetrics metrics;
    private int stopX;
    private Random r;
    private Bitmap eggImg;
    private Paint paint;
    public static int score  = 0;
    public GameSurface(Context context, DisplayMetrics metrics){
        super(context);
        // Đảm bảo Game Surface có thể focus để điều khiển các sự kiện.
        this.setFocusable(true);
        this.metrics = metrics;
        // Sét đặt các sự kiện liên quan tới Game.
        this.getHolder().addCallback(this);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            stopX =  (int)event.getX();
            basket.setStopX(stopX);
            basket.setVelocity(0.5f);
            return true;
        }
        return false;
    }
    public void update()  {
        this.basket.update();
        int index = 0;
        for (Egg egg :lsEgg){

            egg.update();
            if (Impact.BasketVsEgg(basket,egg)){
                score ++;
                lsEgg.remove(index);
            }
            if (Impact.EggVsEarth(egg,metrics.heightPixels)){
                score --;
                lsEgg.remove(index);
            }
            index ++;
        }
    }
    public void createEgg(){
        if (lsEgg.size() >= 0 && lsEgg.size()<20)
            lsEgg.add(new Egg(this,eggImg,r.nextInt(metrics.widthPixels - 0) + 10, 20) );
    }
    @Override
    public void draw(Canvas canvas)  {
        super.draw(canvas);
        canvas.drawBitmap(background, 0, 0, null);
        for (Egg egg :lsEgg){
            egg.draw(canvas);
        }
        this.basket.draw(canvas);
        paint.setColor(Color.BLUE);
        paint.setTextSize(50);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText("SCORE :" + score, 50 , 50,paint  );
        if (score < 0){
            Log.d(TAG, "draw: " + score);
            paint.setTextSize(200);
            canvas.drawText("GAME OVER ", 400 , 400,paint  );
        }
    }
    // Thi hành phương thức của interface SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // Khoi tao View
        Bitmap basketImg = BitmapFactory.decodeResource(this.getResources(),R.drawable.basket);
        eggImg = BitmapFactory.decodeResource(this.getResources(),R.drawable.egg);
        eggImg = Bitmap.createScaledBitmap(eggImg,50,50,true);
        basketImg = Bitmap.createScaledBitmap(basketImg,150,150     ,true);
        this.background = BitmapFactory.decodeResource(this.getResources(), R.drawable.background);
        background = Bitmap.createScaledBitmap(background,metrics.widthPixels,metrics.heightPixels,true);
        paint = new Paint();
        Egg.stopY = metrics.heightPixels;
        r = new Random();
        int xRandom;
        lsEgg = new ArrayList<Egg>();
        lsEgg.add(new Egg(this,eggImg,r.nextInt(metrics.widthPixels - 0) + 10, 20) );
        this.basket = new Basket(this,basketImg,50, metrics.heightPixels -220);
        this.gameThread = new GameThread(this,holder);
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }
    // Thi hành phương thức của interface SurfaceHolder.Callback
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }
    // Thi hành phương thức của interface SurfaceHolder.Callback
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry= true;
        while(retry) {
            try {
                this.gameThread.setRunning(false);


                // Luồng cha, cần phải tạm dừng chờ GameThread kết thúc.
                this.gameThread.join();

            }catch(InterruptedException e)  {
                e.printStackTrace();
            }
            retry= true;
        }
    }
}
