package org.osrs.api.objects;

import org.osrs.api.methods.MethodContext;
import org.osrs.api.methods.MethodProvider;

public abstract class RSRenderable extends MethodProvider{
	public abstract org.osrs.api.wrappers.RenderableNode getAccessor();
	public RSRenderable(MethodContext context){
		super(context);
	}
	public int getHeight(){
		return getAccessor().height();
	}
	public boolean getDisableRenderState(){
		org.osrs.api.wrappers.RenderableNode a = getAccessor();
		if(a!=null)
			return a.getDisableRenderState();
		return false;
	}
	public void setDisableRenderState(){
		org.osrs.api.wrappers.RenderableNode a = getAccessor();
		if(a!=null)
			a.setDisableRenderState();
	}
	@Override
	public boolean equals(Object o){
		if(o!=null && o instanceof RSRenderable){
			if(this.getAccessor().entityUID()==((RSRenderable)o).getAccessor().entityUID())
				return true;
		}
		return false;
	}
}
