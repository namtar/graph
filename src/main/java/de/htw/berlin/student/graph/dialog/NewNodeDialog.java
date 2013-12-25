package de.htw.berlin.student.graph.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.htw.berlin.student.graph.model.Graph;
import de.htw.berlin.student.graph.model.Node;

public class NewNodeDialog extends JDialog {

	private static final long serialVersionUID = 2616063156466566879L;
	private JTextField nodeTextTextField;

	private final Graph graph;

	/**
	 * Constructor.
	 * 
	 * @param graph the {@link Graph} object.
	 * @param frame the parent frame
	 */
	public NewNodeDialog(final Graph graph, final JFrame frame) {

		this.graph = graph;

		setLayout(new BorderLayout());

		add(createInputPanelArea(), BorderLayout.CENTER);
		add(createButtonPanel(), BorderLayout.PAGE_END);
		// show dialog
		setTitle("Neuer Knoten");
		setModal(true);
		setPreferredSize(new Dimension(300, 200));
		setResizable(false);
		pack();
		setLocationRelativeTo(frame); // must be called between pack and setVisible to work properly
		setVisible(true);

	}

	private JPanel createButtonPanel() {

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));

		JButton cancelBtn = new JButton("Abbrechen");
		JButton okBtn = new JButton("Ok");

		cancelBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

		okBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// validate that at least one char has been set to the textfield
				if (nodeTextTextField.getText() == null || nodeTextTextField.getText().length() < 1) {
					JOptionPane.showMessageDialog(NewNodeDialog.this, "Es wurde kein Text eigegeben.", "Validierungs-Fehler", JOptionPane.ERROR_MESSAGE);
				} else {
					graph.addNode(new Node(nodeTextTextField.getText()));
					setVisible(false);
				}
			}
		});

		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(cancelBtn);
		buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPanel.add(okBtn);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		return buttonPanel;
	}

	private JPanel createInputPanelArea() {

		JPanel inputPanelWrapper = new JPanel();
		inputPanelWrapper.setLayout(new BoxLayout(inputPanelWrapper, BoxLayout.PAGE_AXIS));
		JPanel inputPanel = new JPanel();

		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.LINE_AXIS));

		inputPanelWrapper.add(inputPanel);
		inputPanelWrapper.add(Box.createVerticalGlue());

		JLabel nodeTextLbl = new JLabel("Knotentext: ");
		nodeTextTextField = new JTextField();
		nodeTextTextField.setMaximumSize(nodeTextTextField.getPreferredSize());
		nodeTextTextField.setMinimumSize(new Dimension(150, nodeTextTextField.getPreferredSize().height));
		nodeTextTextField.setPreferredSize(new Dimension(150, nodeTextTextField.getPreferredSize().height));

		inputPanel.add(nodeTextLbl);
		inputPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		inputPanel.add(nodeTextTextField);
		inputPanel.add(Box.createHorizontalGlue());

		inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		return inputPanelWrapper;
	}
}
