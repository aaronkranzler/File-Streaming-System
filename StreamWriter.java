/**
 * class to write current line in TSVFile to stream
 */
import java.io.*;

public class StreamWriter {
	private TSVFile outputStream;
	private FileWriter writer;
	private BufferedWriter otherWriter;
	private String lastWrittenLine;
	/**
	 * constructor, takes TSVFile as parameter
	 * @param outputStream
	 * @throws IOException
	 */
	public StreamWriter(TSVFile outputStream) throws IOException
	{
		this.outputStream = outputStream;
		writer = new FileWriter("New" + outputStream.getName());
		otherWriter = new BufferedWriter(writer);
		lastWrittenLine = "";
	}
	/**
	 * accessor of lastWrittenLine, used in TSVPipeline method wasThisMethodStreamed()
	 * @return lastWrittenLine
	 */
	public String getLastWrittenLine()
	{
		return lastWrittenLine;
	}
	/**
	 * method to write inputted line, which is usually the current line in TSVFile
	 * @param line
	 * @throws IOException
	 */
	public void writeLine(String line) throws IOException
	{
		otherWriter.write(line + "\n");
		lastWrittenLine = line;
	}
	/**
	 * method to close writer
	 */
	public void closeWriter()
	{
		try {
			otherWriter.close();
		} catch (IOException e) {
		    
		}
	}

}
