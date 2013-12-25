package graph;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.junit.Before;
import org.junit.Test;

import de.htw.berlin.student.graph.io.GraphMapper;
import de.htw.berlin.student.graph.io.XmlAdjacencyWrapper;
import de.htw.berlin.student.graph.io.XmlEdge;
import de.htw.berlin.student.graph.io.XmlGraph;
import de.htw.berlin.student.graph.io.XmlNode;
import de.htw.berlin.student.graph.model.Edge;
import de.htw.berlin.student.graph.model.Graph;
import de.htw.berlin.student.graph.model.Node;

public class XmlTest {

	private Graph graph;

	@Before
	public void before() {

		Map<Node, List<Edge>> adjacencyList = new HashMap<Node, List<Edge>>();

		Node node = new Node("Testnode1");
		Node node2 = new Node("Testnode2");
		Node node3 = new Node("Testnode3");

		List<Edge> nodeList = new ArrayList<Edge>();
		nodeList.add(new Edge(node2, 1));
		nodeList.add(new Edge(node3, 2));
		
		List<Edge> nodeList2 = new ArrayList<Edge>();
		nodeList2.add(new Edge(node, 3));

		adjacencyList.put(node, nodeList);
		adjacencyList.put(node2, nodeList2);

		graph = new Graph(adjacencyList);
	}

	@Test
	public void testJaxBConversion() {

		Class<?>[] classes = { XmlGraph.class, XmlAdjacencyWrapper.class, XmlNode.class, XmlEdge.class };

		try {
			XmlGraph xmlGraph = GraphMapper.mapGraphToXmlGraph(graph);

			JAXBContext context = JAXBContext.newInstance(classes);
			Marshaller marshaller = context.createMarshaller();
			StringWriter stringWriter = new StringWriter();
			marshaller.marshal(xmlGraph, stringWriter);
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			System.out.println(stringWriter.toString());

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}
