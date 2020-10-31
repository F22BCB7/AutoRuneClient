package org.osrs.input.mouse;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.osrs.api.methods.MethodContext;
import org.osrs.api.methods.MethodDefinition;

/**
 * Base class for all jobs
 */
public abstract class Job extends MethodDefinition {
    protected Runnable runnable;
    protected Future<?> future;
    protected boolean cancelled = false;

    public Job(MethodContext botEnv) {
        super(botEnv);
    }

    /**
     * Starts the job if its not allready started
     */
    public void start(){
        if(future != null && future.isDone()){
        	methods.mouse.mouseHandler.executorService.shutdownNow();
        }
        cancelled = false;
        if(this instanceof MouseJob){
            methods.mouse.mouseHandler.addMouseJobInternally((MouseJob) this);
        }
        onStart();
        future = methods.mouse.mouseHandler.executorService.submit(runnable);
    }

    /**
     * Checks if the job is still executing
     * @return
     */
    public boolean isAlive(){
        return future != null && !future.isDone();
    }

    /**
     * Cancels the keyboard job
     * @return
     */
    public boolean cancel(){
        cancelled = true;
        onCancelled();                                     
        return future == null || future.cancel(true);
    }

    /**
     * Waits until the TextInputJob is done executing.
     */
    public void join(){
        if(future == null){
            return;
        }
        try {
            future.get(5000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    protected abstract void onCancelled();
    protected abstract void onStart();
}
