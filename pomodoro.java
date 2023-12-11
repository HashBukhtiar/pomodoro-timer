package Pomodoro;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.Scanner;
import java.util.InputMismatchException;

public class pomodoro {
    static float workTime = 25;
    static float breakTime = 5;
    
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
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        AtomicBoolean stop = new AtomicBoolean(false);

        executor.execute(() -> {
            Scanner sc = new Scanner(System.in);
            while (true) {
                System.out.print("\nEnter 'stop' to stop the timer: ");
                String input = sc.nextLine();
                if ("stop".equalsIgnoreCase(input)) {
                    stop.set(true);
                    break;
                }
            }
        });

        ScheduledFuture<?> future = executor.scheduleAtFixedRate(new Runnable() {
            float remainingWorkTime = workTime * 60;
            float remainingBreakTime = breakTime * 60;

            @Override
            public void run() {
                if (stop.get()) {
                    throw new RuntimeException("\nTimer stopped by user");
                }

                boolean isWorkTime = remainingWorkTime > 0;

                if (isWorkTime) {
                    if (remainingWorkTime == workTime * 60) {
                        System.out.println("\n\n---------- Work Time Started ----------");
                    }
                    System.out.printf("%s\n", formatTime(remainingWorkTime));
                    remainingWorkTime--;
                } else if (remainingBreakTime > 0) {
                    if (remainingBreakTime == breakTime * 60) {
                        System.out.println("\n---------- Break Time Started ----------");
                    }
                    System.out.printf("%s\n", formatTime(remainingBreakTime));
                    remainingBreakTime--;
                } else {
                    remainingWorkTime = workTime * 60;
                    remainingBreakTime = breakTime * 60;   
            }
        }}, 0, 1, TimeUnit.SECONDS);

        try {
            future.get();
        } catch (ExecutionException e) {
            if (stop.get()) {
                System.out.println("Timer stopped. Returning to main menu.");
            } else {
                System.out.println("An error occurred: " + e.getCause());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            executor.shutdown();
        }
    }

    public static void main(String[] args) {
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
