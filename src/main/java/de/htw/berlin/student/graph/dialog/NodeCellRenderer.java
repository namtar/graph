package de.htw.berlin.student.graph.dialog;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import de.htw.berlin.student.graph.model.Node;

/**
 * A cell renderer for the JComboBox when displaying a node object.
 * 
 * @author Matthias Drummer
 */
public class NodeCellRenderer extends JLabel implements ListCellRenderer {

	private static final long serialVersionUID = -2382521736169585943L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

		Node node = (Node) value;
		JLabel label = new JLabel();

		if (node != null && node.getNodeText() != null) {
			label.setText(node.getNodeText());
		}

		return label;
	}

}
