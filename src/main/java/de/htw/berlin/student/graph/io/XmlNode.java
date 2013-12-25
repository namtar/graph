package de.htw.berlin.student.graph.io;

/**
 * The node xml representation.
 * 
 * @author Matthias Drummer
 */
public class XmlNode {

	private String nodeText;

	public void setNodeText(String nodeText) {
		this.nodeText = nodeText;
	}

	public String getNodeText() {
		return nodeText;
	}

	@Override
	public String toString() {
		return "XmlNode [nodeText=" + nodeText + "]";
	}

}
