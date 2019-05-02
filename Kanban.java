package sample;

import java.io.Serializable;

public class Kanban implements Serializable {
    String name;

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    String priority;

    public Kanban(String name, String date, String description,String priority) {
        this.name = name;
        this.date = date;
        this.description = description;
        this.priority=priority;
    }
    public Kanban()
    {
        this.name="defaultName";
        this.date="defaultDate";
        this.description="defaultDesc";
        this.priority="defaultPriority";
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String date;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        if(priority=="High")
        return "(!) "+name;
        else
            return name;
    }
}
