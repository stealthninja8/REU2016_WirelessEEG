package eeggui;

import java.util.EventObject;

public class eegEvent extends EventObject {
	
	private String desired_elec;
	private String analysis;
	
	public eegEvent(Object source) {
		super(source);
	}
	
	public eegEvent(Object source, String elec, String ana) {
		super(source);
		this.desired_elec = elec;
		this.analysis = ana;
	}

	public String getDesired_elec() {
		return desired_elec;
	}
	
	public String getAnalysis() {
		return this.analysis;
	}

}
