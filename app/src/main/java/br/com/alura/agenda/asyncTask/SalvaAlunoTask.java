package br.com.alura.agenda.asyncTask;

import br.com.alura.agenda.dao.AlunoDao;
import br.com.alura.agenda.dao.TelefoneDao;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.model.Telefone;

public class SalvaAlunoTask extends BaseAlunoComTelefone {
    private final AlunoDao alunoDAO;
    private final TelefoneDao telefoneDAO;
    private final Aluno aluno;
    private final Telefone telefoneFixo;
    private final Telefone telefoneCelular;

    public SalvaAlunoTask(AlunoDao alunoDAO, TelefoneDao telefoneDAO, Aluno aluno, Telefone telefoneFixo, Telefone telefoneCelular, FinalizadaListener listener) {
        super(listener);
        this.alunoDAO = alunoDAO;
        this.telefoneDAO = telefoneDAO;
        this.aluno = aluno;
        this.telefoneFixo = telefoneFixo;
        this.telefoneCelular = telefoneCelular;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        int alunoId = alunoDAO.salvar(aluno).intValue();
        vinculaAlunoComTelefone(alunoId, telefoneFixo, telefoneCelular);
        telefoneDAO.salva(telefoneFixo, telefoneCelular);
        return null;
    }
}
