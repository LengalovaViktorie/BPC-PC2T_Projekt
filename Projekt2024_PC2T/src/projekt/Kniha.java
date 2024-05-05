package projekt;

public class Kniha {
	
	private String nazevKnihy;
    private String jmenoAutora;
    private int rokVydani;
    private boolean dostupnost;

	public Kniha(String nazevKnihy, String jmenoAutora, int rokVydani, boolean dostupnost)
	{
		this.nazevKnihy = nazevKnihy;
		this.jmenoAutora = jmenoAutora;
		this.rokVydani = rokVydani;
		this.dostupnost = dostupnost;
	}
	
	public String getNazevKnihy()
	{
		return nazevKnihy;
	}
	
	public void setNazevKnihy(String nazevKnihy)
	{
		this.nazevKnihy = nazevKnihy;
	}
	
	public String getJmenoAutora()
	{
		return jmenoAutora;
	}
	
	public void setJmenoAutora(String jmenoAutora)
	{
		this.jmenoAutora=jmenoAutora;
	}
	
	public int getRokVydani()
	{
		return rokVydani;
	}
	
	public void setRokVydani(int rokVydani)
	{
		this.rokVydani=rokVydani;
	}
	
	public boolean getDostupnost() {
		return dostupnost;
	}
	
	public void setDostupnost(boolean dostupnost) {
		this.dostupnost=dostupnost;
	}

}
