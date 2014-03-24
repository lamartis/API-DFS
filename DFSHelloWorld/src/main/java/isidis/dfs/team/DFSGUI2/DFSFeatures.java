package isidis.dfs.team.DFSGUI2;

import isidis.dfs.team.api.dfs.common.exceptions.EndpointNotReacheableException;
import isidis.dfs.team.api.dfs.common.exceptions.FileNotFoundException;
import isidis.dfs.team.api.dfs.common.exceptions.SystemUserPermissionException;
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

import org.apache.hadoop.fs.PathExistsException;
import org.apache.hadoop.hdfs.protocol.HdfsFileStatus;

public class DFSFeatures implements ActionListener {

	private static final String newline = "\n";

	private JPanel panel;
	private JTextField pathFile;
	private JLabel labelNameFile;
	private File file;
	private JTabbedPane tabbedPane;
	private JButton buttonCreate;
	private JButton buttonList;
	private Supervisor supervisor;
	private JPanel panel1, panel2;
	private JTextField renameField ;

	public DFSFeatures()	{

		panel = new JPanel(new FlowLayout());
		panel.setPreferredSize(new Dimension(500,200));
		panel1 = new JPanel(new FlowLayout());
		panel2 = new JPanel(new FlowLayout());
		
		pathFile = new JTextField(15);
		labelNameFile = new JLabel("Path location : ");
		buttonCreate = new JButton("create");
		buttonList = new JButton("list");
		
		buttonCreate.addActionListener(this);
		supervisor = Supervisor.getInstance();

		tabbedPane = new JTabbedPane();

		panel1.add(labelNameFile);
		panel1.add(pathFile);
		panel1.add(buttonCreate);
		panel1.add(buttonList);
		
		JButton renameButton = new JButton("Rename/Place");
		renameField = new JTextField(15);
		JLabel filepathLocation = new JLabel("Path/File location : ");
		panel2.add(filepathLocation);
		panel2.add(renameField);
		panel2.add(renameButton);
		
		panel.add(panel1);
		panel.add(panel2);
	}

	public JTabbedPane getDeleteFrame()	{

		JTextArea area1 = new JTextArea();
		tabbedPane.addTab("Additional features", panel);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		return tabbedPane;
	}

	public void actionPerformed(ActionEvent e) {
		String message = null;
		if	(e.getSource() == buttonCreate)	{
			try {
				DFSProvider.getInstance2().mkdirs(pathFile.getText());
				message =  " with success";
			} catch (EndpointNotReacheableException e1) {
				message =  " [Endpoint Not Reacheable Exception]";
			} catch (URISyntaxException e1) {
				message = " [URI Syntax Exception]";
			} catch (PathExistsException e1) {
				message = " [Path already Exists Exception]";
			}
			supervisor.getScrenSupervisor().append("Create folder: "+ pathFile.getText() + message + "\n");

		} else if (e.getSource() == buttonList) {
			
			try {
				HdfsFileStatus[] files = DFSProvider.getInstance2().listPaths(pathFile.getText());
				message =  " with success";
				
			} catch (EndpointNotReacheableException e1) {
				message =  " [Endpoint Not Reacheable Exception]";
			} catch (URISyntaxException e1) {
				message = " [URI Syntax Exception]";
			} catch (PathExistsException e1) {
				message = " [Path already Exists Exception]";
			}
			supervisor.getScrenSupervisor().append("Create folder: "+ pathFile.getText() + message + "\n");

			
		} else {
			
		}
	}

}
