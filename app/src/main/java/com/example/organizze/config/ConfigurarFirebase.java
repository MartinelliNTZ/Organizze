package com.example.organizze.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfigurarFirebase {

    private static FirebaseAuth autenticacao;
    private static DatabaseReference databaseReference;
    public static final String TABLE_MOVIMENTACOS = "movimentacoes";
    public static final String TABLE_USUARIOS = "usuarios";

    public static FirebaseAuth getAutenticacao() {
        /*Verifica se a autenticação ja foi estanciada e retorna ela*/
        if (autenticacao==null){
            autenticacao= FirebaseAuth.getInstance();
        }
        return autenticacao;
    }
    public static DatabaseReference getDatabaseReference() {
        /*Verifica se a autenticação ja foi estanciada e retorna ela*/
        if (databaseReference==null){
            databaseReference= FirebaseDatabase.getInstance().getReference();
        }
        return databaseReference;
    }
}
