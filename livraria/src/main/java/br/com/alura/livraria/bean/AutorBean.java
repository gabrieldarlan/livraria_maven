package br.com.alura.livraria.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.alura.livraria.dao.DAO;
import br.com.alura.livraria.modelo.Autor;

@ManagedBean
@ViewScoped
public class AutorBean {
	private Autor autor = new Autor();

	public Autor getAutor() {
		return autor;
	}

	public String gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());

		new DAO<Autor>(Autor.class).adiciona(this.autor);
		this.autor = new Autor();
		
		return "livro?faces-redirect=true";
	}
}