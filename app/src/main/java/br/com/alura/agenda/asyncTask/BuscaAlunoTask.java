package br.com.alura.agenda.asyncTask;

import android.os.AsyncTask;

import java.util.List;

import br.com.alura.agenda.dao.AlunoDao;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.ui.adapter.ListAlunosAdapter;

/*AsyncTask é utilizado para executra operações em paralelo e, conforme o resiltado é obtido, a UI thread é notificada.
* AsyncTask é uma API de classe abstrata*/
public class BuscaAlunoTask extends AsyncTask<Void, Void, List<Aluno>> {
    private final AlunoDao dao;
    private final ListAlunosAdapter adapter;

    public BuscaAlunoTask(AlunoDao dao, ListAlunosAdapter adapter) {
        this.dao = dao;
        this.adapter = adapter;
    }

    @Override
    protected List<Aluno> doInBackground(Void[] objects) { //tudo que for executado em background será executado numa thread secundária
        return dao.todos();
    }

    @Override
    protected void onPostExecute(List<Aluno> listaAlunos) {    //será executada quando a operação em background for finalizada. recebe como parametro o resultado de doInBackground
        super.onPostExecute(listaAlunos);
        adapter.atualizaLista(listaAlunos);
    }
}
