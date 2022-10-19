package com.example.organizze.activitys;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.organizze.R;
import com.example.organizze.config.ConfigurarFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
                private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                private DatabaseReference nomes = databaseReference.child("nomes");
                private int img1, img2, img3, img4;
                private Button btCadastro, btLogin;
                private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_cadastro);
        linkagem();
        botoes();
      //


    }

    @Override
    protected void onStart() {
        super.onStart();
        ActionBar bar = getSupportActionBar();
        assert bar != null;
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFFFF")));

        userIsLogado();
    }

    private void botoes(){
        btCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CadastroActivity.class));
            }
        });
         btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });


    }
    private void linkagem(){
        btCadastro=findViewById(R.id.btCadastro);
        btLogin=findViewById(R.id.btLogin);

    }
    private void userIsLogado(){
       autenticacao= ConfigurarFirebase.getAutenticacao();

       if (autenticacao.getCurrentUser() !=null){
           startActivity(new Intent(MainActivity.this, PrinciapalActivity.class));
       }else{
           startActivity(new Intent(this, Slider.class));/*Chama os slides*/
       }

    }

}