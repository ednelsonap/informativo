package br.com.spdm.informativo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Gerencia implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length=60)
	private String coordenadorMedico;
	
	@Column(length=60)
	private String coordenadorAdministrativo;
	
	@Column(length=300)
	private String observacao;
	
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
	public Integer getId() {
		return id;
	}
	
}
