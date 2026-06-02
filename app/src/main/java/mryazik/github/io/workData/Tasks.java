package mryazik.github.io.workData;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.logging.Level;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Tasks {
    @JsonProperty(value = "groups_id")
    private int groups_id = 0;
    @JsonProperty(value = "tasks_id")
    private int task_id = 0;
    @JsonProperty(value = "name_task")
    private String name_task = "";
    @JsonProperty(value = "description")
    private String description = "";
    @JsonProperty(value = "priority")
    private int priority = 10;

    public Tasks() {};
    public Tasks(int groups_id, int task_id, String name_task, String description, int priority)
    {
        this.groups_id = groups_id;
        this.task_id = task_id;
        this.name_task = name_task;
        this.description = description;
        this.priority = priority;
    }

    @JsonIgnore
    public int getTaskId()
    {
        return this.task_id;
    }

    @JsonIgnore
    public int getGroupsId() {return this.groups_id;}

    @JsonIgnore
    public String getNameTask()
    {
        return this.name_task;
    }

    @JsonIgnore
    public void changeNameTask(String text)
    {
        this.name_task = text;
    }

    @JsonIgnore
    public void changeDescription(String text)
    {
        this.description = text;
    }

    @JsonIgnore
    public void changePriority(int priority)
    {
        if (priority < 1)
        {
            jsonData.logger.log(Level.SEVERE, "Не удалось поменять приоритет задачи, он не может быть меньше 1");
            return;
        }

        this.priority = priority;
    }
}
