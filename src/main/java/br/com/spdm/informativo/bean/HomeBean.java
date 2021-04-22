package br.com.spdm.informativo.bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import br.com.spdm.informativo.dao.PlantaoDao;
import br.com.spdm.informativo.model.Plantao;

@Named
@ViewScoped
public class HomeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PlantaoDao plantaoDao;
	private Plantao plantao = new Plantao();

	public boolean exibirMensagemPs() {
		try {
			this.plantao = plantaoDao.buscaPlantaoPs();
			return false;
		} catch (PersistenceException e){
			return true;
		}
	}

	public boolean exibirMensagemUpa() {
		try {
			this.plantao = plantaoDao.buscaPlantaoUpa();
			return false;
		} catch (PersistenceException e){
			return true;
		}
	}
	
	public Plantao getPlantao() {
		return plantao;
	}

	public void setPlantao(Plantao plantao) {
		this.plantao = plantao;
	}
	
	
}
