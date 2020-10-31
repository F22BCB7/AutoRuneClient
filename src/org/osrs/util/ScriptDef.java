/*******************************************************
* Created by Marneus901                                *
* © 2013 Dynamac.org                                   *
********************************************************
* Dynamac's main script definition.                    *
* To be used in order to interact or read/write data   *
* to the client that is being hooked.                  *
********************************************************/
package org.osrs.util;

import java.awt.Graphics;
import java.security.SecureRandom;
import java.util.Random;

import org.osrs.api.methods.MethodContext;
import org.osrs.api.objects.RSActor;
import org.osrs.api.wrappers.Actor;
import org.osrs.api.wrappers.ChatboxMessage;
import org.osrs.api.wrappers.ClassInfo;
import org.osrs.api.wrappers.IncomingPacketMeta;
import org.osrs.api.wrappers.OutgoingPacket;
import org.osrs.api.wrappers.OutgoingPacketMeta;
import org.osrs.api.wrappers.PacketContext;

public abstract class ScriptDef extends Thread{
	public abstract void run();
	public boolean isPaused=false;
	public final SecureRandom random = new SecureRandom();
	public MethodContext methods = null;
	public void setContext(MethodContext context){
		methods=context;
	}
	/**
	 * Returns a random value between 0
	 * and given max value.
	 * @param max
	 * @return random
	 */
	public int random(int max){
		return random(0, max);
	}
	/**
	 * Returns a random value between 
	 * given min and max bounds.
	 * @param min
	 * @param max
	 * @return random
	 */
	public int random(int min, int max){
		try{
			int a = max-min;
			random.setSeed(Data.randomSeed);
			return random.nextInt(Math.abs(a))+min;
		}
		catch(Exception e){
			return 0;
		}
	}
	/**
	 * Only used for Script DebugFrame 
	 * seperate canvas rendering for scripts
	 * @param g
	 * @return graphics
	 */
	public Graphics paintDebug(Graphics g){
		return g;
	}
	/**
	 * Used in the standard client canvas rendering
	 * @param g
	 * @return graphics
	 */
	public Graphics paint(Graphics g){
		return g;
	}
	/**
	 * Stops this current instance script
	 */
	@SuppressWarnings("deprecation")
	public void stopScript(){
		isPaused=false;
		this.stop();
	}
	/**
	 * Pauses this current instance script
	 */
	@SuppressWarnings("deprecation")
	public void pause(){
		isPaused=true;
		this.suspend();
	}
	/**
	 * Unpauses this current instance script
	 */
	@SuppressWarnings("deprecation")
	public void unpause(){
		isPaused=false;
		this.resume();
	}
	/**
	 * Built-in game chat message listener.
	 * This method is called BEFORE the message
	 * is rendered (game data will not be updated yet).
	 * Override in scripts to implement.
	 * @param message
	 */
	public void recieveChatMessage(ChatboxMessage message){
	}
	/**
	 * Built-in incoming packet listener.
	 * This method is called BEFORE the datastream
	 * is read (game data will not be updated yet).
	 * Override in scripts to implement.
	 * @param packetMeta
	 */
	public void recievePacket(IncomingPacketMeta meta){
	}
	/**
	 * Built-in outgoing packet listener.
	 * This method is called BEFORE the data is sent.
	 * Override in scripts to implement
	 * @param packetMeta
	 */
	public void recievePacket(OutgoingPacketMeta meta){
	}
	/**
	 * Built-in incoming packet listener.
	 * This method is called BEFORE the datastream
	 * is read (game data will not be updated yet).
	 * Override in scripts to implement.
	 * @param packetMeta
	 */
	public void recievePacketData(IncomingPacketMeta meta){
	}
	/**
	 * Built-in incoming packet listener.
	 * This method is called AFTER the datastream
	 * is read (game data will be updated).
	 * Override in scripts to implement.
	 * @param packetMeta
	 */
	public void recievePacketData(PacketContext context, IncomingPacketMeta meta){
	}
	/**
	 * Built-in outgoing packet listener.
	 * This method is called BEFORE the data is sent.
	 * Override in scripts to implement
	 * @param packetMeta
	 */
	public void recievePacketData(OutgoingPacket packet){
	}
	/**
	 * Built-in region XTEA listener.
	 * Override in scripts to implement.
	 */
	public void xteaChanged(){
		
	}
	/**
	 * Built-in reflection checker listener.
	 * Override in scripts to implement
	 * @param info
	 */
	public void reflectionRequested(ClassInfo info){
		
	}
	/**
	 * Built-in on-death event for Npcs and Players
	 * Override in scripts to implement
	 * @param actor
	 */
	public void onDeathEvent(){
		
	}
	/**
	 * Current instance script will 
	 * have its thread sleep for given millisecond 
	 * timeout.
	 * @param timeout
	 */
	public void sleep(int timeout){
		try{
			super.sleep(timeout);
		}
		catch(Exception e){
		}
	}
}
