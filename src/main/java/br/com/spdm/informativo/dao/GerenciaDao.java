package br.com.spdm.informativo.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

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

	
	public void adiciona(Gerencia t) {
		dao.adiciona(t);
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

	/*public List<Gerencia> listaGerenciaPs() {
		String jpql = "from Gerencia g where g.id=1";	
		return em.createQuery(jpql, Gerencia.class).getResultList();
	}
	
	public List<Gerencia> listaGerenciaUpa() {
		String jpql = "from Gerencia g where g.id=2";	
		return em.createQuery(jpql, Gerencia.class).getResultList();
	}*/

	public Gerencia buscaGerenciaDoPs() {
		String jpql = "from Gerencia g where g.id=1";	
		return em.createQuery(jpql, Gerencia.class).getSingleResult();
	}

	public boolean gerenciaExistente(Gerencia gerencia) {
		

		TypedQuery<Gerencia> query = em.createQuery(
				" select g from Gerencia g " + " where g.unidade = :pUnidade", Gerencia.class);

		query.setParameter("pUnidade", gerencia.getUnidade());

		try {
			Gerencia resultado = query.getSingleResult();
		} catch (NoResultException ex) {
			return false;
		}

		//em.close();

		return true;
	}
	
}
