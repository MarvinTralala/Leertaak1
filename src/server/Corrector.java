package server;

import java.util.ArrayList;

public class Corrector {
	
	History history;
	ArrayList<Measurement> historyOfStation;
	int stnHistorySize;
	
	public Corrector(History history) {
		this.history = history;
		stnHistorySize = 0;
	}
	
	public Measurement correctMeasurement(Measurement m) {
		historyOfStation = history.getMeasurementsByID(m.getStn());
		stnHistorySize = historyOfStation.size();
		
		if (stnHistorySize > 1) {
			m.setTemp(correctTemp(m.getTemp()));
			m.setDewp(correctDewp(m.getDewp()));
			m.setStp(correctStp(m.getStp()));
			m.setSlp(correctSlp(m.getSlp()));
			m.setVisib(correctVisib(m.getVisib()));
			m.setWdsp(correctWdsp(m.getWdsp()));
			m.setPrcp(correctPrcp(m.getPrcp()));
			m.setSndp(correctSndp(m.getSndp()));
			m.setCldc(correctCldc(m.getCldc()));
		}
		return m;
	}
	
	private double correctTemp(double value) {
		double[] tempList= new double[stnHistorySize];
		
		//get all values of history
		for (int i=0; i<stnHistorySize; i++) {
			tempList[i] = historyOfStation.get(i).getTemp();
		}
		
		//get the average value of the array
		return correctData(value, getAverage(tempList));
	}
	
	private double correctDewp(double value) {
		double[] tempList= new double[stnHistorySize];
		
		//get all values of history
		for (int i=0; i<stnHistorySize; i++) {
			tempList[i] = historyOfStation.get(i).getDewp();
		}
		
		//get the average value of the array
		return correctData(value, getAverage(tempList));
	}
	
	private double correctStp(double value) {
		double[] tempList= new double[stnHistorySize];
		
		//get all values of history
		for (int i=0; i<stnHistorySize; i++) {
			tempList[i] = historyOfStation.get(i).getStp();
		}
		
		//get the average value of the array
		return correctData(value, getAverage(tempList));
	}
	
	private double correctSlp(double value) {
		double[] tempList= new double[stnHistorySize];
		
		//get all values of history
		for (int i=0; i<stnHistorySize; i++) {
			tempList[i] = historyOfStation.get(i).getSlp();
		}
		
		//get the average value of the array
		return correctData(value, getAverage(tempList));
	}
	
	private double correctVisib(double value) {
		double[] tempList= new double[stnHistorySize];
		
		//get all values of history
		for (int i=0; i<stnHistorySize; i++) {
			tempList[i] = historyOfStation.get(i).getVisib();
		}
		
		//get the average value of the array
		return correctData(value, getAverage(tempList));
	}
	
	private double correctWdsp(double value) {
		double[] tempList= new double[stnHistorySize];
		
		//get all values of history
		for (int i=0; i<stnHistorySize; i++) {
			tempList[i] = historyOfStation.get(i).getWdsp();
		}
		
		//get the average value of the array
		return correctData(value, getAverage(tempList));
	}
	
	private double correctPrcp(double value) {
		double[] tempList= new double[stnHistorySize];
		
		//get all values of history
		for (int i=0; i<stnHistorySize; i++) {
			tempList[i] = historyOfStation.get(i).getPrcp();
		}
		
		//get the average value of the array
		return correctData(value, getAverage(tempList));
	}
	
	private double correctSndp(double value) {
		double[] tempList= new double[stnHistorySize];
		
		//get all values of history
		for (int i=0; i<stnHistorySize; i++) {
			tempList[i] = historyOfStation.get(i).getSndp();
		}
		
		//get the average value of the array
		return correctData(value, getAverage(tempList));
	}
	
	private double correctCldc(double value) {
		double[] tempList= new double[stnHistorySize];
		
		//get all values of history
		for (int i=0; i<stnHistorySize; i++) {
			tempList[i] = historyOfStation.get(i).getCldc();
		}
		
		//get the average value of the array
		return correctData(value, getAverage(tempList));
	}
	
	private double correctData(double data, double average) {
		if (data == 0 || data > (average * 1.2) || data < (average * 0.8)) {
			data = average;
		}
		
		return data;
	}
	
	private double getAverage(double[] numbers) {
		double total = 0;
		int count = numbers.length;
		
		for(int i=0; i<count; i++) {
			total += numbers[i];
		}
		
		return (double)Math.round((total / count) * 10) / 10;
	}
}
