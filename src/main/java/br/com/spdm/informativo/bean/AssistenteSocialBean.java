package br.com.spdm.informativo.bean;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import br.com.spdm.informativo.dao.AssistenteSocialDao;
import br.com.spdm.informativo.model.AssistenteSocial;

@Named
@ConversationScoped
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

	// método para gravar médico no banco
	public void salvar() {
		System.out.println("Gravando assistenteSocial " + this.assistenteSocial.getNome());

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
			context.addMessage(null, new FacesMessage("Médico " + this.assistenteSocial.getNome() + " cadastrado! "));
		}
		this.assistenteSocial = new AssistenteSocial();
	}

	public void limpar() {
		this.assistenteSocial = new AssistenteSocial();
		PrimeFaces.current().resetInputs("formAssistenteSocial:panelGridCadastro");
	}

	// método para remover médico do banco
	public void remover(AssistenteSocial assistenteSocial) {
		try {
			System.out.println("Removendo Assistente Social " + assistenteSocial.getNome());
			assistenteSocialDao.remove(assistenteSocial);
			context.addMessage(null, new FacesMessage("Assistente Social removido! "));
		} catch (Exception ex) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Não foi possível remover a Assistente Social, pois o mesmo está vinculado a um plantão", null));
		}
	}

	public AssistenteSocial getAssistenteSocial() {
		return assistenteSocial;
	}

	public void setAssistenteSocial(AssistenteSocial assistenteSocial) {
		this.assistenteSocial = assistenteSocial;
	}

}
