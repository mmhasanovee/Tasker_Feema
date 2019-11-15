package xyz.mmhasanovee.taskerfeema.Model;

public class Tasks {

    private String task_id,task_name,task_status,reason;

    public Tasks(){}

    public Tasks(String task_id,String task_name,String task_status, String reason){

        this.task_id=task_id;
        this.task_name=task_name;
        this.task_status=task_status;
        this.reason=reason;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getTask_status() {
        return task_status;
    }

    public void setTask_status(String task_status) {
        this.task_status = task_status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
