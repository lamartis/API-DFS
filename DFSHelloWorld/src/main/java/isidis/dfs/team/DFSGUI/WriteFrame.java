package isidis.dfs.team.DFSGUI;

import isidis.dfs.team.api.dfs.common.exceptions.EndpointNotReacheableException;
import isidis.dfs.team.api.dfs.common.exceptions.FileSizeThresholdNotRespected;
import isidis.dfs.team.api.dfs.common.exceptions.SystemUserPermissionException;
import isidis.dfs.team.tools.DFSProvider;

import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.hadoop.fs.FileAlreadyExistsException;

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

	private byte[] byteFile = null;

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

		if	(e.getSource() == buttonWrite)	{
			file = fc.getSelectedFile();
			if	(file != null){

				byteFile = new byte[(int) file.length()];

				try {
					//convert file into array of bytes
					FileInputStream fileInputStream=null;
					fileInputStream = new FileInputStream(file);
					fileInputStream.read(byteFile);
					fileInputStream.close();

					System.out.println("Done");
				}catch(Exception c){

				}

				String message = null;
				try {
					//System.out.println(read(file).length);
					DFSProvider.getInstance1().writeFile(byteFile, pathFile.getText());
					message = " with success !";
				} catch (SystemUserPermissionException e1) {
					message = " [System User Permission denied]";
				} catch (FileAlreadyExistsException e1) {
					message = " [File Already Exists Exception]";
				} catch (EndpointNotReacheableException e1) {
					message = " [Endpoint Not Reacheable]";
				} catch (FileSizeThresholdNotRespected e1) {
					message = " [File Size Exceeds Fixed Threshold]";
				} catch (URISyntaxException e1) {
					message = " [URI Syntax Exception]";
				} catch (IOException e4) {
					message = " [Lecture ERROR]";
				}
				supervisor.getScrenSupervisor().append("Write File  :  " + file.getName() + " to " + pathFile.getText() + " " + message +newline);
			}
		}
	}

	@Override
	public boolean action(Event evt, Object what) {
		// TODO Auto-generated method stub
		return super.action(evt, what);
	}
}
