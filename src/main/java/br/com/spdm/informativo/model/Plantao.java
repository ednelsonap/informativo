package br.com.spdm.informativo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class Plantao implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(unique=true)
	@Enumerated(EnumType.STRING)
	private Unidade unidade;
	
	@NotBlank
	@Column(length=100)
	private String nomeUnidade;
	
	@NotBlank
	@Column(length=50)
	private String coordenadorMedico;

	@NotBlank
	@Column(length=50)
	private String coordenadorAdministrativo;
	
	@Column(length=300)
	private String observacao;
	
	@ManyToMany
	private List<Medico> medicos = new ArrayList<Medico>();
	
	@ManyToMany
	private List<AssistenteSocial> assistentesSociais = new ArrayList<AssistenteSocial>();
	
	public void adicionaMedico(Medico medico){	
		this.medicos.add(medico);
	}
	
	public void adicionaAssistenteSocial(AssistenteSocial assistenteSocial){	
		this.assistentesSociais.add(assistenteSocial);
	}
	
	public void removeMedico(Medico medico) {
		this.medicos.remove(medico);
	}
	
	public void removeAssistenteSocial(AssistenteSocial assistenteSocial) {
		this.assistentesSociais.remove(assistenteSocial);
	}
	
	//getters and setters
	
	public List<Medico> getMedicos() {
		return medicos;
	}
	public List<AssistenteSocial> getAssistentesSociais() {
		return assistentesSociais;
	}
	
	public Integer getId() {
		return id;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public String getCoordenadorMedico() {
		return coordenadorMedico;
	}

	public void setCoordenadorMedico(String coordenadorMedico) {
		this.coordenadorMedico = coordenadorMedico;
	}

	public String getCoordenadorAdministrativo() {
		return coordenadorAdministrativo;
	}

	public void setCoordenadorAdministrativo(String coordenadorAdministrativo) {
		this.coordenadorAdministrativo = coordenadorAdministrativo;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getNomeUnidade() {
		return nomeUnidade;
	}

	public void setNomeUnidade(String nomeUnidade) {
		this.nomeUnidade = nomeUnidade;
	}
	
}
