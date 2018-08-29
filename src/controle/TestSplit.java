package controle;

import java.util.ArrayList;

public class TestSplit {

	public static void main(String[] args) {

		String st = "Data e hora - #id status #ativo";
		String[] arr ;

		arr = st.replaceAll("-", "").split("#");
		for (String string : arr) {

System.out.println(string);
System.out.println("|");
		}

	}

}
