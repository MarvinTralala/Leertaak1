package server;

public class Measurement {
	
	int stn, wnddir;
	String date = null, time = null, frshtt;
	double temp, dewp, stp, slp, visib, wdsp, prcp, sndp, cldc;
	
	public Measurement(int stn, String date, String time, double temp, double dewp, double stp, double slp, double visib, double wdsp, double prcp, double sndp, String frshtt, double cldc, int wnddir) {
		this.stn = stn;
		this.date = date;
		this.time = time;
		this.temp = temp;
		this.dewp = dewp;
		this.stp = stp;
		this.slp = slp;
		this.visib = visib;
		this.wdsp = wdsp;
		this.prcp = prcp;
		this.sndp = sndp;
		this.frshtt = frshtt;
		this.cldc = cldc;
		this.wnddir = wnddir;
	}
	
	public int getStn() { return stn; }
	
	public String getDate() { return  date;	}
	
	public String getTime() { return time; }
	
	public double getTemp() { return temp; }
	
	public double getDewp() { return dewp; }
	
	public double getStp() { return stp; }
	
	public double getSlp() { return slp; }
	
	public double getVisib() { return visib; }
	
	public double getWdsp() { return wdsp; }
	
	public double getPrcp() { return prcp; }
	
	public double getSndp() { return sndp; }
	
	public String getFrshtt() { return frshtt; }
	
	public double getCldc() { return cldc; }
	
	public int getWnddir() { return wnddir; }
}
