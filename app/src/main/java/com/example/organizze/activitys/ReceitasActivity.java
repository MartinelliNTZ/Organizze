package com.example.organizze.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.organizze.R;
import com.example.organizze.config.ConfigurarFirebase;
import com.example.organizze.helper.Base64Custom;
import com.example.organizze.helper.DateUtil;
import com.example.organizze.helper.xGambiarra;
import com.example.organizze.models.Movimentacao;
import com.example.organizze.models.Usuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ReceitasActivity extends AppCompatActivity {
    private ConstraintLayout clReceitas;
    private TextInputEditText edtValorR, edtDataR, edtCategoriaR, edtDescricaoR;
    private FloatingActionButton fabAddReceitas;
    private Movimentacao movimentacao;
    private  String data,     categoria,     descricao;
    private final String tipo = Movimentacao.TYPE_RECEITA;
    private Double valor;
    private DatabaseReference firebaseRef = ConfigurarFirebase.getDatabaseReference();
    private FirebaseAuth autemticacao = ConfigurarFirebase.getAutenticacao();
    private Double receitaTotal;
    private Double receitaAtualizada;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receitas);
        linkagem();
        recuperarReceitaTotal();
        fecharTeclado();
        configFAB();
    }
    public void fecharTeclado(){
        clReceitas=findViewById(R.id.clReceitas);
        clReceitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xGambiarra.keyboardHidenn(getApplicationContext());
            }
        });
    }
    public void linkagem(){
        edtValorR =findViewById(R.id.edtValorR);
        edtDataR =findViewById(R.id.edtDataR);
        edtCategoriaR =findViewById(R.id.edtCategoriaR);
        edtDescricaoR =findViewById(R.id.edtDescricaoR);


        edtDataR.setText(DateUtil.dataAtual());
    }
    public void recuperarReceitaTotal(){
        String email = autemticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(email);
        DatabaseReference usuarioAtual =firebaseRef.child("usuarios").child(idUsuario);
        usuarioAtual.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario usuario = snapshot.getValue(Usuario.class);
                receitaTotal=usuario.getTotalReceitas();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void atualizarReceita(){
        String email = autemticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(email);
        DatabaseReference usuarioAtual =firebaseRef.child("usuarios").child(idUsuario);

        usuarioAtual.child("totalReceitas").setValue(receitaAtualizada);


    }
    public boolean validarCampos(){
        data = edtDataR.getText().toString();
        categoria = edtCategoriaR.getText().toString();
        descricao = edtDescricaoR.getText().toString();
        valor =  Double.parseDouble(edtValorR.getText().toString());

        if(valor !=null && valor!=0){
            if(data !=null && !data.equals("") && !data.isEmpty()){
                if(categoria !=null && !categoria.equals("") && !categoria.isEmpty()){
                    if(descricao !=null && !descricao.equals("") && !descricao.isEmpty()){
                        return true;
                    }else{//categoria
                        Toast.makeText(this, "Por favor insira uma descrição válido.", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }else{//categoria
                    Toast.makeText(this, "Por favor insira uma categoria válido.", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }else{//data
                Toast.makeText(this, "Por favor insira uma data válida.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }else{//valor
            Toast.makeText(this, "Por favor insira um valor valido e diferente de zero", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public void configFAB(){
        try {
            fabAddReceitas = findViewById(R.id.fabAddReceita);
            fabAddReceitas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if(validarCampos()) {
                            movimentacao = new Movimentacao(data, categoria, descricao, tipo, valor);
                            receitaAtualizada = receitaTotal + valor;
                            atualizarReceita();
                            movimentacao.salvar();
                            releaseInstance();
                            //  startActivity(new Intent(DespesasActivity.this,PrinciapalActivity.class));
                            finish();
                        }
                    }catch (Exception e){
                        Log.i("Informação: CadReceita", xGambiarra.COD_ERROR+e.getMessage());
                    }
                }
            });
        }catch (Exception e){
            Log.i("Informação: CadReceita", xGambiarra.COD_ERROR+e.getMessage());
        }
    }
}