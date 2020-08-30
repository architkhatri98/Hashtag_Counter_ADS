/*

	Name  : Archit Khatri
	UFID  : 3333 9695
	Email : architkhatri@ufl.edu

*/

import java.io.*;

public class hashtagcounter {

	
	/* This represents main class where input is read from the input file and methods are called according to the input */

	
	public static void main(String[] inputArguments) {
		
		FibonacciHeap heap = new FibonacciHeap();
		boolean outputFileProvided = false;
		
	    String inputHashTagFile = inputArguments[0];
	    String outputFileName = "";

	    if (inputArguments.length > 1) {
	    	outputFileProvided = true;
	    }

	    if (outputFileProvided) {
	    	outputFileName = inputArguments[1];
	    }

	    
	    try {
	    	FileReader inputFileReader = new FileReader(inputHashTagFile);
            BufferedReader bufferedReader = new BufferedReader(inputFileReader);
	        String line;
	        
	        while ((line = bufferedReader.readLine()) != null) {
	            char currentChar = line.charAt(0);
	            
	            if(currentChar=='#') {
	            	String[] parts = line.split(" ");
	            	String hashname = parts[0];
	            	String name = hashname.substring(1);
	            	String number = parts[1];
	                int count = Integer.parseInt(number);
	                heap.insert(name,count);   	
	            }
	            else if(currentChar=='S' || currentChar == 's') {
	                return;
	            }
	            else {
	            	int requestedNum = Integer.parseInt(line);
	            	if (outputFileProvided)
	            		heap.removeMaxWithOP(requestedNum, outputFileName);
	            	else
	            		heap.removeMax(requestedNum);
	            }
	        }
	        bufferedReader.close();
	    } catch(Exception e){
	        System.out.println("Error in the file while reading lines:" + e.getMessage());                      
	    }
	}

}
