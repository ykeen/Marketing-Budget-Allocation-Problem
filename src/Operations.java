
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ghost
 */
public class Operations{
    private ArrayList<Individual> population = new ArrayList<Individual>();
    private int popSize;
    private double budget;

    public Operations(ArrayList<Individual> population, int popSize, double budget) {
        super();
        this.population = population;
        this.popSize = popSize;
        this.budget = budget;
    }
    public ArrayList<Individual> getPopulation() {
		return population;
	}

	public void setPopulation(ArrayList<Individual> population) {
		this.population = population;
	}

	public void createRandomPopulation()
    {
        for (int i = 0; i < this.popSize ; i++) {
            this.population.get(i).buildChromosome(this.budget);
            this.population.get(i).evaluateFitness();
            //System.out.println("#"+(i+1)+" "+population.get(i).getChromosome()+" Profit = "+population.get(i).getFitness());
        }
        // For sorting the population
        Collections.sort(population, Comparator.comparing(Individual::getFitness).reversed());
    }
    public Individual tournamentSelection(){
        int randomInt = ((int)(Math.random() * ( this.popSize- 1)) + 1);
        int randomInt2 = ((int)(Math.random() * ( this.popSize- 1)) + 1);
        if(this.population.get(randomInt).getFitness()>this.population.get(randomInt2).getFitness()){
            return this.population.get(randomInt);
        }
        else{
            return this.population.get(randomInt2);
        }
    }
    public ArrayList<Individual> twoPointCrossover(ArrayList<Individual> parents,ArrayList<Double>upper,ArrayList<Double> lower)
    {
         ArrayList <Individual> offspring = new  ArrayList <Individual>() ;
         int startCrossPointPosition , endCrossPointPosition ;
        double P = 0.7 ;
        int max = parents.get(0).getChromosome().size() ;
        for (int i = 0; i < parents.size(); i+=2) {
            int random1 =((int)(Math.random()*(max - 1))) + 1;
            int random2 =((int)(Math.random()*(max - 1))) + 1;
            if(random1>random2){
                 startCrossPointPosition = random2 ;
                endCrossPointPosition = random1 ;
            }
            else{
                 startCrossPointPosition = random1 ;
                endCrossPointPosition = random2 ;
            }
            double randomNumber = Math.random();
                if(randomNumber<=P){
                    for(int n=0; n<max;n++){
                        if(i<startCrossPointPosition&&i>endCrossPointPosition){
                            parents.get(i).getChromosome().set(n, parents.get(i+1).getChromosome().get(n));
                            parents.get(i+1).getChromosome().set(n, parents.get(i).getChromosome().get(n));
                        }
                        else{
                            parents.get(i).getChromosome().set(n, parents.get(i).getChromosome().get(n));
                            parents.get(i+1).getChromosome().set(n, parents.get(i+1).getChromosome().get(n));
                        }
                    }
                    if (checkFesabilityOfChromosome(parents.get(i).getChromosome())&&checkFesabilityOfChromosome(parents.get(i+1).getChromosome())
                        &&checkFesabilityOfChannel(parents.get(i).getChromosome(),upper,lower)&&checkFesabilityOfChannel(parents.get(i+1).getChromosome(),upper,lower)) {
                         offspring.add(i, parents.get(i)) ;
                         offspring.add(i+1, parents.get(i+1)) ;
                    }
                    else{
                        i-= 2 ;
                    }
                }
                else {
                    offspring.add(i, parents.get(i)) ;
                    offspring.add(i+1, parents.get(i+1)) ;
                }
          
        }
        return offspring ;
    }
    public ArrayList <Individual>  uniformMutation(ArrayList <Individual> offspring ,ArrayList<Double>upper,ArrayList<Double> lower){
        double P = 0.05;
        for (int i = 0; i < offspring.size(); i++) {
            for (int j = 0; j <offspring.get(i).getChromosome().size(); j++) {
                double randomNumber = Math.random();
                if (randomNumber <= P){
                    double r1 = Math.random();
                    double delta ;
                    double deltaLower =  offspring.get(i).getChromosome().get(j) - lower.get(j) ;
                    double deltaUpper =  upper.get(j) - offspring.get(i).getChromosome().get(j) ;
                    if (r1<0.5) {
                        delta  = deltaLower ;
                    }
                    else {
                        delta = deltaUpper ;
                    }
                    double r2 = ((double)(Math.random() * ( delta- 0)) + 0);
                    if (delta == deltaLower) {
                        offspring.get(i).getChromosome().set(j, offspring.get(i).getChromosome().get(j)- r2) ;
                    }
                    else{
                         offspring.get(i).getChromosome().set(j, offspring.get(i).getChromosome().get(j)+ r2) ;
                    }
                }
            }
            if(!checkFesabilityOfChromosome(offspring.get(i).getChromosome())){
               i-- ;
            }
           
        }
        return offspring;
    }
    public ArrayList <Individual> nonUniformMutation(ArrayList <Individual> offspring ,ArrayList<Double> Upper,ArrayList<Double> lower, int currentGeneration , int maxGeneration){
        double P = 0.05;
        for (int i = 0; i < offspring.size(); i++) {
            for (int j = 0; j <offspring.get(i).getChromosome().size(); j++) {
                double randomNumber = Math.random();
                if (randomNumber <= P){
                    double r = Math.random();
                    double y ;
                    double deltaLower =  offspring.get(i).getChromosome().get(j) - lower.get(j) ;
                    double deltaUpper =  Upper.get(j) - offspring.get(i).getChromosome().get(j) ;
                    if (r<0.5) {
                        y  = deltaLower ;
                    }
                    else {
                        y = deltaUpper ;
                    }
                    int b =(int) ((int)(Math.random()*(5- 0.5)) + 0.5) ;
                    double deltaY = y *(1-Math.pow(r,Math.pow((1-currentGeneration/maxGeneration),b)));
                    if (y == deltaLower) {
                        offspring.get(i).getChromosome().set(j, offspring.get(i).getChromosome().get(j)-deltaY) ;
                    }
                    else{
                         offspring.get(i).getChromosome().set(j, offspring.get(i).getChromosome().get(j)+deltaY) ;
                    }
                }
            }
            if(!checkFesabilityOfChromosome(offspring.get(i).getChromosome())){
               i-- ;
            }
        }
        return offspring ;
    }
    boolean checkFesabilityOfChromosome(ArrayList<Double>chromosome){
        boolean flag=false ;
        double sum = 0 ;
        for (int i = 0; i < chromosome.size(); i++) {
            sum+= chromosome.get(i);
        }
        if (sum <= this.budget){
            flag = true ;
        }
        return flag ;
    } 
    boolean checkFesabilityOfChannel(ArrayList<Double>chromosome,ArrayList<Double>upper,ArrayList<Double> lower){
        boolean flag=false ;
        for (int i = 0; i < chromosome.size(); i++) {
            if (chromosome.get(i)<=upper.get(i)&&chromosome.get(i)>=lower.get(i)) {
                flag =true ;
            }
        }
        
        return flag ;
    } 
}
