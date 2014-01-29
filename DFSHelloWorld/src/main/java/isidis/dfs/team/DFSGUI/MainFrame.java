package isidis.dfs.team.DFSGUI;

/*
 * TabbedPaneDemo.java requires one additional file:
 *   images/middle.gif.
 */

import javax.swing.JTabbedPane;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

public class MainFrame extends JPanel {
	
	private JPanel mainPanel;
	private JPanel secondaryPanel1;
	private JPanel secondaryPanel2;
	private JPanel secondaryPanel3;
	private static Supervisor supervisor;
	
	public MainFrame() {
		super();
		
		mainPanel = new JPanel();
		secondaryPanel1 = new JPanel(new GridLayout(1, 1));
		secondaryPanel2 = new JPanel(new FlowLayout());
		secondaryPanel3 = new JPanel(new BorderLayout());
		
		supervisor = Supervisor.getInstance();
		
		JTabbedPane tabbedPane = new JTabbedPane();
		
		ReadFrame readFrame = new ReadFrame();
		WriteFrame writeFrame = new WriteFrame();
		DeleteFile delete = new DeleteFile();
		
		secondaryPanel3.add(writeFrame.getWriteFrame() , BorderLayout.NORTH);
		secondaryPanel3.add(delete.getDeleteFrame(),BorderLayout.SOUTH);
		
		secondaryPanel1.add(readFrame.build());
		secondaryPanel1.add(secondaryPanel3);
		secondaryPanel2.add(supervisor.getSupervisor());
		
		this.add(secondaryPanel1);
		this.add(secondaryPanel2);
		
	}

	/**
	 * 
	 */
	public static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("Distributed Files System Monitoring");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		// Add content to the window.
		//frame.setSize(new Dimension(300,300));
		frame.setPreferredSize(new Dimension((int)dimension.getWidth(),
												(int)dimension.getHeight()-40));
		//frame.setLocationRelativeTo(null);
		frame.add(new MainFrame(), BorderLayout.CENTER);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}
}
