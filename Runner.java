/**
 * NOTE: This system Streams to a new file, by writing to it one line at a time. The resulting Stream exists in the newly 
 * written file, whose name is NewfileName.tsv, where fileName.tsv was the original file that was streamed
 * TESTING: Testing Documentation, with screenshots and input and output .tsv files, are in the Testing Documentation folder
 * 4 tests were completed: one with neither select or compute, one with just select, one with just compute (also tested the 2.4 STATS Terminal option I added), and both select and compute
 * Each time uses a different input file with differing amounts of fields
 */
import java.io.IOException;

public class Runner {
	public static void main(String[] args) throws IOException
	{
		TSVFilter myTSVFilter = new TSVFilter
						.WhichFile("SelectAndComputeTest.tsv")
						.select("name", "aaron")
						.compute("age", Terminal.MAX)
						.done();
		TSVPipeline pipeLine = new TSVPipeline(myTSVFilter);
		System.out.println(pipeLine.doIt());
	}
}
