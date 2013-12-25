package de.htw.berlin.student.graph.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import de.htw.berlin.student.graph.model.Graph;

/**
 * Class that writes a graph to a file and reads a graph from a file.
 * 
 * @author Matthias Drummer
 */
public class FileUtil {

	private final static Class<?>[] classes = { XmlGraph.class, XmlAdjacencyWrapper.class, XmlNode.class, XmlEdge.class };

	/**
	 * Private Constructor to prevent instantiation.
	 */
	private FileUtil() {
	}

	/**
	 * Saves the graph to the given path / filename.
	 * 
	 * @param path the name of the file
	 * @param graph the {@link Graph}
	 * @throws JAXBException
	 * @throws IOException
	 */
	public static void saveGraph(String path, Graph graph) throws JAXBException, IOException {

		StringWriter stringWriter = new StringWriter();
		XmlGraph xmlGraph = GraphMapper.mapGraphToXmlGraph(graph);

		JAXBContext context = JAXBContext.newInstance(classes);
		Marshaller marshaller = context.createMarshaller();
		marshaller.marshal(xmlGraph, stringWriter);
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		OutputStream outputStream = new ByteArrayOutputStream();

		outputStream.write(stringWriter.toString().getBytes());

		File file = new File(path);
		FileOutputStream out = new FileOutputStream(file);
		out.write(stringWriter.toString().getBytes());
		out.close();

	}

	/**
	 * Loads the {@link Graph} from a given file.
	 * 
	 * @param file the file which contains the graph data
	 * @return a {@link Graph}
	 * @throws JAXBException
	 */
	public static Graph loadGraph(File file) throws JAXBException {

		Graph graph;

		JAXBContext context = JAXBContext.newInstance(classes);
		Unmarshaller unmarshaller = context.createUnmarshaller();

		XmlGraph xmlGraph = (XmlGraph) unmarshaller.unmarshal(file);

		graph = GraphMapper.mapXmlGraphToGraph(xmlGraph);

		return graph;
	}
}
