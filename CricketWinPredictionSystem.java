import java.util.Scanner;

public class CricketWinPredictionSystem {

    private static final int TOTAL_OVERS = 2; // Assuming a standard 2-over match for testing
    private static final int BALLS_PER_OVER = 6;
    private static int team1Score;
    private static int team2Score;
    private static int targetScore;
    private static double currentRunRate;
    private static double requiredRunRate;
    private static int wicketsLost;

    // Calculate run rate based on current score and balls played
    public static double calculateRunRate(double score, int overs) {
        return (score / overs);
    }

    // Predict score at the end of the match based on current run rate
    public static double predictScore(double currentRunRate) {
        return currentRunRate * TOTAL_OVERS;
    }

    // Calculate win probability for each team
    public static double calculateWinProbability(int target, double currentScore, int wickets, int oversLeft) {
        double probability;

        // Simple probability formula (just an example)
        probability = (currentScore / target) * 100 - (wickets * 5) + (oversLeft * 2);

        if (probability < 0) probability = 0;
        if (probability > 100) probability = 100;

        return probability;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // First Innings - Team 1
        System.out.println("=== First Innings for Team 1 ===");
        team1Score = 0;
        int ballsPlayedInFirstInnings = 0;
        int wicketsLostTeam1 = 0; // Track wickets for Team 1

        for (int oversPlayed = 1; oversPlayed <= TOTAL_OVERS; oversPlayed++) {
            System.out.println("Over Number: " + oversPlayed);
            for (int balls = 1; balls <= BALLS_PER_OVER; balls++) {
                String runs;
                boolean validInput = false;

                // Keep prompting until valid input is given
                while (!validInput) {
                    System.out.print("Enter runs scored on ball " + balls + " : ");
                    runs = scanner.next();

                    // Check if the input is a wicket
                    if (runs.equalsIgnoreCase("w")) {
                        wicketsLostTeam1++;
                        System.out.println("Score: " + team1Score + "-" + wicketsLostTeam1);
                        validInput = true;
                    } else {
                        try {
                            int runsScored = Integer.parseInt(runs);
                            if (runsScored == 0 || runsScored == 1 || runsScored == 2 || runsScored == 3 || runsScored == 4 || runsScored == 6) {
                                team1Score += runsScored;
                                System.out.println("Score: " + team1Score + "-" + wicketsLostTeam1);
                                ballsPlayedInFirstInnings++;
                                validInput = true;
                            } else {
                                System.out.println("Invalid input! Please enter valid runs (0, 1, 2, 3, 4, 6) or 'w' for wicket.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input! Please enter valid runs (0, 1, 2, 3, 4, 6) or 'w' for wicket.");
                        }
                    }
                }
            }

            currentRunRate = calculateRunRate(team1Score, oversPlayed);
            double predictedScore = predictScore(currentRunRate);
            System.out.println("Current Score\tCurrent Run Rate\tPredicted Score ");
            System.out.print("\t" + team1Score + "\t\t" + currentRunRate + "\t\t" + predictedScore);
            System.out.println();
        }

        // Set target score for team 2
        targetScore = (int) team1Score + 1;
        System.out.println("End of First Innings - Team 1's Score: " + team1Score);
        System.out.println("Target for Team 2: " + targetScore);

        // Second Innings - Team 2
        System.out.println("=== Second Innings for Team 2 ===");
        team2Score = 0;
        int wicketsLostTeam2 = 0; // Track wickets for Team 2

        for (int oversPlayed = 1; oversPlayed <= TOTAL_OVERS; oversPlayed++) {
            System.out.println("Over Number: " + oversPlayed);
            for (int balls = 1; balls <= BALLS_PER_OVER; balls++) {
                String runs;
                boolean validInput = false;

                // Keep prompting until valid input is given
                while (!validInput) {
                    System.out.print("Enter runs scored on ball " + balls + " : ");
                    runs = scanner.next();

                    // Check if the input is a wicket
                    if (runs.equalsIgnoreCase("w")) {
                        wicketsLostTeam2++;
                        System.out.println("Score: " + team2Score + "-" + wicketsLostTeam2);
                        validInput = true;
                    } else {
                        try {
                            int runsScored = Integer.parseInt(runs);
                            if (runsScored == 0 || runsScored == 1 || runsScored == 2 || runsScored == 3 || runsScored == 4 || runsScored == 6) {
                                team2Score += runsScored;
                                System.out.println("Score: " + team2Score + "-" + wicketsLostTeam2);
                                validInput = true;
                            } else {
                                System.out.println("Invalid input! Please enter valid runs (0, 1, 2, 3, 4, 6) or 'w' for wicket.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input! Please enter valid runs (0, 1, 2, 3, 4, 6) or 'w' for wicket.");
                        }
                    }
                }
            }

            if (team2Score >= targetScore) {
                System.out.println("TEAM 2 WON THE MATCH");
                return;
            } else {
                currentRunRate = calculateRunRate(team2Score, oversPlayed);
                requiredRunRate = (targetScore - team2Score) / (TOTAL_OVERS - oversPlayed);
                int team2WinProb = (int) calculateWinProbability(targetScore, team2Score, wicketsLostTeam2, (TOTAL_OVERS - oversPlayed));
                System.out.println("Current Score\tCurrent Run Rate\trequiredRunRate\t team1 Win % \t team2 win %");
                System.out.println(team2Score + "\t\t\t\t" + currentRunRate + "\t\t\t\t" + requiredRunRate + "\t\t\t\t" + (100 - team2WinProb) + "\t\t\t\t" + team2WinProb);
            }
        }
        System.out.println("TEAM 1 WON THE MATCH");
        scanner.close();
    }
}
