/**
 * Class to do calculations for STATS, classed is instantiated each time 
 * setStatLine() is called in TerminalValues, so STATS can be done at any point in the Stream
 * @author - AaronKranzler - amk2308
 */
import java.util.ArrayList;

public class StatHelper {
	private ArrayList<Integer> allNumbers;
	private double average;
	private double standardDeviation;
	private int sum;
	private int count;
	/**
	 * instantiated with an ArrayList of all the values so far, given that they are numbers, the current sum, and the current count
	 * @param allNumbers
	 * @param sum
	 * @param count
	 */
	public StatHelper(int sum, int count)
	{
		this.allNumbers = new ArrayList<Integer>();
		this.sum = sum;
		this.count = count;
	}
	/**
	 * setter for count and sum
	 * @param count
	 * @param sum
	 */
	public void setCountAndSum(int count, int sum)
	{
		this.count = count;
		this.sum = sum;
	}
	/**
	 * method to set ArrayList to current list in Stream
	 * @param list
	 */
	public void setList(ArrayList<Integer> list)
	{
		allNumbers = list;
	}
	/**
	 * calculates average based on given sum and count
	 */
	private void calculateAverage()
	{
		average = ((double)(sum))/((double)(count));
	}
	/**
	 * calculates Standard deviation based on current average and count and sum
	 */
	private void calculateStandardDeviation()
	{
		double sumOfPointStandardDevs = 0.0;
		for(Integer i : allNumbers)
		{
			sumOfPointStandardDevs += Math.sqrt(Math.abs(average - (double)(i.intValue())));
		}
		standardDeviation = Math.sqrt((sumOfPointStandardDevs)/((double)(count)));
	}
	/**
	 * calls the calculating functions, method called in TerminalValues
	 */
	public void calculate()
	{
		calculateAverage();
		calculateStandardDeviation();
	}
	/**
	 * accessor for average
	 * @return
	 */
	public double getAverage()
	{
		return average;
	}
	/**
	 * accessor for Standard Deviation
	 * @return
	 */
	public double getStandardDeviation()
	{
		return standardDeviation;
	}
}