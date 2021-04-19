package br.com.spdm.informativo.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import br.com.spdm.informativo.dao.PlantaoDao;
import br.com.spdm.informativo.model.AssistenteSocial;
import br.com.spdm.informativo.model.Medico;
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
		try{
			this.plantao = plantaoDao.buscaPlantaoUpa();
		} catch (PersistenceException e){
			System.out.println("Não foi encontrado um plantão");
		}
	}

	public List<Medico> getMedicosPlantaoPrincipal() {

		List<Medico> medicosPlantao = new ArrayList<>();
		List<Plantao> listPlantao = plantaoDao.listarMedicosPlantaoUpa();

		for (Plantao plantao : listPlantao) {

			for (Medico medico : plantao.getMedicos()) {
				medicosPlantao.add(medico);
			}
		}
		return medicosPlantao;
	}

	public List<AssistenteSocial> getAssistentesSociaisPlantaoPrincipal() {

		List<AssistenteSocial> assistentesSociaisPlantao = new ArrayList<>();
		List<Plantao> listPlantao = plantaoDao.listarAssistentesSociaisPlantaoUpa();

		for (Plantao plantao : listPlantao) {

			for (AssistenteSocial assistenteSocial : plantao.getAssistentesSociais()) {
				assistentesSociaisPlantao.add(assistenteSocial);
			}
		}
		return assistentesSociaisPlantao;
	}
	
	public Plantao getPlantao() {
		return plantao;
	}

}
