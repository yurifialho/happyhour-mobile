package br.edu.fa7.heppyhour;

import java.io.Serializable;

public class Evento implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5867544897204646115L;
	
	private long id;
	private String local;
	private String descricao;
	private String data;
	private String hora;
	private String dono;
	
	public Evento(String local, String data, String hora, String descricao) {
		this.local = local;
		this.data = data;
		this.hora = hora;
		this.descricao = descricao;
	
	}

	public Evento() {
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}



	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDono() {
		return dono;
	}

	public void setDono(String dono) {
		this.dono = dono;
	}
}
