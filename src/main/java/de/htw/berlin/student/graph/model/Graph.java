package de.htw.berlin.student.graph.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.htw.berlin.student.graph.dialog.GenerateRandomGraphDialog;

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

		// do birectional adding. Ugly duplicated code. TODO: refactor
		// only execute when there is an edge defined.
		if (value != null) {
			Edge reverseEdge = new Edge(key, value.getCosts());

			if (adjacencyList.containsKey(value.getNode())) {
				if (adjacencyList.get(value.getNode()) == null) {
					adjacencyList.put(value.getNode(), new ArrayList<Edge>());
				}
				// TODO: perhaps check if a value may not be added twice.
				// add edge if not null
				if (reverseEdge != null) {
					adjacencyList.get(value.getNode()).add(reverseEdge);
				}
			} else {
				List<Edge> edges = new ArrayList<Edge>();
				// add edge if not null
				if (reverseEdge != null) {
					edges.add(reverseEdge);
				}

				adjacencyList.put(value.getNode(), edges);
			}
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
		// remove bidirectional
		List<Edge> reverseEdges = adjacencyList.get(value.getNode());
		List<Edge> toRemove = new ArrayList<Edge>();
		for (Edge edge : reverseEdges) {
			if (edge.getNode().equals(key)) {
				toRemove.add(edge);
			}
		}
		reverseEdges.removeAll(toRemove);
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

	/**
	 * Creates a number of nodes with random edges by the given values.
	 * 
	 * @param numberOfNodes the number of nodes to be created
	 * @param numberOfEdges the number of random edges to be created
	 */
	public void createRandomGraph(int numberOfNodes, int numberOfEdges) {

		int numberOfPossibleEdges = 0;
		for (int i = numberOfNodes - 1; i > 0; i--) {
			numberOfPossibleEdges += i;
		}
		if (numberOfEdges > numberOfPossibleEdges) {
			throw new IllegalArgumentException("There is a limit of a maximum of edges by " + numberOfPossibleEdges);
		}

		// generate graph by settings
		adjacencyList.clear();
		for (int i = 1; i <= numberOfNodes; i++) {
			Node node = new Node();
			node.setNodeText(String.valueOf(i));
			addNode(node);
		}

		Node[] nodes = new Node[numberOfNodes];
		adjacencyList.keySet().toArray(nodes);
		// edges
		Random rand = new Random();
		int nodeIndex = 0;
		for (int i = 0; i < numberOfEdges; i++) {
			// first add for every node a random edge.

			Node startNode = nodes[nodeIndex];
			// create random edge for actual node.
			boolean hasAlreadyEdge = true;
			// only enter loop, when node is not already full with edges
			while (hasAlreadyEdge && !(adjacencyList.get(startNode).size() == numberOfNodes - 1)) {
				int randomInt = rand.nextInt(numberOfNodes);
				// Logger.getLogger(GenerateRandomGraphDialog.class.getName()).log(Level.INFO, "RandomInt: " +
				// randomInt);
				if (randomInt == nodeIndex) {
					// Logger.getLogger(GenerateRandomGraphDialog.class.getName()).log(Level.INFO, "NumberOfNodes: " +
					// numberOfNodes);
					Logger.getLogger(GenerateRandomGraphDialog.class.getName()).log(Level.INFO,
							"Kollission: randomInt / nodeIndex " + randomInt + " / " + nodeIndex);
					continue;
				}

				Node checkNode = nodes[randomInt];

				List<Edge> edgesForNode = adjacencyList.get(startNode);
				boolean contained = false;
				for (Edge edge : edgesForNode) {
					if (edge.getNode().equals(checkNode)) {
						contained = true;
						break;
					}
				}
				if (!contained) {
					// add new Edge
					Random edgeCostRandom = new Random();
					Edge edge = new Edge(checkNode, edgeCostRandom.nextInt(100));
					addEdge(startNode, edge); // this will add the edge bidirectional. If the node contains the edge.
												// The
												// reverse node has the edge too.
					hasAlreadyEdge = false;
				}

			}

			if (nodeIndex == numberOfNodes - 1) {
				// then start with the first node again.
				Logger.getLogger(GenerateRandomGraphDialog.class.getName()).log(Level.INFO, "Restart Nodes");
				nodeIndex = 0;
			} else {
				nodeIndex++;
			}

		}

	}

	@Override
	public String toString() {
		return "Graph [adjacencyList=" + adjacencyList + "]";
	}

}
