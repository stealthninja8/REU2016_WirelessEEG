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
/**
 *
 * @author neurophys
 */
public class DataMarking {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) throws Exception {
		// TODO code application logic here
		String dataFileName = "B://REU Data/Sam_Move1.exp";
		ArrayList<Double> value1 = new ArrayList<Double>();
		ArrayList<Double> value2 = new ArrayList<Double>();

		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);


		int i =0;
		int j =0; 



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
			value1.add(Double.parseDouble(datavalue[0])); //frame
			value2.add(Double.parseDouble(datavalue[1])); //x


			/**
			 * Printing the value read from file to the console
			 */
			//System.out.println(value1.get(i) + "\t" + value2.get(i) + "\t" + value3.get(i) + "\t"
			//		+ value4.get(i));
			i++;
		}
		bReader.close();

		double max=value2.get(0);
		double min=value2.get(0);


		while(j<i){
			if(value2.get(j) < min) {
				min = value2.get(j);
			}
			if(value2.get(j) > max){
				max=value2.get(j);
			}
			j++;
		}
		j=0;
		double midline = ((max+min)/2);
		int[] mark = new int[i];
		while (j<10){
			mark[j] = 0;
			j++;

		}

		while(j<i-10){
			if(value2.get(j) <= midline+0.001 && value2.get(j) >= midline-0.001){
				if(value2.get(j-10)> value2.get(j+10)){
					if(mark[j-1]==0){
						mark[j]=1;
					}
					if(mark[j-1]== 1 && abs(value2.get(j-1)-midline) > abs(value2.get(j)-midline)){
						mark[j]=1;
						mark[j-1] = 0;
					}
				}
			}
			else{
				mark[j]=0;
			}
			j++;
		}
		while (j<i){
			mark[j]=0;
			j++;
		}
		j = 0;
		System.out.println(midline);
		while(j<i){

			System.out.println(value1.get(j) + "\t" +value2.get(j) + "\t" + mark[j]);
			j++;
		}
	}

}