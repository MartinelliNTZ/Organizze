package com.example.organizze.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.organizze.R;
import com.example.organizze.config.ConfigurarFirebase;
import com.example.organizze.helper.xGambiarra;
import com.example.organizze.models.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class LoginActivity extends AppCompatActivity {
    private EditText edtEmailLog, edtPassLog;
    private Button btLogin;
    private FirebaseAuth autenticacao;
    private Usuario usuario;
    private Activity ac;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        linkagem();
        ac = this;
        getSupportActionBar().hide();
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOk()){
                    String email = edtEmailLog.getText().toString();
                    String senha = edtPassLog.getText().toString();
                    usuario = new Usuario(email,senha);
                    autenticarUsuario();
                }
            }
        });
    }

    private boolean isOk(){
        /*Método que verifica se os camçpos estão preenchidos*/
         if (edtEmailLog.length()==0){
            Toast.makeText(LoginActivity.this, "Preencha seu email", Toast.LENGTH_SHORT).show();
            return false;
        }else if (edtPassLog.length()==0) {
            Toast.makeText(LoginActivity.this, "Preencha sua senha", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return  true;
        }

    }
    private void linkagem(){

        edtEmailLog=findViewById(R.id.edtEmailLog);
        edtPassLog=findViewById(R.id.edtPassLog);
        btLogin=findViewById(R.id.btCadastrarCad);

    }
    private void autenticarUsuario(){
        /*Faz o cadastro dos usuarios*/
        autenticacao= ConfigurarFirebase.getAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(),usuario.getSenha()
        ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, xGambiarra.COD_OK, Toast.LENGTH_SHORT).show();
                    try {
                        startActivity(new Intent(ac, PrinciapalActivity.class));
                        finish();
                    }catch(Exception e){
                        Log.i("INFORMAÇÃO ",""+e.getMessage());
                    }

                } else {
                    String excessao = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        excessao = "Seu email e senha estão incorreto!";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        excessao = "Sua senha esta incorreto!";
                    }catch (Exception e) {
                        excessao = "Erro ao logar usuário " + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(LoginActivity.this, excessao, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}