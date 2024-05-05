package projekt;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Comparator;

public class Databaze {
	private Connection pripojeni;
	private List<Kniha> knihy;

	public Databaze() {

		this.knihy = new ArrayList<>();

		try {

			pripojeni = DriverManager.getConnection("jdbc:sqlite:databaze.db");
			System.out.print("Databáze spojena, ");
		} catch (SQLException e) {
			System.out.println("Chyba při připojování k databázi: " + e.getMessage());

		}

	}

	public void pridejKnihu(Kniha kniha1) {
		knihy.add(kniha1);
	}

	public boolean overNazev(String nazevKnihy) {
		for (Kniha kniha1 : knihy) {
			if (kniha1.getNazevKnihy().equals(nazevKnihy)) {
				return true;
			}
		}
		return false;
	}

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

	public void smazKnihu(String nazevKnihy) {
		if (knihy.removeIf(kniha1 -> kniha1.getNazevKnihy().equals(nazevKnihy))) {
			System.out.println("-----------Kniha " + nazevKnihy + " byla smazána z knihovny!---------------");
		} else
			System.out.println("-----------Kniha " + nazevKnihy + " nebyla nalezena!---------------");
	}

	public void oznacKnihy(String nazevKnihy, boolean dostupnost) {
		for (Kniha kniha1 : knihy) {
			if (kniha1.getNazevKnihy().equals(nazevKnihy)) {
				kniha1.setDostupnost(dostupnost);
				break;
			}
		}
	}

	public void vypisKnihy() {

		Comparator<Kniha> comparator = Comparator.comparing(Kniha::getNazevKnihy);

		Collections.sort(knihy, comparator);

		for (Kniha kniha1 : knihy) {
			System.out.println("Název: " + kniha1.getNazevKnihy() + "\nAutor: " + kniha1.getJmenoAutora()
					+ "\nRok vydání: " + kniha1.getRokVydani());

			if (!kniha1.getDostupnost() == true) {
				System.out.println("Dostupnost: vypůjčená");
			} else {
				System.out.println("Dostupnost: dostupná");
			}

			if (kniha1 instanceof Roman) {
				Roman roman = (Roman) kniha1;
				System.out.println("Typ: Román, žánr: " + roman.getZanr() + "\n======================");
			} else if (kniha1 instanceof Ucebnice) {
				Ucebnice ucebnice = (Ucebnice) kniha1;
				System.out.println("Typ: Učebnice pro " + ucebnice.getRocnik() + ". ročník \n=======================");
			}
		}

		System.out.println("----------Konec--------------");
	}

	public void vyhledejKnihu(String nazevKnihy) {

		for (Kniha kniha1 : knihy) {
			if (kniha1.getNazevKnihy().equals(nazevKnihy)) {

				System.out.println("Název: " + kniha1.getNazevKnihy() + "\nAutor: " + kniha1.getJmenoAutora()
						+ "\nRok vydání: " + kniha1.getRokVydani());

				if (kniha1.getDostupnost() == true) {
					System.out.println("Dostupnost: dostupná");
				} else
					System.out.println("Dostupnost: vypůjčená");

				if (kniha1 instanceof Roman) {
					Roman roman = (Roman) kniha1;
					System.out.println(" Typ: Román Žánr: " + roman.getZanr());
				} else if (kniha1 instanceof Ucebnice) {
					Ucebnice ucebnice = (Ucebnice) kniha1;
					System.out.println("Typ: Učebnice pro " + ucebnice.getRocnik() + ". ročník");
				}
				break;
			}
		}

		System.out.println("======================");
	}

	public void vypisKnihyAutor(String jmenoAutora) {

		List<Kniha> knihyAutora = new ArrayList<>();
		for (Kniha kniha : knihy) {
			if (kniha.getJmenoAutora().equals(jmenoAutora)) {
				knihyAutora.add(kniha);
			}
		}

		Collections.sort(knihyAutora, Comparator.comparingInt(Kniha::getRokVydani));

		for (Kniha kniha : knihyAutora) {
			System.out.println("Název: " + kniha.getNazevKnihy() + " Rok vydání: " + kniha.getRokVydani()
					+ "\n======================");
		}
		System.out.println("-----------Konec-------------");
	}

	public void vypisKnihyZanr(String zanr) {
		for (Kniha kniha1 : knihy) {
			if (kniha1 instanceof Roman && ((Roman) kniha1).getZanr().equals(zanr)) {
				System.out.println("Název: " + kniha1.getNazevKnihy() + "\nAutor: " + kniha1.getJmenoAutora()
						+ "\nRok vydání: " + kniha1.getRokVydani() + "\n======================");
			}

		}
		System.out.println("-----------Konec-------------");
	}

	public void vypisVypujceneKnihy() {
		for (Kniha kniha1 : knihy) {
			if (!kniha1.getDostupnost()) {
				System.out.print("Název: " + kniha1.getNazevKnihy() + "\nAutor: " + kniha1.getJmenoAutora()
						+ "\nRok vydání: " + kniha1.getRokVydani());
				if (kniha1 instanceof Roman) {
					System.out.println("\nTyp: Román" + "\n======================");
				} else if (kniha1 instanceof Ucebnice) {
					System.out.println("\nTyp: Učebnice" + "\n======================");
				}
			}
		}
		System.out.println("------------Konec------------");
	}

	public void ulozDoSouboru(String jmenoSouboru, String nazevKnihy) {
		try {
			FileWriter fw = new FileWriter(jmenoSouboru);
			BufferedWriter out = new BufferedWriter(fw);
			for (Kniha kniha1 : knihy) {
				if (kniha1.getNazevKnihy().equals(nazevKnihy)) {
					if (kniha1 instanceof Roman) {
						Roman roman = (Roman) kniha1;
						out.write("Román;" + kniha1.getNazevKnihy() + ";" + kniha1.getJmenoAutora() + ";"
								+ kniha1.getRokVydani() + ";" + kniha1.getDostupnost() + ";" + roman.getZanr());
					} else if (kniha1 instanceof Ucebnice) {
						Ucebnice ucebnice = (Ucebnice) kniha1;
						out.write("Učebnice;" + kniha1.getNazevKnihy() + ";" + kniha1.getJmenoAutora() + ";"
								+ kniha1.getRokVydani() + ";" + kniha1.getDostupnost() + ";" + ucebnice.getRocnik());
					}
					System.out.println("-----------Kniha " + nazevKnihy + " úspěšně uložena do souboru " + jmenoSouboru
							+ " ---------------");
				}
			}
			out.close();
			fw.close();
		} catch (IOException e) {
			System.out.println("Soubor nelze vytvořit");
		}
	}

	public void nactiZeSouboru(String jmenoSouboru) {
		FileReader fr = null;
		BufferedReader in = null;
		try {
			fr = new FileReader(jmenoSouboru);
			in = new BufferedReader(fr);
			String radek;
			while ((radek = in.readLine()) != null) {
				String[] casti = radek.split(";");
				if (casti.length >= 5) {
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
					System.out.println("-----------Kniha " + nazevKnihy + " úspěšně načtena ze souboru " + jmenoSouboru
							+ " ---------------");
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

	public void ulozDoDatabaze() {
		try {
			pripojeni = DriverManager.getConnection("jdbc:sqlite:databaze.db");

			String sqlDeleteAll = "DELETE FROM Knihy; DELETE FROM Roman; DELETE FROM Ucebnice; DELETE FROM Autor; DELETE FROM AutorKnihy;";
			Statement statement = pripojeni.createStatement();
			statement.executeUpdate(sqlDeleteAll);

			String sqlKnihaExists = "SELECT 1 FROM Knihy WHERE nazevKniha = ?";
			PreparedStatement psKnihaExists = pripojeni.prepareStatement(sqlKnihaExists);

			String sqlKniha = "INSERT OR IGNORE INTO Knihy (nazevKniha, rok, dostupnost) VALUES (?, ?, ?)";
			PreparedStatement psKniha = pripojeni.prepareStatement(sqlKniha, Statement.RETURN_GENERATED_KEYS);

			String sqlRoman = "INSERT INTO Roman (IdKniha, nazevZanru) VALUES (?, ?)";
			PreparedStatement psRoman = pripojeni.prepareStatement(sqlRoman);

			String sqlUcebnice = "INSERT INTO Ucebnice (IdKniha, Doporuceni) VALUES (?, ?)";
			PreparedStatement psUcebnice = pripojeni.prepareStatement(sqlUcebnice);

			String sqlAutorExists = "SELECT IdAutor FROM Autor WHERE jmenoAutora = ?";
			PreparedStatement psAutorExists = pripojeni.prepareStatement(sqlAutorExists);

			String sqlAutor = "INSERT INTO Autor (jmenoAutora) VALUES (?)";
			PreparedStatement psAutor = pripojeni.prepareStatement(sqlAutor);

			String sqlAutorKnihy = "INSERT INTO AutorKnihy (IdAutor, IdKniha) VALUES (?, ?)";
			PreparedStatement psAutorKnihy = pripojeni.prepareStatement(sqlAutorKnihy);

			for (Kniha kniha : knihy) {
				psKnihaExists.setString(1, kniha.getNazevKnihy());
				ResultSet resultSet = psKnihaExists.executeQuery();
				if (resultSet.next()) {
					System.out.println("Kniha '" + kniha.getNazevKnihy() + "' již existuje v databázi. Ignorováno.");
					continue;
				}

				psKniha.setString(1, kniha.getNazevKnihy());
				psKniha.setInt(2, kniha.getRokVydani());
				psKniha.setBoolean(3, kniha.getDostupnost());
				psKniha.executeUpdate();

				ResultSet generatedKeys = psKniha.getGeneratedKeys();
				int idKniha = -1;
				if (generatedKeys.next()) {
					idKniha = generatedKeys.getInt(1);
				}

				if (kniha instanceof Roman) {
					Roman roman = (Roman) kniha;
					psRoman.setInt(1, idKniha);
					psRoman.setString(2, roman.getZanr());
					psRoman.executeUpdate();
				} else if (kniha instanceof Ucebnice) {
					Ucebnice ucebnice = (Ucebnice) kniha;
					psUcebnice.setInt(1, idKniha);
					psUcebnice.setInt(2, ucebnice.getRocnik());
					psUcebnice.executeUpdate();
				}

				psAutorExists.setString(1, kniha.getJmenoAutora());
				resultSet = psAutorExists.executeQuery();
				int idAutor;
				if (resultSet.next()) {
					idAutor = resultSet.getInt(1);
				} else {
					psAutor.setString(1, kniha.getJmenoAutora());
					psAutor.executeUpdate();
					ResultSet generatedKeysAutor = psAutor.getGeneratedKeys();
					if (generatedKeysAutor.next()) {
						idAutor = generatedKeysAutor.getInt(1);
					} else {
						throw new SQLException("Vložení nového autora se nezdařilo.");
					}
				}

				psAutorKnihy.setInt(1, idAutor);
				psAutorKnihy.setInt(2, idKniha);
				psAutorKnihy.executeUpdate();
			}
			System.out.println("Data úspěšně uložena do databáze");
		} catch (SQLException e) {
			System.out.println("Chyba při připojování k databázi: " + e.getMessage());
		} finally {
			try {
				if (pripojeni != null) {
					pripojeni.close();
				}
			} catch (SQLException e) {
				System.out.println("Chyba při uzavírání spojení s databází: " + e.getMessage());
			}
		}
	}

	public void nactiZDatabaze() {
		try {
			String sql = "SELECT Knihy.nazevKniha, Autor.jmenoAutora, Knihy.rok, Knihy.dostupnost, Roman.nazevZanru, Ucebnice.Doporuceni "
					+ "FROM Knihy " + "INNER JOIN AutorKnihy ON Knihy.IdKniha = AutorKnihy.IdKniha "
					+ "INNER JOIN Autor ON AutorKnihy.IdAutor = Autor.IdAutor "
					+ "LEFT JOIN Roman ON Knihy.IdKniha = Roman.IdKniha "
					+ "LEFT JOIN Ucebnice ON Knihy.IdKniha = Ucebnice.IdKniha";

			Statement statement = pripojeni.createStatement();

			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				String nazevKnihy = resultSet.getString("nazevKniha");
				String jmenoAutora = resultSet.getString("jmenoAutora");
				int rok = resultSet.getInt("rok");
				boolean dostupnost = resultSet.getBoolean("dostupnost");
				String zanr = resultSet.getString("nazevZanru");
				int rocnik = resultSet.getInt("Doporuceni");

				if (zanr != null) {
					knihy.add(new Roman(nazevKnihy, jmenoAutora, rok, dostupnost, zanr));
				} else if (rocnik != 0) {
					knihy.add(new Ucebnice(nazevKnihy, jmenoAutora, rok, dostupnost, rocnik));
				} else {
					System.out.println("Nepodařilo se určit typ knihy pro: " + nazevKnihy);
				}
			}
		} catch (SQLException e) {
			System.out.println("Chyba při připojování k databázi: " + e.getMessage());
		} finally {
			try {
				if (pripojeni != null) {
					pripojeni.close();
				}
			} catch (SQLException e) {
				System.out.println("Chyba při uzavírání spojení s databází: " + e.getMessage());
			}
		}
		System.out.println("Knihy z databáze úspěšně načteny");
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
