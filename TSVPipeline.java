/** 
 * Takes in the TSVFilter and calls and performs the reading and writing functionality for the stream
 * Has filter object, reader and writer, and an int to represent the place of field that is being worked on, if such a field is speicified
 * Also has an instance of TerminalExecuter, which helps perform the terminal calculations on the specified fields 
 * @author AaronKranzler - amk2308
 */
import java.io.*;

public class TSVPipeline {
	private TSVFilter filter;
	private StreamReader reader;
	private StreamWriter writer;
	private int placeOfField;
	private TerminalExecuter terminal;
	/**
	 * constructs TSVPipeline with an instance of TSVFilter, which effectively instantiates the reader, writer, and TerminalExecutor with the proper values 
	 * @param filter
	 * @throws IOException
	 */
	public TSVPipeline(TSVFilter filter) throws IOException
	{
		this.filter = filter;
		reader = new StreamReader(filter.fileName);
		writer = new StreamWriter(reader.getFile());
		placeOfField = -1;
		terminal = new TerminalExecuter(filter.terminalType, filter.fieldForTerminal);
	}
	/**
	 * NOTE: ONLY CALLED IN CASE WHERE SELECT() WAS CALLED
	 * Reads first line by calling proper method in StreamReader, writes line, gets array representation of the read line, and iterates through it to find the place of the specified field,
	 * if it exists. If no specified field, placeOfField is left at -1.
	 * returns the same array representation of the line
	 * @return line
	 * @throws IOException
	 */
	private String[] firstLine() throws IOException
	{
		String[] line = reader.readFirstLine();
		writer.writeLine(reader.getFile().getData());
		for(int i = 0; i < line.length; i++)
		{
			if(line[i].equals(filter.selectFieldName))
			{
				placeOfField = i;
			}
		}
		return line;
	}
	/**
	 * NOTE: ONLY CALLED IN CASE WHERE SELECT() WAS CALLED
	 * Reads second line by calling proper method in StreamReader, gets array representation of line, and iterates through it to see if the field in the line matches the specified
	 * field in selectand if either the item in the line matched the specified value in select
	 * the line gets written to the stream
	 * Returns array representation of read line
	 * @return line
	 * @throws IOException
	 */
	private String[] secondLine() throws IOException
	{
		String[] line = reader.readSecondLine();
		if(line[placeOfField].equals(filter.value))
		{
			writer.writeLine(reader.getFile().getData());
		}
		return line;
	}
	/**
	 * NOTE: ONLY CALLED IN CASE WHERE SELECT() WAS CALLED
	 * takes array Representation of next line (if that line exists), checks if the value at the proper spot of the field equals the specified value in select()
	 * if it matches, line gets written to stream
	 * returns the String array representation of the line
	 * @return line
	 * @param line
	 */
	private String[] nextLine(String[] line) throws IOException 
	{
		if(line!=null)
		{
			if(line[placeOfField].equals(filter.value))
			{
				writer.writeLine(reader.getFile().getData());
			}
		}
		return line;
	}
	/**
	 * private method called in doItWithAllSpecifications()
	 * tells if the currentLine has been selected for and streamed
	 * @param line
	 * @return sameOrNo
	 */
	private boolean wasThisLineStreamed(String[] line)
	{
		boolean sameOrNo = false;
		String[] testLine = writer.getLastWrittenLine().split("\t");
		for(int i = 0; i < testLine.length; i++)
		{
			if(testLine[i].equals(line[i]))
			{
				sameOrNo = true;
			}
		}
		return sameOrNo;
	}
	/**
	 * method that performs the reading and writing functionality in the case where both select() and compute() were called
	 * checks if the current line was selected for, and if so, it calls the terminal function, as the terminal functions are only
	 * called on lines that were selected
	 * @throws IOException
	 */
	private void doItWithAllSpecifications() throws IOException
	{
		boolean wasTerminalFirstProperPerformed = false;
		String[] lineOne = firstLine();
		terminal.firstLine(lineOne);
		String[] lineTwo = secondLine();
		if(wasThisLineStreamed(lineTwo)==true)
		{
			terminal.performFirstProperMethod(lineTwo);
			wasTerminalFirstProperPerformed = true;
		}
		String[] line = reader.readNextLine();
		while(line!=null)
		{
			nextLine(line);
			if(wasThisLineStreamed(line)==true && wasTerminalFirstProperPerformed==true)
			{
				terminal.performRestProperMethod(line);
			}
			else if(wasThisLineStreamed(line)==true && wasTerminalFirstProperPerformed==false)
			{
				terminal.performFirstProperMethod(line);
				wasTerminalFirstProperPerformed = true;
			}
			line = reader.readNextLine();
		}
		reader.closeBR();
		writer.closeWriter();
	}
	/**
	 * method that performs the reading and writing functionality in the case where only compute() was called
	 * @throws IOException
	 */
	private void doItWithTerminal() throws IOException
	{
		String[] lineOne = reader.readFirstLine();
		writer.writeLine(reader.getFile().getData());
		terminal.firstLine(lineOne);
		String[] lineTwo = reader.readSecondLine();
		writer.writeLine(reader.getFile().getData());
		terminal.performFirstProperMethod(lineTwo);
		String[] thing = reader.readNextLine();
		while(thing!=null)
		{
			writer.writeLine(reader.getFile().getData());
			terminal.performRestProperMethod(thing);
			thing = reader.readNextLine();
		}
		reader.closeBR();
		writer.closeWriter();
	}
	/**
	 * method that performs the reading and writing functionality in the case where only select() was called
	 * @throws IOException
	 */
	private void doItWithSelect() throws IOException
	{
		firstLine();
		secondLine();
		String[] line = reader.readNextLine();
		while(line!=null)
		{
			nextLine(line);
			line = reader.readNextLine();
		}
		reader.closeBR();
		writer.closeWriter();
	}
	/**
	 * method that performs the reading and writing functionality in the case where neither select() and compute() were called
	 * @throws IOException
	 */
	private void doItWithoutSpecifications() throws IOException 
	{
		reader.readFirstLine();
		writer.writeLine(reader.getFile().getData());
		reader.readSecondLine();
		writer.writeLine(reader.getFile().getData());
		String[] thing = reader.readNextLine();
		while(thing!=null)
		{
			writer.writeLine(reader.getFile().getData());
			thing = reader.readNextLine();
		}
		reader.closeBR();
		writer.closeWriter();
	}
	/**
	 * method that determines whether select() and compute() were called or not and in what combination, and calls the proper method to perform the functionality
	 * returns results of compute(), if it was called
	 * @return returnItem
	 * @throws IOException
	 */
	public Object doIt() throws IOException
	{
		Object returnItem = null;
		if(filter.selectFieldName==null && filter.terminalType==null)
		{
			doItWithoutSpecifications();
		}
		else if(filter.selectFieldName!=null && filter.terminalType==null)
		{
			doItWithSelect();
		}
		else if(filter.selectFieldName==null && filter.terminalType!=null)
		{
			doItWithTerminal();
			returnItem = terminal.getItem();
		}
		else
		{
			doItWithAllSpecifications();
			returnItem = terminal.getItem();
		}
		return returnItem;
	}
}

