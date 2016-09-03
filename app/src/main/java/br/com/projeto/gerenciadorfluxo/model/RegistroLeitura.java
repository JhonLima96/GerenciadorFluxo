package br.com.projeto.gerenciadorfluxo.model;

import java.sql.Time;
import java.util.Date;

/**
 * Created by Jonathan on 31/08/2016.
 */
public class RegistroLeitura {
    private int id;
    private String nome;
    private String data;
    private String hora;

    public RegistroLeitura(){

    }

    public RegistroLeitura(int id, String nome, String data, String hora) {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.hora = hora;
    }
    public int getId() {

        return id;
    }

    public String getNome() {

        return nome;
    }

    public String getData() {
        return data;
    }

    public String getHora() {
        return hora;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

}
