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
		return "login?faces-redirect=true";
	}

	/*private String convertStringToMd5(String valor) {
		   MessageDigest mDigest;
		   try {
		      //Instanciamos o nosso HASH MD5, poderíamos usar outro como
		      //SHA, por exemplo, mas optamos por MD5.
		     mDigest = MessageDigest.getInstance("MD5");

		     //Convert a String valor para um array de bytes em MD5
		     byte[] valorMD5 = mDigest.digest(valor.getBytes("UTF-8"));

		     //Convertemos os bytes para hexadecimal, assim podemos salvar
		     //no banco para posterior comparação se senhas
		     StringBuffer sb = new StringBuffer();
		     for (byte b : valorMD5){
		            sb.append(Integer.toHexString((b & 0xFF) |
		            0x100).substring(1,3));
		     }

		     return sb.toString();

		   } catch (NoSuchAlgorithmException e) {
		     // TODO Auto-generated catch block
		     e.printStackTrace();
		     return null;
		   } catch (UnsupportedEncodingException e) {
		     // TODO Auto-generated catch block
		     e.printStackTrace();
		     return null;
		  }
	}*/
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
}
