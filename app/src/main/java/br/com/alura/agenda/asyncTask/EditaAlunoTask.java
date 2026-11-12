package br.com.alura.agenda.asyncTask;

import java.util.List;

import br.com.alura.agenda.dao.AlunoDao;
import br.com.alura.agenda.dao.TelefoneDao;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.model.Telefone;
import br.com.alura.agenda.model.TipoTelefone;

public class EditaAlunoTask extends BaseAlunoComTelefone {
    public final AlunoDao alunoDAO;
    public final TelefoneDao telefoneDAO;
    public final Aluno aluno;
    public final Telefone telefoneFixo;
    public final Telefone telefoneCelular;
    public final List<Telefone> telefonesDoAluno;

    public EditaAlunoTask(AlunoDao alunoDAO, TelefoneDao telefoneDAO, Aluno aluno, Telefone telefoneFixo, Telefone telefoneCelular, List<Telefone> telefonesDoAluno, FinalizadaListener listener) {
        super(listener);
        this.alunoDAO = alunoDAO;
        this.telefoneDAO = telefoneDAO;
        this.aluno = aluno;
        this.telefoneFixo = telefoneFixo;
        this.telefoneCelular = telefoneCelular;
        this.telefonesDoAluno = telefonesDoAluno;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        alunoDAO.editar(aluno);
        vinculaAlunoComTelefone(aluno.getId(), telefoneFixo, telefoneCelular);
        atualizaIdsDosTelefones(telefoneFixo, telefoneCelular);
        telefoneDAO.atualiza(telefoneFixo, telefoneCelular);
        return null;
    }

    private void atualizaIdsDosTelefones(Telefone telefoneFixo, Telefone telefoneCelular) {
        for (Telefone telefone : telefonesDoAluno) {
            if (telefone.getTipo() == TipoTelefone.FIXO){
                telefoneFixo.setId(telefone.getId());
            } else {
                telefoneCelular.setId(telefone.getId());
            }
        }
    }
}
