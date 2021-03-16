package br.com.spdm.informativo.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.primefaces.PrimeFaces;

import br.com.spdm.informativo.dao.MedicoDao;
import br.com.spdm.informativo.model.Especialidade;
import br.com.spdm.informativo.model.Medico;

@Named
@ViewScoped
public class MedicoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Medico medico = new Medico();
	private List<Medico> medicos;

	@Inject
	private MedicoDao medicoDao;

	@Inject
	private FacesContext context;

	public Especialidade[] getEspecialidades() {
		return Especialidade.values();
	}

	// método para listar todos os medicos do banco
	public List<Medico> getMedicos() {
		this.medicos = medicoDao.listaTodos();
		return this.medicos;
	}

	// método para gravar médico no banco
	@Transactional
	public void salvar() {
		System.out.println("Gravando medico " + this.medico.getNome());

		boolean nomeExiste = medicoDao.nomeExiste(this.medico);
		boolean crmExiste = medicoDao.crmExiste(this.medico);

		if (nomeExiste && this.medico.getId() == null) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Já existe um médico cadastrado com o mesmo nome!", null));

		} else if (crmExiste && this.medico.getId() == null) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Já existe um médico cadastrado com o mesmo CRM!", null));

		} else {
			medicoDao.adiciona(this.medico);
			context.addMessage(null, new FacesMessage("Salvo com sucesso! "));
		}
		this.medico = new Medico();
	}

	// método para alterar médico no banco
	@Transactional
	public void alterar() {

		System.out.println("entrando no método alterar");

		try {
			medicoDao.atualiza(this.medico);
			context.addMessage(null, new FacesMessage("Médico " + medico.getNome() + " atualizado com sucesso!"));
			
			System.out.println("Alterado com sucesso");
			
		} catch (PersistenceException e) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Não foi possível alterar este cadastro! Verifique se não há duplicidade de nome ou CRM.", null));
			
			System.out.println("não foi possível salvar");
		}
		this.medico = new Medico();
	}

	public boolean exibirBotaoAlterar(Medico medico) {
		if (this.medico.getId() != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean exibirBotaoSalvar(Medico medico) {
		if (this.medico.getId() == null) {
			return true;
		} else {
			return false;
		}
	}
	
	public void limpar() {
		this.medico = new Medico();
		PrimeFaces.current().resetInputs("formMedico:panelGridCadastro");
	}

	// método para remover médico do banco
	@Transactional
	public void remover(Medico medico) {
		try {
			System.out.println("Removendo Médico " + medico.getNome());
			medicoDao.remove(medico);
			context.addMessage(null, new FacesMessage("Médico removido! "));
		} catch (Exception ex) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Não foi possível remover o médico, pois o mesmo está vinculado a um plantão", null));
		}
	}
	
	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

}
