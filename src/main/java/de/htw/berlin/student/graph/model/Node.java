package de.htw.berlin.student.graph.model;

import java.io.Serializable;

/**
 * A node model for the graph.
 * 
 * @author Matthias Drummer
 */
public class Node implements Serializable {

	private static final long serialVersionUID = 823544330517091616L;

	private String nodeText;

	/**
	 * Default constructor.
	 */
	public Node() {
	}

	/**
	 * Constructor.
	 * 
	 * @param nodeText the text of the node
	 */
	public Node(String nodeText) {
		this.nodeText = nodeText;
	}

	public void setNodeText(String nodeText) {
		this.nodeText = nodeText;
	}

	public String getNodeText() {
		return nodeText;
	}

	@Override
	public String toString() {
		return "Node [nodeText=" + nodeText + "]";
	}

}
