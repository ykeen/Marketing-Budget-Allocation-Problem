
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ghost
 */
public class Main{

	public static void main(String[] args) throws IOException {
	int noOfGen = 100;
        double marktingBudget_;
        int numberOfChannels_;
        Scanner in = new Scanner(System.in);
        ArrayList<String> channelNames_ = new ArrayList<String>();
        ArrayList<Double> channelROI_ = new ArrayList<>();
        ArrayList<Double> channelUpper_ = new ArrayList<>();
        ArrayList<Double> channelLower_ = new ArrayList<>();
        ArrayList<Channel> channels_ = new ArrayList<Channel>();
        

        System.out.println("Enter the marketing budget (in thousands):");
        marktingBudget_ = in.nextDouble();
        
        System.out.println("\nEnter the number of marketing channels:");
        numberOfChannels_ = in.nextInt();
        
        System.out.println("\nEnter the name and ROI (in %) of each channel separated by space:");
        for (int i = 0; i < numberOfChannels_ ; i++) {
        	channelNames_.add(in.next());
        	channelROI_.add(in.nextDouble());
        }
        
        System.out.println("\nEnter the lower (k) and upper bounds (%) of investment in each channel: (enter x if there is no bound)\n");
        for (int i = 0; i <numberOfChannels_ ; i++) {
        	String lower = in.next(); String upper = in.next();
        	
        	if (lower.equals("x"))
        		lower = "0";
        	channelLower_.add(Double.parseDouble(lower));
        	
        	if (upper.equals("x"))
        		channelUpper_.add(marktingBudget_); // max value
        	else
        		channelUpper_.add(marktingBudget_*(Double.parseDouble(upper)/100));
        }
		while(noOfGen!=2000)
		{
	        double marktingBudget=marktingBudget_;
	        int numberOfChannels=numberOfChannels_;
	        ArrayList<String> channelNames = new ArrayList<String>(channelNames_);
	        
	        ArrayList<Double> channelROI = new ArrayList<>(channelROI_);
	        

	        ArrayList<Double> channelUpper = new ArrayList<>(channelUpper_);
	        

	        ArrayList<Double> channelLower = new ArrayList<>(channelLower_);
	        

	        ArrayList<Channel> channels = new ArrayList<Channel>(channels_);


		
		System.out.println("\nPlease wait while running the GA…\n\n");


		// Getting Channels
		for (int i = 0; i <numberOfChannels ; i++) {
			Channel channel = new Channel(channelNames.get(i),channelLower.get(i),
					channelUpper.get(i),channelROI.get(i));
			channels.add(channel);
		}

		ArrayList<Individual> pop = new ArrayList<Individual>();
		for (int i = 0; i < noOfGen; i++) {
			Individual ind = new Individual(channels);
			pop.add(ind);
		}
		Operations op = new Operations(pop,noOfGen,marktingBudget);
                ArrayList<Individual> selectedParents ;
                ArrayList<Individual> offspring ;
                 ArrayList<Individual> offspringAfterMutation ;
                Individual individual = new Individual(channels);
		op.createRandomPopulation();
                int numOfIterator = 1 ;
                while(numOfIterator!=noOfGen){
                    selectedParents = new ArrayList<Individual>();
                    offspring = new ArrayList<Individual>();
                    offspringAfterMutation = new ArrayList<Individual>();
                    individual.evaluateFitness() ;
                    for (int i = 0; i < noOfGen-20; i++) {
                        selectedParents.add(i, op.tournamentSelection());
                    }
                    offspring = op.twoPointCrossover(selectedParents,channelUpper,channelLower);
                    individual.evaluateFitness() ;
                    //offspringAfterMutation = op.uniformMutation(offspring, channelUpper, channelLower);
                    offspringAfterMutation = op.nonUniformMutation(offspring, channelUpper, channelLower, numOfIterator,noOfGen); // new Generation
                    individual.evaluateFitness() ;
                    for (int i = 0; i < 20; i++) {
                    	offspringAfterMutation.add(op.getPopulation().get(i));
                    }
                    Collections.sort(offspringAfterMutation, Comparator.comparing(Individual::getFitness).reversed());

                    op.setPopulation(offspringAfterMutation);
                    
                    numOfIterator ++ ;
                }
                System.out.println("The final marketing budget allocation is: \n");

                for (int i = 0; i < channels.size(); i++) {
					System.out.println(channelNames.get(i)+" -> "+ Double.parseDouble(new DecimalFormat("##.#").format(op.getPopulation().get(0).getChromosome().get(i)))+ " K");
				
				}
                System.out.println("The total profit is "+ Double.parseDouble(new DecimalFormat("##.#").format(op.getPopulation().get(0).getFitness()))+" K");
              
                noOfGen+=50;
		}                

	}
	public static void show(ArrayList<Double>result,ArrayList<Channel> channels)
	{
		for (int i = 0; i < result.size(); i++) {
			result.get(i);
			System.out.println(channels.get(i).getName()+" -> "+Double.parseDouble(new DecimalFormat("##.#").format(result.get(i))));
		}
	}
}
/*
100
4

TV 8
Google 12
Twitter 7
Facebook 11

2.7 58
20.5 x
x 18
10 x

 */