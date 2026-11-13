import java.util.Arrays;
import static java.lang.Math.*;

public class Algos {
    private static int getDigit(int number, int position) {
        return (number / (int) pow(10, position)) % 10;
    }

    public static int[] CombSort(int[] arr) {
        int gap = arr.length;
        double shirnk = 1.3;
        boolean sorted = false;
        while (sorted == false) {
            gap = max(1, (int) floor(gap / shirnk));
            sorted = (gap == 1);
            for (int i = 0; i < arr.length - gap; i++) {
                if (arr[i] > arr[i + gap]) {
                    int temp = arr[i];
                    arr[i] = arr[i + gap];
                    arr[i + gap] = temp;
                    sorted = false;
                }
            }
        }
        return arr;
    }

    public static int[] Insertion_Sort(int[] arr) {
        for (int j = 1; j < arr.length; j++) {
            int key = arr[j];
            int i = j - 1;
            while ((i >= 0) && (arr[i] > key)) {
                arr[i + 1] = arr[i];
                i = i - 1;
            }
            arr[i + 1] = key;
        }
        return arr;
    }

    public static int[] SHAKERSORT(int[] arr) {
        boolean swapped = true;
        while (swapped) {
            swapped = false;
            for (int i = 0; i < arr.length - 2; i++) {
                if (arr[i] > arr[i + 1]) {
                    int temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }

            swapped = false;
            for (int i = arr.length - 2; i > 0; i--) {
                if (arr[i] > arr[i + 1]) {
                    int temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapped = true;
                }
            }
        }
        return arr;
    }

    public static int[] SHELLSORT(int[] arr) {
        int n = arr.length;
        int gap = n / 2;
        while (gap > 0) {
            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                int j = i;
                while ((j >= gap) && (arr[j - gap] > temp)) {
                    arr[j] = arr[j - gap];
                    j = j - gap;
                }
                arr[j] = temp;
            }
            gap = gap / 2;
        }
        return arr;
    }

    public static int[] RADIXSORT(int[] arr, int d) {
        for (int pos = 0; pos < d; pos++) {
            arr = COUNTINGSORT(arr, pos);
        }
        return arr;
    }

    public static int[] COUNTINGSORT(int[] arr, int pos) {
        int[] count = new int[10];
        Arrays.fill(count, 0);
        int size = arr.length;
        int[] output = new int[size];
        for (int i = 0; i < size; i++) {
            int digit = getDigit(arr[i], pos);
            count[digit] = count[digit] + 1;
        }
        for (int i = 1; i < 10; i++) {
            count[i] = count[i] + count[i - 1];
        }
        for (int i = size - 1; i >= 0; i--) {
            int digit = getDigit(arr[i], pos);
            count[digit] = count[digit] - 1;
            output[count[digit]] = arr[i];
        }
        return output;
    }
}
