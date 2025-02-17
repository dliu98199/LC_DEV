package com.pinterest;

import java.util.*;

public class Q8P1PinRelationChecker {

    // Function to check if two pins are related using BFS
    public static boolean checkPinsRelated(Map<String, List<String>> boards, String pin1, String pin2) {
        // Build the graph as an adjacency list
        Map<String, Set<String>> graph = new HashMap<>();

        for(Map.Entry<String, List<String>> entry: boards.entrySet()){
            List<String> pins = entry.getValue();
            for(int i = 0; i < pins.size(); i++){
                for(int j = i + 1; j < pins.size(); j++){
                    graph.putIfAbsent(pins.get(i), new HashSet<>());
                    graph.putIfAbsent(pins.get(j), new HashSet<>());
                    graph.get(pins.get(i)).add(pins.get(j));
                    graph.get(pins.get(j)).add(pins.get(i));
                }
            }

        }

        // If either pin is not in the graph, they are not related
        if(!graph.containsKey(pin1) || !graph.containsKey(pin2)){
            return false;
        }

        // Perform BFS to check if pin1 and pin2 are connected
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.offer(pin1);
        visited.add(pin1);

        while (!queue.isEmpty()) {
            String currentNode = queue.poll();
            if(currentNode.equals(pin2)){
                return true;
            }
            for(String nextNode: graph.get(currentNode)){
                if(!visited.contains(nextNode)){
                    queue.add(nextNode);
                    visited.add(nextNode);
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        // Example test cases
        Map<String, List<String>> boards = new HashMap<>();
        boards.put("Travel", Arrays.asList("Mountain", "Lake", "Lake", "Lodge"));
        boards.put("Decorations", Arrays.asList("House", "Patio", "Lodge"));
        boards.put("Paintings", Arrays.asList("Picasso", "Lake", "Mountain"));
        boards.put("Clothes", Arrays.asList("Nike", "Lululemon"));

        System.out.println(checkPinsRelated(boards, "Lake", "Lodge")); // true
        System.out.println(checkPinsRelated(boards, "Picasso", "patio")); // false
    }
}
