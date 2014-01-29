package isidis.dfs.team.DFSGUI;

import isidis.dfs.team.tools.DFSProvider;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Connection extends JFrame implements ActionListener {

	private JTextField URL = new JTextField("hdfs://192.168.0.41:9000", 30);
	private JLabel lebel = new JLabel("URL to Connect : ");
	private static JButton press = new JButton("Connexion");
	private JPanel panel = new JPanel(new FlowLayout());
	private JPanel panel1 = new JPanel(new FlowLayout());
	private JTextField user = new JTextField("hduser", 10);
	
	private JFrame frame;
	
	public Connection()	{
		frame = new JFrame("Connexion");
		
		panel1.add(lebel);
		panel1.add(URL);
		panel1.add(user);
		panel.add(panel1);
		panel.add(press);
		URL.addActionListener(this);
		press.addActionListener(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.pack();
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if	(e.getSource() == press)	{
			try {
				DFSProvider.getInstance(URL.getText(), user.getText());
				new MainFrame().createAndShowGUI();
				frame.dispose();
			} catch (URISyntaxException e1) {
				URL.setText("URI Syntax Exception");
			}
		}
	}
	public static void main(String[] args) {
		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Turn off metal's use of bold fonts
				
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				new Connection();
				System.out.println(press.getActionCommand());
				//MainFrame.createAndShowGUI();
			}
		});
	}
	
	
}
