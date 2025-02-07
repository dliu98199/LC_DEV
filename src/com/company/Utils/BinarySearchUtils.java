package com.company.Utils;
import java.util.List;

public class BinarySearchUtils {

    /**
     * Finds the leftmost insertion index of `val` in a sorted list.
     * @param sortedList A list of sorted elements.
     * @param val The value to search for.
     * @return The leftmost index where `val` should be inserted.
     */
    public static int bisectLeft(List<Double> sortedList, double val) {
        int left = 0, right = sortedList.size();

        while (left < right) {
            int mid = (left + right) / 2;
            if (sortedList.get(mid) < val) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;  // Leftmost insertion index
    }
}
