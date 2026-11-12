package br.com.alura.agenda.asyncTask;

import android.os.AsyncTask;
import android.widget.TextView;

import br.com.alura.agenda.dao.TelefoneDao;
import br.com.alura.agenda.model.Telefone;

public class BuscaPrimeiroTelefoneDoAlunoTask extends AsyncTask<Void, Void, Telefone> {
    private final TelefoneDao dao;
    private final int alunoId;
    private final PrimeiroTelefoneEncontradoListener listener;

    public BuscaPrimeiroTelefoneDoAlunoTask(TelefoneDao dao, int alunoId, PrimeiroTelefoneEncontradoListener listener) {
        this.dao = dao;
        this.alunoId = alunoId;
        this.listener = listener;
    }

    @Override
    protected Telefone doInBackground(Void... voids) {
        return dao.buscaPrimeiroTelefoneDoAluno(alunoId);
    }

    @Override
    protected void onPostExecute(Telefone primeiroTelefone) {
        super.onPostExecute(primeiroTelefone);
        listener.quandoEncontrado(primeiroTelefone);
    }

    /*trabalhando com AsyncTask ou demais tarefas que tendam a demorar deveremos evitar o envio de referências que tenham contexto. Caso contrário, a referência pode ser segurada de maneira implícita,
    * mesmo que a referência da Activity, por exemplo, já tenho sido destruída e não seja mais reutilizável
    * O AsyncTask não tendo um listener próprio, aqui é criado um que permitirá avisar que uma ação foi finalizada e não ficará mais com a responsabilidade de atualizar as informações.
    * A AsyncTask só deverá fazer a execução em segundo plano e replicar o resultado disso*/
    public interface PrimeiroTelefoneEncontradoListener {
        void quandoEncontrado(Telefone telefoneEncontrado);    //responsável de verificar quando o primeiro telefone for encontrado
    }
}
