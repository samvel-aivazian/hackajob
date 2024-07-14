package football_session;

import football_session.service.TournamentService;

public class Main {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        final String teamKey = "manutd";
        final TournamentService tournamentService = new TournamentService();
        final int totalGoals = tournamentService.getTeamTotalGoals(teamKey);

        System.out.println("Total Goals for the Team '" + teamKey + "': " + totalGoals);

        long timeSpent = System.currentTimeMillis() - startTime;

        System.out.println("Time spent: " + timeSpent);
    }

}
