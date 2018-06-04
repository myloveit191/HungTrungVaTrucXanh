package vn.tdmu.nghia.collecteggs;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Basket {
    private Bitmap image;
    private float velocity;
    private int startX;
    private int startY;
    private long lastDrawNanoTime =-1;
    private int stopX;
    private GameSurface gameSurface;

    public Basket(GameSurface gameSurface, Bitmap image, int x, int y){
        this.gameSurface= gameSurface;
        this.image = image;
        this.startX = x;
        this.startY = y;
    }
    public void update(){
        if (velocity != 0 ){
            long now = System.nanoTime();
            if(lastDrawNanoTime==-1) {
                lastDrawNanoTime= now;
            }
            // Đổi nano giây ra mili giây (1 nanosecond = 1000000 millisecond).
            int deltaTime = (int) ((now - lastDrawNanoTime) / 1000000);
            // Quãng đường mà nhân vật đi được (fixel).
            float distance = velocity * deltaTime;
            if ((stopX > startX  && startX + (int) distance > stopX )|| (stopX < startX  && startX - (int) distance < stopX ) ){
                velocity = 0;
            }else {
                // Tính toán vị trí mới của nhân vật.
                this.startX = (stopX > startX)?(startX + (int) distance) : (startX - (int) distance);
            }

        }
    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(image, getStartX(), getStartY(), null);
        this.lastDrawNanoTime= System.nanoTime();
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
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
