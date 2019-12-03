package com.usjt.beehealthy.Model;

import java.io.Serializable;
import java.util.Date;

public class Usuario implements Serializable {

    private int id;
    private int foto;
    private String nome;
    private String email;
    private String senha;
    private String tipo;
    private String nascimento;


    public Usuario(int Id, String nome, String email, String senha, String tipo, String nascimento) {
        this.id = Id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
        this.nascimento = nascimento;
    }

    public Usuario(String nome, String email, String senha, String tipo, String nascimento) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
        this.nascimento = nascimento;
    }

    public Usuario() {

    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getFoto() {
        return foto;
    }


    public void setFoto(int foto) {
        this.foto = foto;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }
}
