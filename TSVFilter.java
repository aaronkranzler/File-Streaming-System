/**
 * Constructed using the builder pattern
 * Takes a WhichFile object, which should hold all the information and specifications needed for the stream
 * Has a file name, a name of the field specified in select(), a value for the specificed field in select(), a field specified in compute,
 *  and a type for the Terminal value in compute()
 */
import java.util.ArrayList;

public class TSVFilter {
	public String fileName;
	public String selectFieldName;
	public String value;
	public String fieldForTerminal;
	public Terminal terminalType;
	/**
	 * constructor, certain elements are instantiated based on whether select() and/or compute() have been called in the passed WhichFile
	 * @param file
	 */
	public TSVFilter(WhichFile file)
	{
		fileName = file.fileName;
		if(file.specifications.size()!=0)
		{
			selectFieldName = file.specifications.get(0);
			value = file.specifications.get(1);
		}
		if(file.fieldForTerminal!=null)
		{
			fieldForTerminal = file.fieldForTerminal;
			terminalType = file.terminalType;
		}
	}
	/**
	 * class within filter (builder patter), holds specifications for the field name and value in select(), respectively and holds same thign for compute() specifications
	 * values are determined based on which methods are called
	 * @author aaronkranzler
	 *
	 */
	public static class WhichFile{
		public String fileName;
		public ArrayList<String> specifications;
		public Terminal terminalType;
		public String fieldForTerminal;
		/**
		 * constructor, takes in fileName and creates object
		 * @param name
		 */
		public WhichFile(String name)
		{
			fileName = name;
			specifications = new ArrayList<String>();
		}
		/**
		 * Takes in specifications for the field to be worked on and the calculation from Terminal
		 * @param fieldName
		 * @param type
		 * @return this
		 */
		public WhichFile compute(String fieldName, Terminal type)
		{
			fieldForTerminal = fieldName;
			terminalType = type;
			return this;
		}
		/*
		 * takes in specifications for the field and value to be included in the stream
		 */
		public WhichFile select(String fieldName, String value)
		{
			specifications.add(fieldName);
			specifications.add(value);
			return this;
		}
		/**
		 * instantiates with the current WhichFile and returns the TSVFilter object
		 * @return TSVFilter(this)
		 */
		public TSVFilter done()
		{
			return new TSVFilter(this);
		}
	}
	/**
	 * toString method for filter
	 * @return String
	 */
	public String toString()
	{
		return fileName + selectFieldName + value;
	}
	
}
