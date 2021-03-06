package br.com.spdm.informativo.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import br.com.spdm.informativo.model.Medico;
import br.com.spdm.informativo.util.JPAUtil;

public class MedicoDao implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;
	
	private DAO<Medico> dao;
	
	@PostConstruct
	void init(){
		this.dao = new DAO<Medico>(this.em, Medico.class);
	}
	
	//Métodos Delegados de DAO
	public void adiciona(Medico medico) {
		dao.adiciona(medico);
	}

	public void remove(Medico medico) {
		dao.remove(medico);
	}

	public void atualiza(Medico medico) {
		dao.atualiza(medico);
	}

	public List<Medico> listaTodos() {
		return dao.listaTodos();
	}

	public int contaTodos() {
		return dao.contaTodos();
	}

	public Medico buscaPorId(Integer id) {
		return dao.buscaPorId(id);
	}

	public boolean nomeExiste(Medico medico) {
		
		EntityManager em = new JPAUtil().getEntityManager();
		TypedQuery<Medico> query = em.createQuery(
				  " select m from Medico m "
				+ " where m.nome = :pNome", Medico.class);
		
		query.setParameter("pNome", medico.getNome());
		
		try {
			Medico resultado =  query.getSingleResult();
		} catch (NoResultException ex) {
			return false;
		}
		
		return true;
	}
	
	public boolean crmExiste(Medico medico) {
		
		EntityManager em = new JPAUtil().getEntityManager();
		TypedQuery<Medico> query = em.createQuery(
				  " select m from Medico m "
				+ " where m.crm = :pCrm", Medico.class);
		
		query.setParameter("pCrm", medico.getCrm());
		
		try {
			Medico resultado =  query.getSingleResult();
		} catch (NoResultException ex) {
			return false;
		}
		
		return true;
	}
}
