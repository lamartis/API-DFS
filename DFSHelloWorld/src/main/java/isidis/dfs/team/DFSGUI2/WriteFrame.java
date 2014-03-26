package isidis.dfs.team.DFSGUI2;

import isidis.dfs.team.api.dfs.common.exceptions.EndpointNotReacheableException;
import isidis.dfs.team.api.dfs.common.exceptions.FileSizeThresholdNotRespected;
import isidis.dfs.team.api.dfs.common.exceptions.SystemUserPermissionException;
import isidis.dfs.team.api2.dfs.interfaces.RemoteIterator;
import isidis.dfs.team.tools.DFSProvider;

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
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.hadoop.fs.FileAlreadyExistsException;

public class WriteFrame extends JComponent implements ActionListener  {

	RemoteIterator<Void> remoteIterator = null;
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
	StringBuffer message = new StringBuffer();
	
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
		String message = "";
		if	(e.getSource() == buttonWrite)	{
			int returnVal = fc.showOpenDialog(this);
			file = fc.getSelectedFile();
			if	(file != null){

				System.out.println(file.getAbsolutePath());
				try {
					remoteIterator = DFSProvider.getInstance2().writeFile(new File(file.getAbsolutePath()), 0, pathFile.getText());
					message = " with success !";
					new Thread(new MyThread()).start();
				} catch (SystemUserPermissionException e1) {
					message = "writing  [System User Permission denied]";
				} catch (FileAlreadyExistsException e1) {
					message = "writing  [File Already Exists Exception]";
				} catch (FileSizeThresholdNotRespected e1) {
					message = "writing  [File Size Exceeds Fixed Threshold]";
				} catch (URISyntaxException e1) {
					message = "writing  [URI Syntax Exception]";
				} catch (EndpointNotReacheableException e1) {
					message = "writing  [Endpoint Not Reacheable]";
				}
				supervisor.add(message);
				
			}
		}
	}
	
	public class MyThread implements Runnable{

		String m = null;
				
		public MyThread() {
		}
		
		@Override
		public void run() {
			int i = 1;

			long numberOfBlocks = remoteIterator.getNumberOfBlocks();
			
			try {
				while (remoteIterator.hasNext()) {
					String m = " Writing " + i++ + "/" + numberOfBlocks;
					supervisor.add( m  );
					remoteIterator.next();
				}
			} catch (IOException e1) {
				message.append(" [IOException Exception]");
			}
			
		}
		
	}
	
}
