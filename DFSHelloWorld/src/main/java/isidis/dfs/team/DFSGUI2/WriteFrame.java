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
			int returnVal = fc.showOpenDialog(this);
			file = fc.getSelectedFile();
			if	(file != null){
				
				StringBuffer message = new StringBuffer();
				try {
					remoteIterator = DFSProvider.getInstance2().writeFile(new File(file.getAbsolutePath()), pathFile.getText());
					message.append(" with success !");
				} catch (SystemUserPermissionException e1) {
					message.append("  [System User Permission denied]");
				} catch (FileAlreadyExistsException e1) {
					message.append("  [File Already Exists Exception]");
				} catch (EndpointNotReacheableException e1) {
					message.append("  [Endpoint Not Reacheable]");
				} catch (FileSizeThresholdNotRespected e1) {
					message.append("  [File Size Exceeds Fixed Threshold]");
				} catch (URISyntaxException e1) {
					message.append("  [URI Syntax Exception]");
				}
				
				long numberOfBlocks = remoteIterator.getNumberOfBlocks();
				int i = 0;
				try {
					while (remoteIterator.hasNext()) {
						remoteIterator.next();
						message.append("\n downloading " + i + "/" + numberOfBlocks); 
					}
				} catch (IOException e1) {
					message.append(" [IOException Exception]");
				}
				supervisor.getScrenSupervisor().append("Write File  :  " + file.getName() + " to " + pathFile.getText() + " " + message +newline);
			}
		}
	}
}
