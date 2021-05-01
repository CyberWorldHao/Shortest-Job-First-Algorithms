/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

/**
 *
 * @author CWH
 */
import java.io.PrintStream;
import java.util.*;
// Java program to implement Shortest Remaining Time First

class Process {
    int processID; // Process ID
    int burstime; // Burst Time
    int arrivalTime; // Arrival Time

    public Process(int processID, int burstime, int arrivalTime) {
        this.processID = processID;
        this.burstime = burstime;
        this.arrivalTime = arrivalTime;
    }
}

public class SJF {
    // Method to find the waiting time for all
    // processes
    static void findWaitingTime(Process proc[], int n, int wt[]) {
        int rt[] = new int[n];

        // Copy the burst time into rt[]
        for (int i = 0; i < n; i++)
            rt[i] = proc[i].burstime;

        int complete = 0, t = 0, minm = Integer.MAX_VALUE;
        int shortest = -1, finish_time, count = 1;
        boolean check = false;

        // Process until all processes gets
        // completed
        while (complete != n) {
            System.out.println("## Time " + count++);
            // Find process with minimum
            // remaining time among the
            // processes that arrives till the
            // current time`
            for (int j = 0; j < n; j++) {
                if (proc[j].arrivalTime == t && rt[j] > 0) {
                    System.out.println("Process ID " + proc[j].processID + " has arrived");
                }
                if ((proc[j].arrivalTime <= t) && (rt[j] < minm) && rt[j] > 0 && shortest == -1) {
                    minm = rt[j];
                    shortest = j;
                    check = true;
                }
            }

            // if no process arrive yet
            if (check == false) {
                System.out.println("There ain't processes arrive");
                System.out.println("");
                t++;
                continue;
            }

            System.out.println("### Process ID Waiting List : ");
            for (int j = 0; j < n; j++) {
                if ((proc[j].arrivalTime <= t) && (proc[j].processID != proc[shortest].processID) && rt[j] > 0) {
                    System.out.println("Process ID " + proc[j].processID);
                }
            }

            // Reduce remaining time by one
            rt[shortest]--;
            System.out.println("### Running Process ID " + proc[shortest].processID);
            System.out.println("Running Time left to completion : " + rt[shortest]);

            // Update minimum
            minm = rt[shortest];
            if (minm == 0)
                minm = Integer.MAX_VALUE;

            // If a process gets completely
            // executed
            if (rt[shortest] == 0) {

                // Increment complete
                complete++;
                check = false;

                // Find finish time of current
                // process
                finish_time = t + 1;
                System.out.println("Process ID " + proc[shortest].processID + " is completed");
                System.out.println("finish time is " + finish_time);
                // Calculate waiting time
                wt[shortest] = finish_time - proc[shortest].burstime - proc[shortest].arrivalTime;

                if (wt[shortest] < 0) {
                    wt[shortest] = 0;
                }

                for (int j = 0; j < n; j++) {
                    if ((proc[j].arrivalTime <= t) && (rt[j] < minm) && rt[j] > 0) {
                        minm = rt[j];
                        shortest = j;
                        check = true;
                    }
                }

            }
            // Increment time
            t++;
            System.out.println("");
        }
    }

    // Method to calculate turn around time
    static void findTurnAroundTime(Process proc[], int n, int wt[], int tat[]) {
        // calculating turnaround time by adding
        // burstime[i] + wt[i]
        for (int i = 0; i < n; i++)
            tat[i] = proc[i].burstime + wt[i];
    }

    // Method to calculate average time
    static void findavgTime(Process proc[], int n) {
        int wt[] = new int[n], tat[] = new int[n];
        double total_wt = 0, total_tat = 0;

        // Function to find waiting time of all
        // processes
        findWaitingTime(proc, n, wt);

        // Function to find turn around time for
        // all processes
        findTurnAroundTime(proc, n, wt, tat);

        // Display processes along with all
        // details
        System.out.println("### Result");
        System.out.printf("|%10s |%11s |%13s |%16s|\n", "Processes", "Burst time", "Waiting time", "Turn around time");
        System.out.printf("|%10s |%11s |%13s |%16s|\n", "---------", "----------", "------------", "----------------");
        // Calculate total waiting time and
        // total turnaround time
        for (int i = 0; i < n; i++) {
            total_wt = total_wt + wt[i];
            total_tat = total_tat + tat[i];
            // System.out.println(" " + proc[i].processID + "\t\t" + proc[i].burstime +
            // "\t\t " + wt[i] + "\t\t" + tat[i]);
            System.out.printf("|%10d |%11d |%13d |%16d|\n", proc[i].processID, proc[i].burstime, wt[i], tat[i]);
        }

        System.out.printf("Average waiting time = %.3f", (total_wt / n));
        System.out.println("");
        System.out.printf("Average turn around time = %.3f", (total_tat / n));
    }

    public static void main(String[] args) {
        // Process proc[] = { new Process(1, 6, 1), new Process(2, 8, 1), new Process(3,
        // 7, 2), new Process(4, 3, 3) };

        Scanner sc = new Scanner(System.in); // Create a Scanner object
        System.out.print("Please enter the numbers of process : ");
        int n = sc.nextInt();
        Process proc[] = new Process[n];
        System.out.println("Please enter the process info according this format");
        System.out.println("Process ID (space) Burst-Time (space) Arrival-Time (space) Next Process ID ...");
        for (int i = 0; i < n; i++) {
            proc[i] = new Process(sc.nextInt(), sc.nextInt(), sc.nextInt());
            // proc[i].processID = sc.nextInt(); // Enter process id
            // proc[i].burstime = sc.nextInt(); // Enter burst time
            // proc[i].arrivalTime = sc.nextInt(); // Enter arrival time
        }
        sc.close();

        // Print to file
        String outputFileName = ".\\SJF_output.md";
        try {
            PrintStream fileStream = new PrintStream(outputFileName);
            System.setOut(fileStream);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        findavgTime(proc, proc.length);
    }
}