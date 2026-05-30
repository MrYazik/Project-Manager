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

    @JsonIgnore
    private int count = 0;
    // И за того что еть геттер, нет необходимости импользовать jsonProperty
    @JsonIgnore
    private int count_idea = 0;

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

    public int getCountProjects()
    {
        this.count++;
        return this.count;
    }

    public int getCountIdeas()
    {
        this.count_idea++;
        return count_idea;
    }

    public List<Projects> getProjects()
    {
        return this.projects;
    }
    public List<Ideas> getIdeas()
    {
        return this.ideas;
    }

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
