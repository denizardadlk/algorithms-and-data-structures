import java.io.Serializable;
import java.util.*;

public class Project implements Serializable {
    static final long serialVersionUID = 33L;
    private final String name;
    private final List<Task> tasks;

    public Project(String name, List<Task> tasks) {
        this.name = name;
        this.tasks = tasks;
    }

    /**
     * @return the total duration of the project in days
     */
    public int getProjectDuration() {
        int projectDuration = 0;
        int[] schedule = this.getEarliestSchedule();
        projectDuration = tasks.get(schedule.length - 1).getDuration() + schedule[schedule.length - 1];
        // TODO: YOUR CODE HERE

        return projectDuration;
    }

    /**
     * Schedule all tasks within this project such that they will be completed as
     * early as possible.
     *
     * @return An integer array consisting of the earliest start days for each task.
     */
    public int[] earliestrecursion(int index , int[] earliestendtime){
        int[] temp = earliestendtime;
        Task task = tasks.get(index);
        int start = 0;
        for (int dependency : task.getDependencies()) {
            if(temp[dependency]!=-1){
                start = Math.max(start, tasks.get(dependency).getEnd());
            }
            else{
                earliestrecursion(dependency, temp);
                start = Math.max(start, tasks.get(dependency).getEnd());
            }
        }
        task.setStart(start);
        task.setEnd(start + task.getDuration());
        temp[index] = task.getStart();
        return temp;
    }
    public int[] getEarliestSchedule() {

        // TODO: YOUR CODE HERE
        int[] earliestendtime = new int[tasks.size()];
        for (int index = 0; index < earliestendtime.length; index++) {
            earliestendtime[index] = -1;
        }
        for (int i = earliestendtime.length-1; i >= 0; i--) {
            earliestendtime = earliestrecursion(i, earliestendtime);
        }
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            indices.add(i);
        }

        indices.sort(Comparator.comparingInt(i -> tasks.get(i).getEnd()));

        /* int[] sortedEarliestEndTime = new int[tasks.size()];
        for (int i = 0; i < indices.size(); i++) {
            sortedEarliestEndTime[i] = earliestendtime[indices.get(i)];
        } */
        return earliestendtime;

    }

    public static void printlnDash(int limit, char symbol) {
        for (int i = 0; i < limit; i++)
            System.out.print(symbol);
        System.out.println();
    }

    /**
     * Some free code here. YAAAY!
     */
    public void printSchedule(int[] schedule) {
        int limit = 65;
        char symbol = '-';
        printlnDash(limit, symbol);
        System.out.println(String.format("Project name: %s", name));
        printlnDash(limit, symbol);

        // Print header
        System.out.println(String.format("%-10s%-45s%-7s%-5s", "Task ID", "Description", "Start", "End"));
        printlnDash(limit, symbol);
        for (int i = 0; i < schedule.length; i++) {
            Task t = tasks.get(i);
            System.out.println(String.format("%-10d%-45s%-7d%-5d", i, t.getDescription(), schedule[i],
                    schedule[i] + t.getDuration()));
        }
        printlnDash(limit, symbol);
        System.out.println(String.format("Project will be completed in %d days.",
                tasks.get(schedule.length - 1).getDuration() + schedule[schedule.length - 1]));
        printlnDash(limit, symbol);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Project project = (Project) o;

        int equal = 0;

        for (Task otherTask : ((Project) o).tasks) {
            if (tasks.stream().anyMatch(t -> t.equals(otherTask))) {
                equal++;
            }
        }

        return name.equals(project.name) && equal == tasks.size();
    }

}
