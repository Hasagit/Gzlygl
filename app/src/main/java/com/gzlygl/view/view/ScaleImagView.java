package com.gzlygl.view.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by DengJf on 2017/12/14.
 */

public class ScaleImagView extends android.support.v7.widget.AppCompatImageView {
    private int beginX,beginY;

    private int moveType;

    private float beforeScale;

    private int beginW=500,beginH=500;

    public ScaleImagView(Context context) {
        super(context);
        DisplayMetrics dm =getResources().getDisplayMetrics();
        beginW=dm.widthPixels;
    }

    public ScaleImagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        DisplayMetrics dm =getResources().getDisplayMetrics();
        beginW=dm.widthPixels;

    }

    public ScaleImagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        DisplayMetrics dm =getResources().getDisplayMetrics();
        beginW=dm.widthPixels;
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x= (int) event.getX();//获取触摸位置
        int y= (int) event.getY();
        Log.e("w&h",beginW+"   "+beginH);
        Log.e("get_w&h",getHeight()+"   "+getWidth());
        switch (event.getAction()&event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                //单点触摸
                //Log.e("place","down_1");
                beginX=x;
                beginY=y;
                moveType=1;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                //Log.e("place","down_2");
                //多点触摸
                moveType=2;
                beforeScale=getScaleLength(event);
                break;
            case MotionEvent.ACTION_MOVE:
                if (moveType==1){
                    //计算拖动距离
                    int moveX=x-beginX;
                    int moveY=y-beginY;
                    layout(getLeft()+moveX,getTop()+moveY,getRight()+moveX,getBottom()+moveY);
                }else {
                    float afterScale=getScaleLength(event);
                    int scale= (int) (afterScale-beforeScale)/4;
                    int h=getHeight();
                    int w=getWidth();
                    if (scale<0&scale>-150&w>beginW){
                        layout(getLeft()-scale,getTop()-scale,getRight()+scale,getBottom()+scale);
                    }
                    if (scale>0){
                        layout(getLeft()-scale,getTop()-scale,getRight()+scale,getBottom()+scale);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                moveType=1;
                break;
        }
        return true;
    }

    private float getScaleLength(MotionEvent event){
        try {
            //两个触摸点的坐标
            float x1 = event.getX();
            float y1 = event.getY();
            float x2 = event.getX(1);
            float y2 = event.getY(1);
            //利用勾股定理求得斜角边
            return (float) Math.sqrt(Math.pow((x1-x2), 2)+ Math.pow((y1-y2), 2));
        }catch (IllegalArgumentException e){
            return beforeScale;
        }
    }
}
