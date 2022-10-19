package com.example.organizze.models;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.organizze.config.ConfigurarFirebase;
import com.example.organizze.helper.Base64Custom;
import com.example.organizze.helper.xGambiarra;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

/**
 * Created by jamiltondamasceno
 */

public class Usuario {

    private String idUsuario;
    private String nome;
    private String email;
    private String senha;
    private Double totalDespesas = 0.0;
    private Double totalReceitas = 0.0;


    public Usuario() {
    }
    public Usuario( String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public void salvar(){
        String id = Base64Custom.codificarBase64(email);
        this.idUsuario=id;
        DatabaseReference firebase = ConfigurarFirebase.getDatabaseReference();
        firebase.child("usuarios")
                .child( this.idUsuario )
                .setValue( this )
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.i("Informação: Cadastro", xGambiarra.COD_OK+unused.toString());
                    }})
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Informação: Cadastro",xGambiarra.COD_ERROR+e.getMessage());
                        e.printStackTrace();

                    }
                });;
    }
/*@Exclude quando se envia um this. para o firebase o Exclude ira excluir uma dado que vc não queira que va para nuvem*/
    @Exclude
    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Double getTotalDespesas() {
        return totalDespesas;
    }

    public void setTotalDespesas(Double totalDespesas) {
        this.totalDespesas = totalDespesas;
    }

    public Double getTotalReceitas() {
        return totalReceitas;
    }

    public void setTotalReceitas(Double totalReceitas) {
        this.totalReceitas = totalReceitas;
    }
}
