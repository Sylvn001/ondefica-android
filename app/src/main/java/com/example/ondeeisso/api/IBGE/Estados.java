package com.example.ondeeisso.api.IBGE;

import androidx.annotation.NonNull;

public class Estados{
    public int id;
    public String sigla;
    public String nome;
    public Regiao regiao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Regiao getRegiao() {
        return regiao;
    }

    public void setRegiao(Regiao regiao) {
        this.regiao = regiao;
    }

    @NonNull
    @Override
    public String toString() {
        return this.getSigla();
    }
}
