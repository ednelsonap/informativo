package br.com.spdm.informativo.bean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import br.com.spdm.informativo.dao.EspecialidadeDao;
import br.com.spdm.informativo.dao.MedicoDao;
import br.com.spdm.informativo.model.Especialidade;
import br.com.spdm.informativo.model.Medico;

@Named
@ConversationScoped
public class MedicoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Medico medico = new Medico();
	private List<Medico> medicos;

	@Inject
	private MedicoDao medicoDao;

	@Inject
	private FacesContext context;

	@Inject
	private EspecialidadeDao especialidadeDao;

	public List<Especialidade> getEspecialidades() {
		return this.especialidadeDao.listaTodos();
	}

	// método para listar todos os medicos do banco
	public List<Medico> getMedicos() {
		this.medicos = medicoDao.listaTodos();
		return this.medicos;
	}

	// método para gravar médico no banco
	public void salvar() {
		System.out.println("Gravando medico " + this.medico.getNome());

		boolean nomeExiste = medicoDao.nomeExiste(this.medico);
		boolean crmExiste = medicoDao.crmExiste(this.medico);

		if (nomeExiste && this.medico.getId() == null) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Já existe um médico cadastrado com o mesmo nome!", null));

		} else if (crmExiste && this.medico.getId() == null) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Já existe um médico cadastrado com o mesmo CRM!", null));

		} else {
			medicoDao.adiciona(this.medico);
			context.addMessage(null, new FacesMessage("Médico " + this.medico.getNome() + " cadastrado! "));
		}
		this.medico = new Medico();
	}

	public Date getDataMinima() {
		Calendar dataAtual = Calendar.getInstance();
		dataAtual.add(Calendar.DATE, +1);
		return dataAtual.getTime();
	}

	/*public Date diasAposVencimento(int diaVencimento) {
		Locale myLocale = Locale.getDefault();
		Calendar dataAtual = Calendar.getInstance(myLocale);
		dataAtual.add(Calendar.DATE, -diaVencimento);
		return dataAtual.getTime();
	}*/

	public void limpar() {
		this.medico = new Medico();
		PrimeFaces.current().resetInputs("formMedico:panelGridCadastro");
	}

	// método para remover médico do banco
	public void remover(Medico medico) {
		try {
			System.out.println("Removendo Médico " + medico.getNome());
			medicoDao.remove(medico);
			context.addMessage(null, new FacesMessage("Médico removido! "));
		} catch (Exception ex) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Não foi possível remover o médico, pois o mesmo está vinculado a um plantão", null));
		}
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

}
