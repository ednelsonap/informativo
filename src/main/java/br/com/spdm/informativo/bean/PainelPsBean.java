package br.com.spdm.informativo.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import br.com.spdm.informativo.dao.PlantaoDao;
import br.com.spdm.informativo.model.Plantao;

@Named
@ConversationScoped
public class PainelPsBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private PlantaoDao plantaoDao;
	
	private Plantao plantao;
	
	@PostConstruct
	public void init(){
		try{
			this.plantao = plantaoDao.buscaPlantaoPs();
		} catch (PersistenceException e){
			System.out.println("Não foi encontrado um plantão");
		}
	}

	public Plantao getPlantao() {
		return plantao;
	}

}
