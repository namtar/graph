package de.htw.berlin.student.graph;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Main class for the graph program
 * 
 * @author Matthias Drummer
 */
public class Main {

	public static void main(String[] args) {

		Runnable runner = new Runnable() {

			@Override
			public void run() {

				try {
					UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}

				MainWindow window = new MainWindow();
			}
		};

		SwingUtilities.invokeLater(runner);
	}

}
