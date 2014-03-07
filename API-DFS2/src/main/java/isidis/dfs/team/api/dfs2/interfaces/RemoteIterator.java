package isidis.dfs.team.api.dfs2.interfaces;

import isidis.dfs.team.api.dfs2.implementation.MyHdfsClient;

import org.apache.hadoop.hdfs.DFSClient;
import org.apache.log4j.Logger;

public abstract class RemoteIterator<E> implements org.apache.hadoop.fs.RemoteIterator<E> {
	protected String fileLocation = null;
	protected int position = 0;
	protected DFSClient client = MyHdfsClient.getInstance();
	protected static Logger logger = Logger.getLogger(RemoteIterator.class);
}
