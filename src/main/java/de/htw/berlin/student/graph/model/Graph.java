package de.htw.berlin.student.graph.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A graph model. Normally a model should not have any logic, but in this case we implement logic to manipulate the
 * adjacencyList like reorganizing, adding nodes, removing nodes, e.g
 * 
 * @author Matthias Drummer
 */
public class Graph implements Serializable {

	private static final long serialVersionUID = -8145332884618329120L;

	private Map<Node, List<Edge>> adjacencyList;

	/**
	 * Default constructor.
	 */
	public Graph() {
		// when creating a new graph ensure that a new adjacencyList is created
		adjacencyList = new HashMap<Node, List<Edge>>();
	}

	/**
	 * Constructor.
	 * 
	 * @param adjacencyList the structure of the graph
	 */
	public Graph(Map<Node, List<Edge>> adjacencyList) {
		this.adjacencyList = adjacencyList;
	}

	public void setAdjacencyList(Map<Node, List<Edge>> adjacencyList) {
		this.adjacencyList = adjacencyList;
	}

	public Map<Node, List<Edge>> getAdjacencyList() {
		return adjacencyList;
	}

	/**
	 * Adds a given edge to the adjacency list. If the base node is not yet part of the adjacency list a new entry is
	 * added.
	 * 
	 * @param key the base node
	 * @param value the value according to the base node
	 */
	public void addEdge(Node key, Edge value) {

		if (adjacencyList.containsKey(key)) {
			if (adjacencyList.get(key) == null) {
				adjacencyList.put(key, new ArrayList<Edge>());
			}
			// TODO: perhaps check if a value may not be added twice.
			// add edge if not null
			if (value != null) {
				adjacencyList.get(key).add(value);
			}
		} else {
			List<Edge> edges = new ArrayList<Edge>();
			// add edge if not null
			if (value != null) {
				edges.add(value);
			}

			adjacencyList.put(key, edges);
		}
	}

	/**
	 * Simply adds a new node, without setting any edges.
	 * 
	 * @param node the node to be added
	 */
	public void addNode(Node node) {
		addEdge(node, null);
	}

	public void removeEdge(Node key, Edge value) {

		if (!adjacencyList.containsKey(key)) {
			throw new IllegalArgumentException("The adjacency list does not contain a node for the given key: " + key);
		}
		List<Edge> edges = adjacencyList.get(key);

		if (!edges.contains(value)) {
			throw new IllegalArgumentException("The list of edges does not contain the given edge to remove: " + value);
		}

		edges.remove(value);
	}

	/**
	 * Deletes a node.
	 * 
	 * @param key the node to be deleted
	 */
	public void deleteNode(Node key) {

		if (!adjacencyList.containsKey(key)) {
			throw new IllegalArgumentException("The adjacency list does not contain a node for the given key: " + key);
		}

		adjacencyList.remove(key);

		// clean up all edges
		for (Entry<Node, List<Edge>> entry : adjacencyList.entrySet()) {

			List<Edge> toRemove = new ArrayList<Edge>();

			for (Edge edge : entry.getValue()) {
				if (edge.getNode().equals(key)) {
					toRemove.add(edge);
				}
			}
			entry.getValue().removeAll(toRemove);
		}
	}

	@Override
	public String toString() {
		return "Graph [adjacencyList=" + adjacencyList + "]";
	}

}
