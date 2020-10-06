package br.com.spdm.informativo.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.spdm.informativo.model.Plantao;

public class PlantaoDao implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;
	
	private DAO<Plantao> dao;
	
	@PostConstruct
	void init(){
		this.dao = new DAO<Plantao>(this.em, Plantao.class);
	}

	public void adiciona(Plantao t) {
		dao.adiciona(t);
	}

	public void remove(Plantao t) {
		dao.remove(t);
	}

	public void atualiza(Plantao t) {
		dao.atualiza(t);
	}

	public List<Plantao> listaTodos() {
		return dao.listaTodos();
	}

	public List<Plantao> listar() {
		String jpql = "select distinct(p) from Plantao p "
				+ " join fetch p.medicos where p.id=1";
		
		return em.createQuery(jpql, Plantao.class).getResultList();
	}
}
