 package com.ervas.seva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ervas.seva.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.channels.ClosedByInterruptException;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseAuth auth;
    private FirebaseUser mUser;
    private DatabaseReference mReferans;
    private HashMap<String,Object>mData;
    MediaPlayer ply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        ply=MediaPlayer.create(MainActivity.this,R.raw.prologue);
        ply.start();

        auth= FirebaseAuth.getInstance();
        mReferans= FirebaseDatabase.getInstance().getReference();
        mUser= auth.getCurrentUser();

        /*
        FirebaseUser firebaseUser = auth.getCurrentUser();

        if (firebaseUser != null) {

            Intent intent = new Intent(MainActivity.this, Lobi.class);
            startActivity(intent);
            finish();

        }
*/


    }

    public void calCliked(View view){
        ply.start();
    }
    public void durdurClickes(View view){
        ply.pause();
    }


    public void signInClicked (View view) {
        String email = binding.emailText.getText().toString();
        String password = binding.passwordText.getText().toString();
        String name= binding.nameText.getText().toString();



        if(email.equals("") || password.equals("") || name.equals("")){
            Toast.makeText(this, "Boş geçilemez", Toast.LENGTH_SHORT).show();
        }
        else {
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    mUser=auth.getCurrentUser();
                    mData=new HashMap<>();
                    mData.put("kullaniciAdi",name);
                    mData.put("Email",email);
                    mData.put("Sifre",password);
                    mData.put("kulaniciid",mUser.getUid());

                    mReferans.child("kullanicilar").child(mUser.getUid()).setValue(mData)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Intent intent = new Intent(MainActivity.this,Lobi.class);
                                    ply.pause();

                                    startActivity(intent);
                                    finish();
                                }
                            });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });
        }


    }

    public void signUpClicked(View view) {
        String email = binding.emailText.getText().toString();
        String password = binding.passwordText.getText().toString();


        if(email.equals("") ||password.equals("")){
            Toast.makeText(this, "Boş geçilemez", Toast.LENGTH_SHORT).show();
        }

        else {
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    mUser=auth.getCurrentUser();
                    assert mUser != null;
                    verileriGetir(mUser.getUid());
                    Intent intent = new Intent(MainActivity.this,Lobi.class);
                    ply.pause();
                    startActivity(intent);
                    finish();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    public void verileriGetir(String uid){

        mReferans=FirebaseDatabase.getInstance().getReference("kullanicilar").child(uid);
        mReferans.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snp:snapshot.getChildren()){

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void veriyiGüncelle(HashMap<String,Object> hashMap,final String uid){
        mReferans=FirebaseDatabase.getInstance().getReference("kullanicilar").child(uid);
        mReferans.updateChildren(hashMap).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Başarı ile güncellendi", Toast.LENGTH_SHORT).show();

                    System.out.println("--güncellenen veriler--");
                    verileriGetir(uid);
                }

            }
        });

    }

    public void güncelleClicked(View view){
        String email = binding.emailText.getText().toString();
        String password = binding.passwordText.getText().toString();
        String name= binding.nameText.getText().toString();


        mData=new HashMap<>();
        mData.put("Sifre",password);

        assert mUser!=null;
        veriyiGüncelle(mData,mUser.getUid());



    }



}