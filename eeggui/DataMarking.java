/*
 * Iowa State University 
 * 07/11/16
 * Andrew Zaman, Steve Heinisch, Zoe Kendall, Samantha Morris
 * 
 * Data structure that reads one or more .exp files containing finger position data, marks maximums and 
 * minimums before computing amplitudes and IMI's for the data. It prints the patient name followed
 * by the wet and dry amplitudes in two columns, then the wet and dry IMI in two columns. It also 
 * calculates and prints summary statistics (mean and standard deviation) for each patient at the end.
 */
package eeggui;

import static java.lang.Math.abs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class DataMarking {

	private ArrayList<Double> frames;
	private ArrayList<Double> values;

	private ArrayList<Double> wetAmpl;
	private ArrayList<Double> wetIMI;
	
	private ArrayList<Double> dryAmpl;
	private ArrayList<Double> dryIMI;
	
	// Class Constructor: initializes new ArrayLists for each instance.
	DataMarking() {
		frames = new ArrayList<Double>();
		values = new ArrayList<Double>();
		wetAmpl = new ArrayList<Double>();
		wetIMI = new ArrayList<Double>();
		dryAmpl = new ArrayList<Double>();
		dryIMI = new ArrayList<Double>();
	}

	// markData is the method accessible to other classes, which is
	// used as the initial call to run computations.
	public void markData( String[] files ) {
		for (String file : files) {
			if (file.toLowerCase().contains("cog")) {
				readFile(file, "dry");

			}
			else {
				readFile(file, "wet");
			}
			frames.clear();
			values.clear();
		}
		
		// determine name of participant
		String name = files[0].substring(files[0].lastIndexOf("\\")+1);
		name = name.substring(0, name.indexOf("M"));

		/*ArrayList<Double> amplDiffs = new ArrayList<Double>();
		ArrayList<Double> imiDiffs = new ArrayList<Double>();
		
		
		for (int i = 0; i < wetAmpl.size(); i++)
			amplDiffs.add(dryAmpl.get(i) - wetAmpl.get(i));
		for (int j = 0; j < wetIMI.size(); j++)
			imiDiffs.add(dryIMI.get(j) - wetIMI.get(j));*/
		
		System.out.println(name.toUpperCase()+"\nAMPLITUDES:");
		if (wetAmpl.size() == dryAmpl.size()) {
			for (int i = 0; i < wetAmpl.size(); i++)
				System.out.println(wetAmpl.get(i) + "\t" + dryAmpl.get(i));
		} else if (wetAmpl.size() > dryAmpl.size()){
			for (int j = 0; j < wetAmpl.size(); j++) {
				try {
					System.out.println(wetAmpl.get(j) + "\t" + dryAmpl.get(j));
				} catch (IndexOutOfBoundsException ie) {
					System.out.println(wetAmpl.get(j) + "\t\t");
				}
			}
		} else if (dryAmpl.size() > wetAmpl.size()) {
			for (int k = 0; k < dryAmpl.size(); k++) {
				try {
					System.out.println(wetAmpl.get(k) + "\t" + dryAmpl.get(k));
				} catch (IndexOutOfBoundsException ie) {
					System.out.println("\t" + dryAmpl.get(k));
				}
			}
		}
		System.out.println("<<<============================>>>\nIMI:");
		if (wetIMI.size() == dryIMI.size()) {
			for (int i = 0; i < wetIMI.size(); i++)
				System.out.println(wetIMI.get(i) + "\t" + dryIMI.get(i));
		} else if (wetIMI.size() > dryIMI.size()){
			for (int j = 0; j < wetIMI.size(); j++) {
				try {
					System.out.println(wetIMI.get(j) + "\t" + dryIMI.get(j));
				} catch (IndexOutOfBoundsException ie) {
					System.out.println(wetIMI.get(j) + "\t\t");
				}
			}
		} else if (dryIMI.size() > wetIMI.size()) {
			for (int k = 0; k < dryIMI.size(); k++) {
				try {
					System.out.println(wetIMI.get(k) + "\t" + dryIMI.get(k));
				} catch (IndexOutOfBoundsException ie) {
					System.out.println("\t" + dryIMI.get(k));
				}
			}
		}
		System.out.println("<<<============================>>>\nSummary Statistics:");
		
		// find average amplitude and imi
		double meanWetAmpl = average(wetAmpl);
		double meanWetIMI = average(wetIMI);
		double meanDryAmpl = average(dryAmpl);
		double meanDryIMI = average(dryIMI);
		
		// find the standard deviation of amplitude and imi
		double stddevWetAmpl = stddev(wetAmpl, meanWetAmpl);
		double stddevWetIMI = stddev(wetIMI, meanWetIMI);
		double stddevDryAmpl = stddev(dryAmpl, meanDryAmpl);
		double stddevDryIMI = stddev(dryIMI, meanDryIMI);
		System.out.println("wet amplitude (m): " + meanWetAmpl + " +/- " + stddevWetAmpl);
		System.out.println("wet IMI (s): " + meanWetIMI + " +/- " + stddevWetIMI);
		System.out.println("dry amplitude (m): " + meanDryAmpl + " +/- " + stddevDryAmpl);
		System.out.println("dry IMI (s): " + meanDryIMI + " +/- " + stddevDryIMI);
		
		// compute t statistic
		/*double meanAmplDiff = average(amplDiffs);
		double meanIMIDiff = average(imiDiffs);
		double amplDiffstd = stddev(amplDiffs, meanAmplDiff);
		double imiDiffstd = stddev(imiDiffs, meanIMIDiff);
		double stderrAmpl = amplDiffstd/Math.sqrt(amplDiffs.size());
		double stderrIMI = imiDiffstd/Math.sqrt(imiDiffs.size());
		double T_ampl = meanAmplDiff/stderrAmpl;
		double T_imi = meanIMIDiff/stderrIMI;
		System.out.println("amplitude T statistic: " + T_ampl);
		System.out.println("IMI T statistic: " + T_imi);*/
		System.out.println(">>>================================================================<<<");

		// remove the last patient's data
		wetAmpl.clear();
	    wetIMI.clear();
	    dryAmpl.clear();
	    dryIMI.clear();
	    
	}

	private void readFile( String fileName, String type ) {
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);

		// Create buffered reader to read in .exp file. 
		try {
			BufferedReader bReader = new BufferedReader(new FileReader(fileName));

			// Create iterator. 
			int i = 0;
			String line;

			// Read in frames and x values from file and place them in respective arrays. 
			while ((line = bReader.readLine()) != null) 
			{
				//Split the content of tab separated line. 
				String datavalue[] = line.split("\t");
				// Add the values to respective arrays. 
				try {
					frames.add(Double.parseDouble(datavalue[0])); 
					values.add(Double.parseDouble(datavalue[1])); 
				} catch (NumberFormatException nf) { continue; }

				i++;
			}
			// Close the file. 
			bReader.close();
			mark(type);
		} catch (Exception e) {}
	}

	private void mark(String type) {
		// Create iterator. 
		int j = 0;
		// Remove all numbers in first column before 0, including measurement rate and data capture period.  
		while(frames.get(j) != 0.0) 
		{
			frames.remove(j);
		}

		// Calculate average position. . 
		double avg = average(values);

		// Create array of doubles for min/max marks. 
		double[] mark = new double[frames.size()];

		// Create lists of doubles for max and min values. 
		ArrayList<Double> maxes = new ArrayList<Double>();
		ArrayList<Double> mins = new ArrayList<Double>();

		// Create lists of doubles for max and min position marks. 
		ArrayList<Double> fmin = new ArrayList<Double>();
		ArrayList<Double> fmax = new ArrayList<Double>();

		// Create booleans to indicate if a max/min should be marked.  
		boolean addMax = true;
		boolean addMin = true;

		int pos;
		// Start at frame 175 and find position maxes and mins. 
		// The position is inverted
		for(pos = 175; pos < values.size() - 40; pos++)
		{   
			// Add a max. 
			// If the the forward slope is decreasing, addMax is true, and the position is greater than the average, 
			// and the max to add is at least 500 frames past the last min, add a max to maxes and its position to the fmax. 
			if ((values.get(pos + 10)-values.get(pos))/40 <= -0.000055 && addMax && values.get(pos) > avg ) 
			{
				if (fmin.size() > 0 && pos > fmin.get(fmin.size()-1) + 225) 
				{
					maxes.add(values.get(pos-175));
					fmax.add(frames.get(pos-175));
					addMax = false;
					addMin = true;
					mark[pos] = -0.03;
				} 
				else if (fmin.size() == 0) 
				{
					maxes.add(values.get(pos-175));
					fmax.add(frames.get(pos-175));
					// Set addMax to false and addMin to true so a min will be added next. 
					addMax = false;
					addMin = true;
					// Mark the max in the mark array. 
					mark[pos] = -0.03;
				}
			}
			// Add a min. 
			// If the the forward slope is increasing, addMin is true, and the position is less than the average,
			// add a max to maxes and its position to the fmax. 
			else if ((values.get(pos+10)-values.get(pos))/40 >= 0.00001 && addMin && values.get(pos) < avg) 
			{
				mins.add(values.get(pos-175));
				fmin.add(frames.get(pos-175));
				addMin = false;
				addMax = true;
				// Mark the min in the mark array. 
				mark[pos] = -0.07;
			}
			else 
			{ 
				mark[pos] = 0; 
			}
		}

		// Get index of the first maximum whose frame number is greater than 2000. 
		double maxFrame = 0; int maxIndex = 0; int minIndex = 0; double minFrame =0;
		for (int m = 0; m < fmax.size(); m++) {
			if (fmax.get(m) > frames.size()*0.1) {
				maxFrame = fmax.get(m);
				maxIndex = m;
				break;
			}
		}

		// Get the first min's frame number and its index. 
		for (int n = 0; n < fmin.size(); n++) {
			if (fmin.get(n) > maxFrame) {
				minIndex = n;
				minFrame = fmin.get(n);
				break;
			}
		}

		while (minFrame < frames.size()*0.9) {
			// Calculate amplitudes up to the last 2000 frames and add them to ampl ArrayList.
			if (type.equals("wet"))
				wetAmpl.add(abs(mins.get(minIndex++)) - abs(maxes.get(maxIndex++)));
			else dryAmpl.add(abs(mins.get(minIndex++)) - abs(maxes.get(maxIndex++)));
			try{
				minFrame = fmin.get(minIndex);
				if (minFrame < 18000) {
					if (type.equals("wet"))
						wetIMI.add((fmin.get(minIndex)-fmin.get(minIndex-1))/200);
					else dryIMI.add((fmin.get(minIndex)-fmin.get(minIndex-1))/200);
				}
			} catch (IndexOutOfBoundsException e) { break; }
		}
		
	}
	
	// average function computes the average of a set of doubles
	private double average( ArrayList<Double> nums ) {
		double sum = 0;
		for (double num : nums) sum += num;
		return sum/nums.size();
	}

	// stddev function computes the standard deviation of a set of
	// numbers given its mean
	private double stddev(ArrayList<Double> vals, double mean) {
		ArrayList<Double> vars = new ArrayList<Double>();
		for (double val : vals) vars.add(Math.pow(val-mean, 2.0));
		return Math.sqrt(average(vars));
	}
	
}
