package com.oakcity.nicknack.basicproviders.wol;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.oakcity.nicknack.core.actions.ActionParameterException;

public class _WakeOnLanTest {

	
	@Test
	public void emptyArray() {
		WakeOnLan wol = new WakeOnLan();
		try {
			wol.send();
		} catch (IOException | ActionParameterException e) {
			fail();
		}
	}
	
	@Test
	public void sanityTestNoExceptions() {
		WakeOnLan wol = new WakeOnLan("AA:BB:CC:DD:EE:FF");
		try {
			wol.send();
		} catch (IOException | ActionParameterException e) {
			fail();
		}
	}
	
}
