package br.com.lucas.feira.juliaapp.Entidade;

import java.io.Serializable;

public class Card implements Serializable{

    private Integer codigo;
    private String titulo;

    public Card(Integer codigo, String titulo) {
        this.codigo = codigo;
        this.titulo = titulo;
    }

    public Card() {
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
