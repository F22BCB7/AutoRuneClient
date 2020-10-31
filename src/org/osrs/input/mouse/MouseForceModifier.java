package org.osrs.input.mouse;

public interface MouseForceModifier {
    public Vector2D applyForce(double deltaTime, MouseJob mouseJob);
}

