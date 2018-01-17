/**
 * This class reads the given and outputs each line of text to be stored, viewed, worked on, and written to a New .tsv file (only one at a time)
 * When reading, it checks the amount of items in the first line, and then it checks the type of each item in the second line
 * A general form of the proper file line (a series of Longs and Strings) is denoted and stored in the TSVFile class, all subsequent lines that 
 * do not follow this form are realized in this class (with a helper class) and not stored to be written to the new file
 * This class has a TSVFile, which represents the file being streamed, but only holds one line of text in storage at a time
 * nameOfStream is simply the .tsv file name
 * BufferedReader and FileReader are part of Java's file-reading methods in the API 
 */

import java.io.*;

public class StreamReader {
	private TSVFile currentStream;
	private String nameOfStream;
	private BufferedReader br;
	private FileReader fr;
	/**
	 * Constructor, try-catches to ensure file exists
	 * @param name
	 */
	public StreamReader(String name)
	{
		nameOfStream = name;
		currentStream = new TSVFile(name);
		try {
			fr = new FileReader(nameOfStream);
			br = new BufferedReader(fr);

		} catch (IOException e) {
			System.out.println("That file doesn't exist, program won't run");
			e.printStackTrace();

		}
	}
	/**
	 * Accessor for representation of current file, note that the representation only holds in storage the current line that is beign worked on
	 * @return currentStream
	 */
	public TSVFile getFile()
	{
		return currentStream;
	}
	/**
	 * Reads first line, returns the line as an array of Strings, which are split by tabs, sets the line as the current line being worked on, storing it as 
	 * the main line in the TSVFile
	 * Also sets number of fields in TSVFile
	 * @return items
	 */
	public String[] readFirstLine()
	{
		String firstLine = "";
		try {
			firstLine = br.readLine();
		} catch (IOException e) {
			System.out.println("No first line!");
			e.printStackTrace();
		}
		String[] items = firstLine.split("\t");
		currentStream.setNumberOfFields(items.length);
		currentStream.changeLine(firstLine);
		return items;
	}
	/**
	 * Reads second line, sets stored line to the current line in TSVFile
	 * Splits line by tabs into String array. The array, named items, is iterated through and checked for the types of items, which are stored in an order 
	 * of Strings and Longs, denoted by "S" and "L" respectively. This order is stored in the TSVFile as a fieldOrder, all subsequent lines must obey this order
	 * @return items
	 */
	public String[] readSecondLine()
	{
		String secondLine = "";
		try {
			secondLine = br.readLine();
		} catch (IOException e1) {
			System.out.println("No second line!");
			e1.printStackTrace();
		}
		currentStream.changeLine(secondLine);
		String[] items = secondLine.split("\t");
		String lineForm = "";
		for(int i = 0; i < items.length; i++)
		{
			try {
				Integer.parseInt(items[i]);
			    lineForm += "L";
			} catch (Exception e){
				lineForm += "S"; 
				}
		}
		currentStream.setFieldOrder(lineForm);
		return items;
	}
	/**
	 * reads all subsequent lines, checks to insure that lines follow the proper order and type of fields as defined by the first two lines with the use of a helper class (LineChecker)
	 * If it matches the proper criteria, it gets set to the current line in the TSVFile and gets written to the stream.
	 * Returns String array representation of the line, split by tabs
	 * @return items
	 * @throws IOException
	 */
	public String[] readNextLine() throws IOException
	{
		String[] items = null;
		String nextLine;
		nextLine = br.readLine();
		LineChecker checker = new LineChecker(currentStream.getFieldOrder());
		if(nextLine!=null)
		{
			items = nextLine.split("\t");
			if(checker.checkLine(items)==true)
			{
				currentStream.changeLine(nextLine);
			}
		}
		return items;
	}
	/**
	 * closes BufferedReader after the entire stream has been read
	 * @throws IOException
	 */
	public void closeBR() throws IOException
	{
		br.close();
	}
}
