package projekt;


public class Ucebnice extends Kniha {
	private int rocnik;

    public Ucebnice(String nazevKnihy, String jmenoAutora, int rokVydani, boolean dostupnost, int rocnik) {
        super(nazevKnihy, jmenoAutora, rokVydani, dostupnost);
        this.rocnik = rocnik;
    }
    public int getRocnik() {
        return rocnik;
    }

    public void setRocnik(int rocnik) {
        this.rocnik = rocnik;
    }
}
