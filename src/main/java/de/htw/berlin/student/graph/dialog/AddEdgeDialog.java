package de.htw.berlin.student.graph.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import javax.swing.JTextField;

import de.htw.berlin.student.graph.model.Edge;
import de.htw.berlin.student.graph.model.Graph;
import de.htw.berlin.student.graph.model.Node;

/**
 * A dialog to add a new edge.
 * 
 * @author Matthias Drummer
 */
public class AddEdgeDialog extends JDialog {

	private static final long serialVersionUID = -8016523915790229589L;

	private final Graph graph;
	private JComboBox sourceNode;
	private JComboBox targetNode;
	private JTextField edgeCostsTextField;

	/**
	 * Constructor.
	 * 
	 * @param frame the parent window
	 * @param graph the {@link Graph}. Is modified by reference.
	 */
	public AddEdgeDialog(final JFrame frame, final Graph graph) {

		this.graph = graph;

		setLayout(new BorderLayout());

		add(createInputPanel(), BorderLayout.CENTER);
		add(createButtonPanel(), BorderLayout.PAGE_END);
		// show dialog
		setTitle("Kante hinzufügen");
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

		JPanel textAreaPanel = new JPanel();
		textAreaPanel.setLayout(new BoxLayout(textAreaPanel, BoxLayout.LINE_AXIS));

		ComboBoxModel sourceNodeModel = new DefaultComboBoxModel(graph.getAdjacencyList().keySet().toArray());

		sourceNodeModel.setSelectedItem(null);

		sourceNode = new JComboBox(sourceNodeModel);
		targetNode = new JComboBox();
		sourceNode.setMaximumSize(sourceNode.getPreferredSize());
		sourceNode.setMinimumSize(new Dimension(150, sourceNode.getPreferredSize().height));
		sourceNode.setPreferredSize(new Dimension(150, sourceNode.getPreferredSize().height));
		targetNode.setMaximumSize(targetNode.getPreferredSize());
		targetNode.setMinimumSize(new Dimension(150, targetNode.getPreferredSize().height));
		targetNode.setPreferredSize(new Dimension(150, targetNode.getPreferredSize().height));

		NodeCellRenderer renderer = new NodeCellRenderer();

		sourceNode.setRenderer(renderer);
		targetNode.setRenderer(renderer);

		sourceNode.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// only display nodes which do not have already an edge

				targetNode.removeAllItems();
				Node selectedNode = (Node) sourceNode.getSelectedItem();

				if (selectedNode != null) {

					List<Node> nodesToDisplay = new ArrayList<Node>();
					Set<Node> allNodes = graph.getAdjacencyList().keySet();

					// get edged for selected node and throw out all target nodes where already an edge exists
					List<Edge> edgesForSelectedNode = graph.getAdjacencyList().get(selectedNode);
					Set<Node> nodesInEdges = new HashSet<Node>();
					for (Edge edge : edgesForSelectedNode) {
						nodesInEdges.add(edge.getNode());
					}

					for (Node node : allNodes) {
						if (!node.equals(selectedNode) && !nodesInEdges.contains(node)) {
							nodesToDisplay.add(node);
						}
					}

					ComboBoxModel targetNodeModel = new DefaultComboBoxModel(nodesToDisplay.toArray());
					targetNode.setModel(targetNodeModel);
				}
			}
		});

		inputPanel.add(sourceNode);
		// inputPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		inputPanel.add(rigid);
		inputPanel.add(targetNode);
		inputPanel.add(Box.createHorizontalGlue());
		inputPanelWrapper.add(inputPanel);

		textAreaPanel.add(new JLabel("Gewichtung der Kante: "));
		edgeCostsTextField = new JTextField();
		edgeCostsTextField.setMaximumSize(edgeCostsTextField.getPreferredSize());
		edgeCostsTextField.setMinimumSize(new Dimension(150, edgeCostsTextField.getPreferredSize().height));
		edgeCostsTextField.setPreferredSize(new Dimension(150, edgeCostsTextField.getPreferredSize().height));

		textAreaPanel.add(edgeCostsTextField);
		textAreaPanel.add(Box.createHorizontalGlue());

		inputPanelWrapper.add(textAreaPanel);
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

				Integer edgeCosts = null;
				boolean catchedError = false;

				if (edgeCostsTextField.getText() == null || edgeCostsTextField.getText().isEmpty()) {
					catchedError = true;
					JOptionPane.showMessageDialog(AddEdgeDialog.this, "Es muss eine Bewertung eingegeben werden.", "Validierungs-Fehler",
							JOptionPane.ERROR_MESSAGE);
				}

				if (edgeCostsTextField.getText() != null && edgeCostsTextField.getText().length() > 0) {

					try {
						edgeCosts = Integer.valueOf(edgeCostsTextField.getText());											
					} catch (NumberFormatException e1) {
						catchedError = true;
						JOptionPane.showMessageDialog(AddEdgeDialog.this, "Es muss entweder ein Zahl an Bewertung eingeben werden.",
								"Validierungs-Fehler", JOptionPane.ERROR_MESSAGE);
					}
				}

				if (!catchedError) {
					if (sourceNode.getSelectedItem() == null || targetNode.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(AddEdgeDialog.this, "Es muss ein Quellknoten und ein Zielknoten gewählt sein um eine Kante zu erzeugen.",
								"Validierungs-Fehler", JOptionPane.ERROR_MESSAGE);
					} else {

						Node source = (Node) sourceNode.getSelectedItem();
						Node target = (Node) targetNode.getSelectedItem();

						Edge edge = new Edge(target, edgeCosts);
						graph.addEdge(source, edge);

						setVisible(false);
					}
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
