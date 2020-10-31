package org.osrs.api.methods;

import java.util.HashMap;

import org.osrs.api.constants.WidgetData;
import org.osrs.api.objects.GroundItem;
import org.osrs.api.objects.RSWidget;
import org.osrs.api.objects.Tab;

public class Tabs extends MethodDefinition{
	private Tab[] tabs = null;
	public Tab combat = null;
	public Tab skills = null;
	public Tab quests = null;
	public Tab inventory = null;
	public Tab equipment = null;
	public Tab prayer = null;
	public Tab magic = null;
	public Tab friendsList = null;
	public Tab account = null;
	public Tab clan = null;
	public Tab options = null;
	public Tab emotes = null;
	public Tab music = null;
	private int disabledID = -1;
	public Tabs(MethodContext context) {
		super(context);
		combat = new Tab(context, WidgetData.RESIZEMODE_TAB_COMBAT_OPTIONS[0], WidgetData.RESIZEMODE_TAB_COMBAT_OPTIONS[1], WidgetData.TAB_COMBAT_OPTIONS[0], WidgetData.TAB_COMBAT_OPTIONS[1], "Combat", 0);
		skills = new Tab(context, WidgetData.RESIZEMODE_TAB_SKILLS[0], WidgetData.RESIZEMODE_TAB_SKILLS[1], WidgetData.TAB_SKILLS[0], WidgetData.TAB_SKILLS[1], "Skills", 1);
		quests = new Tab(context, WidgetData.RESIZEMODE_TAB_QUEST_LIST[0], WidgetData.RESIZEMODE_TAB_QUEST_LIST[1], WidgetData.TAB_QUEST_LIST[0], WidgetData.TAB_QUEST_LIST[1], "Quests", 2);
		inventory = new Tab(context, WidgetData.RESIZEMODE_TAB_INVENTORY[0], WidgetData.RESIZEMODE_TAB_INVENTORY[1], WidgetData.TAB_INVENTORY[0], WidgetData.TAB_INVENTORY[1], "Inventory", 3);
		equipment = new Tab(context, WidgetData.RESIZEMODE_TAB_WORN_EQUIPMENT[0], WidgetData.RESIZEMODE_TAB_WORN_EQUIPMENT[1], WidgetData.TAB_WORN_EQUIPMENT[0], WidgetData.TAB_WORN_EQUIPMENT[1], "Equipment", 4);
		prayer = new Tab(context, WidgetData.RESIZEMODE_TAB_PRAYER[0], WidgetData.RESIZEMODE_TAB_PRAYER[1], WidgetData.TAB_PRAYER[0], WidgetData.TAB_PRAYER[1], "Prayer", 5);
		magic = new Tab(context, WidgetData.RESIZEMODE_TAB_MAGIC[0], WidgetData.RESIZEMODE_TAB_MAGIC[1], WidgetData.TAB_MAGIC[0], WidgetData.TAB_MAGIC[1], "Magic", 6);
		friendsList = new Tab(context, WidgetData.RESIZEMODE_TAB_FRIENDSLIST[0], WidgetData.RESIZEMODE_TAB_FRIENDSLIST[1], WidgetData.TAB_FRIENDSLIST[0], WidgetData.TAB_FRIENDSLIST[1], "Friends List", 7);
		account = new Tab(context, WidgetData.RESIZEMODE_TAB_ACCOUNT_MANAGEMENT[0], WidgetData.RESIZEMODE_TAB_ACCOUNT_MANAGEMENT[1], WidgetData.TAB_ACCOUNT_MANAGEMENT[0], WidgetData.TAB_ACCOUNT_MANAGEMENT[1], "Account Management", 8);
		clan = new Tab(context, WidgetData.RESIZEMODE_TAB_CLANCHAT[0], WidgetData.RESIZEMODE_TAB_CLANCHAT[1], WidgetData.TAB_CLANCHAT[0], WidgetData.TAB_CLANCHAT[1], "Clan Chat", 9);
		options = new Tab(context, WidgetData.RESIZEMODE_TAB_OPTIONS[0], WidgetData.RESIZEMODE_TAB_OPTIONS[1], WidgetData.TAB_OPTIONS[0], WidgetData.TAB_OPTIONS[1], "Options", 10);
		emotes = new Tab(context, WidgetData.RESIZEMODE_TAB_EMOTES[0], WidgetData.RESIZEMODE_TAB_EMOTES[1], WidgetData.TAB_EMOTES[0], WidgetData.TAB_EMOTES[1], "Emotes", 11);
		music = new Tab(context, WidgetData.RESIZEMODE_TAB_MUSICPLAYER[0], WidgetData.RESIZEMODE_TAB_MUSICPLAYER[1], WidgetData.TAB_MUSICPLAYER[0], WidgetData.TAB_MUSICPLAYER[1], "Music", 12);
		tabs = new Tab[]{combat, skills, quests, inventory, equipment, prayer, magic, friendsList, account, clan, options, emotes, music};
	}
	public Tab[] getAllTabs(){
		return tabs;
	}
	public Tab getSelectedTab(){
		for(Tab tab : tabs){
			RSWidget widget = tab.getWidget();
			if(widget!=null){
				if(widget.getInternal().spriteID()==1181)//find your selected tab
					return tab;//and return it
			}
		}
		return null;
	}
	public Tab getTab(String name){
		for(Tab tab : tabs){
			if(tab.name.toLowerCase().equals(name.toLowerCase()))
				return tab;
		}
		return null;
	}
	public Tab getTab(int index){
		if(index<0 || index>=tabs.length)
			return null;
		return tabs[index];
	}
}
