package mryazik.github.io.workData;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.logging.Level;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Ideas {
    @JsonProperty(value = "id")
    private int id = 0;
    @JsonProperty(value = "title")
    private String title = "";
    @JsonProperty(value = "note")
    private String note = "";
    @JsonProperty(value = "isComplited")
    private Boolean isComplited = false;
    @JsonProperty(value = "priority")
    private int priority = 10;

    public Ideas() {}
    public Ideas(int id, String title, String note, int priority)
    {
        this.id = id;
        this.title = title;
        this.note = note;
        this.priority = priority;
    }

    @JsonIgnore
    public String getTitle() {return this.title;}
    @JsonIgnore
    public String getNote() {return this.note;}
    @JsonIgnore
    public boolean getStatus()
    {
        return this.isComplited;
    }
    public int getId()
    {
        return this.id;
    }
    public int getPriority()
    {
        return this.priority;
    }
    public void changePriority(int new_priority)
    {
        if (new_priority < 1)
        {
            jsonData.logger.log(Level.SEVERE, "Не удалось поменять приоритет идеи, он не может быть меньше нуля");
            return;
        }

        this.priority = new_priority;
    }
}
