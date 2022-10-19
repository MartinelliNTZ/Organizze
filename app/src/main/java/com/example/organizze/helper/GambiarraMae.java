package com.example.organizze.helper;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.text.Editable;
import android.util.Base64;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;

public class GambiarraMae {
    /*##################################################............VARIÁVEIS...........###############################################*/
    /*---------------------------------VARIAVEIS DE USO GERAL-----------------------------------------*/
    public static final String COD_ERROR = "Houve um erro com a execução. ";
    public static final String COD_ERROR_LOG = "Houve um erro com a execução. Código: ";
    public static final String COD_OK = "Sucesso na execução. ";


    /*---------------------------------VARIAVEIS INTERNAS-----------------------------------------
    * usadas dentro da propria classe*/
    private static final String BT_CONFIRMAR = "Confirmar ";
    private static final String BT_SIM = "Sucesso na execução. ";


    /*##################################################............MÉTODOS...........###############################################*/

    /*-------------------------------------ALERT DIALOG'S------------------------
    *para passar um Icone como parametro basta ultilizar android.R.drawable.
    *Para configurar um evento de clique basta criar um objeto  AlertDialog.Builder alerta = XString.dialogSim(..)
    * NÃO ULTILIZAR O SET SET POSIVITE OU NEGATIVE BUTTON POIS JA FOI CHAMADO O METODO .SHOW()*/
    public static AlertDialog.Builder dialogSim(Context context, String mensagem){

        //Instanciar AlertDialog
        AlertDialog.Builder dialog = new AlertDialog.Builder( context );

        //Configurar titulo e mensagem
        dialog.setTitle("Alerta");
        dialog.setMessage(mensagem);

        //Configurar cancelamento
        dialog.setCancelable(false);

        //Configurar icone
        dialog.setIcon( android.R.drawable.ic_dialog_alert );

        //Configura acoes para sim e nao
        dialog.setPositiveButton(BT_SIM, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {            }        });

        //Criar e exibir AlertDialog
        dialog.create();
        dialog.show();
        return dialog;

    }
    public static AlertDialog.Builder dialogSim(Context context,String mensagem, String titulo){

        //Instanciar AlertDialog
        AlertDialog.Builder dialog = new AlertDialog.Builder( context );

        //Configurar titulo e mensagem
        dialog.setTitle(titulo);
        dialog.setMessage(mensagem);

        //Configurar cancelamento
        dialog.setCancelable(false);

        //Configurar icone
        dialog.setIcon( android.R.drawable.ic_dialog_alert );

        //Configura acoes para sim e nao
        dialog.setPositiveButton(BT_SIM, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {            }        });

        //Criar e exibir AlertDialog
        dialog.create();
        dialog.show();
        return dialog;

    }
    public static AlertDialog.Builder dialogSim(Context context,String mensagem, String titulo, int iconID){

        //Instanciar AlertDialog
        AlertDialog.Builder dialog = new AlertDialog.Builder( context );

        //Configurar titulo e mensagem
        dialog.setTitle(titulo);
        dialog.setMessage(mensagem);

        //Configurar cancelamento
        dialog.setCancelable(false);

        //Configurar icone
        dialog.setIcon( iconID );

        //Configura acoes para sim e nao
        dialog.setPositiveButton(BT_SIM, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {            }        });

        //Criar e exibir AlertDialog
        dialog.create();
        dialog.show();
        return dialog;

    }
/*Aqui deve-se configurar o setpositive ou negative e usar o .create.show();  */
    public static AlertDialog.Builder dialog(Context context, String mensagem){

        //Instanciar AlertDialog
        AlertDialog.Builder dialog = new AlertDialog.Builder( context );

        //Configurar titulo e mensagem
        dialog.setTitle("Alerta");
        dialog.setMessage(mensagem);

        //Configurar cancelamento
        dialog.setCancelable(false);

        //Configurar icone
        dialog.setIcon( android.R.drawable.ic_dialog_alert );


        //Criar e exibir AlertDialog
        dialog.create();
        return dialog;

    }
    public static AlertDialog.Builder dialog(Context context,String mensagem, String titulo){

        //Instanciar AlertDialog
        AlertDialog.Builder dialog = new AlertDialog.Builder( context );

        //Configurar titulo e mensagem
        dialog.setTitle(titulo);
        dialog.setMessage(mensagem);

        //Configurar cancelamento
        dialog.setCancelable(false);

        //Configurar icone
        dialog.setIcon( android.R.drawable.ic_dialog_alert );


        //Criar e exibir AlertDialog
        dialog.create();
        return dialog;

    }
    public static AlertDialog.Builder dialog(Context context,String mensagem, String titulo, int iconID){

        //Instanciar AlertDialog
        AlertDialog.Builder dialog = new AlertDialog.Builder( context );

        //Configurar titulo e mensagem
        dialog.setTitle(titulo);
        dialog.setMessage(mensagem);

        //Configurar cancelamento
        dialog.setCancelable(false);

        //Configurar icone
        dialog.setIcon( iconID );

        //Criar e exibir AlertDialog
        dialog.create();
        return dialog;

    }

/*-------------------------------------SNACKBAR------------------------*/
    /*PARA PASSAR A ACTIVITY ULTILIZE this ou MainActivity.this*/
    public static void snackbarConfirmar(Activity activity, String mensagem){
        View view = activity.findViewById(android.R.id.content);
       Snackbar.make(
                        view,
                        mensagem,
                        Snackbar.LENGTH_LONG
                ).setAction(BT_CONFIRMAR, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).setActionTextColor( activity.getResources().getColor( android.R.color.holo_orange_dark ) )
                .show();
    }
    public static Snackbar snackbar(Activity activity, String mensagem){

        View view = activity.findViewById(android.R.id.content);
        return  Snackbar.make(
                        view,
                        mensagem,
                        Snackbar.LENGTH_LONG
                ).setAction(BT_CONFIRMAR, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).setActionTextColor( activity.getResources().getColor( android.R.color.holo_orange_dark ) )
                ;
    }

    /*-------------------------------------TOAST------------------------*/
    /*PARA PASSAR A ACTIVITY ULTILIZE this ou MainActivity.this*/
    public static void toastCentralizado(Activity activity, String mensagem){
        Toast toast = Toast.makeText(activity, mensagem, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }

    /*--------------------------ABRE E FECHA TECLADO-----------------------------------*/
    /*PARA ABRIR O TECLADO É NECESSARIO PASSAR O EDITtEXT QUE VAI RECEBER FOCO*/
    public static void keyboardShow(Context context, TextInputEditText textInputEditText) {
        textInputEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_NOT_ALWAYS);
    }//método que exibe o teclado
    public static void keyboardShow(Context context, EditText editText) {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_NOT_ALWAYS);
    }//método que exibe o teclado
    public static void keyboardHidenn(Context context){
        /*Médoto que fecha o teclado
        * É preciso passar o contect da aplicação*/
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if(imm.isActive())
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }//metodo que fecha o teclado


    /*----------------------------FORMATAÇÃO DE DECIMAIS---------------------------------*/
    public static Double decimalFormat(Double valor){
        Double resultado;
        DecimalFormat fmt = new DecimalFormat("0.00");
        String stringValor = valor.toString();
        double dbvalor = Double.parseDouble(stringValor);
        resultado=Double.parseDouble(fmt.format(dbvalor));
        return resultado;
    }
    public static Double decimalFormat(String valor){
        Double resultado;
        DecimalFormat fmt = new DecimalFormat("0.00");
        double dbvalor = Double.parseDouble(valor);
        resultado=Double.parseDouble(fmt.format(dbvalor));
        return resultado;
    }
    public static Double decimalFormat(int valor){
        Double resultado;
        DecimalFormat fmt = new DecimalFormat("0.00");
        String stringValor = ""+valor;
        double dbvalor = Double.parseDouble(stringValor);
        resultado=Double.parseDouble(fmt.format(dbvalor));
        return resultado;
    }
    public static Double decimalFormat(Editable valor){
        DecimalFormat fmt = new DecimalFormat("0.00");
        String stringValor = valor.toString();
        double dbvalor = Double.parseDouble(stringValor);

        Double resultado= Double.valueOf(fmt.format(dbvalor));



        return resultado;
    }

    /*Tratamendo de virgula ainda falta configurar*/
    public static String decimalToVirgula(Double valor){
       String resultado = "";
        return resultado;
    }
    public static Double decimalToVirgula(String valor){
       Double resultado =0.0;
        return resultado;
    }
    public static String formatarDoisDigitos(String dado){
        /*O método recebe a chave coringa(%)depois recebe o valor que deve ser preenchido
        * no nosso caso é o 0
        * o 2d se recebe a dois digitos
        * caso recebamos o numero 2 o retorno é 02
        * caso fosse ("%x10d") e recebecemos um 2 o retorno seria xxxxxxxxx2*/
        String retorno =String.format("%02d",dado);
        return   retorno;

    }


    /*-----------------------------CONVERSÃO--------------------------------
    *Configura a imagem para ser salva na memória e retona como um array de Bytes*/
    public byte[] converterImageParaArrayBytes(ImageView img){
        /*Configura a imagem para ser salva na memória*/
        img.setDrawingCacheEnabled(true);
        img.buildDrawingCache();

        /*Recupera o bitmap da imagem*/
        Bitmap bitmap = img.getDrawingCache();

        /*Cria um ByteArray*/
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        /*Comprimi bitmap para outro formato de imagem png, jpg etc
         * 1* paramentro se refere ao tipo de extensão que vc deseja
         * 2* parametro a qualidade da imagem 0=qualidade péssima 100=qualidade maxima 75=valor OK
         * 3* parametro é uma representação do dados onde serão armazenados os dados da imagem*/
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75,baos);

        /*Converte o baos para pixels brutos em uma matriz de bytes
         * só assim é possivel salvar no firebase, pois ele requer uma matriz de bytes*/
        byte [] dadosImagem = baos.toByteArray();
        return  dadosImagem;
    }

    /*----------------------------CODIFICAÇÃO PARA BASE 64---------------------------------*/
    public static String codificarBase64(String email){
        return Base64.encodeToString(
                email.getBytes(),
                android.util.Base64.DEFAULT).replaceAll("(\\n|\\r)","");
    }
    public static String decodificarBase64(String codificacao){
        return   Base64.decode(codificacao,Base64.DEFAULT).toString();

    }


    /*-------------------------------------------------------------*/
    /*-------------------------------------------------------------*/


    /*##################################################............CLASSES...........###############################################*/
    /*-------------------------TRATAMENTO DE CLICK RECICLERVIEW---------------------------------*/
    public static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

        private OnItemClickListener mListener;
        GestureDetector mGestureDetector;

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View childView = rv.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(childView, rv.getChildAdapterPosition(childView));
                return true;
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }

        public interface OnItemClickListener extends AdapterView.OnItemClickListener {
            public void onItemClick(View view, int position);

            public void onLongItemClick(View view, int position);
        }

        public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
            mListener = listener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && mListener != null) {
                        mListener.onLongItemClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });

        }
    }

}

