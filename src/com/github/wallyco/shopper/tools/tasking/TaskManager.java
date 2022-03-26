package com.github.wallyco.shopper.tools.tasking;

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
//    private void getNext(){
//        taskQueue.poll();
//        if(taskQueue.peek() != null){
//            resetTask();
//        }else{
//            ScriptManager.getScriptManager().stop();
//        }
//    }

    public boolean add(Task task) {
        //log("Task Manager - add");
        if(!taskQueue.contains(task)) {
            taskQueue.add(task);
            return true;
        }
        return false;
    }

    public void clear(){
        taskQueue.clear();
    }

    private void executeTask(){
        if(!taskQueue.isEmpty()){
            taskQueue.peek().execute();
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

}
