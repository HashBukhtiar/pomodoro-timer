package Pomodorro;

import java.util.Timer;
import java.util.Scanner;
import java.util.InputMismatchException;

public class pomodorro {
    static float workTime = 25;
    static float breakTime = 5;
    
    
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
        chooseTimes();
    }
}
