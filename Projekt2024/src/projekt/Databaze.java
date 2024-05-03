package projekt;

//TODO načtení a uložení do databáze

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Comparator;


public class Databaze {
	 private Connection pripojeni;
	 private List<Kniha> knihy;

	    public Databaze() {
	    	// Nastavení připojení k databázi
	    	 	
	        try {
	            pripojeni = DriverManager.getConnection("jdbc:sqlite:funguj.db");
	        } catch (SQLException e) {
	            System.out.println("Chyba při připojování k databázi: " + e.getMessage());
	           
	        }
	    	
	   // }
	       /* public void insertNewUser(String email, String name, String surname) {
	            if (email == null || name == null || surname == null)
	              throw new NullPointerException("Email, name and surname must be set.");

	            

	            String insertUser = "INSERT INTO  kniha" + "(email,name,surname)" + "VALUES(?,?,?)";

	            try (PreparedStatement prStmt = conn.prepareStatement(insertUser)) {
	              prStmt.setString(1, email);
	              prStmt.setString(2, name);
	              prStmt.setString(3, surname);

	              prStmt.executeUpdate();

	              System.out.println("Nov� u�ivatel byl vlo�en do datab�ze!");
	            } catch (SQLException e) {
	              System.out.println("U�ivatel u� byl vlo�en nebo jste zadali �patn� SQL p��kaz INSERT");
	              // e.printStackTrace();
	            }*/
	           
	    
	        this.knihy = new ArrayList<>();
	    }

	  //Metoda na přidání knihy
	    public void pridejKnihu(Kniha kniha1) {
	        knihy.add(kniha1);
	    }
	    
	    //Metoda na ověření názvu knihy
	    public boolean overNazev(String nazevKnihy) {
	        for (Kniha kniha1 : knihy) {
	        	if (kniha1.getNazevKnihy().equals(nazevKnihy)) {	
	        		return true;
	        	}
	        }
	        return false;
	    }

	    //Metoda na ověření názvu knihy
	    public boolean overAutora(String jmenoAutora) {
	        for (Kniha kniha1 : knihy) {
	        	if (kniha1.getJmenoAutora().equals(jmenoAutora)) {	
	        		return true;
	        	}
	        	
	        }
	        return false;
	    }
	    
	    public void upravKnihu(String novyNazev, String noveJmenoAutora, int novyRokVydani, boolean novaDostupnost) {
	    	for (Kniha kniha1 : knihy) {
	            if (kniha1.getNazevKnihy().equals(novyNazev)) {
	                kniha1.setJmenoAutora(noveJmenoAutora);
	                kniha1.setRokVydani(novyRokVydani);
	                kniha1.setDostupnost(novaDostupnost);
	                break;
	            }
	    	 } 
	    }

	    // Metoda pro smazání knihy
	    public void smazKnihu(String nazevKnihy) {
	        if(knihy.removeIf(kniha1 -> kniha1.getNazevKnihy().equals(nazevKnihy))) {
	        	System.out.println("-----------Kniha " + nazevKnihy + " byla smazána z knihovny!---------------");
	        }
	        else
	        	System.out.println("-----------Kniha " + nazevKnihy + " nebyla nalezena!---------------");
	    }
	    

	    // Metoda pro označení knihy jako vypůjčené/vrácené
	    public void oznacKnihy(String nazevKnihy, boolean dostupnost) {
	        for (Kniha kniha1 : knihy) {
	            if (kniha1.getNazevKnihy().equals(nazevKnihy)) {
	                kniha1.setDostupnost(dostupnost);
	                break;
	            }
	        }
	    }

	    // Metoda pro výpis všech knih
	    public void vypisKnihy() {
	    	
	    	 Comparator<Kniha> comparator = Comparator.comparing(Kniha::getNazevKnihy);

	    	    // Seřazení knih podle abecedy
	    	    Collections.sort(knihy, comparator);
	    	
	        for (Kniha kniha1 : knihy) {
	            System.out.print("Název: "+kniha1.getNazevKnihy()+ "		Autor: " + kniha1.getJmenoAutora()+ "		Rok vydání: "+ kniha1.getRokVydani()+ "	Dostupnost: "+ kniha1.getDostupnost());
	        
	         // Pokud je kniha typu Román
	            if (kniha1 instanceof Roman) {
	                Roman roman = (Roman) kniha1;
	                System.out.println(" Typ: Román Žánr: " + roman.getZanr());
	            }
	            // Pokud je kniha typu Učebnice
	            else if (kniha1 instanceof Ucebnice) {
	                Ucebnice ucebnice = (Ucebnice) kniha1;
	                System.out.println(" Typ: Učebnice pro " + ucebnice.getRocnik()+". ročník");
	            }
	        }
	            
	        
	        
	        System.out.println("------------------------");
	    }

	    // Metoda pro vyhledání knihy
	    public void vyhledejKnihu(String nazevKnihy) {
	    	
	        for (Kniha kniha1 : knihy) {
	            if (kniha1.getNazevKnihy().equals(nazevKnihy)) {
	            	
	            	System.out.print("Název: "+kniha1.getNazevKnihy()+ "	Autor: " + kniha1.getJmenoAutora()+ "	Rok vydání: "+ kniha1.getRokVydani()+ "	Dostupnost: "+ kniha1.getDostupnost());
	            	 // Pokud je kniha typu Román
		            if (kniha1 instanceof Roman) {
		                Roman roman = (Roman) kniha1;
		                System.out.println(" Typ: Román Žánr: " + roman.getZanr());
		            }
		            // Pokud je kniha typu Učebnice
		            else if (kniha1 instanceof Ucebnice) {
		                Ucebnice ucebnice = (Ucebnice) kniha1;
		                System.out.println(" Typ: Učebnice pro " + ucebnice.getRocnik()+". ročník");
		            }
	                break;
	            }
	        }
	        System.out.println("------------------------");
	    }

	    // Metoda pro výpis všech knih daného autora
	    public void vypisKnihyAutor(String jmenoAutora) {
	       
	    	 List<Kniha> knihyAutora = new ArrayList<>();
	    	for (Kniha kniha : knihy) {
	            if (kniha.getJmenoAutora().equals(jmenoAutora)) {
	                knihyAutora.add(kniha);
	            }
	        }

	        // Seřadíme seznam knih chronologicky od nejstarší po nejmladší
	        Collections.sort(knihyAutora, Comparator.comparingInt(Kniha::getRokVydani));

	        // Vypíšeme knihy daného autora chronologicky
	        for (Kniha kniha : knihyAutora) {
	            System.out.println("Název: " + kniha.getNazevKnihy() + "	Autor: " + kniha.getJmenoAutora() + " Rok vydání: " + kniha.getRokVydani() /*+ " Dostupnost: " + kniha.getDostupnost()*/);
	        }
	        System.out.println("------------------------");
	    }
	    	
	    	/* for (Kniha kniha1 : knihy) {
	            if (kniha1.getJmenoAutora().equals(jmenoAutora)) {
	            	System.out.println("Název: "+kniha1.getNazevKnihy()+ "	Autor: " + kniha1.getJmenoAutora()+ "	Rok vydání: "+ kniha1.getRokVydani()+ "	Dostupnost: "+ kniha1.getDostupnost());
	            }
	        }
	        System.out.println("------------------------");*/
	    

	    // Metoda pro výpis všech knih daného žánru/učebnice pro určitý ročník
	   public void vypisKnihyZanr(String zanr) {
	        for (Kniha kniha1 : knihy) {
	            if (kniha1 instanceof Roman && ((Roman) kniha1).getZanr().equals(zanr)) {
	            	 System.out.println("Název: " + kniha1.getNazevKnihy() + "	Autor: " + kniha1.getJmenoAutora() + " Rok vydání: " + kniha1.getRokVydani() /*+ " Dostupnost: " + kniha.getDostupnost()*/);
	            } 

	        }
	        System.out.println("------------------------");
	    }

	    // Metoda pro výpis všech vypůjčených knih s informací o typu knihy
	    public void vypisVypujceneKnihy() {
	        for (Kniha kniha1 : knihy) {
	            if (!kniha1.getDostupnost()) {
	            	System.out.print("Název: "+kniha1.getNazevKnihy()+ "	Autor: " + kniha1.getJmenoAutora()+ "	Rok vydání: "+ kniha1.getRokVydani());
	                if (kniha1 instanceof Roman) {
	                    System.out.println("  Typ: Román");
	                } else if (kniha1 instanceof Ucebnice) {
	                    System.out.println("  Typ: Učebnice");
	                }
	            }
	        }
	        System.out.println("------------------------");
	    }

	    /*public void ulozDoSouboru(String nazevSouboru, String nazevKnihy) {
	    	for (Kniha kniha1 : knihy) {
	            if (kniha1.getNazevKnihy().equals(nazevKnihy)) {
	            	try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(nazevSouboru))) {
	    	            outputStream.writeObject(nazevKnihy);
	    	            System.out.println("Kniha byla úspěšně uložena do souboru " + nazevSouboru);
	    	        } catch (IOException e) {
	    	            System.out.println("Chyba při ukládání knihy do souboru: " + e.getMessage());
	    	        }
	                break;
	            }
	        }
	        
	    }*/
	    
	    public void ulozDoSouboru(String jmenoSouboru, String nazevKnihy) {
	        try {
	            FileWriter fw = new FileWriter(jmenoSouboru);
	            BufferedWriter out = new BufferedWriter(fw);
	            //out.write("Pocet " + knihy.size()); // Počet knih
	            //out.newLine();
	            for (Kniha kniha1 : knihy) {
	                if (kniha1.getNazevKnihy().equals(nazevKnihy)) {
	                    if (kniha1 instanceof Roman) {
	                        Roman roman = (Roman) kniha1;
	                        out.write("Román;" + kniha1.getNazevKnihy() + ";" + kniha1.getJmenoAutora() + ";" + kniha1.getRokVydani() + ";" + kniha1.getDostupnost() + ";" + roman.getZanr());
	                    } else if (kniha1 instanceof Ucebnice) {
	                        Ucebnice ucebnice = (Ucebnice) kniha1;
	                        out.write("Učebnice;" + kniha1.getNazevKnihy() + ";" + kniha1.getJmenoAutora() + ";" + kniha1.getRokVydani() + ";" + kniha1.getDostupnost() + ";" + ucebnice.getRocnik());
	                    }
	                    //out.newLine();
	                    System.out.println("-----------Kniha "+ nazevKnihy +" úspěšně uložena do souboru "+ jmenoSouboru+" ---------------");
	                }
	            }
	            out.close();
	            fw.close();
	        } catch (IOException e) {
	            System.out.println("Soubor nelze vytvořit");
	        }
	    }
	    
	    
	   /* public void ulozDoSouboru(String nazevSouboru, List<Kniha> seznamKnih) {
	        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(nazevSouboru))) {
	            outputStream.writeObject(seznamKnih);
	            System.out.println("Seznam knih byl úspěšně uložen do souboru " + nazevSouboru);
	        } catch (IOException e) {
	            System.out.println("Chyba při ukládání seznamu knih do souboru: " + e.getMessage());
	        }
	    }*/
	    
	    /*public void ulozDoSouboru(String nazevSouboru, List<Kniha> seznamKnih, String nazevKniha) {
	        for (Kniha kniha : seznamKnih) {
	            if (kniha.getNazevKnihy().equals(nazevKniha)) {
	                try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(nazevSouboru))) {
	                    outputStream.writeObject(kniha);
	                    System.out.println("Kniha '" + nazevKniha + "' byla úspěšně uložena do souboru " + nazevSouboru);
	                    return; // Uložili jsme knihu, takže opustíme metodu
	                } catch (IOException e) {
	                    System.out.println("Chyba při ukládání knihy do souboru: " + e.getMessage());
	                }
	            }
	        }
	        System.out.println("Kniha '" + nazevKniha + "' nebyla nalezena v seznamu.");
	    }*/
	    
	    

	    /*public List<Kniha> nactiZeSouboru(String nazevSouboru) {
	        List<Kniha> seznamKnih = null;
	        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(nazevSouboru))) {
	            seznamKnih = (List<Kniha>) inputStream.readObject();
	            System.out.println("Seznam knih byl úspěšně načten ze souboru " + nazevSouboru);
	        } catch (IOException | ClassNotFoundException e) {
	            System.out.println("Chyba při načítání seznamu knih ze souboru: " + e.getMessage());
	        }
	        return seznamKnih;
	    }*/

	   /* public void nactiZeSouboru(String jmenoSouboru) {
	        FileReader fr = null;
	        BufferedReader in = null;
	        try {
	            fr = new FileReader(jmenoSouboru);
	            in = new BufferedReader(fr);
	            String radek = in.readLine();
	            String oddelovac = ";";
	            String[] castiTextu = radek.split(oddelovac);
	            //if (castiTextu.length != 2)
	               // return false;
	            int pocetKnih = Integer.parseInt(castiTextu[1]);
	            for (int i = 0; i < pocetKnih; i++) {
	                radek = in.readLine();
	                castiTextu = radek.split(";");
	                if (castiTextu.length == 4) { // Předpokládejme, že informace o knize jsou ve formátu: nazevKnihy jmenoAutora rokVydani dostupnost
	                    String nazevKnihy = castiTextu[0];
	                    String jmenoAutora = castiTextu[1];
	                    int rokVydani = Integer.parseInt(castiTextu[2]);
	                    boolean dostupnost = Boolean.parseBoolean(castiTextu[3]);
	                    knihy.add(new Kniha(nazevKnihy, jmenoAutora, rokVydani, dostupnost));
	                }
	            }
	        } catch (IOException e) {
	            System.out.println("Soubor nelze otevřít");
	          // return false;
	        } catch (NumberFormatException e) {
	            System.out.println("Chyba integrity dat v souboru"+ e.getMessage());
	          // return false;
	        } finally {
	            try {
	                if (in != null) {
	                    in.close();
	                    fr.close();
	                }
	            } catch (IOException e) {
	                System.out.println("Soubor nelze zavřít");
	                //return false;
	            }
	        }

	       // return true;
	    }*/
	    
	/*    public void nactiZeSouboru(String jmenoSouboru) {
	        FileReader fr = null;
	        BufferedReader in = null;
	        try {
	            fr = new FileReader(jmenoSouboru);
	            in = new BufferedReader(fr);
	            String radek = in.readLine();
	            String oddelovac = ";";
	            String[] castiTextu = radek.split(oddelovac);
	            int pocetKnih = Integer.parseInt(castiTextu[1]);
	            for (int i = 0; i < pocetKnih; i++) {
	                radek = in.readLine();
	                castiTextu = radek.split(oddelovac);
	                if (castiTextu.length == 4) {
	                    String nazevKnihy = castiTextu[0];
	                    String jmenoAutora = castiTextu[1];
	                    int rokVydani = Integer.parseInt(castiTextu[2]);
	                    boolean dostupnost = Boolean.parseBoolean(castiTextu[3]);
	                    knihy.add(new Kniha(nazevKnihy, jmenoAutora, rokVydani, dostupnost));
	                }
	            }
	        } catch (IOException e) {
	            System.out.println("Soubor nelze otevřít: " + e.getMessage());
	        } catch (NumberFormatException e) {
	            System.out.println("Chyba integrity dat v souboru: " + e.getMessage());
	        } finally {
	            try {
	                if (in != null) {
	                    in.close();
	                    if (fr != null) {
	                        fr.close();
	                    }
	                }
	            } catch (IOException e) {
	                System.out.println("Soubor nelze zavřít: " + e.getMessage());
	            }
	        }
	    }*/
	    
	/*    public void nactiZeSouboru(String jmenoSouboru) {
	        FileReader fr = null;
	        BufferedReader in = null;
	        try {
	            fr = new FileReader(jmenoSouboru);
	            in = new BufferedReader(fr);
	            String radek = in.readLine();
	            String oddelovac = ";";
	            String[] castiTextu = radek.split(oddelovac);
	            int pocetKnih = Integer.parseInt(castiTextu[1]);
	            for (int i = 0; i < pocetKnih; i++) {
	                radek = in.readLine();
	                castiTextu = radek.split(oddelovac);
	                if (castiTextu.length == 4) {
	                    String nazevKnihy = castiTextu[0];
	                    String jmenoAutora = castiTextu[1].trim(); // Odstraníme přebytečné bílé znaky
	                    int rokVydani = Integer.parseInt(castiTextu[2]);
	                    boolean dostupnost = Boolean.parseBoolean(castiTextu[3]);
	                    knihy.add(new Kniha(nazevKnihy, jmenoAutora, rokVydani, dostupnost));
	                }
	            }
	        } catch (IOException e) {
	            System.out.println("Soubor nelze otevřít: " + e.getMessage());
	        } catch (NumberFormatException e) {
	            System.out.println("Chyba integrity dat v souboru: " + e.getMessage());
	        } finally {
	            try {
	                if (in != null) {
	                    in.close();
	                    if (fr != null) {
	                        fr.close();
	                    }
	                }
	            } catch (IOException e) {
	                System.out.println("Soubor nelze zavřít: " + e.getMessage());
	            }
	        }
	    }*/
	    
	    
	   /* public void nactiZeSouboru(String jmenoSouboru) {
	        FileReader fr = null;
	        BufferedReader in = null;
	        try {
	            fr = new FileReader(jmenoSouboru);
	            in = new BufferedReader(fr);
	            String radek;
	            while ((radek = in.readLine()) != null) {
	                System.out.println("Načtený řádek: " + radek);
	                // Zde můžete pokračovat v zpracování načtených dat
	            }
	        } catch (IOException e) {
	            System.out.println("Soubor nelze otevřít: " + e.getMessage());
	        } finally {
	            try {
	                if (in != null) {
	                    in.close();
	                    if (fr != null) {
	                        fr.close();
	                    }
	                }
	            } catch (IOException e) {
	                System.out.println("Soubor nelze zavřít: " + e.getMessage());
	            }
	        }
	    }*/
	    
	    
	 /*   public void nactiZeSouboru(String jmenoSouboru) {
	        FileReader fr = null;
	        BufferedReader in = null;
	        try {
	            fr = new FileReader(jmenoSouboru);
	            in = new BufferedReader(fr);
	            String radek;
	            while ((radek = in.readLine()) != null) {
	                System.out.println("Načtený řádek: " + radek);
	                String[] casti = radek.split(";");
	                for (String cast : casti) {
	                    System.out.println("Část: " + cast);
	                }
	                // Zde můžete pokračovat v zpracování načtených dat
	            }
	        } catch (IOException e) {
	            System.out.println("Soubor nelze otevřít: " + e.getMessage());
	        } finally {
	            try {
	                if (in != null) {
	                    in.close();
	                    if (fr != null) {
	                        fr.close();
	                    }
	                }
	            } catch (IOException e) {
	                System.out.println("Soubor nelze zavřít: " + e.getMessage());
	            }
	        }
	    }*/


	   /* FUNKCNI public void nactiZeSouboru(String jmenoSouboru) {
	        FileReader fr = null;
	        BufferedReader in = null;
	        try {
	            fr = new FileReader(jmenoSouboru);
	            in = new BufferedReader(fr);
	            String radek;
	            while ((radek = in.readLine()) != null) {
	               // System.out.println("Načtený řádek: " + radek);
	                String[] casti = radek.split(";");
	                if (casti.length == 4) { // Kontrola, zda má řádek správný počet částí
	                    String nazevKnihy = casti[0];
	                    String jmenoAutora = casti[1];
	                    int rokVydani = Integer.parseInt(casti[2]);
	                    boolean dostupnost = Boolean.parseBoolean(casti[3]);
	                    knihy.add(new Kniha(nazevKnihy, jmenoAutora, rokVydani, dostupnost));
	                    System.out.println("-----------Kniha "+ nazevKnihy +" úspěšně načtena ze souboru "+ jmenoSouboru +" ---------------");
	                } else {
	                    System.out.println("Chybný formát řádku: " + radek);
	                }
	            }
	        } catch (IOException e) {
	            System.out.println("Chyba při čtení ze souboru: " + e.getMessage());
	        } catch (NumberFormatException e) {
	            System.out.println("Chyba při převodu číselné hodnoty: " + e.getMessage());
	        } finally {
	            try {
	                if (in != null) {
	                    in.close();
	                }
	                if (fr != null) {
	                    fr.close();
	                }
	            } catch (IOException e) {
	                System.out.println("Chyba při zavírání souboru: " + e.getMessage());
	            }
	        }
	        
	    }*/
	    
	    public void nactiZeSouboru(String jmenoSouboru) {
	        FileReader fr = null;
	        BufferedReader in = null;
	        try {
	            fr = new FileReader(jmenoSouboru);
	            in = new BufferedReader(fr);
	            String radek;
	            while ((radek = in.readLine()) != null) {
	                // System.out.println("Načtený řádek: " + radek);
	                String[] casti = radek.split(";");
	                if (casti.length >= 5) { // Kontrola, zda má řádek správný počet částí
	                    String typKnihy = casti[0];
	                    String nazevKnihy = casti[1];
	                    String jmenoAutora = casti[2];
	                    int rokVydani = Integer.parseInt(casti[3]);
	                    boolean dostupnost = Boolean.parseBoolean(casti[4]);
	                    if (typKnihy.equals("Román")) {
	                        if (casti.length == 6) {
	                            String zanr = casti[5];
	                            knihy.add(new Roman(nazevKnihy, jmenoAutora, rokVydani, dostupnost, zanr));
	                        } else {
	                            System.out.println("Chybějící informace o žánru: " + radek);
	                        }
	                    } else if (typKnihy.equals("Učebnice")) {
	                        if (casti.length == 6) {
	                            int rocnik = Integer.parseInt(casti[5]);
	                            knihy.add(new Ucebnice(nazevKnihy, jmenoAutora, rokVydani, dostupnost, rocnik));
	                        } else {
	                            System.out.println("Chybějící informace o ročníku: " + radek);
	                        }
	                    } else {
	                        System.out.println("Neznámý typ knihy: " + typKnihy);
	                    }
	                    System.out.println("-----------Kniha "+ nazevKnihy +" úspěšně načtena ze souboru "+ jmenoSouboru +" ---------------");
	                } else {
	                    System.out.println("Chybný formát řádku: " + radek);
	                }
	            }
	        } catch (IOException e) {
	            System.out.println("Chyba při čtení ze souboru: " + e.getMessage());
	        } catch (NumberFormatException e) {
	            System.out.println("Chyba při převodu číselné hodnoty: " + e.getMessage());
	        } finally {
	            try {
	                if (in != null) {
	                    in.close();
	                }
	                if (fr != null) {
	                    fr.close();
	                }
	            } catch (IOException e) {
	                System.out.println("Chyba při zavírání souboru: " + e.getMessage());
	            }
	        }
	    }

	    
	    
	    
	
	    // Metoda pro uložení veškerých informací do SQL databáze
	    
	    	public void ulozDoDatabaze() {
	    	    try {
	    	        Statement statement = pripojeni.createStatement();
	    	        // SQL dotaz pro uložení dat
	    	        String sqlDotaz = "INSERT INTO knihy (nazev, autor, rok_vydani, dostupnost) VALUES ('NazevKnihy', 'JmenoAutora', 2024, true)";
	    	        statement.executeUpdate(sqlDotaz);
	    	        System.out.println("Data byla úspěšně uložena do databáze.");
	    	    } catch (SQLException e) {
	    	        System.out.println("Chyba při ukládání dat do databáze: " + e.getMessage());
	    	    }
	    	}

	    	// Metoda pro načtení veškerých informací z SQL databáze
	    	public void nactiZDatabaze() {
	    	    try {
	    	        Statement statement = pripojeni.createStatement();
	    	        // SQL dotaz pro načtení dat
	    	        String sqlDotaz = "SELECT * FROM knihy";
	    	        statement.executeQuery(sqlDotaz);
	    	        System.out.println("Data byla úspěšně načtena z databáze.");
	    	    } catch (SQLException e) {
	    	        System.out.println("Chyba při načítání dat z databáze: " + e.getMessage());
	    	    }
	    	}
	    	
	    	public void disconnect() {
	            if (pripojeni != null) {
	                try {
	                    pripojeni.close();
	                } catch (SQLException ex) {
	                    System.out.println(ex.getMessage());
	                }
	            }
	        }
		}

