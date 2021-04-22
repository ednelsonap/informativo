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

import br.com.spdm.informativo.dao.UsuarioDao;
import br.com.spdm.informativo.model.Usuario;
import br.com.spdm.informativo.util.PassGenerator;

@Named
@ViewScoped
public class UsuarioBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Usuario usuario = new Usuario();
	
	private List<Usuario> usuarios;
	
	@Inject
	private FacesContext context;
	
	@Inject
	private UsuarioDao usuarioDao;

	@Transactional
	public void salvar() {
		
		try {
			if (this.usuario.getId() == null) {
			this.usuario.setSenha(new PassGenerator().generate(this.usuario.getSenha()));
			usuarioDao.adiciona(this.usuario);
			context.addMessage(null, new FacesMessage("Salvo com sucesso! "));

			} else {
				usuarioDao.atualiza(this.usuario);
				context.addMessage(null, new FacesMessage("Alterado com sucesso! "));			
			}
			this.usuario = new Usuario();
		} catch (PersistenceException e){
			String mensagem = e.getMessage();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					mensagem , null));
		}
	
	}
	
	public void limpar() {
		this.usuario = new Usuario();
		PrimeFaces.current().resetInputs("formUsuario:panelGridCadastro");
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		this.usuarios = usuarioDao .listaTodos();
		return this.usuarios;
	}
	
	
}
