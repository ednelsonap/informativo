package br.com.spdm.informativo.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.spdm.informativo.model.Especialidade;

public class EspecialidadeDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;
	
	private DAO<Especialidade> dao;
	
	@PostConstruct
	void init(){
		this.dao = new DAO<Especialidade>(this.em, Especialidade.class);
	}

	public void adiciona(Especialidade t) {
		dao.adiciona(t);
	}

	public void remove(Especialidade t) {
		dao.remove(t);
	}

	public void atualiza(Especialidade t) {
		dao.atualiza(t);
	}

	public List<Especialidade> listaTodos() {
		return dao.listaTodos();
	}

	public Especialidade buscaPorId(Integer id) {
		return dao.buscaPorId(id);
	}
	
	
}
