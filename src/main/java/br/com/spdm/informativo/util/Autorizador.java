package br.com.spdm.informativo.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.spdm.informativo.model.Usuario;

public class Autorizador implements PhaseListener {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void afterPhase(PhaseEvent evento) {

		FacesContext context = evento.getFacesContext();
		String nomePagina = context.getViewRoot().getViewId();
		
		System.out.println(nomePagina);
		
		if("/login.xhtml".equals(nomePagina)) {
			return;
		}
			
		if("/ps/plantao.xhtml".equals(nomePagina)) {
			return;
		}
		
		if("/ps/painel.xhtml".equals(nomePagina)) {
			return;
		}
		
		if("/upa/plantao.xhtml".equals(nomePagina)) {
			return;
		}
		
		if("/upa/painel.xhtml".equals(nomePagina)) {
			return;
		}
		
		if("/ps/painelSlider.xhtml".equals(nomePagina)) {
			return;
		}
		
		if("/upa/painelSlider.xhtml".equals(nomePagina)) {
			return;
		}
		
		Usuario usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
		
		if(usuarioLogado != null) {
			return;
		}
		
		//redirecionamento para login.xhtml

		NavigationHandler handler = context.getApplication().getNavigationHandler();
		handler.handleNavigation(context, null, "/login?faces-redirect=true");
		context.renderResponse();
	} 

	@Override
	public void beforePhase(PhaseEvent event) {
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
