package br.com.alura.agenda.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.alura.agenda.model.Aluno;

@Dao
public interface AlunoDao {
    @Insert //anotações dizem ao Room o que fazer com os dados
    Long salvar(Aluno aluno);

    @Query("SELECT * from aluno")   //anotação para criação manual de queries
    List<Aluno> todos();

    @Delete
    void remover(Aluno aluno);

    @Update
    void editar(Aluno aluno);
}
