package entity;

import java.util.ArrayList;

public class Corso {
	
	private String Codice;
	private String nome;
	private int CFU;
	private boolean semestre; //0 primo semestre 1 secondo semestre
	private ArrayList<Dettagli> dettagli;
	private Professore p;

	public Corso() {
		// TODO Auto-generated constructor stub
		this.dettagli=new ArrayList<Dettagli>();
	}

	public Corso(String codice, String nome, int cFU, boolean semestre, ArrayList<Dettagli> dettagli,Professore p) {
		super();
		Codice = codice;
		this.nome = nome;
		CFU = cFU;
		this.semestre = semestre;
		this.dettagli = dettagli;
		this.p=p;
	}

	public String getCodice() {
		return Codice;
	}

	public void setCodice(String codice) {
		Codice = codice;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getCFU() {
		return CFU;
	}

	public void setCFU(int cFU) {
		CFU = cFU;
	}

	public boolean isSemestre() {
		return semestre;
	}

	public void setSemestre(boolean semestre) {
		this.semestre = semestre;
	}

	public ArrayList<Dettagli> getDettagli() {
		return dettagli;
	}

	public void setDettagli(ArrayList<Dettagli> dettagli) {
		this.dettagli = dettagli;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Codice == null) ? 0 : Codice.hashCode());
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
		Corso other = (Corso) obj;
		if (Codice == null) {
			if (other.Codice != null)
				return false;
		} else if (!Codice.equals(other.Codice))
			return false;
		return true;
	}
	
	
}
