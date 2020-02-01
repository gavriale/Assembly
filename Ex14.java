
/** 
 * @author Alexander Gavrilov
 * Iterative Methods : findValWhat,findValTest,subStrC,subStrMaxC
 * Recursive methods : spiderman,spidermanPhoneBooth20,countPaths
 */

public class Ex14 {

	/**
	 * Runtime - O(n),memory O(1)
	 * 
	 * @param m - two dimension array,the array is sorted by rows and columns
	 * @param val - value we want to check if he is in the array m[][]
	 * @return return true if value found in the array,false otherwise
	 */
	public static boolean findValWhat(int[][] m, int val) {

		boolean rowFound = false;
		int indexOfRow = 0;
		/*
		 * The array is sorted so we want to find to row in which value val may
		 * be located. the while loop check n rows, so the runtime is O(n)
		 */
		while (!rowFound) {

			if (indexOfRow > m.length - 1)
				return false;
			if (m[indexOfRow][m.length - 1] == val)
				return true;
			if (m[indexOfRow][m.length - 1] > val)
				rowFound = true;
			else
				indexOfRow = indexOfRow + 1;
		}	
		
		/*
		 * The for loop is checking the row in which val may be located
		 * The runtime of the for loop is O(n)
		 */
		for (int i = 0; i < m.length; i++) {
			if (m[indexOfRow][i] == val)
				return true;
		}
		return false;
	}

	/**
	 * Runtime - O(n),memory O(1)
	 * @param m - Two dimension array sorted by Columns !
	 * @param val - check if the integer val is in the array or not
	 * @return true if found val, false otherwise
	 * 
	 * runtime - worst case is O(constant*n) == O(n)
	 */
	
	public static boolean findValTest(int[][] m, int val) {
        
		//starting the search for val from the last row and the first column in the array
		int row = m.length - 1;
		int col = 0;
        
        
		/* Runtime - one while loop, worst case is O(constant*n) == O(n),memory O(1) 
		  The while loop is a search in the array for value val
		  Every step is down(row-1) in the array or right in the array(column+1)
		  boolean move - There is only one step in each iteration,if we already moved down or right
		  move is true and then it will not do other moves(not enter into other if's)
		 */
		while (row >= 0 && col <= m.length - 1) {

			boolean move = false;
			if (m[row][col] == val)
				return true;
			if (row - 1 >= 0 && m[row - 1][col] == val)
				return true;
			if (col + 1 <= m.length - 1 && m[row][col + 1] == val)
				return true;
			if (!move && val > m[row][col]) {
				col++;
				move = true;
			}
			if (!move && row - 1 >= 0 && col < m.length && val < m[row][col] && val < m[row - 1][col]) {
				row--;
				move = true;
			}
			if (row - 1 >= 0 && val < m[row][col] && val > m[row - 1][col]) {
				col++;
			}
			if (row == 0) {
				col++;
			}
			if (col == m.length - 1) {
				row--;
			}

		}

		return false;
	}
    
	
	
	/**
	 * The method gets String from the user and checks how many subStrings there is in the String that starts with c
	 * end with c and have one c in between.
	 * 
	 * runtime of subStrC - one while loop -> O(n),memory O(1)
	 * 
	 * @param s -we want to check how many substring there is in String s
	 * @param c - the substring start with 'c' end with 'c' and have 0 or 1 c in between 
	 * @return number of substrings that start with 'c' end with 'c' and have at most one 'c' in between
	 */
	public static int subStrC(String s, char c) {

		int index = 0;
		int counter = 0;
		int cCount = 0;
		int cMiddleIndex = 0;

		if (s.length() < 2)
			return 0;

		while (index < s.length()) {

			if (s.charAt(index) == 'c') {
				cCount++;
				if (cCount == 2) {
					cMiddleIndex = index;
				}
				if (cCount == 3) {
					cCount = 1;
					counter++;
					index = cMiddleIndex + 1;
				}
				else {
					index++;
				}
			}

			else {
				index++;
			}
		}
		return counter;
	}
	
	
	/**
	 * The method check how many sub strings there is in String s, the sub strings have to start with char c
	 * given by the user, the sub strings start with char c,end with char c,and have 0<=c<=k in between
	 * 
	 * Runtime -> O(n^2),Memory O(1)
	 * @param s - we check this String and want to find how many substtring there is with given parameters 
	 * @param c - every sub String that begins with c & end with c and have 0<=c<=k will be counted  
	 * @param k - the number of times c can appear in between sub string that starts with c and end with c
	 * @return - number of sub strings
	 */
    public static int subStrMaxC(String s, char c, int k) {
        if (s.length() <= 2) {
            return 0;
        }
        int index = 0;
        int substringCounter = 0;
        while (index < s.length() - 1) {
            char currentChar = s.charAt(index);
            if (currentChar == c) {
                int currentCharSubstringCounter = 0;
                if (index + 1 >= s.length()) {
                    return substringCounter;
                }
                /*
                 * start with char c, from char c use the for loop to count the number of sub strings
                 * with no more then k C's
                 */
                for (int j = index + 1; j < s.length() && currentCharSubstringCounter <= k; j++) {
                    if (s.charAt(j) == c) {
                        currentCharSubstringCounter++;
                    }
                }
                //add the number of sub Strings from the current 'c' to the total number of sub strings
                substringCounter = substringCounter + currentCharSubstringCounter;
            }
            //search for the next c in the String
            index++;
        }
        return substringCounter;
    }

		
	/**
	 * the method checks in how many ways spiderman can get to floor n moving one or two floors each time
	 * @param n - number of floors in the building
	 * @return - The number of ways spiderman have to reach floor n
	 */
    public static int spiderman(int n){
		
		if(n==0 || n==1)
			return 1;
		if(n==2)
			return 2;
		
		return spiderman(n-1)+spiderman(n-2);
	 }
	 
    /**
     * The method check in how many ways spiderman can get to floor n, if there is condition that he have to take 
     * the elevator if he gets to floor 20
     * 
     * @param n - number of floors in the building
     * @return - The number of ways spiderman have to reach floor n with the elevator on floor 20
     */
     public static int spidermanPhoneBooth20(int n){
	   
	   if(n<20 || n>29)
		   return 0;
	   
	   /*
	    * step 1; calculate the total number of ways assuming there is no elavtor with method spiderman(int n)
	    * step 2; calculate all the ways to get to floor 20 multiply it by the number of ways to get to floor n from 20
	    * step 3; substract #2-#1, then we get all the ways to get to floor without visiting floor 20
	    * step 4; add the number of ways to get to floor 20 after subtracting it in step 3
	    */
	   return  spiderman(n) - spiderman(20)*spiderman(n-20) + spiderman(20);    	
     }
	 
	
     
    /**
      * @param mat - two dimensional array[n][k](n!=k or n==k) with int's from 0 to 99
      * @return return the number of paths there is from mat[0][0] to mat[mat length -1][mat width -1]
      * @return we get the number of paths from countPaths(int [][] matrix,int row,int col) 
    */
    public static int countPaths(int [][] mat){
         
 	    int output = 0;
     	if(mat.length == 0 &&mat[0].length==0)
     		return 0;
     	if(mat.length == 1 &&mat[0].length==1)
     		return 1;
     	
     	return countPaths(mat,0,0);   
    } 
    
    /**
     * 
     * @param matrix two dimensional array[n][k](n!=k or n==k) with int's from 0 to 99
     * @param row - our location on the path in the row of the matrix
     * @param col - our location on the path in the column of the matrix
     * @return the number of paths 
     * 
     * n every step we can go down or left, cannot go up or right in the array
     */
    private static int countPaths(int [][] matrix,int row,int col){

 	     int x=0;
 	     if(row == matrix.length-1 && col == matrix[0].length-1)
 	             x=1;       
 	     if(row > matrix.length-1 || col > matrix[0].length-1)
 	    	     return 0;
         if(matrix[row][col]==0)
    	         return 0;
 	               
    	 return x + countPaths(matrix,row + matrix[row][col]/10,col + matrix[row][col]%10)+
    	    		countPaths(matrix,row + matrix[row][col]%10,col + matrix[row][col]/10);  	           	               	    
    }
	    	
}

