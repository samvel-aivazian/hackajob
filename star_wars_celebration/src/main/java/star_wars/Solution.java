package star_wars;

import java.io.*;
import java.net.*;

import java.util.*;
import java.util.stream.Collectors;

import com.google.gson.*;

public class Solution {

    private final Gson GSON;

    public Solution() {
        GSON = new Gson();
    }

    public String run(String film, String character) {
        final String charactersAppearedInTheFilm = getCharactersAppearedInTheFilm(film);
        final String filmsWhereTheCharacterAppeared = getFilmsWhereTheCharacterAppeared(character);

        return charactersAppearedInTheFilm + filmsWhereTheCharacterAppeared;
    }

    private String getCharactersAppearedInTheFilm(String filmTitle) {
        final String filmsResponseBody = fetchResponseBody("https://challenges.hackajob.co/swapi/api/films/?format=json");
        final Films filmsResponse = parseResponseBody(Films.class, filmsResponseBody);
        final Optional<Film> filmObjectOpt = filmsResponse.getResults()
                .stream()
                .filter(film -> film.getTitle().equals(filmTitle))
                .findFirst();

        if (filmObjectOpt.isEmpty()) {
            return filmTitle + ": none; ";
        }

        final List<String> charactersAppearedInTheFilmList = filmObjectOpt.get().getCharacters()
                .stream()
                .map(characterEndpoint -> fetchResponseBody(characterEndpoint + "?format=json"))
                .map(characterResponseBody -> parseResponseBody(Character.class, characterResponseBody).getName())
                .sorted()
                .collect(Collectors.toList());

        final String charactersAppearedInTheFilm = String.join(", ", charactersAppearedInTheFilmList);

        return filmTitle + ": " + charactersAppearedInTheFilm + "; ";
    }

    private String getFilmsWhereTheCharacterAppeared(String characterName) {
        Character character = findCharacter(characterName);
        if (character == null) {
            return characterName + ": none";
        }

        final List<String> filmsWhereTheCharacterAppearedList = character.getFilms()
                .stream()
                .map(filmEndpoint -> fetchResponseBody(filmEndpoint + "?format=json"))
                .map(filmResponseBody -> parseResponseBody(Film.class, filmResponseBody).getTitle())
                .sorted()
                .collect(Collectors.toList());

        final String filmsWhereTheCharacterAppeared = String.join(", ", filmsWhereTheCharacterAppearedList);

        return characterName + ": " + filmsWhereTheCharacterAppeared;
    }

    private Character findCharacter(String characterName) {
        String swapiLink = "https://challenges.hackajob.co/swapi/api/people?format=json";

        while (true) {
            String charactersResponseBody = fetchResponseBody(swapiLink);
            Characters appearedCharacters = parseResponseBody(Characters.class, charactersResponseBody);

            for (Character character : appearedCharacters.getResults()) {
                if (character.getName().equalsIgnoreCase(characterName)) {
                    return character;
                }
            }

            if (appearedCharacters.getNext() == null) {
                break;
            } else {
                swapiLink = appearedCharacters.getNext() + "&format=json";
            }
        }

        return null;
    }

    private String fetchResponseBody(String link) {
        try {
            StringBuilder result = new StringBuilder();

            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();

            return result.toString();
        } catch (Exception e) {
            return null;
        }
    }

    private <T> T parseResponseBody(Class<T> jsonClass, String response) {
        JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();

        return GSON.fromJson(jsonObject, jsonClass);
    }

}

class Films {

    private List<Film> results;

    public List<Film> getResults() {
        return results;
    }

}

class Film {

    private String title;
    private List<String> characters;

    public String getTitle() {
        return title;
    }

    public List<String> getCharacters() {
        return characters;
    }

}

class Characters {

    private String next;
    private List<Character> results;

    public String getNext() {
        return next;
    }

    public List<Character> getResults() {
        return results;
    }

}

class Character {

    private String name;
    private List<String> films;

    public String getName() {
        return name;
    }

    public List<String> getFilms() {
        return films;
    }

}