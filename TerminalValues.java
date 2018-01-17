/**
 * Class that holds all the values for all the terminal functions
 * Performs actual calculations on values
 * all values are public for easy access from TerminalExecuter
 * @author - AaronKranzler - amk2308
 */
import java.util.ArrayList;

public class TerminalValues {
	public static boolean sameOrNo;//initialize to true
	public static int firstNum;
	public static String firstWord;
	public static int count;
	public static int min;
	public static int max;
	public static int sum;
	public static String statLine;
	private StatHelper helper;
	/**
	 * Constructor that takes the initial values for each computation 
	 * This constructor is used for the case where any Terminal call except for ALLSAME is called
	 * @param start
	 */
	public TerminalValues(InitialTerminalValues start)//case where calls on numbers happen
	{
		sameOrNo=true;
		firstNum = start.firstItem;
		count = 0;
		min = start.firstMin;
		max = start.firstMax;
		sum = start.firstSum;
		statLine = "If it equals this, statLine was not worked on";
		helper = new StatHelper(sum, count);
	}
	/**
	 * Constructor that takes the initial value for the computations
	 * This constructor is only used when the Terminal type is ALLSAME
	 * @param startingWord
	 */
	public TerminalValues(String startingWord)//case where calculate command is on an actual word, where ALLSAME is called
	{
		sameOrNo = true; 
		firstWord = startingWord;
		statLine = "If it equals this, statLine was not worked on";
		
	}
	/**
	 * Method called to set statLine to proper value, calls method to perform STATS calculations
	 * Calls on StatHelper to perform calculations for average and standard deviation
	 * @param listOfNumbers
	 */
	public void setStatLine(ArrayList<Integer> listOfNumbers)
	{
		helper.setCountAndSum(count, sum);
		helper.setList(listOfNumbers);
		helper.calculate();
		statLine = "Count: " + count + ", Sum: " + sum + ", Average: " + helper.getAverage() + ", Standard Deviation: " + helper.getStandardDeviation();
	}
	/**
	 * setter for sameOrNo
	 * @param trueOrFalse
	 */
	public void setSameOrNo(boolean trueOrFalse)
	{
		sameOrNo = trueOrFalse;
	}
	/**
	 * increases count, called for every line in stream
	 */
	public void incrementCount()
	{
		count++;
	}
	/**
	 * setter for min, compares given value to current min
	 * @param comparedItem
	 */
	public void setMin(int comparedItem)
	{
		if(comparedItem < min)
		{
			min = comparedItem;
		}
	}
	/**
	 * setter max, compares given value to current max
	 * @param comparedItem
	 */
	public void setMax(int comparedItem)
	{
		if(comparedItem > max)
		{
			max = comparedItem;
		}
	}
	/**
	 * adds current item value to sum
	 * @param itemToAdd
	 */
	public void addToSum(int itemToAdd)
	{
		sum+=itemToAdd;
	}
}
