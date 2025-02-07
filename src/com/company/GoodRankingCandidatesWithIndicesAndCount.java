package com.company;

import java.util.*;

public class GoodRankingCandidatesWithIndicesAndCount {

    // Follow up 2
    public static List<int[]> getGoodRankCandidateIndices(double[] engagementScore, double[] relevanceScore, int k) {
        int n = engagementScore.length;
        List<int[]> result = new ArrayList<>();

        if (n <= k) return result;
        if (k == 0) {
            for (int i = 0; i < n; i++) {
                result.add(new int[]{i, i});
            }
            return result;
        }

        // Step 1: Store original indices and sort by relevance score
        List<double[]> candidates = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            candidates.add(new double[]{relevanceScore[i], engagementScore[i], i});
        }
        candidates.sort(Comparator.comparingDouble(a -> a[0]));

        // Step 2: Use TreeSet to maintain sorted engagement scores
        TreeSet<Double> engagementSet = new TreeSet<>();

        for (double[] candidate : candidates) {
            int index = (int) candidate[2];
            double engagement = candidate[1];

            // Step 3: Count elements smaller than current engagement score using headSet()
            int insertPos = engagementSet.headSet(engagement).size();

            // Step 4: If at least k elements have smaller engagement scores, add index to result
            if (insertPos >= k) {
                result.add(new int[]{index, insertPos});
            }

            // Step 5: Insert engagement score into TreeSet
            engagementSet.add(engagement);
        }

        // Step 6: Sort the final result by original index for correct order
        result.sort(Comparator.comparingInt(a -> a[0]));

        return result;
    }

    public static void main(String[] args) {
        double[] engagementScore = {0.1, 0.2, 0.0};
        double[] relevanceScore = {0.2, 0.1, 0.3};
        int k = 1;

        List<int[]> result = getGoodRankCandidateIndices(engagementScore, relevanceScore, k);
        for (int[] entry : result) {
            System.out.println("(" + entry[0] + "," + entry[1] + ")");
        }
    }
}
