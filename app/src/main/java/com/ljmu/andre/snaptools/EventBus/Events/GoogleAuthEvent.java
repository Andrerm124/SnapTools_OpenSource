package com.ljmu.andre.snaptools.EventBus.Events;

import com.ljmu.andre.snaptools.Networking.Packets.LoginPacket;

/**
 * This class was created by Andre R M (SID: 701439)
 * It and its contents are free to use by all
 */

public class GoogleAuthEvent {
	private LoginPacket loginPacket;
	private String message;

	public GoogleAuthEvent(LoginPacket loginPacket) {
		this.loginPacket = loginPacket;
	}

	public GoogleAuthEvent(String message) {
		this.message = message;
	}


	public LoginPacket getSyncData() {
		return loginPacket;
	}

	public String getMessage() {
		return message;
	}
}
