
/**
 * representation of the current file being Streamed, only holds one line at a time in storage (currentData)
 * Holds the name of file and the order of Strings and Longs in which each line should follow
 * @author aaronkranzler
 *
 */
public class TSVFile {
	private String currentData;
	private String name; 
	private String fieldOrder;
	private int numberOfFields;
	/**
	 * Constructor for instantiation with both a line of text and a fileName
	 * @param data
	 * @param name
	 */
	public TSVFile(String data, String name)
	{
		this.currentData = data;
		this.name = name;
	}
	/**
	 * constructor for instantiation with only the fileName
	 * @param name
	 */
	public TSVFile(String name)
	{
		this.name = name;
		currentData = new String(); 
	}
	/**
	 * sets the amount of fields each line should properly have
	 * @param num
	 */
	public void setNumberOfFields(int num)
	{
		numberOfFields = num;
	}
	/**
	 * accessor for current line being worked on
	 * @return
	 */
	public String getData()
	{
		return currentData;
	}
	/**
	 * sets the proper order of Strings and Longs in which each line should follow
	 * @param order
	 */
	public void setFieldOrder(String order)
	{
		fieldOrder = order;
	}
	/**
	 * accessor for proper order
	 * @return fieldOrder
	 */
	public String getFieldOrder()
	{
		return fieldOrder;
	}
	/**
	 * accessor for number of fields
	 * @return numberOfFields
	 */
	public int getNumberOfFields()
	{
		return numberOfFields;
	}
	/**
	 * accessor for file name
	 * @return name
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * sets the current line to a new line
	 * @param newLine
	 */
	public void changeLine(String newLine)
	{
		currentData = newLine;
	}
}
