import java.io.Serializable;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClubFairSetupPlanner implements Serializable {
    static final long serialVersionUID = 88L;

    /**
     * Given a list of Project objects, prints the schedule of each of them.
     * Uses getEarliestSchedule() and printSchedule() methods of the current project
     * to print its schedule.
     * 
     * @param projectList a list of Project objects
     */
    public void printSchedule(List<Project> projectList) {
        // TODO: YOUR CODE HERE
        for (Project project : projectList) {
            project.printSchedule(project.getEarliestSchedule());
        }
    }

    /**
     * Parse the input XML file and return a list of Project objects
     *
     * @param filename the input XML file
     * @return a list of Project objects
     */
    public List<Project> readXML(String filename) {
        List<Project> projectList = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filename));
            document.getDocumentElement().normalize();

            NodeList nodeList = document.getElementsByTagName("Project");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    String name = element.getElementsByTagName("Name").item(0).getTextContent();

                    List<Task> taskList = new ArrayList<>();
                    NodeList taskNodes = element.getElementsByTagName("Task");
                    for (int j = 0; j < taskNodes.getLength(); j++) {
                        Node taskNode = taskNodes.item(j);
                        if (taskNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element taskElement = (Element) taskNode;

                            String taskID = taskElement.getElementsByTagName("TaskID").item(0).getTextContent();
                            String Description = taskElement.getElementsByTagName("Description").item(0)
                                    .getTextContent();
                            String Duration = taskElement.getElementsByTagName("Duration").item(0).getTextContent();

                            List<Integer> dependencies = new ArrayList<>();
                            NodeList dependencyNodes = taskElement.getElementsByTagName("DependsOnTaskID");
                            for (int k = 0; k < dependencyNodes.getLength(); k++) {
                                dependencies.add(Integer.parseInt(dependencyNodes.item(k).getTextContent()));
                            }
                            Task task = new Task(Integer.parseInt(taskID), Description, Integer.parseInt(Duration), dependencies);
                            taskList.add(task);
                        }
                    }

                    Project project = new Project(name,taskList);

                    projectList.add(project);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return projectList;
    }
}
