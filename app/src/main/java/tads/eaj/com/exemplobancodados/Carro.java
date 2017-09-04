package tads.eaj.com.exemplobancodados;

/**
 * Created by Taniro on 06/11/2016.
 */
public class Carro {

    public Carro() {

    }

    public Carro(int ano, String nome, String tipo, String desc) {

        this.ano = ano;
        this.nome = nome;
        this.tipo = tipo;
        this.desc = desc;
        this.id = 0;
    }

    private long id;
    private String nome;
    private String tipo;
    private String desc;
    private int ano;

    public long getId() {
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    @Override
    public String toString() {
        return "Carro{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", tipo='" + tipo + '\'' +
                ", desc='" + desc + '\'' +
                ", ano=" + ano +
                '}';
    }
}

