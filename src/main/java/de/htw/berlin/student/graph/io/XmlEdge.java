package de.htw.berlin.student.graph.io;


/**
 * The xml representation of a node.
 * 
 * @author Matthias Drummer
 */
public class XmlEdge {

	private XmlNode node;
	private Integer costs;

	public void setNode(XmlNode node) {
		this.node = node;
	}

	public XmlNode getNode() {
		return node;
	}

	public void setCosts(Integer costs) {
		this.costs = costs;
	}

	public Integer getCosts() {
		return costs;
	}

	@Override
	public String toString() {
		return "XmlEdge [node=" + node + ", costs=" + costs + "]";
	}

}
