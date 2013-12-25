package de.htw.berlin.student.graph.model;

import java.io.Serializable;

/**
 * A model that represents an edge and contains one node.
 * 
 * @author Matthias Drummer
 */
public class Edge implements Serializable {

	private static final long serialVersionUID = -323711243605074965L;

	private Node node;
	private Integer costs;

	/**
	 * Constructor.
	 * 
	 * @param node the node that belongs to the edge.
	 */
	public Edge(Node node) {
		this.node = node;
	}

	/**
	 * Constructor.
	 * 
	 * @param node the node that belongs to the edge.
	 * @param costs the costs of the edge if available.
	 */
	public Edge(Node node, Integer costs) {
		this.node = node;
		this.costs = costs;
	}

	public Node getNode() {
		return node;
	}

	public Integer getCosts() {
		return costs;
	}

	@Override
	public String toString() {
		return "Edge [node=" + node + ", costs=" + costs + "]";
	}

}
