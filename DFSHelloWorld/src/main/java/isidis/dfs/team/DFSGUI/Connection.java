package isidis.dfs.team.DFSGUI;

import java.net.URISyntaxException;

import isidis.dfs.team.api.dfs.common.exceptions.EndpointNotReacheableException;
import isidis.dfs.team.tools.DFSProvider;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * @author dfs-one
 *
 */
public class Connection {

	public static void main(String[] args) {
		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Turn off metal's use of bold fonts

				UIManager.put("swing.boldMetal", Boolean.FALSE);
				try{
					DFSProvider.getInstance2();
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (EndpointNotReacheableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				new MainFrame().createAndShowGUI();
			}
		});
	}


}
