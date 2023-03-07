package com.ervas.seva;
import android.annotation.SuppressLint;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.Collections;


public class ciftKolay extends AppCompatActivity {
    //emülatör hatası alınca önce emülatörün interneti varmı kontrol et yada yeni emülatör ekle
    //firebase bağlantısı ekleri
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mReference;
    private DocumentReference mdoc;
    private Button buton;
    BitmapDrawable[] bitmapdizisi=new BitmapDrawable[44];
    private TextView textView3;
    private TextView timeText;
    private FirebaseFirestore mFirestore;

    int a;
    int b;
    int[] dizi=new int[44];
    String [] kartad= new String[44];
    String [] kartev= new String[44];
    String [] kartpuan= new String[44];
    String [] kartresm= new String[44];
    Bitmap[] base64formati=new Bitmap[44];
    ImageView[] dizi1=new ImageView[44];

    ArrayList<Integer> randomla;
    ImageButton firstResim;
    int ilkHamle, ikinihamle;
    boolean bloqueo = false;
    final Handler handler = new Handler();
    MediaPlayer play,ply;//müzik
    int arkafon;//buluttan değil içerden geliyor

    ImageButton imb00, imb01, imb02, imb03;
    ImageButton[] table = new ImageButton[4];
    Button yenileButon, cikisButon;
    TextView puan1;
    TextView puan2;
    int sayi1,sayi2;
    int skorsay,skorsay2;
    Bitmap[] imagenes=new Bitmap[2];
    Bitmap[] imagenes3=new Bitmap[44];
    ArrayList<Bitmap> imagenes1=new ArrayList<Bitmap>(44);



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cift_kolay);
        //müzik çalmaya başlıyor
        ply=MediaPlayer.create(ciftKolay.this,R.raw.prologue);
        ply.start();
        total();
//zamanlayıcı başlatıldı süre 60 olunca oyun bizi lobi ekranına atıcak
        //istersek oyun kazanınca da bizi atabilir ama gerek görmedim
        timeText=(TextView) findViewById(R.id.timeText);
        cikisButon = findViewById(R.id.botonJuegoSalir);

        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeText.setText("Süre:: "+millisUntilFinished/1000);

            }

            @Override
            public void onFinish() {
                //oyundan atarken  gelecek ses
                ply=MediaPlayer.create(ciftKolay.this,R.raw.basarisiz);
                ply.start();
                timeText.setText("süre bitti");
                cikisButon.callOnClick();


            }
        }.start();

    }


    private void butonTanimla(){
        //ekranda olan yenile ve çıkış butonları tanımlanıp görevleri atandı
        yenileButon = findViewById(R.id.botonJuegoReiniciar);
        cikisButon= findViewById(R.id.botonJuegoSalir);
        yenileButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                total();
            }
        });

        cikisButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void baslangic(){
        //oyuna başlamadan önce olan oyun puanları 0 atandı. herkey aynı puanda
        puan1 = findViewById(R.id.texto_puntuacion);
        puan2 = findViewById(R.id.texto_puntuacion2);
        sayi1 = 0;
        sayi2 = 0;
        skorsay= 0;
        puan1.setText("oyuncu1:: " + sayi1 );
        puan2.setText("oyuncu2:: " + sayi2);
    }


    private void randomREsim(){
        //resimler random bir şelikde firebase firestoden geliyor.
        //resiler firestora string tipinde tek tanımlandı
        int i;
        ArrayList<Bitmap> authors = new ArrayList<Bitmap>();
        authors=farebase();
        imagenes3=authors.toArray(new Bitmap[44]);
        Random random = new Random();
        int randomNumber1= random.nextInt(44);
        int randomNumber2 = random.nextInt(44);
        imagenes[0]=imagenes3[randomNumber1];
        imagenes[1]=imagenes3[randomNumber2];
        arkafon= R.drawable.x;


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



    public void calCliked(View view){
        //müziği yeniden aç
        ply.start();
    }
    public void durdurClickes(View view){
        //müziği durdur
        ply.pause();
    }



    private void kardTanimla(){
        //kartlar tek tek tanımlandı
        imb00 = findViewById(R.id.boton00);
        imb01 = findViewById(R.id.boton01);
        imb02 = findViewById(R.id.boton02);
        imb03 = findViewById(R.id.boton03);


        table[0] = imb00;
        table[1] = imb01;
        table[2] = imb02;
        table[3] = imb03;


    }

    private void islemler(int i, final ImageButton imgb){
        if(firstResim == null){//eğer resim seçilmediyse
            firstResim = imgb;
            firstResim.setScaleType(ImageView.ScaleType.CENTER_CROP);
            firstResim.setImageBitmap(imagenes[randomla.get(i)]);
            firstResim.setEnabled(false);
            ilkHamle = randomla.get(i);
        }
        else {//eğer resim seçildi ise
            bloqueo = true;
            imgb.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imgb.setImageBitmap(imagenes[randomla.get(i)]);
            imgb.setEnabled(false);
            ikinihamle = randomla.get(i);
            if( ilkHamle == ikinihamle){//seçilen iki resim  eşit ise puan kazanırsın
                firstResim = null;
                bloqueo = false;
                skorsay++;
                sayi1 =sayi1+40;
                play=MediaPlayer.create(ciftKolay.this,R.raw.happy);
                play.start();
                puan1.setText("oyuncu1:: " + sayi1 );
                puan2.setText("oyuncu2:: " + sayi2);
                if(skorsay == imagenes.length){
                    play=MediaPlayer.create(ciftKolay.this,R.raw.alkis);
                    play.start();
                    Toast toast = Toast.makeText(getApplicationContext(), "seviye tamamlandı!", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
            else {//resimler eşit değil ise sıra ikinci oyuncuya geçer
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        firstResim.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        firstResim.setImageResource(arkafon);
                        firstResim.setEnabled(true);
                        imgb.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        imgb.setImageResource(arkafon);
                        imgb.setEnabled(true);
                        bloqueo = false;
                        firstResim = null;
                        play= MediaPlayer.create(ciftKolay.this,R.raw.basarisiz);
                        play.start();
                        Toast toast = Toast.makeText(getApplicationContext(), "sıra diğer oyuncuda", Toast.LENGTH_LONG);
                        toast.show();
                        islemler2(i,imgb);
                    }

                }, 1000);
            }

        }
    }
    private void islemler2(int i, final ImageButton imgb){
        if(firstResim == null){//eğer resim seçilmediyse
            firstResim= imgb;
            firstResim.setScaleType(ImageView.ScaleType.CENTER_CROP);
            firstResim.setImageBitmap(imagenes[randomla.get(i)]);
            firstResim.setEnabled(false);
            ilkHamle= randomla.get(i);
        } else {//resim seçildiyse
            bloqueo = true;
            imgb.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imgb.setImageBitmap(imagenes[randomla.get(i)]);
            imgb.setEnabled(false);
            ikinihamle= randomla.get(i);
            if( ilkHamle ==ikinihamle){//seçilen kartların aynı olması durumunda yapılacaklar
                firstResim= null;
                bloqueo = false;
                skorsay2++;
                sayi2++;
                puan1.setText("oyuncu1:: " + sayi1 );
                puan2.setText("oyuncu2:: " + sayi2);
                if(skorsay == imagenes.length){//oyun biterse
                    Toast toast = Toast.makeText(getApplicationContext(), "eviye tamamlandı!!", Toast.LENGTH_LONG);
                    toast.show();
                }
            } else {//seçilen kartların farklı olması durumunda yapılacaklar
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        firstResim.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        firstResim.setImageResource(arkafon);
                        firstResim.setEnabled(true);
                        imgb.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        imgb.setImageResource(arkafon);
                        imgb.setEnabled(true);
                        bloqueo = false;
                        firstResim = null;
                        Toast toast = Toast.makeText(getApplicationContext(), "sıra diğer oyuncuda", Toast.LENGTH_LONG);
                        toast.show();
                        islemler(i,imgb);


                    }
                }, 1000);


            }

        }
    }


    private ArrayList<Bitmap> farebase()
    {   //resimler farebase den base 64 tipinde alındı
        int k;
        FirebaseFirestore.getInstance().collection("kartBilgileri").addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (e != null) {

            }
            if (queryDocumentSnapshots != null) {
                for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                    Map<String, Object> data = snapshot.getData();
//datada kayıtlı olan isimler aynı olmalı yoksa resimler gelmez
                    String isim = (String) data.get("ad");
                    String ev = (String) data.get("ev");
                    String resim = (String) data.get("resim");
//base 64 den resim foramatına dönüştürüldü
                    byte[] imageBytes = Base64.decode(resim, Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    BitmapDrawable drawable = new BitmapDrawable(bitmap);
                    imagenes1.add(bitmap);
//çevrilen fotoğreak diziye gönderildi


                }
            }
        });


        return imagenes1;
    }
    private void total(){
        //random atanan kartları resim kutularıyla eşleltir ve ikili halde ekranda göstermek için hazırlandı

        kardTanimla();
        butonTanimla();
        baslangic();
        randomREsim();
        randomla = randomlaD(imagenes.length);
        for(int i=0; i<table.length; i++){
            table[i].setScaleType(ImageView.ScaleType.CENTER_CROP);
            table [i].setImageBitmap(imagenes[randomla.get(i)]);
            //tablero[i].setImageResource(fondo);
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<table.length; i++){
                    table[i].setScaleType(ImageView.ScaleType.CENTER_CROP);
                    //tablero[i].setImageResource(imagenes[arrayDesordenado.get(i)]);
                    table[i].setImageResource(arkafon);
                }
            }
        }, 500);
        for(int i=0; i<table.length; i++) {
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

}
