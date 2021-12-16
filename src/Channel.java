

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ghost
 */
public class Channel {
	private String name;
	private double upper;
	private double lower;
	private double roi;

	public Channel(String name, double lower, double upper, double roi) {
		super();
		this.name = name;
		this.upper = upper;
		this.lower = lower;
		this.roi = roi;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getUpper() {
		return upper;
	}
	public void setUpper(double upper) {
		this.upper = upper;
	}
	public double getLower() {
		return lower;
	}
	public void setLower(double lower) {
		this.lower = lower;
	}
	public double getRoi() {
		return roi;
	}
	public void setRoi(double roi) {
		this.roi = roi;
	}
	@Override
	public String toString() {
		return "Channel [name=" + name + ", upper=" + upper + ", lower=" + lower + ", roi=" + roi + "]";
	}
}