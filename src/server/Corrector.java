package server;

import java.util.ArrayList;

public class Corrector {
	
	History history;
	
	public Corrector(History history) {
		this.history = history;
	}
	
	public Measurement correctMeasurement(Measurement m) {
		ArrayList<Measurement> historyOfStation = history.getMeasurementsByID(m.getStn());
		
		if (historyOfStation.size() > 1) {
			//correct the data of m
		}
		return m;
	}
}
