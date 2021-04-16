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

import br.com.spdm.informativo.dao.AssistenteSocialDao;
import br.com.spdm.informativo.model.AssistenteSocial;

@Named
@ViewScoped
public class AssistenteSocialBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private AssistenteSocial assistenteSocial = new AssistenteSocial();
	private List<AssistenteSocial> assistentesSociais;

	@Inject
	private AssistenteSocialDao assistenteSocialDao;

	@Inject
	private FacesContext context;

	// método para listar todos os assistenteSocials do banco
	public List<AssistenteSocial> getAssistentesSociais() {
		this.assistentesSociais = assistenteSocialDao.listaTodos();
		return this.assistentesSociais;
	}

	@Transactional
	public void salvar() {

		boolean nomeExiste = assistenteSocialDao.nomeExiste(this.assistenteSocial);
		boolean cressExiste = assistenteSocialDao.cressExiste(this.assistenteSocial);

		if (nomeExiste && this.assistenteSocial.getId() == null) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Já existe uma Assistente Social cadastrado com o mesmo nome!", null));

		} else if (cressExiste && this.assistenteSocial.getId() == null) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Já existe uma Assistente Social cadastrado com o mesmo CRESS!", null));

		} else {
			assistenteSocialDao.adiciona(this.assistenteSocial);
			context.addMessage(null, new FacesMessage("Salvo com sucesso! "));
		}
		this.assistenteSocial = new AssistenteSocial();
	}

	@Transactional
	public void alterar() {

		try {
			assistenteSocialDao.atualiza(this.assistenteSocial);
			context.addMessage(null, new FacesMessage("Alterado com sucesso!"));

		} catch (PersistenceException e) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Não foi possível alterar", null));
		}
		this.assistenteSocial = new AssistenteSocial();
	}

	public boolean exibirBotaoAlterar(AssistenteSocial assistenteSocial) {
		if (this.assistenteSocial.getId() != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean exibirBotaoSalvar(AssistenteSocial assistenteSocial) {
		if (this.assistenteSocial.getId() == null) {
			return true;
		} else {
			return false;
		}
	}

	public void limpar() {
		this.assistenteSocial = new AssistenteSocial();
		PrimeFaces.current().resetInputs("formAssistenteSocial:panelGridCadastro");
	}

	@Transactional
	public void remover(AssistenteSocial assistenteSocial) {
		try {
			
			assistenteSocialDao.remove(assistenteSocial);
			context.addMessage(null, new FacesMessage("Removido com sucesso! "));
			
		} catch (PersistenceException ex) {
			
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Não foi possível remover", null));
		} 
	}

	public AssistenteSocial getAssistenteSocial() {
		return assistenteSocial;
	}

	public void setAssistenteSocial(AssistenteSocial assistenteSocial) {
		this.assistenteSocial = assistenteSocial;
	}

}
