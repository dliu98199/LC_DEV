package com.pinterest;

import java.util.*;

public class GoodRankingCandidates {

    public static int getGoodRankCandidate(double[] engagementScore, double[] relevanceScore, int k) {
        if (engagementScore.length != relevanceScore.length) {
            return -1;  // Length mismatch
        }

        int n = engagementScore.length;
        if (n <= k) {
            return 0;  // Not enough candidates
        }
        if (k == 0) {
            return n;  // All candidates satisfy if k == 0
        }

        // Pair relevance and engagement scores, and sort by relevance
        List<double[]> pairedScores = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            pairedScores.add(new double[]{relevanceScore[i], engagementScore[i]});
        }
        pairedScores.sort(Comparator.comparingDouble(a -> a[0]));  // Sort by relevance score

        // Use TreeSet for efficient insertion and searching
        List<Double> engagementSortedList = new ArrayList<>();
        TreeSet<Double> engagementTreeSet = new TreeSet<>();
        int result = 0;

        for (double[] scores : pairedScores) {

            double engagement = scores[1];

            // Count how many elements are less than the current engagement score
            if (engagementTreeSet.headSet(engagement, false).size() >= k) {
                result++;
            }
//            // binary search method:
//            int insertPos = bisectLeft(engagementSortedList, engagement);
//            if(insertPos >= k){
//                result ++;
//            }

            // Add the engagement score to TreeSet
            engagementTreeSet.add(engagement);
        }

        return result;
    }

    // Custom bisect_left function to find the insertion point
    private static int bisectLeft(List<Double> sortedList, double val) {
        int left = 0, right = sortedList.size();

        while (left < right) {
            int mid = (left + right) / 2;
            if (sortedList.get(mid) < val ) {
                 left = mid + 1;
            } else {
                //The key reason why we move right = mid in binary search (bisectLeft) is to ensure that we always find the leftmost (first) occurrence of a number, even if there are duplicates.
                 right = mid;
            }
        }
        return left;
    }


    public static void main(String[] args) {
        double[] engagementScore = {0.1, 0.2, 0.0};
        double[] relevanceScore = {0.2, 0.3, 0.1};
        int k = 1;

        int result = getGoodRankCandidate(engagementScore, relevanceScore, k);
        System.out.println("Number of good ranking candidates: " + result);
    }

    /*
        When you click "Run" in an IDE like IntelliJ or Eclipse:
        The IDE finds the main method in the specified class.
        It calls the main method using the JVM.
        The program executes.
    */
}
