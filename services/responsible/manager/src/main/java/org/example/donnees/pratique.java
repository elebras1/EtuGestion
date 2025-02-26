package org.example.donnees;

public class pratique implements java.io.Serializable {

	private static final long serialVersionUID = 7482361721121315834L;
	
	private int code_sportif;
	
	private int code_discipline;

	public int getCode_sportif() {
		return code_sportif;
	}

	public void setCode_sportif(int code_sportif) {
		this.code_sportif = code_sportif;
	}

	public int getCode_discipline() {
		return code_discipline;
	}

	public void setCode_discipline(int code_discipline) {
		this.code_discipline = code_discipline;
	}

	public pratique(int code_sportif, int code_discipline) {
		super();
		this.code_sportif = code_sportif;
		this.code_discipline = code_discipline;
	}
	
}
