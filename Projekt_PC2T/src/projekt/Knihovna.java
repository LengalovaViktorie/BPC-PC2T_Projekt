package projekt;

import java.util.Scanner;

public class Knihovna {

	public static int pouzeCelaCisla(Scanner sc) {
		int cislo = 0;
		try {
			cislo = sc.nextInt();
		} catch (Exception e) {
			System.out.println("Nastala výjimka typu: " + e.toString());
			System.out.println("Zadejte prosím celé číslo ");
			sc.nextLine();
			cislo = pouzeCelaCisla(sc);
		}
		return cislo;
	}

	public static float pouzeCisla(Scanner sc) {
		float cislo = 0;
		try {
			cislo = sc.nextFloat();
		} catch (Exception e) {
			System.out.println("Nastala výjimka typu: " + e.toString());
			System.out.println("Zadejte prosím číslo ");
			sc.nextLine();
			cislo = pouzeCisla(sc);
		}
		return cislo;
	}

	public static boolean pouzeBoolean(Scanner sc) {
		boolean hodnota;
		try {
			hodnota = sc.nextBoolean();
		} catch (Exception e) {
			System.out.println("Nastala výjimka typu: " + e.toString());
			System.out.println("Zadejte prosím hodnoty true nebo false ");
			sc.nextLine();
			hodnota = pouzeBoolean(sc);
		}
		return hodnota;
	}

	public static void pauza(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			System.out.println("Pauza byla přerušena, nastala výjimka typu: " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Databaze mojeDatabaze = new Databaze();

		int volba;
		String nazevKnihy;
		String jmenoAutora;
		int rokVydani;
		boolean dostupnost;

		mojeDatabaze.nactiZDatabaze();

		boolean run = true;
		while (run) {
			System.out.println("Zvolte, jakou operaci chcete provést:");
			System.out.println("1.  Přidání nové knihy");
			System.out.println("2.  Úprava knihy");
			System.out.println("3.  Smazání knihy");
			System.out.println("4.  Označení knihy jako vypůjčené/vrácené");
			System.out.println("5.  Výpis knih");
			System.out.println("6.  Vyhledání knihy");
			System.out.println("7.  Výpis všech knih daného autora");
			System.out.println("8.  Výpis všech knih daného žánru");
			System.out.println("9.  Výpis všech vypůjčených knih");
			System.out.println("10. Uložení informace o vybrané knize do souboru");
			System.out.println("11. Načtení všech informací o dané knize ze souboru");
			System.out.println("12. Ukončení aplikace");

			volba = pouzeCelaCisla(sc);
			sc.nextLine();

			switch (volba) {
			case 1:
				System.out.println("Zadejte název knihy, kterou chcete přidat:");
				nazevKnihy = sc.nextLine();

				if (mojeDatabaze.overNazev(nazevKnihy) == true) {
					System.out.println(
							"-----------Kniha s názvem " + nazevKnihy + " již byla vytvořena! ---------------");
					System.out.println("-----------Přesměrování na hlavní stránku ---------------");
					pauza(2000);
					break;
				}

				System.out.println("Zadejte jméno autora:");
				jmenoAutora = sc.nextLine();
				System.out.println("Zadejte rok vydání:");
				rokVydani = pouzeCelaCisla(sc);
				System.out.println("Zadejte dostupnost pomocí slov true (dostupná) nebo false (vypůjčená):");
				dostupnost = pouzeBoolean(sc);

				System.out.println("Zadejte 1 pro román a 0 pro učebnici:");
				int druhKnihy = sc.nextInt();

				if (druhKnihy == 1) {
					System.out.println("Zadejte:\n1: pro Thriller\n2: pro Fantasy\n3: pro Sci-fi\n4: pro Young Adult\n5: pro Beletrie");
					int volbaZanru = pouzeCelaCisla(sc);

					if (volbaZanru >= 1 && volbaZanru <= 5) {
						String zvolenyZanr = null;
						if (volbaZanru == 1) {
							zvolenyZanr = "Thriller";
						} else if (volbaZanru == 2) {
							zvolenyZanr = "Fantasy";
						} else if (volbaZanru == 3) {
							zvolenyZanr = "Sci-fi";
						} else if (volbaZanru == 4) {
							zvolenyZanr = "Young Adult";
						} else if (volbaZanru == 5) {
							zvolenyZanr = "Beletrie";
						}
						mojeDatabaze
								.pridejKnihu(new Roman(nazevKnihy, jmenoAutora, rokVydani, dostupnost, zvolenyZanr));
					} else {
						System.out.println("Neplatná volba žánru.");
					}
				} else if (druhKnihy == 0) {
					System.out.println("Zadejte ročník, pro který je učebnice vhodná (1-9):");
					int rocnik = pouzeCelaCisla(sc);
					mojeDatabaze.pridejKnihu(new Ucebnice(nazevKnihy, jmenoAutora, rokVydani, dostupnost, rocnik));
				} else {
					System.out.println("Neplatná volba");
				}

				System.out.println("-----------Kniha přidána do knihovny!--------------------");
				System.out.println("-----------Přesměrování na hlavní stránku ---------------");
				pauza(3000);
				break;

			case 2:
				System.out.println("Zadejte název knihy, kterou chcete upravit:");
				String novyNazev = sc.nextLine();
				if (mojeDatabaze.overNazev(novyNazev) == false) {
					System.out.println("-----------Kniha " + novyNazev + " nebyla nalezena!---------------");
					System.out.println("-----------Přesměrování na hlavní stránku ---------------");
					pauza(3000);
					break;
				}

				System.out.println("Zadejte nové jméno autora:");
				String noveJmenoAutora = sc.nextLine();
				System.out.println("Zadejte nový rok vydání:");
				int novyRokVydani = pouzeCelaCisla(sc);
				System.out.println("Zadejte novou dostupnost pomocí slov true (dostupná) nebo false (vypůjčená):");
				boolean novaDostupnost = pouzeBoolean(sc);

				mojeDatabaze.upravKnihu(novyNazev, noveJmenoAutora, novyRokVydani, novaDostupnost);
				System.out.println("-----------Kniha byla upravena!---------------");
				System.out.println("-----------Přesměrování na hlavní stránku ---------------");
				pauza(3000);
				break;
			case 3:
				System.out
						.println("Zadejte název knihy, kterou chcete smazat (dbejte prosím velkých a malých písmen):");
				String smazanyNazev = sc.nextLine();

				mojeDatabaze.smazKnihu(smazanyNazev);
				pauza(3000);
				break;
			case 4:
				System.out
						.println("Zadejte název knihy, kterou chcete označit (dbejte prosím velkých a malých písmen):");
				String oznacNazev = sc.nextLine();
				if (mojeDatabaze.overNazev(oznacNazev) == false) {
					System.out.println("-----------Kniha " + oznacNazev + " nebyla nalezena!---------------");
					pauza(3000);
					break;
				}
				System.out.println("Zadejte novou dostupnost pomocí slov true (dostupná) nebo false (vypůjčená): ");
				boolean novaDostupnostOznaceni = pouzeBoolean(sc);

				mojeDatabaze.oznacKnihy(oznacNazev, novaDostupnostOznaceni);
				System.out.println("------------Dostupnost knihy byla aktualizována!------------");
				System.out.println("-----------Přesměrování na hlavní stránku ---------------");
				pauza(3000);
				break;
			case 5:

				mojeDatabaze.vypisKnihy();
				pauza(3000);
				break;
			case 6:
				System.out.println("Zadejte název knihy, kterou chcete vyhledat (dbejte prosím velkých a malých písmen):");
				String hledatNazev = sc.nextLine();

				if (mojeDatabaze.overNazev(hledatNazev) == false) {
					System.out.println("-----------Kniha " + hledatNazev + " nebyla nalezena!---------------");
					System.out.println("-----------Přesměrování na hlavní stránku ---------------");
					pauza(3000);
					break;
				}

				mojeDatabaze.vyhledejKnihu(hledatNazev);
				pauza(3000);
				break;
			case 7:

				System.out.println("Zadejte jméno autora, jehož knihy chcete vypsat (dbejte prosím velkých a malých písmen):");
				String hledatAutor = sc.nextLine();

				if (mojeDatabaze.overAutora(hledatAutor) == false) {
					System.out.println("-----------Autor " + hledatAutor + " nebyl nalezen!---------------");
					System.out.println("-----------Přesměrování na hlavní stránku ---------------");
					pauza(3000);
					break;
				}

				mojeDatabaze.vypisKnihyAutor(hledatAutor);
				pauza(4000);
				break;

			case 8:

				System.out.println("Zadejte:\n1: pro Thriller\n2: pro Fantasy\n3: pro Sci-fi\n4: pro Young Adult\n5: pro Beletrie");
				int volbaZanru = sc.nextInt();

				if (volbaZanru >= 1 && volbaZanru <= 5) {
					String zvolenyZanr = null;
					if (volbaZanru == 1) {
						zvolenyZanr = "Thriller";
					} else if (volbaZanru == 2) {
						zvolenyZanr = "Fantasy";
					} else if (volbaZanru == 3) {
						zvolenyZanr = "Sci-fi";
					} else if (volbaZanru == 4) {
						zvolenyZanr = "Young Adult";
					} else if (volbaZanru == 5) {
						zvolenyZanr = "Beletrie";
					}
					mojeDatabaze.vypisKnihyZanr(zvolenyZanr);
				} else {
					System.out.println("Neplatná volba žánru.");
				}

				pauza(4000);
				break;
			case 9:

				mojeDatabaze.vypisVypujceneKnihy();
				pauza(4000);
				break;
			case 10:
				System.out
						.println("Zadejte název knihy, kterou chcete uložit (dbejte prosím velkých a malých písmen):");
				String ulozNazev = sc.nextLine();

				if (mojeDatabaze.overNazev(ulozNazev) == false) {
					System.out.println("-----------Kniha " + ulozNazev + " nebyl nalezen!---------------");
					System.out.println("-----------Přesměrování na hlavní stránku ---------------");
					pauza(3000);
					break;
				}

				System.out.println("Zadejte soubor, do kterého chcete načíst knihu (dbejte prosím i typu souboru (např.: kniha.txt)):");
				String souborKniha = sc.nextLine();

				mojeDatabaze.ulozDoSouboru(souborKniha, ulozNazev);

				pauza(3000);
				break;
			case 11:
				System.out.println("Zadejte soubor, ze kterého chcete načíst knihu (dbejte prosím i typu souboru (např.: kniha.txt)):");
				String souborCteniKniha = sc.nextLine();

				mojeDatabaze.nactiZeSouboru(souborCteniKniha);
				pauza(3000);
				break;
			case 12:
				mojeDatabaze.ulozDoDatabaze();
				run = false;
				break;

			}
		}
		sc.close();
	}
}
