package com.github.kmbulebu.nicknack.basicproviders.wol;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.github.kmbulebu.nicknack.core.actions.ActionAttributeException;

public class WakeOnLan {
	
	final String[] macAddresses;
	
	private static final int WOL_PORT = 7;
	
	public WakeOnLan(String... macAddresses) {
		this.macAddresses = macAddresses;
	}
	
	public void send() throws IOException, ActionAttributeException {
		IOException exception = null;
		for (String macAddress : macAddresses) {
			try {
				wakeUp(macAddress);
			} catch (IOException e) {
				if (exception == null) {
					exception = e;
				}
			}
		}
		
		if (exception != null) {
			throw exception;
		}
	}
	
	protected void wakeUp(String macAddress) throws IOException, ActionAttributeException {
		
		final WakeOnLanPacket packet = new WakeOnLanPacket(macAddress);
		
		final byte[] data = packet.toBytes();
		
		final List<InetAddress> broadcastAddresses = getBroadcastAddresses();
		
		IOException exception = null;
		for (InetAddress broadcast : broadcastAddresses) {
			try {
				send(broadcast, data);
			} catch (IOException e) {
				if (exception == null) {
					exception = e;
				}
			}
		}
		
		if (exception != null) {
			throw exception;
		}
		
	}
	
	protected void send(InetAddress broadcastAddress, byte[] data) throws IOException {
		final DatagramSocket socket = new DatagramSocket();
		socket.setBroadcast(true);	
		socket.connect(broadcastAddress, WOL_PORT);
		
		final DatagramPacket packet = new DatagramPacket(data, data.length, broadcastAddress, WOL_PORT);
		
		socket.send(packet);
		
		socket.disconnect();
		
		socket.close();
	}
	
	protected DatagramPacket buildDatagramPacket(byte[] data, InetAddress broadcastAddress) {
		final DatagramPacket packet = new DatagramPacket(data, data.length, broadcastAddress, WOL_PORT);
		return packet;
	}
	
	public List<InetAddress> getBroadcastAddresses() throws SocketException {
		final List<InetAddress> broadcastAddresses = new ArrayList<InetAddress>();
		final Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
		while (e.hasMoreElements()) {
			final NetworkInterface nic = e.nextElement();
			if (!nic.isLoopback()) {
				for (InterfaceAddress ifaceAddress : nic.getInterfaceAddresses()) {
					InetAddress broadcast = ifaceAddress.getBroadcast();
					if (broadcast != null) {
						broadcastAddresses.add(broadcast);
					}
				}
			}
		}
		return broadcastAddresses;
	}

}
