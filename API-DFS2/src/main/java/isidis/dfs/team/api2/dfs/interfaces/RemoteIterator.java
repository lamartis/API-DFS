package isidis.dfs.team.api2.dfs.interfaces;

public interface RemoteIterator<E> extends org.apache.hadoop.fs.RemoteIterator<E> {
	/**
	 * The goal of this method is to know the number of blocks, that can help us to calculate download progress for example.
	 * @return
	 */
	public long getNumberOfBlocks();
	
}
