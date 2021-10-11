package com.br.systock.model;

public class Type {

    private String descricao;

    private String status;

    public Type(String descricao, String status) {

        this.descricao = descricao;
        this.status = status;
    }

    public Type() {
    }

    public Type(String s, String toString, String string, String s1){}

    public String getdescricao() {
        return this.descricao;
    }

    public String getStatus() { return this.status; }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
