package br.com.alura.agenda.asyncTask;

import android.os.AsyncTask;

import java.util.List;

import br.com.alura.agenda.dao.TelefoneDao;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.model.Telefone;

public class BuscaTodosTelefonesDoAluno extends AsyncTask<Void, Void, List<Telefone>> {
    private final TelefoneDao dao;
    private final Aluno aluno;
    private final TelefonesDoAlunoEncontradosListener listener;

    public BuscaTodosTelefonesDoAluno(TelefoneDao dao, Aluno aluno, TelefonesDoAlunoEncontradosListener listener) {
        this.dao = dao;
        this.aluno = aluno;
        this.listener = listener;
    }

    @Override
    protected List<Telefone> doInBackground(Void... voids) {
        return dao.buscaTodosTelefonesDoAluno(aluno.getId());
    }

    @Override
    protected void onPostExecute(List<Telefone> telefones) {
        super.onPostExecute(telefones);
        listener.quandoEncontrados(telefones);
    }

    public interface TelefonesDoAlunoEncontradosListener {
        void quandoEncontrados(List<Telefone> telefones);
    }
}
