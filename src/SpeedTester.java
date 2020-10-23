import a2.*; // This project requires code from COMP250 Assignment 2 Fall 2020 at McGill University
import RuntimeTester.*; // This project requires the library https://github.com/TheBigSasha/RuntimeTester

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Random;

/**
 * Speed test class for testing the time efficiency of your code in COMP250 Assignment 2
 * This class does not test if your code works, just how fast it runs.
 * 
 * Good luck!
 * 
 * @author Sasha Aleshchenko
 * @version 1.0
 */
public class SpeedTester {
    private static Random rand = new Random();                                          //Generates our dataset
    private static HashMap<String, Polynomial> savedPolynomials = new HashMap<>();      //Helps speed up runtime by storing the polynomials from previous steps
    
    public static void main(String args[]){
        MainWindow.launchGrapher(SpeedTester.class);
    }

    /**
     * Tests the speed of addterm in Polynomial for the nth term
     * @param size n
     * @return     how fast it was to add term n
     */
    @benchmark(name="Add Term", expectedEfficiency = "O(n)", category = "Assignment 2", description = "Adds a term to a polynomial of a size n")
    public static long addTermBenchmark(long size){
        final String methodID = "addTerm";
        int sizeAdj = (int) size;   //The Polynomial works with ints for the exponent
        if(!savedPolynomials.containsKey(methodID)) savedPolynomials.put(methodID, new Polynomial());
        Polynomial ourPolynomial = savedPolynomials.get(methodID);
        if(ourPolynomial.size() > sizeAdj - 1) {
            ourPolynomial = new Polynomial();
            savedPolynomials.put(methodID, ourPolynomial);
        }
        if(size < 2){
            for(int i = 0; i < sizeAdj; i++){
                ourPolynomial.addTerm(new Term(i, BigInteger.probablePrime(24,rand)));
            }
            BigInteger val = BigInteger.valueOf(rand.nextLong());
            Term t = new Term(sizeAdj, val);
            long starTime = System.nanoTime();
            ourPolynomial.addTerm(t);
            long endTime = System.nanoTime();
            return endTime - starTime;
        }else {

            for (int i = ourPolynomial.size(); i < sizeAdj - 1; i++) {
                ourPolynomial.addTerm(new Term(i, BigInteger.probablePrime(24, rand)));
            }
        }
        BigInteger val = BigInteger.valueOf(rand.nextLong());
        Term t = new Term(sizeAdj, val);
        long starTime = System.nanoTime();
        ourPolynomial.addTerm(t);
        long endTime = System.nanoTime();
        return endTime - starTime;
    }
    
}
