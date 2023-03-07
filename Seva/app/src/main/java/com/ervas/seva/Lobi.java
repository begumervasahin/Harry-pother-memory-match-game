package com.ervas.seva;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Lobi extends AppCompatActivity {
    Button play;
    MediaPlayer ply;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobi);
        ply= MediaPlayer.create(Lobi.this,R.raw.prologue);
        ply.start();



    }


    public void calCliked(View view){
        ply.start();
    }
    public void durdurClickes(View view){
        ply.pause();
    }




    public void  beginnerClicked(View view){
        Intent intent = new Intent(Lobi.this,Kolay.class);
        ply.pause();
        startActivity(intent);


    }

    public void  hardClicked(View view){
        Intent intent = new Intent(Lobi.this,Zor.class);
        ply.pause();
        startActivity(intent);



    }


    public void intermediateClicked(View view) {

        Intent intent = new Intent(Lobi.this,Orta.class);
        ply.pause();
        startActivity(intent);

    }

    public void beginner2Clicked(View view) {
        Intent intent = new Intent(Lobi.this,ciftKolay.class);
        ply.pause();
        startActivity(intent);
    }

    public void intermediate2Clicked(View view) {
        Intent intent = new Intent(Lobi.this,ciftOrta.class);
        ply.pause();
        startActivity(intent);
    }

    public void hard2Clicked(View view) {
        Intent intent = new Intent(Lobi.this,ciftZor.class);
        ply.pause();
        startActivity(intent);
    }
}