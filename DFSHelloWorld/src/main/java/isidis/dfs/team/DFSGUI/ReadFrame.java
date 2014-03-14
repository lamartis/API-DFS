package isidis.dfs.team.DFSGUI;

import isidis.dfs.team.api.dfs.common.exceptions.EndpointNotReacheableException;
import isidis.dfs.team.api.dfs.common.exceptions.FileNotFoundException;
import isidis.dfs.team.api.dfs.common.exceptions.FileSizeThresholdNotRespected;
import isidis.dfs.team.api.dfs.common.exceptions.SystemUserPermissionException;
import isidis.dfs.team.tools.DFSProvider;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ReadFrame implements ActionListener {

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
		String message = null;
		area.setText("");
		if	(e.getSource() == buttonRead) {
			try {
				System.out.println("reading " + pathFile.getText());
				System.out.println(DFSProvider.getInstance1().readFile(pathFile.getText()).length);
				message = " with success !";
			} catch (SystemUserPermissionException e1) {
				message = " [system user permission Exception]";
			} catch (FileNotFoundException e1) {
				message = " [File Not Found]";
			} catch (EndpointNotReacheableException e1) {
				message = " [Endpoint not reacheable Exception]";
			} catch (FileSizeThresholdNotRespected e1) {
				message = " [File Size Exceeds Fixed Threshold]";
			} catch (URISyntaxException e1) {
				message = " [URI Syntax Exception]";
			}
			supervisor.getScrenSupervisor().append("Read File : " + pathFile.getText() + message + "\n");

		}
	}

}
