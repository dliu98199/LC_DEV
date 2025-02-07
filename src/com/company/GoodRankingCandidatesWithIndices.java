package com.company;

import com.company.Utils.BinarySearchUtils;

import java.util.*;

public class GoodRankingCandidatesWithIndices {

    // Follow up 1
    public static List<Integer> getGoodRankCandidateIndices(double[] engagementScore, double[] relevanceScore, int k) {
        int n = engagementScore.length;
        if (n <= k) return new ArrayList<>();
        if (k == 0) {
            List<Integer> allIndices = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                allIndices.add(i);
            }
            return allIndices;
        }

        // Step 1: Store original indices and sort by relevance score
        List<double[]> candidates = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            candidates.add(new double[]{relevanceScore[i], engagementScore[i], i});
        }
        candidates.sort(Comparator.comparingDouble(a -> a[0]));  // Sort by relevance_score

        // Step 2: Maintain sorted engagement scores in an ArrayList
        List<Double> engagementSortedList = new ArrayList<>();
        List<Integer> goodCandidateIndices = new ArrayList<>();

        for (double[] candidate : candidates) {
            int index = (int) candidate[2];  // Get original index
            double engagement = candidate[1];

            // Step 3: Find insertion position using binary search
            int insertPos = BinarySearchUtils.bisectLeft(engagementSortedList, engagement);

            // Step 4: If at least k elements have smaller engagement scores, add index to result
            if (insertPos >= k) {
                goodCandidateIndices.add(index);
            }

            // Step 5: Insert engagement score into sorted order
            engagementSortedList.add(insertPos, engagement);
        }

        // Sort the indices before returning to maintain original order
        Collections.sort(goodCandidateIndices);
        return goodCandidateIndices;
    }

    public static void main(String[] args) {
        double[] engagementScore = {0.1, 0.2, 0.3};
        double[] relevanceScore = {0.1, 0.2, 0.3};
        int k = 0;

        List<Integer> result = getGoodRankCandidateIndices(engagementScore, relevanceScore, k);
        System.out.println("Indices of good ranking candidates: " + result);
    }
}
