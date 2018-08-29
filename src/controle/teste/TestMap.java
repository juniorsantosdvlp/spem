package controle.teste;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestMap {
	public static void main(String[] args) {

		Map<String, String> listaAliq = new HashMap<String, String>();
		listaAliq.put("19", "20");
		listaAliq.put("13", "20");
		listaAliq.put("8", "20");
		listaAliq.put("27", "52");

		// TODO Auto-generated method stub

		ArrayList<String[]> array = new ArrayList<String[]>();

		String[] oi1 = { "C170", "13", "21.5" };
		String[] oi2 = { "C100", "20", "32" };
		String[] oi3 = { "C170", "8", "40.8" };
		String[] oi4 = { "C170", "12", "10" };
		String[] oi5 = { "C170", "20", "25.0" };
		String[] oi6 = { "C100", "12", "50" };
		String[] oi7 = { "C190", "13", "21.5" };
		String[] oi8 = { "C170", "20", "32" };
		String[] oi9 = { "C190", "20", "40.8" };
		String[] oi10 = { "C190", "12", "10" };
		String[] oi11 = { "C190", "10", "1.0" };
		String[] oi12 = { "C200", "12", "50" };

		array.add(oi1);
		array.add(oi2);
		array.add(oi3);
		array.add(oi4);
		array.add(oi5);
		array.add(oi6);
		array.add(oi7);
		array.add(oi8);
		array.add(oi9);
		array.add(oi10);
		array.add(oi11);
		array.add(oi12);

		int index = 0;
		int indexaux = 0;
		int indexc100 = 0;
		// int indexc190 = 0;
		String aliqa = "";
		double base = 0;

		for (String[] strings : array) {
			if (strings[0].equals("C100")) {

				indexc100 = index;
				indexaux = index;

			} else if (strings[0].equals("C190") && listaAliq.containsKey(array.get(index)[1])) {
				aliqa = listaAliq.get(array.get(index)[1]);
				base = Double.parseDouble(array.get(index)[2]);
				System.out.println("Linha com erro de tributação: " + index);
				// indexc190 = index;

				while (!array.get(indexaux)[0].equals("C200")) {

					if (array.get(indexaux)[0].equals("C190") && array.get(indexaux)[1].equals(aliqa)) {
						base += Double.parseDouble(array.get(indexaux)[2].replaceAll(",", "."));
						array.get(indexaux)[2] = Double.toString(base);
						System.out.println(
								"linha com a aliq certa: " + indexaux + " Aliq certa: " + array.get(indexaux)[1]);

					}
					indexaux++;
				}

			}

			index++;
			indexaux = index;
		}
		System.out.println(listaAliq.containsKey("19" + ",00"));
		System.out.println(listaAliq.get("45"));
		for (String[] strings : array) {
			for (String string : strings) {
				System.out.print(string + "|");
			}
			System.out.println();
		}
	}

}