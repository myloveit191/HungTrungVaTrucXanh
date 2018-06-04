package vn.tdmu.nghia.collecteggs;

import android.graphics.Canvas;
import android.view.SurfaceHolder;


public class GameThread extends Thread {
    private boolean running;
    private GameSurface gameSurface;
    private SurfaceHolder surfaceHolder;
    private int count = 0;
    public GameThread(GameSurface gameSurface, SurfaceHolder surfaceHolder)  {
        this.gameSurface= gameSurface;
        this.surfaceHolder= surfaceHolder;
    }
    @Override
    public void run(){
        long startTime = System.nanoTime();

        while (running){
            Canvas canvas= null;
            try{
                // Lấy ra đối tượng Canvas và khóa nó lại.
                canvas = this.surfaceHolder.lockCanvas();


                // Đồng bộ hóa.
                synchronized (canvas)  {
                    this.gameSurface.update();
                    this.gameSurface.draw(canvas);
                }
            }catch (Exception e){

            }finally {
                if(canvas!= null)  {
                    // Mở khóa cho Canvas.
                    this.surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            long now = System.nanoTime() ;
            long waitTime = (now - startTime)/1000000;
            if(waitTime < 10)  {
                waitTime= 10; // Millisecond.
            }
            if (count  > 120) {
                this.gameSurface.createEgg();
                count = 0;
            }
            try {
                // Ngừng chương trình một chút.
                this.sleep(waitTime);
            } catch(InterruptedException e)  {

            }
            startTime = System.nanoTime();
            count++;
            if (GameSurface.score < 0){
               running =false;
            }
        }
        if (!running){
            try {
                this.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void setRunning(boolean running)  {
        this.running= running;
    }
}
