package com.demo.demo.model;

public enum TicketStatus {
    OPEN(101),
    CLOSED(102),
    BLOCKED(103);
	
	private final int value;
	
	TicketStatus(int value){
	    this.value = value;

	}
	public int getValue() {
	    return value;
	}

}


