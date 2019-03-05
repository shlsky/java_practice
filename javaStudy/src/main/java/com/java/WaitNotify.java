package com.java;

/**
 * @author hongling.shl
 * @date 2019/2/19
 */
public class WaitNotify {
	
	public static final String LOCK = "LOCK";
	
	public static void main(String[] args) {
		
		for (int i=0;i<5;i++){
			int finalI = i;
			new Thread(){
				
				@Override
				public void run() {
					synchronized (LOCK){
						System.out.println("I'm " +String.valueOf(finalI));
						try {
							if (finalI == 0){
								LOCK.wait();
							} else {
								LOCK.notify();
							}
							
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println("I'm " +String.valueOf(finalI) + " wake!");
					}
					
				}
			}.start();
		}
	}
}
