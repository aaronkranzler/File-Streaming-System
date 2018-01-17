/**
 * class that holds initial values for fields
 * @author aaronkranzler
 *
 */
public class InitialTerminalValues {
	public int firstItem;
	public int firstMin;
	public int firstMax;
	public int firstSum;
	/**
	 * constructor with first item, given that it is an int
	 * @param item
	 */
	public InitialTerminalValues(int item)
	{
		firstItem = item;
		firstMin = item;
		firstMax = item;
		firstSum = item;
	}
}
