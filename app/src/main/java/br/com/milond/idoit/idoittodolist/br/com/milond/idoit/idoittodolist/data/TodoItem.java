package br.com.milond.idoit.idoittodolist.br.com.milond.idoit.idoittodolist.data;

/**
 * Created by Carlos Leao (carloseduardogu@gmail.com) on 27/04/2015.
 */
public class TodoItem {

    public void setId(long id) {
        this.id = id;
    }

    public void setCreationDate(Long creationDate) {
        this.creationDate = creationDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public long getId() {
        return id;
    }

    public Long getCreationDate() {
        return creationDate;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getComplete() {
        return complete;
    }

    long id;
    Long creationDate;
    String description;
    Boolean complete;

    public TodoItem() {

    }

    TodoItem(Long date, String title) {
        this.creationDate = date;
        this.description = title;
    }

}
