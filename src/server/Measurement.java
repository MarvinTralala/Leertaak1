package server;

public class Measurement {
	
	int stn, wnddir;
	String date = null, time = null, frshtt;
	double temp, dewp, stp, slp, visib, wdsp, prcp, sndp, cldc;
	
	/**
	 * Creates measurement object
	 * @param stn
	 * @param date
	 * @param time
	 * @param temp
	 * @param dewp
	 * @param stp
	 * @param slp
	 * @param visib
	 * @param wdsp
	 * @param prcp
	 * @param sndp
	 * @param frshtt
	 * @param cldc
	 * @param wnddir
	 */
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
	
	/*
	 * Getters
	 */
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
	
	/*
	 * setters
	 */
	public void setTemp(double temp) {
		this.temp = temp;
	}
	
	public void setDewp(double dewp) {
		this.dewp = dewp;
	}
	
	public void setStp(double stp) {
		this.stp = stp;
	}
	
	public void setSlp(double slp) {
		this.slp = slp;
	}
	
	public void setVisib(double visib) {
		this.visib = visib;
	}

	public void setWdsp(double wdsp) {
		this.wdsp = wdsp;
	}
	
	public void setPrcp(double prcp) {
		this.prcp = prcp;
	}
	
	public void setSndp(double sndp) {
		this.sndp = sndp;
	}
	
	public void setCldc(double cldc) {
		this.cldc = cldc;
	}
	
}
