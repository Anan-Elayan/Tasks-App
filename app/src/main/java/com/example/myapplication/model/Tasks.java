package com.example.myapplication.model;



import java.io.Serializable;

public class Tasks implements Serializable {
    
    private String taskTitle;
    private String data;
    private String time;
    private String date;
    private  int tasksId=0;
    private boolean isDone ;

    public Tasks(int id, String taskTitle, String data, String time, String date) {
        this.taskTitle = taskTitle;
        this.data = data;
        this.time = time;
        this.date = date;
        tasksId = id;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTasksId() {
        return tasksId;
    }

    public void setTasksId(int tasksId) {
        this.tasksId = tasksId;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    // TODO: 11/10/2023 add toString method


    @Override
    public String toString() {
        return   "Title : " +taskTitle + '\n' + "Date :" + date + '\n' +"Time : "+time+'\n' + "Data : "+ data+'\n' ;
    }

}
