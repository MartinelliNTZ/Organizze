package com.example.organizze.helper;

import android.util.Base64;

import java.nio.charset.StandardCharsets;

public class Base64Custom {
    public static String codificarBase64(String email){
        return Base64.encodeToString(
                email.getBytes(),
                android.util.Base64.DEFAULT).replaceAll("(\\n|\\r)","");
    }
    public static String decodificarBase64(String codificacao){
        return   Base64.decode(codificacao,Base64.DEFAULT).toString();

    }
}
