package br.com.alura.agenda.asyncTask;

import android.os.AsyncTask;

import br.com.alura.agenda.model.Telefone;

public abstract class BaseAlunoComTelefone extends AsyncTask<Void, Void, Void> {
    private final FinalizadaListener listener;

    protected BaseAlunoComTelefone(FinalizadaListener listener) {
        this.listener = listener;
    }

    protected void vinculaAlunoComTelefone(int alunoId, Telefone... telefones) {
        for (Telefone telefone : telefones) {
            telefone.setAlunoId(alunoId);
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.quandoFinalizada();
    }

    public interface FinalizadaListener {
        void quandoFinalizada();
    }
}
