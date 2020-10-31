package org.osrs.input.mouse;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import org.osrs.api.methods.MethodContext;
import org.osrs.api.wrappers.Client;
import org.osrs.api.wrappers.MouseListener;
import org.osrs.util.Data;
import org.osrs.util.ScriptDef;

public class MouseHoverJob extends MouseJob {
    protected Vector2D velocity = new Vector2D();
    protected Vector2D lastAcceleration = new Vector2D();
    protected MouseMoveListener callback;
    protected MouseTarget target;
    protected boolean finished = false;
    protected boolean paused = false;
    protected boolean drag = false;
    protected List<MouseForceModifier> forceModifiers = new ArrayList<MouseForceModifier>();
    private double speedMultiplier = 1.0D;
    private MTimer timeLimit = null;
    
    public MouseHoverJob(EventFactory eventFactory, MethodContext botEnvironment, final MouseMoveListener callback, final MouseTarget target) {
        super(eventFactory, botEnvironment);
        runnable = new Runnable() {
            public void run() {
                while(!finished){
                    if (timeLimit != null && timeLimit.isDone()) {
                        break;
                    }
                    if(paused){
                        sleep(8, 10);
                        continue;
                    }
                    Client client = botEnvironment.game.getClient();
                    if(client!=null){
                    	if(!botEnvironment.randomHandler.randomDetected){
	                    	ScriptDef script = Data.currentScripts.get(client);
	                    	if(script!=null){
	                    		if(script.isPaused){
	                    			break;
	                    		}
	                    	}
                    	}
                    }
                    MouseListener mouse = getMouse();
                    Point point = target.get();
                    if(point.x < 0 || point.y < 0){
                        finished = true;
                        break;
                    }
                    if(target.isOver(mouse.getX(), mouse.getY())){
                        callback.onMouseOverTarget(MouseHoverJob.this);
                    }
                    if(finished){
                        break;
                    }
                    double deltaTime = random(8D, 10D)/1000D;
                    applyForces(deltaTime);
                    moveMouse(deltaTime);
                    int millis = (int) (deltaTime * 1000);
                    sleep(millis);
                }
                callback.onFinished(MouseHoverJob.this);
                methods.mouse.mouseHandler.removeMouseJobInternally(MouseHoverJob.this);
            }
        };
        this.callback = callback;
        this.target = target;
        forceModifiers.add(new MouseForceModifier() {
            // TARGET GRAVITY
            public Vector2D applyForce(double deltaTime, MouseJob mouseJob) {
                Vector2D force = new Vector2D();

                Vector2D toTarget = new Vector2D();
                Point pTarget = MouseHoverJob.this.target.get();
                MouseListener mouse = virtualMouse;
                toTarget.xUnits = pTarget.x-mouse.getX();
                toTarget.yUnits = pTarget.y-mouse.getY();
                if(toTarget.xUnits == 0 && toTarget.yUnits == 0){
                    return null;
                }

                double alpha = toTarget.getAngle();
                double acc = random(1500, 2000)*speedMultiplier;
                force.xUnits = Math.cos(alpha)*acc;
                force.yUnits = Math.sin(alpha)*acc;

                return force;
            }
        });

        forceModifiers.add(new MouseForceModifier() {
            // "friction"
            public Vector2D applyForce(double deltaTime, MouseJob mouseJob) {
                return velocity.multiply(-1);
            }
        });

        forceModifiers.add(new MouseForceModifier() {

            private int offset = random(300, 500);
            private double offsetAngle = -1;
            // Offset
            public Vector2D applyForce(double deltaTime, MouseJob mouseJob) {
                if(offsetAngle == -1){
                    offsetAngle = random(-Math.PI, Math.PI);
                }
                Vector2D toTarget = new Vector2D();
                Point pTarget = MouseHoverJob.this.target.get();
                MouseListener mouse = virtualMouse;
                toTarget.xUnits = pTarget.x-mouse.getX();
                toTarget.yUnits = pTarget.y-mouse.getY();
                if(offset > 0 && toTarget.getLength() > random(25, 55)){
                    Vector2D force = new Vector2D();
                    force.xUnits = Math.cos(offsetAngle)*offset;
                    force.yUnits = Math.sin(offsetAngle)*offset;
                    offset -= random(0, 6);
                    return force;
                }
                return null;
            }
        });

        forceModifiers.add(new MouseForceModifier() {
            // correction when close
            public Vector2D applyForce(double deltaTime, MouseJob mouseJob) {
                Vector2D toTarget = new Vector2D();
                Point pTarget = MouseHoverJob.this.target.get();
                MouseListener mouse = virtualMouse;
                toTarget.xUnits = pTarget.x-mouse.getX();
                toTarget.yUnits = pTarget.y-mouse.getY();
                double length = toTarget.getLength();
                if(length < random(75, 125)){
                    Vector2D force = new Vector2D();

                    double speed = velocity.getLength();
                    double rh = speed*speed;
                    double s = toTarget.xUnits * toTarget.xUnits + toTarget.yUnits * toTarget.yUnits;
                    if(s == 0){
                        return null;
                    }
                    double f = rh/ s;
                    f = Math.sqrt(f);
                    Vector2D adjustedToTarget = toTarget.multiply(f);

                    force.xUnits = (adjustedToTarget.xUnits-velocity.xUnits)/(deltaTime);
                    force.yUnits = (adjustedToTarget.yUnits-velocity.yUnits)/(deltaTime);

                    double v = 4D / length;
                    if(v < 1D){
                        force = force.multiply(v);
                    }
                    if(length < 10){
                        force = force.multiply(0.5D);
                    }
                    return force;
                }
                return null;
            }
        });

        forceModifiers.add(new MouseForceModifier() {
            // correction when close
            public Vector2D applyForce(double deltaTime, MouseJob mouseJob) {
                Point pTarget = MouseHoverJob.this.target.get();
                MouseListener mouse = virtualMouse;
                int mouseX = mouse.getX();
                int mouseY = mouse.getY();
                //if(mouseX > pTarget.x-2 && mouseX < pTarget.x+2 && mouseY > pTarget.y-2 && mouseY < pTarget.y+2){
                if(mouseX == pTarget.x && mouseY == pTarget.y){
                    velocity.xUnits = 0;
                    velocity.yUnits = 0;
                }
                return null;
            }
        });
    }

    protected void applyForces(double deltaTime){
        Vector2D force = new Vector2D();
        for(MouseForceModifier modifier: forceModifiers){
            Vector2D f = modifier.applyForce(deltaTime, this);
            if(f == null){
                continue;
            }
            force.add(f);
        }

        if(Double.isNaN(force.xUnits) || Double.isNaN(force.yUnits)){
            return;
        }
        lastAcceleration = force;
        velocity.add(force.multiply(deltaTime));
    }

    protected void moveMouse(double deltaTime){
        MouseListener mouse = getMouse();
        int mouseX = mouse.getX();
        int mouseY = mouse.getY();

        Vector2D deltaPosition = velocity.multiply(deltaTime);
        int newX = mouseX + (int) deltaPosition.xUnits;
        int newY = mouseY + (int) deltaPosition.yUnits;
        if(newX == virtualMouse.getX() && newY == virtualMouse.getY()){
            return;
        }
        setVirtualMousePos(newX, newY); 

        if(!paused && !finished){

            MouseEvent mouseEvent;
            if (drag) {
                mouseEvent = eventFactory.createMouseDragged(newX, newY, true);
            } else {
                mouseEvent = eventFactory.createMoveMouse(newX, newY);
            }
            dispatchEvent(mouseEvent);
        }
    }

    /**
     * Makes the bot click at the current mouse position
     * @param button
     */
    public void doMouseClick(boolean button){
        boolean wasPaused = paused;
        if(wasPaused){
            paused = true;
        }
        int mouseX = virtualMouse.getX();
        int mouseY = virtualMouse.getY();
        MouseEvent mouseEvent = eventFactory.createMousePress(mouseX, mouseY, button);
        dispatchEvent(mouseEvent);
        sleep(50, 100);
        mouseEvent = eventFactory.createMouseRelease(mouseX, mouseY, button);
        dispatchEvent(mouseEvent);   
        mouseEvent = eventFactory.createMouseClicked(mouseX, mouseY, button);
        dispatchEvent(mouseEvent);
        if(wasPaused){
            paused = false;
        }    
    }

    protected void onCancelled() {
        finished = true;
        callback.onFinished(this);
        methods.mouse.mouseHandler.resumeAllJobs();
    }

    protected void onStart() {
    	methods.mouse.mouseHandler.pauseAllJobs();
        finished = false;
        paused = false;
    }

    public synchronized void pause(){
        paused = true;
    }

    public synchronized void resume(){
        paused = false;
    }

    public void stop() {
        finished = true;
        methods.mouse.mouseHandler.removeMouseJobInternally(this);
    }

    public synchronized MouseTarget getTarget() {
        return target;
    }

    public synchronized boolean isPaused(){
        return paused;
    }

    public synchronized void setDrag(boolean drag) {
        this.drag = drag;
    }

    public synchronized void setTimeLimit(MTimer timeLimit) {
        this.timeLimit = timeLimit;
    }
    
    /**
     * 1.0 is normal speed
     * @return
     */
    public synchronized double getSpeedMultiplier() {
        return speedMultiplier;
    }

    /**
     * 1.0 is normal speed
     * @param speedMultiplier
     */
    public synchronized void setSpeedMultiplier(double speedMultiplier) {
        this.speedMultiplier = speedMultiplier;
    }
}
