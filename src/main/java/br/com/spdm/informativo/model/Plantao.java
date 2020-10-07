package br.com.spdm.informativo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Plantao implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String coordenadorMedico;
	private String coordenadorAdministrativo;
	private String horaInicio;
	private String horaFim;
	@ManyToMany
	private List<Medico> medicos = new ArrayList<Medico>();;
	private String mensagemGerencia;
	
	public void adicionaMedico(Medico medico){
		
		this.medicos.add(medico);
	}
	
	public void removeMedico(Medico medico) {
		this.medicos.remove(medico);
	}
	
	//getters and setters
	public String getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}
	public String getHoraFim() {
		return horaFim;
	}
	public void setHoraFim(String horaFim) {
		this.horaFim = horaFim;
	}
	public List<Medico> getMedicos() {
		return medicos;
	}
	
	public Integer getId() {
		return id;
	}
	public String getCoordenadorMedico() {
		return coordenadorMedico;
	}
	public String getCoordenadorAdministrativo() {
		return coordenadorAdministrativo;
	}
	public String getMensagemGerencia() {
		return mensagemGerencia;
	}
}
