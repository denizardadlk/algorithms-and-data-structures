import java.io.Serializable;
import java.util.List;

public class Task implements Serializable {
    static final long serialVersionUID = 66L;
    private final int taskID;
    private final String description;
    private final int duration;
    private final List<Integer> dependencies;
    private int endtime=0;
    private int starttime=0;

    public Task(int taskID, String description, int duration, List<Integer> dependencies) {
        this.taskID = taskID;
        this.description = description;
        this.duration = duration;
        this.dependencies = dependencies;
    }
    public void setEnd(int endtime) {
        this.endtime = endtime;
        
    }
    public int getEnd() {
        return endtime;
    }
    public void setStart(int starttime) {
        this.starttime = starttime;
        
    }
    public int getStart() {
        return starttime;
    }
    public int getTaskID() {
        return taskID;
    }

    public String getDescription() {
        return description;
    }

    public int getDuration() {
        return duration;
    }

    public List<Integer> getDependencies() {
        return dependencies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return taskID == task.taskID && duration == task.duration && description.equals(task.description) && dependencies.equals(task.dependencies);
    }
}
