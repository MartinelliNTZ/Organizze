package com.example.organizze.activitys;

import android.os.Bundle;

import com.example.organizze.R;
import com.google.android.material.snackbar.Snackbar;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class Slider extends IntroActivity {
    private int img1, img2, img3, img4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        linkagem();
        ocultarBotoesDeNavegacao();
        adicionarFragmentSlides();


    }

    @Override
    protected void onPause() {
        super.onPause();
       // Toast.makeText(this, "564sfd5f4ds", Toast.LENGTH_SHORT).show();
        releaseInstance();
        finish();
    }

    private void adicionarFragmentSlides(){
        /*Simplesmente o metodo que chama o metodo e passa os parametros*/
        criarFragmentSlide(R.layout.intro1);
        criarFragmentSlide(R.layout.intro2);
        criarFragmentSlide(R.layout.intro3);
        criarFragmentSlide(R.layout.intro4);


    }
    private void criarFragmentSlide(int fragment){
        /*Infla o layout que o caminho para o qual esta o layout qque devemos inflar como um slide.*/
        addSlide(new FragmentSlide.Builder()
                .background(R.color.holo_blue_dark)
                .backgroundDark(R.color.holo_blue_dark)
                .fragment(fragment)
                .build());

    }
    private void criarFragmentSlide(int fragment, boolean paraFrente,boolean paraTras){
        /*Infla o layout que o caminho para o qual esta o layout qque devemos inflar como um slide.
         *Também recebe dois booleanos que se refem a capacidade de ir pra frente ou pra tras*/
        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.holo_blue_dark)
                .backgroundDark(android.R.color.holo_blue_dark)
                .fragment(fragment)
                .canGoBackward(paraTras)
                .canGoForward(paraFrente)
                .build());

    }
    private void adicionarSimpleSlides(){
        /*Simplesmente o metodo que chama o metodo e passa os parametros*/
        criarSimpleSlides("titulo1","descricao",img1,R.color.black);
        criarSimpleSlides("titulo2","descricao2",img2,R.color.purple_500);
        criarSimpleSlides("titulo3","descricao3",img3,R.color.white);
        criarSimpleSlides("titulo4","descricao4",img4,R.color.teal_200);
    }
    public void criarSimpleSlides (String titulo,String descricao, int imagem ){
        addSlide(new SimpleSlide.Builder()
                .title(titulo)
                .description(descricao)
                .image(imagem)
                .background(R.color.purple_500)
                .build()
        )  ;
    }
    public void criarSimpleSlides (String titulo,String descricao, int imagem, int background ){
        addSlide(new SimpleSlide.Builder()
                .title(titulo)
                .description(descricao)
                .image(imagem)
                .background(R.color.purple_500)
                .build()
        )  ;
    }
    private void linkagem(){
        img1=R.drawable.um;
        img2=R.drawable.dois;
        img3=R.drawable.tres;
        img4=R.drawable.quatro;

    }
    private void ocultarBotoesDeNavegacao(){
        /*Este método oculta os botoes de navegação do slider*/
        setButtonBackVisible(false);
        setButtonNextVisible(false);

    }


}