package org.osrs.input.mouse;

import java.awt.Point;

public interface MouseTarget {
    /**
     * Should return a point within the target, the mouse will be moving towards this point, but it is not 100% sure that it will hit this exact pixel.
     *
     * @return Point: screen position of the target
     */
    public Point get();

    /**
     * Is called to check if the mouse is over the target.
     *
     * @param posX the screenX position to compare with
     * @param posY the screenY position to compare with
     * @return true if the point is over the target, false if not.
     */
    public boolean isOver(int posX, int posY);
}

/*
public MouseTarget getTarget() {
if (getModel() != null) {
    return new MouseTarget() {
        public Point get() {
            return getCenter();
        }

        public boolean isOver(int posX, int posY) {
            return getModel().isPointOver(posX, posY);
        }
    };
} else {
    return new MouseTarget() {
        public Point get() {
            return getCenter();
        }

        public boolean isOver(int posX, int posY) {
            Point p = getScreenPos();
            return new Rectangle(p.x - 2, p.y - 2, 4, 4).contains(posX, posY);
        }
    };
}
}*/