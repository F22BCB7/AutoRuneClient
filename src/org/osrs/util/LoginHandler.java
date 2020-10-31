package org.osrs.util;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import org.osrs.api.objects.RSWidget;
import org.osrs.api.wrappers.Server;
import org.osrs.input.mouse.EventFactory;
import org.osrs.input.mouse.MouseHoverJob;
import org.osrs.input.mouse.MouseJob;
import org.osrs.input.mouse.MouseMoveListener;
import org.osrs.input.mouse.MouseTarget;
import org.osrs.util.ScriptDef;

public class LoginHandler extends ScriptDef{
	public Server setServer = null;
	public Thread handlerThread = null;
	public boolean membersAccount = false;
	public boolean letHandlerRun = false;
	public long timeWaitingUntil=-1;
	private String usernameToLogin = "";
	private String passwordToLogin = "";
	public Rectangle existingUserBounds = new Rectangle(500, 275, 135, 30);
	public Rectangle loginBounds = new Rectangle(235, 305, 135, 30);
	public Rectangle cancelWorldsBounds = new Rectangle(815, 5, 50, 15);
	public Rectangle okBounds = new Rectangle(420, 285, 135, 30);
	public void log(String s){
		System.out.println(s);
	}
	@Override
	public void run() {
		handlerThread=this;
		log("[LoginHandler] Started.");
		while(true){
			if(!letHandlerRun){
				sleep(5000);
				continue;
			}
			ScriptDef script = Data.currentScripts.get(methods.game.getClient());
			if(script==null){
				sleep(5000);
				continue;
			}
			if(script.isPaused){
				sleep(1000);
				continue;
			}
			if(timeWaitingUntil!=-1 && System.currentTimeMillis()<timeWaitingUntil){
				sleep(10000);
				continue;
			}
			int gameState = methods.game.getGameState();
			if(gameState==30){//Logged in
				//log("[LoginHandler] Logged in.");
				timeWaitingUntil=-1;
				sleep(10000);//10 seconds
			}
			else if(gameState==20){//Attempting login
				log("[LoginHandler] Attempting Login");
				while(gameState==20){
					sleep(100);
					gameState = methods.game.getGameState();
				}
			}
			else if(gameState==10){//Logged out
				//log("[LoginHandler] Logged Out");
				if(usernameToLogin.equals("") || passwordToLogin.equals("")){
					letHandlerRun=false;
					sleep(10000);
					continue;
				}
				int loginState = methods.game.getLoginState();
				if(loginState==0){//Welcome to Runescape New/Existing User buttons
					log("[LoginHandler] Clicking Existing User");
			        //clickMouse(existingUserBounds.x+(existingUserBounds.width/2), existingUserBounds.y+(existingUserBounds.height/2));
					methods.game.setLoginState(2);
				}
				else if(loginState==2){
					String message1 = methods.game.getLoginMessage1();
					if(message1.contains("has been updated!")){
						System.err.println("[LoginHandler] Runescape has been updated!");
						System.exit(0);
					}
					else if(message1.contains("need a members account")){
						this.membersAccount=false;
					}
					else if(message1.contains("standing in a members")){
						this.membersAccount=true;
					}
					else if(message1.contains("many login attempts")){
						methods.game.setLoginMessages("Waiting for server hop allowance.", "", "");
						
						timeWaitingUntil=System.currentTimeMillis()+(1000*60*15);
						sleep((1000*60*15));
					}
					else if(!message1.contains("Loaded New")){
						setServer = getRandomWorld();
						if(setServer!=null){
							log("[LoginHandler] Switching worlds");
							methods.game.setWorld(setServer);
							methods.game.setLoginMessages("Loaded New Server Connection!", "Number : "+setServer.number()+" Members : "+(setServer.members()==0?"false":"true"), "");
						}
						else{
							log("[LoginHandler] Loading worlds");
							methods.game.loadWorlds();
							sleep(500);
							methods.game.setLoginMessages("Loaded Server List!", "", "");
							clickMouse(cancelWorldsBounds.x+(random(cancelWorldsBounds.width)), cancelWorldsBounds.y+(random(cancelWorldsBounds.height)));
						}
					}
					else{
						if(methods.game.isLoginWorldSelectorOpen()){
							log("[LoginHandler] Closing world list.");
							clickMouse(cancelWorldsBounds.x+(random(cancelWorldsBounds.width)), cancelWorldsBounds.y+(random(cancelWorldsBounds.height)));
						}
						else{
							String currentUsername = methods.game.getUsername();
							if(currentUsername.equals(usernameToLogin)){
								String currentPassword = methods.game.getPassword();
								if(currentPassword.equals(passwordToLogin)){
									log("[LoginHandler] Clicking login");
									clickMouse((methods.game.getCenterLoginScreenX()-150)+(loginBounds.width/2), loginBounds.y+(loginBounds.height/2));
								}
								else{
									log("[LoginHandler] Setting password");
									methods.game.setPassword(passwordToLogin);
								}
							}
							else{
								log("[LoginHandler] Switching username");
								methods.game.setUsername(usernameToLogin);
							}
						}
					}
				}
				else if(loginState==3){
					System.err.println("WRONG LOGIN INFO! SHUTTING DOWN!");
					break;
				}
				else if(loginState==12){//Account disabled
					System.err.println("ACCOUNT GOT BANNED! SHUTTING DOWN!");
					File directory = new File(System.getProperty("user.home") + "\\osrs\\profiles\\");
					if(!directory.exists())
						directory.mkdirs();
					File profile = new File(System.getProperty("user.home") + "\\osrs\\profiles\\" + usernameToLogin.split("@")[0] + ".txt");
					profile.delete();
					System.exit(0);
				}
				else if(loginState==24){//Disconnected from server
					log("[LoginHandler] Disconnected from server!");
					//clickMouse(okBounds.x+(random(okBounds.width)), okBounds.y+(random(okBounds.height)));
					methods.game.setLoginState(2);
				}
			}
			sleep(random(250, 500));
		}
	}
	public Server getRandomWorld(){
		ArrayList<Server> servers = new ArrayList<Server>();
		Server[] array = methods.game.getServers();
		if(array!=null){
			for(Server server : array){
				if(server.members()==(membersAccount?1:0)){
					if(server.population()>1900 && server.population()<1)
						continue;
					String activity = server.activity();
					if(activity.contains("skill total"))
						continue;
					if(activity.equals("-")){
						servers.add(server);
					}
				}
			}
		}
		if(servers.size()>1){
			return servers.get(random(servers.size()));
		}
		if(servers.size()==1)
			return servers.get(0);
		return null;
	}
	public void setLoginInfo(String user, String pass){
		this.usernameToLogin=user;
		this.passwordToLogin=pass;
		letHandlerRun = true;
	}
	@Override
	public Graphics paint(Graphics g){
		int y = 75;
		int x = 250;
		if(handlerThread!=null){
			for(StackTraceElement trace : handlerThread.getStackTrace()){
				g.drawString(""+trace, x, y);
				y+=15;
			}
		}
		g.drawString("Login State : "+methods.game.getLoginState(), x, y);
		y+=15;
		g.drawString("Game State : "+methods.game.getGameState(), x, y);
		y+=15;
		g.drawString("Center Screen X : "+methods.game.getCenterLoginScreenX(), x, y);
		y+=15;
		if(timeWaitingUntil!=-1){

			g.drawString("Waiting : Waiting", x, y);
			y+=15;
			g.drawString("Login Limit Reached", x, y);
			y+=15;
			g.drawString("Time Left : "+((timeWaitingUntil-System.currentTimeMillis())/1000)+"s", x, y);
			y+=15;
		}
		return g;
	}
	public void clickMouse(int x, int y){
        EventFactory eventFactory = new EventFactory(methods);
        MouseEvent mouseEvent = eventFactory.createMousePress(x, y, true);
        methods.game.getMouseListener().sendEvent(mouseEvent);
		for(int i=0;i<20;++i){
			if(methods.mouse.isPressed())
				break;
			sleep(new Random().nextInt(10));
		}
		sleep(random(100, 500));
        mouseEvent = eventFactory.createMouseRelease(x, y, true);
        methods.game.getMouseListener().sendEvent(mouseEvent);
		for(int i=0;i<20;++i){
			if(!methods.mouse.isPressed())
				break;
			sleep(new Random().nextInt(10));
		}

        mouseEvent = eventFactory.createMouseClicked(x, y, true);
        methods.game.getMouseListener().sendEvent(mouseEvent);
	}
}
/*
//Welcome page (with new user and existing user buttons)
public static final int STATE_WELCOME = 0;

//Warning message for PVP and high risk worlds.
public static final int STATE_WARNING_MESSAGE = 1;

//Login page (with username and password fields)
public static final int STATE_LOGIN = 2;

//Username or password is incorrect.
public static final int STATE_INVALID = 3;

public static final int STATE_AUTHENTICATOR = 4;
public static final int STATE_FORGOT_PASSWORD = 5;
public static final int STATE_MESSAGE = 6;
public static final int STATE_DATE_OF_BIRTH = 7;
public static final int STATE_NOT_ELIGIBLE = 8;
public static final int STATE_DISABLED = 12;
public static final int STATE_DISCONNECTED = 24;
*/
