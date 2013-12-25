package de.htw.berlin.student.graph.io;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A xml representation class.
 * 
 * @author Matthias Drummer
 */
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "Graph")
public class XmlGraph {

	@XmlElement(name = "adjacencyItem")
	private List<XmlAdjacencyWrapper> adjacencyWrapper;

	public void setAdjacencyWrapper(List<XmlAdjacencyWrapper> adjacencyWrapper) {
		this.adjacencyWrapper = adjacencyWrapper;
	}

	public List<XmlAdjacencyWrapper> getAdjacencyWrapper() {
		return adjacencyWrapper;
	}

	@Override
	public String toString() {
		return "XmlGraph [adjacencyWrapper=" + adjacencyWrapper + "]";
	}

}
