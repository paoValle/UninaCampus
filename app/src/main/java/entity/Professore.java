package entity;

public class Professore {
	
	private String Nome;
	private String Cognome;
	private String email;
	private String telefono;

	public Professore() {
		// TODO Auto-generated constructor stub
	}
		
	public Professore(String nome, String cognome, String email, String telefono) {
		super();
		Nome = nome;
		Cognome = cognome;
		this.email = email;
		this.telefono = telefono;
	}

	public String getNome() {
		return Nome;
	}

	public void setNome(String nome) {
		Nome = nome;
	}

	public String getCognome() {
		return Cognome;
	}

	public void setCognome(String cognome) {
		Cognome = cognome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Cognome == null) ? 0 : Cognome.hashCode());
		result = prime * result + ((Nome == null) ? 0 : Nome.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		Professore other = (Professore) obj;
		if (Cognome == null) {
			if (other.Cognome != null)
				return false;
		} else if (!Cognome.equals(other.Cognome))
			return false;
		if (Nome == null) {
			if (other.Nome != null)
				return false;
		} else if (!Nome.equals(other.Nome))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}
	
    
}
