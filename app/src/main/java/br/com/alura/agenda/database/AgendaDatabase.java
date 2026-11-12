package br.com.alura.agenda.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import br.com.alura.agenda.dao.AlunoDao;
import br.com.alura.agenda.dao.TelefoneDao;
import br.com.alura.agenda.database.converter.CalendarConverter;
import br.com.alura.agenda.database.converter.TelefoneConverter;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.model.Telefone;

import static br.com.alura.agenda.database.AgendaMigrations.MIGRATIONS;

@Database(entities = {Aluno.class, Telefone.class}, version = 6, exportSchema = false)
@TypeConverters({CalendarConverter.class, TelefoneConverter.class})  /*indica ao banco que qualquer operação feita terá uma conversão para um tipo especifico. aqui é passado um array com conversores disponíveis*/
public abstract class AgendaDatabase extends RoomDatabase {

    public static final String NOME_BANCO_DE_DADOS = "agenda.db";

    public abstract AlunoDao getAlunoDAO(); //metodo abstrato que devolve uma instância do RoomAlunoDao. o Room consegue identificar que é um DAO já conhecido por conta da anotação

    public abstract TelefoneDao getTelefoneDao();

    public static AgendaDatabase getInstance(Context context) {
        return Room.databaseBuilder(context, AgendaDatabase.class, NOME_BANCO_DE_DADOS).addMigrations(MIGRATIONS).build();
    }
}
