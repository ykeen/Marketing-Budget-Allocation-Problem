
import java.util.ArrayList;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ghost
 */
public class Individual{
	private double fitness;
	private ArrayList<Double>chromosome = new ArrayList<Double>();
	private ArrayList<Channel> channels = new ArrayList<Channel>();
	public Individual(ArrayList<Channel> channels) {
		super();
		this.channels = channels;
	}
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	public ArrayList<Double> getChromosome() {
		return chromosome;
	}
	public void setChromosome(ArrayList<Double> chromosome) {
		this.chromosome = chromosome;
	}
	public ArrayList<Channel> getChannels() {
		return channels;
	}
	public void setChannels(ArrayList<Channel> channels) {
		this.channels = channels;
	}
	public void buildChromosome(double budget) {
		double budget2=-1;
		ArrayList<Double>result= new ArrayList<Double>();
		while(budget2<0 || budget2>1)
		{
			budget2=budget;
			result= new ArrayList<Double>();
			for (int i = 0; i < channels.size(); i++) {
				double r = rand(channels.get(i).getLower(),Math.min(channels.get(i).getUpper(),budget2));
				result.add(r);
				budget2-=r;
			}
		}
		for (int i = 0; i < channels.size(); i++) {
			this.chromosome.add(result.get(i));
		}
	}
	public double rand(double rangeMin,double rangeMax)
	{
		Random r = new Random();
		double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
		return randomValue;
	}
	public double evaluateFitness()
	{
		double result=0;
		for (int i = 0; i < chromosome.size(); i++) {
			result += chromosome.get(i)*(channels.get(i).getRoi()/100);
		}
		this.setFitness(result);
		return this.fitness;
	}
	public double getFitness()
	{
		return this.fitness;
	}

}
