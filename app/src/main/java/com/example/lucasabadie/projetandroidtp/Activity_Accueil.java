package com.example.lucasabadie.projetandroidtp;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Activity_Accueil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        findViewById(R.id.btnQuit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.btnRules).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Accueil.this);
                builder.setCancelable(false);
                builder.setTitle("Welcome in Search the intruder");
                builder.setMessage("Just tap on Let's play, configure the game as you want, put the item, the color, the number of items and the speed, and switch to the game screen ! Enjoy ! ;)")
                        .setPositiveButton("I understand !", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                // Create the AlertDialog object and return it
                builder.create();

                builder.show();
            }
        });

        findViewById(R.id.btnPlay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_Accueil.this, Activity_Game.class);
                startActivity(i);

                finish();
            }
        });
    }
}