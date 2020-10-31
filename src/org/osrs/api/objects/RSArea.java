package org.osrs.api.objects;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;

public class RSArea {
	private Polygon tileArea = null;
	public ArrayList<RSTile> regionTiles = new ArrayList<RSTile>();
	public RSArea(){
		tileArea=new Polygon();
	}
	public RSArea(RSTile...tiles){
		tileArea=new Polygon();
		for(RSTile tile : tiles)
			addTile(tile);
		Rectangle r = tileArea.getBounds();
		for(int x = 0;x<=r.getWidth();++x){
			for(int y =0;y<=r.getHeight();++y){
				RSTile tile = new RSTile(r.x+x, r.y+y);
				if(this.containsTile(tile)){
					regionTiles.add(tile);
				}
			}
		}
	}
	public void addTile(RSTile tile){
		tileArea.addPoint(tile.getX(), tile.getY());
		System.out.println("Added tile coordinate : "+tile.toString());
	}
	public void addTiles(RSTile...tiles){
		for(RSTile tile : tiles)
			addTile(tile);
	}
	public boolean containsTile(RSTile tile){
		return tileArea.contains(tile.getX(), tile.getY());
	}
}
