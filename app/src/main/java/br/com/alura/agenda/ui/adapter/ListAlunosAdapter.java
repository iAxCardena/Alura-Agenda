package br.com.alura.agenda.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.agenda.R;
import br.com.alura.agenda.asyncTask.BuscaPrimeiroTelefoneDoAlunoTask;
import br.com.alura.agenda.dao.TelefoneDao;
import br.com.alura.agenda.database.AgendaDatabase;
import br.com.alura.agenda.model.Aluno;

public class ListAlunosAdapter extends BaseAdapter {
    private final List<Aluno> alunos = new ArrayList<>();   //DataSet, lista interna do Adapter
    private final Context context;
    private final TelefoneDao dao;

    public ListAlunosAdapter(Context context) {
        this.context = context;
        dao = AgendaDatabase.getInstance(context).getTelefoneDao();
    }

    //representa a quantidade de elementos do adapter
    @Override
    public int getCount() {
        return alunos.size();
    }

    //retorna o elemento pela posição
    @Override
    public Aluno getItem(int posicao) {
        return alunos.get(posicao);
    }

    //retornar o id do elemento pela posição
    @Override
    public long getItemId(int posicao) {
        return alunos.get(posicao).getId();
    }

    //cria a view para cada elemento
    @Override
    public View getView(int posicao, View view, ViewGroup viewGroup) {
        View viewCriada = criaView(viewGroup);
        Aluno alunoRecebido = alunos.get(posicao);

        vinculaInformacoes(viewCriada, alunoRecebido);

        return viewCriada;
    }

    private void vinculaInformacoes(View viewCriada, Aluno alunoRecebido) {
        TextView alunoNome = viewCriada.findViewById(R.id.item_aluno_nome);
        TextView alunoTelefone = viewCriada.findViewById(R.id.item_aluno_telefone);
        alunoNome.setText(alunoRecebido.getNome());
        new BuscaPrimeiroTelefoneDoAlunoTask(dao, alunoRecebido.getId(), telefone -> {  //removendo a responsabilidade do AsyncTask de atualizar informações chamando um listener próprio
            if(telefone != null) {
                alunoTelefone.setText(telefone.getNumero());
            }
        }).execute();
    }

    private View criaView(ViewGroup viewGroup) {
        /*Inflar um layout diretamente em um LinearLayout ocorre sem problemas. Mas utilizando AdapterViews, que delegam a responsabilidade do processo de vinculação dos itens aos Adapters,
         * acabam gerando uma Exception. Portanto é necessário indicar que o processo de inflate não realizará o comportamento padrão, adicionando um argumento 'false' que automaticamente gerará 'attachToRoot'*/
        return LayoutInflater.from(context).inflate(R.layout.item_aluno, viewGroup, false);
    }

    public void remove(Aluno aluno) {
        alunos.remove(aluno);
        notifyDataSetChanged(); //notifica a modificação do DataSet e atualiza tela (?)
    }

    public void atualizaLista(List<Aluno> alunos) {
        this.alunos.clear();
        this.alunos.addAll(alunos);
        notifyDataSetChanged();
    }
}
