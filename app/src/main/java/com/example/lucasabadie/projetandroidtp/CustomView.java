package com.example.lucasabadie.projetandroidtp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Lucas Abadie on 31/10/2016.
 */

public class CustomView extends View implements View.OnTouchListener {

    //region Variables / Getter / Setter

        //region Variables

            private static final String TAG = "CustomView";
            private Activity activity;

            private ArrayList <DrawingObject> objectTab;
            private IntruderObject intruder;
            private MediaPlayer mMP;

            private Bitmap mObject;
            private Bitmap tempObject;
            private int mNumObject;
            private int tempNumObject;
            private String msColor;
            private String tempColor;
            private int mSpeed;
            private int tempSpeed;

            private int xMax;
            private int xTouch;
            private int yMax;
            private int yTouch;

            private Random rdm;
            private boolean bIsReload = false;

        //endregion

        //region Getter / Setter
            public MediaPlayer getMediaPlayer() { return mMP; }

            public void setObject(Bitmap mObject) { this.mObject = mObject; }
            public void setNumObject(int mNumObject) { this.mNumObject = mNumObject; }
            public void setsColor(String msColor) { this.msColor = msColor; }
            public void setSpeed(int mSpeed) { this.mSpeed = mSpeed; }
            public void setActivity(Activity activity) { this.activity = activity; }
            public void setMediaPlayer(MediaPlayer mMP) { this.mMP = mMP; }
        //endregion

    //endregion

    //region Constructor

        public CustomView (Context context) {
            super(context);
            init();
        }


        public CustomView (Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public void init () {
            rdm = new Random();
            objectTab = new ArrayList<>();

            //Initialisation of temporary variables (or save variables) for next tests
            tempObject = mObject;
            tempNumObject = mNumObject;
            tempColor = msColor;
            tempSpeed = mSpeed;

            removeCallbacks(animator);
            post(animator);
        }

    //endregion

    //region Methods

        /** Method for retrieving data on the size of the view **/
        @Override
        protected void onLayout(boolean changed, int left, int top, int
                right, int bottom) {
            super.onLayout(changed, left, top, right, bottom);
            xMax = this.getWidth();
            yMax = this.getHeight();
        }

        /** Method for add data in the ArrayList<> **/
        private DrawingObject addObject() {
            DrawingObject drawingObject;

            drawingObject = new DrawingObject(mObject, xMax, yMax, rdm.nextInt(10 - 5)+1, rdm.nextInt(15 - 10 + 1 )+1, mSpeed, msColor);

            return drawingObject;
        }

        private Runnable animator = new Runnable() {
            @Override
            public void run() {
                invalidate();
                postDelayed(this, 15);
            }
        };

        @Override
        protected void onDraw(Canvas canvas) {

            // Test to clear and recreate the ArrayList items when changing settings
            if ( tempObject != mObject || tempNumObject != mNumObject || tempColor != msColor || tempSpeed != mSpeed || bIsReload) {

                bIsReload = false;

                tempObject = mObject;
                tempNumObject = mNumObject;
                tempColor = msColor;
                tempSpeed = mSpeed;

                objectTab.clear();

                for (int i = 0; i < mNumObject-1; i++) {
                    objectTab.add(this.addObject());
                }

                intruder = new IntruderObject(mObject, xMax, yMax, rdm.nextInt(10 - 5)+1, rdm.nextInt(15 - 10 + 1 )+1, mSpeed, msColor);
            }

            // Draw item by item contained in the ArrayList
            for (int i = 0; i < mNumObject-1; i++) {
                objectTab.get(i).draw(canvas);
            }

            // Draw intruder
            intruder.draw(canvas);

            // Move item by item contained in the ArrayList
            for (int i = 0; i < mNumObject-1; i++) {
                objectTab.get(i).update();
            }

            //Move intruder
            intruder.update();

        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            // Test to see if the screen is pressed
            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){

                // Recovery of the x and y position of the pressed area
                xTouch = (int) motionEvent.getX()+30;
                yTouch = (int) motionEvent.getY()+30;

                // Test to see if the x and y position of the pressed area are contained in the intruder zone
                if( ((xTouch < (intruder.getX() + intruder.getObject().getWidth()) + 30) && (xTouch > (intruder.getX() + intruder.getObject().getWidth()) - 30)) &&
                        ((yTouch < (intruder.getY() + intruder.getObject().getHeight()) + 30) && (yTouch > (intruder.getY() + intruder.getObject().getHeight()) - 30))) {
                    Log.d(TAG,"onTouch : You win.");

                    mMP.seekTo(0);
                    mMP.start();

                    final Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(2000);

                    // Use the Builder class for convenient dialog construction
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setCancelable(false);
                    builder.setMessage("You win ! Do you want play again ?")
                            .setPositiveButton("Yes, Play again!", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    mMP.pause();
                                    v.cancel();
                                    bIsReload = true;

                                    SharedPreferences pref = getContext().getSharedPreferences("td4", Context.MODE_PRIVATE);

                                    SharedPreferences.Editor editor = pref.edit();

                                    editor.putLong("timestart", new Date().getTime());

                                    editor.commit();
                                }
                            })
                            .setNegativeButton("No, I wanna leave", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    mMP.stop();
                                    activity.finish();
                                    v.cancel();
                                    System.exit(0);
                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.create();

                    builder.show();

                    // Fin

                    SharedPreferences pref = getContext().getSharedPreferences("td4", Context.MODE_PRIVATE);

                    long timestart = pref.getLong("timestart",0);
                    long timeend = new Date().getTime();

                    Log.d("test","start : "+timestart);
                    Log.d("test","end : "+timeend);

                    double tempsdiff = Double.valueOf((timeend - timestart) / 1000.0);

                    Log.d("test","temps diff : "+tempsdiff);

                    RealmConfiguration config2 = new RealmConfiguration.Builder(getContext())
                            .name("td4")
                            .schemaVersion(3)
                            .deleteRealmIfMigrationNeeded()
                            .build();


                    Realm realm = Realm.getInstance(config2);

                    realm.beginTransaction();

                    Score score = realm.createObject(Score.class);
                    score.setDate(new Date());
                    score.setScore(tempsdiff);

                    realm.commitTransaction();
                }

                return true;
            }

            return false;
        }

    //endregion

}
