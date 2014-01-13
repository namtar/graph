package de.htw.berlin.student.graph.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import de.htw.berlin.student.graph.model.Graph;
import de.htw.berlin.student.graph.util.SpringUtilities;

/**
 * A dialog to gernerate a graph with a given number of nodes and edges.
 * 
 * @author Matthias Drummer
 */
public class GenerateRandomGraphDialog extends JDialog {

	private static final long serialVersionUID = -7313085077925444747L;

	private final Graph graph;
	private JTextField numberOfNodesTextField;
	private JTextField numberOfEdgesTextField;

	/**
	 * Constructor.
	 * 
	 * @param graph the {@link Graph}. Is modified by reference.
	 */
	public GenerateRandomGraphDialog(final JFrame frame, final Graph graph) {
		this.graph = graph;

		setLayout(new BorderLayout());

		add(createInputPanel(), BorderLayout.CENTER);
		add(createButtonPanel(), BorderLayout.PAGE_END);

		setTitle("Graph erzeugen");
		setModal(true);
		// setPreferredSize(new Dimension(400, 200));
		setResizable(false);
		pack();
		setLocationRelativeTo(frame); // must be called between pack and setVisible to work properly
		setVisible(true);
	}

	private JPanel createInputPanel() {

		JPanel panel = new JPanel();
		SpringLayout springLayout = new SpringLayout();

		panel.setLayout(springLayout);

		String[] labels = { "Anzahl Knoten", "Anzahl Kanten" };

		int numPairs = labels.length;
		JLabel numberOfNodesLabel = new JLabel(labels[0], JLabel.TRAILING);
		JLabel numberOfEdgesLabel = new JLabel(labels[1], JLabel.TRAILING);

		numberOfNodesTextField = new JTextField(10);
		numberOfEdgesTextField = new JTextField(10);
		panel.add(numberOfNodesLabel);
		numberOfNodesLabel.setLabelFor(numberOfNodesTextField);
		panel.add(numberOfNodesTextField);

		panel.add(numberOfEdgesLabel);
		numberOfEdgesLabel.setLabelFor(numberOfEdgesTextField);
		panel.add(numberOfEdgesTextField);

		// Lay out the panel.
		SpringUtilities.makeCompactGrid(panel, numPairs, 2, // rows, cols
				6, 6, // initX, initY
				6, 6); // xPad, yPad

		return panel;
	}

	private JPanel createButtonPanel() {

		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));

		JButton generateButton = new JButton("Generieren");
		JButton closeButton = new JButton("Schließen");

		generateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				boolean catchedError = false;
				int numberOfNodes = 0;
				int numberOfEdges = 0;
				// validate settings
				try {
					numberOfNodes = Integer.valueOf(numberOfNodesTextField.getText());
					numberOfEdges = Integer.valueOf(numberOfEdgesTextField.getText());
				} catch (NumberFormatException e1) {
					catchedError = true;
					JOptionPane.showMessageDialog(GenerateRandomGraphDialog.this, "Es muss eine Zahl eingegeben werden.", "Validierungs-Fehler",
							JOptionPane.ERROR_MESSAGE);
				}

				// validate. The may be not more edges that number of nodes - 1
				int maxEdges = 0;
				for (int i = numberOfNodes - 1; i > 0; i--) {
					maxEdges += i;
				}

				if (numberOfEdges > maxEdges) {
					catchedError = true;
					JOptionPane
							.showMessageDialog(GenerateRandomGraphDialog.this,
									"Es können nicht mehr Kanten pro Node generiert werden als Nodes vorhanden sind.", "Validierungs-Fehler",
									JOptionPane.ERROR_MESSAGE);
				}

				if (!catchedError) {

					graph.createRandomGraph(numberOfNodes, numberOfEdges);
					// close
					setVisible(false);

				}
			}
		});

		closeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

		panel.add(generateButton);
		panel.add(closeButton);

		return panel;
	}
}
