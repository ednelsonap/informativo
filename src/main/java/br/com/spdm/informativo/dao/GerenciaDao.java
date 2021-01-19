package br.com.spdm.informativo.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.spdm.informativo.model.Gerencia;

public class GerenciaDao implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em; 
	
	private DAO<Gerencia> dao;
	
	@PostConstruct
	void init(){
		this.dao = new DAO<Gerencia>(this.em, Gerencia.class);
	}

	public void atualiza(Gerencia t) {
		dao.atualiza(t);
	}

	public List<Gerencia> listaTodos() {
		return dao.listaTodos();
	}
	
	public List<Gerencia> listaTodosPaginada(int firstResult, int maxResults, String coluna, String valor) {
		return dao.listaTodosPaginada(firstResult, maxResults, coluna, valor);
	}

	public Gerencia buscaPorId(Integer id) {
		return dao.buscaPorId(id);
	}

	public List<Gerencia> listaGerenciaPs() {
		String jpql = "from Gerencia g where g.id=1";	
		return em.createQuery(jpql, Gerencia.class).getResultList();
	}
	
	public List<Gerencia> listaGerenciaUpa() {
		String jpql = "from Gerencia g where g.id=2";	
		return em.createQuery(jpql, Gerencia.class).getResultList();
	}
	
}
