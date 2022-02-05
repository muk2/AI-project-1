import java.util.Scanner;
import java.io.*;
import java.awt.Color;
public class triangle {
 
    public static void main(String[] args) {
        int startX = 0;
        int startY = 0;
        int goalX = 0;
        int goalY = 0;
        int rows = 0;
        int cols = 0;
        BufferedReader reader;
        String temp = "";

        try {
			reader = new BufferedReader(new FileReader(
					"jatt.txt"));
			String line = reader.readLine();
            int lineCounter = 0;
            
			while (line != null) {
                if(lineCounter == 0){
                    temp = line;
                  startX = Integer.parseInt(temp.substring(0 , 1));
                  startY = Integer.parseInt(temp.substring(2 , 3));
                }
                if(lineCounter == 1){
                    temp = line;
                    goalX = Integer.parseInt(temp.substring(0 , 1));
                      goalY = Integer.parseInt(temp.substring(2 , 3));
                }
                if(lineCounter == 2){
                    temp = line;
                    rows = Integer.parseInt(temp.substring(0 , 1));
                    cols = Integer.parseInt(temp.substring(2 , 3));
                }
				// read next line
				line = reader.readLine();
               
                lineCounter++;
			}
            
			reader.close();
		} catch (IOException e) {
            e.printStackTrace();
		}
        System.out.println(startX);
         System.out.println(startY);
         StdDraw.setXscale(1, rows + 1);
        StdDraw.setYscale(1, cols + 1);
        int [][] grid = new int[rows][cols];
        
       
        for(int i = 1; i <= rows + 1; i++){
            StdDraw.line(i, 0, i, cols + 1);
        }
        /* good */
        for(int j = 1; j<= cols + 1; j++){
            StdDraw.line(0, j, rows + 1, j);
        }
        
    
        StdDraw.setPenRadius(0.1);
        StdDraw.point(startX, (cols + 2) - startY ) ;
        StdDraw.point(goalX, (cols + 2) - goalY ) ;
       int [][] openData = new int[rows][cols];
        int[] dataScanner = new int[rows * cols];
        try {
			reader = new BufferedReader(new FileReader(
					"jatt.txt"));
			String line = reader.readLine();
            int lineCounter = 0;
            int openOrNot = 0; 
            int count = 0;
			while (line != null) {
                if(lineCounter > 2){
                    temp = line;
                    
                    openOrNot = Integer.parseInt(temp.substring(4 , 5));
                   dataScanner[count] = openOrNot;
                 count++;
                }
				// read next line
				line = reader.readLine();
               
                lineCounter++;
			}
			reader.close();
		} catch (IOException e) {
            e.printStackTrace();
		}
        int count = 0;
          
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                openData[i][j] = dataScanner[count++];
            }
        }
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if(openData[i][j] == 1){
                    int xNoGo = i ;
                    int yNoGo = j;
                    System.out.println(i);
                    System.out.println(j);
                    StdDraw.setPenRadius(0.01);
                    StdDraw.setPenColor(255, 0, 0);
                    StdDraw.point(i + 1, (cols + 1) - j ) ;
                    StdDraw.line(i + 1, (cols + 1) - j , i + 1, (cols) - j );
                    StdDraw.line(i + 1, (cols + 1) - j , i + 2, (cols + 1) - j );
                    StdDraw.line(i + 2, (cols + 1) - j , i + 2, (cols) - j );
                    StdDraw.line(i + 1, (cols ) - j , i + 2, (cols) - j );
                    
                }
              
            }
        }

    }
}
