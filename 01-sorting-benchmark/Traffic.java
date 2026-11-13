import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;
public class Traffic {
    public static Vector<Integer> readFile(String path) {
        Vector<Integer> ret=new Vector<Integer>();
        try {
            File myFile = new File(path);
            Scanner scanner = new Scanner(myFile);
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String[] dataArray = data.split(",");
                String val =dataArray[dataArray.length-1];
                ret.add(Integer.parseInt(val));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return ret;
    }
}
