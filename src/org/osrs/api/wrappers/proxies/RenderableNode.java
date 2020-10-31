package org.osrs.api.wrappers.proxies;

import org.osrs.api.objects.RSModel;
import org.osrs.injection.bytescript.BClass;
import org.osrs.injection.bytescript.BDetour;
import org.osrs.injection.bytescript.BField;
import org.osrs.injection.bytescript.BFunction;
import org.osrs.injection.bytescript.BMethod;
import org.osrs.injection.bytescript.BGetter;
import org.osrs.injection.bytescript.BVar;
import org.osrs.util.Data;

@BClass(name="RenderableNode")
public class RenderableNode extends EntityNode implements org.osrs.api.wrappers.RenderableNode{
	@BVar
	public boolean ignoreDisableRendering=false;
	@BFunction
	@Override
	public boolean getDisableRenderState(){
		return ignoreDisableRendering;
	}
	@BFunction
	@Override
	public void setDisableRenderState(){
		ignoreDisableRendering=!ignoreDisableRendering;
	}
	@BMethod(name="renderAt")
	public void _renderAt(int a, int b, int c, int d, int e, int f, int g, int h, long i){}
	@BDetour
	@Override
	public void renderAt(int a, int b, int c, int d, int e, int f, int g, int h, long i){
		if(!Client.disableRendering || ignoreDisableRendering){
			_renderAt(a, b, c, d, e, f, g, h, i);
		}
	}
	
	@BField
	public int height;
	@BGetter
	@Override
	public int height(){return height;}
	@BVar
	public RSModel tempCachedModel = null;
	@BMethod(name="getModel")
	public org.osrs.api.wrappers.Model _getModel(int a){return null;}
	@BDetour
	public org.osrs.api.wrappers.Model getModel(int a){
		org.osrs.api.wrappers.Model model = _getModel(a);
		if(model!=null){
			if(tempCachedModel!=null)
				tempCachedModel.updateModel(model);
			else
				tempCachedModel = new RSModel(Data.clientContexts.get(Client.clientInstance), model);
		}
		else{
			this.tempCachedModel=null;
		}
		return model;
	}
	@BFunction
	@Override
	public org.osrs.api.wrappers.Model invokeGetModel(int a){
		return _getModel(a);
	}
	@BFunction
	@Override
	public RSModel getTempModel(){
		return tempCachedModel;
	}
}