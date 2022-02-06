import java.util.Scanner;
import java.io.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class triangle {
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
    public static boolean IsWalkable(int [][ ] grid, Point point, String operation){
        System.out.println(grid.length);
        System.out.println(grid[0].length);

        if (point.y < 0 || point.y > grid.length - 1){
            return false;
        } 
        else if(point.x < 0 || point.x > grid[0].length - 1){
            return false;
        } 
        else{

            if(operation == "upRight"){
            
                if(grid[point.x][point.y] == 0 && !(point.y - 1 < 0 || point.y - 1 > grid.length - 1) && !(point.x - 1 < 0 || point.x  - 1> grid[0].length - 1) && grid[point.x - 1][point.y - 1] == 0){
                    return grid[point.y][point.x] == 0;
                }else{
                    return false;
                }
            }
            if(operation == "upLeft"){
                if(grid[point.x][point.y] == 0 && !(point.y - 1 < 0 || point.y - 1 > grid.length - 1) && !(point.x + 1 < 0 || point.x  + 1> grid[0].length - 1) && grid[point.x + 1][point.y - 1] == 0){
                    return grid[point.y][point.x] == 0;
                }else{
                    return false;
                }
            }
            if(operation == "downRight"){
                if(!(point.y + 1 < 0 || point.y + 1 > grid.length - 1) || !(point.x - 1 < 0 || point.x - 1> grid[0].length - 1))
                if(grid[point.x][point.y] == 0  && grid[point.x - 1][point.y + 1] == 0){
                    return grid[point.y][point.x] == 0;
                }else{
                    return false;
                }
            }
            if(operation == "downLeft"){
                if((point.y + 1 < 0 || point.y + 1 > grid.length ) == false && (point.x + 1 < 0 || point.x + 1 > grid[0].length ) == false){

                    if(grid[point.x][point.y] == 0 && grid[point.x + 1][point.y + 1] == 0){
                        return grid[point.y][point.x] == 0;
                    }else{
                        return false;
                    }
                }
            }
        }
      return true;
    }

    public static List<Point> FindNeighbors(int[][] map, Point point) {
        List<Point> neighbors = new ArrayList<>();
        Point up = point.offset(0,  1);
        Point upRight = point.offset(1,  1);
        Point upLeft = point.offset(1,  -1);
        Point downRight = point.offset(1, -1);
        Point downLeft = point.offset(-1, -1);
        Point down = point.offset(0,  -1);
        Point left = point.offset(-1, 0);
        Point right = point.offset(1, 0);
       
        
        if (IsWalkable(map, upRight, "upRight"))neighbors.add(upRight);
        if (IsWalkable(map, upLeft, "upLeft")) neighbors.add(upLeft);
        if (IsWalkable(map, downRight, "downRight")) neighbors.add(downRight);
        if (IsWalkable(map, downLeft, "downLeft")) neighbors.add(downLeft);
        if (IsWalkable(map, up, "up")) neighbors.add(up);
        if (IsWalkable(map, down, "down")) neighbors.add(down);
        if (IsWalkable(map, left, "left")) neighbors.add(left);
        if (IsWalkable(map, right, "right")) neighbors.add(right);
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
                for (Point neighbor : FindNeighbors(map, point)) {
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
            Point start = new Point(startX - 1, startY - 1, null);
            Point end = new Point(goalX - 1, goalY - 1, null);
            List<Point> path = FindPath(openData, start, end);
            if (path != null) {
                for (Point point : path) {
                    System.out.println(point);
                }
            }
            else
                System.out.println("No path found");
        }


    }

