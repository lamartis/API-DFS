package isidis.dfs.team.api.dfs2.interfaces;

public interface RemoteIterator<E> extends org.apache.hadoop.fs.RemoteIterator<E> {
	
	public long getNumberOfBlocks();
	
}
