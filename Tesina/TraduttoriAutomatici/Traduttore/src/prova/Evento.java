package prova;

public class Evento {

	String idElemento = null;
	String item = null;
	String action = null;
	String pos = null;
	String rif = null;
	
	public Evento(String idElemento, String item, String action, String pos , String rif) {
		super();
		this.idElemento = idElemento;
		this.item = item;
		this.action = action;
		this.pos = pos;
		this.rif = rif;
	}

	public Evento() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public String getIdElemento() {
		return idElemento;
	}
	public void setIdElemento(String idElemento) {
		this.idElemento = idElemento;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	

	public String getRif() {
		return rif;
	}

	public void setRif(String rif) {
		this.rif = rif;
	}

	@Override
	public String toString() {
		return "Evento:	" + "id: "+idElemento + "		item: " + item
				+ "		action: " + action + "		pos: "+ pos + "		rif: "+rif ;
	}
	
	
}
