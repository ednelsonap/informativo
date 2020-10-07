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

import br.com.spdm.informativo.dao.MedicoDao;
import br.com.spdm.informativo.dao.PlantaoDao;
import br.com.spdm.informativo.model.Medico;
import br.com.spdm.informativo.model.Plantao;

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

	private Plantao plantao = new Plantao();
	private Integer medicoId;

	// método p/ listar médicos cadastrados
	public List<Medico> getMedicos() {
		return this.medicoDao.listaTodos();
	}

	public void gravarMedico() {
		Medico medico = this.medicoDao.buscaPorId(this.medicoId);
		this.plantao.adicionaMedico(medico);
		System.out.println("Gravando médico " + medico.getNome() + " no plantão.");
	}

	public void adicionarNoPlantao(){
		Medico medico = this.medicoDao.buscaPorId(this.medicoId);
		Plantao plantaoPrincipal = this.plantaoDao.buscaPlantaoIdUm();
		plantaoPrincipal.adicionaMedico(medico);
		this.plantaoDao.adiciona(plantaoPrincipal);
	}
	
	public void removerMedicoDoPlantao(Medico medico) {
		Plantao plantaoPrincipal = this.plantaoDao.buscaPlantaoIdUm();
		plantaoPrincipal.removeMedico(medico);
		this.plantaoDao.atualiza(plantaoPrincipal);
	}

	public List<Medico> getMedicosDoPlantao() {
		return this.plantao.getMedicos();
	}

	public List<Medico> getPlantaoPrincipal() {
		
		List<Medico> medicosPlantao = new ArrayList<>();
		List<Plantao> listPlantao = plantaoDao.listar();
		
		for (Plantao plantao : listPlantao) {
			
			for (Medico medico : plantao.getMedicos()) {
				medicosPlantao.add(medico);
			}
		}
		return medicosPlantao;
	}

	public String formMedico() {
		System.out.println("Chamanda do formulário do Médico.");
		return "medico?faces-redirect=true";
	}

	// método para gravar médico no banco
	public void salvar() {
		System.out.println("Gravando medico do plantão ");

		/*
		 * for (Medico medico : medicosSelecionados) {
		 * this.plantao.adicionaMedico(medico); }
		 */
		if (plantao.getId() == null) {
			plantaoDao.adiciona(this.plantao);
			context.addMessage(null, new FacesMessage("Plantão cadastrado! "));
		} else {
			plantaoDao.atualiza(this.plantao);
			context.addMessage(null, new FacesMessage("Plantão atualizado! "));
		}
		this.plantao = new Plantao();
	}

	public void limpar() {
		this.plantao = new Plantao();
		PrimeFaces.current().resetInputs("formPlantao:panelGridCadastro");
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

}
