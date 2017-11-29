package entity;

public class Aula {
	
	private String id;
	private String piano;
	private int capienza;
	private Edificio ed;
	

	public Aula() {
		// TODO Auto-generated constructor stub
	}

	public Aula(String id, String piano, int capienza, Edificio ed) {
		super();
		this.id = id;
		this.piano = piano;
		this.capienza = capienza;
		this.ed=ed;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPiano() {
		return piano;
	}

	public void setPiano(String piano) {
		this.piano = piano;
	}

	public int getCapienza() {
		return capienza;
	}

	public void setCapienza(int capienza) {
		this.capienza = capienza;
	}

	
	public Edificio getEd() {
		return ed;
	}

	public void setEd(Edificio ed) {
		this.ed = ed;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Aula other = (Aula) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
