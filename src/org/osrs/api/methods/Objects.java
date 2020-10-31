package org.osrs.api.methods;

import org.osrs.api.objects.GameObject;
import org.osrs.api.objects.RSTile;
import org.osrs.api.wrappers.Region;
import org.osrs.api.wrappers.*;

import java.util.ArrayList;

public class Objects extends MethodDefinition{
	public Objects(MethodContext context){
		super(context);
	}
	public GameObject[] getAllObjects(){
		ArrayList<GameObject> objects = new ArrayList<GameObject>();
		int plane=0, x=0, y=0;
		for(Tile[][] map : methods.game.getRegion().tiles()){
			for(Tile[] row : map){
				for(Tile tile : row){
					if(tile!=null){
						for(InteractableObject io : tile.objects()){
							if(io==null)
								continue;
							objects.add(new GameObject(methods, io, new RSTile(x, y, plane)));
						}
						BoundaryObject bo = tile.boundary();
						if(bo!=null)
							objects.add(new GameObject(methods, bo, new RSTile(x, y, plane)));
						FloorDecoration fo = tile.floor();
						if(fo!=null)
							objects.add(new GameObject(methods, fo, new RSTile(x, y, plane)));
						WallDecoration wo = tile.wall();
						if(wo!=null)
							objects.add(new GameObject(methods, wo, new RSTile(x, y, plane)));
					}
					y++;
				}
				y=0;
				x++;
			}
			x=0;
			plane++;
		}
		return objects.toArray(new GameObject[]{});
	}
	public GameObject[] getAllAt(RSTile t){
		return getAllAt(t.getX(), t.getY(), t.getPlane());
	}
	public GameObject[] getAllAt(int x, int y){
		return getAllAt(x, y, methods.game.getCurrentPlane());
	}
	public GameObject[] getAllAt(int x, int y, int plane){
        ArrayList<GameObject> objects = new ArrayList<GameObject>();
    	int localX = x - methods.game.getMapBaseX();
    	int localY = y - methods.game.getMapBaseY();
    	if(localX<0 || localX>=104 || localY<0 || localY>=104)
    		return new GameObject[]{};
    	Region region = methods.game.getRegion();
    	if(region!=null){
    		Tile tile = region.tiles()[plane][localX][localY];
    		if(tile!=null){
    			for(InteractableObject io : tile.objects()){
					if(io==null)
						continue;
    				objects.add(new GameObject(methods, io, new RSTile(x, y, plane)));
    			}
    			BoundaryObject bo = tile.boundary();
    			if(bo!=null)
    				objects.add(new GameObject(methods, bo, new RSTile(x, y, plane)));
    			FloorDecoration fo = tile.floor();
    			if(fo!=null)
    				objects.add(new GameObject(methods, fo, new RSTile(x, y, plane)));
    			WallDecoration wo = tile.wall();
    			if(wo!=null)
    				objects.add(new GameObject(methods, wo, new RSTile(x, y, plane)));
    		}
    	}
        return objects.toArray(new GameObject[]{});
	}

	public GameObject getPrimaryAt(RSTile t) {
		int plane = t.getPlane();
		int localX = t.getLocalX(methods);
		int localY = t.getLocalY(methods);
		Tile tile = methods.game.getRegion().tiles()[plane][localX][localY];
		if (tile == null) return null;
		for (InteractableObject io : tile.objects()) {
			if (io != null && !(io.model() instanceof Actor)) {
				return new GameObject(methods, io, t);
			}
		}
		return null;
	}
	/**
	 * Returns top object on tile
	 * InteractableObject > BoundaryObject > FloorDecoration > WallDecoration
	 * @param tile
	 * @return object
	 */
	public GameObject getObjectAt(RSTile t){
		return getObjectAt(t.getX(), t.getY(), t.getPlane());
	}
	public GameObject getObjectAt(int x, int y){
		return getObjectAt(x, y, methods.game.getCurrentPlane());
	}
	public GameObject getObjectAt(int x, int y, int plane){
    	int localX = x - methods.game.getMapBaseX();
    	int localY = y - methods.game.getMapBaseY();
    	Region region = methods.game.getRegion();
    	if(region!=null && localX<104 && localY<104 && localX>=0 && localY>=0){
    		Tile tile = region.tiles()[plane][localX][localY];
    		if(tile!=null){
    			for(InteractableObject io : tile.objects()){
    				if(io!=null)
    					return new GameObject(methods, io, new RSTile(x, y, plane));
    			}
    			BoundaryObject bo = tile.boundary();
    			if(bo!=null)
    				return  new GameObject(methods, bo, new RSTile(x, y, plane));
    			FloorDecoration fo = tile.floor();
    			if(fo!=null)
    				return new GameObject(methods, fo, new RSTile(x, y, plane));
    			WallDecoration wo = tile.wall();
    			if(wo!=null)
    				return new GameObject(methods, wo, new RSTile(x, y, plane));
    		}
    	}
        return null;
	}
	public GameObject[] getObjectsByID(long id){
		ArrayList<GameObject> objects = new ArrayList<GameObject>();
		int plane=0, x=0, y=0;
		for(Tile[][] map : methods.game.getRegion().tiles()){
			for(Tile[] row : map){
				for(Tile tile : row){
					if(tile!=null){
						for(InteractableObject io : tile.objects()){
							GameObject go = new GameObject(methods, io, new RSTile(x+methods.game.getMapBaseX(), y+methods.game.getMapBaseY(), plane));
							if(id==go.getID())
								objects.add(go);
						}
						BoundaryObject bo = tile.boundary();
						if(bo!=null){
							GameObject go = new GameObject(methods, bo, new RSTile(x+methods.game.getMapBaseX(), y+methods.game.getMapBaseY(), plane));
							if(id==go.getID())
								objects.add(go);
						}
						FloorDecoration fo = tile.floor();
						if(fo!=null){
							GameObject go = new GameObject(methods, fo, new RSTile(x+methods.game.getMapBaseX(), y+methods.game.getMapBaseY(), plane));
							if(id==go.getID())
								objects.add(go);
						}
						WallDecoration wo = tile.wall();
						if(wo!=null){
							GameObject go = new GameObject(methods, wo, new RSTile(x+methods.game.getMapBaseX(), y+methods.game.getMapBaseY(), plane));
							if(id==go.getID())
								objects.add(go);
						}
					}
					y++;
				}
				y=0;
				x++;
			}
			x=0;
			plane++;
		}
		return objects.toArray(new GameObject[]{});
	}
	public GameObject[] getObjectsByID(long...ids){
		ArrayList<GameObject> objects = new ArrayList<GameObject>();
		int plane=0, x=0, y=0;
		for(Tile[][] map : methods.game.getRegion().tiles()){
			for(Tile[] row : map){
				for(Tile tile : row){
					if(tile!=null){
						for(InteractableObject io : tile.objects()){
							GameObject go = new GameObject(methods, io, new RSTile(x+methods.game.getMapBaseX(), y+methods.game.getMapBaseY(), plane));
							for(long id : ids){
								if(id==go.getID()){
									objects.add(go);
									break;
								}
							}
						}
						BoundaryObject bo = tile.boundary();
						if(bo!=null){
							GameObject go = new GameObject(methods, bo, new RSTile(x+methods.game.getMapBaseX(), y+methods.game.getMapBaseY(), plane));
							for(long id : ids){
								if(id==go.getID()){
									objects.add(go);
									break;
								}
							}
						}
						FloorDecoration fo = tile.floor();
						if(fo!=null){
							GameObject go = new GameObject(methods, fo, new RSTile(x+methods.game.getMapBaseX(), y+methods.game.getMapBaseY(), plane));
							for(long id : ids){
								if(id==go.getID()){
									objects.add(go);
									break;
								}
							}
						}
						WallDecoration wo = tile.wall();
						if(wo!=null){
							GameObject go = new GameObject(methods, wo, new RSTile(x+methods.game.getMapBaseX(), y+methods.game.getMapBaseY(), plane));
							for(long id : ids){
								if(id==go.getID()){
									objects.add(go);
									break;
								}
							}
						}
					}
					y++;
				}
				y=0;
				x++;
			}
			x=0;
			plane++;
		}
		return objects.toArray(new GameObject[]{});
	}
	public GameObject getNearestByID(long id){
		GameObject closestObj = null;
		double closestDistance = 999;
		RSTile closestTile = null;
		int baseX=methods.game.getMapBaseX(), baseY=methods.game.getMapBaseY();
		int plane = methods.game.getCurrentPlane();
		for(int localX=0;localX<104;++localX){
			for(int localY=0;localY<104;++localY){
				int x = localX+baseX;
				int y = localY+baseY;
				RSTile tile = new RSTile(x, y, plane);
				double distance = methods.calculations.distanceTo(tile);
				if(closestTile!=null){
					if(distance>=closestDistance)
						continue;
				}
				for(GameObject go : getAllAt(tile)){
					if(go.getID()==id){
						closestObj=go;
						closestTile=tile;
						closestDistance = distance;
					}
				}
			}
		}
		return closestObj;
	}
	public GameObject getNearestByID(long...ids){
		GameObject closestObj = null;
		double closestDistance = 999;
		RSTile closestTile = null;
		int baseX=methods.game.getMapBaseX(), baseY=methods.game.getMapBaseY();
		int plane = methods.game.getCurrentPlane();
		for(int localX=0;localX<104;++localX){
			for(int localY=0;localY<104;++localY){
				int x = localX+baseX;
				int y = localY+baseY;
				RSTile tile = new RSTile(x, y, plane);
				double distance = methods.calculations.distanceTo(tile);
				if(closestTile!=null){
					if(distance>=closestDistance)
						continue;
				}
				for(GameObject go : getAllAt(tile)){
					for(long id : ids){
						if(go.getID()==id){
							closestObj=go;
							closestTile=tile;
							closestDistance = distance;
							break;
						}
					}
				}
			}
		}
		return closestObj;
	}
	public GameObject getNextNearestByID(RSTile ignore, long id){
		GameObject closest = null;
		int plane=0, x=0, y=0;
		for(Tile[][] map : methods.game.getRegion().tiles()){
			for(Tile[] row : map){
				for(Tile tile : row){
					if(tile!=null){
						RSTile rstile = new RSTile(x+methods.game.getMapBaseX(), y+methods.game.getMapBaseY(), plane);
						if(rstile.equals(ignore))
							continue;
						for(InteractableObject io : tile.objects()){
							if(io==null)
								continue;
							GameObject go = new GameObject(methods, io, rstile);
							if(id==go.getID()){
								if(closest==null || methods.calculations.distanceTo(closest.getLocation())<methods.calculations.distanceTo(rstile)){
									closest=go;
								}
							}
						}
						BoundaryObject bo = tile.boundary();
						if(bo!=null){
							GameObject go = new GameObject(methods, bo, rstile);
							if(id==go.getID()){
								if(closest==null || methods.calculations.distanceTo(closest.getLocation())<methods.calculations.distanceTo(rstile)){
									closest=go;
								}
							}
						}
						FloorDecoration fo = tile.floor();
						if(fo!=null){
							GameObject go = new GameObject(methods, fo, rstile);
							if(id==go.getID()){
								if(closest==null || methods.calculations.distanceTo(closest.getLocation())<methods.calculations.distanceTo(rstile)){
									closest=go;
								}
							}
						}
						WallDecoration wo = tile.wall();
						if(wo!=null){
							GameObject go = new GameObject(methods, wo, rstile);
							if(id==go.getID()){
								if(closest==null || methods.calculations.distanceTo(closest.getLocation())<methods.calculations.distanceTo(rstile)){
									closest=go;
								}
							}
						}
					}
					y++;
				}
				y=0;
				x++;
			}
			x=0;
			plane++;
		}
		return closest;
	}
	public GameObject getNextNearestByID(RSTile ignore, long...ids){
		GameObject closest = null;
		int plane=0, x=0, y=0;
		for(Tile[][] map : methods.game.getRegion().tiles()){
			for(Tile[] row : map){
				for(Tile tile : row){
					if(tile!=null){
						RSTile rstile = new RSTile(x+methods.game.getMapBaseX(), y+methods.game.getMapBaseY(), plane);
						if(rstile.equals(ignore))
							continue;
						for(InteractableObject io : tile.objects()){
							if(io==null)
								continue;
							
							GameObject go = new GameObject(methods, io, rstile);
							for(long id : ids){
								if(id==go.getID()){
									if(closest==null || methods.calculations.distanceTo(closest.getLocation())<methods.calculations.distanceTo(rstile)){
										closest=go;
										break;
									}
								}
							}
						}
						BoundaryObject bo = tile.boundary();
						if(bo!=null){
							GameObject go = new GameObject(methods, bo, rstile);
							for(long id : ids){
								if(id==go.getID()){
									if(closest==null || methods.calculations.distanceTo(closest.getLocation())<methods.calculations.distanceTo(rstile)){
										closest=go;
										break;
									}
								}
							}
						}
						FloorDecoration fo = tile.floor();
						if(fo!=null){
							GameObject go = new GameObject(methods, fo, rstile);
							for(long id : ids){
								if(id==go.getID()){
									if(closest==null || methods.calculations.distanceTo(closest.getLocation())<methods.calculations.distanceTo(rstile)){
										closest=go;
										break;
									}
								}
							}
						}
						WallDecoration wo = tile.wall();
						if(wo!=null){
							GameObject go = new GameObject(methods, wo, rstile);
							for(long id : ids){
								if(id==go.getID()){
									if(closest==null || methods.calculations.distanceTo(closest.getLocation())<methods.calculations.distanceTo(rstile)){
										closest=go;
										break;
									}
								}
							}
						}
					}
					y++;
				}
				y=0;
				x++;
			}
			x=0;
			plane++;
		}
		return closest;
	}
	public boolean isObjectAt(RSTile tile, long id){
    	int localX = tile.getX() - methods.game.getMapBaseX();
    	int localY = tile.getY() - methods.game.getMapBaseY();
    	Region region = methods.game.getRegion();
    	if(region!=null){
    		Tile t = region.tiles()[tile.getPlane()][localX][localY];
    		if(t!=null){
    			for(InteractableObject io : t.objects()){
					if(io==null)
						continue;
    				GameObject go = new GameObject(methods, io, tile);
    				if(id==go.getID())
    					return true;
    			}
    			BoundaryObject bo = t.boundary();
    			if(bo!=null){
    				GameObject go = new GameObject(methods, bo, tile);
    				if(id==go.getID())
    					return true;
    			}
    			FloorDecoration fo = t.floor();
    			if(fo!=null){
    				GameObject go = new GameObject(methods, fo, tile);
    				if(id==go.getID())
    					return true;
    			}
    			WallDecoration wo = t.wall();
    			if(wo!=null){
    				GameObject go = new GameObject(methods, wo, tile);
    				if(id==go.getID())
    					return true;
    			}
    		}
    	}
		return false;
	}
	public boolean isObjectAt(RSTile tile, long...ids){
    	int localX = tile.getX() - methods.game.getMapBaseX();
    	int localY = tile.getY() - methods.game.getMapBaseY();
    	Region region = methods.game.getRegion();
    	if(region!=null){
    		Tile t = region.tiles()[tile.getPlane()][localX][localY];
    		if(t!=null){
    			for(InteractableObject io : t.objects()){
					if(io==null)
						continue;
    				GameObject go = new GameObject(methods, io, tile);
    				for(long id : ids){
    					if(id==go.getID())
    						return true;
    				}
    			}
    			BoundaryObject bo = t.boundary();
    			if(bo!=null){
    				GameObject go = new GameObject(methods, bo, tile);
    				for(long id : ids){
    					if(id==go.getID())
    						return true;
    				}
    			}
    			FloorDecoration fo = t.floor();
    			if(fo!=null){
    				GameObject go = new GameObject(methods, fo, tile);
    				for(long id : ids){
    					if(id==go.getID())
    						return true;
    				}
    			}
    			WallDecoration wo = t.wall();
    			if(wo!=null){
    				GameObject go = new GameObject(methods, wo, tile);
    				for(long id : ids){
    					if(id==go.getID())
    						return true;
    				}
    			}
    		}
    	}
		return false;
	}
}
