package br.com.alura.agenda.asyncTask;

import android.os.AsyncTask;

import br.com.alura.agenda.dao.AlunoDao;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.ui.adapter.ListAlunosAdapter;

public class RemoveAlunoTask extends AsyncTask<Void, Void, Void> {
    private final AlunoDao dao;
    private final ListAlunosAdapter adapter;
    private final Aluno aluno;

    public RemoveAlunoTask(AlunoDao dao, ListAlunosAdapter adapter, Aluno aluno) {
        this.dao = dao;
        this.adapter = adapter;
        this.aluno = aluno;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.remover(aluno);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        /*Para fazer uma atualização de tela deve-se evitar que o adapter seja chamado no doInBackground().
         Ele precisará estar dentro do onPostExecute(), que será responsável pelas execuções na UI Thread.*/
        adapter.remove(aluno);
    }
}
