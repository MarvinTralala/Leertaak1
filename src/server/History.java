package server;

import java.util.ArrayList;

public class History {
	
	ArrayList<Measurement> measurements;
	
	public History() {
		measurements = new ArrayList<Measurement>();
	}
	
	public void addToHistory(Measurement m) {
		if (measurements.size() == 300) {
			measurements.remove(0);
		}
		measurements.add(m);
	}
	
	public int size() {
		return measurements.size();
	}
	
	public ArrayList<Measurement> getMeasurementsByID(int stn) {
		ArrayList<Measurement> temp = new ArrayList<Measurement>();
		for(int i=0; i<measurements.size(); i++) {
			Measurement m = measurements.get(i);
			if (m.getStn() == stn) {
				temp.add(m);
			}
		}
		return temp;
	}
}
