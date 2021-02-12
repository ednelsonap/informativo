package br.com.spdm.informativo.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import org.primefaces.PrimeFaces;

import br.com.spdm.informativo.dao.GerenciaDao;
import br.com.spdm.informativo.model.Gerencia;
import br.com.spdm.informativo.model.Unidade;

@Named
@ConversationScoped
public class GerenciaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Gerencia gerencia = new Gerencia();

	@Inject
	private GerenciaDao gerenciaDao;

	@Inject
	private FacesContext context;

	private List<Gerencia> listaGerencia;

	private String observacaoGerencia;

	@PostConstruct
	void init() {
		this.listaGerencia = gerenciaDao.listaTodos();
	}

	public void carregaGerenciaPeloId() {
		this.gerencia = gerenciaDao.buscaPorId(this.gerencia.getId());
	}

	public Unidade[] getUnidades() {
		return Unidade.values();
	}

	public Gerencia getGerencia() {
		return gerencia;
	}

	public void setGerencia(Gerencia gerencia) {
		this.gerencia = gerencia;
	}

	public void salvar() {

		boolean gerenciaJaExisteParaAUnidade = gerenciaDao.gerenciaExistente(this.gerencia);

		System.out.println("Existe uma gerencia com esta unidade = " + gerenciaJaExisteParaAUnidade);
		System.out.println("O id dessa gerencia é = " + this.gerencia.getId());

		if (gerenciaJaExisteParaAUnidade && this.gerencia.getId() == null) {

			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Já existe uma gerência cadastrada para esta unidade!", null));

		} else if (this.gerencia.getId() != null){
			try {
				gerenciaDao.atualiza(this.gerencia);
				context.addMessage(null, new FacesMessage("Dados atualizados com sucesso! "));
			} catch (PersistenceException e) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Não foi possível salvar! Verifique se já não existe uma gerência com esta unidade", null));
			}

		} else {
			gerenciaDao.adiciona(this.gerencia);
			context.addMessage(null, new FacesMessage("Gerência cadastrada com sucesso! "));
		}

		this.gerencia = new Gerencia();

	}

	public void limpar() {
		this.gerencia = new Gerencia();
		PrimeFaces.current().resetInputs("formGerencia:panelGridGerencia");
	}

	public List<Gerencia> getListaGerencia() {
		return listaGerencia;
	}

	public void getGerenciaDoPs() {
		this.observacaoGerencia = gerenciaDao.buscaGerenciaDoPs().getObservacao();
	}

	public String getObservacaoGerencia() {
		return observacaoGerencia;
	}

	public void setObservacaoGerencia(String observacaoGerencia) {
		this.observacaoGerencia = observacaoGerencia;
	}
}
