import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MaxScrollsDP {
    private ArrayList<ArrayList<Integer>> safesDiscovered = new ArrayList<>();
    // Input format will be the same as following:
    // Number of safes
    // [Complexity,Scroll] Pair
    // [Complexity,Scroll] Pair
    // .
    // .
    // .
    // [Complexity,Scroll] Pair
    // See example provided below:
    // 3
    // [5,10]
    // [10,10]
    // [5,20]

    public MaxScrollsDP(ArrayList<ArrayList<Integer>> safesDiscovered) {
        this.safesDiscovered = safesDiscovered;
    }

    public ArrayList<ArrayList<Integer>> getSafesDiscovered() {
        return safesDiscovered;
    }

    public OptimalScrollSolution optimalSafeOpeningAlgorithm() throws FileNotFoundException {
        int time = safesDiscovered.size();
        int max_know = time * 5;
        int[][] dp = new int[time + 1][(max_know + 1)];
        for (int i = 0; i < time + 1; i++) {
            for (int j = 0; j < (max_know + 1); j++) {
                dp[i][j] = -1;
            }
        }
        dp[0][0]=0;
        for (int i = 1; i < time + 1; i++) {
            int complexity = safesDiscovered.get(i - 1).get(0);
            int points = safesDiscovered.get(i - 1).get(1);
            for (int j = 0; j < max_know ; j++) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j]);
                if (j >= 5 && dp[i - 1][j - 5] !=-1) {
                    dp[i][j] = Math.max(dp[i - 1][j - 5], dp[i][j]);
                }
                if (j + complexity <= max_know && dp[i - 1][j + complexity] != -1) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j + complexity] + points);
                }
            }
        }
        int max_point = 0;
        for (int j = 0; j <= max_know; j++) {
            max_point = Math.max(max_point, dp[time][j]);
        }
        OptimalScrollSolution ret = new OptimalScrollSolution(safesDiscovered, max_point);
        return ret;
    }
}
