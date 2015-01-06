package com.github.kmbulebu.nicknack.providers.dsc.internal;

public class Partitions {	
	
	private final Partition[] partitions;
	
	public Partitions() {
		partitions = new Partition[16];
	}
	
	public Partition getPartition(int partitionNumber) {
		// Partitions start with 1, not zero.
		return partitions[partitionNumber - 1];
	}
	
	public void setPartition(int partitionNumber, Partition partition) {
		partitions[partitionNumber - 1] = partition;
	}
	
}
