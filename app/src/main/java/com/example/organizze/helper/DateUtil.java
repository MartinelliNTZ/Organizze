package com.example.organizze.helper;

import java.text.SimpleDateFormat;

public class DateUtil {

    public static String dataAtual(){
        /*Método que retorna a data atual do systema
        *A classe simpledateformat recebe como pattern o dia (d) / mes (M) / ano (yyyy)
        * estes são os parametros para o padrao brasileiro porem existem diversos outros padroes*/
        long date = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = simpleDateFormat.format(date);
        return dateString;

    }
    public static String dataAtualHora(){
        /*Método que retorna a data atual do systema
        *A classe simpledateformat recebe como pattern o dia (d) / mes (M) / ano (yyyy)
        * além de hora(hh), minutos ()mm e segundos (ss)
        * estes são os parametros para o padrao brasileiro porem existem diversos outros padroes*/
        long date = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String dateString = simpleDateFormat.format(date);
        return dateString;

    }
    public static String mesAno(String data){
        /*Recebe uma data que deve estar no fomato dd/MM/yyyy
        * e retona sem as barras os valored de mes e ano 032022
        * O metodo split precisa receber um indice que esteja na String para quebrar a variavel
        * em varios indices de um array nesse caso usaremos a /
        * Mas no caso de horas poderia ser :   */
        String retornoData[] = data.split("/");
        String dia = retornoData[0];
        String mes = retornoData[1];
        String ano = retornoData[2];
        String mesAno = mes+ano;
        return mesAno;
    }
}
