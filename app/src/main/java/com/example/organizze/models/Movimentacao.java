package com.example.organizze.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.organizze.config.ConfigurarFirebase;
import com.example.organizze.helper.Base64Custom;
import com.example.organizze.helper.DateUtil;
import com.example.organizze.helper.xGambiarra;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Movimentacao {
    private String data;
    private String dataCriacao;
    private String categoria;
    private String descricao;
    private String tipo;
    private String key;
    private Double valor;


    public static final String TYPE_RECEITA = "receitas";
    public static final String TYPE_DESPESA = "despesas";

        /*CONSTRUTORES*/
    public Movimentacao() {
    }

    public Movimentacao(String categoria, String descricao, Double valor) {
        this.categoria = categoria;
        this.descricao = descricao;
        this.valor = valor;
    }

    public Movimentacao(String data, String categoria, String descricao, String tipo, Double valor) {
        this.data = data;
        this.categoria = categoria;
        this.descricao = descricao;
        this.tipo = tipo;
        this.valor = valor;
    }

    /*MÉTODOS*/
    public void salvar(){
        try {
            this.dataCriacao =DateUtil.dataAtualHora();
            DatabaseReference firebase = ConfigurarFirebase.getDatabaseReference();
            FirebaseAuth autenticacao = ConfigurarFirebase.getAutenticacao();

            String id = Base64Custom.codificarBase64(autenticacao.getCurrentUser().getEmail());

            firebase.child(ConfigurarFirebase.TABLE_MOVIMENTACOS)
                    .child(id)
                    .child(DateUtil.mesAno(this.data))
                    .push()
                    .setValue(this)

            ;
        }catch(Exception e){
            Log.i("Informação",""+e.getMessage());
        }
    }

    /*GET AND SETTERS*/

    public String getDataCriacao() {
        return dataCriacao;
    }
    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public Double getValor() {
        return valor;
    }
    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
