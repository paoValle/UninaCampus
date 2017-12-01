package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class UtenteRegistrato {

	private String nome;
	private String UID;
	private String cognome;
	private String matricola;
	private String email;
	private double media;
	private CorsoDiLaurea corso;
	private HashMap<String, Esame> libretto;
	private HashMap<String, Corso> corsiScelti;
	
	public UtenteRegistrato() {
		this.libretto=new HashMap<>();
		this.corsiScelti=new HashMap<>();
	}

	public UtenteRegistrato(String UID) {
		this.UID = UID;
		this.libretto=new HashMap<>();
		this.corsiScelti=new HashMap<>();
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUID() {
		return UID;
	}

	public void setUID(String UID) {
		this.UID = UID;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getMatricola() {
		return matricola;
	}

	public void setMatricola(String matricola) {
		this.matricola = matricola;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getMedia() {
		return media;
	}

	public void setMedia(double media) {
		this.media = media;
	}

	public CorsoDiLaurea getCorso() {
		return corso;
	}


	public void setCorso(CorsoDiLaurea corso) {
		this.corso = corso;
	}


	public HashMap<String, Esame> getLibretto() {
		return libretto;
	}


	public void setLibretto(HashMap libretto) {
		this.libretto = libretto;
	}

	public HashMap getCorsiScelti() {
		return corsiScelti;
	}


	public void setCorsiScelti(HashMap corsiScelti) {
		this.corsiScelti = corsiScelti;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((matricola == null) ? 0 : matricola.hashCode());
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
		UtenteRegistrato other = (UtenteRegistrato) obj;
		if (matricola == null) {
			if (other.matricola != null)
				return false;
		} else if (!matricola.equals(other.matricola))
			return false;
		return true;
	}

	

}
