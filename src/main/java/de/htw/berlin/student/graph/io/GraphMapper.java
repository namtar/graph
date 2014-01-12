package de.htw.berlin.student.graph.io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.htw.berlin.student.graph.model.Edge;
import de.htw.berlin.student.graph.model.Graph;
import de.htw.berlin.student.graph.model.Node;

/**
 * A mapper helper.
 * 
 * @author Matthias Drummer
 */
public final class GraphMapper {

	/**
	 * Private constructor.
	 */
	private GraphMapper() {
	}

	/**
	 * Maps a {@link Graph} to a {@link XmlGraph}.
	 * 
	 * @param graph the {@link Graph} to be mapped
	 * @return the mapped {@link XmlGraph}
	 */
	public static XmlGraph mapGraphToXmlGraph(Graph graph) {

		XmlGraph xGraph = new XmlGraph();

		xGraph.setAdjacencyWrapper(mapAdjacencyListToXmlAdjacencyWrapper(graph.getAdjacencyList()));

		return xGraph;
	}

	private static List<XmlAdjacencyWrapper> mapAdjacencyListToXmlAdjacencyWrapper(Map<Node, List<Edge>> adjacencyList) {

		List<XmlAdjacencyWrapper> wrapper = new ArrayList<XmlAdjacencyWrapper>();

		if (adjacencyList == null) {
			return wrapper;
		}

		for (Entry<Node, List<Edge>> entry : adjacencyList.entrySet()) {

			XmlAdjacencyWrapper wrapperItem = new XmlAdjacencyWrapper();
			wrapperItem.setNode(mapNodeToXmlNode(entry.getKey()));

			List<XmlEdge> xmlEdges = new ArrayList<XmlEdge>();

			if (entry.getValue() != null) {
				for (Edge edge : entry.getValue()) {
					xmlEdges.add(mapEdgeToXmlEdge(edge));
				}
			}
			wrapperItem.setEdges(xmlEdges);
			wrapper.add(wrapperItem);
		}

		return wrapper;
	}

	private static XmlNode mapNodeToXmlNode(Node node) {

		XmlNode xmlNode = new XmlNode();
		if (node != null) {
			xmlNode.setNodeText(node.getNodeText());
		}

		return xmlNode;
	}

	private static XmlEdge mapEdgeToXmlEdge(Edge edge) {

		XmlEdge xmlEdge = new XmlEdge();

		xmlEdge.setCosts(edge.getCosts());
		xmlEdge.setNode(mapNodeToXmlNode(edge.getNode()));

		return xmlEdge;
	}

	/**
	 * Maps a {@link XmlGraph} to a {@link Graph}.
	 * 
	 * @param xmlGraph the {@link XmlGraph} to be mapped
	 * @return the mapped {@link Graph}
	 */
	public static Graph mapXmlGraphToGraph(XmlGraph xmlGraph) {

		Graph graph = new Graph();

		graph.setAdjacencyList(mapXmlAdjacencyWrapperToMap(xmlGraph.getAdjacencyWrapper()));

		return graph;
	}

	private static Map<Node, List<Edge>> mapXmlAdjacencyWrapperToMap(List<XmlAdjacencyWrapper> xmlAdjacencyWrapper) {

		Map<Node, List<Edge>> adjacencyList = new HashMap<Node, List<Edge>>();

		for (XmlAdjacencyWrapper wrapper : xmlAdjacencyWrapper) {

			Node node = mapXmlNodeToNode(wrapper.getNode());
			List<Edge> edges = mapXmlEdgesToEdges(wrapper.getEdges());
			adjacencyList.put(node, edges);
		}

		return adjacencyList;
	}

	private static Node mapXmlNodeToNode(XmlNode xmlNode) {

		Node node = new Node();

		node.setNodeText(xmlNode.getNodeText());
		return node;
	}

	private static List<Edge> mapXmlEdgesToEdges(List<XmlEdge> xmlEdges) {

		List<Edge> edges = new ArrayList<Edge>();

		if (xmlEdges == null) {
			return edges;
		}

		for (XmlEdge xmlEdge : xmlEdges) {

			Edge edge = new Edge(mapXmlNodeToNode(xmlEdge.getNode()), xmlEdge.getCosts());
			edges.add(edge);
		}

		return edges;
	}
}
