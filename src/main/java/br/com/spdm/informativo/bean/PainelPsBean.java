package br.com.spdm.informativo.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.spdm.informativo.dao.GerenciaDao;
import br.com.spdm.informativo.model.Gerencia;

@Named
@ConversationScoped
public class PainelPsBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private GerenciaDao gerenciaDao;
	
	private Gerencia gerencia;
	
	@PostConstruct
	public void init(){
		this.gerencia = gerenciaDao.buscaGerenciaDoPs();
	}

	public Gerencia getGerencia() {
		return gerencia;
	}

}
