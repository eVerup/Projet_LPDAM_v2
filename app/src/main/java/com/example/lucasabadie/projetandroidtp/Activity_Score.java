package com.example.lucasabadie.projetandroidtp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class Activity_Score extends AppCompatActivity {
    ArrayList<String> scores = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RealmConfiguration config2 = new RealmConfiguration.Builder(this)
                .name("td4")
                .schemaVersion(3)
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm realm = Realm.getInstance(config2);

        RealmQuery<Score> query = realm.where(Score.class);

        RealmResults<Score> result1 = query.findAll();
        result1.sort("score");

        for( int i = 0 ; i < result1.size() ; i++ ) {
            Log.d("test","score : "+result1.get(i).getScore());
            scores.add(result1.get(i).getDate()+"\n"+String.valueOf(result1.get(i).getScore())+" s");
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                scores );

        ((ListView)findViewById(R.id.list)).setAdapter(arrayAdapter);
    }
}
