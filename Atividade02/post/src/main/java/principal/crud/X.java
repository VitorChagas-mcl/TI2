package principal.crud;

public class X {
    
    private int id_jogo;
    private String titulo;
    private double preco;

    public X() {
    }

    public X(int id_jogo, String titulo, double preco) {
        this.id_jogo = id_jogo;
        this.titulo = titulo;
        this.preco = preco;
    }

    public int getId_jogo() {
        return id_jogo;
    }

    public void setId_jogo(int id_jogo) {
        this.id_jogo = id_jogo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public String toString(){
        return "ID: " + id_jogo + "Titulo: " + titulo + "R$" + preco;
    }
}
