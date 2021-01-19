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
import br.com.spdm.informativo.model.Gerencia;

@Named
@ConversationScoped
public class GerenciaBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Gerencia gerencia = new Gerencia();

	@Inject
	private GerenciaDao gerenciaDao;
	
	private List<Gerencia> listaGerenciaPs;
	
	@PostConstruct
	void init(){
		this.listaGerenciaPs = gerenciaDao.listaGerenciaPs();
	}
	
	public Gerencia getGerencia() {
		return gerencia;
	}

	public void setGerencia(Gerencia gerencia) {
		this.gerencia = gerencia;
	}

	public void salvar() {
		
		if(this.gerencia.getId() == null) {
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
}
