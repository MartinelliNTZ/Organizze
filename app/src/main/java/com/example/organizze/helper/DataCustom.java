package com.example.organizze.helper;

public class DataCustom {
    private String dia;
    private int mesNumerico;
    private String mesString;
    private String dataCompleta;
    private int ano;

    public DataCustom() {
    }
    public DataCustom(String data) {
            /*Recebe uma data que deve estar no fomato dd/MM/yyyy
             * e retona sem as barras os valored de mes e ano 032022
             * O metodo split precisa receber um indice que esteja na String para quebrar a variavel
             * em varios indices de um array nesse caso usaremos a /
             * Mas no caso de horas poderia ser :   */
            String retornoData[] = data.split("/");
            this.dia = retornoData[0];
            this.mesNumerico = Integer.parseInt(retornoData[1]);
            this.ano = Integer.parseInt(retornoData[2]);
            setarMesString();


    }

    public DataCustom(String dia, int mesNumerico, int ano) {
        this.dia = dia;
        this.mesNumerico = mesNumerico;
        this.ano = ano;
        setarMesString();
    }

    public void setarMesString(){


        switch (mesNumerico){
            case(1):
                mesString="Janeiro";
                break;
            case(2):
                mesString="Fevereiro";
                break;
            case(3):
                mesString="Março";
                break;
            case(4):
                mesString="Abril";
                break;
            case(5):
                mesString="Maio";
                break;
            case(6):
                mesString="Junho";
                break;
            case(7):
                mesString="Julho";
                break;
            case(8):
                mesString="Agosto";
                break;
            case(9):
                mesString="Setembro";
                break;
            case(10):
                mesString="Outubro";
                break;
            case(11):
                mesString="Novembro";
                break;
            case(12):
                mesString="Dezembro";
                break;

        }
        this.dataCompleta = this.dia+"/"+this.mesNumerico+"/"+this.ano;
    }
    public void avancarMes(){
        if(mesNumerico<12){
            mesNumerico++;
            setarMesString();
        }else if (mesNumerico==12){
            mesNumerico = 1;
            ano++;
            setarMesString();
        }

    }
    public void retrocederMes(){
        if(mesNumerico>1){
            mesNumerico--;
        }else if (mesNumerico==1){
            mesNumerico = 12;
            ano--;
        }
        setarMesString();
    }

    public String getDia() {
        return dia;
    }
    public void setDia(String dia) {
        this.dia = dia;
    }
    public int getMesNumerico() {
        return mesNumerico;
    }
    public void setMesNumerico(int mesNumerico) {
        this.mesNumerico = mesNumerico;
        setarMesString();
    }
    public String getMesString() {
        return mesString;
    }
    public void setMesString(String mesString) {
        this.mesString = mesString;
    }
    public int getAno() {
        return ano;
    }
    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getDataCompleta() {
        return dataCompleta;
    }
}
