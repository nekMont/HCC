import java.io.*;
import java.util.*;

public class reader {

   public static void main(String args[]) throws IOException {

      writePopup();

   } // close main

   // Reads from "LeetCodeRatio.csv" to write html to 
   // "popup.html" and creates a table
   public static void writePopup () throws IOException {
   
   try {
   
      // declare file to be the webscrapped info
   	File f = new File("LeetCodeRatio.csv");
   	// declare scanner to look at data file
   	Scanner scan = new Scanner(f);
   	// declare pen to write to html file
   	FileWriter pen = new FileWriter("popup.html");
   	// read in the first line (headers) from .csv
   	String line = scan.nextLine();
   	
   	// write initial html tags
   	pen.write("<html>\n");
   	
   	pen.write("<head>\n");
   	
   	// set title for tab
   	pen.write("\t<title>Like to Dislike Ratio!</title>\n");
   	
   	// link html to css file
   	pen.write("\t<link rel=\"stylesheet\" href=\"./popup.css\">\n");
   	
   	// formal html tags
   	pen.write("</head>\n\n");
   	pen.write("<body>\n");

      // search bar
	  pen.write("\t<label for=\"myInput\">Search: </label>\n");
      pen.write("\t<input type=\"text\" id=\"myInput\" placeholder=\"Question Name\">\n");
	  pen.write("\t<br><br>\n");
   	
   	// create table with black background
   	pen.write("\t<table class=\"table-sortable\" id=\"ourTable\" bgcolor=\"black\" width=\"900\">\n");
   	// create table header row
   	pen.write("\t\t<thead>\n");
   	pen.write("\t\t\t<tr bgcolor=\"#C9C0BD\">\n");
   	// add colomun headers to table
   	pen.write("\t\t\t\t<th width=\"50\">#</th>\n");
   	pen.write("\t\t\t\t<th width=\"400\">Question</th>\n");
   	pen.write("\t\t\t\t<th width=\"100\">Difficulty</th>\n");
   	pen.write("\t\t\t\t<th width=\"100\">Likes</th>\n");
   	pen.write("\t\t\t\t<th width=\"100\">Dislikes</th>\n");
   	pen.write("\t\t\t\t<th width=\"150\">Like-Dislike Ratio</th>\n");
   	
	// closing tags
   	pen.write("\t\t\t</tr>\n");
   	pen.write("\t\t</thead>\n");
   	pen.write("\t\t<tbody>\n");
   	
   	// while there is stuff to read from .csv
   	while (scan.hasNext()) {
   	
   	   // variable to handle error cases
   	   int count = 0;
   	   // read a line from .csv
   	   line = scan.nextLine();
   	   // count the commas in the read line (for error cases)
   	   int commaCount = countCommas(line);
   	   
   	   
   	   if (commaCount > 1)
   	      line = line.substring(findIntegerIndex(line));
   	   
   	   // debug printing
   	   //System.out.println(line);
   	   
   	   // declare empty strings 
   	   String question = "", difficulty = "", likes = "", dislikes = "", link = "";
   	   String likeDislikeRatio = "", number = "";
   	   
   	   // error cases there is a comma in the question name
   	   if (commaCount > 5) {
   	      count = countCommas(line) - 5;
   	      commaCount = 5;
   	   } // close if
   	   
   	   // process the information from a line
   	   if (commaCount == 5) {
   	   
   	      // grab question number
   	      number = line.substring(0,line.indexOf('.'));
   	      line = line.substring(line.indexOf(' ') + 1);
   	   
   	      // grab the question name store in String question
   	      while (count >= 0) {
   	         question = question + line.substring(0,line.indexOf(','));
   	         line = line.substring(line.indexOf(',') + 1);
   	         count--;
   	      } // close while
   	      
   	      //System.out.println(number);

   	      // grab the acceptance percentage
   	      //acceptance = line.substring(0,line.indexOf(','));
   	      // filter out the acceptance percentage from string
   	      line = line.substring(line.indexOf(',') + 1);
   	   
   	      // grab the difficulty
   	      difficulty = line.substring(0,line.indexOf(','));
   	      line = line.substring(line.indexOf(',') + 1);
   	   
   	      // grab the likes
   	      likes = line.substring(0,line.indexOf(','));
   	      line = line.substring(line.indexOf(',') + 1);
   	   
   	      // grab the dislikes
   	      dislikes = line.substring(0,line.indexOf(','));
   	      line = line.substring(line.indexOf(',') + 1);
   	   
   	      // grab the link
   	      link = line;
   	      
   	      // calculate the like-dislike ratio in format "xx.xx%"
   	      float ratio = Float.valueOf(likes) / (Float.valueOf(likes) + Float.valueOf(dislikes));
   	      likeDislikeRatio = String.valueOf(ratio);
   	      likeDislikeRatio = likeDislikeRatio.substring(2,4) + "." + likeDislikeRatio.substring(4,6) + "%";
   	   
   	      //create new row in table
   	      pen.write("\t\t\t<tr bgcolor=\"seashell\" align=\"center\">\n");
   	      
   	      // add question number cell
   	      pen.write("\t\t\t\t<td>" + number + "</td>\n");
   	   
   	      // add question cell
   	      pen.write("\t\t\t\t<td align=\"left\"><a href=\"" + link + "\" target='_blank' rel='noopener noreferrwe'>" + question + "</a></td>\n");
   	   
   	      // add difficulty cell with color!
   	      if (difficulty.contains("Easy"))
   	         pen.write("\t\t\t\t<td style=\"color:MediumSeaGreen;\">" + difficulty + "</td>\n");
   	      else if (difficulty.contains("Medium"))
   	   	   pen.write("\t\t\t\t<td style=\"color:Orange;\">" + difficulty + "</td>\n");
   	      else 
   	   	   pen.write("\t\t\t\t<td style=\"color:Tomato;\">" + difficulty + "</td>\n");
   	   
   	      // add like cell
   	      pen.write("\t\t\t\t<td>" + likes + "</td>\n");
   	      // add dislike cell
   	      pen.write("\t\t\t\t<td>" + dislikes + "</td>\n");
   	      // add like-dislike ratio cell
   	      pen.write("\t\t\t\t<td>" + likeDislikeRatio + "</td>\n");
   	   
   	      //  close table
   	      pen.write("\t\t\t</tr>\n");
   	   
   	   } // close if
   	
   	} // close while
   	
   	// ending tags
   	pen.write("\t\t</tbody>\n");
   	pen.write("\t</table>\n");
   	
   	// call javascript
   	pen.write("\t<script src=\"./popup.js\"></script>\n");
   	
	// closing tags
   	pen.write("</body>\n");
   	pen.write("</html>");
   	
   	// close the readers
   	pen.close();
   	scan.close();
   	
   } // close try
   
   catch (FileNotFoundException ie) {
   
   	System.out.println("Error: File Not Found");
      // exit main
      return;
   
   }
   
   } // close writePopup
   
   // counts commas in a given string
   public static int countCommas (String line) {
   
      int count = 0;
      
      while (line != "") {
      
         if (line.contains(",")) {
         
            line = line.substring(line.indexOf(',') + 1);
            count++;
         
         } // close if
         
         else
            line = "";
      
      } // close while
      
      return count;
   
   } // close countCommas
   
   // finds the first integer in a string
   public static int findIntegerIndex (String line) {
   
      int count = 0;
   
   	while (true) {
   	
   	   if (line.charAt(count) >= '0' && line.charAt(count) <= '9')
   	      return count;
   	      
   	   count++;
   	
   	} // close while
   
   } // close find/integerIndex

} // close class