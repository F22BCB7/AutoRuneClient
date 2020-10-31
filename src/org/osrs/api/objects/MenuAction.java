package org.osrs.api.objects;

public class MenuAction {
//item.getIndex(), 9764864, 37, item.getID(), "Drop", "<col=ff9040>Copper ore", methods.game.getMouseX(), methods.game.getMouseY()
//0, 0, 10, npcToDisplay.calculateMenuTag(), "Attack", "<col=ffff00>Chicken<col=80ff00>  (level-1)", methods.game.getMouseX(), methods.game.getMouseY()
//loc.getX()-methods.game.getMapBaseX(), loc.getY()-methods.game.getMapBaseY(), 20, giToDisplay.getID(), "Take", "<col=ff9040>Feather", methods.game.getMouseX(), methods.game.getMouseY()
	public int arg1=-1;
	public int arg2=-1;
	public int opcode=-1;
	public int tag=-1;
	public String action="";
	public String option="";
	public int mouseX=-1;
	public int mouseY=-1;
	public MenuAction(int arg1, int arg2, int opcode, int tag, String action, String option, int mouseX, int mouseY){
		this.arg1=arg1;
		this.arg2=arg2;
		this.opcode=opcode;
		this.tag=tag;
		this.action=action;
		this.option=option;
		this.mouseX=mouseX;
		this.mouseY=mouseY;
	}
}
