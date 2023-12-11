package Pomodoro;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.SwingUtilities;

import java.util.Scanner;
import java.util.InputMismatchException;

public class pomodoro {
    static float workTime = 25;
    static float breakTime = 5;
    static pomodoroGUI gui;
    static AtomicBoolean stop = new AtomicBoolean(false);
    
    public static void displayMenu() {
        System.out.println("1 - Start");
        System.out.println("2 - Settings");
        System.out.println("3 - Exit");
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
        }
    }

    public static String formatTime(float time) {
        int minutes = (int) time / 60;
        int seconds = (int) time % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public static void startTimer(float workTime, float breakTime) {
        stop.set(false);
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    
        executor.scheduleAtFixedRate(new Runnable() {
            float remainingWorkTime = workTime * 60;
            float remainingBreakTime = breakTime * 60;
    
            @Override
            public void run() {
                try {
                    if (stop.get()) {
                        executor.shutdown();
                        return;
                    }
    
                    boolean isWorkTime = remainingWorkTime > 0;
    
                    if (isWorkTime) {
                        if (remainingWorkTime == workTime * 60) {
                            System.out.println("\n\n---------- Work Time Started ----------");
                        }
                        System.out.printf("%s\n", formatTime(remainingWorkTime));
                        remainingWorkTime--;
                        SwingUtilities.invokeLater(() -> pomodoro.gui.updateWorkTimeLabel(formatTime(remainingWorkTime)));
                    } else if (remainingBreakTime > 0) {
                        if (remainingBreakTime == breakTime * 60) {
                            System.out.println("\n---------- Break Time Started ----------");
                        }
                        System.out.printf("%s\n", formatTime(remainingBreakTime));
                        remainingBreakTime--;
                        SwingUtilities.invokeLater(() -> pomodoro.gui.updateBreakTimeLabel(formatTime(remainingBreakTime)));
                    } else {
                        remainingWorkTime = workTime * 60;
                        remainingBreakTime = breakTime * 60;   
                    }
                } catch (Exception e) {
                    System.out.println("An error occurred: " + e.getMessage());
                    executor.shutdown();
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }
    
    public static void main(String[] args) {
        gui = new pomodoroGUI();
        while (true) {
            displayMenu();
            int choice = getChoice();
            switch (choice) {
                case 1: // Start Timer
                    startTimer(workTime, breakTime);
                    break;
                case 2: // Settings
                    chooseTimes();
                    break;
                case 3: // Exit
                    System.exit(0);
                    break;
                default:
                    System.out.println("Error: Something went wrong.");
                    break;
            }
        }
    }
}
