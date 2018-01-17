/**
 * class that executes each function possible within Terminal (aka the items specified in compute())
 * Does this with helper classes that hold values, specifically with InitialTerminalValues and TerminalValues
 * Holds the type of Terminal call specified and the field to which this call is to be performed on, holds an int for the place of the aforementioned field
 * Holds the first instance of the specified, be it a String or an int
 * Has an ArrayList holding all of the different values for the specified field that have been presented in the Stream so far
 */
import java.util.ArrayList;

public class TerminalExecuter {
	private Terminal type;
	private String field;
	private int placeOfField;
	private String firstItem;
	private int intFirstItem;
	private InitialTerminalValues firstItems;
	private TerminalValues items;
	private ArrayList<Integer> listOfItems;
	/**
	 * Constructor, just sets the terminal type and the specified field, sets placeOfField to -1, as it hasn't been computed yet
	 * @param type
	 * @param field
	 */
	public TerminalExecuter(Terminal type, String field)
	{
		this.type = type;
		this.field = field;
		placeOfField = -1;
		listOfItems = new ArrayList<Integer>();
	}
	/**
	 * finds the place of the specified field in the line
	 * @param line
	 */
	public void firstLine(String[] line)
	{
		for(int i = 0; i < line.length; i++)
		{
			if(line[i].equalsIgnoreCase(field))
			{
				placeOfField = i;
			}
		}
	}
	/**
	 * this determines which helper classes will be used based on whether the specified field is a String or a Long
	 * If long, both InitialTerminalValues and TerminalValues will be instantiated, as both are necessary for the numerical calculations
	 * If String, only TerminalValues will be instantiated, as the calculation ALLSAME will be performed, and InitialTerminalValues is not necessary for this
	 * After the proper instantiations, a call to performRestProperMethod is called and performs the proper function on the current line
	 * @param line
	 */
	public void performFirstProperMethod(String[] line) //performed on each line
	{
		firstItem = line[placeOfField];
		try {
			intFirstItem = Integer.parseInt(firstItem);
			firstItems = new InitialTerminalValues(intFirstItem);
			items = new TerminalValues(firstItems);
		} catch (Exception e){
			items = new TerminalValues(firstItem);
			}
		performRestProperMethod(line);
	}
	public Object getItem()
	{
		Object returnItem;
		if(getIntItem()==Integer.MIN_VALUE)
		{
			if(items.statLine.equals("If it equals this, statLine was not worked on") || items.statLine==null)
			{
				returnItem = items.sameOrNo;
			}
			else
			{
				returnItem = items.statLine;
			}
		}
		else
		{
			returnItem = getIntItem();
		}
		return returnItem;
	}
	private int getIntItem()
	{
		int returnItem = Integer.MIN_VALUE;
		switch(type)
		{
			case COUNT: returnItem = items.count;
			break;
			case MIN: returnItem = items.min;
			break;
			case MAX: returnItem = items.max;
			break;
			case SUM: returnItem = items.sum;
			break;
		}
		return returnItem;
	}
	/**
	 * Performs the proper method/calculation, depending on what the Terminal type is 
	 * This performs one part of the calculation, this needs to be called for each line that is streamed, and the calculations/methods over the entire
	 * stream will be done 
	 * @param line
	 */
	public void performRestProperMethod(String[] line)
	{
		switch(type)
		{
			case ALLSAME: performALLSAME(line);
			break;
			case COUNT: performCOUNT(line);
			break;
			case MIN: performMIN(line);
			break;
			case MAX: performMAX(line);
			break;
			case SUM: performSUM(line);
			break;
			case STATS: performSTATS(line);
		}
	}
	/**
	 * 2.4
	 * method that performs the functionality for the STATS Terminal calculation, uses helper classes called from TerminalValues
	 * @param line
	 */
	private void performSTATS(String[] line)
	{
		performCOUNT(line);
		performSUM(line);
		items.setStatLine(listOfItems);
		
	}
	/**
	 * method that performs the functionality for the ALLSAME Terminal method, holds the first item (in the case where this method is called, it's a String)
	 * as a basis for comparison, and checks if the specified value on the current line matches, if not, the value in TerminalValues is changed to false
	 * @param line
	 */
	private void performALLSAME(String[] line)
	{
		if(line[placeOfField].equals(firstItem)==false)
		{
			items.setSameOrNo(false);
		}
	}
	/**
	 * method that performs the functionality for the COUNT Terminal method, it simply adds to a count for each line, as it is called for each Streamed line
	 * It also adds the value, if a number, to the ArrayList listOfItems to be used  for calculations in the STATS method
	 * @param line
	 */
	private void performCOUNT(String[] line) 
	{
		items.incrementCount();
		int itemToAdd;
		try {
			itemToAdd = Integer.parseInt(line[placeOfField]);
			listOfItems.add(itemToAdd);
		}	catch(Exception e) {
		}
	}
	/**
	 * method performs the functionality for the MIN Terminal method, it takes the value, parses it to an int (try catches to ensure the specified value is 
	 * a number, and gives exception message if not), and runs it through the setMin() method in TerminalValues
	 * @param line
	 */
	private void performMIN(String[] line)
	{
		try {
			int item = Integer.parseInt(line[placeOfField]);
			items.setMin(item);
		} catch(Exception e){
			System.out.println("This isn't a number, program can't perform MIN on it");
		}
		
	}
	/**
	 * method performs the functionality for the MAX Terminal method, it takes the value, parses it to an int (try catches to ensure the specified value is 
	 * a number, and gives exception message if not), and runs it through the setMax() method in TerminalValues
	 * @param line
	 */
	private void performMAX(String[] line)
	{
		try {
			int item = Integer.parseInt(line[placeOfField]);
			items.setMax(item);
		} catch(Exception e){
			System.out.println("This isn't a number, program can't perform MAX on it");
		}
		
	}
	/**
	 * method performs the functionality for the SUM Terminal method, it takes the value, parses it to an int (try catches to ensure the specified value is 
	 * a number, and gives exception message if not), and runs it through the addToSum() method in TerminalValues
	 * @param line
	 */
	private void performSUM(String[] line)
	{
		try {
			int item = Integer.parseInt(line[placeOfField]);
			items.addToSum(item);
		} catch(Exception e){
			System.out.println("This isn't a number, program can't perform SUM on it");
		}
	}
}
