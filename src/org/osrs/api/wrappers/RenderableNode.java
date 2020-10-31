package org.osrs.api.wrappers;

import org.osrs.api.objects.RSModel;
import org.osrs.api.wrappers.proxies.Model;

public interface RenderableNode extends EntityNode{
	public int height();

	public boolean getDisableRenderState();

	public void setDisableRenderState();

	public void renderAt(int a, int b, int c, int d, int e, int f, int g, int h, long i);

	public RSModel getTempModel();

	public org.osrs.api.wrappers.Model invokeGetModel(int a);
}