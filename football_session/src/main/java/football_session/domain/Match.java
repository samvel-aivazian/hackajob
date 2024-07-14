package football_session.domain;

public class Match {

    private String date;
    private Team team1;
    private Team team2;
    private int score1;
    private int score2;

    public String getDate() {
        return date;
    }

    public Team getTeam1() {
        return team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public int getScore1() {
        return score1;
    }

    public int getScore2() {
        return score2;
    }

}
