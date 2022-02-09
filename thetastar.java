import java.util.Scanner;

import javax.swing.text.html.HTMLDocument.RunElement;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
public class thetastar {



    public static class Point {
        public int x;
        public int y;
        public Point previous;
        

        public Point(int x, int y, Point previous) {
            this.x = x;
            this.y = y;
            this.previous = previous;
        }

        @Override
        public String toString() { return String.format("(%d, %d)", x, y); }

        @Override
        public boolean equals(Object o) {
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() { return Objects.hash(x, y); }

        public Point offset(int ox, int oy) { return new Point(x + ox, y + oy, this);  }
    }
    public static boolean IsWalkable(int [][ ] grid, Point point, String operation, Point originalPoint, Point start, Point end){
        int x0 = originalPoint.x;
        int y0 = originalPoint.y;
        int x1 = point.x;
        int y1 = point.y;
        double dX = x1- x0;
        double dY = y1 - y0;
        double f = 0;
       
        if(dY <  0){
            dY = Math.abs(dY);
            originalPoint.y = 0;

        }else{
            originalPoint.y = 1;
        } 
        if(dX <  0){
            dX = Math.abs(dX);
            originalPoint.x = 0;

        }else{
            originalPoint.x = 1;
        }
        if(dX >= dY){
            while(x0 != x1){
                f = f + dY;
                if(f >= dX){
                    if(grid[x0+ ((originalPoint.x - 1)/2)][y0 +((originalPoint.y - 1)/2)] == 1){
                        return false;
                    }
                   y0 = y0 + originalPoint.y; 
                }
                if(f != 0  && grid[x0+ ((originalPoint.x - 1)/2)][y0 +((originalPoint.y - 1)/2)] == 1){
                    return false;
                }
                if(dY == 0 && grid[x0 + ((originalPoint.x - 1)/ 2)][y0] == 1 && grid[x0 + ((originalPoint.x - 1)/ 2)][y0 - 1] == 1){
                    return false;
                }
                x0 = x0 + originalPoint.x;
            }
        }else{
            while(y0 != y1){
                f = f +dX;
                if(f >= dY){

                    if(grid[x0 + ((originalPoint.x - 1)/ 2)][y0 + ((originalPoint.y - 1) / 2)] == 1){
                        return false;
                    }
                    x0 = x0 + originalPoint.x;
                    f = f - dY;
                }
                if(f != 0  && grid[x0+ ((originalPoint.x - 1)/2)][y0 +((originalPoint.y - 1)/2)] == 1){
                    return false;
                }
                if(dX == 0 && grid[x0][y0 + ((originalPoint.y - 1)/ 2)] == 1 && grid[x0 - 1][y0 + ((originalPoint.y - 1) / 2)] == 1){
                    return false;
                }
                y0 = y0 + originalPoint.y;

            }
        }
        return grid[point.x][point.y];
        
      
        
        
    }

    public static List<Point> FindNeighbors(int[][] map, Point point, Point start, Point end) {
        List<Point> neighbors = new ArrayList<>();
        Point up = point.offset(0,  1);
        Point upRight = point.offset(1,  1);
        Point upLeft = point.offset(1,  -1);
        Point downRight = point.offset(1, -1);
        Point downLeft = point.offset(-1, -1);
        Point down = point.offset(0,  -1);
        Point left = point.offset(-1, 0);
        Point right = point.offset(1, 0);
       
        
        if (IsWalkable(map, up, "up", point, start , end)) neighbors.add(up);
        if (IsWalkable(map, down, "down", point, start , end)) neighbors.add(down);
        if (IsWalkable(map, left, "left", point, start , end)) neighbors.add(left);
        if (IsWalkable(map, right, "right", point, start , end)) neighbors.add(right);
        if (IsWalkable(map, upRight, "upRight", point, start , end))neighbors.add(upRight);
        if (IsWalkable(map, upLeft, "upLeft", point, start , end)) neighbors.add(upLeft);
        if (IsWalkable(map, downRight, "downRight", point, start , end)) neighbors.add(downRight);
        if (IsWalkable(map, downLeft, "downLeft", point, start , end)) neighbors.add(downLeft);
        return neighbors;
    }

    public static List<Point> FindPath(int[][] map, Point start, Point end) {
        boolean finished = false;
        List<Point> used = new ArrayList<>();
        used.add(start);
        while (!finished) {
            List<Point> newOpen = new ArrayList<>();
            for(int i = 0; i < used.size(); ++i){
                Point point = used.get(i);
                for (Point neighbor : FindNeighbors(map, point, start, end)) {
                   
                    if (!used.contains(neighbor) && !newOpen.contains(neighbor)) {
                        newOpen.add(neighbor);
                    }
                }
            }

            for(Point point : newOpen) {
                used.add(point);
                if (end.equals(point)) {
                    finished = true;
                    break;
                }
            }

            if (!finished && newOpen.isEmpty())
                return null;
        }

        List<Point> path = new ArrayList<>();
        Point point = used.get(used.size() - 1);
        while(point.previous != null) {
            path.add(0, point);
            point = point.previous;
        }
        return path;
    }


        

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
					args[0]));
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
        StdDraw.point(startX + 1, (cols + 1) - startY ) ;
        StdDraw.point(goalX + 1, (cols + 1) - goalY ) ;
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

            //A* configuration
            Point start = new Point(startX , startY , null);
            Point end = new Point(goalX , goalY , null);
            List<Point> path = FindPath(openData, start, end);
            int pathCount = 0;
           //Path Values / Coordinates
            if (path != null) {
               for(int i = 0; i < path.size(); i++){
                    if(i < path.size()- 1){
                        int x1 = path.get(i).x;
                        int y1 = path.get(i).y;
                        int x2 = path.get(i + 1).x;
                        int y2 = path.get(i + 1).y;
                        StdDraw.setPenRadius(0.02);
                        StdDraw.setPenColor(0,0,255);
                        if(i == 0){
                            StdDraw.line(start.x + 1 , (cols + 1) - start.y, x1 + 1, (cols + 1) - y1);
                           
                            StdDraw.line(x1 + 1, (cols + 1) - y1, x2 + 1, (cols + 1) - y2);
                             
                        }else{

                            StdDraw.line(x1 + 1, (cols + 1) - y1, x2 + 1, (cols + 1) - y2);
                        }



                        System.out.println(path.get(i + 1).x);
                    }
                
               }
                for (Point point : path) {
                    System.out.println(point);
                }
            }
            else{

                System.out.println("No path found");
            }
            double h=0;
                double g=0;
                double f=0;
                while(true){

                if(StdDraw.isMousePressed()){
                    int x=(int)Math.round(StdDraw.mouseX()-1);
                    int y=(int)Math.round(StdDraw.mouseY()-1);
                    y=cols-y;
                    h=Math.sqrt(Math.pow((x-goalX),2)+Math.pow((y-goalY),2));
                    g=Math.sqrt(Math.pow((x-startX),2)+Math.pow((y-startY),2));
                    f=h+g;

                    System.out.println("G value: "+g+" H value: "+h+" F value: "+f);
                    StdDraw.pause(200);
                    //StdDraw.text(3,1.2,"G value: "+g+" H value: "+h+" F value: "+f);

                }

            }
        }




}
