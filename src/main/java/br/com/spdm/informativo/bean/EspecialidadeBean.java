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

import br.com.spdm.informativo.dao.EspecialidadeDao;
import br.com.spdm.informativo.model.Especialidade;

@Named
@ViewScoped
public class EspecialidadeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Especialidade especialidade = new Especialidade();
	
	private List<Especialidade> especialidades;

	@Inject
	private EspecialidadeDao especialidadeDao;

	@Inject
	private FacesContext context;

	public List<Especialidade> getEspecialidades() {
		this.especialidades = especialidadeDao.listaTodos();
		return this.especialidades;
	}

	@Transactional
	public void salvar() {
		
		boolean especialidadeExiste = especialidadeDao.especialidadeExiste(this.especialidade);
		
		if (especialidadeExiste && this.especialidade.getId() == null) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Já existe uma especialidade com o mesmo nome!", null));

		} else {
			especialidadeDao.adiciona(this.especialidade);
			context.addMessage(null, new FacesMessage("Salvo com sucesso! "));			
		}
		
		this.especialidade = new Especialidade();
	}

	@Transactional
	public void alterar() {

		try {
			especialidadeDao.atualiza(this.especialidade);
			context.addMessage(null, new FacesMessage("Alterado com sucesso!"));
					
		} catch (PersistenceException e) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Não foi possível alterar", null));
		}
		this.especialidade = new Especialidade();
	}
	
	@Transactional
	public void remover(Especialidade especialidade) {
		try {
			
			especialidadeDao.remove(especialidade);
			context.addMessage(null, new FacesMessage("Especialidade removida! "));
		
		} catch (PersistenceException ex) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Não foi possível remover", null));
		}
	}

	public boolean exibirBotaoAlterar(Especialidade especialidade) {
		if (this.especialidade.getId() != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean exibirBotaoSalvar(Especialidade especialidade) {
		if (this.especialidade.getId() == null) {
			return true;
		} else {
			return false;
		}
	}
	
	public void limpar() {
		this.especialidade = new Especialidade();
		PrimeFaces.current().resetInputs("formEspecialidade:panelGridCadastro");
	}
	
	public Especialidade getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}
	
}
