package entity;

import java.util.GregorianCalendar;

public class Esame {

	private Corso corso;
	private String Data;
	private int voto;
	private String UID;

	public Esame() {
		// TODO Auto-generated constructor stub
	}

	public Esame(Corso corso, String data, int voto, String UID) {
		super();
		this.corso = corso;
		this.Data = data;
		this.voto = voto;
		this.UID=UID;
	}

	public Corso getCorso() {
		return corso;
	}

	public void setCorso(Corso corso) {
		this.corso = corso;
	}

	public String getData() {
		return Data;
	}

	public void setData(String data) {
		Data = data;
	}

	public int getVoto() {
		return voto;
	}

	public void setVoto(int voto) {
		this.voto = voto;
	}

	public String getUID() {
		return Data;
	}

	public void setUID(String ID) {
		UID = ID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((corso == null) ? 0 : corso.hashCode());
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
		Esame other = (Esame) obj;
		if (corso == null) {
			if (other.corso != null)
				return false;
		} else if (!corso.equals(other.corso))
			return false;
		return true;
	}

	
}
