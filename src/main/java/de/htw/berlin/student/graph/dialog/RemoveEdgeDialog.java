package de.htw.berlin.student.graph.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxEditor;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import de.htw.berlin.student.graph.model.Edge;
import de.htw.berlin.student.graph.model.Graph;
import de.htw.berlin.student.graph.model.Node;

/**
 * A dialog to remove an edge for a specific base node.
 * 
 * @author Matthias Drummer
 */
public class RemoveEdgeDialog extends JDialog {

	private static final long serialVersionUID = 4633599220212597688L;

	private final Graph graph;

	private JComboBox nodeCb;
	private JComboBox edgeCb;

	public RemoveEdgeDialog(final JFrame frame, final Graph graph) {
		this.graph = graph;

		setLayout(new BorderLayout());

		add(createInputPanel(), BorderLayout.CENTER);
		add(createButtonPanel(), BorderLayout.PAGE_END);
		// show dialog
		setTitle("Kante löschen");
		setModal(true);
		setPreferredSize(new Dimension(400, 200));
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
		nodeCb.setRenderer(new NodeCellRenderer());

		nodeCb.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {

				// if the first time an item was selected one is not able to select no item again.
				// perhaps do this ((DefaultComboBoxModel)jCmbPatients.getModel()).insertElementAt("--None--",0);

				edgeCb.removeAllItems(); // clear up edge combo box
				Node selectedNode = (Node) nodeCb.getSelectedItem();

				if (selectedNode != null) {
					List<Edge> edgesForNode = graph.getAdjacencyList().get(selectedNode);
					ComboBoxModel edgeCbModel = new DefaultComboBoxModel(edgesForNode.toArray());
					edgeCb.setModel(edgeCbModel);
				}

			}
		});

		edgeCb = new JComboBox();
		edgeCb.setMaximumSize(edgeCb.getPreferredSize());
		edgeCb.setMinimumSize(new Dimension(150, edgeCb.getPreferredSize().height));
		edgeCb.setPreferredSize(new Dimension(150, edgeCb.getPreferredSize().height));
		edgeCb.setRenderer(new EdgeCellRenderer());

		inputPanel.add(new JLabel("Knoten"));
		inputPanel.add(rigid);
		inputPanel.add(nodeCb);
		inputPanel.add(rigid);
		inputPanel.add(edgeCb);

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
		JButton deleteBtn = new JButton("Löschen");

		cancelBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

		deleteBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (nodeCb.getSelectedItem() == null || edgeCb.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(RemoveEdgeDialog.this, "Es muss ein Knoten und eine Kante ausgewählt sein um eine Kante löschen zu können.",
							"Validierungs-Fehler", JOptionPane.ERROR_MESSAGE);
				} else {
					Node node = (Node) nodeCb.getSelectedItem();
					Edge edge = (Edge) edgeCb.getSelectedItem();

					graph.removeEdge(node, edge);
					setVisible(false);
				}

			}
		});

		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(cancelBtn);
		buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPanel.add(deleteBtn);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		return buttonPanel;
	}
}
