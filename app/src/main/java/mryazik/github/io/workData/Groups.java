package mryazik.github.io.workData;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Groups {
    @JsonProperty(value = "group_id")
    private int group_id = 0;
    @JsonProperty(value = "idea_id")
    private int idea_id = 0;
    @JsonProperty(value = "title")
    private String title = "";
    @JsonProperty(value = "tasks_id")
    private List<Integer> tasks_id = new ArrayList<>();
    @JsonProperty(value = "priority")
    private int priority = 10;

    public Groups() {};
    public Groups(int idea_id, String title, int priority) {
        this.idea_id = idea_id;
        this.title = title;
        this.priority = priority;

        Random random = new Random();
        jsonData jsonObject = workJsonFile.getJsonInfo();
        // Генерируем id группы, конечно его придётся проверять на повторки, потом когда нибудь исправлю для оптимизации
        int random_id = random.nextInt();

        jsonObject.getGroups().forEach(group -> {
            if (random_id == group.getGroupId())
            {
                group_id = random.nextInt();
                // Небольшой шанс остаётся
            }
        });
    }

    @JsonIgnore
    public int getIdeaId()
    {
        return this.idea_id;
    }

    @JsonIgnore
    public int getGroupId()
    {
        return group_id;
    }

    @JsonIgnore
    public String getTitle()
    {
        return this.title;
    }

    public void addTasks(List<Integer> tasks_id)
    {
       this.tasks_id.addAll(tasks_id);
    }

    public void changePriority(int priority)
    {
        if (priority < 1)
        {
            jsonData.logger.log(Level.SEVERE, "Не удалось поменять приоритет группы, он не может быть меньше 1");
            return;
        }

        this.priority = priority;
    }
}
