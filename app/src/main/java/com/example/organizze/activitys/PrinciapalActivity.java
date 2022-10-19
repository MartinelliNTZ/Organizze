package com.example.organizze.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.organizze.R;
import com.example.organizze.adapter.MovimentacoesAdapter;
import com.example.organizze.config.ConfigurarFirebase;
import com.example.organizze.helper.Base64Custom;
import com.example.organizze.helper.DataCustom;
import com.example.organizze.helper.DateUtil;
import com.example.organizze.helper.xGambiarra;
import com.example.organizze.models.Movimentacao;
import com.example.organizze.models.Usuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PrinciapalActivity extends AppCompatActivity {

    private com.github.clans.fab.FloatingActionButton fabDespesas, fabReceitas;
    private ImageButton  btLeft, btRight;
    private DatabaseReference firebaseRef = ConfigurarFirebase.getDatabaseReference();
    private DatabaseReference movimentacaoRef;
    private DatabaseReference usuarioRef;
    private FirebaseAuth autenticacao = ConfigurarFirebase.getAutenticacao();
    private Double receitaTotal, despesasTotal, saldoAtual;
    private TextView txtSaldo, txtMesAtual, txtSaudacao;
    private DataCustom dataAtual;
    private Toolbar toolbar;
    private String nomeUsuario;
    private String emailUsuario;
    private String idUsuario;
    private String mesAnoSelecionada;
    private ValueEventListener valueEventListenerUsuario;
    private ValueEventListener valueEventListenerMovimentacoes;
    private List<Movimentacao> listaMovimentacao= new ArrayList<>();
    private Movimentacao currentMovimentacao = new Movimentacao();
    private RecyclerView recyclerView;
    private MovimentacoesAdapter movimentacoesAdapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_princiapal);
        linkage();
        configurarFab();
        butons();
        configurarToolbar();
        swipe();
    }
    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sair:
                FirebaseAuth autenticacao= ConfigurarFirebase.getAutenticacao();
                autenticacao.signOut();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override protected void onStop() {
        super.onStop();
        Log.i("Informação","Evento removido");
        /*Remove o listener desocupando espaço na memoria*/
        usuarioRef.removeEventListener(valueEventListenerUsuario);
        movimentacaoRef.removeEventListener(valueEventListenerMovimentacoes);
    }
    @Override protected void onStart() {
        Log.i("Informação","Evento Adicionado");
        configMesatual();
        recuperarReceitaTotal();
        recuperarReferencias();
        setarReciclerView();

        super.onStart();
    }

    public void configurarFab(){
        try {
            fabDespesas = findViewById(R.id.menu_despesa);
            fabDespesas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                    startActivity(new Intent(PrinciapalActivity.this, DespesasActivity.class));
                    }catch (Exception e){
                        Log.i("Informação",""+e.getMessage());
                    }
                }
            });
            fabReceitas = findViewById(R.id.menu_receita);
            fabReceitas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        startActivity(new Intent(PrinciapalActivity.this, ReceitasActivity.class));
                    }catch (Exception e){
                        Log.i("Informação",""+e.getMessage());
                    }
                }
            });
        }catch (Exception e){
            Log.i("Informação",""+e.getMessage());
        }
    }
    public void recuperarReceitaTotal(){
        emailUsuario = autenticacao.getCurrentUser().getEmail();
        idUsuario = Base64Custom.codificarBase64(emailUsuario);
        usuarioRef =firebaseRef.child("usuarios").child(idUsuario);
        mesAnoSelecionada = DateUtil.mesAno(dataAtual.getDataCompleta());
        valueEventListenerUsuario= usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario usuario = snapshot.getValue(Usuario.class);
                receitaTotal=usuario.getTotalReceitas();
                despesasTotal= usuario.getTotalDespesas();
                saldoAtual=receitaTotal-despesasTotal;
                txtSaldo.setText("Saldo: "+saldoAtual);
                nomeUsuario=usuario.getNome();
                txtSaudacao.setText("Olá "+nomeUsuario+". Como você está hoje?");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void linkage(){
        recyclerView=findViewById(R.id.recyclerView);
        txtSaldo=findViewById(R.id.txtSaldo);
        txtMesAtual=findViewById(R.id.txtMesAtual);
        txtSaudacao=findViewById(R.id.txtSaudacao);
        btLeft=findViewById(R.id.btLeft);
        btRight=findViewById(R.id.btRight);
    }
    public void configMesatual(){
        /*Configura a data atual no textView logo quando a aplicação é iniciada*/
        String data = DateUtil.dataAtual();
        dataAtual = new DataCustom(data);
        data =""+ dataAtual.getMesString() + " " + dataAtual.getAno();
        txtMesAtual.setText(data);
    }
    public void butons(){
        /*Listeners dos butons
        * Right e Left alteram o mes atual no text view
        * bt desloga o usuario*/
        btRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataAtual.avancarMes();
                mesAnoSelecionada = DateUtil.mesAno(dataAtual.getDataCompleta());
                movimentacaoRef.removeEventListener(valueEventListenerMovimentacoes);
                recuperarReferencias();
                String data =""+ dataAtual.getMesString() + " " + dataAtual.getAno();
                txtMesAtual.setText(data);
            }
        });
        btLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataAtual.retrocederMes();
                mesAnoSelecionada = DateUtil.mesAno(dataAtual.getDataCompleta());
                movimentacaoRef.removeEventListener(valueEventListenerMovimentacoes);
                recuperarReferencias();
                String data =""+ dataAtual.getMesString() + " " + dataAtual.getAno();
                txtMesAtual.setText(data);
            }
        });

    }
    public void configurarToolbar(){
        try {
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Página Inicial");
        setSupportActionBar(toolbar);
        }   catch (Exception e){
        Log.i("Informação",""+e.getMessage());
        }
    }
    public void setarReciclerView(){
        try{
            movimentacoesAdapter=new MovimentacoesAdapter(getApplicationContext(),listaMovimentacao);
            RecyclerView.LayoutManager layoutManager=new LinearLayoutManager( getApplicationContext());
            recyclerView.setAdapter(movimentacoesAdapter);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
            Log.i("Informação", xGambiarra.COD_OK+"RecyclerView adicionado.");
        }catch (Exception e){
            Log.i("Informação", xGambiarra.COD_ERROR_LOG + e.getMessage());
        }
    }/*Método que configura o recicler view*/
    public void recuperarReferencias(){
        try{
           /**/
            movimentacaoRef= firebaseRef.child(ConfigurarFirebase.TABLE_MOVIMENTACOS).child(idUsuario).child(mesAnoSelecionada);
            /**/
            valueEventListenerMovimentacoes = movimentacaoRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    listaMovimentacao.clear();
                    for (DataSnapshot dados: snapshot.getChildren()){
                        Movimentacao movimentacao = dados.getValue(Movimentacao.class);
                        String key = dados.getKey() == null ? "ABC" :dados.getKey();
                        movimentacao.setKey(key);
                        listaMovimentacao.add(movimentacao);
                        Log.i("Informação aqui", xGambiarra.COD_ERROR_LOG +mesAnoSelecionada+ movimentacao.getCategoria());
                    }
                    movimentacoesAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            Log.i("Informação", xGambiarra.COD_OK);
        }catch (Exception e){
            Log.i("Informação aq", xGambiarra.COD_ERROR_LOG + e.getMessage());
        }
    }/**/
    public void swipe(){

        try{
           /* Cria um item touch*/
            ItemTouchHelper.Callback itemTouch = new ItemTouchHelper.Callback() {
                @Override
                public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                    /*O DRAGFLAGS SE REfere ao tipo de click que possuira no nosso caso só sera possivel arrastar*/
                    int dragFlags = ItemTouchHelper.ACTION_STATE_IDLE;
                    /*O swipe flags define a direção para qual podera ocorrer o movimento*/
                    int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                    return makeMovementFlags(dragFlags,swipeFlags);
                }

                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    excluirMovimentacao(viewHolder);
                }
            };

            /*Linka o item touch ao recyclerview*/
            new ItemTouchHelper(itemTouch).attachToRecyclerView(recyclerView);






            Log.i("Informação", xGambiarra.COD_OK);
        }catch (Exception e){
            Log.i("Informação aq", xGambiarra.COD_ERROR_LOG + e.getMessage());
        }
    }/**/
    public void excluirMovimentacao(RecyclerView.ViewHolder viewHolder){
        try{
           /* Cria um item touch
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Excluir M")*/
            AlertDialog.Builder alert = xGambiarra.dialog(this,
                    "Você tem certeza que deseja excluir essa movimentação?",
                    "Excluir Movimentação da Conta");
            saldoAtual =1.0;
            alert.setCancelable(false);
            alert.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int position = viewHolder.getAdapterPosition();
                    currentMovimentacao = listaMovimentacao.get(position);
                    String mess = "Você apagou uma "+
                            (currentMovimentacao.getTipo() == Movimentacao.TYPE_DESPESA ? "Despesa" : "Receita")+
                            " no valor de R$"+
                            currentMovimentacao.getValor();
                    Toast.makeText(PrinciapalActivity.this, mess, Toast.LENGTH_SHORT).show();
                    movimentacaoRef= firebaseRef
                            .child(ConfigurarFirebase.TABLE_MOVIMENTACOS)
                            .child(idUsuario)
                            .child(mesAnoSelecionada);
                    movimentacaoRef.child(currentMovimentacao.getKey()).removeValue();
                    movimentacoesAdapter.notifyItemRemoved(position);
                    atualizarSaldo();
                }
            })
            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(PrinciapalActivity.this, "Cancelado", Toast.LENGTH_SHORT).show();
                            movimentacoesAdapter.notifyDataSetChanged();


                        }
                    });
            alert.create().show();


            Log.i("Informação", xGambiarra.COD_OK);
        }catch (Exception e){
            Log.i("Informação aq", xGambiarra.COD_ERROR_LOG + e.getMessage());
        }
    }/**/
    public void atualizarSaldo(){
        try{
            /**/
            /**/
            receitaTotal = currentMovimentacao.getTipo().equals(Movimentacao.TYPE_DESPESA) ?
                    receitaTotal + currentMovimentacao.getValor() :
                    receitaTotal - currentMovimentacao.getValor();
            String mess = "Saldo: R$ "+ receitaTotal;
            txtSaldo.setText(mess);


            Log.i("Informação", xGambiarra.COD_OK);
        }catch (Exception e){
            Log.i("Informação aq", xGambiarra.COD_ERROR_LOG + e.getMessage());
        }
    }/**/
}