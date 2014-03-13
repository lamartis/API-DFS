package isidis.dfs.team.api2.dfs.interfaces;

/**
 * This interface is used in order to add newest method to the RemoteIterator's Hadoop interface.
 * @author saad
 *
 * @param <E>
 */
public interface RemoteIterator<E> extends org.apache.hadoop.fs.RemoteIterator<E> {
	/**
	 * The goal of this method is to return the number of blocks, that can help us to calculate download progress for example.
	 * @return
	 * Value returned refers to the number of blocks
	 */
	public long getNumberOfBlocks();
	
}
