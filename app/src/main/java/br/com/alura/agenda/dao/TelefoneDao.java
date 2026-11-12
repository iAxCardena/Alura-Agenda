package br.com.alura.agenda.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import br.com.alura.agenda.model.Telefone;

@Dao
public interface TelefoneDao {
    @Query("SELECT * FROM Telefone where alunoId = :alunoId LIMIT 1")
    Telefone buscaPrimeiroTelefoneDoAluno(int alunoId);

    @Insert
    void salva(Telefone... telefones);  //com VAR ARGS é possível mandar vários parâmetros e salvar mais de um aluno e até trabalhar com nomes genéricos

    @Query("SELECT * FROM Telefone where alunoId = :alunoId")
    List<Telefone> buscaTodosTelefonesDoAluno(int alunoId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)    //possui mesmo comportamento da atualização. insere linhas novas e no conflito com uma linha existente, substitui
    void atualiza(Telefone... telefones);
}
