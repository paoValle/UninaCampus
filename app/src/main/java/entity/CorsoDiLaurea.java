package entity;

import java.util.ArrayList;

public class CorsoDiLaurea {

	private String id;
	private String nome;
	private ArrayList<Corso> corsi;

	public CorsoDiLaurea() {
		// TODO Auto-generated constructor stub
		this.corsi=new ArrayList<Corso>();
	}

	public CorsoDiLaurea(String id, String nome, ArrayList<Corso> corsi) {
		super();
		this.id = id;
		this.nome = nome;
		this.corsi=new ArrayList<Corso>();
		this.corsi = corsi;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ArrayList<Corso> getCorsi() {
		return corsi;
	}

	public void setCorsi(ArrayList<Corso> corsi) {
		this.corsi = corsi;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		CorsoDiLaurea other = (CorsoDiLaurea) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	
	
}
