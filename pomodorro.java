package Pomodorro;

import java.util.Timer;
import java.util.Scanner;
import java.util.InputMismatchException;

public class pomodorro {
    static float workTime = 25;
    static float breakTime = 5;
    
    public static void displayMenu() {
        System.out.println("1. Start");
        System.out.println("2. Settings");
        System.out.println("3. Exit");
    }

    public static int getChoice() {
        int choice;
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Enter choice: ");
                choice = sc.nextInt();
                if (choice >= 1 && choice <= 3) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        sc.close();
        return choice;
    }

    public static void chooseTimes() {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Work Time (mins): ");
            workTime = sc.nextFloat();
            System.out.print("Break Time (mins): ");
            breakTime = sc.nextFloat();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
        } finally {
            sc.close();
        }
    }


    public static void main(String[] args) {
        while (true) {
            displayMenu();
            int choice = getChoice();
            switch (choice) {
                case 1:
                    break;
                case 2:
                    chooseTimes();
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Error: Something went wrong.");
                    break;
            }
        }
    }
}
