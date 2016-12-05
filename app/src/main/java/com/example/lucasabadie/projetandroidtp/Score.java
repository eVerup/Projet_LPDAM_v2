package com.example.lucasabadie.projetandroidtp;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Score extends RealmObject {
    private Date date;
    private double score;

    public Score() {
    }

    public Score(Date date, double score) {
        this.date = date;
        this.score = score;
    }

    public Score(double score) {
        this.score = score;
        this.date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
