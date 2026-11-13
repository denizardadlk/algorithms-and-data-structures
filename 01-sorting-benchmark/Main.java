import org.knowm.xchart.*;
import java.io.FileReader;
import org.knowm.xchart.style.Styler;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;
import java.io.*;

class Main {
  public static void main(String args[]) throws IOException {
    int[] splits = new int[] { 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 251282 };

    double[][] unsorted_y = new double[5][10];
    double[][] sorted_y = new double[5][10];
    double[][] rev_sorted_y = new double[5][10];

    Vector<Integer> unsorted = Traffic.readFile("TrafficFlowDataset.csv");
    int[] unsortedArray = unsorted.stream().mapToInt(Integer::intValue).toArray();
    int[] sortedArray = Algos.SHELLSORT(unsortedArray.clone());
    int[] rev_sortedArray = reverseArray(sortedArray.clone());

    double start, end;
    
    int iter = 10;

    for (int k = 0; k < splits.length; k++) {
      int i = splits[k];
      double comb_t = 0, ins_t = 0, shaker_t = 0, shell_t = 0, radix_t = 0;
      int[] temp = Arrays.copyOfRange(unsortedArray.clone(), 0, i);
      int digits = getMaxDigits(temp);

      for (int index = 0; index < iter; index++) {
        start = System.currentTimeMillis();
        Algos.CombSort(temp.clone());
        end = System.currentTimeMillis();
        comb_t += end - start;

        start = System.currentTimeMillis();
        Algos.Insertion_Sort(temp.clone());
        end = System.currentTimeMillis();
        ins_t += end - start;

        start = System.currentTimeMillis();
        Algos.SHAKERSORT(temp.clone());
        end = System.currentTimeMillis();
        shaker_t += end - start;

        start = System.currentTimeMillis();
        Algos.SHELLSORT(temp.clone());
        end = System.currentTimeMillis();
        shell_t += end - start;

        start = System.currentTimeMillis();
        Algos.RADIXSORT(temp.clone(), digits);
        end = System.currentTimeMillis();
        radix_t += end - start;
      }

      comb_t /= iter;
      ins_t /= iter;
      shaker_t /= iter;
      shell_t /= iter;
      radix_t /= iter;

      unsorted_y[0][k] = Math.max(comb_t, 0.1); 
      unsorted_y[1][k] = Math.max(ins_t, 0.1);
      unsorted_y[2][k] = Math.max(shaker_t, 0.1);
      unsorted_y[3][k] = Math.max(shell_t, 0.1);
      unsorted_y[4][k] = Math.max(radix_t, 0.1);

      System.out.println("CombSort: " + comb_t + ", InsertionSort: " + ins_t + ", ShakerSort: " + shaker_t
          + ", ShellSort: " + shell_t + ", RadixSort: " + radix_t);
          System.out.println("n: " + i + " " + "Random");
    }

    for (int k = 0; k < splits.length; k++) {
      int i = splits[k];
      double comb_t = 0, ins_t = 0, shaker_t = 0, shell_t = 0, radix_t = 0;
      int[] temp = Arrays.copyOfRange(sortedArray.clone(), 0, i);
      int digits = getMaxDigits(temp);
      
      for (int index = 0; index < iter; index++) {
        start = System.currentTimeMillis();
        Algos.CombSort(temp.clone());
        end = System.currentTimeMillis();
        comb_t += end - start;

        start = System.currentTimeMillis();
        Algos.Insertion_Sort(temp.clone());
        end = System.currentTimeMillis();
        ins_t += end - start;

        start = System.currentTimeMillis();
        Algos.SHAKERSORT(temp.clone());
        end = System.currentTimeMillis();
        shaker_t += end - start;

        start = System.currentTimeMillis();
        Algos.SHELLSORT(temp.clone());
        end = System.currentTimeMillis();
        shell_t += end - start;

        start = System.currentTimeMillis();
        Algos.RADIXSORT(temp.clone(), digits);
        end = System.currentTimeMillis();
        radix_t += end - start;
      }

      comb_t /= iter;
      ins_t /= iter;
      shaker_t /= iter;
      shell_t /= iter;
      radix_t /= iter;

      System.out.println("CombSort: " + comb_t + ", InsertionSort: " + ins_t + ", ShakerSort: " + shaker_t
          + ", ShellSort: " + shell_t + ", RadixSort: " + radix_t);
          
      sorted_y[0][k] = Math.max(comb_t, 0.1);
      sorted_y[1][k] = Math.max(ins_t, 0.1);
      sorted_y[2][k] = Math.max(shaker_t, 0.1);
      sorted_y[3][k] = Math.max(shell_t, 0.1);
      sorted_y[4][k] = Math.max(radix_t, 0.1);

      System.out.println("n: " + i + " " + "Sort");
    }

    for (int k = 0; k < splits.length; k++) {
      int i = splits[k];
      double comb_t = 0, ins_t = 0, shaker_t = 0, shell_t = 0, radix_t = 0;
      int[] temp = Arrays.copyOfRange(rev_sortedArray.clone(), 0, i);
      int digits = getMaxDigits(temp);
      
      for (int index = 0; index < iter; index++) {
        start = System.currentTimeMillis();
        Algos.CombSort(temp.clone());
        end = System.currentTimeMillis();
        comb_t += end - start;

        start = System.currentTimeMillis();
        Algos.Insertion_Sort(temp.clone());
        end = System.currentTimeMillis();
        ins_t += end - start;

        start = System.currentTimeMillis();
        Algos.SHAKERSORT(temp.clone());
        end = System.currentTimeMillis();
        shaker_t += end - start;

        start = System.currentTimeMillis();
        Algos.SHELLSORT(temp.clone());
        end = System.currentTimeMillis();
        shell_t += end - start;

        start = System.currentTimeMillis();
        Algos.RADIXSORT(temp.clone(), digits);
        end = System.currentTimeMillis();
        radix_t += end - start;
      }

      comb_t /= iter;
      ins_t /= iter;
      shaker_t /= iter;
      shell_t /= iter;
      radix_t /= iter;

      System.out.println("CombSort: " + comb_t + ", InsertionSort: " + ins_t + ", ShakerSort: " + shaker_t
          + ", ShellSort: " + shell_t + ", RadixSort: " + radix_t);
          
      rev_sorted_y[0][k] = Math.max(comb_t, 0.1);
      rev_sorted_y[1][k] = Math.max(ins_t, 0.1);
      rev_sorted_y[2][k] = Math.max(shaker_t, 0.1);
      rev_sorted_y[3][k] = Math.max(shell_t, 0.1);
      rev_sorted_y[4][k] = Math.max(radix_t, 0.1);

      System.out.println("n: " + i + " " +"Rev");
    }

    showAndSaveChart("Unsorted Array Performance", splits, unsorted_y);
    showAndSaveChart("Sorted Array Performance", splits, sorted_y);
    showAndSaveChart("Reverse Sorted Array Performance", splits, rev_sorted_y);
    compareAlgorithms(splits, unsorted_y, sorted_y, rev_sorted_y);
  }

  public static void showAndSaveChart(String title, int[] xAxis, double[][] yAxis) throws IOException {
    // Create Chart
    XYChart chart = new XYChartBuilder().width(800).height(600).title(title)
        .yAxisTitle("Time in Milliseconds").xAxisTitle("Input Size").build();

    // Convert x axis to double[]
    double[] doubleX = Arrays.stream(xAxis).asDoubleStream().toArray();

    // Customize Chart
    chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
    chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
    chart.getStyler().setMarkerSize(8);
    chart.getStyler().setYAxisLogarithmic(true); // Using logarithmic scale for raw values
    
    // Add series for each sorting algorithm
    chart.addSeries("Comb Sort", doubleX, yAxis[0]);
    chart.addSeries("Insertion Sort", doubleX, yAxis[1]);
    chart.addSeries("Shaker Sort", doubleX, yAxis[2]);
    chart.addSeries("Shell Sort", doubleX, yAxis[3]);
    chart.addSeries("Radix Sort", doubleX, yAxis[4]);

    // Save the chart as PNG
    BitmapEncoder.saveBitmap(chart, title.replace(" ", "_") + ".png", BitmapEncoder.BitmapFormat.PNG);

    // Show the chart
    new SwingWrapper<>(chart).displayChart();
  }

  public static void compareAlgorithms(int[] xAxis, double[][] unsorted, double[][] sorted, double[][] rev_sorted) throws IOException {
    String[] algorithms = {"Comb Sort", "Insertion Sort", "Shaker Sort", "Shell Sort", "Radix Sort"};
    
    for (int i = 0; i < 5; i++) {
      // Create Chart
      XYChart chart = new XYChartBuilder().width(800).height(600)
          .title(algorithms[i] + " Performance Comparison")
          .yAxisTitle("Time in Milliseconds").xAxisTitle("Input Size").build();

      // Convert x axis to double[]
      double[] doubleX = Arrays.stream(xAxis).asDoubleStream().toArray();

      // Customize Chart
      chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
      chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
      chart.getStyler().setMarkerSize(8);
      chart.getStyler().setYAxisLogarithmic(true);
      
      // Add series for each sorting algorithm
      chart.addSeries("Unsorted Array", doubleX, unsorted[i]);
      chart.addSeries("Sorted Array", doubleX, sorted[i]);
      chart.addSeries("Reverse Sorted Array", doubleX, rev_sorted[i]);

      // Save the chart as PNG
      BitmapEncoder.saveBitmap(chart, algorithms[i].replace(" ", "_") + "_Comparison.png", BitmapEncoder.BitmapFormat.PNG);

      // Show the chart
      new SwingWrapper<>(chart).displayChart();
    }
  }


  public static int[] reverseArray(int[] array) {
    int start = 0;
    int end = array.length - 1;
    while (start < end) {
      // Swap elements at start and end
      int temp = array[start];
      array[start] = array[end];
      array[end] = temp;
      start++;
      end--;
    }
    return array;
  }

  public static int getMaxDigits(int[] array) {
    int temp = 0;
    for (int num : array) {
      if (num <= 0)
        continue; // Skip non-positive numbers
      int digits = (int) Math.log10(num) + 1;
      temp = Math.max(temp, digits);
    }
    return temp > 0 ? temp : 1; // Return at least 1 digit
  }
}