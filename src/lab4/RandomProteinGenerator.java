package lab4;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
public class RandomProteinGenerator {
/* constructor 
 *  
 *  if useUniformFrequencies == true, the random proteins have an equal probability of all 20 residues.
 *  
 *  if useUniformFrequencies == false, the 20 residues defined by
 *  { 'A', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'Y' }
 *  
 *  have a distribution of 
 *  
 *  {0.072658f, 0.024692f, 0.050007f, 0.061087f,
    0.041774f, 0.071589f, 0.023392f, 0.052691f, 0.063923f,
    0.089093f, 0.023150f, 0.042931f, 0.052228f, 0.039871f,
    0.052012f, 0.073087f, 0.055606f, 0.063321f, 0.012720f,
    0.032955f}
 * 
 */
public final boolean useUniformFrequencies;
public RandomProteinGenerator(boolean useUniformFrequencies)
{
	this.useUniformFrequencies = useUniformFrequencies;
}
public boolean getUseUniformFrequencies()
{
	return this.useUniformFrequencies;
}

/*
 * Returns a randomly generated protein of length length.
 */
public String getRandomProtein(int length)
{
	String[] residues = { "A","R", "N", "D", "C", "Q", "E", "G",  "H", "I", "L", "K", "M", "F", "P", "S", "T", "W", "Y", "V" };
	StringBuffer buff = new StringBuffer();
	for (int x=0; x < length; x++)
	{
		Random random = new Random();
		int position = random.nextInt(20);
		buff.append(residues[position]);
	}
	String s = buff.toString();
	return s;
}

/*
 * Returns the probability of seeing the given sequence
 * given the underlying residue frequencies represented by
 * this class.  For example, if useUniformFrequencies==false in 
 * constructor, the probability of "AC" would be 0.072658 *  0.024692
 */
public double getExpectedFrequency(String protSeq)
{
	float freq = 1;
	if (useUniformFrequencies == true)
	{
		freq = (float) Math.pow(0.05, protSeq.length());
	}
	if (useUniformFrequencies == false)
	{
		String residues = "ARNDCQEGHILKMFPSTWYV";
		List<Float> distribution = new ArrayList <Float>(){{ add(0.072658f); add(0.024692f); add(0.050007f); add(0.061087f);
		   add(0.041774f); add(0.071589f); add(0.023392f); add(0.052691f); add(0.063923f); add(0.089093f); add(0.023150f); add(0.042931f); add(0.052228f); add(0.039871f);
		   add(0.052012f); add(0.073087f); add(0.055606f); add(0.063321f); add(0.012720f); }};
		
		for(int x=0; x < protSeq.length(); x++)
		{
			int position = residues.indexOf(protSeq.charAt(x));
			freq = freq * distribution.get(position);
		}
	}
	return freq;
}

/*
* calls getRandomProtein() numIterations times generating a protein with length equal to protSeq.length().
 * Returns the number of time protSeq was observed / numIterations
 */
public double getFrequencyFromSimulation( String protSeq, int numIterations )
{
	String[] residues = { "A","R", "N", "D", "C", "Q", "E", "G",  "H", "I", "L", "K", "M", "F", "P", "S", "T", "W", "Y", "V" };
	int observed = 0;
	for (int i=0; i < numIterations; i++)
	{
		String s ="";
		StringBuffer buff = new StringBuffer();
		for (int j=0; j < protSeq.length(); j++)
		{
			
			Random random = new Random();
			int position = random.nextInt(20);
			buff.append(residues[position]);
			s = buff.toString();

		}
		//System.out.println(s + "\t" + protSeq);
		if (s.equals(protSeq))
		{
			observed = observed + 1;
		}
			
		
	}
	double result = (double) observed/numIterations;
	return result;
}

public static void main(String[] args) throws Exception
{
	RandomProteinGenerator uniformGen = new RandomProteinGenerator(true);
	String testProtein = uniformGen.getRandomProtein(5);
	System.out.println(testProtein);
	String testProtein2 = "ACD";
	int numIterations =  100000000;
	System.out.println(uniformGen.getExpectedFrequency(testProtein2));  // should be 0.05^3 = 0.000125
	System.out.println(uniformGen.getFrequencyFromSimulation(testProtein2,numIterations));  // should be close

	RandomProteinGenerator realisticGen = new RandomProteinGenerator(false);
	
	// should be 0.072658 *  0.024692 * 0.050007 == 8.97161E-05
	System.out.println(realisticGen.getExpectedFrequency(testProtein));  
	System.out.println(realisticGen.getFrequencyFromSimulation(testProtein,numIterations));  // should be close
	
	
	
}
}
