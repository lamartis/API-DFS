package isidis.dfs.team.DFSGUI;

import isidis.dfs.team.tools.DFSProvider;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DeleteFile implements ActionListener {

	private static final String newline = "\n";

	private JPanel panel;
	private JTextField pathFile;
	private JLabel labelNameFile;
	private File file;
	private JTabbedPane tabbedPane;
	private JButton buttonDelete;
	private Supervisor supervisor;
	private JPanel panel1;


	public DeleteFile()	{

		panel = new JPanel(new FlowLayout());
		panel.setPreferredSize(new Dimension(500,200));
		panel1 = new JPanel(new FlowLayout());
		pathFile = new JTextField(15);
		labelNameFile = new JLabel("File name : ");
		buttonDelete = new JButton("Delete File");
		buttonDelete.addActionListener(this);
		supervisor = Supervisor.getInstance();

		tabbedPane = new JTabbedPane();

		panel1.add(labelNameFile);
		panel1.add(pathFile);
		panel.add(panel1);
		panel.add(buttonDelete);
	}

	public JTabbedPane getDeleteFrame()	{

		JTextArea area1 = new JTextArea();
		tabbedPane.addTab("Delete File", panel);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		return tabbedPane;
	}

	public void actionPerformed(ActionEvent e) {
	/*	String message = null;
		if	(e.getSource() == buttonDelete)	{
			try {
				DFSProvider.getInstance().deleteFile(pathFile.getText());
				message =  " with success";
			} catch (SystemUserPermissionException e1) {
				message =  " [System user permission exception]";
			} catch (FileNotFoundException e1) {
				message =  " [File not found Exception]";
			} catch (EndpointNotReacheableException e1) {
				message =  " [Endpoint Not Reacheable Exception]";
			}
			supervisor.getScrenSupervisor().append("Delete File: "+ pathFile.getText() + message + "\n");

		}*/
	}

}
