package football_session.domain;

import java.util.List;

/**
 * The Round class represents a round in a football tournament.
 * A round typically contains multiple matches and has a name to identify it.
 */
public class Round {

    private String name;
    private List<Match> matches;

    /**
     * Gets the name of the round.
     *
     * @return the name of the round.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the list of matches in the round.
     *
     * @return a list of Match objects representing the matches in the round.
     */
    public List<Match> getMatches() {
        return matches;
    }

}

