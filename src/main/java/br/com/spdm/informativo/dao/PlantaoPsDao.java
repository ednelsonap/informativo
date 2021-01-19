package br.com.spdm.informativo.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.spdm.informativo.model.PlantaoPs;

public class PlantaoPsDao implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;
	
	private DAO<PlantaoPs> dao;
	
	@PostConstruct
	void init(){
		this.dao = new DAO<PlantaoPs>(this.em, PlantaoPs.class);
	}

	public void adiciona(PlantaoPs t) {
		dao.adiciona(t);
	}

	public void remove(PlantaoPs t) {
		dao.remove(t);
	}

	public void atualiza(PlantaoPs t) {
		dao.atualiza(t);
	}

	public List<PlantaoPs> listaTodos() {
		return dao.listaTodos();
	}

	
	
	public PlantaoPs buscaPorId(Integer id) {
		return dao.buscaPorId(id);
	}

	public PlantaoPs buscaPlantaoIdUm() {
		//Plantao plantaoPrincipal = new Plantao();
		String jpql = "select p from PlantaoPs p where p.id=1";
		return em.createQuery(jpql, PlantaoPs.class).getSingleResult();
	}

	public List<PlantaoPs> listar() {
		String jpql = "select distinct(p) from PlantaoPs p "
				+ " join fetch p.medicos where p.id=1";
		
		return em.createQuery(jpql, PlantaoPs.class).getResultList();
	}
}
