package com.example.lucasabadie.projetandroidtp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

import java.util.Random;

/**
 * Created by Lucas Abadie on 04/11/2016.
 */

public class IntruderObject {

    //region Variables / Getter / Setter

        //region Variables

            private Bitmap mObject;
            private String msColor;
            private String tempColor;
            private int mSpeed;

            private float x;
            private int mxMax = 0;
            private int mxSpeed;
            private float y;
            private int myMax = 0;
            private int mySpeed;

            private Random rdm;
            private Paint paint;

        //endregion

        //region Getter / Setter

            public float getX() { return x; }
            public float getY() { return y; }
            public Bitmap getObject() { return mObject; }

        //endregion

    //endregion

    //region Constructor

        public IntruderObject(Bitmap object, int xMax, int yMax, int xSpeed, int ySpeed, int speed, String sColor) {
            this.mObject = object;
            this.mxMax = xMax;
            this.myMax = yMax;
            this.mxSpeed = xSpeed;
            this.mySpeed = ySpeed;
            this.mSpeed = speed;
            this.msColor = sColor;

            init();
        }

        private void init() {
            rdm = new Random();
            paint = new Paint();

            // Test to set the color of intruder based on the selected color
            switch (msColor) {
                case "#000000": {
                    tempColor = "#333333";
                    break;
                }
                case "#1e76ea": {
                    tempColor = "#115bbb";
                    break;
                }
                case "#38cd0b": {
                    tempColor = "#289108";
                    break;
                }
                case "#ff0004": {
                    tempColor = "#ff3336";
                    break;
                }
            }

            // Set the ColorFilter for the bitmap (replace pixel color)
            paint.setColorFilter(new PorterDuffColorFilter(Color.parseColor(tempColor), PorterDuff.Mode.SRC_ATOP));

            // Set random x and y position
            x = rdm.nextInt((mxMax - mObject.getWidth()) - 0)+1;
            y = rdm.nextInt((myMax - mObject.getWidth()) - 0)+1;
        }

    //endregion

    //region Methods

        /** Method for update the x and y position of intruder (move action) **/
        public void update() {

            if (x < 0 && y <0) {
                x = mObject.getWidth()/2;
                y = mObject.getHeight()/2;
            } else {
                x +=  mxSpeed + (mxSpeed * (mSpeed / 100.0));
                y +=  mySpeed + (mySpeed * (mSpeed / 100.0));
                if ((x > mxMax - mObject.getWidth()) || (x < 0)) {
                    mxSpeed = mxSpeed*-1;
                }
                if ((y > myMax - mObject.getHeight()) || (y < 0)) {
                    mySpeed = mySpeed*-1;
                }
            }
        }

        /** Method for draw intruder **/
        public void draw (Canvas canvas) {
            if(mObject != null){
                canvas.drawBitmap(mObject, x, y, paint);
            }
        }

    //endregion

}
