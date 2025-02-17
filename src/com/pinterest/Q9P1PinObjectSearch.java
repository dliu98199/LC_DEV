package com.pinterest;

import java.util.*;


public class Q9P1PinObjectSearch {
    static class Pixel {
        int x, y;

        Pixel(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static Pixel[][] createPin(int width, int height) {
        Pixel[][] pin = new Pixel[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pin[i][j] = new Pixel(i, j);
            }
        }
        return pin;
    }

    public static boolean isSameObject(Pixel p1, Pixel p2) {
        /*
            0  X  0  0  Y  Y
            X  X  0  0  Y  Y
            0  X  Z  0  0  0
            0  0  Z  0  0  0
            0  0  0  0  0  W
            0  0  0  0  W  W
        */
        List<List<Pixel>> allObjects = Arrays.asList(
                Arrays.asList(new Pixel(0,1), new Pixel(1,0), new Pixel(1,1), new Pixel(2,1)), // Top-left cluster
                Arrays.asList(new Pixel(0,4), new Pixel(0,5), new Pixel(1,4), new Pixel(1,5)), // Top-right cluster
                Arrays.asList(new Pixel(2,2), new Pixel(3,2), new Pixel(2,3)), // Middle cluster
                Arrays.asList(new Pixel(4,5), new Pixel(5,4), new Pixel(5,5)) // Bottom-right cluster
        );

        for (List<Pixel> object : allObjects) {
            boolean p1Exists = false, p2Exists = false;
            for (Pixel p : object) {
                if (p.x == p1.x && p.y == p1.y) p1Exists = true;
                if (p.x == p2.x && p.y == p2.y) p2Exists = true;
                if (p1Exists && p2Exists) return true;
            }

        }
        return false;
    }

    public static int countObjects(Pixel[][] pin) {
        int width = pin.length;
        if (width == 0) return 0;
        int height = pin[0].length;

        List<String> visitedCoord = new ArrayList<>();
        int count = 0;
        int[][] directions = {{-1,0}, {1,0}, {0,-1}, {0,1}};

        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                String currentCoord = i + "," + j;
                if(visitedCoord.contains(currentCoord)) continue;

                Queue<int[]> queue = new LinkedList<>();
                queue.offer(new int[]{i,j});
                visitedCoord.add(currentCoord);
                boolean foundObject = false;

                while (!queue.isEmpty()){
                    int[] pixel = queue.poll();
                    int px = pixel[0];
                    int py = pixel[1];
                    for(int[] direction: directions){
                        int nx = px + direction[0]; int ny = py + direction[1];

                        if(nx >=0 && ny >=0 && ny <height && nx< width && !visitedCoord.contains(nx+","+ny)){
                            if(isSameObject(new Pixel(px, py), new Pixel(nx,ny))){
                                queue.add(new int[] {nx,ny});
                                visitedCoord.add(nx+","+ny);
                                foundObject = true;
                            }

                        }
                    }
                }

               if( foundObject ){
                   count++;
               }
            }
        }

        return count;

    }

    public static int countObjectsOpt(Pixel[][] pin) {
        int width = pin.length;
        if (width == 0) return 0;
        int height = pin[0].length;

        //Set<String> visited = new HashSet<>();
        List<String> visited = new ArrayList<>();
        int count = 0;
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Up, Down, Left, Right

        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                String coordKey = w + "," + h;
                if (visited.contains(coordKey)) continue; // Skip if already visited

                Queue<int[]> queue = new LinkedList<>();
                queue.add(new int[]{w, h});
                visited.add(coordKey);
                boolean foundObject = false;

                while (!queue.isEmpty()) {
                    int[] pixelCoord = queue.poll();
                    int px = pixelCoord[0], py = pixelCoord[1];

                    for (int[] dir : directions) {
                        int nx = px + dir[0], ny = py + dir[1];
                        if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                            String nextKey = nx + "," + ny;
                            if (!visited.contains(nextKey) && isSameObject(new Pixel(px, py), new Pixel(nx, ny))) {
                                queue.add(new int[]{nx, ny});
                                visited.add(nextKey);
                                foundObject = true;
                            }
                        }
                    }
                }

                if (foundObject) count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Pixel[][] pin = createPin(4,5);
        int numObjects = countObjects(pin);
        int numObjectsOPT = countObjectsOpt(pin);

        System.out.println("Number of objects in the pin: " + numObjects);
        System.out.println("Number of objects in the pin_OPT: " + numObjectsOPT);
    }
}
