package com.wez.quartz.generator;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

/**
 *
 */
public class StrongUuidGenerator {

	protected static TimeBasedGenerator timeBasedGenerator;

	public StrongUuidGenerator() {
		ensureGeneratorInitialized();
	}

	protected void ensureGeneratorInitialized() {
		if (timeBasedGenerator == null) {
			synchronized (StrongUuidGenerator.class) {
				if (timeBasedGenerator == null) {
					timeBasedGenerator = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
				}
			}
		}
	}

	public String getNextId() {
		return timeBasedGenerator.generate().toString().replace("-", "");
	}

}
