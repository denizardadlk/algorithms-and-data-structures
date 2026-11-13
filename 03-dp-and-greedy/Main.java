import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {

        /** Safe-lock Opening Algorithm Below **/

        System.out.println("##Initiate Operation Safe-lock##");
        // TODO: Your code goes here
        ArrayList<ArrayList<Integer>> safeset = new ArrayList<ArrayList<Integer>>();
        int number=0;
        try {
            File myObj = new File(args[0]);
            Scanner myReader = new Scanner(myObj);
            String num = myReader.nextLine();
            number = Integer.parseInt(num);
            while (myReader.hasNextLine()) {
              String data = myReader.nextLine();
              String[] temp=data.split(",");
              ArrayList<Integer> temp_num = new ArrayList<>();
              temp_num.add(Integer.parseInt(temp[0]));
              temp_num.add(Integer.parseInt(temp[1]));
              safeset.add(temp_num);
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
          MaxScrollsDP max_dp= new MaxScrollsDP(safeset);
          OptimalScrollSolution scroll= max_dp.optimalSafeOpeningAlgorithm();
          scroll.printSolution(scroll);
        // You are expected to read the file given as the first command-line argument to read
        // safes arriving each minute. Then, use this data to instantiate a
        // OptimalScrollSolution object. You need to call optimalSafeOpeningAlgorithm() method
        // of your MaxScrollsDP object to get the solution, and finally print it using
        // printSolution() method of OptimalScrollSolution object.
        System.out.println("\n+##Operation Safe-lock Completed##");

        /** Operation Artifact Algorithm Below **/

        System.out.println("##Initiate Operation Artifact##");
        // TODO: Your code goes here
        ArrayList<Integer> artifact_set = new ArrayList<Integer>();
        try {
            File myObj = new File(args[1]);
            Scanner myReader = new Scanner(myObj);
            String data = myReader.nextLine();
            String[] temp=data.split(",");
            for (String x : temp) {
                artifact_set.add(Integer.parseInt(x));
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
        OptimalShipSolution op_ship = new OptimalShipSolution(artifact_set, 0);
        MinShipsGP minship = new MinShipsGP(artifact_set);
        op_ship = minship.optimalArtifactCarryingAlgorithm();
        op_ship.printSolution(op_ship);
        // You are expected to read the file given as the second command-line argument to read
        // each artifact. Then, use this data to instantiate an OptimalShipSolution object.
        // You need to call optimalArtifactCarryingAlgorithm() method
        // of your MinShipsGP object to get the solution, and finally print it using
        // printSolution() method of OptimalShipSolution object.
        System.out.println("##Operation Artifact Completed##");

    }
}