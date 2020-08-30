/*

	Name  : Archit Khatri
	UFID  : 3333 9695
	Email : architkhatri@ufl.edu

*/


import java.util.*;
import java.io.*;

/*This represents the main Fibonacci heap where an FibonacciHeapNode object is created along with a Hashtable */

public class FibonacciHeap {
	
	public FibonacciHeapNode root;
	HashMap<String, FibonacciHeapNode> hashmp = new HashMap<>();
    
    /* Constructor for Fibonacci Heap */
    public void FibonacciHeap() {
        root = null;
        HashMap hashmp = new HashMap();
    }

    
	public void hashTagsWriteWithOP(String inputHashTags, String outputFileName) {

		/*

		hashTagsWriteWithOP():
		This function will write the max hashtags on the file that is provided 
		by the user. The hashtags that are written on the file are the one which 
		result from the root of the max Fibonacci heap.

		*/
        
        try {
            Writer write = new BufferedWriter(new FileWriter(outputFileName, true));
            write.write(inputHashTags);
            write.write("\n");
	        write.close();
	    }
		catch(Exception errorMessage) {
   			System.out.println(" There is an error while reading file : " + errorMessage.getMessage());
    	}
	}	


	public void printHashtags(String inputHashTags) {

		/*

		printHashTags():
		This will simply display the max hashtags that were removed from 
		the Fibonacci heap onto the user’s terminal which is the desired output.

		*/


		System.out.println(inputHashTags);
	}
	

	public void insert(String name , int elementValue) {

	/*

	Insert():
	This is the most basic functionality, it will create a node and then
	insert that node with the hashtag name into the Fibonacci Heap. Takes
	two argument, name of the hashtag and the value of the count of that
	hashtag. If the hashtag already exists in the hashtable, then the 
	node would be passed on to the increaseKey() method.
	
	*/
		
		FibonacciHeapNode node = new FibonacciHeapNode(elementValue,name);
		boolean resultKey = hashmp.containsKey(name);
	  
	  	if(resultKey==false) {
	  
	    	node.elementValue = elementValue;
	   		node.name = name;

	    	if (root != null) {
	    	
	        	if(root.right==root) {
	        		node.right = root;
	        		root.right = node;
	        		node.left = root;
	        		root.left = node;
	        	}
	        	else {	
	    	    	node.left = root;
		        	node.right = root.right;
		        	root.right = node;
	        		node.right.left = node;
	        	}

	        	if (node.elementValue > root.elementValue) {
	            	root = node;
	        	}   
	    
	    	}
	    	else {
	        	root = node;
	    	}   
	   		hashmp.put(name, node);
	  	}  
	  	else {
			this.increaseKey(name,elementValue);
		}	
	}	

	
	public void combinePairs() {

		/*

		combinePairs():
		combinePairs method will do a pairwise combine on the Fibonacci Heap 
		after the max node is removed and then a new hashmap is created that 
		will keep the track of new degree of the nodes. This function will 
		continue until we reach the first node from where we started.

		*/

		
		HashMap <Integer, FibonacciHeapNode> pcHashmap = new HashMap<>();
		FibonacciHeapNode currentNode = root;
		FibonacciHeapNode idx = root;
		FibonacciHeapNode max = root;
		FibonacciHeapNode tempIndex;

		boolean lastNode = false;
		do{
			currentNode=idx;			 
			tempIndex = idx.right;
			if (tempIndex==max) {
				lastNode = true;
			}
			while(pcHashmap.containsKey(currentNode.degree)==true) {
				FibonacciHeapNode temp = pcHashmap.get(currentNode.degree);
				pcHashmap.remove(currentNode.degree);
				if(temp.elementValue>=currentNode.elementValue && temp!=currentNode) {
					if(currentNode==max) {
						max=max.right;
						root = max;
					}
					currentNode.right.left=currentNode.left;
					currentNode.left.right=currentNode.right;
					currentNode.parent=temp;
					currentNode.childCut = false;
						
					if(temp.degree==0) {
						temp.child = currentNode;
						currentNode.right=currentNode;
						currentNode.left=currentNode;
					}
					else {
						currentNode.right=temp.child.right;
						temp.child.right.left=currentNode;
						temp.child.right=currentNode;
						currentNode.left=temp.child;
					}	
						
					temp.child=currentNode;
					temp.degree++;
					currentNode=temp;
						
				}
				else if(temp.elementValue<currentNode.elementValue && temp!=currentNode) {
					if(temp==max) {
						max=max.right;
						root = max;
					}
					temp.right.left=temp.left;
					temp.left.right=temp.right;
					temp.parent=currentNode;
					temp.childCut = false;
					temp.left = null;
					temp.right = null;
					if(currentNode.degree==0) {
						currentNode.child = temp;
						temp.right=temp;
						temp.left=temp;
					}
					else {
						temp.right=currentNode.child.right;
						currentNode.child.right.left=temp;
						currentNode.child.right=temp;
						temp.left=currentNode.child;
					}
					currentNode.child=temp;
					currentNode.degree++;
				}	
			}
			pcHashmap.put(currentNode.degree,currentNode);
			
			if(lastNode==true) {
				break;
			}
			if(idx!=tempIndex) {	
				idx = tempIndex.left;
			}
			idx = idx.right;
		} while(idx!=max);
		
		FibonacciHeapNode tempvar = max;
		FibonacciHeapNode maxval = max;
		
		do {
			if(tempvar.elementValue>maxval.elementValue) {
		    	maxval = tempvar;
		    }
			tempvar=tempvar.right;
			
		} while(tempvar!=max);
		root = maxval;
    }


    public void removeMaxWithOP(int requestedNum, String outputFileName) {

    	/*

    	removeMaxWithOP():
		Remove the maximum recursively until mentioned explicitly. Everytime 
		a max node is removed, it is stored in a different node. After removing
		max there is need to do a pairwise combine so, combinePairs method is called.
		
    	*/


	   int tempval = requestedNum;
	   FibonacciHeapNode nextroot = null;
	   FibonacciHeapNode requestedNode = null;
	   FibonacciHeapNode temp = root;
	   FibonacciHeapNode temp1 = root;
	   String appendedString = "";
	   
	    while(requestedNum>=1) {
		   
			if (root != null) {
	            
	       		if (root.child != null) {
	    	   		FibonacciHeapNode child = root.child;
               		do {
                   		child.parent = null;
                   		child = child.right;
               		} while (child != root.child);
                }
	          	if(root.right==root) {
	        	   nextroot = null;
	            }
	          	else {
	        	   nextroot = root.right;
	          	}	  
		   	}
		    
		    root.left.right=root.right; 
			root.right.left=root.left;

	        root.left =  root;
	        root.right = root;
	        FibonacciHeapNode childroot = root.child;
	        root.child = null;
	       	appendedString+=root.name;
	       	if(requestedNum>1) {   
	       		appendedString+=',';
	       	}
           	hashmp.remove(root.name);
           	root = mergeTrees(nextroot,childroot);
           	combinePairs();
        
          	requestedNum--;
          	if(requestedNode==null) {
        	  requestedNode = temp;
          	}
          	else {
          		requestedNode.right.left=temp;
          		temp.right=requestedNode.right;
          		temp.left=requestedNode;
          		requestedNode.right=temp;
          	}    
          	temp=root;
        }
	
     	hashTagsWriteWithOP(appendedString, outputFileName);
	 	while(tempval>=1) {
			insert(requestedNode.name,requestedNode.elementValue);
		   	requestedNode=requestedNode.right;
			tempval--;
	   	}
	}

	public void removeMax(int requestedNum) {

		/*

    	removeMax():
		Remove the maximum recursively until mentioned explicitly. Everytime 
		a max node is removed, it is stored in a different node. After removing
		max there is need to do a pairwise combine so, combinePairs method is called.
		
    	*/
		
	   int tempval = requestedNum;
	   FibonacciHeapNode nextroot = null;
	   FibonacciHeapNode requestedNode = null;
	   FibonacciHeapNode temp = root;
	   FibonacciHeapNode temp1 = root;
	   String appendedString = "";
	   
	    while(requestedNum>=1) {
		   
			if (root != null) {
	            
	       		if (root.child != null) {
	    	   		FibonacciHeapNode child = root.child;
               		do {
                   		child.parent = null;
                   		child = child.right;
               		} while (child != root.child);
                }
	          	if(root.right==root) {
	        	   nextroot = null;
	            }
	          	else {
	        	   nextroot = root.right;
	          	}	  
		   	}
		    
		    root.left.right=root.right; 
			root.right.left=root.left;

	        root.left =  root;
	        root.right = root;
	        FibonacciHeapNode childroot = root.child;
	        root.child = null;
	       	appendedString+=root.name;
	       	if(requestedNum>1) {   
	       		appendedString+=',';
	       	}
           	hashmp.remove(root.name);
           	root = mergeTrees(nextroot,childroot);
           	combinePairs();
        
          	requestedNum--;
          	if(requestedNode==null) {
        	  requestedNode = temp;
          	}
          	else {
          		requestedNode.right.left=temp;
          		temp.right=requestedNode.right;
          		temp.left=requestedNode;
          		requestedNode.right=temp;
          	}    
          	temp=root;
        }
	
     	printHashtags(appendedString);
	 	while(tempval>=1) {
			insert(requestedNode.name,requestedNode.elementValue);
		   	requestedNode=requestedNode.right;
			tempval--;
	   	}
	}


	public void increaseKey(String name, int elementValue) {

	/*

	increaseKey():
	This function will increase the value of the element if the current value 
	of the element is greater than the parent node value. If the condition becomes 
	true then the node is removed and becomes the root by inserting at that place. 
	Then childCut values are checked and the childCut value is set to true even if 
	it is cut cascade.

	*/   
		
		FibonacciHeapNode temporaryNode = new FibonacciHeapNode(elementValue, name);
		temporaryNode.childCut=true;
		FibonacciHeapNode tnode = null;
		tnode = hashmp.get(name);
		
		int temp = tnode.elementValue;
		tnode.elementValue = temp + elementValue;
		if(tnode.parent==null) {
			if (tnode.elementValue>root.elementValue) {
				 root = tnode;	 
			}
		}
		else {
		    if(tnode.elementValue > tnode.parent.elementValue) {
				while(temporaryNode.childCut == true) {			
		    		tnode.parent.degree = tnode.parent.degree-1;   
		    		temporaryNode = tnode.parent;
		    		if(tnode.left == tnode && tnode.right == tnode) {
		    			tnode.parent.child = null;
		    		}
		    		else {	 
		    	 		tnode.left.right = tnode.right;
		    	 		tnode.right.left = tnode.left;
		    	 		if(tnode.parent.child == tnode){
		    				tnode.parent.child = tnode.right;
		    	 		}
		    		}
		    	
		    		tnode.parent=null;

		    		if(root.right==root) {
			        	tnode.right=root;
			        	root.right=tnode;
			        	tnode.left=root;
			        	root.left=tnode;
			    	}
			    	else {	
			        	tnode.left = root;
				    	tnode.right = root.right;
				    	root.right = tnode;
				    	tnode.right.left = tnode;
			    	}
		    		if(temporaryNode.parent == null) {
		       			break;
		    		}
		    		if(temporaryNode.childCut == true){
		    		   tnode=temporaryNode;
		    		}
		    	}  
		    	temporaryNode.childCut = true;
		 	}
		}
	}
            
	
    public FibonacciHeapNode mergeTrees(FibonacciHeapNode n1, FibonacciHeapNode n2) {

    	/*

		mergeTrees():
		This function is used when the root is removed from the heap and the resulting 
		child nodes are needed to be melded back together. Therefore it takes two 
		arguments FibonacciHeapNode1 and FibonacciHeapNode2 and merges the tree together.

    	*/

        
        if (n1 == null && n2 == null) {
            return null;
        }
        else if (n1 == null) {
              return n2;   
        }
        else if (n2 == null) {
            return n1;
        }
        else {
        	FibonacciHeapNode tree1,tree2;
        	tree1 = n1.right;
        	tree2 = n2.right;   
        	n1.right=tree2;
        	tree2.left=n1;
        	n2.right=tree1;
        	tree1.left=n2;
        	return n1;
        }
    } 

}

class FibonacciHeapNode {

	/*
	
	This class contains the structure of the node of the Fibonacci heap. 
	The attributes of the node are child, left, right, parent, childCut, 
	degree, name and elementValue.

	*/
	
    FibonacciHeapNode child, fibHeapNode, left, right, parent;   
    boolean childCut;
    public String name; 
    public int elementValue, degree;
    

/* Constructor for Node*/
    public FibonacciHeapNode(int elementValue, String name) {
    	this.name = name;
    	this.child = null;
    	this.parent = null;
	    this.right = this;
	    this.elementValue = elementValue;
    	this.degree=0;
    	this.childCut = false;
        this.left = this;
	}
}
