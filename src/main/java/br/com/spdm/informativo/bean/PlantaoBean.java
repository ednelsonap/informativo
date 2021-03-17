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
import br.com.spdm.informativo.model.Unidade;

@Named
@ViewScoped
public class PlantaoBean implements Serializable {

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

	//private List<Plantao> listaPlantao;

	/*@PostConstruct
	void init() {
		this.listaPlantao = plantaoDao.listaTodos();
	}*/
	
	public Unidade[] getUnidades() {
		return Unidade.values();
	}

	public List<Plantao> getListaPlantao() {
		return plantaoDao.listaTodos();
	}

	public List<Medico> getMedicos() {
		return this.medicoDao.listaTodos();
	}

	public List<AssistenteSocial> getAssistentesSociais() {
		return this.assistenteSocialDao.listaTodos();
	}

	public void gravarMedico() {
		Medico medico = this.medicoDao.buscaPorId(this.medicoId);
		this.plantao.adicionaMedico(medico);
		System.out.println("Gravando médico " + medico.getNome() + " no plantão.");
	}

	public void gravarAssistenteSocial() {
		AssistenteSocial assistenteSocial = this.assistenteSocialDao.buscaPorId(this.assistenteSocialId);
		this.plantao.adicionaAssistenteSocial(assistenteSocial);
		System.out.println("Gravando Assistente Social " + assistenteSocial.getNome() + " no plantão.");
	}

	@Transactional
	public void adicionarMedicoNoPlantao() {
		Medico medico = this.medicoDao.buscaPorId(this.medicoId);
		Plantao plantao = this.plantaoDao.buscaPlantaoPs();
	
		if (plantao.getMedicos().contains(medico)) {
			System.out.println("Este médico já está no plantão");
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Médico já adicionado ao plantão!", null));
		} else {
			plantao.adicionaMedico(medico);
			this.plantaoDao.adiciona(plantao);
			context.addMessage(null, new FacesMessage("Médico " + medico.getNome() + " cadastrado! "));
		}
	}

	@Transactional
	public void adicionarAssistenteSocialNoPlantao() {
		AssistenteSocial assistenteSocial = this.assistenteSocialDao.buscaPorId(this.assistenteSocialId);
		Plantao plantao = this.plantaoDao.buscaPlantaoPs();

		if (!plantao.getAssistentesSociais().contains(assistenteSocial)) {
			plantao.adicionaAssistenteSocial(assistenteSocial);
			this.plantaoDao.adiciona(plantao);
			context.addMessage(null,
					new FacesMessage("Assistente Social " + assistenteSocial.getNome() + " cadastrado! "));
		} else {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Assistente Social já adicionado ao plantão!", null));
		}
	}

	@Transactional
	public void removerMedicoDoPlantao(Medico medico) {
		Plantao plantaoPrincipal = this.plantaoDao.buscaPlantaoPs();
		plantaoPrincipal.removeMedico(medico);
		this.plantaoDao.atualiza(plantaoPrincipal);
	}

	@Transactional
	public void removerAssistenteSocialDoPlantao(AssistenteSocial assistenteSocial) {
		Plantao plantaoPrincipal = this.plantaoDao.buscaPlantaoPs();
		plantaoPrincipal.removeAssistenteSocial(assistenteSocial);
		this.plantaoDao.atualiza(plantaoPrincipal);
	}

	public List<Medico> getMedicosDoPlantao() {
		return this.plantao.getMedicos();
	}

	public List<AssistenteSocial> getAssistentesSociaisDoPlantao() {
		return this.plantao.getAssistentesSociais();
	}

	public List<Medico> getMedicosPlantaoPrincipal() {

		List<Medico> medicosPlantao = new ArrayList<>();
		List<Plantao> listPlantao = plantaoDao.listarPlantaoPs();

		for (Plantao plantao : listPlantao) {

			for (Medico medico : plantao.getMedicos()) {
				medicosPlantao.add(medico);
			}
		}
		return medicosPlantao;
	}

	public List<AssistenteSocial> getAssistentesSociaisPlantaoPrincipal() {

		List<AssistenteSocial> assistentesSociaisPlantao = new ArrayList<>();
		List<Plantao> listPlantao = plantaoDao.listarPlantaoPs();

		for (Plantao plantao : listPlantao) {

			for (AssistenteSocial assistenteSocial : plantao.getAssistentesSociais()) {
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

	@Transactional
	public void salvar() {

		try {
			if (plantao.getId() == null) {
				plantaoDao.adiciona(this.plantao);
				context.addMessage(null, new FacesMessage("Plantão Cadastrado! "));
			} else {
				plantaoDao.atualiza(this.plantao);
				context.addMessage(null, new FacesMessage("Plantão Atualizado! "));
			}

		} catch (PersistenceException e) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Não foi possível alterar este cadastro!", null));
			
		}	
		this.plantao = new Plantao();
	}

	public void limpar() {
		this.plantao = new Plantao();
		PrimeFaces.current().resetInputs("formPlantao:panelGridPlantao");
	}

	public void carregar(Plantao plantao) {
		System.out.println("Carregando plantao");
		this.plantao = this.plantaoDao.buscaPorId(plantao.getId());
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
