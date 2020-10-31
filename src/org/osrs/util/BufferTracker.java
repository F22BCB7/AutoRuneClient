package org.osrs.util;

import java.util.ArrayList;

public class BufferTracker {
	public Object[] trackedData;
	public BufferTracker(){
		trackedData = new Object[0];
	}
	public void clearBuffer(){
		trackedData = new Object[0];
	}
	public void writeData(int a){
		try{
			if(trackedData==null || trackedData.length==0){
				trackedData = new Object[1];
				trackedData[0]=a;
			}
			else{
				Object[] temp = new Object[trackedData.length+1];
				for(int i=0;i<trackedData.length;++i){
					temp[i]=trackedData[i];
				}
				temp[trackedData.length]=a;
				trackedData = temp;
			}
		}
		catch(Exception e){
			
		}
	}
	public void writeData(long a) {
		try{
			if(trackedData==null || trackedData.length==0){
				trackedData = new Object[1];
				trackedData[0]=a;
			}
			else{
				Object[] temp = new Object[trackedData.length+1];
				for(int i=0;i<trackedData.length;++i){
					temp[i]=trackedData[i];
				}
				temp[trackedData.length]=a;
				trackedData = temp;
			}
		}
		catch(Exception e){
			
		}
	}
	public void writeData(boolean a) {
		try{
			if(trackedData==null || trackedData.length==0){
				trackedData = new Object[1];
				trackedData[0]=a;
			}
			else{
				Object[] temp = new Object[trackedData.length+1];
				for(int i=0;i<trackedData.length;++i){
					temp[i]=trackedData[i];
				}
				temp[trackedData.length]=a;
				trackedData = temp;
			}
		}
		catch(Exception e){
			
		}
	}
	public void writeData(String a) {
		try{
			if(trackedData==null || trackedData.length==0){
				trackedData = new Object[1];
				trackedData[0]=a;
			}
			else{
				Object[] temp = new Object[trackedData.length+1];
				for(int i=0;i<trackedData.length;++i){
					temp[i]=trackedData[i];
				}
				temp[trackedData.length]=a;
				trackedData = temp;
			}
		}
		catch(Exception e){
			
		}
	}
	public void writeData(CharSequence a) {
		try{
			if(trackedData==null || trackedData.length==0){
				trackedData = new Object[1];
				trackedData[0]=a;
			}
			else{
				Object[] temp = new Object[trackedData.length+1];
				for(int i=0;i<trackedData.length;++i){
					temp[i]=trackedData[i];
				}
				temp[trackedData.length]=a;
				trackedData = temp;
			}
		}
		catch(Exception e){
			
		}
	}

	public void readData(String a) {
		try{
			if(trackedData==null || trackedData.length==0){
				trackedData = new Object[1];
				trackedData[0]=a;
			}
			else{
				Object[] temp = new Object[trackedData.length+1];
				for(int i=0;i<trackedData.length;++i){
					temp[i]=trackedData[i];
				}
				temp[trackedData.length]=a;
				trackedData = temp;
			}
		}
		catch(Exception e){
			
		}
	}
	public void readData(int a) {
		try{
			if(trackedData==null || trackedData.length==0){
				trackedData = new Object[1];
				trackedData[0]=a;
			}
			else{
				Object[] temp = new Object[trackedData.length+1];
				for(int i=0;i<trackedData.length;++i){
					temp[i]=trackedData[i];
				}
				temp[trackedData.length]=a;
				trackedData = temp;
			}
		}
		catch(Exception e){
			
		}
	}
	public void readData(long a) {
		try{
			if(trackedData==null || trackedData.length==0){
				trackedData = new Object[1];
				trackedData[0]=a;
			}
			else{
				Object[] temp = new Object[trackedData.length+1];
				for(int i=0;i<trackedData.length;++i){
					temp[i]=trackedData[i];
				}
				temp[trackedData.length]=a;
				trackedData = temp;
			}
		}
		catch(Exception e){
			
		}
	}
	public void readData(boolean a) {
		try{
			if(trackedData==null || trackedData.length==0){
				trackedData = new Object[1];
				trackedData[0]=a;
			}
			else{
				Object[] temp = new Object[trackedData.length+1];
				for(int i=0;i<trackedData.length;++i){
					temp[i]=trackedData[i];
				}
				temp[trackedData.length]=a;
				trackedData = temp;
			}
		}
		catch(Exception e){
			
		}
	}
}
