import a2.*; // This project requires code from COMP250 Assignment 2 Fall 2020 at McGill University
import RuntimeTester.*; // This project requires the library https://github.com/TheBigSasha/RuntimeTester

import java.awt.*;
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
    private static final String categoryName = "Assignment 2";
    
    public static void main(String args[]){
        Visualizer.launch(SpeedTester.class);
    }

    /**
     * Tests the speed of addterm in Polynomial for the nth term
     * @param size n
     * @return     how fast it was to add term n
     */
    @benchmark(name="Add Term", expectedEfficiency = "O(n)", category = categoryName, description = "Adds a term to a polynomial of a size n")
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

    /**
     * Tests the speed of multiply add for 2 polynomials of size n
     * @param size n
     * @return     how fast it was to add size n
     */
    @benchmark(name="Add Polynomial", category =categoryName, expectedEfficiency = "O(n1 + n2)", description = "The expected runtime is the size of the first polynomial plus the second")
    public static long testAddPoly2N(long size){
        final String methodID1 = "add2N1";
        final String methodID2 = "add2N2";
        if(!savedPolynomials.containsKey(methodID1) || savedPolynomials.get(methodID1).size() > size){
            savedPolynomials.put(methodID1, new Polynomial());
        }
        if(!savedPolynomials.containsKey(methodID2) || savedPolynomials.get(methodID2).size() > size){
            savedPolynomials.put(methodID2, new Polynomial());
        }

        Polynomial p1 = savedPolynomials.get(methodID1);
        Polynomial p2 = savedPolynomials.get(methodID2);    //TODO: Implement polynomial sharing between methods for speed++

        if(size < 2){
            for(int i = 0; i <= size; i++){
                p1.addTerm(new Term(i, BigInteger.probablePrime(24,rand)));
                p2.addTerm(new Term(i, BigInteger.probablePrime(24,rand)));
            }
            long starTime = System.nanoTime();
            Polynomial.add(p1,p2);
            long endTime = System.nanoTime();
            return endTime - starTime;
        }else {
            for(int i = p1.size(); i <= size; i++){
                p1.addTerm(new Term(i, BigInteger.probablePrime(24,rand)));
            }
            for(int i = p2.size(); i <= size; i++){
                p2.addTerm(new Term(i, BigInteger.probablePrime(24,rand)));
            }
            long startTime = System.nanoTime();
            Polynomial.add(p1,p2);
            long endTime = System.nanoTime();
            return endTime-startTime;
        }

    }

    /**
     * Tests the speed of multiply term in Polynomial for the nth term
     * @param size n
     * @return     how fast it was to add term n
     */
    @benchmark(name="Multiply Term", expectedEfficiency = "O(n)", category = categoryName, description = "Multiples a polynomial of size n by a term")
    public static long multiplyBenchmark(long size){
        final String methodID = "multiplyTerm";
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
        ourPolynomial.multiplyTerm(t);
        long endTime = System.nanoTime();
        return endTime - starTime;
    }

    /**
     * Tests the speed of multiply Polynomials of size n
     * @param size n
     * @return     how fast it was to multiply polynomials of size n
     */
    @benchmark(name="Multiply Polynomial", category =categoryName, expectedEfficiency = "O(n1 * n2)", description = "The expected runtime is the size of the first polynomial times the second")
    public static long testMultPoly2N(long size){
        final String methodID1 = "mult2N1";
        final String methodID2 = "mult2N2";
        if(!savedPolynomials.containsKey(methodID1) || savedPolynomials.get(methodID1).size() > size){
            savedPolynomials.put(methodID1, new Polynomial());
        }
        if(!savedPolynomials.containsKey(methodID2) || savedPolynomials.get(methodID2).size() > size){
            savedPolynomials.put(methodID2, new Polynomial());
        }

        Polynomial p1 = savedPolynomials.get(methodID1);
        Polynomial p2 = savedPolynomials.get(methodID2);    //TODO: Implement polynomial sharing between methods for speed++

        if(size < 2){
            for(int i = 0; i <= size; i++){
                p1.addTerm(new Term(i, BigInteger.probablePrime(24,rand)));
                p2.addTerm(new Term(i, BigInteger.probablePrime(24,rand)));
            }
            long starTime = System.nanoTime();
            Polynomial.multiply(p1,p2);
            long endTime = System.nanoTime();
            return endTime - starTime;
        }else {
            for(int i = p1.size(); i <= size; i++){
                p1.addTerm(new Term(i, BigInteger.probablePrime(24,rand)));
            }
            for(int i = p2.size(); i <= size; i++){
                p2.addTerm(new Term(i, BigInteger.probablePrime(24,rand)));
            }
            long startTime = System.nanoTime();
            Polynomial.multiply(p1,p2);
            long endTime = System.nanoTime();
            return endTime-startTime;
        }

    }

    /**
     * Tests the speed of multiply Polynomials of size n
     * @param size n
     * @return     how fast it was to multiply polynomials of size n
     */
    @benchmark(name="Multiply Polynomial", category =categoryName, expectedEfficiency = "O(n1 * 1)", description = "The expected runtime is the size of the first polynomial times the second. Here one polynomial never grows.")
    public static long testMultPoly2N_oneNeverGrows(long size){
        final String methodID2 = "mult2N2_2";

        if(!savedPolynomials.containsKey(methodID2) || savedPolynomials.get(methodID2).size() > size){
            savedPolynomials.put(methodID2, new Polynomial());
        }

        Polynomial p1 = new Polynomial();
        p1.addTerm(new Term(15, BigInteger.TEN));
        Polynomial p2 = savedPolynomials.get(methodID2);    //TODO: Implement polynomial sharing between methods for speed++

        if(size < 2){
            for(int i = 0; i <= size; i++){
                p2.addTerm(new Term(i, BigInteger.probablePrime(24,rand)));
            }
            long starTime = System.nanoTime();
            Polynomial.multiply(p1,p2);
            long endTime = System.nanoTime();
            return endTime - starTime;
        }else {
            for(int i = p2.size(); i <= size; i++){
                p2.addTerm(new Term(i, BigInteger.probablePrime(24,rand)));
            }
            long startTime = System.nanoTime();
            Polynomial.multiply(p1,p2);
            long endTime = System.nanoTime();
            return endTime-startTime;
        }

    }

}
