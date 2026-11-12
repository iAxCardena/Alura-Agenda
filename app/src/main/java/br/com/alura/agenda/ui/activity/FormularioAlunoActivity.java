package br.com.alura.agenda.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import br.com.alura.agenda.R;
import br.com.alura.agenda.asyncTask.BuscaTodosTelefonesDoAluno;
import br.com.alura.agenda.asyncTask.EditaAlunoTask;
import br.com.alura.agenda.asyncTask.SalvaAlunoTask;
import br.com.alura.agenda.dao.AlunoDao;
import br.com.alura.agenda.dao.TelefoneDao;
import br.com.alura.agenda.database.AgendaDatabase;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.model.Telefone;
import br.com.alura.agenda.model.TipoTelefone;

public class FormularioAlunoActivity extends AppCompatActivity {

    public static final String TITLE_APPBAR_NOVO_ALUNO = "Novo Aluno";
    private static final String TITLE_APPBAR_EDITAR_ALUNO = "Editar Aluno";
    public static final String CHAVE_ALUNO = "aluno";
    private EditText campoNome;
//    private EditText alunoSobrenome;
    private EditText campoTelefoneFixo;
    private EditText campoTelefoneCelular;
    private EditText campoEmail;
    private AlunoDao alunoDAO;
    private TelefoneDao telefoneDAO;
    private Aluno aluno;
    private AgendaDatabase database;
    private List<Telefone> telefonesDoAluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);

        database = AgendaDatabase.getInstance(this);
        alunoDAO = database.getAlunoDAO();
        telefoneDAO = database.getTelefoneDao();
        inicializaCampos();
//        configuraBotaoSalvar();
        carregaAluno();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_formulario_aluno_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.activity_formulario_aluno_menu_salvar) {
            finalizaFormulario();
        }

        return super.onOptionsItemSelected(item);
    }

    private void carregaAluno() {
        if(getIntent().hasExtra(CHAVE_ALUNO)) {
            setTitle(TITLE_APPBAR_EDITAR_ALUNO);
            aluno = (Aluno) getIntent().getSerializableExtra(CHAVE_ALUNO);
            preencheCampos();
        } else {
            setTitle(TITLE_APPBAR_NOVO_ALUNO);
            aluno = new Aluno();
        }
    }

    private void preencheCampos() {
        campoNome.setText(aluno.getNome());
        campoEmail.setText(aluno.getEmail());
        preencheCamposDeTelefone();
    }

    private void preencheCamposDeTelefone() {
        new BuscaTodosTelefonesDoAluno(telefoneDAO, aluno, telefones -> {
            this.telefonesDoAluno = telefones;
            for(Telefone telefone : telefonesDoAluno) {
                if(telefone.getTipo() == TipoTelefone.FIXO) {
                    campoTelefoneFixo.setText(telefone.getNumero());
                } else {
                    campoTelefoneCelular.setText(telefone.getNumero());
                }
            }
        }).execute();
    }

    private void finalizaFormulario() {
        preencheAluno();

        Telefone telefoneFixo = criaTelefone(campoTelefoneFixo, TipoTelefone.FIXO);
        Telefone telefoneCelular = criaTelefone(campoTelefoneCelular, TipoTelefone.CELULAR);

        if(aluno.temIdValido()) {
            editaAluno(telefoneFixo, telefoneCelular);
        } else {
            salvaAluno(telefoneFixo, telefoneCelular);
        }
    }

    private Telefone criaTelefone(EditText campoTelefoneFixo, TipoTelefone fixo) {
        String numeroFixo = campoTelefoneFixo.getText().toString();
        return new Telefone(numeroFixo, fixo);
    }

    private void salvaAluno(Telefone telefoneFixo, Telefone telefoneCelular) {
        /*finalizar o formulário fora da Async Task, isto é, fora do processo que ocorre em paralelo, não é uma boa prática.
        logo foi criado um listener para que fosse finalizado assim que todas as operações estivessem prontas */
        new SalvaAlunoTask(alunoDAO, telefoneDAO, aluno, telefoneFixo, telefoneCelular, this::finish).execute();
    }

    private void editaAluno(Telefone telefoneFixo, Telefone telefoneCelular) {
        new EditaAlunoTask(alunoDAO, telefoneDAO, aluno, telefoneFixo, telefoneCelular, telefonesDoAluno, this::finish).execute();
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

    private void vinculaAlunoComTelefone(int alunoId, Telefone... telefones) {
        for (Telefone telefone : telefones) {
            telefone.setAlunoId(alunoId);
        }
    }

    private void inicializaCampos() {
        campoNome = findViewById(R.id.activity_formulario_aluno_nome);
        campoTelefoneFixo = findViewById(R.id.activity_formulario_aluno_telefone_fixo);
        campoTelefoneCelular = findViewById(R.id.activity_formulario_aluno_telefone_celular);
        campoEmail = findViewById(R.id.activity_formulario_aluno_email);
    }

    private void preencheAluno() {
        String nome = campoNome.getText().toString();
        String telefoneFixo = campoTelefoneFixo.getText().toString();
        String telefoneCelular = campoTelefoneCelular.getText().toString();
        String email = campoEmail.getText().toString();

        aluno.setNome(nome);
        aluno.setEmail(email);
    }
}