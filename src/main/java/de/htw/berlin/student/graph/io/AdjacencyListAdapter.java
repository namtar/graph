package de.htw.berlin.student.graph.io;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import de.htw.berlin.student.graph.model.Edge;
import de.htw.berlin.student.graph.model.Node;

public class AdjacencyListAdapter extends XmlAdapter<AdjacencyListAdapter.AdaptedMap, Map<Node, List<Edge>>> {

	public static class AdaptedMap {

		public List<Entry> entries = new ArrayList<Entry>();
	}

	public static class Entry {

		public Node node;
		@XmlElement(name = "edges")
		public List<Edge> edges;

		/**
		 * Default constructor needed by JaxB.
		 */
		public Entry() {
		}

		public Entry(Node node, List<Edge> edges) {
			this.node = node;
			this.edges = edges;
		}
	}

	@Override
	public Map<Node, List<Edge>> unmarshal(AdaptedMap v) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AdaptedMap marshal(Map<Node, List<Edge>> v) throws Exception {

		AdaptedMap map = new AdaptedMap();
		for (java.util.Map.Entry<Node, List<Edge>> entry : v.entrySet()) {
			map.entries.add(new Entry(entry.getKey(), entry.getValue()));
		}
		return map;
	}
}
