package controle.teste;
import java.awt.Cursor;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;


public class ExemploProgress extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ExemploProgress() throws InterruptedException {
		JProgressBar progresso = new JProgressBar();
		setSize(300,100);
		add(progresso);
		setLocationRelativeTo(null);
		setCursor(new Cursor(Cursor.WAIT_CURSOR));
		setVisible(true);
		progresso.setToolTipText("Aguarde...");
		for (int i=1;i<200;i++){
			if (i==30)
				progresso.setToolTipText("Ta quase");
			if (i==70)
				progresso.setToolTipText("firme");
			progresso.setValue(i);
			Thread.sleep(100);
		}
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		JOptionPane.showMessageDialog(this, "Carregamento concluído");
	}
	public static void main(String[] args) throws InterruptedException{
		new ExemploProgress();
	}
}