package vn.tdmu.nghia.collecteggs;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import static android.content.ContentValues.TAG;


public class Egg {
    private Bitmap image;
    private static final float VELOCITY = 0.2f;
    private int startX;
    private int startY;
    private int stopX;
    public static int stopY;
    private GameSurface gameSurface;
    private long lastDrawNanoTime =-1;
    public Egg(GameSurface gameSurface, Bitmap image, int x, int y){
        this.gameSurface= gameSurface;
        this.image = image;
        this.startX = x;
        this.startY = y;
    }
    public void update(){
        long now = System.nanoTime();
        if(lastDrawNanoTime==-1) {
            lastDrawNanoTime= now;
        }
        int deltaTime = (int) ((now - lastDrawNanoTime) / 1000000);
        float distance = VELOCITY * deltaTime;
        this.startY = startY + (int) distance;
        if (startY>stopY){
            startY = -20;
        }
    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(image, getStartX(), getStartY(), null);
        this.lastDrawNanoTime= System.nanoTime();
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getStopX() {
        return stopX;
    }

    public void setStopX(int stopX) {
        this.stopX = stopX;
    }
    public Bitmap getImage() {
        return image;
    }
}
