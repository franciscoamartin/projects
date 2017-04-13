package com.example.chico.minhaagenda;

import java.io.Serializable;

/**
 * Created by Chico on 07/04/2017.
 */
public class Contato implements Serializable{

    private String nome;
    private String email;
    private String site;
    private String telefone;
    private String endereco;
    private String foto;

    private Long id;

    @Override
    public String toString() {
        return "(" + id + ")" + nome;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSite() {
        return site;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getFoto() {
        return foto;
    }

    public Long getId() {
        return id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
