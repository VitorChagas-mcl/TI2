package model;

public class Jogo {
    
    private int id;
    private String titulo;
    private float preco;

    public Jogo() {
    }

    public Jogo(int id, String titulo, float preco) {
        this.id = id;
        this.titulo = titulo;
        this.preco = preco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setPreco(float preco) {
        this.preco = preco;
    }

    @Override
    public String toString(){
        return "ID: " + id + "Titulo: " + titulo + "R$" + preco;
    }
    
    @Override
	public boolean equals(Object obj) {
		return (this.getId() == ((Jogo) obj).getId());
	}	
}