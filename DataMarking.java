package datamarking;

/*
* Iowa State University 
* 07/11/16
* Andrew Zaman, Steve Heinisch, Zoe Kendall, Samantha Morris
*/
//package data.marking;

import java.io.BufferedReader;
import java.io.FileReader;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Scanner;
import java.text.DecimalFormat;

public class DataMarking {
    
    public static void main(String[] args) throws Exception {
        String dataFileName = "C:/Users/Sam/Desktop/SteveMove70_Trial1.exp";
        ArrayList<Double> frames = new ArrayList<Double>();
        ArrayList<Double> values = new ArrayList<Double>();
        
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        
        // Create buffered reader to read in exp file. 
        BufferedReader bReader = new BufferedReader(new FileReader(dataFileName));
       
        // Create iterator. 
        int i = 0;
        String line;
        
        // Read in frames and x values from file and place them in respective arrays. 
        while ((line = bReader.readLine()) != null) 
        {
            //Split the content of tabbed separated line. 
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
        
        // Create iterator. 
        int j = 0;
        // Remove all numbers in first column before 0, including measurement rate and data capture period.  
        while(frames.get(j) != 0.0) 
        {
            frames.remove(j);
        }
        
        // Get sum of all position values. 
        double sum = 0;
        for (double val : values) 
        {
            sum += val;
        }
        // Calculate average position. . 
        double avg = sum/values.size();
        
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
        for(pos = 175; pos < values.size() - 40; pos++)
        {   
            // Add a max. 
            // If the the forward slope is decreasing, addMax is true, and the position is greater than the average, 
            // and the max to add is at least 500 frames past the last min, add a max to maxes and its position to the fmax. 
            if ((values.get(pos + 40)-values.get(pos))/40 <= -0.000055 && addMax && values.get(pos) > avg ) 
            {
                if (fmin.size() > 0 && pos > fmin.get(fmin.size()-1) + 500) 
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
                    fmax.add(values.get(pos-175));
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
            else if ((values.get(pos+40)-values.get(pos))/40 >= 0.00001 && addMin && values.get(pos) < avg) 
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
        
       // Print frame number, value, and the mark status at that location (max or min). 
        for(int l = 0; l < frames.size(); l++)
        {
            System.out.println(frames.get(l) + "\t" +values.get(l) + "\t" + mark[l]);     
        }
    }
    
}
