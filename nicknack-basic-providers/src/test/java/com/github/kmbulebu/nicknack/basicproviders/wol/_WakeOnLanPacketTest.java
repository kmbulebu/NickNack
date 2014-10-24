package com.github.kmbulebu.nicknack.basicproviders.wol;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import com.github.kmbulebu.nicknack.basicproviders.wol.WakeOnLanPacket;
import com.github.kmbulebu.nicknack.core.actions.ActionParameterException;

public class _WakeOnLanPacketTest {
	
	@Test
	public void testPacketContents() {
		final String macAddress="0A:1B:1C:1D:1E:1F";
		final byte[] expectedPacket ={(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
									(byte) 0x0A, (byte) 0x1B, (byte) 0x1C, (byte) 0x1D, (byte) 0x1E, (byte) 0x1F,
									(byte) 0x0A, (byte) 0x1B, (byte) 0x1C, (byte) 0x1D, (byte) 0x1E, (byte) 0x1F,
									(byte) 0x0A, (byte) 0x1B, (byte) 0x1C, (byte) 0x1D, (byte) 0x1E, (byte) 0x1F,
									(byte) 0x0A, (byte) 0x1B, (byte) 0x1C, (byte) 0x1D, (byte) 0x1E, (byte) 0x1F,
									(byte) 0x0A, (byte) 0x1B, (byte) 0x1C, (byte) 0x1D, (byte) 0x1E, (byte) 0x1F,
									(byte) 0x0A, (byte) 0x1B, (byte) 0x1C, (byte) 0x1D, (byte) 0x1E, (byte) 0x1F,
									(byte) 0x0A, (byte) 0x1B, (byte) 0x1C, (byte) 0x1D, (byte) 0x1E, (byte) 0x1F,
									(byte) 0x0A, (byte) 0x1B, (byte) 0x1C, (byte) 0x1D, (byte) 0x1E, (byte) 0x1F,
									(byte) 0x0A, (byte) 0x1B, (byte) 0x1C, (byte) 0x1D, (byte) 0x1E, (byte) 0x1F,
									(byte) 0x0A, (byte) 0x1B, (byte) 0x1C, (byte) 0x1D, (byte) 0x1E, (byte) 0x1F,
									(byte) 0x0A, (byte) 0x1B, (byte) 0x1C, (byte) 0x1D, (byte) 0x1E, (byte) 0x1F,
									(byte) 0x0A, (byte) 0x1B, (byte) 0x1C, (byte) 0x1D, (byte) 0x1E, (byte) 0x1F,
									(byte) 0x0A, (byte) 0x1B, (byte) 0x1C, (byte) 0x1D, (byte) 0x1E, (byte) 0x1F,
									(byte) 0x0A, (byte) 0x1B, (byte) 0x1C, (byte) 0x1D, (byte) 0x1E, (byte) 0x1F,
									(byte) 0x0A, (byte) 0x1B, (byte) 0x1C, (byte) 0x1D, (byte) 0x1E, (byte) 0x1F,
									(byte) 0x0A, (byte) 0x1B, (byte) 0x1C, (byte) 0x1D, (byte) 0x1E, (byte) 0x1F};
		
		try {
			WakeOnLanPacket wolPacket = new WakeOnLanPacket(macAddress);
			assertTrue(Arrays.equals(expectedPacket, wolPacket.toBytes()));
		} catch (ActionParameterException e) {
			fail();
		}
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testBadMacAddress() {
		try {
			WakeOnLanPacket wolPacket = new WakeOnLanPacket("0A:1B:1C:1D:1E");
			fail();
		} catch (ActionParameterException e) {
			// pass
		}
		
		try {
			WakeOnLanPacket wolPacket = new WakeOnLanPacket("0A1B1C1D1E1F");
			fail();
		} catch (ActionParameterException e) {
			// pass
		}
		
		try {
			WakeOnLanPacket wolPacket = new WakeOnLanPacket("0X1B1C1D1E1F");
			fail();
		} catch (ActionParameterException e) {
			// pass
		}
		
	}

}
