package football_session.domain;

import java.util.List;

public class TournamentInformation {

    private String name;
    private List<Round> rounds;

    public String getName() {
        return name;
    }

    public List<Round> getRounds() {
        return rounds;
    }

}
