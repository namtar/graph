package de.htw.berlin.student.graph.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import de.htw.berlin.student.graph.model.Graph;
import de.htw.berlin.student.graph.model.Node;

/**
 * A dialog to delete one node.
 * 
 * @author Matthias Drummer
 */
public class DeleteNodeDialog extends JDialog {

	private static final long serialVersionUID = 8550125689074490014L;

	private final Graph graph;

	private JComboBox nodeCb;

	/**
	 * Constructor.
	 * 
	 * @param frame the parent frame
	 * @param graph the {@link Graph} object
	 */
	public DeleteNodeDialog(final JFrame frame, final Graph graph) {

		this.graph = graph;

		setLayout(new BorderLayout());

		add(createInputPanel(), BorderLayout.CENTER);
		add(createButtonPanel(), BorderLayout.PAGE_END);
		// show dialog
		setTitle("Knoten löschen");
		setModal(true);
		setPreferredSize(new Dimension(300, 200));
		setResizable(false);
		pack();
		setLocationRelativeTo(frame); // must be called between pack and setVisible to work properly
		setVisible(true);
	}

	private JPanel createInputPanel() {

		Component rigid = Box.createRigidArea(new Dimension(10, 0));

		JPanel inputPanelWrapper = new JPanel();
		inputPanelWrapper.setLayout(new BoxLayout(inputPanelWrapper, BoxLayout.PAGE_AXIS));

		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.LINE_AXIS));

		ComboBoxModel sourceNodeModel = new DefaultComboBoxModel(graph.getAdjacencyList().keySet().toArray());

		sourceNodeModel.setSelectedItem(null);

		nodeCb = new JComboBox(sourceNodeModel);
		nodeCb.setMaximumSize(nodeCb.getPreferredSize());
		nodeCb.setMinimumSize(new Dimension(150, nodeCb.getPreferredSize().height));
		nodeCb.setPreferredSize(new Dimension(150, nodeCb.getPreferredSize().height));

		NodeCellRenderer renderer = new NodeCellRenderer();

		nodeCb.setRenderer(renderer);

		inputPanel.add(new JLabel("Knoten"));
		inputPanel.add(rigid);
		inputPanel.add(nodeCb);
		inputPanel.add(Box.createHorizontalGlue());
		inputPanelWrapper.add(inputPanel);

		inputPanelWrapper.add(Box.createVerticalGlue());

		inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		return inputPanelWrapper;
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

				if (nodeCb.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(DeleteNodeDialog.this, "Es muss ein Knoten ausgewählt sein um ihn löschen zu können.",
							"Validierungs-Fehler", JOptionPane.ERROR_MESSAGE);
				} else {
					Node node = (Node) nodeCb.getSelectedItem();
					graph.deleteNode(node);
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
}
