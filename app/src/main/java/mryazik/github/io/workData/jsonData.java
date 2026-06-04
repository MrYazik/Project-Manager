package mryazik.github.io.workData;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class jsonData {
    protected static Logger logger = Logger.getLogger(jsonData.class.getName());

    @JsonProperty(value = "projects")
    private List<Projects> projects = new ArrayList<>();
    @JsonProperty(value = "ideas")
    private List<Ideas> ideas = new ArrayList<>();
    @JsonProperty(value = "groups")
    private List<Groups> groups = new ArrayList<>();
    @JsonProperty(value = "tasks")
    private List<Tasks> tasks = new ArrayList<>();

    @JsonProperty(value = "count_projects")
    private int count = 0;
    @JsonProperty(value = "count_ideas")
    private int count_idea = 0;
    @JsonProperty(value = "count_groups")
    private int count_groups = 0;
    @JsonProperty(value = "count_tasks")
    private int count_task = 0;

    public void addProjects(List<Projects> projects)
    {
        this.projects.addAll(projects);
    }
    public void addIdeas(List<Ideas> ideas)
    {
        this.ideas.addAll(ideas);
    }
    public void addGroups(List<Groups> groups)
    {
        this.groups.addAll(groups);
    }
    public void addTask(List<Tasks> tasks)
    {
        this.tasks.addAll(tasks);
    }

    public void deleteProject(Projects project)
    {
        projects.remove(project);
    }
    public void deleteIdea(Ideas idea)
    {
// 1. Находим группу, которую нужно удалить
        groups.stream()
                .filter(group -> group.getIdeaId() == idea.getId())
                .findFirst()
                .ifPresent(groupToDelete -> {
                    // 2. Удаляем все задачи, связанные с этой найденной группой
                    tasks.removeIf(task -> task.getGroupsId() == groupToDelete.getGroupId());
                });

// 3. Теперь безопасно удаляем саму группу (или группы) из списка
        groups.removeIf(group -> group.getIdeaId() == idea.getId());

        // Удаляем саму идею
        ideas.remove(idea);
    }
    public void deleteGroup(Groups group)
    {
        // Безопасно удаляем все задачи, связанные с этой группой
        tasks.removeIf(task -> task.getGroupsId() == group.getGroupId());

        // Удаляем саму группу
        groups.remove(group);
    }
    public void deleteTask(Tasks task)
    {
        tasks.remove(task);
    }

    @JsonIgnore
    public int getCountProjects()
    {
        this.count++;
        return this.count;
    }
    @JsonIgnore
    public int getCountIdeas()
    {
        this.count_idea++;
        return count_idea;
    }
    @JsonIgnore
    public int getCountTasks()
    {
        this.count_task++;
        return count_task;
    }
    @JsonIgnore
    public int getCountGroups()
    {
        this.count_groups++;
        return count_groups;
    }

    public List<Projects> getProjects()
    {
        return this.projects;
    }
    public List<Ideas> getIdeas()
    {
        return this.ideas;
    }
    public List<Groups> getGroups() {return this.groups;}
    public List<Tasks> getTasks() {return this.tasks;}

    public static String outJsonToString(jsonData data)
    {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String outMapper = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);

            logger.log(Level.INFO, outMapper);
            return outMapper;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
