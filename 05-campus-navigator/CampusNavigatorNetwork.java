import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;

public class CampusNavigatorNetwork implements Serializable {
    static final long serialVersionUID = 11L;
    public double averageCartSpeed;
    public final double averageWalkingSpeed = 1000 / 6.0;
    public int numCartLines;
    public Station startPoint;
    public Station destinationPoint;
    public List<CartLine> lines;

    /**
     * Write the necessary Regular Expression to extract string constants from the
     * fileContent
     * 
     * @return the result as String
     */
    public String getStringVar(String varName, String fileContent) {
        // TODO: Your code goes here
        Pattern p = Pattern.compile("\\s*" + varName + "\\s*=\\s*\"([^\"]+)\"");
        Matcher m = p.matcher(fileContent);
        if (m.find()) {
            return m.group(1);
        }

        return "";
    }

    /**
     * Write the necessary Regular Expression to extract floating point numbers from
     * the fileContent
     * Your regular expression should support floating point numbers with an
     * arbitrary number of
     * decimals or without any (e.g. 5, 5.2, 5.02, 5.0002, etc.).
     * 
     * @return the result as Double
     */
    public Double getDoubleVar(String varName, String fileContent) {
        // TODO: Your code goes here
        Pattern p = Pattern.compile("\\s*" + varName + "\\s*=\\s*([0-9]+(\\.[0-9]+)?)");
        Matcher m = p.matcher(fileContent);
        if (m.find()) {
            return Double.parseDouble(m.group(1));
        }

        return 0.0;
    }
    public int getIntVar(String varName, String fileContent) {
        Pattern p = Pattern.compile("\\s*" + varName + "\\s*=\\s*([0-9]+)");
        Matcher m = p.matcher(fileContent);
        m.find();
        return Integer.parseInt(m.group(1));
    }

    /**
     * Write the necessary Regular Expression to extract a Point object from the
     * fileContent
     * points are given as an x and y coordinate pair surrounded by parentheses and
     * separated by a comma
     * 
     * @return the result as a Point object
     */
    public Point getPointVar(String varName, String fileContent) {
        // TODO: Your code goes here
        Pattern p = Pattern.compile(varName + "\\s*=\\s*\\(\\s*(\\d+)\\s*,\\s*(\\d+)\\s*\\)");
        Matcher m = p.matcher(fileContent);
        if (m.find()) {
            int x = Integer.parseInt(m.group(1).trim()); 
            int y = Integer.parseInt(m.group(2).trim()); 
            return new Point(x, y);
        }
        return new Point(0, 0); 
    }

    /**
     * Function to extract the cart lines from the fileContent by reading train line
     * names and their
     * respective stations.
     * 
     * @return List of CartLine instances
     */
    public List<CartLine> getCartLines(String fileContent) {
        List<CartLine> cartLines = new ArrayList<>();
        String[] lines = fileContent.split("\n");
        String currentLineName = null;
        List<Station> currentStations = null;

        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("cart_line_name")) {
                currentLineName = line.split("=")[1].trim().replace("\"", "");
                currentStations = new ArrayList<>();
            } else if (line.startsWith("cart_line_stations")) {
                Pattern p = Pattern.compile("\\s*\\(\\s*(\\d+)\\s*,\\s*(\\d+)\\s*\\)");
                Matcher m = p.matcher(line);
                int index = 1;
                while (m.find()) {
                    int x = Integer.parseInt(m.group(1));
                    int y = Integer.parseInt(m.group(2));
                    String temp = currentLineName + " Station " + String.valueOf(index);
                    currentStations.add(new Station(new Point(x, y), temp));
                    index += 1;
                }
                if (currentLineName != null && currentStations != null) {
                    cartLines.add(new CartLine(currentLineName, currentStations));
                }
            }
        }

        return cartLines;
    }

    /**
     * Function to populate the given instance variables of this class by calling
     * the functions above.
     */
    public void readInput(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            StringBuilder fileContent = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                fileContent.append(line.trim()).append("\n");
            }

            String content = fileContent.toString();
            startPoint = new Station(getPointVar("starting_point", content), "Starting Point");
            destinationPoint = new Station(getPointVar("destination_point", content), "Final Point");
            averageCartSpeed = getDoubleVar("average_cart_speed", content) * (100.0 / 6.0);
            List<CartLine> cartLines = getCartLines(content);
            lines = cartLines;
            numCartLines = lines.size();
            
    

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
