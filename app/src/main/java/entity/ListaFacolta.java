package entity;

import java.util.ArrayList;

public class ListaFacolta {
	
	private ArrayList<CorsoDiLaurea> facolta;

	public ListaFacolta() {
		// TODO Auto-generated constructor stub
		this.facolta=new ArrayList<CorsoDiLaurea>();
	}

	public ArrayList<CorsoDiLaurea> getFacolta() {
		return facolta;
	}

	public void setFacolta(ArrayList<CorsoDiLaurea> facolta) {
		this.facolta = facolta;
	}
	
}
