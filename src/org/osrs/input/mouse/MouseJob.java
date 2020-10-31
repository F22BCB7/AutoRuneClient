package org.osrs.input.mouse;

import java.awt.event.MouseEvent;

import org.osrs.api.methods.MethodContext;
import org.osrs.api.wrappers.MouseListener;

public abstract class MouseJob extends Job {
    protected EventFactory eventFactory;
    protected MouseListener virtualMouse;
    public int virtalMouseX;
    public int virtualMouseY;

    public MouseJob(EventFactory eventFactory, MethodContext botEnvironment) {
        super(botEnvironment);
        this.eventFactory = eventFactory;
        virtalMouseX = getMouse().getX();
        virtualMouseY = getMouse().getY();
        virtualMouse = methods.game.getMouseListener();
    }

    protected void setVirtualMousePos(int x, int y){
        virtalMouseX = x;
        virtualMouseY = y;
    }

    /**
     * Gets the internal bot mouse
     * @return
     */
    protected MouseListener getMouse(){
        return methods.game.getMouseListener();
    }

    /**
     * Dispatchs an event to the game
     * @param mouseEvent
     */
    protected void dispatchEvent(MouseEvent mouseEvent){
        getMouse().sendEvent(mouseEvent);
    }

    
    public  abstract void stop();

    public abstract void doMouseClick(boolean button);

    public abstract void pause();

    public abstract boolean isPaused();

    public abstract void resume();
}
