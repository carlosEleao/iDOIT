package br.com.milond.idoit.idoittodolist.br.com.milond.idoit.idoittodolist.data;

import java.util.Date;

/**
 * Created by Carlos Leao (carloseduardogu@gmail.com) on 27/04/2015.
 */
public class TodoItem {

    long id;
    Date creationDate;
    Date todoDate;
    Date completeDate;
    String description;
    Boolean complete;

    public TodoItem() {

    }

    TodoItem(String description, Date creationDate, Date todoDate, Date completeDate, boolean complete) {
        this.description = description;
        this.creationDate = creationDate;
        this.todoDate = todoDate;
        this.completeDate = completeDate;
        this.complete = complete;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getTodoDate() {
        return todoDate;
    }

    public void setTodoDate(Date todoDate) {
        this.todoDate = todoDate;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

}
