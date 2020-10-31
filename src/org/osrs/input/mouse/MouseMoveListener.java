package org.osrs.input.mouse;

public interface MouseMoveListener {
    /**
     * Will be called when the mouse is over the target and will continue to be called with around 8 ms spacing after that.
     * (Only if the mouse is still over the target obviously)
     *
     * You should make the mouse click and such from here.
     * @param mouseJob the mouse job, for easier access without using final variables
     */
    public void onMouseOverTarget(MouseJob mouseJob);

    /**
     * Is called right before the execution stops
     * @param mouseJob The job this callback belongs to. For easier access without using final variables
     */
    public void onFinished(MouseJob mouseJob);
}
