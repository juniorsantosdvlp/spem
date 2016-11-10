package controle.teste;

import java.awt.Cursor;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import controle.LerArquivo;
import dominio.ArquivoSPED;
import visao.Escolhedor2;

public class Programa extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Programa() throws InterruptedException, IOException {
		
		JProgressBar progresso = new JProgressBar();
		setSize(400,100);
		add(progresso);
		setLocationRelativeTo(null);
		setCursor(new Cursor(Cursor.WAIT_CURSOR));
		progresso.setToolTipText("Aguarde...");
		setTitle("Carregando");
		
		LerArquivo leitor = new LerArquivo();
		ArquivoSPED arquivo = new ArquivoSPED();
		File file = Escolhedor2.escolher();
		setVisible(true);
		progresso.setValue(10);  // Controle de progresso
		
		arquivo = leitor.montaArquivoSPED(file);
		progresso.setValue(50);  // Controle de progresso
		
		arquivo.corrigirArquivo();
		progresso.setValue(75);  // Controle de progresso
		
		arquivo.salvarArquivo();
		progresso.setValue(100); // Controle de progresso
		
		
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		setVisible(false);
		JOptionPane.showMessageDialog(this, "arquivo corrigido salvo em C:/Users/Junior/Desktop");
		System.exit(0);
		
	}
	public static void main(String[] args) throws IOException, InterruptedException {
		new Programa();

		
	}

}