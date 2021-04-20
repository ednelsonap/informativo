package br.com.spdm.informativo.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.spdm.informativo.model.Usuario;

public class UsuarioDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	private DAO<Usuario> dao;

	@PostConstruct
	void init() {
		this.dao = new DAO<Usuario>(this.em, Usuario.class);
	}

	public Usuario getUsuario(String nomeUsuario, String senha) {

	      try {
	        Usuario usuario = (Usuario) em
	         .createQuery(
	             "SELECT u from Usuario u where u.nomeUsuario = :pName and u.senha = :pSenha")
	         .setParameter("pName", nomeUsuario)
	         .setParameter("pSenha", senha).getSingleResult();

	        return usuario;
	      } catch (NoResultException e) {
	            return null;
	      }
	      
	}

	public void adiciona(Usuario t) {
		dao.adiciona(t);
	}

	public void remove(Usuario t) {
		dao.remove(t);
	}

	public void atualiza(Usuario t) {
		dao.atualiza(t);
	}

	public List<Usuario> listaTodos() {
		return dao.listaTodos();
	}

	public Usuario buscaPorId(Integer id) {
		return dao.buscaPorId(id);
	}

}
