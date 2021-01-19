package org.osrs.randoms;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import org.osrs.api.constants.ActionOpcodes;
import org.osrs.api.methods.MethodContext;
import org.osrs.api.objects.RSNpc;
import org.osrs.api.objects.RSPlayer;
import org.osrs.api.wrappers.Client;
import org.osrs.api.wrappers.Player;
import org.osrs.util.Data;
import org.osrs.util.ScriptDef;

public class RandomHandler extends Thread{
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
	private boolean enable=true;
	private Client client = null;
	private MethodContext methods = null;
	public boolean randomDetected = false;
	public RandomHandler(Client c){
		client=c;
		methods=Data.clientContexts.get(c);
	}
	public boolean isEnabled(){
		return enable;
	}
	public void setEnabled(boolean status){
		if(status){
			if(!enable){
				this.resume();
				enable=true;
			}
		}
		else{
			if(enable){
				this.suspend();
				enable=false;
			}
		}
	}
	@Override
	public void run() {
		System.out.println("Random Event Handler Started.");
		while(Data.clientInstances.contains(client)){
			if(isEnabled()){
				RSPlayer local = methods.players.getLocalPlayer();
				if(local!=null){
					for(RSNpc npc : methods.npcs.getAll()){
						if(npc==null)
							continue;
						if(!RANDOM_NPCS.contains(npc.getID()))
							continue;
						int id = npc.accessor.get().interactingID();
						if(id==-1)
							continue;
						id -= 32768;
						Player[] players = methods.game.getClient().players();
						if(players!=null && players.length>=2000){
							Player pl = players[id];
							if(pl!=null){
								if(pl.equals(local.accessor.get())){
									try{
										if(methods.calculations.random(1000)<30){
											//randomly ignore the random event
											System.out.println("Random event detected! Randomly ignoring for 30sec...");
											sleep(30000);
										}
										else{
											int reaction = methods.calculations.random(20000);
											System.out.println(sdf.format(new Timestamp(System.currentTimeMillis())) + " Random Detected : "+npc.getID()+":"+npc.getName()+" Reacting in : "+reaction);
											randomDetected=true;
											sleep(reaction);
											methods.game.doClick(0, 0, ActionOpcodes.NPC_ACTION_4, npc.calculateMenuTag(), "Dismiss", "");
											sleep(5000);
											randomDetected=false;
										}
									}
									catch(Exception e){
										
									}
								}
							}
						}
					}
				}
			}
			try {
				sleep(100);
			} catch (Exception e) {
			}
		}
		System.out.println("Random Event Handler Ended.");
	}
	public static ArrayList<Integer> RANDOM_NPCS = null;
	public static int DRUNK_DWARF = 322;
	public static int GENIE = 326;
	public static int RICK_TURPENTINE = 375;
	public static int PILLORY = 380;
	public static int EVIL_BOB = 390;
	public static int EVIL_BOB_2 = 6754;
	public static int CAPT_ARNAV = 5426;
	public static int FROG = 5429;
	public static int NILES_1 = 5436;
	public static int MILES_1 = 5437;
	public static int GILES_1 = 5438;
	public static int NILES_2 = 5439;
	public static int MILES_2 = 5440;
	public static int GILES_2 = 5441;
	public static int SANDWICH_LADY = 5510;
	public static int POSTIE_PETE = 6738;
	public static int SERGEANT_DAMIEN = 6743;
	public static int FLIP = 6744;
	public static int LEO = 6746;
	public static int BEE_KEEPER = 6747;
	public static int FREAKY_FORESTER = 6748;
	public static int DUNCE = 6749;
	public static int OLD_MAN_1 = 6750;
	public static int OLD_MAN_2 = 6752;
	public static int OLD_MAN_3 = 6753;
	public static int QUIZ_MASTER = 6755;
	static{
		RANDOM_NPCS = new ArrayList<Integer>();
		RANDOM_NPCS.add(DRUNK_DWARF);
		RANDOM_NPCS.add(GENIE);
		RANDOM_NPCS.add(RICK_TURPENTINE);
		RANDOM_NPCS.add(PILLORY);
		RANDOM_NPCS.add(EVIL_BOB);
		RANDOM_NPCS.add(CAPT_ARNAV);
		RANDOM_NPCS.add(FROG);
		RANDOM_NPCS.add(NILES_1);
		RANDOM_NPCS.add(MILES_1);
		RANDOM_NPCS.add(GILES_1);
		RANDOM_NPCS.add(NILES_2);
		RANDOM_NPCS.add(MILES_2);
		RANDOM_NPCS.add(GILES_2);
		RANDOM_NPCS.add(SANDWICH_LADY);
		RANDOM_NPCS.add(POSTIE_PETE);
		RANDOM_NPCS.add(SERGEANT_DAMIEN);
		RANDOM_NPCS.add(FLIP);
		RANDOM_NPCS.add(LEO);
		RANDOM_NPCS.add(BEE_KEEPER);
		RANDOM_NPCS.add(FREAKY_FORESTER);
		RANDOM_NPCS.add(DUNCE);
		RANDOM_NPCS.add(OLD_MAN_1);
		RANDOM_NPCS.add(OLD_MAN_2);
		RANDOM_NPCS.add(OLD_MAN_3);
		RANDOM_NPCS.add(QUIZ_MASTER);
	}
}
