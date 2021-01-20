package br.com.spdm.informativo.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import br.com.spdm.informativo.dao.GerenciaDao;
import br.com.spdm.informativo.model.Unidade;
import br.com.spdm.informativo.model.Gerencia;

@Named
@ConversationScoped
public class GerenciaBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Gerencia gerencia = new Gerencia();

	@Inject
	private GerenciaDao gerenciaDao;
	
	private List<Gerencia> listaGerenciaPs;
	
	private String observacaoGerencia;
	
	@PostConstruct
	void init(){
		this.listaGerenciaPs = gerenciaDao.listaTodos();
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
		
		if(this.gerencia.getId() == null) {
			System.out.println("gerencia é nula: " + this.gerencia.getId() + this.gerencia.getCoordenadorAdministrativo() );
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Não foi possível salvar! "));
		} else {
		
			gerenciaDao.atualiza(this.gerencia);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Dados atualizados! "));
		}
		this.gerencia = new Gerencia();
	}
	
	public void limpar(){
		this.gerencia = new Gerencia();
		PrimeFaces.current().resetInputs("formGerencia:panelGridGerencia");
	}
	
	public List<Gerencia> getListaGerenciaPs() {
		return listaGerenciaPs;
	}

	public List<Gerencia> getListaGerenciaUpa(){
		
		return gerenciaDao.listaGerenciaUpa();
	}
	
	public void getGerenciaDoPs(){
		this.observacaoGerencia = gerenciaDao.buscaGerenciaDoPs().getObservacao();
	}

	public String getObservacaoGerencia() {
		return observacaoGerencia;
	}

	public void setObservacaoGerencia(String observacaoGerencia) {
		this.observacaoGerencia = observacaoGerencia;
	}
}
