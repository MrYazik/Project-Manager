package mryazik.github.io.workData;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Projects {
    @JsonProperty(value = "id")
    private int id = 0;
    @JsonProperty(value = "title")
    private String title = "";
    @JsonProperty(value = "readme")
    private String readme = "";
    @JsonProperty(value = "ideas_id")
    private List<Integer> ideas_id = new ArrayList<>();


    public Projects() {};
    public Projects(int id, String title, String readme) // создаёт проект конструктором
    {
        this.id = id;
        this.title = title;
        this.readme = readme;
    }
    public void addIdeas(List<Integer> ideas_id)
    {
        this.ideas_id.addAll(ideas_id);
    }

    @JsonIgnore
    public String getReadme()
    {
        return this.readme;
    }
    public void changeReadme(String readme) {this.readme = readme;}
    @JsonIgnore
    public String getTitle() {return  this.title;}

    public boolean isProjectContainsThisNote(int id)
    {
        AtomicBoolean status = new AtomicBoolean(false);

        ideas_id.forEach((id_) -> {
            if (id == id_) {
                status.set(true);
            }
        });

        return status.get();
    }
}
