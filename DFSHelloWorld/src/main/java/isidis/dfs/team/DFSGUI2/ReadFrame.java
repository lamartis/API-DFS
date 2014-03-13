package isidis.dfs.team.DFSGUI2;

import isidis.dfs.team.api.dfs.common.exceptions.EndpointNotReacheableException;
import isidis.dfs.team.api.dfs.common.exceptions.FileNotFoundException;
import isidis.dfs.team.api.dfs.common.exceptions.FileSizeThresholdNotRespected;
import isidis.dfs.team.api.dfs.common.exceptions.SystemUserPermissionException;
import isidis.dfs.team.api2.dfs.interfaces.RemoteIterator;
import isidis.dfs.team.tools.DFSProvider;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.hadoop.fs.Path;

public class ReadFrame implements ActionListener {

	RemoteIterator<byte[]> remoteIterator2 = null;
	private static final String newline = "\n";
	private JPanel panel;
	private JTextField pathFile;
	private JLabel labelPathFile;
	//private JLabel LabelNameFile;
	private JScrollPane scroll;

	//Supervision des actions read
	private Supervisor supervisor;

	private JTabbedPane tabbedPane;
	private JTextArea area;
	private JButton buttonRead;

	private JPanel panel1;

	public ReadFrame()	{

		panel = new JPanel(new FlowLayout());
		panel.setPreferredSize(new Dimension(20,20));
		panel1 = new JPanel(new FlowLayout());
		pathFile = new JTextField(34);
		labelPathFile = new JLabel("File path : ");
		buttonRead = new JButton("Read");
		buttonRead.addActionListener(this);
		area = new JTextArea(20,40);
		scroll = new JScrollPane(area);

		supervisor = Supervisor.getInstance();

		tabbedPane = new JTabbedPane();

		panel1.add(labelPathFile);
		panel1.add(pathFile);
		panel.add(panel1);
		panel.add(scroll);
		panel.add(buttonRead);

	}

	public JTabbedPane build()	{
		tabbedPane.addTab("Read File", panel);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		return tabbedPane;
	}

	public void actionPerformed(ActionEvent e) {
		StringBuffer message = new StringBuffer();
		area.setText("");
		if	(e.getSource() == buttonRead) {
			try {
				System.out.println("reading " + pathFile.getText());
				remoteIterator2 = DFSProvider.getInstance2().readFile(pathFile.getText());
				message.append(" with success !");
			} catch (SystemUserPermissionException e1) {
				message.append("[system user permission Exception]");
			} catch (FileNotFoundException e1) {
				message.append("[File Not Found]");
			} catch (EndpointNotReacheableException e1) {
				message.append("[Endpoint not reacheable Exception]");
			} catch (FileSizeThresholdNotRespected e1) {
				message.append("[File Size Exceeds Fixed Threshold]");
			} catch (URISyntaxException e1) {
				message.append("[URI Syntax Exception]");
			}
			
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(new File(pathFile.getText().split("/")[pathFile.getText().split("/").length - 1]));
			} catch (java.io.FileNotFoundException e1) {
				e1.printStackTrace();
			}

			long numberOfBlocks = remoteIterator2.getNumberOfBlocks();
			int i = 1;
			try {
				while (remoteIterator2.hasNext()) {
					fos.write(remoteIterator2.next());
					message.append(" \n downloading " + i + "/" + numberOfBlocks); 
				}
				fos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			supervisor.getScrenSupervisor().append("Read File : " + pathFile.getText() + message + "\n");

		}
	}

}
