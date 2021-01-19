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
import br.com.spdm.informativo.dao.PlantaoUpaDao;
import br.com.spdm.informativo.model.Medico;
import br.com.spdm.informativo.model.PlantaoUpa;

@Named
@ViewScoped
public class PlantaoUpaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext context;
	@Inject
	private PlantaoUpaDao plantaoUpaDao;
	@Inject
	private MedicoDao medicoDao;

	private PlantaoUpa plantaoUpa = new PlantaoUpa();
	
	private Integer medicoId;

	// método p/ listar médicos cadastrados
	public List<Medico> getMedicos() {
		return this.medicoDao.listaTodos();
	}

	public void gravarMedico() {
		Medico medico = this.medicoDao.buscaPorId(this.medicoId);
		this.plantaoUpa.adicionaMedico(medico);
		System.out.println("Gravando médico " + medico.getNome() + " no plantão.");
	}

	public void adicionarNoPlantao(){
		Medico medico = this.medicoDao.buscaPorId(this.medicoId);
		PlantaoUpa plantaoUpaPrincipal = this.plantaoUpaDao.buscaPlantaoIdUm();
		plantaoUpaPrincipal.adicionaMedico(medico);
		this.plantaoUpaDao.adiciona(plantaoUpaPrincipal);
	}
	
	public void removerMedicoDoPlantao(Medico medico) {
		PlantaoUpa plantaoUpaPrincipal = this.plantaoUpaDao.buscaPlantaoIdUm();
		plantaoUpaPrincipal.removeMedico(medico);
		this.plantaoUpaDao.atualiza(plantaoUpaPrincipal);
	}

	public List<Medico> getMedicosDoPlantao() {
		return this.plantaoUpa.getMedicos();
	}

	public List<Medico> getMedicosPlantaoPrincipal() {
		
		List<Medico> medicosPlantao = new ArrayList<>();
		List<PlantaoUpa> listPlantao = plantaoUpaDao.listar();
		
		for (PlantaoUpa plantaoUpa : listPlantao) {
			
			for (Medico medico : plantaoUpa.getMedicos()) {
				medicosPlantao.add(medico);
			}
		}
		return medicosPlantao;
	}

	public PlantaoUpa getPlantaoPrincipal() {
		PlantaoUpa plantaoUpaPrincipal = plantaoUpaDao.buscaPlantaoIdUm();
		return plantaoUpaPrincipal;
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
		 * this.plantaoUpa.adicionaMedico(medico); }
		 */
		if (plantaoUpa.getId() == null) {
			plantaoUpaDao.adiciona(this.plantaoUpa);
			context.addMessage(null, new FacesMessage("Plantão cadastrado! "));
		} else {
			plantaoUpaDao.atualiza(this.plantaoUpa);
			context.addMessage(null, new FacesMessage("Plantão atualizado! "));
		}
		this.plantaoUpa = new PlantaoUpa();
	}

	public void limpar() {
		this.plantaoUpa = new PlantaoUpa();
		PrimeFaces.current().resetInputs("formPlantao:panelGridCadastro");
	}

	public void carregar(PlantaoUpa plantaoUpa) {
		System.out.println("Carregando plantaoUpa");
		this.plantaoUpa = this.plantaoUpaDao.buscaPorId(plantaoUpa.getId());
	}
	
	// getters and setters
	public PlantaoUpa getPlantaoUpa() {
		return plantaoUpa;
	}

	public void setPlantao(PlantaoUpa plantaoUpa) {
		this.plantaoUpa = plantaoUpa;
	}

	public Integer getMedicoId() {
		return medicoId;
	}

	public void setMedicoId(Integer medicoId) {
		this.medicoId = medicoId;
	}

}
