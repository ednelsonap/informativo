package br.com.spdm.informativo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

	@Column(length=10)
	@Enumerated(EnumType.STRING)
	private Unidade unidade;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	
	
	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Gerencia other = (Gerencia) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
