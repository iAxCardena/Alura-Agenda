package br.com.alura.agenda.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.agenda.model.Aluno;

public class AlunoDAOLocal {
    private final static List<Aluno> alunos = new ArrayList<>();
    private static int contadorDeIds = 1;

    public void salvar(Aluno aluno) {
        aluno.setId(contadorDeIds);
        alunos.add(aluno);
        contadorDeIds++;
    }

    public void editar(Aluno aluno) {
        Aluno alunoEncontrado = encontraAluno(aluno);
        if(alunoEncontrado != null) {
            int posicaoAluno = alunos.indexOf(alunoEncontrado);
            alunos.set(posicaoAluno, aluno);
        }
    }

    private Aluno encontraAluno(Aluno aluno) {
        for (Aluno a : alunos) {
            if(a.getId() == aluno.getId()) {
                return a;
            }
        }
        return null;
    }

    public List<Aluno> todos() {
        return new ArrayList<>(alunos);
    }

    public void remover(Aluno aluno) {
        Aluno alunoEncontrado = encontraAluno(aluno);
        if(alunoEncontrado != null) {
            alunos.remove(alunoEncontrado);
        }
    }
}
