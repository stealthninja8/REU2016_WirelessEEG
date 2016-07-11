/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package data.marking;

import java.io.BufferedReader;
import java.io.FileReader;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Scanner;
import java.text.DecimalFormat;
/**
 * /**
 *
 * @author neurophys
 */
public class DataMarking {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        String dataFileName = "B:/REU Data/SteveMove70_Trial1.exp";
        ArrayList<Double> value1 = new ArrayList<Double>();
        ArrayList<Double> value2 = new ArrayList<Double>();
        
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        
        
        int i =0;
        
        
        
        /**
         * Creating a buffered reader to read the file
         */
        BufferedReader bReader = new BufferedReader(
                new FileReader(dataFileName));
        
        String line;
        
        /**
         * Looping the read block until all lines in the file are read.
         */
        while ((line = bReader.readLine()) != null) {
            
            
            /**
             * Splitting the content of tabbed separated line
             */
            String datavalue[] = line.split("\t");
            try {
            value1.add(Double.parseDouble(datavalue[0])); //frame
            value2.add(Double.parseDouble(datavalue[1])); //x
            } catch (NumberFormatException nf) { continue; }
            
            
            /**
             * Printing the value read from file to the console
             */
            //System.out.println(value1.get(i) + "\t" + value2.get(i) + "\t" + value3.get(i) + "\t"
            // + value4.get(i));
            i++;
        }
        bReader.close();
        
        int j = 0;
        while(value1.get(j) != 0.0) {
        value1.remove(j);
        }
        
        double sum = 0;
        for (double val : value2) {
            sum += val;
        }
        double avg = sum/value2.size();
        
        double[] mark = new double[value1.size()];
        ArrayList<Double> maxes = new ArrayList<Double>();
        ArrayList<Double> mins = new ArrayList<Double>();
        ArrayList<Double> fmin = new ArrayList<Double>();
        ArrayList<Double> fmax = new ArrayList<Double>();
        
        boolean addMax = true;
        boolean addMin = true;
        j = 175;
        while(j<value2.size()-40){
        	//System.out.println((value2.get(j+40)-value2.get(j)));
            
            if ((value2.get(j+40)-value2.get(j))/40 <= -0.000055 && addMax && value2.get(j) > avg ) {
                if (fmin.size() > 0 && j > fmin.get(fmin.size()-1) + 500) {
                maxes.add(value2.get(j-175));
                fmax.add(value1.get(j-175));
                addMax = false;
                addMin = true;
                mark[j] = -0.03;
                } else if (fmin.size() == 0) {
                    maxes.add(value2.get(j-175));
                fmax.add(value1.get(j-175));
                addMax = false;
                addMin = true;
                mark[j] = -0.03;
                }
            } else if ((value2.get(j+40)-value2.get(j))/40 >= 0.00001 && addMin && value2.get(j) < avg) {
                mins.add(value2.get(j-175));
                fmin.add(value1.get(j-175));
                addMin = false;
                addMax = true;
                mark[j] = -0.07;
            } else { mark[j] = 0; }
           
            j++;
            /*if (value2.get(j) <= avg + 1.5E-5 && value2.get(j) >= avg - 1.5E-5) {
            	addMin = true;
            	addMax = true;
            }*/
        }
        System.out.println("maxes --> " + maxes.size()  + "\t" + "mins --> " + mins.size());

        System.out.println("MINS");
        for (int k = 0; k < mins.size(); k++) {
            System.out.println(fmin.get(k) + "\t" + mins.get(k));
        }
        
        System.out.println("MAXES");
        for (int k = 0; k < maxes.size(); k++) {
            System.out.println(fmax.get(k) + "\t" + maxes.get(k));
        }
        for(int l = 0; l < value1.size(); l++){
            
            System.out.println(value1.get(l) + "\t" +value2.get(l) + "\t" + mark[l]);
            
        }
    }
    
}