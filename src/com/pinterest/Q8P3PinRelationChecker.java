package com.pinterest;

import java.util.*;

public class Q8P3PinRelationChecker {

        public static Map<Integer, List<String>> computeAllPairRelationScores(Map<String, List<String>> boards) {
            // Step 1: Build the Graph
            Map<String, Set<String>> graph = new HashMap<>();

            for (List<String> pins : boards.values()) {
                for (int i = 0; i < pins.size(); i++) {
                    for (int j = i + 1; j < pins.size(); j++) {
                        graph.computeIfAbsent(pins.get(i), k -> new HashSet<>()).add(pins.get(j));
                        graph.computeIfAbsent(pins.get(j), k -> new HashSet<>()).add(pins.get(i));
                    }
                }
            }

            // Step 2: Compute Shortest Path for Every Pair
            Map<Integer, List<String>> scoreMap = new TreeMap<>();   // TreeMap sorts by score
            List<String> pinsList = new ArrayList<>(graph.keySet()); // Get all the pairs

            for (int i = 0; i < pinsList.size(); i++) {
                for (int j = i + 1; j < pinsList.size(); j++) {
                    String pin1 = pinsList.get(i);
                    String pin2 = pinsList.get(j);
                    int score = bfsShortestPath(graph, pin1, pin2);

                    //if (score != Integer.MAX_VALUE) { // Only store connected pairs
                        scoreMap.computeIfAbsent(score, k -> new ArrayList<>())
                                .add("(" + pin1 + ", " + pin2 + ")");
                //    }
                }
            }

            return scoreMap;
        }

        // Helper method: BFS to find shortest path between pin1 and pin2
        private static int bfsShortestPath(Map<String, Set<String>> graph, String pin1, String pin2) {
            if (!graph.containsKey(pin1) || !graph.containsKey(pin2)) return -1;

            Queue<String> queue = new LinkedList<>();
            Map<String, Integer> distance = new HashMap<>();

            queue.offer(pin1);
            distance.put(pin1, 0);

            while (!queue.isEmpty()) {
                String curNode = queue.poll();
                int curDistance = distance.get(curNode);

                if (curNode.equals(pin2)) {
                    return curDistance; // Found shortest path
                }

                for (String neighbor : graph.get(curNode)) {
                    if (!distance.containsKey(neighbor)) { // Unvisited node
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
            boards.put("Paintings", Arrays.asList("Picasso", "Lake"));
            boards.put("Clothes", Arrays.asList("Nike", "Lululemon")); // Isolated board

            Map<Integer, List<String>> result = computeAllPairRelationScores(boards);

            // Print sorted scores with grouped pairs
            for (Map.Entry<Integer, List<String>> entry : result.entrySet()) {
                System.out.println("Score " + entry.getKey() + ": " + entry.getValue());
            }
        }


}
