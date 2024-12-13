package com.cleancode.knuth;

import java.util.List;
import java.util.ArrayList;

public class PrimeNumbers {
    private static final int MAX_DIVISORS = 30;
    private static final int ROWS_PER_PAGE = 50;
    private static final int COLUMNS_PER_PAGE = 4;

    public static void main(String[] args) {
        int totalPrimes = 1000;
        List<Integer> primeList = computePrimes(totalPrimes);

        PrimeDisplay primeDisplay = new PrimeDisplay(ROWS_PER_PAGE, COLUMNS_PER_PAGE);
        primeDisplay.showPrimes(primeList, totalPrimes);
    }

    public static List<Integer> computePrimes(int limit) {
        List<Integer> primes = new ArrayList<>();
        int[] multiples = new int[MAX_DIVISORS + 1];
        primes.add(2);

        int current = 1;
        int primeCount = 1;
        int square = 9;
        int divisorLimit = 2;

        while (primeCount < limit) {
            boolean isPrime;
            do {
                current += 2;
                if (current == square) {
                    divisorLimit++;
                    square = primes.get(divisorLimit - 1) * primes.get(divisorLimit - 1);
                    multiples[divisorLimit - 1] = current;
                }
                isPrime = true;
                for (int i = 1; i < divisorLimit && isPrime; i++) {
                    while (multiples[i] < current) {
                        multiples[i] += primes.get(i) * 2;
                    }
                    if (multiples[i] == current) {
                        isPrime = false;
                    }
                }
            } while (!isPrime);
            primeCount++;
            primes.add(current);
        }

        return primes;
    }
}

class PrimeDisplay {
    private final int rows;
    private final int columns;

    public PrimeDisplay(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    public void showPrimes(List<Integer> primes, int totalPrimes) {
        int pageNumber = 1;
        int offset = 0;

        while (offset < totalPrimes) {
            System.out.printf("Prime Numbers (Page %d):%n", pageNumber);
            for (int row = 0; row < rows && offset + row < totalPrimes; row++) {
                for (int col = 0; col < columns; col++) {
                    int index = offset + row + col * rows;
                    if (index < totalPrimes) {
                        System.out.printf("%8d", primes.get(index));
                    }
                }
                System.out.println();
            }
            System.out.println();
            pageNumber++;
            offset += rows * columns;
        }
    }
}