package football_session.service;

import football_session.domain.Match;
import football_session.domain.Round;
import football_session.domain.TournamentInformation;
import football_session.utils.HTTPUtils;
import football_session.utils.json.JSONUtils;

public class TournamentService {

    /**
     * Fetches tournament data from a remote JSON endpoint, parses it,
     * and calculates the total goals scored by the specified team.
     *
     * @param teamKey The key of the team for which to calculate total goals.
     * @return The total goals scored by the specified team.
     * <p>
     * Time Complexity: O(F + P + R × M)
     * - F: Time to fetch the JSON data.
     * - P: Time to parse the JSON data.
     * - R: Number of rounds.
     * - M: Total number of matches across all rounds.
     * <p>
     * Space Complexity: O(S)
     * - S: Size of the JSON data.
     */
    public int getTeamTotalGoals(final String teamKey) {
        final String tournamentInformationLink =
                "https://s3.eu-west-1.amazonaws.com/hackajob-assets1.p.hackajob/challenges/football_session/football.json";
        final String tournamentInformationJson = HTTPUtils.fetchResponseBody(tournamentInformationLink);
        final TournamentInformation tournamentInformation = JSONUtils.toJsonObject(TournamentInformation.class, tournamentInformationJson);

        return calculateTotalGoals(teamKey, tournamentInformation);
    }

    /**
     * Calculates the total goals scored by the specified team in the tournament.
     *
     * @param teamKey The key of the team for which to calculate total goals.
     * @param tournamentInformation The tournament information containing rounds and matches.
     * @return The total goals scored by the specified team.
     * <p>
     * Time Complexity: O(R × M)
     * - R: Number of rounds.
     * - M: Total number of matches across all rounds.
     * <p>
     * Space Complexity: O(1)
     * - The space complexity is constant because we only use a fixed amount of additional space (the accumulator totalGoals).
     */
    private int calculateTotalGoals(final String teamKey, final TournamentInformation tournamentInformation) {
        int totalGoals = 0;

        for (Round round : tournamentInformation.getRounds()) {
            for (Match match : round.getMatches()) {
                if (match.getTeam1().getKey().equals(teamKey)) {
                    totalGoals += match.getScore1();
                } else if (match.getTeam2().getKey().equals(teamKey)) {
                    totalGoals += match.getScore2();
                }
            }
        }

        return totalGoals;
    }

    /* Option 1
        final Map<String, Integer> teamScore = tournamentInformation.getRounds()
                .stream()
                .flatMap(round -> round.getMatches().stream())
                .flatMap(match -> Stream.of(
                        new AbstractMap.SimpleEntry<>(match.getTeam1().getKey(), match.getScore1()),
                        new AbstractMap.SimpleEntry<>(match.getTeam2().getKey(), match.getScore2())
                ))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        Integer::sum
                ));

        return teamScore.get(teamKey);
     */

    /* Option 2
        return tournamentInformation.getRounds()
                .stream()
                .flatMap(round -> round.getMatches().stream())
                .flatMap(match -> Stream.of(
                        new AbstractMap.SimpleEntry<>(match.getTeam1().getKey(), match.getScore1()),
                        new AbstractMap.SimpleEntry<>(match.getTeam2().getKey(), match.getScore2())
                ))
                .filter(entry -> entry.getKey().equals(teamKey))
                .mapToInt(Map.Entry::getValue)
                .sum();


         Summary
            Time Complexity: 𝑂(𝐹+𝑃+𝑅×𝑀)
            Space Complexity: 𝑂(𝑆+𝑅×𝑀)
            Here:
            * 𝐹 - is the time to fetch the JSON data.
            * 𝑃 - is the time to parse the JSON data.
            * 𝑅 - is the number of rounds.
            * 𝑀 - is the average number of matches per round.
            * 𝑆 - is the size of the JSON data.
     */

    /* Option 3
        return tournamentInformation.getRounds()
                .stream()
                .flatMap(round -> round.getMatches().stream())
                .mapToInt(match -> {
                    int goals = 0;

                    if (match.getTeam1().getKey().equals(teamKey)) {
                        goals += match.getScore1();
                    }

                    if (match.getTeam2().getKey().equals(teamKey)) {
                        goals += match.getScore2();
                    }

                    return goals;
                })
                .sum();


         Summary
            Time Complexity: 𝑂(𝐹+𝑃+𝑅×𝑀)
            Space Complexity: 𝑂(S)
            Here:
            * 𝐹 - is the time to fetch the JSON data.
            * 𝑃 - is the time to parse the JSON data.
            * 𝑅 - is the number of rounds.
            * 𝑀 - is the average number of matches per round.
            * 𝑆 - is the size of the JSON data.
     */

}
