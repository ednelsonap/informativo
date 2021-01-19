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
import br.com.spdm.informativo.dao.PlantaoPsDao;
import br.com.spdm.informativo.model.Medico;
import br.com.spdm.informativo.model.PlantaoPs;

@Named
@ViewScoped
public class PlantaoPsBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext context;
	@Inject
	private PlantaoPsDao plantaoDao;
	@Inject
	private MedicoDao medicoDao;

	private PlantaoPs plantaoPs = new PlantaoPs();
	private Integer medicoId;

	// método p/ listar médicos cadastrados
	public List<Medico> getMedicos() {
		return this.medicoDao.listaTodos();
	}

	public void gravarMedico() {
		Medico medico = this.medicoDao.buscaPorId(this.medicoId);
		this.plantaoPs.adicionaMedico(medico);
		System.out.println("Gravando médico " + medico.getNome() + " no plantão.");
	}

	public void adicionarNoPlantao(){
		Medico medico = this.medicoDao.buscaPorId(this.medicoId);
		PlantaoPs plantaoPrincipal = this.plantaoDao.buscaPlantaoIdUm();
		plantaoPrincipal.adicionaMedico(medico);
		this.plantaoDao.adiciona(plantaoPrincipal);
	}
	
	public void removerMedicoDoPlantao(Medico medico) {
		PlantaoPs plantaoPrincipal = this.plantaoDao.buscaPlantaoIdUm();
		plantaoPrincipal.removeMedico(medico);
		this.plantaoDao.atualiza(plantaoPrincipal);
	}

	public List<Medico> getMedicosDoPlantao() {
		return this.plantaoPs.getMedicos();
	}

	public List<Medico> getMedicosPlantaoPrincipal() {
		
		List<Medico> medicosPlantao = new ArrayList<>();
		List<PlantaoPs> listPlantao = plantaoDao.listar();
		
		for (PlantaoPs plantaoPs : listPlantao) {
			
			for (Medico medico : plantaoPs.getMedicos()) {
				medicosPlantao.add(medico);
			}
		}
		return medicosPlantao;
	}

	public PlantaoPs getPlantaoPrincipal() {
		PlantaoPs plantaoPrincipal = plantaoDao.buscaPlantaoIdUm();
		return plantaoPrincipal;
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
		if (plantaoPs.getId() == null) {
			plantaoDao.adiciona(this.plantaoPs);
			context.addMessage(null, new FacesMessage("Plantão cadastrado! "));
		} else {
			plantaoDao.atualiza(this.plantaoPs);
			context.addMessage(null, new FacesMessage("Plantão atualizado! "));
		}
		this.plantaoPs = new PlantaoPs();
	}

	public void limpar() {
		this.plantaoPs = new PlantaoPs();
		PrimeFaces.current().resetInputs("formPlantao:panelGridCadastro");
	}

	public void carregar(PlantaoPs plantaoPs) {
		System.out.println("Carregando plantao");
		this.plantaoPs = this.plantaoDao.buscaPorId(plantaoPs.getId());
	}
	
	// getters and setters
	public PlantaoPs getPlantao() {
		return plantaoPs;
	}

	public void setPlantao(PlantaoPs plantaoPs) {
		this.plantaoPs = plantaoPs;
	}

	public Integer getMedicoId() {
		return medicoId;
	}

	public void setMedicoId(Integer medicoId) {
		this.medicoId = medicoId;
	}

}
