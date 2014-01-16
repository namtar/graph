package de.htw.berlin.student.graph;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.htw.berlin.student.graph.model.Edge;
import de.htw.berlin.student.graph.model.Graph;
import de.htw.berlin.student.graph.model.Node;

public class TestGraph {

	private Graph testInstance;

	@Before
	public void before() {
		this.testInstance = new Graph();
	}

	/**
	 * Tests the {@link Graph#addNode(de.htw.berlin.student.graph.model.Node)} method.
	 */
	@Test
	public void testAddNode() {

		// preparation
		Node node = new Node("Testnode");

		// execute
		testInstance.addNode(node);

		// validation / verification
		Map<Node, List<Edge>> list = testInstance.getAdjacencyList();

		Assert.assertNotNull("The list may not be null.", list);
		Assert.assertTrue(list.keySet().size() == 1);

		for (Node nodeFromMap : list.keySet()) {
			Assert.assertEquals(nodeFromMap, node);
			Assert.assertEquals(nodeFromMap.getNodeText(), "Testnode");
		}
	}

	/**
	 * Tests the {@link Graph#addEdge(Node, Edge)} method.
	 */
	@Test
	public void testAddEdge() {

		// preparation
		Node node = new Node("Testnode");
		Node node2 = new Node("Testnode2");
		Edge edge = new Edge(node2, 10);

		// execute
		testInstance.addNode(node);
		testInstance.addNode(node2);
		testInstance.addEdge(node, edge);

		// verify
		Map<Node, List<Edge>> list = testInstance.getAdjacencyList();
		Assert.assertNotNull("The list may not be null.", list);
		Assert.assertTrue(list.keySet().size() == 2);

		Assert.assertNotNull(list.get(node));
		Assert.assertFalse(list.get(node).isEmpty());
		Assert.assertTrue(list.get(node).size() == 1);
		Assert.assertEquals(edge, list.get(node).get(0));

		Assert.assertNotNull(list.get(node2));
		Assert.assertFalse(list.get(node2).isEmpty());
		Assert.assertTrue(list.get(node2).size() == 1);
		Assert.assertFalse(edge.equals(list.get(node2).get(0)));

		Assert.assertEquals(list.get(node2).get(0).getNode(), node);
	}

	/**
	 * Tests the {@link Graph#createRandomGraph(int, int)} method.
	 */
	@Test
	public void testGenerateRandomGraph() {

		// preparation
		int numberOfNodes = 10;
		int numberOfEdges = 45;

		// execute
		for (int i = 0; i < 10; i++) {
			testInstance.createRandomGraph(numberOfNodes, numberOfEdges);

			// verfiy
			Map<Node, List<Edge>> list = testInstance.getAdjacencyList();

			Assert.assertNotNull("The list may not be null.", list);
			Assert.assertTrue(list.keySet().size() == numberOfNodes);

			for (Entry<Node, List<Edge>> entry : list.entrySet()) {
				Assert.assertNotNull(entry.getValue());

				StringBuilder sb = new StringBuilder("Edges: ");
				for (Edge edge : entry.getValue()) {
					sb.append(edge.getNode());
					sb.append(", ");
				}

				Assert.assertTrue("Node failed: " + entry.getKey() + ", Edges size: " + entry.getValue().size() + sb.toString(),
						entry.getValue().size() == numberOfNodes - 1);
			}

		}

	}
}
