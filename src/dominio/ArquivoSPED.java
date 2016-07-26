package dominio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ArquivoSPED {

	private String nomeArquivo;
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
		int indice = 0;
		int c100index = 0;
		Double baseIcms = 0.0;
		Double totalIcms = 0.0;
		Double icmsAntigo = 0.0;
		Double totalIcms19 = 0.0;

		for (String[] strings : arquivoArray) {
			if (strings[0].equals("C100")) {
				c100index = indice;
			} else if (strings[0].equals("C170") && strings[13].equals("19,00")) {
				baseIcms = Double.parseDouble(strings[12].replaceAll(",", "."));
				totalIcms = baseIcms * 0.2;

				strings[13] = "20,00";
				strings[14] = String.format("%.2f", totalIcms);

			} else if (strings[0].equals("C190") && strings[3].equals("19,00")) {
				strings[3] = "20,00";
				baseIcms = Double.parseDouble(strings[5].replaceAll(",", "."));
				totalIcms = baseIcms * 0.2;
				totalIcms19 = Double.parseDouble(strings[6].replaceAll(",", "."));
				strings[6] = String.format("%.2f", totalIcms);
				totalIcms =  totalIcms - totalIcms19;
				icmsAntigo = Double.parseDouble(arquivoArray.get(c100index)[21].replaceAll(",", ".")) + totalIcms;
				
				
				
				arquivoArray.get(c100index)[21] = String.format("%.2f", icmsAntigo);

			}

			indice++;
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