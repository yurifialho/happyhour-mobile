package br.edu.fa7.heppyhour;

import java.io.Serializable;

public class Convidado implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5867544897204646115L;
	
	private long id;
	private String nome;
	private String status;
	private String contato;
	private Integer evento;
	
	public Convidado(String nome, String status, String contato, Integer evento) {
		this.nome = nome;
		this.status = status;
		this.contato = contato;
		this.evento = evento;
	
	}

	public Convidado() {
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public Integer getEvento() {
		return evento;
	}

	public void setEvento(Integer evento) {
		this.evento = evento;
	}

	

}
