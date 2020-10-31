package org.osrs.api.objects;
import org.osrs.api.methods.MethodContext;

import java.util.Comparator;

public class RSTile implements Comparable<RSTile> {

	public static final Comparator<RSTile> COMPARATOR = Comparator
			.comparingInt(RSTile::getPlane)
			.thenComparingInt(RSTile::getY)
			.thenComparingInt(RSTile::getX);


	private final int x;
	private final int y;
	private final int plane;

	public RSTile(int x, int y) {
		this.x = x;
		this.y = y;
		this.plane = 0;
	}

	public RSTile(int x, int y, int plane) {
		this.x = x;
		this.y = y;
		this.plane = plane;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getPlane() {
		return plane;
	}


	public int distanceTo(RSTile tile) {
		return (int) Math.hypot(this.x - tile.x, this.y - tile.y);
	}

	public RSTile offset(int dx, int dy) {
		return new RSTile(this.x + dx, this.y + dy);
	}

	public int getLocalX(MethodContext ctx) {
		return this.x - ctx.game.getMapBaseX();
	}
	public int getLocalY(MethodContext ctx) {
		return this.y - ctx.game.getMapBaseY();
	}


	@Override
	public int compareTo(RSTile obj) {
		return COMPARATOR.compare(this, obj);
	}

	@Override
	public int hashCode() {
		//no data loss!
		return plane << 30 | y << 15 | x;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RSTile) {
			RSTile t = (RSTile) obj;
			return (t.x == this.x && t.y == this.y && t.plane == this.plane);
		}
		return false;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + plane + ")";
	}
}