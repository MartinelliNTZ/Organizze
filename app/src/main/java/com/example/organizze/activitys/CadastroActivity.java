package com.example.organizze.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.organizze.R;
import com.example.organizze.config.ConfigurarFirebase;
import com.example.organizze.helper.Base64Custom;
import com.example.organizze.helper.xGambiarra;
import com.example.organizze.models.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastroActivity extends AppCompatActivity {
            private EditText edtNomeCad,edtEmailCad, edtPassCad;
            private Button btCadastrarCad;
            private FirebaseAuth autenticacao;
            private Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        linkagem();
        getSupportActionBar().hide();

        btCadastrarCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOk()){
                    String nome = edtNomeCad.getText().toString();
                    String email = edtEmailCad.getText().toString();
                    String senha = edtPassCad.getText().toString();

                    usuario = new Usuario(nome,email,senha);
                    cadastrarUsuario();



                }
            }
        });

    }
    private void cadastrarUsuario(){
        /*Faz o cadastro dos usuarios*/
        autenticacao= ConfigurarFirebase.getAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CadastroActivity.this, xGambiarra.COD_OK, Toast.LENGTH_SHORT).show();
                    autenticacao.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha());
                    usuario.salvar();
                    startActivity(new Intent(CadastroActivity.this,PrinciapalActivity.class));
                    finish();
                } else {
                    String excessao = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        excessao = "Digite uma senha mais forte!";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        excessao = "Por favor digite um e-mail valido!";
                    } catch (FirebaseAuthUserCollisionException e) {
                        excessao = "Já existe uma conta cadastrada com esse e-mail!";
                    } catch (Exception e) {
                        excessao = "Erro ao cadastrar usuário " + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroActivity.this, excessao, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    private boolean isOk(){
        /*Método que verifica se os camçpos estão preenchidos*/
        if (edtNomeCad.length()==0){
            Toast.makeText(CadastroActivity.this, "Preencha seu nome", Toast.LENGTH_SHORT).show();
            return false;
        }else if (edtEmailCad.length()==0){
            Toast.makeText(CadastroActivity.this, "Preencha seu email", Toast.LENGTH_SHORT).show();
            return false;
        }else if (edtPassCad.length()==0) {
            Toast.makeText(CadastroActivity.this, "Preencha sua senha", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return  true;
        }

    }
    private void linkagem(){
        edtNomeCad=findViewById(R.id.edtNomeCad);
        edtEmailCad=findViewById(R.id.edtEmailCad);
        edtPassCad=findViewById(R.id.edtPassCad);
        btCadastrarCad=findViewById(R.id.btCadastrarCad);

    }
}