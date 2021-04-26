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

	private String novaSenha;
	
	private String confirmaSenha;
	
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
	
	@Transactional
	public void alterarSenha() {
		
		if(novaSenha.equals(confirmaSenha)){
			this.usuario.setSenha(new PassGenerator().generate(novaSenha));
			usuarioDao.atualiza(this.usuario);
			context.addMessage(null, new FacesMessage("Senha alterada com sucesso!"));
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"As senhas digitadas não são iguais", null));
		}
	}
	
	@Transactional
	public void remover(Usuario usuario) {
		Usuario usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
		try {
			if(usuarioLogado.equals(usuario)) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Não é possível remover um usuário em uso!", null));
			} else {
				usuarioDao.remove(usuario);
				context.addMessage(null, new FacesMessage("Usuário removido! "));
			}
		} catch (PersistenceException ex) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Não foi possível remover", null));
		}
	}
	
	public void limpar() {
		this.usuario = new Usuario();
		PrimeFaces.current().resetInputs("formUsuario:panelGridCadastro");
	}
	
	public boolean desabilitarInputText(Usuario usuario) {
		if (this.usuario.getId() == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		this.usuarios = usuarioDao.listaTodos();
		return this.usuarios;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}
	
}
