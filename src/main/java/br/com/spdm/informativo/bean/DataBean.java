package br.com.spdm.informativo.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class DataBean {
	private Date dataAtual = new Date(System.currentTimeMillis());
	private String dataFormatada;

	public String getDataFormatada() {
		SimpleDateFormat sdt = new SimpleDateFormat("dd/MM/yyyy");
	    dataFormatada = sdt.format(dataAtual);
		return dataFormatada;
	}
}
