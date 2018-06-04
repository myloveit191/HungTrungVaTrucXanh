package vn.tdmu.nghia.collecteggs;


import android.util.Log;

import static android.content.ContentValues.TAG;

public class Impact {
    public static boolean BasketVsEgg(Basket basket, Egg egg) {
        int x2Basket = basket.getStartX() + basket.getImage().getWidth();
        int y2Basket = basket.getStartY() + basket.getImage().getHeight();

        int x2Egg = egg.getStartX() + egg.getImage().getWidth();
        int y2Egg = egg.getStartY() + egg.getImage().getHeight();
//        Log.d(TAG, "Basket: " + basket.getImage().getWidth() + "- " +  basket.getImage().getHeight());
//        Log.d(TAG, "Egg: " + egg.getImage().getWidth() + "- " +  egg.getImage().getHeight());
//        if (basket.getStartX() < x2Egg){
//            Log.d(TAG, "basket 1:  ");
//        }
//        if (x2Basket > egg.getStartX()){
//            Log.d(TAG, "basket 2: ");
//        }
//        if (basket.getStartY() > y2Egg){
//            Log.d(TAG, "basket 3: ");
//        }
//        if (y2Basket < egg.getStartY()){
//            Log.d(TAG, "basket 4: ");
//        }
//
        if ((basket.getStartX() < x2Egg) && (x2Basket > egg.getStartX())
                && (basket.getStartY() < y2Egg) && (y2Basket > egg.getStartY())) {
            return true;
        }
        return false;
    }
    public static boolean EggVsEarth(Egg egg, int y){
        int y2Egg = egg.getStartY() + egg.getImage().getHeight();
        if (y2Egg > y){
            return true;
        }
        return false;
    }
}
