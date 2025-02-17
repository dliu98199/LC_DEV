package com.pinterest;
import java.util.*;

public class Q8P2PinRelationChecker {
    // Function to compute the relation score (shortest path) between two pins
    public static int getRelationScore(Map<String, List<String>> boards, String pin1, String pin2) {
        // Step 1: Build the graph as an adjacency list
        Map<String, Set<String>> graph = new HashMap<>();

        for (List<String> pins : boards.values()) {
            for (int i = 0; i < pins.size(); i++) {
                for (int j = i + 1; j < pins.size(); j++) {
                    if (!graph.containsKey(pins.get(i))) {
                        graph.put(pins.get(i), new HashSet<>());
                    }
                    if (!graph.containsKey(pins.get(j))) {
                        graph.put(pins.get(j), new HashSet<>());
                    }
                    graph.get(pins.get(i)).add(pins.get(j));
                    graph.get(pins.get(j)).add(pins.get(i));

                }
            }
        }

        return bfsShortestPath(graph, pin1, pin2); // No connection found
    }

    public static int bfsShortestPath(Map<String, Set<String>> graph, String pin1, String pin2){
        // Step 2: BFS to find the shortest path from pin1 to pin2
        if (!graph.containsKey(pin1) || !graph.containsKey(pin2)) {
            return -1; // Pins are not related
        }

        Queue<String> queue = new LinkedList<>();
        Map<String, Integer> distance = new HashMap<>();
        queue.offer(pin1);
        distance.put(pin1, 0);

        while(!queue.isEmpty()) {
            String curNode = queue.poll();
            int curDistance = distance.get(curNode);

            if (curNode.equals(pin2)) {
                return curDistance;
            }

            for (String neighbor : graph.get(curNode)) {
                if (!distance.containsKey(neighbor)) {// Unvisited node
                    queue.offer(neighbor);
                    distance.put(neighbor, curDistance + 1);
                }
            }

        }
        return -1; // No path found
    }

    public static void main(String[] args) {
        // Example test cases
        Map<String, List<String>> boards = new HashMap<>();
        boards.put("Travel", Arrays.asList("Mountain", "Lake", "Lodge"));
        boards.put("Decorations", Arrays.asList("House", "Patio", "Lodge"));
        boards.put("Paintings", Arrays.asList("Picasso", "Lake", "Mountain"));
        boards.put("Clothes", Arrays.asList("Nike", "Lululemon"));

        System.out.println(getRelationScore(boards, "Lake", "Lodge"));  // Output: 1
        System.out.println(getRelationScore(boards, "Mountain", "Patio")); // Output: 2
        System.out.println(getRelationScore(boards, "Picasso", "Nike")); // Output: -1 (not connected)
    }
}
