package de.htw.berlin.student.graph;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.xml.bind.JAXBException;

import de.htw.berlin.student.graph.dialog.AddEdgeDialog;
import de.htw.berlin.student.graph.dialog.DeleteNodeDialog;
import de.htw.berlin.student.graph.dialog.NewNodeDialog;
import de.htw.berlin.student.graph.dialog.RemoveEdgeDialog;
import de.htw.berlin.student.graph.io.FileUtil;
import de.htw.berlin.student.graph.model.Graph;
import de.htw.berlin.student.graph.view.ShowGraphView;

/**
 * The main window of the application.
 * 
 * @author Matthias Drummer
 */
public class MainWindow extends JFrame {

	private static final long serialVersionUID = -2488306547631112453L;
	private static final Logger LOGGER = Logger.getLogger(MainWindow.class.getName());

	private JPanel contentPane;
	private Graph graph;
	private ShowGraphView showGraphView;

	/**
	 * Default constructor.
	 */
	public MainWindow() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(1024, 768));
		setResizable(false);

		setTitle("Graph Programm");
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());

		initActionBar();
		initComponents();
		initMenu();
		doPosition();
		addGraphDrawArea();

		pack();
		setVisible(true);
	}

	private void doPosition() {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = screenSize.height;
		int width = screenSize.width;

		int x = (width / 2 - 1024 / 2);
		int y = (height / 2 - 768 / 2);
		// One could use the dimension of the frame. But when doing so, one have to call this method !BETWEEN! pack and
		// setVisible. Otherwise the calculation will go wrong.

		this.setLocation(x, y);
	}

	private void initComponents() {

		graph = new Graph(); // initial create an empty graph.

	}

	/**
	 * All actions are done within a dialog window
	 */
	private void initActionBar() {

		JPanel actionBar = new JPanel();
		contentPane.add(actionBar, BorderLayout.NORTH);
		actionBar.setLayout(new FlowLayout(FlowLayout.LEFT));

		// create button new Node
		JButton newNodeBtn = new JButton("Neuer Knoten");
		newNodeBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				NewNodeDialog dialog = new NewNodeDialog(graph, MainWindow.this);
				showGraphView.repaint();
			}
		});

		// create button to add edge
		JButton addEdgeBtn = new JButton("Kante hinzufügen");
		addEdgeBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AddEdgeDialog dialog = new AddEdgeDialog(MainWindow.this, graph);
				showGraphView.repaint();
			}
		});

		// create button to delete edge
		JButton deleteEdgeBtn = new JButton("Kante löschen");
		deleteEdgeBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				RemoveEdgeDialog dialog = new RemoveEdgeDialog(MainWindow.this, graph);
				showGraphView.repaint();
			}
		});

		// create button to delete node
		JButton deleteNodeBtn = new JButton("Knoten löschen");
		deleteNodeBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DeleteNodeDialog dialog = new DeleteNodeDialog(MainWindow.this, graph);
				showGraphView.repaint();
			}
		});

		actionBar.add(newNodeBtn);
		actionBar.add(addEdgeBtn);
		actionBar.add(deleteEdgeBtn);
		actionBar.add(deleteNodeBtn);
	}

	private void initMenu() {

		JMenuBar menubar = new JMenuBar();
		JMenu mainMenu = new JMenu("Hauptmenü");

		JMenuItem newGraphItem = new JMenuItem("Neuer leerer Graph");
		newGraphItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				graph = new Graph();
				showGraphView.repaint();
			}
		});

		JMenuItem saveGraphItem = new JMenuItem("Graph Speichern");
		saveGraphItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: save the existing graph to file using some dialog.
				JFileChooser fc = new JFileChooser();

				if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					String path = fc.getSelectedFile().toString();
					try {
						FileUtil.saveGraph(path, graph);
						JOptionPane.showMessageDialog(MainWindow.this, "Graph erfolgreich gespeichert.");
					} catch (JAXBException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(MainWindow.this, "Beim Speichern trat ein Fehler auf.", "Fehler", JOptionPane.ERROR_MESSAGE);
					} catch (IOException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(MainWindow.this, "Beim Speichern trat ein Fehler auf.", "Fehler", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		JMenuItem loadGraphItem = new JMenuItem("Graph Laden");
		loadGraphItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: load graph from file using file open dialog
				JFileChooser fc = new JFileChooser();
				if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					try {
						graph = FileUtil.loadGraph(file);
					} catch (JAXBException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(MainWindow.this, "Beim Laden trat ein Fehler auf.", "Fehler", JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});

		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(JFrame.NORMAL);
			}
		});

		mainMenu.add(saveGraphItem);
		mainMenu.add(loadGraphItem);
		mainMenu.add(exitItem);

		menubar.add(mainMenu);
		setJMenuBar(menubar);
	}

	private void addGraphDrawArea() {

		showGraphView = new ShowGraphView(graph);
		showGraphView.setBorder(BorderFactory.createLineBorder(Color.RED));

		contentPane.add(showGraphView, BorderLayout.CENTER);
	}
}
