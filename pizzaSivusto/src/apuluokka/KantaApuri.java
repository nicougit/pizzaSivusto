package apuluokka;

import java.util.ArrayList;
import java.util.Scanner;

public class KantaApuri {

	// Pelkästään tietokannan dummyentryjen luontiin väliaikaisesti

	public static void main(String[] args) {

		String taytePohja = "INSERT INTO Taytteet VALUES (null, ";
		String pizzaPohja = "INSERT INTO Pizzat VALUES (null, ";
		String kantajuttu = "(SELECT id FROM Taytteet WHERE nimi = ";
		Boolean letsgo = true;
		Scanner skanneri = new Scanner(System.in);

		ArrayList<String> lauseet = new ArrayList<>();

		System.out.print("pizza / täyte: ");
		String valinta = skanneri.nextLine().toLowerCase();

		if (valinta.equals("pizza")) {
			while (letsgo) {
				String tayte1;
				String tayte2;
				String tayte3;
				String tayte4;
				String tayte5;
				
				
				System.out.print("Pizzan nimi: ");
				String input = pizzaPohja + "'" + skanneri.nextLine() + "', ";
				System.out.print("Pizzan hinta: ");
				input += skanneri.nextLine().replace(",", ".") + ", ";
				System.out.print("Täyte 1: ");
				tayte1 = kantajuttu + "'" + skanneri.nextLine() + "'), ";
				System.out.print("Täyte 2: ");
				tayte2 = kantajuttu + "'" + skanneri.nextLine() + "'), ";
				System.out.print("Täyte 3 (done = done): ");
				String inputti = skanneri.nextLine();
				if (inputti.equals("done")) {
					lauseet.add(input + tayte1 + tayte2 + "null, null, null);");
				}
				else {
					System.out.print("Täyte 4 (done = done): ");
					tayte3 = kantajuttu + "'" + inputti + "'), ";
					inputti = skanneri.nextLine();
					if (inputti.equals("done")) {
						lauseet.add(input + tayte1 + tayte2 + tayte3 + "null, null);");
					}
					else {
						System.out.print("Täyte 5 (done = done): ");
						tayte4 = kantajuttu + "'" + inputti + "'), ";
						inputti = skanneri.nextLine();
						if (inputti.equals("done")) {
							lauseet.add(input + tayte1 + tayte2 + tayte3 + tayte4 + "null);");
						}
						else {
							tayte5 = kantajuttu + "'" + inputti + "');";
							lauseet.add(input + tayte1 + tayte2 + tayte3 + tayte4 + tayte5);
						}
					}
				}
				
				System.out.print("Lisätäänkö lisää pizzoja k / e: ");
				String jatkovalinta = skanneri.nextLine();
				if (jatkovalinta.equals("e")) {
					letsgo = false;
				}
				
			}
		} else if (valinta.equals("täyte")) {
			while (letsgo) {
				System.out.print("Täytteen nimi (done = exit): ");
				String inputti = skanneri.nextLine();

				if (inputti.equals("done") || inputti.equals("exit")) {
					letsgo = false;
				} else {
					lauseet.add(taytePohja + "'" + inputti + "');");
				}
			}
		}

		for (int i = 0; i < lauseet.size(); i++) {
			System.out.println(lauseet.get(i));
		}

	}

}
