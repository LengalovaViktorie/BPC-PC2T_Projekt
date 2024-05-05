package projekt;

public class Roman extends Kniha {
	private String zanr;

	public Roman(String nazevKnihy, String jmenoAutora, int rokVydani, boolean dostupnost, String zanr) {
		super(nazevKnihy, jmenoAutora, rokVydani, dostupnost);
		this.zanr = zanr;
	}

	public String getZanr() {
		return zanr;
	}

	public void setZanr(String zanr) {
		this.zanr = zanr;
	}
}
