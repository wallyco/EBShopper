package com.github.wallyco.shopper.tools.tasking;

import com.epicbot.api.shared.util.time.Time;
import com.github.wallyco.shopper.tools.item.Memory;

import java.util.LinkedList;
import java.util.Queue;

public class TaskManager {

    private static TaskManager tm = null;
    private Queue<Task> taskQueue = new LinkedList<>();

    private TaskManager() {
    }

    public static TaskManager getInstance(){
        if(tm == null){
            tm = new TaskManager();
        }
        return tm;
    }

    public void execute(){
        executeTask();
//        if(loopcounter > 3) {
//            log("Task Manager - Executing" +
//                    "\n" + taskQueue);
//            loopcounter = 0;
//        }
        //TODO Make a levelmanger
//        if(levelManager.continueLeveling()){
//            executeTask();
//        }else{
//            getNext();
//        }
//        loopcounter++;
    }
    //TODO Probably dont need this
    private void getNext(){
        taskQueue.poll();
    }

    public boolean add(Task task) {
        if(!taskQueue.contains(task)) {
            Time.sleep(100, () -> taskQueue.add(task));
            return true;
        }
        return false;
    }

    public void clear(){
        taskQueue.clear();
    }

    private void executeTask(){
        if(!taskQueue.isEmpty()){
            if(!taskQueue.peek().execute()){
                getNext();
            }
        }
    }

    public void insertAtHeadCopy(Task task) {
        Queue<Task> copy = new LinkedList<>();
        if(!taskQueue.contains(task)) {
            //log("Task Manager - insertAtHeadCopy");
            for(Task t : taskQueue) {
                copy.add(t);
            }
            clear();
            this.add(task);
            for(Task t : copy) {
                taskQueue.add(t);
            }
            copy.clear();
        }
    }

    @Override
    public String toString() {
        return "TaskManager{" +
                "taskQueue=" + taskQueue +
                '}';
    }

    public String getCurrentTask(){
        if(!taskQueue.isEmpty())
            return taskQueue.peek().toString();
        else
            return "None";
    }
}
