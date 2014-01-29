package isidis.dfs.team.DFSGUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class WriteFrame extends JComponent implements ActionListener  {

	private JPanel panel;
	private JTextField pathFile;
	private JLabel labelPathFile;
	private JFileChooser fc;
	private File file;
	private JTabbedPane tabbedPane;
	private JButton buttonWrite;
	private Supervisor supervisor;
	private static final String newline = "\n";
	private byte[] byteFile;
	
	private JPanel panel1;
	
	public WriteFrame()	{
		
		panel = new JPanel(new FlowLayout());
		panel.setPreferredSize(new Dimension(500, 200));
		panel1 = new JPanel(new FlowLayout());
		pathFile = new JTextField(15);
		labelPathFile = new JLabel("File name : ");
		buttonWrite = new JButton("Search File Write");
		buttonWrite.addActionListener(this);
		fc = new JFileChooser();
		supervisor = Supervisor.getInstance();
		
		tabbedPane = new JTabbedPane();
		
		panel1.add(labelPathFile);
		panel1.add(pathFile);
		panel.add(panel1);
		panel.add(buttonWrite);
	}
	
	public JTabbedPane getWriteFrame()	{
		
		JTextArea area1 = new JTextArea();
		tabbedPane.addTab("Write File", panel);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
	return tabbedPane;
}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if	(e.getSource() == buttonWrite)	{
			
			int returnVal = fc.showOpenDialog(this);
			file = fc.getSelectedFile();
			if	(file != null)
				supervisor.getScrenSupervisor().append("Selected File  :  "+file.getName()+ ""+newline);
		}
	}
}
