package br.com.spdm.informativo.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.primefaces.PrimeFaces;

import br.com.spdm.informativo.dao.AssistenteSocialDao;
import br.com.spdm.informativo.dao.MedicoDao;
import br.com.spdm.informativo.dao.PlantaoDao;
import br.com.spdm.informativo.model.AssistenteSocial;
import br.com.spdm.informativo.model.Medico;
import br.com.spdm.informativo.model.Plantao;

@Named
@ViewScoped
public class PlantaoPsBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext context;
	@Inject
	private PlantaoDao plantaoDao;
	@Inject
	private MedicoDao medicoDao;
	@Inject
	private AssistenteSocialDao assistenteSocialDao;

	private Integer medicoId;
	private Integer assistenteSocialId;
	private Plantao plantao = new Plantao();
	private Medico medico = new Medico();
	private AssistenteSocial assistenteSocial = new AssistenteSocial();
	
	//lista os médicos do selectOneMenu da tela de vínculo
	public List<Medico> getMedicos() {
		return this.medicoDao.listaTodos();
	}
	
	//lista os médicos do selectOneMenu da tela de vínculo
	public List<AssistenteSocial> getAssistentesSociais() {
		return this.assistenteSocialDao.listaTodos();
	}

	/*public void gravarMedico() {
		Medico medico = this.medicoDao.buscaPorId(this.medicoId);
		this.plantao.adicionaMedico(medico);
		System.out.println("Gravando médico " + medico.getNome() + " no plantão.");
	}

	public void gravarAssistenteSocial() {
		AssistenteSocial assistenteSocial = this.assistenteSocialDao.buscaPorId(this.assistenteSocialId);
		this.plantao.adicionaAssistenteSocial(assistenteSocial);
		System.out.println("Gravando Assistente Social " + assistenteSocial.getNome() + " no plantão.");
	}*/

	@Transactional
	public void vincularMedicoAoPlantao() {

		this.medico = this.medicoDao.buscaPorId(this.medicoId);

		try {
			this.plantao = this.plantaoDao.buscaPlantaoPs();
		} catch (PersistenceException e) {
			e.getMessage();
		}

		if (this.plantao.getId() == null) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Não foi encontrado um plantão da unidade PS!", null));

		} else if (this.plantao.getMedicos().contains(medico)) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Médico já vinculado ao plantão!", null));

		} else {
			this.plantao.adicionaMedico(medico);
			this.plantaoDao.atualiza(this.plantao);
			context.addMessage(null, new FacesMessage("Vinculado com sucesso!"));
		}
	}

	@Transactional
	public void vincularAssistenteSocialAoPlantao() {

		this.assistenteSocial = this.assistenteSocialDao.buscaPorId(this.assistenteSocialId);

		try {
			this.plantao = this.plantaoDao.buscaPlantaoPs();
		} catch (PersistenceException e) {
			e.getMessage();
		}

		if (this.plantao.getId() == null) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Não foi encontrado um plantão da unidade PS!", null));

		} else if (this.plantao.getAssistentesSociais().contains(this.assistenteSocial)) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Assistente Social já vinculado ao plantão!", null));

		} else {
			this.plantao.adicionaAssistenteSocial(this.assistenteSocial);
			this.plantaoDao.atualiza(this.plantao);
			context.addMessage(null, new FacesMessage("Vinculado com sucesso!"));
		}
	}

	@Transactional
	public void removerMedicoDoPlantao(Medico medico) {
		this.plantao = plantaoDao.buscaPlantaoPs();
		this.plantao.removeMedico(medico);
		plantaoDao.atualiza(this.plantao);
	}

	@Transactional
	public void removerAssistenteSocialDoPlantao(AssistenteSocial assistenteSocial) {
		this.plantao = plantaoDao.buscaPlantaoPs();
		this.plantao.removeAssistenteSocial(assistenteSocial);
		plantaoDao.atualiza(this.plantao);
	}

	public List<Medico> getMedicosDoPlantao() {
		return this.plantao.getMedicos();
	}

	public List<AssistenteSocial> getAssistentesSociaisDoPlantao() {
		return this.plantao.getAssistentesSociais();
	}

	//lista de médicos vinculados ao plantao do ps
	public List<Medico> getMedicosPlantaoPs() {

		List<Medico> medicosPlantaoPs = new ArrayList<>();
		List<Plantao> listaPlantao = plantaoDao.listarMedicosPlantaoPs();

		for (Plantao plantao : listaPlantao) {

			for (Medico medico : plantao.getMedicos()) {
				medicosPlantaoPs.add(medico);
			}
		}
		return medicosPlantaoPs;
	}
	
	public List<AssistenteSocial> getAssistentesSociaisPlantaoPs() {

		List<AssistenteSocial> assistentesSociaisPlantaoPs = new ArrayList<>();
		List<Plantao> listaPlantao = plantaoDao.listarAssistentesSociaisPlantaoPs();

		for (Plantao plantao : listaPlantao) {

			for (AssistenteSocial assistenteSocial : plantao.getAssistentesSociais()) {
				assistentesSociaisPlantaoPs.add(assistenteSocial);
			}
		}
		return assistentesSociaisPlantaoPs;
	}

	public Plantao getPlantaoPrincipal() {
		try {
			Plantao plantaoPrincipal = plantaoDao.buscaPlantaoPs();
			return plantaoPrincipal;
		} catch (Exception e) {
			return null;
		}
	}

	public void limparMedico() {
		this.plantao = new Plantao();
		this.medico = new Medico();
		this.medicoId = null;
		PrimeFaces.current().resetInputs("formVinculoMedico:selectMedico");
	}
	
	public void limparAssistenteSocial() {
		this.plantao = new Plantao();
		this.assistenteSocial = new AssistenteSocial();
		this.assistenteSocialId = null;
		PrimeFaces.current().resetInputs("formVinculoAssistenteSocial:selectAssistenteSocial");
	}

	// getters and setters
	public Plantao getPlantao() {
		return plantao;
	}

	public void setPlantao(Plantao plantao) {
		this.plantao = plantao;
	}

	public Integer getMedicoId() {
		return medicoId;
	}

	public void setMedicoId(Integer medicoId) {
		this.medicoId = medicoId;
	}

	public Integer getAssistenteSocialId() {
		return assistenteSocialId;
	}

	public void setAssistenteSocialId(Integer assistenteSocialId) {
		this.assistenteSocialId = assistenteSocialId;
	}
}
