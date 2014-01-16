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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nodeText == null) ? 0 : nodeText.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (nodeText == null) {
			if (other.nodeText != null)
				return false;
		} else if (!nodeText.equals(other.nodeText))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Node [nodeText=" + nodeText + "]";
	}

}
