package br.com.spdm.informativo.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.spdm.informativo.dao.UsuarioDao;
import br.com.spdm.informativo.model.Usuario;
import br.com.spdm.informativo.util.PassGenerator;

@Named
@ViewScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioDao usuarioDao;
	private Usuario usuario = new Usuario();
	@Inject
	private FacesContext context;

	public String entrar() {

		this.usuario = usuarioDao.getUsuario(usuario.getNomeUsuario(), new PassGenerator().generate(usuario.getSenha()));
		
		if (this.usuario == null) {
			this.usuario = new Usuario();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário e/ou senha inválido!", "Erro no Login!"));
			return null;
		} else {
			context.getExternalContext().getSessionMap()
			.put("usuarioLogado", this.usuario);
			return "/home?faces-redirect=true";
		}

	}
	
	public String sair() {
		context.getExternalContext().getSessionMap().remove("usuarioLogado");
		return "/login?faces-redirect=true";
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
}
