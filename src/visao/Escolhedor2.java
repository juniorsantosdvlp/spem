package visao;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Escolhedor2 {

	public static File escolher() {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivo TXT", "txt");
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(filter);
		chooser.setAcceptAllFileFilterUsed(false);
		int retorno = chooser.showSaveDialog(null);
		if (retorno == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		} else
			return null;
	}

}
