package br.com.spdm.informativo.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.spdm.informativo.dao.PlantaoDao;
import br.com.spdm.informativo.model.Plantao;

@Named
@ConversationScoped
public class PainelUpaBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private PlantaoDao plantaoDao;
	
	private Plantao plantao;
	
	@PostConstruct
	public void init(){
		this.plantao = plantaoDao.buscaPlantaoUpa();
	}

	public Plantao getPlantao() {
		return plantao;
	}

}
