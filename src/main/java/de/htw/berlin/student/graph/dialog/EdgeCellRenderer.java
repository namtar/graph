package de.htw.berlin.student.graph.dialog;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import de.htw.berlin.student.graph.model.Edge;

/**
 * A cell renderer for edge combo boxes.
 * 
 * @author Matthias Drummer
 */
public class EdgeCellRenderer extends JLabel implements ListCellRenderer {

	private static final long serialVersionUID = -4354426903722055318L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

		Edge edge = (Edge) value;

		JLabel label = new JLabel();

		if (edge != null && edge.getNode() != null && edge.getNode().getNodeText() != null) {
			label.setText("Zielknoten: " + edge.getNode().getNodeText());
		}

		return label;
	}

}
