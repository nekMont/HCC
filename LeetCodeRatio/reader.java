import java.io.*;
import java.util.*;

public class reader {

   // set to true to enable debug printing
   static boolean debug = false;

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
   	pen.write("<!DOCTYPE html>\n\n");
   	pen.write("<html>\n");
   	pen.write("<head>\n");
   	
   	// set title for tab
   	pen.write("\t<title>Like to Dislike Ratio!</title>\n");
   	
   	// link html to css file
   	pen.write("\t<link rel=\"stylesheet\" href=\"./popup.css\">\n");
   	
   	// opening html tags
   	pen.write("</head>\n\n");
   	pen.write("<body>\n");

    // search bars
	pen.write("\t<label style='color:white' for=\"myInput\">Search: </label>\n");
    pen.write("\t<input type=\"text\" id=\"myInput\" placeholder=\"Question Name\">\n");
	pen.write("\t<input type='text' id='myInputCompany' placeholder='Company'>\n");
	pen.write("\t<br><br>\n");
   	
   	// create table with black background
   	pen.write("\t<table class=\"table-sortable\" id=\"ourTable\" bgcolor=\"black\" width=\"900\">\n");
   	// create table header row
   	pen.write("\t\t<thead>\n");
   	pen.write("\t\t\t<tr bgcolor=\"#C9C0BD\">\n");
   	// add colomun headers to table
   	pen.write("\t\t\t\t<th width=\"50\">#</th>\n");
   	pen.write("\t\t\t\t<th width=\"350\">Question</th>\n");
   	pen.write("\t\t\t\t<th width=\"100\">Difficulty</th>\n");
   	pen.write("\t\t\t\t<th width=\"200\">Like-Dislike Ratio</th>\n");
   	pen.write("\t\t\t\t<th width=\"200\">Company</th>\n");
   	
	// closing tags
   	pen.write("\t\t\t</tr>\n");
   	pen.write("\t\t</thead>\n");
   	pen.write("\t\t<tbody>\n");
   	
   	// read in everything from .csv file
   	while (scan.hasNext()) {
   	
   	   // read a line from .csv
   	   line = scan.nextLine();
   	   // count the commas in the read line (for error cases)
   	   int commaCount = countCommas(line);
   	   
	   // line contains information
   	   if (commaCount >= 1)
   	      line = line.substring(findIntegerIndex(line));
   	   
   	   // debug printing
   	   if (debug)
   	      System.out.println(line);
   	   
   	   // declare empty strings 
   	   String question = "", difficulty = "", likes = "", dislikes = "", link = "";
   	   String likeDislikeRatio = "", number = "", company = "";
   	   
   	   // process the information from a line
   	   if (commaCount >= 6) {
   	   
   	      // grab question number
   	      number = line.substring(0,line.indexOf('.'));
   	      line = line.substring(line.indexOf(' ') + 1);

   	      // grab the question name store in String question
   	      question = question + line.substring(0,line.indexOf(','));
   	      line = line.substring(line.indexOf(',') + 1);

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
   	      link = line.substring(0,line.indexOf(','));
   	      line = line.substring(line.indexOf(',') + 1);
   	      
   	      // grab the company name
   	      if (line.contains("\""))
   	         company = line.substring(line.indexOf('"')+1,line.lastIndexOf('"'));
   	      else
   	         company = line;
   	      
		  // debug printing
   	      if (debug) {
	   	      System.out.println(number);
	   	      System.out.println(question);
	   	      System.out.println(difficulty);
	   	      System.out.println(likes);
	   	      System.out.println(dislikes);
	   	      System.out.println(link);
	   	      System.out.println(company);
   	      } // close if
   	      
   	      // convert numbers with xx.xk into their numerical value
   	      if (likes.contains("K"))
   	         likes = convertK(likes);;
   	      if (dislikes.contains("K"))
   	         dislikes = convertK(dislikes);
   	      
   	      // calculate the like-dislike ratio in format "xx.xx%"
   	      float total = Float.valueOf(likes) + Float.valueOf(dislikes);
		  float like_percentage = (Float.valueOf(likes) / (total)) * 100;
		  float dislike_percentage = (Float.valueOf(dislikes) / (total)) * 100;

   	      //create new row in table
   	      pen.write("\t\t\t<tr bgcolor=\"seashell\" align=\"center\">\n");
   	      
   	      // add question number cell
   	      pen.write("\t\t\t\t<td>" + number + "</td>\n");
   	   
   	      // add question cell
   	      pen.write("\t\t\t\t<td align=\"left\"><a href=\"" + link + "\" target='_blank' rel='noopener noreferrwe'>" + question + "</a></td>\n");
   	   
   	      // add difficulty cell with color!
   	      if (difficulty.contains("Easy"))
   	         pen.write("\t\t\t\t<td style=\"color:MediumSeaGreen;\"> <img src='/images/" + difficulty + ".png'>" + difficulty + "</td>\n");
   	      else if (difficulty.contains("Medium"))
   	   	   pen.write("\t\t\t\t<td style=\"color:Orange;\"> <img src='/images/" + difficulty + ".png'>" + difficulty + "</td>\n");
   	      else 
   	   	   pen.write("\t\t\t\t<td style=\"color:Tomato;\"> <img src='/images/" + difficulty + ".png'>" + difficulty + "</td>\n");

		  // closing cell tag
		  pen.write("\t\t\t\t<td>\n");

          // calculate ratio of likes to dislikes
		  float ratio = (Float.valueOf(likes) + Float.valueOf(dislikes));
		  ratio = Float.valueOf(likes) / ratio;
		  // write ratio into html as hidden element, used for sorting
		  pen.write("\t\t\t\t\t<span id='ratio' hidden>" + ratio + "</span>\n");
   	   
   	      // add like bar
   	      pen.write("\t\t\t\t\t<div id='likes' style='height:5px; width:" + like_percentage + "%;background:green;float:left;'></div>\n");
		  // add dislike bar
		  pen.write("\t\t\t\t\t<div id='dislikes' style='height:5px; width:" + dislike_percentage + "%;background:red;float:right;'></div>\n");
		  // add like counter
		  pen.write("\t\t\t\t\t<div id='like' style='color:black;font-size:12px;float:left;'>likes: "+ likes +" </div>\n");
		  // add dislike counter
		  pen.write("\t\t\t\t\t<div id='dislike' style='color:black;font-size:12px;float:right;'>dislikes: " + dislikes + "</div>\n\t\t\t\t</td>\n");

   	      // add company cell
   	      pen.write("\t\t\t\t<td>" + company + "</td>\n");
   	   
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
   
   } // close catch
   
   } // close writePopup
   
   // counts commas in a given string
   public static int countCommas (String line) {
   
      int count = 0;
      
      while (line != "") {
      
	     // comma found
         if (line.contains(",")) {
         
            line = line.substring(line.indexOf(',') + 1);
            count++;
         
         } // close if
         
		 // no comma fomund
         else
            line = "";
      
      } // close while
      
      return count;
   
   } // close countCommas
   
   // finds the first integer in a string
   public static int findIntegerIndex (String line) {
   
      int count = 0;
   
    // run until an integer is found
   	while (true) {
   	
   	   if (line.charAt(count) >= '0' && line.charAt(count) <= '9')
   	      return count;
   	      
   	   count++;
   	
   	} // close while
   
   } // close find/integerIndex
   
   // converts numbers from xx.xk form into numeric form
   public static String convertK (String line) {
   
      // remove K from input
      int index = line.indexOf('K');
      String answer = line.substring(0,index);
      
	  // input contains decimal
      if (line.contains(".")) {
         index = line.indexOf('.');
         answer = answer.substring(0,index) + answer.substring(index+1) + "00";
      } // close if

	  // input does not contain decimal
      else
         answer = answer + "000";
      
      return answer;
   } // close convertK

} // close class