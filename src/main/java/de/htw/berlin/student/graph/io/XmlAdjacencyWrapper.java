package de.htw.berlin.student.graph.io;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * The xml adjacency wrapper to steer around the map problem with jaxb.
 * 
 * @author Matthias Drummer
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlAdjacencyWrapper {

	private XmlNode node;
	
	@XmlElement(name = "edge")
	private List<XmlEdge> edges;

	public void setEdges(List<XmlEdge> edges) {
		this.edges = edges;
	}

	public List<XmlEdge> getEdges() {
		return edges;
	}

	public void setNode(XmlNode node) {
		this.node = node;
	}

	public XmlNode getNode() {
		return node;
	}

	@Override
	public String toString() {
		return "XmlAdjacencyWrapper [node=" + node + ", edges=" + edges + "]";
	}

}
