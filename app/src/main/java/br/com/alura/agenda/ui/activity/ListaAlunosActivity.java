package br.com.alura.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.com.alura.agenda.R;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.ui.ListaAlunosView;

import static br.com.alura.agenda.ui.activity.FormularioAlunoActivity.CHAVE_ALUNO;

//AppCompatActivity é uma boa prática no Android, por dar suporte a versões anteriores do Android
@SuppressWarnings("ALL")
public class ListaAlunosActivity extends AppCompatActivity {
    public static final String TITLE_APPBAR = "Lista de alunos";
    private ListView listaAlunos;
    private ListaAlunosView listaAlunosView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        setTitle(TITLE_APPBAR);
        listaAlunosView = new ListaAlunosView(this);
        configuraBotaoCriarNovoAluno();
        configuraLista();
    }

    //cria o menu de contexto atrelando ao arquivo estático do menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.activity_lista_alunos_menu, menu);
    }

    //chamada quando um item do menu de contexto for selecionado
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if(itemId == R.id.activity_lista_alunos_menu_remover) {
            listaAlunosView.confirmaRemocao(item);
        }

        return super.onContextItemSelected(item);
    }

    private void configuraBotaoCriarNovoAluno() {
        FloatingActionButton botaoSalvar = findViewById(R.id.activity_lista_alunos_fab_novo_aluno);

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abreFormularioModoInsercao();
            }
        });
    }

    private void abreFormularioModoInsercao() {
        startActivity(new Intent(ListaAlunosActivity.this, FormularioAlunoActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        listaAlunosView.atualizaLista();
    }

    private void configuraLista() {
        //encontra a ListView pelo id especificado
        listaAlunos = findViewById(R.id.activity_lista_alunos_listView);

        //cria um Adapter. um Adapter age como uma ponte entre uma AdapterView e os dados ocultos para a view. o Adapter providencia acesso aos dados.
        //ele também é responsável por criar uma View para cada item existente no conjunto de dados.
        //uma ArrayView recebe como argumentos o contexto (essa mesma view, então 'this'), o resource e, nesse caso, uma lista de dados
        //uma AdapterView é um ViewGroup que exibe itens carregas em um adaptador
        listaAlunosView.configuraAdapter(listaAlunos);

        configuraListeners(listaAlunos);
        registerForContextMenu(listaAlunos);
    }

    private void configuraListeners(ListView listaAlunos) {
        configuraOnItemClickListener(listaAlunos);
//        configuraOnItemLongClickListener(listaAlunos);
    }

//    private void configuraOnItemLongClickListener(ListView listaAlunos) {
//        listaAlunos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int posicao, long id) {
//                Aluno aluno = (Aluno) adapterView.getItemAtPosition(posicao);
//                removeAluno(aluno);
//                //o retorno identifica se o evento do LongClick será completamente consumido (true) ou se será passado para frente (nesse caso, para editar o aluno se for false)
//                return false;
//            }
//        });
//    }

    private void configuraOnItemClickListener(ListView listaAlunos) {
        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                Aluno aluno = (Aluno) adapterView.getItemAtPosition(posicao);
                abreFormularioModoEdicao(aluno);
            }
        });
    }

    private void abreFormularioModoEdicao(Aluno aluno) {
        Intent intent = new Intent(ListaAlunosActivity.this, FormularioAlunoActivity.class);
        intent.putExtra(CHAVE_ALUNO, aluno);
        startActivity(intent);
    }
}
