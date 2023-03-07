package com.ervas.seva;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import java.util.Random;

import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;

public class Orta extends AppCompatActivity {
    int b;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mReference;
    private DocumentReference mdoc;
    private Button buton;
    BitmapDrawable[] bitmapdizisi=new BitmapDrawable[44];
    private TextView textView3;
    private TextView timeText;
    private FirebaseFirestore mFirestore;

    String [] kartadidizi= new String[44];
    String [] kartevidizi= new String[44];
    String [] kartpuanidizi= new String[44];
    String [] kartresmidizi= new String[44];
    Bitmap[] base64format=new Bitmap[44];
    ImageView[] dizi2=new ImageView[44];
    MediaPlayer play,ply;

    int[] dizi=new int[44];

    int a;
    ImageButton imb00, imb01, imb02, imb03, imb04, imb05, imb06, imb07, imb08, imb09, imb10, imb11, imb12, imb13, imb14, imb15;
    ImageButton[] table = new ImageButton[16];
    Button butonyenile, butoncikis;
    TextView puan;
    int puan1;
    int hamlesayac;
    Bitmap[] imagenes=new Bitmap[8];
    Bitmap[] imagenes3=new Bitmap[44];
    ArrayList<Bitmap> imagenes1=new ArrayList<Bitmap>(44);

    int fondo;

    ArrayList<Integer> randomla;
    ImageButton ilk;
    int ilkHamle, ikinciHamle;
    boolean bloqueo = false;
    final Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orta);
        ply=MediaPlayer.create(Orta.this,R.raw.prologue);
        ply.start();
        total();

        timeText=(TextView) findViewById(R.id.timeText);
        butoncikis = findViewById(R.id.botonJuegoSalir);
        total();
        new CountDownTimer(45000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeText.setText("Süre:: "+millisUntilFinished/1000);

            }

            @Override
            public void onFinish() {
                play=MediaPlayer.create(Orta.this,R.raw.basarisiz);
                play.start();
                timeText.setText("süre bitti");
                butoncikis.callOnClick();


            }
        }.start();

    }


    public void calCliked(View view){
        ply.start();
    }
    public void durdurClickes(View view){
        ply.pause();
    }



    private void kartTanimla(){
        imb00 = findViewById(R.id.boton00);
        imb01 = findViewById(R.id.boton01);
        imb02 = findViewById(R.id.boton02);
        imb03 = findViewById(R.id.boton03);
        imb04 = findViewById(R.id.boton04);
        imb05 = findViewById(R.id.boton05);
        imb06 = findViewById(R.id.boton06);
        imb07 = findViewById(R.id.boton07);
        imb08 = findViewById(R.id.boton08);
        imb09 = findViewById(R.id.boton09);
        imb10 = findViewById(R.id.boton10);
        imb11 = findViewById(R.id.boton11);
        imb12 = findViewById(R.id.boton12);
        imb13 = findViewById(R.id.boton13);
        imb14 = findViewById(R.id.boton14);
        imb15 = findViewById(R.id.boton15);


        table[0] = imb00;
        table[1] = imb01;
        table[2] = imb02;
        table[3] = imb03;
        table[4] = imb04;
        table[5] = imb05;
        table[6] = imb06;
        table[7] = imb07;
        table[8] = imb08;
        table[9] = imb09;
        table[10] = imb10;
        table[11] = imb11;
        table[12] = imb12;
        table[13] = imb13;
        table[14] = imb14;
        table[15] = imb15;

    }

    private void butonTanimla(){
        butonyenile = findViewById(R.id.botonJuegoReiniciar);
        butoncikis = findViewById(R.id.botonJuegoSalir);
        butonyenile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                total();
            }
        });

        butoncikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void baslangic(){
        puan = findViewById(R.id.texto_puntuacion);
        puan1 = 0;
        hamlesayac = 0;
        puan.setText("Skor " + puan1);
    }

    private void resimTanımla(){
        //resimler random bir şelikde firebase firestoden geliyor.
        //resiler firestora string tipinde tek tanımlandı

        int i;
        ArrayList<Bitmap> authors = new ArrayList<Bitmap>();
        authors=anafonksiyon();
        imagenes3=authors.toArray(new Bitmap[44]);

        Random random = new Random();
        int randomNumber1= random.nextInt(44);
        int randomNumber2 = random.nextInt(44);
        int randomNumber3= random.nextInt(44);
        int randomNumber4= random.nextInt(44);
        int randomNumber5= random.nextInt(44);
        int randomNumber6 = random.nextInt(44);
        int randomNumber7= random.nextInt(44);
        int randomNumber8= random.nextInt(44);


        imagenes[0]=imagenes3[randomNumber1];
        imagenes[1]=imagenes3[randomNumber2];
        imagenes[2]=imagenes3[randomNumber3];
        imagenes[3]=imagenes3[randomNumber4];
        imagenes[4]=imagenes3[randomNumber5];
        imagenes[5]=imagenes3[randomNumber6];
        imagenes[6]=imagenes3[randomNumber7];
        imagenes[7]=imagenes3[randomNumber8];



        fondo = R.drawable.x;


    }

    private ArrayList<Integer> randomlaD(int longitud){
        //random olması için dizi tanımlandı
        ArrayList<Integer> result = new ArrayList<Integer>();
        for(int i=0; i<longitud*2; i++){
            result.add(i % longitud);
        }
        Collections.shuffle(result);
        //karıştırma yapıldı

        return result;
    }

    private void islemler(int i, final ImageButton imgb){
        if(ilk == null){//eğer kart seçilmemişse
            ilk = imgb;
            ilk.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ilk.setImageBitmap(imagenes[randomla.get(i)]);
            ilk.setEnabled(false);
            ilkHamle = randomla.get(i);
        } else {//kart seçilmiş ise uygulanacklar
            bloqueo = true;
            imgb.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imgb.setImageBitmap(imagenes[randomla.get(i)]);
            imgb.setEnabled(false);
            ikinciHamle = randomla.get(i);
            if(ilkHamle == ikinciHamle){//doğru resimler seçilmiş ise
                ilk = null;
                bloqueo = false;
                hamlesayac++;
                puan1=puan1+20;
                play=MediaPlayer.create(Orta.this,R.raw.happy);
                play.start();
                puan.setText("skor " + puan1);
                if(hamlesayac == imagenes.length){//tüm kartlar doğru eşleşmiş ise
                    play=MediaPlayer.create(Orta.this,R.raw.alkis);
                    play.start();
                    Toast toast = Toast.makeText(getApplicationContext(), "Tebrikler seviye tamamlandı", Toast.LENGTH_LONG);
                    toast.show();
                }
            } else {//yanlış resim seçilmiş ise
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ilk.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        ilk.setImageResource(fondo);
                        ilk.setEnabled(true);
                        imgb.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        imgb.setImageResource(fondo);
                        imgb.setEnabled(true);
                        bloqueo = false;
                        ilk = null;
                        puan1=puan1-12;
                        play= MediaPlayer.create(Orta.this,R.raw.basarisiz);
                        play.start();
                        puan.setText("skor: " + puan1);
                    }
                }, 1000);
            }
        }
    }

    private void total(){
        //kod ilk çalıştığında her zaman burası işleme girecek ve tüm çğırmalar burdan yapılacak
        kartTanimla();
        butonTanimla();
        baslangic();
        resimTanımla();
        randomla = randomlaD(imagenes.length);
        for(int i = 0; i< table.length; i++){
            table[i].setScaleType(ImageView.ScaleType.CENTER_CROP);
            table[i].setImageBitmap(imagenes[randomla.get(i)]);
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i< table.length; i++){
                    table[i].setScaleType(ImageView.ScaleType.CENTER_CROP);
                    table[i].setImageResource(fondo);
                }
            }
        }, 500);
        for(int i = 0; i< table.length; i++) {
            final int j = i;
            table[i].setEnabled(true);
            table[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!bloqueo)
                        islemler(j, table[j]);
                }
            });
        }

    }
    private ArrayList<Bitmap> anafonksiyon()
    //firebase den gelen veriler bu kod yardımı ile alındı
    {    int k;

        FirebaseFirestore.getInstance().collection("kartBilgileri").addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (e != null) {

            }
            if (queryDocumentSnapshots != null) {
                for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                    Map<String, Object> data = snapshot.getData();
                    // puan = (String)(data.get("puan"));
                    String isim = (String) data.get("ad");

                    String resim = (String) data.get("resim");

                    byte[] imageBytes = Base64.decode(resim, Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    BitmapDrawable drawable = new BitmapDrawable(bitmap);
                    imagenes1.add(bitmap);
                    String ev = (String) data.get("ev");

                }
            }
        });


        return imagenes1;
    }
}












