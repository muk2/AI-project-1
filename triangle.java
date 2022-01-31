import java.util.Scanner;
import java.io.*;
import java.awt.Color;
public class Triangle {
 
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
            StdDraw.line(i, 0, i, rows);
        }
        for(int j = 1; j<= cols + 1; j++){
            StdDraw.line(0, j, rows + 1, j);
        }
        
    
        StdDraw.setPenRadius(0.1);
        StdDraw.point(startX, (cols + 2) - startY ) ;
        StdDraw.point(goalX, (cols + 2) - goalY ) ;
       
    }
}
