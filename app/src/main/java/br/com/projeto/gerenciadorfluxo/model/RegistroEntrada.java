package br.com.projeto.gerenciadorfluxo.model;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jonathan on 31/08/2016.
 */
public class RegistroEntrada {
    private long ra;
    private String nome_aluno;
    private String data_entrada;
    private String hora_entrada;
    private String tipo_entrada;
    private String hora_saida;

    public RegistroEntrada(long ra, String nome_aluno, String data_entrada, String hora_entrada, String tipo_entrada, String hora_saida) {
        this.ra = ra;
        this.nome_aluno = nome_aluno;
        this.data_entrada = data_entrada;
        this.hora_entrada = hora_entrada;
        this.tipo_entrada = tipo_entrada;
        this.hora_saida = hora_saida;
    }

    public RegistroEntrada(){
        /*Método construtor vazio, essencial para o Firebase*/
    }

    public long getRa() {
        return ra;
    }

    public String getNome_aluno() {
        return nome_aluno;
    }

    public String getData_entrada() {
        return data_entrada;
    }

    public String getHora_entrada() {
        return hora_entrada;
    }

    public String getTipo_entrada() {
        return tipo_entrada;
    }

    public String getHora_saida() {
        return hora_saida;
    }

    public void setRa(long ra) {
        this.ra = ra;
    }

    public void setNome_aluno(String nome_aluno) {
        this.nome_aluno = nome_aluno;
    }

    public void setData_entrada(String data_entrada) {
        this.data_entrada = data_entrada;
    }

    public void setHora_entrada(String hora_entrada) {
        this.hora_entrada = hora_entrada;
    }

    public void setTipo_entrada(String tipo_entrada) {
        this.tipo_entrada = tipo_entrada;
    }

    public void setHora_saida(String hora_saida) {
        this.hora_saida = hora_saida;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ra", ra);
        result.put("nome_aluno", nome_aluno);
        result.put("data_entrada", data_entrada);
        result.put("hora_entrada", hora_entrada);
        result.put("tipo_entrada", tipo_entrada);
        result.put("hora_saida", hora_saida);
        return result;
    }
}
