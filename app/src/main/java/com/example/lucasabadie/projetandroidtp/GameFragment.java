package com.example.lucasabadie.projetandroidtp;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */

public class GameFragment extends Fragment {

    //region Variables

        private static final String TAG = "GameFragment";

        private View v;
        private Resources mResources;
        private MediaPlayer mp;
        private CustomView customView;

        private Bitmap object;
        private int numObject;
        private String sColor;
        private int speed;

    //endregion

    //region Constructor

        public GameFragment() {
            // Required empty public constructor
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            v = inflater.inflate(R.layout.fragment_game, container, false);

            mResources = getResources();
            mp = MediaPlayer.create(getContext(), R.raw.musicwin);
            object = BitmapFactory.decodeResource(mResources,R.drawable.ic_camaro);
            numObject = 1;
            sColor = "#000000";
            speed = 0;

            customView = (CustomView) v.findViewById(R.id.CustomView);
            customView.setActivity(getActivity());
            customView.setObject(object);
            customView.setNumObject(numObject);
            customView.setsColor(sColor);
            customView.setSpeed(speed);
            customView.setMediaPlayer(mp);

            customView.setOnTouchListener(customView);

            return v;
        }

    //endregion

    //region Methods

        /** Method for set the object (item) **/
        public void changerObject(Bitmap m) {
            object = m;
        }

        /** Method for set the number of items **/
        public void changerNombre( int n ) {
            numObject = n;
        }

        /** Method for set the color (in string because the treatment is done later) **/
        public void changerColor(String s) {
            sColor = s;
        }

        /** Method for set the speed **/
        public void changerSpeed(int i) {
            speed = i;
        }

        /** Method for start the game **/
        public void Game() {

            customView.setObject(object);
            customView.setNumObject(numObject);
            customView.setsColor(sColor);
            customView.setSpeed(speed);
        }

        @Override
        public void onPause() {
            super.onPause();

            customView.getMediaPlayer().pause();
        }

    //endregion

}
