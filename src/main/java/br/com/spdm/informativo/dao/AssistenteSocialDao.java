package br.com.spdm.informativo.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.spdm.informativo.model.AssistenteSocial;
import br.com.spdm.informativo.util.JPAUtil;

public class AssistenteSocialDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	private DAO<AssistenteSocial> dao;

	@PostConstruct
	void init() {
		this.dao = new DAO<AssistenteSocial>(this.em, AssistenteSocial.class);
	}

	public void remove(AssistenteSocial t) {
		dao.remove(t);
	}

	public void adiciona(AssistenteSocial t) {
		dao.adiciona(t);
	}

	public void atualiza(AssistenteSocial t) {
		dao.atualiza(t);
	}

	public List<AssistenteSocial> listaTodos() {
		return dao.listaTodos();
	}

	public AssistenteSocial buscaPorId(Integer id) {
		return dao.buscaPorId(id);
	}

	public boolean nomeExiste(AssistenteSocial assistenteSocial) {

		EntityManager em = new JPAUtil().getEntityManager();
		TypedQuery<AssistenteSocial> query = em
				.createQuery(" select a from AssistenteSocial a " + " where a.nome = :pNome", AssistenteSocial.class);

		query.setParameter("pNome", assistenteSocial.getNome());

		try {
			AssistenteSocial resultado = query.getSingleResult();
		} catch (NoResultException ex) {
			return false;
		}

		return true;
	}

	public boolean cressExiste(AssistenteSocial assistenteSocial) {

		EntityManager em = new JPAUtil().getEntityManager();
		TypedQuery<AssistenteSocial> query = em
				.createQuery(" select a from AssistenteSocial a " + " where a.cress = :pCress", AssistenteSocial.class);

		query.setParameter("pCress", assistenteSocial.getCress());

		try {
			AssistenteSocial resultado = query.getSingleResult();
		} catch (NoResultException ex) {
			return false;
		}

		return true;
	}

}
