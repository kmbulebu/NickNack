package com.github.kmbulebu.nicknack.basicproviders.wol;

import java.util.Arrays;

import com.github.kmbulebu.nicknack.core.actions.ActionAttributeException;

public class WakeOnLanPacket {
	
	final byte[] macAddress;
	
	public WakeOnLanPacket(String macAddress) throws ActionAttributeException {
		this.macAddress = toByteArray(macAddress);
	}
	
	protected static final byte[] toByteArray(String macAddress) throws ActionAttributeException {
		final String[] macAddressSplit = macAddress.split(":");
		
		if (macAddressSplit.length != 6) {
			throw new ActionAttributeException("Mac Address must be formatted with five colons.");
		}

		final byte[] bytes = new byte[6];
		for(int i=0; i < bytes.length; i++){
			try {
				bytes[i] = new Integer(Integer.parseInt(macAddressSplit[i], 16)).byteValue();
			} catch (NumberFormatException e) {
				throw new ActionAttributeException("Mac Address must contain only hexadecimal [0-9A-E] values.");
			}
		}
		return bytes;
	}
	
	public byte[] toBytes() {
		final byte[] bytes = new byte[17 * 6];
		
		// Header is 6 bytes of 0xFF
		Arrays.fill(bytes, 0, 6, (byte) 0xFF);
		
		// Mac Address 16 times over.
		for (int i = 1; i < 17; i++) {
            for (int p = 0; p < 6; p++) {
            	bytes[(i * 6) + p] = macAddress[p]; 
            }
		}
		
		return bytes;
	}

}
