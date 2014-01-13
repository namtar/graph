package de.htw.berlin.student.graph.view;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import de.htw.berlin.student.graph.model.Edge;
import de.htw.berlin.student.graph.model.Graph;
import de.htw.berlin.student.graph.model.Node;

/**
 * Panel that displays a graph.
 * 
 * @author Matthias Drummer
 */
public class ShowGraphView extends JPanel {

	private static final long serialVersionUID = -8844154418092892713L;
	private static final Logger LOGGER = Logger.getLogger(ShowGraphView.class.getName());

	private JPanel drawArea;

	private final Graph graph;
	private final int ARR_SIZE = 4;

	/**
	 * Constructor.
	 */
	public ShowGraphView(final Graph graph) {

		this.graph = graph;

		initComponents();
	}

	private void initComponents() {

		drawArea = new JPanel() {

			private static final long serialVersionUID = 8806060132491059257L;

			@Override
			public void paint(Graphics g) {

				if (graph.getAdjacencyList() == null) {
					return;
				}

				Map<Node, ChoordWrapper> choordsForNodes = new HashMap<Node, ChoordWrapper>();

				int offsetX = drawArea.getWidth() / 2;
				int offsetY = drawArea.getHeight() / 2;

				// LOGGER.log(Level.INFO, "OffsetX: " + offsetX);
				// LOGGER.log(Level.INFO, "OffsetY: " + offsetY);

				int height = 30;
				int width = 30;
				double angle = 2 * Math.PI / graph.getAdjacencyList().keySet().size();
				int radius = drawArea.getHeight() / 2 - 50;
				FontMetrics f = g.getFontMetrics();
				int nodeHeight = Math.max(height, f.getHeight());
				int nodeWidth = nodeHeight;

				int i = 0;
				for (Node node : graph.getAdjacencyList().keySet()) {
					// calculate choordinates
					int choordX = Double.valueOf(offsetX + Math.cos(i * angle) * radius).intValue();
					int choordY = Double.valueOf(offsetY + Math.sin(i * angle) * radius).intValue();
					// LOGGER.log(Level.INFO, "ChoordX: " + choordX);
					// LOGGER.log(Level.INFO, "ChoordY: " + choordY);

					choordsForNodes.put(node, new ChoordWrapper(choordX, choordY));
					i++;
				}

				// draw edges first
				// TODO: we draw one edge two times at the moment because we have an undirected graph. But this
				// shouldn`t matter because we have the same edge costs and no one will see in. Perhaps refactor later.
				for (Entry<Node, List<Edge>> entry : graph.getAdjacencyList().entrySet()) {

					ChoordWrapper startNode = choordsForNodes.get(entry.getKey());

					for (Edge edge : entry.getValue()) {
						ChoordWrapper targetNode = choordsForNodes.get(edge.getNode());
						g.setColor(Color.RED);
						g.drawLine(startNode.getChoordX(), startNode.getChoordY(), targetNode.getChoordX(), targetNode.getChoordY());

						// if (startNode.getChoordX() - targetNode.getChoordX() < 0) {

						// int tx = 0;
						// int ty = 0;
						// double gradient = (targetNode.getChoordY() - startNode.getChoordY()) /
						// (targetNode.getChoordX() - startNode.getChoordX());
						// LOGGER.log(Level.INFO, "Gradient: " + gradient);

						// if (startNode.getChoordX() == targetNode.getChoordX()) {
						// tx = targetNode.getChoordX();
						// } else {
						// if ((startNode.getChoordX() - targetNode.getChoordX()) < 0) {
						// tx = targetNode.getChoordX() - Double.valueOf((nodeHeight / 2)).intValue();
						// } else {
						// tx = targetNode.getChoordX() + Double.valueOf((nodeHeight / 2)).intValue();
						// }
						// }
						// if (startNode.getChoordY() == targetNode.getChoordY()) {
						// ty = targetNode.getChoordY();
						// } else {
						// if ((startNode.getChoordY() - targetNode.getChoordY()) < 0) {
						// ty = targetNode.getChoordY() - Double.valueOf((nodeHeight / 2)).intValue();
						// } else {
						// ty = targetNode.getChoordY() + Double.valueOf((nodeHeight / 2)).intValue();
						// }
						// }

						// drawArrow(g, startNode.getChoordX(), startNode.getChoordY(), tx, ty);

						// draw edge costs
						int labelX = (startNode.getChoordX() - targetNode.getChoordX()) / 2;
						int labelY = (startNode.getChoordY() - targetNode.getChoordY()) / 2;

						labelX *= -1;
						labelY *= -1;

						labelX += startNode.getChoordX();
						labelY += startNode.getChoordY();

						// LOGGER.log(Level.INFO, "LabelX: " + labelX);
						// LOGGER.log(Level.INFO, "LabelY: " + labelY);

						g.setColor(Color.BLACK);
						g.drawString(String.valueOf(edge.getCosts()), labelX - f.stringWidth(String.valueOf(edge.getCosts())) / 2, labelY + f.getHeight() / 2);
					}
				}

				for (Entry<Node, ChoordWrapper> entry : choordsForNodes.entrySet()) {
					// first paint a single node for testing.
					g.setColor(Color.black);
					// int nodeWidth = Math.max(width, f.stringWidth(entry.getKey().getNodeText()) + width / 2);

					ChoordWrapper wrapper = entry.getValue();
					// LOGGER.log(Level.INFO, wrapper.toString());

					g.setColor(Color.white);
					g.fillOval(wrapper.getChoordX() - nodeWidth / 2, wrapper.getChoordY() - nodeHeight / 2, nodeWidth, nodeHeight);
					g.setColor(Color.black);
					g.drawOval(wrapper.getChoordX() - nodeWidth / 2, wrapper.getChoordY() - nodeHeight / 2, nodeWidth, nodeHeight);

					g.drawString(entry.getKey().getNodeText(), wrapper.getChoordX() - f.stringWidth(entry.getKey().getNodeText()) / 2,
							wrapper.getChoordY() + f.getHeight() / 2);

				}
			}
		};
		JScrollPane scrollPane = new JScrollPane(drawArea);

		drawArea.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		// scrollPane.setPreferredSize(new Dimension(200, 200));

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(scrollPane);
	}

	private void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
		Graphics2D g = (Graphics2D) g1.create();

		double dx = x2 - x1, dy = y2 - y1;
		double angle = Math.atan2(dy, dx);
		int len = (int) Math.sqrt(dx * dx + dy * dy);
		AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
		at.concatenate(AffineTransform.getRotateInstance(angle));
		g.transform(at);

		// Draw horizontal arrow starting in (0, 0)
		// g.drawLine(0, 0, len, 0);
		g.fillPolygon(new int[] { len, len - ARR_SIZE, len - ARR_SIZE, len }, new int[] { 0, -ARR_SIZE, ARR_SIZE, 0 }, 4);
	}

	/**
	 * Inner class to wrap the node choordinates.
	 * 
	 * @author Matthias Drummer
	 * @author Marcel Piater
	 */
	private class ChoordWrapper {

		private int choordX;
		private int choordY;

		/**
		 * Constructor.
		 * 
		 * @param choordX the x choordinate
		 * @param choordY the y choordinate
		 */
		public ChoordWrapper(int choordX, int choordY) {
			this.choordX = choordX;
			this.choordY = choordY;
		}

		public int getChoordX() {
			return choordX;
		}

		public int getChoordY() {
			return choordY;
		}

		@Override
		public String toString() {
			return "ChoordWrapper [choordX=" + choordX + ", choordY=" + choordY + "]";
		}

	}
}
