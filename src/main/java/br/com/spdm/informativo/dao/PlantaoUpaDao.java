package br.com.spdm.informativo.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.spdm.informativo.model.PlantaoUpa;

public class PlantaoUpaDao implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;
	
	private DAO<PlantaoUpa> dao;
	
	@PostConstruct
	void init(){
		this.dao = new DAO<PlantaoUpa>(this.em, PlantaoUpa.class);
	}

	public void adiciona(PlantaoUpa t) {
		dao.adiciona(t);
	}

	public void remove(PlantaoUpa t) {
		dao.remove(t);
	}

	public void atualiza(PlantaoUpa t) {
		dao.atualiza(t);
	}

	public List<PlantaoUpa> listaTodos() {
		return dao.listaTodos();
	}

	
	
	public PlantaoUpa buscaPorId(Integer id) {
		return dao.buscaPorId(id);
	}

	public PlantaoUpa buscaPlantaoIdUm() {
		//Plantao plantaoPrincipal = new Plantao();
		String jpql = "select p from PlantaoUpa p where p.id=1";
		return em.createQuery(jpql, PlantaoUpa.class).getSingleResult();
	}

	public List<PlantaoUpa> listar() {
		String jpql = "select distinct(p) from PlantaoUpa p "
				+ " join fetch p.medicos where p.id=1";
		
		return em.createQuery(jpql, PlantaoUpa.class).getResultList();
	}
}
