package dominio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ArquivoSPED {

	private String nomeArquivo;
	String aliqAtual;
	private String caminhoDoArquivo;
	private ArrayList<String[]> arquivoArray;

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String getCaminhoDoArquivo() {
		return caminhoDoArquivo;
	}

	public void setCaminhoDoArquivo(String caminhoDoArquivo) {
		this.caminhoDoArquivo = caminhoDoArquivo;
	}

	public ArrayList<String[]> getSbArquivo() {
		return arquivoArray;
	}

	public void setSbArquivo(ArrayList<String[]> sbArquivo) {
		this.arquivoArray = sbArquivo;
	}

	public void corrigirArquivo() {
		// declaração das variaveis
		ArrayList<Integer> linhas = new ArrayList<Integer>();
		double totalIcmsAtual;
		double valorC100Atualizado;
		// int indiceC190;
		int indice = 0;
		int indexAux = 0;
		int indexC100 = 0;
		int indexC405 = 0;
		int contC190 = 0;
		int indexC470 = 0;
		int contC420;
		double baseIcms = 0;
		double totalIcmsCorrigido = 0.0;

		/**
		 * Inicia Leitura do arquivo em forma de lista Procurando pelo 1o C100
		 */
		Map<String, String> listaAliq = new HashMap<String, String>();
		Map<String, String> listaC420Cod = new HashMap<String, String>();
		// Map<String, String> listaC420Descr = new HashMap<String, String>();
		// Map<Integer, String[]> listaC420Corrigido = new HashMap<Integer,
		// String[]>();

		listaAliq.put("19", "20,00");
		listaAliq.put("13", "20,00");
		listaAliq.put("7", "20,00");
		listaAliq.put("8", "20,00");
		listaAliq.put("27", "20,00");
		listaAliq.put("26", "20,00");
		listaAliq.put("7,00", "20,00");
		listaAliq.put("26,00", "20,00");
		listaAliq.put("19,00", "20,00");
		listaAliq.put("13,00", "20,00");
		listaAliq.put("8,00", "20,00");
		listaAliq.put("27,00", "20,00");

		listaC420Cod.put("T0700", "T2000");
		listaC420Cod.put("T0800", "T2000");
		listaC420Cod.put("T1300", "T2000");
		listaC420Cod.put("T1900", "T2000");
		listaC420Cod.put("T2700", "T2000");
		listaC420Cod.put("T2600", "T2000");
		listaC420Cod.put("T2000", "T2000");
		String descricao20 = "Contador de Aliquota - 20,00%";

		for (String[] strings : arquivoArray) {

			/**
			 * Método para corrigir entrada Recebe a alquota que está sendo
			 * usada na linha do arquivo e corrige se necessario
			 *
			 */

			// marcação dos atributos que serão modificados

			if (strings[0].equals("C100")) {
				indexC100 = indice;

			}
			/**
			 * Utiliza o Map com as configurações para buscar por aliquota
			 * erradas no c170
			 */
			// corrige ncm

			else if (strings[0].equals("0200") && strings[12].equals("0000000")) {
				strings[12] = "1702500";
			}

			// termina correção

			else if (strings[0].equals("C170")) {
				if (listaAliq.containsKey(strings[13]) || listaAliq.containsKey(strings[13] + ",00")) {
					aliqAtual = strings[13];
					// Captura a base de ICMS do produto
					baseIcms = Double.parseDouble(strings[12].replaceAll(",", "."));
					// passa o valor da base com uma nova aliquota
					totalIcmsCorrigido = baseIcms
							* (Double.parseDouble(listaAliq.get(aliqAtual).replaceAll(",", ".")) / 100);
					// Troca a aliquota para a certa
					strings[13] = listaAliq.get(aliqAtual);
					// Muda o valor do ICMS segundo a base correta
					strings[14] = String.format("%.2f", totalIcmsCorrigido);

				}
			}
			/**
			 * Utiliza o Map com as configurações para buscar por aliquota
			 * erradas no c190
			 *
			 **/
			else if (strings[0].equals("C190")) {
				if (listaAliq.containsKey(strings[3]) || listaAliq.containsKey(strings[3] + ",00")) {
					indexAux = indexC100;
					// indiceC190 = indice;
					// altera a variavel que marca a aliquota q está sendo
					// alterada
					// no momento
					aliqAtual = strings[3];
					// Recupera o valor da base de ICMS
					baseIcms = Double.parseDouble(strings[5].replace(",", "."));
					// atualiza a variavel com o valor correto do ICMS
					totalIcmsCorrigido = baseIcms
							* (Double.parseDouble(listaAliq.get(aliqAtual).replaceAll(",", ".")) / 100);
					// Recupera o valor de ICMS atual
					totalIcmsAtual = Double.parseDouble(strings[6].replace(",", "."));
					/*
					 * atualiza variavel com o o valor da diferença entre o
					 * corrigido e o errado
					 */
					double diferençaIcms = totalIcmsCorrigido - totalIcmsAtual;

					// atualiza a variavel com a soma da diferença de ICMS ao
					// C100
					valorC100Atualizado = Double.parseDouble(arquivoArray.get(indexC100)[21].replace(",", "."))
							+ diferençaIcms;

					// atualiza o C100 o arquivo
					arquivoArray.get(indexC100)[21] = String.format("%.2f", valorC100Atualizado);

					do {
						indexAux++;
						if (arquivoArray.get(indexAux)[0].equals("C190") && arquivoArray.get(indexAux)[3].length() > 1
								&& arquivoArray.get(indexAux)[3].substring(0, 2)
										.equals(listaAliq.get(aliqAtual).substring(0, 2))) {

							baseIcms += Double.parseDouble(arquivoArray.get(indexAux)[5].replace(",", "."));
							totalIcmsCorrigido += Double.parseDouble(arquivoArray.get(indexAux)[6].replace(",", "."));
							linhas.add(indexAux);
							contC190++;
						}

					} while (!arquivoArray.get(indexAux)[0].equals("C100")
							&& !arquivoArray.get(indexAux)[0].equals("9999"));

					// atualiza C190 com o valor correto do ICMS
					strings[6] = String.format("%.2f", totalIcmsCorrigido);
					strings[4] = String.format("%.2f", baseIcms);
					strings[5] = String.format("%.2f", baseIcms);
					// altera o valor da aliquota no arquivo para C190
					strings[3] = listaAliq.get(aliqAtual);
				}

			} else if (strings[0].equals("C405")) {

				indexC405 = indice;

			} else if (strings[0].equals("C420") && listaC420Cod.containsKey(strings[1])) {
				strings[1] = "";
				arquivoArray.get(indice)[1] = "";
				indexAux = indexC405;
				contC420 = Integer.parseInt(strings[3]);
				baseIcms = Double.parseDouble(strings[2].replace(",", "."));

				do {
					/**
					 * Por toda a nota se C420 estiver errado
					 */
					if (arquivoArray.get(indexAux)[0].equals("C420")
							&& listaC420Cod.containsKey(arquivoArray.get(indexAux)[1])) {

						baseIcms += Double.parseDouble(arquivoArray.get(indexAux)[2].replace(",", "."));
						contC420 += Integer.parseInt(arquivoArray.get(indexAux)[3]);
						arquivoArray.get(indexAux)[1] = "";
						System.out.println("C420 " + "Linha " + indexAux + "campo " + arquivoArray.get(indexAux)[0]);
						linhas.add(indexAux);
					}
					indexAux++;
				} while (!arquivoArray.get(indexAux)[0].equals("C460"));

				if (contC420 >= 100)
					contC420 = 99;

				strings[1] = "T2000";
				strings[2] = String.format("%.2f", baseIcms).replace(".", ",");
				strings[3] = Integer.toString(contC420);
				strings[4] = descricao20;

			}

			else if (strings[0].equals("C470") && listaAliq.containsKey(strings[8])) {
				indexC470 = indice;
				strings[8] = listaAliq.get(strings[8].replace(".", ","));
				indexAux = indice;
			} else if (strings[0].equals("C470")) {
				indexC470 = indice;
				baseIcms = 0;
				indexAux = indexC470;
			} else if (strings[0].equals("C490") && listaAliq.containsKey(strings[3])) {
				baseIcms = Double.parseDouble(strings[5].replaceAll(",", "."));
				// System.out.print("achou errado ");
				aliqAtual = strings[3];
				// altera bases
				// altera o auxiliar para o 470 acima
				do {
					if ((arquivoArray.get(indexAux)[0].equals("C490"))
							&& ((arquivoArray.get(indexAux)[3].equals(listaAliq.get(aliqAtual)))
									|| arquivoArray.get(indexAux)[3].equals(listaAliq.get(aliqAtual).substring(0,
											listaAliq.get(aliqAtual).indexOf(","))))) {

						// System.out.println("com aliq: " + baseIcms);
						baseIcms += Double.parseDouble(arquivoArray.get(indexAux)[5].replaceAll(",", "."));
						// System.out.println("somou " + baseIcms);
						linhas.add(indexAux);
						System.out.println(
								"adicionou C490" + "Linha " + indexAux + "campo " + arquivoArray.get(indexAux)[0]);
					} else if (arquivoArray.equals("20") || arquivoArray.equals("20,00")) {
					}

					indexAux++;
				} while (!arquivoArray.get(indexAux)[0].equals("C990"));

				// System.out.println("1a " + indexAux);
				// altera valor ICMS
				// System.out.println("passou: " + baseIcms);
				strings[3] = listaAliq.get(aliqAtual);
				strings[4] = String.format("%.2f", baseIcms);
				strings[5] = String.format("%.2f", baseIcms);
				strings[6] = String.format("%.2f", baseIcms * 0.2);
				strings[6] = strings[6].replace(".", ",");

				indexAux = indice;

			} else if (arquivoArray.get(indice)[0].equals("C990")) {

				arquivoArray.get(indice)[1] = Integer
						.toString(Integer.parseInt(arquivoArray.get(indice)[1]) - contC190);
			} else if (arquivoArray.get(indice)[0].equals("9900") && arquivoArray.get(indice)[1].equals("C190")) {

				arquivoArray.get(indice)[2] = Integer
						.toString(Integer.parseInt(arquivoArray.get(indice)[2]) - contC190);
			} else if (arquivoArray.get(indice)[0].equals("9999")) {

				arquivoArray.get(indice)[1] = Integer
						.toString(Integer.parseInt(arquivoArray.get(indice)[1]) - linhas.size());
			}

			indice++;

		}

		/** Apaga linhas corrigidas **/
		for (int i = 0; i < linhas.size(); i++) {
			// S//ystem.out.println(linhas.get(0));

			// System.out.println(Arrays.toString(arquivoArray.get(linhas.get(i)
			// - i)));
			System.out.println("apagou a linha " + linhas.get(i));
			arquivoArray.remove(linhas.get(i).intValue() - i);
		}

	}

	public void salvarArquivo() {

		StringBuilder sbarquivo = new StringBuilder();
		for (String[] strings : arquivoArray) {
			for (int i = 0; i < strings.length; i++) {
				sbarquivo.append("|");
				sbarquivo.append(strings[i]);
			}
			sbarquivo.append("|");
			sbarquivo.append("\r\n");
		}
		String arquivoString = sbarquivo.toString();

		File arquivo = new File(this.caminhoDoArquivo, "corrigido " + nomeArquivo);

		try {
			if (arquivo.exists()) {
				arquivo.delete();
			}

			FileWriter fileW = new FileWriter(arquivo);
			BufferedWriter fr = null;
			fr = new BufferedWriter(fileW);

			try {
				fr.write(arquivoString);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			// Salva as Alterações
			try {
				fr.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// Fecha a escrita no Arquivo
			try {
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// }
		} catch (IOException e3) {
			e3.printStackTrace();
		}
	}

	public ArrayList<String[]> getArquivoArray() {
		return arquivoArray;
	}

	public void setArquivoArray(ArrayList<String[]> arquivoArray) {
		this.arquivoArray = arquivoArray;
	}
}