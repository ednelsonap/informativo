package br.com.spdm.informativo.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import br.com.spdm.informativo.dao.AssistenteSocialDao;
import br.com.spdm.informativo.dao.MedicoDao;
import br.com.spdm.informativo.dao.PlantaoPsDao;
import br.com.spdm.informativo.model.AssistenteSocial;
import br.com.spdm.informativo.model.Medico;
import br.com.spdm.informativo.model.Plantao;

@Named
@ViewScoped
public class PlantaoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext context;
	@Inject
	private PlantaoPsDao plantaoDao;
	@Inject
	private MedicoDao medicoDao;
	@Inject
	private AssistenteSocialDao assistenteSocialDao;
	private Integer medicoId;
	private Integer assistenteSocialId;
	private Plantao plantaoPs = new Plantao();
	

	public List<Medico> getMedicos() {
		return this.medicoDao.listaTodos();
	}

	public List<AssistenteSocial> getAssistentesSociais() {
		return this.assistenteSocialDao.listaTodos();
	}
	
	public void gravarMedico() {
		Medico medico = this.medicoDao.buscaPorId(this.medicoId);
		this.plantaoPs.adicionaMedico(medico);
		System.out.println("Gravando médico " + medico.getNome() + " no plantão.");
	}

	public void gravarAssistenteSocial() {
		AssistenteSocial assistenteSocial = this.assistenteSocialDao.buscaPorId(this.assistenteSocialId);
		this.plantaoPs.adicionaAssistenteSocial(assistenteSocial);
		System.out.println("Gravando Assistente Social " + assistenteSocial.getNome() + " no plantão.");
	}
	
	public void adicionarMedicoNoPlantao(){
		Medico medico = this.medicoDao.buscaPorId(this.medicoId);
		Plantao plantao = this.plantaoDao.buscaPlantaoPs();
		
		if(!plantao.getMedicos().contains(medico)){
			plantao.adicionaMedico(medico);
			this.plantaoDao.adiciona(plantao);
			context.addMessage(null, new FacesMessage("Médico " + medico.getNome() + " cadastrado! "));
		} else {
			System.out.println("médico já está no plantão");
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Médico já adicionado ao plantão!", null));
		}
	}
	
	public void adicionarAssistenteSocialNoPlantao(){
		AssistenteSocial assistenteSocial = this.assistenteSocialDao.buscaPorId(this.assistenteSocialId);
		Plantao plantao = this.plantaoDao.buscaPlantaoPs();
		
		if(!plantao.getAssistentesSociais().contains(assistenteSocial)){
			plantao.adicionaAssistenteSocial(assistenteSocial);
			this.plantaoDao.adiciona(plantao);
			context.addMessage(null, new FacesMessage("Assistente Social " + assistenteSocial.getNome() + " cadastrado! "));
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Assistente Social já adicionado ao plantão!", null));
		}
	}
	
	public void removerMedicoDoPlantao(Medico medico) {
		Plantao plantaoPrincipal = this.plantaoDao.buscaPlantaoPs();
		plantaoPrincipal.removeMedico(medico);
		this.plantaoDao.atualiza(plantaoPrincipal);
	}
	
	public void removerAssistenteSocialDoPlantao(AssistenteSocial assistenteSocial) {
		Plantao plantaoPrincipal = this.plantaoDao.buscaPlantaoPs();
		plantaoPrincipal.removeAssistenteSocial(assistenteSocial);
		this.plantaoDao.atualiza(plantaoPrincipal);
	}

	public List<Medico> getMedicosDoPlantao() {
		return this.plantaoPs.getMedicos();
	}

	public List<AssistenteSocial> getAssistentesSociaisDoPlantao() {
		return this.plantaoPs.getAssistentesSociais();
	}
	
	public List<Medico> getMedicosPlantaoPrincipal() {
		
		List<Medico> medicosPlantao = new ArrayList<>();
		List<Plantao> listPlantao = plantaoDao.listarPlantaoPs();
		
		for (Plantao plantaoPs : listPlantao) {
			
			for (Medico medico : plantaoPs.getMedicos()) {
				medicosPlantao.add(medico);
			}
		}
		return medicosPlantao;
	}

	public List<AssistenteSocial> getAssistentesSociaisPlantaoPrincipal() {
		
		List<AssistenteSocial> assistentesSociaisPlantao = new ArrayList<>();
		List<Plantao> listPlantao = plantaoDao.listarPlantaoPs();
		
		for (Plantao plantaoPs : listPlantao) {
			
			for (AssistenteSocial assistenteSocial : plantaoPs.getAssistentesSociais()) {
				assistentesSociaisPlantao.add(assistenteSocial);
			}
		}
		return assistentesSociaisPlantao;
	}
	
	public Plantao getPlantaoPrincipal() {
		Plantao plantaoPrincipal = plantaoDao.buscaPlantaoPs();
		return plantaoPrincipal;
	}
	
	public String formMedico() {
		System.out.println("Chamanda do formulário do Médico.");
		return "medico?faces-redirect=true";
	}

	public String formAssistenteSocial() {
		System.out.println("Chamanda do formulário do Assistente Social.");
		return "assistentesocial?faces-redirect=true";
	}
	
	// método para gravar médico no banco
	public void salvar() {
		System.out.println("Gravando medico do plantão ");

		/*
		 * for (Medico medico : medicosSelecionados) {
		 * this.plantao.adicionaMedico(medico); }
		 */
		if (plantaoPs.getId() == null) {
			plantaoDao.adiciona(this.plantaoPs);
			context.addMessage(null, new FacesMessage("Plantão cadastrado! "));
		} else {
			plantaoDao.atualiza(this.plantaoPs);
			context.addMessage(null, new FacesMessage("Plantão atualizado! "));
		}
		this.plantaoPs = new Plantao();
	}

	public void limpar() {
		this.plantaoPs = new Plantao();
		PrimeFaces.current().resetInputs("formPlantao:panelGridCadastro");
	}

	public void carregar(Plantao plantaoPs) {
		System.out.println("Carregando plantao");
		this.plantaoPs = this.plantaoDao.buscaPorId(plantaoPs.getId());
	}
	
	// getters and setters
	public Plantao getPlantao() {
		return plantaoPs;
	}

	public void setPlantao(Plantao plantaoPs) {
		this.plantaoPs = plantaoPs;
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
