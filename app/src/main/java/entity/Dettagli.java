package entity;

public class Dettagli {

	private String giorno;
	private String oraInizio;
	private String orafine;
	private Aula aula;
	
	public Dettagli() {
		// TODO Auto-generated constructor stub
	}

	//ritornano gg, mm e aaaa dalla data in formato gg/mm/aaaa
	/*public int getDay() {
		return Integer.parseInt(giorno.substring(0, giorno.indexOf("/")-1));
	}
	public int getMonth() {
		return Integer.parseInt(giorno.substring(0, giorno.indexOf("/")-1));
	}
	public int getYear() {
		return Integer.parseInt(giorno.substring(giorno.indexOf("/", giorno.indexOf("/")+1)+1));
	}*/
	//ritornano ore e min dall orario in formato hh:mm
	public int getStartHour() {
		return Integer.parseInt(oraInizio.substring(0, oraInizio.indexOf(":")));
	}
	public int getStartMin() {
		return Integer.parseInt(oraInizio.substring(oraInizio.indexOf(":")+1));
	}

	public String getGiorno() {
		return giorno;
	}

	public void setGiorno(String giorno) {
		this.giorno = giorno;
	}

	public String getOraInizio() {
		return oraInizio;
	}

	public void setOraInizio(String oraInizio) {
		this.oraInizio = oraInizio;
	}

	public String getOrafine() {
		return orafine;
	}

	public void setOrafine(String orafine) {
		this.orafine = orafine;
	}

	public Aula getAula() {
		return aula;
	}

	public void setAula(Aula aula) {
		this.aula = aula;
	}
	

}
