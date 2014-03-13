package isidis.dfs.team.DFSGUI2;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Supervisor {

	private static JPanel panel;
	private static JLabel labelMessage;
	private static JTextArea screenSupervisor;
	private static JScrollPane scroll;
	private static Supervisor supervisor;
	
	private Supervisor()	{
		super();
		panel = new JPanel(new BorderLayout());
		labelMessage = new JLabel("Supervisor");
		screenSupervisor = new JTextArea(10,90);
		scroll = new JScrollPane(screenSupervisor);
		screenSupervisor.setEditable(false);
		panel.add(labelMessage,BorderLayout.NORTH);
		panel.add(scroll,BorderLayout.SOUTH);
		//supervisor.append("Hoo");
	}
	
	public static Supervisor getInstance()	{
		
		if	(supervisor==null)
			return new Supervisor();
		else
			return supervisor;
	}
	
	public static JPanel getSupervisor()	{
		return panel;
	}
	
	public JTextArea getScrenSupervisor()	{
		return screenSupervisor;
	}
}
