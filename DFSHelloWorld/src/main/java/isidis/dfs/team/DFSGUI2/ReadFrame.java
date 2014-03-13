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
		String message = "";
		area.setText("");
		if	(e.getSource() == buttonRead) {
			try {
				remoteIterator2 = DFSProvider.getInstance2().readFile(pathFile.getText());
				supervisor.getScrenSupervisor().setText(supervisor.getScrenSupervisor().getText() + "\n" + "Starting downloading file " + pathFile.getText() );
				
				new Thread(new MyThread()).start();
				
			} catch (SystemUserPermissionException e1) {
				message = ("reading " + pathFile.getText() + " [system user permission Exception]");
			} catch (FileNotFoundException e1) {
				message = ("reading " + pathFile.getText() + " [File Not Found]");
			} catch (EndpointNotReacheableException e1) {
				message = ("reading " + pathFile.getText() + " [Endpoint not reacheable Exception]");
			} catch (FileSizeThresholdNotRespected e1) {
				message = ("reading " + pathFile.getText() + " [File Size Threshold Not Respected]");
			} catch (URISyntaxException e1) {
				message = ("reading " + pathFile.getText() +  " [URI Syntax Exception]");
			}
			supervisor.getScrenSupervisor().setText(supervisor.getScrenSupervisor().getText() + "\n" + message );
			
			
		}
	}
	
	public class MyThread implements Runnable{

		String m = null;
				
		public MyThread() {
		}
		
		@Override
		public void run() {
			int i = 1;

			long numberOfBlocks = remoteIterator2.getNumberOfBlocks();
			
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(new File(pathFile.getText().split("/")[pathFile.getText().split("/").length - 1]));
			} catch (java.io.FileNotFoundException e1) {
				e1.printStackTrace();
			}
			
			try {
				while (remoteIterator2.hasNext()) {
					String m = " \n downloading " + i++ + "/" + numberOfBlocks;
					supervisor.getScrenSupervisor().setText(supervisor.getScrenSupervisor().getText() + m);
					fos.write(remoteIterator2.next());
				}
				fos.close();

				String message = ("reading " + pathFile.getText() + " with success !");
				supervisor.getScrenSupervisor().setText(supervisor.getScrenSupervisor().getText() + "\n" + message );
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
		
	}

}


