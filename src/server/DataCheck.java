package server;

//import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class DataCheck {
	
	//storage for all the data per stations
	private Map<Integer, LinkedList<Integer>> dataMap;
	
	/**
	 * Verify the data that goes in the database.
	 * @param station
	 * @param measurement
	 * @return
	 */
	public boolean verifyData(int station, double measurement){
		
		//temporary list which contains the measurement
		LinkedList<Integer> templist = dataMap.get(Integer.valueOf(station));
		
		//if empty create a new list, else work with the existing list
		if(templist != null){
			
			//first 30 data are considered the right data.
			if(templist.size()<30){
				templist.add((int)measurement);
				return true;
			}
			
			//TODO: Extrapolate
			int[] difference=null;
			
			for(int i=1;i<templist.size();i++){
				difference[i-1]=templist.get(i)-templist.get(i-1);
			}
			
			//TODO: check for more than 20% offset
			
		
		} 
		//create the new list
		else{ 
			LinkedList<Integer> list = new LinkedList<Integer>();
			list.add((int)measurement);
			dataMap.put(station, list);
		}

		
		return false;
	}
	
	
}
