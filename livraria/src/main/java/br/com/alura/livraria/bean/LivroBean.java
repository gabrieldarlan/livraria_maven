
package br.com.alura.livraria.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import br.com.alura.livraria.dao.DAO;
import br.com.alura.livraria.modelo.Autor;
import br.com.alura.livraria.modelo.Livro;
import br.com.alura.livraria.util.RedirectView;

@ManagedBean
@ViewScoped
public class LivroBean {

	private Livro livro = new Livro();
	private Integer autorId;

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public void gravarAutor() {
		if (autorId != null) {
			Autor autor = new DAO<Autor>(Autor.class).buscaPorId(autorId);
			this.livro.adicionaAutor(autor);
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Selecione um autor"));
		}
	}

	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Livro deve ter pelo menos um Autor."));
		}

		if (this.livro.getId() == null) {
			new DAO<Livro>(Livro.class).adiciona(this.livro);
		} else {
			new DAO<Livro>(Livro.class).atualiza(this.livro);

		}

		limpar();
	}

	public void remover(Livro livro) {
		try {
			new DAO<Livro>(Livro.class).remove(livro);
		} catch (Exception e) {
			throw new RuntimeException("Erro na remoção do livro");
		}
	}

	public void removerAutor(Autor autor) {
		this.livro.remove(autor);
	}
	
	public void alterar(Livro livro) {
		this.livro = livro;
	}

	public void limpar() {
		this.livro = new Livro();
	}

	public List<Autor> getAutores() {
		return new DAO<Autor>(Autor.class).listaTodos();
	}

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public List<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}

	public List<Livro> getLivros() {
		return new DAO<Livro>(Livro.class).listaTodos();
	}

	public void comecaComDigitoUm(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
		String valor = value.toString();
		if (!valor.startsWith("1")) {
			throw new ValidatorException(new FacesMessage("Deveria começar com 1!"));
		}
	}

	public RedirectView formAutor() {
		return new RedirectView("autor");
	}
}
