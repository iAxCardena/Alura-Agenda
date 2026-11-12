package br.com.alura.agenda.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;

import br.com.alura.agenda.asyncTask.BuscaAlunoTask;
import br.com.alura.agenda.asyncTask.RemoveAlunoTask;
import br.com.alura.agenda.dao.AlunoDao;
import br.com.alura.agenda.database.AgendaDatabase;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.ui.adapter.ListAlunosAdapter;

public class ListaAlunosView {
    private final AlunoDao dao;
    private final ListAlunosAdapter adapter;
    private final Context context;

    public ListaAlunosView(Context context) {
        this.context = context;
        this.adapter = new ListAlunosAdapter(this.context);
        dao = AgendaDatabase.getInstance(context).getAlunoDAO();
    }

    public void confirmaRemocao(final MenuItem item) {
        /*Dialogs são janelas que levam o usuário a tomar uma decisão ou inserir informações adicionais. Elas não ocupam toda a tela e são normalmente usadas para eventos
        modais que exijam que usuários realizem uma ação antes de continuar.No caso do AlertDialog, temos acesso ao Builder que permite, por exemplo,
        adicionar um título ou mensagem de forma encadeada durante a criação.*/
        new AlertDialog.Builder(context)
                .setTitle("Removendo Aluno")    //titulo do dialog
                .setMessage("Deseja remover o aluno selecionado?")  //mensagem
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {   //botão de confirmação. ao ser clicada realiza a ação de remoção
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AdapterView.AdapterContextMenuInfo adapterMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                        Aluno aluno = adapter.getItem(adapterMenuInfo.position);
                        removeAluno(aluno);
                    }
                })
                .setNegativeButton("Não", null).show(); //botão de negação. sem listener. resulta apenas no fechamendo do dialog
    }

    public void atualizaLista() {
        new BuscaAlunoTask(dao, adapter).execute(); //execute() a chamada para a AsyncTask encapsulando tudo que esteja no doInBackground(), sendo gerada uma nova Thread para a execução fora da UI Thread
    }

    private void removeAluno(Aluno aluno) {
        new RemoveAlunoTask(dao, adapter, aluno).execute();

    }

    public void configuraAdapter(ListView listaAlunos) {
        listaAlunos.setAdapter(adapter);
    }
}
