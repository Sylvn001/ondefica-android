package com.example.ondeeisso.api.IBGE;
 class Mesorregiao{
    public int id;
    public String nome;
}
 class Microrregiao{
    public int id;
    public String nome;
    public Mesorregiao mesorregiao;
}

 class UF{
    public int id;
    public String sigla;
    public String nome;
    public Regiao regiao;
}
public class Cidades {

        public int id;
        public String nome;
        public Microrregiao microrregiao;

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

    public Microrregiao getMicrorregiao() {
        return microrregiao;
    }

    public void setMicrorregiao(Microrregiao microrregiao) {
        this.microrregiao = microrregiao;
    }

    @Override
    public String toString() {
        return this.getNome();
    }
}
