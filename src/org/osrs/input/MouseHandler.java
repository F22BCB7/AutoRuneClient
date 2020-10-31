package org.osrs.input;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.osrs.api.methods.MethodContext;
import org.osrs.api.methods.MethodDefinition;
import org.osrs.api.wrappers.MouseListener;
import org.osrs.input.mouse.EventFactory;
import org.osrs.input.mouse.MTimer;
import org.osrs.input.mouse.MouseHoverJob;
import org.osrs.input.mouse.MouseJob;
import org.osrs.input.mouse.MouseMoveListener;
import org.osrs.input.mouse.MouseTarget;
import org.osrs.input.mouse.Targetable;

public class MouseHandler extends MethodDefinition{
	public MouseHandler(MethodContext context){
		super(context);
	}
    public List<MouseJob> mouseJobs = Collections.synchronizedList(new ArrayList<MouseJob>());
    private Double defaultSpeed = 1.0D;
    public ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * Creates a MouseHoverJob with the given callback and target
     * @param callback The callback
     * @param target The target
     * @return The created mouse job, you have to start it manually
     */
    public MouseHoverJob createMouseHoverJob(MouseMoveListener callback, MouseTarget target){
        MouseHoverJob job = new MouseHoverJob(new EventFactory(methods), methods, callback, target);
        job.setSpeedMultiplier(defaultSpeed);
        return job;
    }

    /**
     * Creates a MouseHoverJob with the given callback and target 
     * @param callback The callback
     * @param targetable The target
     * @return The created mouse job, you have to start it manually
     */
    public MouseHoverJob createMouseHoverJob(MouseMoveListener callback, Targetable targetable){
        return createMouseHoverJob(callback, targetable.getTarget());
    }

    /**
     * Creates a MouseHoverJob with the given callback and target
     * @param callback The callback
     * @param target The target
     * @param runTime MTimer containing how long the job should run
     * @return The created mouse job, you have to start it manually
     */
    public MouseHoverJob createMouseHoverJob(MouseMoveListener callback, MouseTarget target, MTimer runTime){
        MouseHoverJob job = new MouseHoverJob(new EventFactory(methods), methods, callback, target);
        job.setTimeLimit(runTime);
        job.setSpeedMultiplier(defaultSpeed);
        return job;
    }

    /**
     * Creates a MouseHoverJob with the given callback and target
     * @param callback The callback
     * @param targetable The target
     * @param runTime MTimer containing how long the job should run
     * @return The created mouse job, you have to start it manually
     */
    public MouseHoverJob createMouseHoverJob(MouseMoveListener callback, Targetable targetable, MTimer runTime){
        return createMouseHoverJob(callback, targetable.getTarget(), runTime);
    }

    /**
     * Moves the mouse to the screen position and clicks the mouse if told so.
     * @param x the x screen position
     * @param y the y screen position
     * @param click Boolean, if this is true the bot will click after reaching the target.
     * @param button true =  left mouse button, false = right mouse button
     */
    public synchronized void moveMouse(final int x, final int y, final boolean click, final boolean button) {
        MouseTarget mouseTarget = new MouseTarget() {
            public Point get() {
                return new Point(x, y);
            }

            public boolean isOver(int posX, int posY) {
                return posX > x-1 && posX < x+1 && posY > y-1 && posY < y+1;
            }
        };

        MouseHoverJob mouseHoverJob = createMouseHoverJob(new MouseMoveListener() {
            int count = 0;
            public void onMouseOverTarget(MouseJob mouseJob) {
                if(!click){
                    mouseJob.stop();
                    return;
                }
				count++;
				if(count > random(3, 16)){
				    mouseJob.stop();
				    mouseJob.doMouseClick(button);
				    return;
				}
            }

            public void onFinished(MouseJob mouseJob) {
            }
        }, mouseTarget);
        mouseHoverJob.start();
        mouseHoverJob.join();
    }

    /**
     * Moves the mouse to the screen position given.
     * @param x the x screen position
     * @param y the y screen position
     */
    public void moveMouse(final int x, final int y) {
        moveMouse(x, y, false, false);
    }

    /**
     * Moves the mouse to the screen position and clicks the mouse btton given.
     * @param p The screen point to move to
     * @param button true =  left mouse button, false = right mouse button
     */
    public void moveMouse(Point p, final boolean button) {
        moveMouse(p.x, p.y, true, button);
    }

    /**
     * Moves the mouse to the screen position and clicks the mouse btton given.
     * @param p The screen point to move to
     */
    public void moveMouse(Point p) {
        moveMouse(p.x, p.y);
    }

    /**
     * Moves the mouse to a point inside a rectangle
     * @param r The rectangle to move the mouse to
     */
    public void moveMouse(Rectangle r) {
        moveMouse(r.x + random(0, r.width), r.y + random(0, r.height));
    }
    /**
     * VERY Human like method - great for anti bans!
     * This will move the mouse around the screen at a random distance between
     * 1 and maxDistance, but will sometimes move it more than one, like a human
     * would, resulting in cool effects like cursor circling and more.
     *
     * @param maxDistance
     * @return true if it is going to call on itself again, false otherwise
     *         (returns false to you every time)
     */
    public boolean moveMouseRandomly(int maxDistance) {
        if (maxDistance == 0) {
            return false;
        }
        maxDistance = random(1, maxDistance);
        Point p = new Point(getRandomMouseX(maxDistance), getRandomMouseY(maxDistance));
        if (p.x < 1 || p.y < 1) {
            p.x = p.y = 1;
        }
        moveMouse(p.x, p.y);
        return random(0, 2) != 0 && moveMouseRandomly(maxDistance / 2);
    }

    /**
     * Gives a X position on the screen within the maxDistance.
     *
     * @param maxDistance the maximum distance the cursor will move on the X axis
     * @return A random int that represents a X coordinate for the
     *         moveMouseRandomly method.
     */
    private int getRandomMouseX(int maxDistance) {
        Point p = getMousePos();
        if (random(0, 2) == 0) {
            return p.x - random(0, p.x < maxDistance ? p.x : maxDistance);
        }
		return p.x + random(1, (762 - p.x < maxDistance) ? 762 - p.x : maxDistance);
    }

    /**
     * Gives a Y position on the screen within the maxDistance.
     *
     * @param maxDistance the maximum distance the cursor will move on the Y axis
     * @return A random int that represents a Y coordinate for the
     *         moveMouseRandomly method.
     */
    private int getRandomMouseY(int maxDistance) {
        Point p = getMousePos();
        if (random(0, 2) == 0) {
            return p.y - random(0, p.y < maxDistance ? p.y : maxDistance);
        }
		return p.y + random(1, (500 - p.y < maxDistance) ? 500 - p.y : maxDistance);
    }

    /**
     * Gets the mouse position
     *
     * @return Point with coords.
     */
    public Point getMousePos() {
    	MouseListener mouse = methods.game.getMouseListener();
        return new Point(mouse.getX(), mouse.getY());
    }

    /**
     * Makes the mouse click the given button.
     * @param button
     */
    public void clickMouse(boolean button) {
        EventFactory eventFactory = new EventFactory(methods);
        Point point = getMousePos();
        MouseEvent mouseEvent = eventFactory.createMousePress(point.x, point.y, button);
        dispatchEvent(mouseEvent);
		for(int i=0;i<20;++i){
			if(isMousePressed())
				break;
			sleep(new Random().nextInt(10));
		}
		sleep(random(100, 500));
        mouseEvent = eventFactory.createMouseRelease(point.x, point.y, button);
        dispatchEvent(mouseEvent);
		for(int i=0;i<20;++i){
			if(!isMousePressed())
				break;
			sleep(new Random().nextInt(10));
		}

        mouseEvent = eventFactory.createMouseClicked(point.x, point.y, button);
        dispatchEvent(mouseEvent);
    }

    /**
     * Moves the mouse to the start point, presses the mouse, moves to the destination and releases.
     * @param startPoint
     * @param destPoint
     */
    public void clickAndDragMouse(Point startPoint, final Point destPoint) {
        EventFactory eventFactory = new EventFactory(methods);
        MouseTarget mouseTarget = new MouseTarget() {
            public Point get() {
                return new Point(destPoint.x, destPoint.y);
            }

            public boolean isOver(int posX, int posY) {
                return posX > destPoint.x-1 && posX < destPoint.x+1 && posY > destPoint.y-1 && posY < destPoint.y+1;
            }
        };

        if (!getMousePos().equals(startPoint)) {
            moveMouse(startPoint);
        }
		sleep(random(100, 300));
		
        MouseEvent mouseEvent = eventFactory.createMousePress(startPoint.x, startPoint.y, true);
        dispatchEvent(mouseEvent);
        sleep(50, 80);

        MouseHoverJob mouseHoverJob = createMouseHoverJob(new MouseMoveListener() {
            int count = 0;
            public void onMouseOverTarget(MouseJob mouseJob) {
                count++;
                if(count > random(3, 16)){
                    mouseJob.stop();
                    return;
                }
            }

            public void onFinished(MouseJob mouseJob) {
            }
        }, mouseTarget);
        mouseHoverJob.setDrag(true);
        mouseHoverJob.start();
        mouseHoverJob.join();

		sleep(random(100, 300));
        mouseEvent = eventFactory.createMouseRelease(destPoint.x, destPoint.y, true);
        dispatchEvent(mouseEvent);

    }

    /**
     * @return Returns true if the mouse is being held down by either the bot or the user.
     */
    public boolean isMousePressed() {
        return methods.game.getMouseListener().isPressed() || methods.game.getMouseListener().isRealPressed();
    }

    private void dispatchEvent(MouseEvent mouseEvent) {
        getMouse().sendEvent(mouseEvent);
    }

    public synchronized void pauseAllJobs(){
        for(MouseJob mouseJob: mouseJobs){
            if(mouseJob.isAlive()){
                mouseJob.pause();
            }
        }
    }

    public synchronized  void resumeAllJobs(){
        for(MouseJob mouseJob: mouseJobs){
            if(mouseJob.isPaused()){
                mouseJob.resume();
            }
        }
    }

    public synchronized void stopAllJobs() {
        for(MouseJob mouseJob: mouseJobs){
            if (mouseJob.isAlive()) {
                mouseJob.cancel();
            }
        }
    }

    public boolean isMouseActive() {
        for(MouseJob mouseJob: mouseJobs){
            if(mouseJob.isAlive() && !mouseJob.isPaused()){
                return true;
            }
        }
        return false;
    }

    /**
     * Internal method, don't use.
     * @param mouseJob
     */
    public void removeMouseJobInternally(MouseJob mouseJob) {
        mouseJobs.remove(mouseJob);
    }

    /**
     * Internal method, don't use.
     * @param mouseJob
     */
    public void addMouseJobInternally(MouseJob mouseJob) {
        mouseJobs.add(mouseJob);
    }

    /**
     * Gets the default mouse speed.
     * 1 is normal.
     * 2 is double acceleration
     * @return
     */
    public Double getDefaultSpeed() {
        return defaultSpeed;
    }

    /**
     * Sets the default mouse speed.
     * 1 is normal.
     * 2 is double acceleration
     * @param defaultSpeed
     */
    public void setDefaultSpeed(Double defaultSpeed) {
        this.defaultSpeed = defaultSpeed;
    }
    /**
     * Gets the internal bot mouse
     * @return
     */
    protected MouseListener getMouse(){
        return methods.game.getMouseListener();
    }
}
