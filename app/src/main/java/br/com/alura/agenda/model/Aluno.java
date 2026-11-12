package br.com.alura.agenda.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

@Entity
public class Aluno implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id = 0;
    private String nome;
//    private String sobrenome;
    private String email;
    private Calendar momentoCadastro = Calendar.getInstance();

    @Ignore
    public Aluno(String nome, String telefoneFixo, String email) {
        this.nome = nome;
//        this.telefoneFixo = telefoneFixo;
        this.email = email;
    }

    public Aluno() {

    }

    //    public String getTelefoneCelular() {
//        return telefoneCelular;
//    }
//
//    public void setTelefoneCelular(String telefoneCelular) {
//        this.telefoneCelular = telefoneCelular;
//    }

    public Calendar getMomentoCadastro() {
        return momentoCadastro;
    }

    public void setMomentoCadastro(Calendar momentoCadastro) {
        this.momentoCadastro = momentoCadastro;
    }

//    public String getSobrenome() {
//        return sobrenome;
//    }
//
//    public void setSobrenome(String sobrenome) {
//        this.sobrenome = sobrenome;
//    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

//    public void setTelefoneFixo(String telefoneFixo) {
//        this.telefoneFixo = telefoneFixo;
//    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

//    public String getTelefoneFixo() {
//        return telefoneFixo;
//    }

    public String getEmail() {
        return email;
    }

    @NonNull
    @Override
    public String toString() {
        return nome;
    }

    public boolean temIdValido() {
        return id > 0;
    }

//    public String getNomeCompleto() {
//        return nome + " " + sobrenome;
//        return nome;
//    }

//    public String dataFormatada() {
//        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
//        return formato.format(momentoCadastro.getTime());
//    }
}
