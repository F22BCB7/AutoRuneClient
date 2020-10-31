package org.osrs.input.mouse;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class Vector2D {
    public double xUnits;
    public double yUnits;

    public void draw(Graphics2D g, int posX, int posY) {
        Stroke stroke = g.getStroke();
        g.setStroke(new BasicStroke(2));
        g.drawLine(posX, posY, posX + (int) xUnits, posY + (int) yUnits);
        g.setStroke(stroke);
    }

    public Vector2D sum(Vector2D vector) {
        Vector2D out = new Vector2D();
        out.xUnits = xUnits + vector.xUnits;
        out.yUnits = xUnits + vector.yUnits;
        return out;
    }

    public void add(Vector2D vector) {
        xUnits += vector.xUnits;
        yUnits += vector.yUnits;
    }

    public Vector2D multiply(double factor) {
        Vector2D out = new Vector2D();
        out.xUnits = xUnits * factor;
        out.yUnits = yUnits * factor;
        return out;
    }

    public double getLength() {
        return Math.sqrt(xUnits * xUnits + yUnits * yUnits);
    }

    public double getAngle() {
        return Math.atan2(yUnits, xUnits);
    }
}
