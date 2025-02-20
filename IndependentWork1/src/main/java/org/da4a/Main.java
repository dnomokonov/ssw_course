package org.da4a;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    private static int countCasher = 5;
    private static int countСustomers = 10;

    public static void main(String[] args) {
        Casher[] cashers = new Casher[countCasher];

        for (int i = 0; i < countCasher; i++) {
            cashers[i] = new Casher(i + 1);
        }

        Semaphore semaphore = new Semaphore(countCasher);

        Thread [] threads = new Thread[countСustomers];
        for (int i = 0; i < countСustomers; i++) {
            threads[i] = new Thread(new Customer(i + 1, cashers, semaphore));
            threads[i].start();
        }

        System.out.println("All customers are served!");
    }

    public static class Casher {
        private final int id;
        private final ReentrantLock lock = new ReentrantLock();

        public Casher(int id) {
            this.id = id;
        }

        public boolean tryEmptyCasher(int thId) {
            if (lock.tryLock()) {
                try {
                    System.out.println("Thread Customer " + thId + " uses a cash register №" + id);
                    Thread.sleep(5000);
                    System.out.println("Thread Customer " + thId + " emptied the till №" + id);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                lock.unlock();

                return true;
            }

            return false;
        }
    }

    public static class Customer implements Runnable {
        private final int id;
        private final Casher[] cashers;
        private final Semaphore semaphore;

        public Customer(int id, Casher[] cashers, Semaphore semaphore) {
            this.id = id;
            this.cashers = cashers;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();
                for (Casher casher : cashers) {
                    if (casher.tryEmptyCasher(id)) break;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            semaphore.release();
        }

    }

}