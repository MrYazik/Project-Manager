package mryazik.github.io.workData;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.logging.Level;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Ideas {
    private int id = 0;
    private String title = "";
    private String note = "";
    private Boolean isComplited = false;
    private int priority = 10;

    public Ideas(int id, String title, String note, int priority)
    {
        this.id = id;
        this.title = title;
        this.note = note;
        this.priority = priority;
    }

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
