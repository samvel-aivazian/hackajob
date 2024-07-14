package star_wars;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Solution {

    private final Gson GSON;

    public Solution() {
        GSON = new Gson();
    }

    public int run(String character) {
        boolean allPersonsChecked = false;
        String swapiLink = "https://challenges.hackajob.co/swapi/api/people?format=json";

        int totalFilmCount = 0;

        while (!allPersonsChecked) {
            JsonObject responseBody = fetchResponseBody(swapiLink);
            People appearedPeople = toPeople(responseBody);

            for (Person person : appearedPeople.getResults()) {
                if (person.getName().equalsIgnoreCase(character)) {
                    totalFilmCount = person.getFilmsCount();
                    allPersonsChecked = true;
                    break;
                }
            }

            if (appearedPeople.getNext() == null) {
                allPersonsChecked = true;
            } else {
                swapiLink = appearedPeople.getNext() + "&format=json";
            }
        }

        return totalFilmCount;
    }

    private JsonObject fetchResponseBody(String link) {
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

            JsonObject jsonObject = new JsonParser().parse(result.toString()).getAsJsonObject();

            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private People toPeople(JsonObject json) {
        return GSON.fromJson(json, People.class);
    }

}

class People {

    private int count;
    private String next;
    private String previous;
    private List<Person> results;

    public int getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }

    public List<Person> getResults() {
        return results;
    }

}

class Person {

    private String name;
    private String height;
    private String mass;
    private String hair_color;
    private String skin_color;
    private String eye_color;
    private String birth_year;
    private String gender;
    private String homeworld;
    private List<String> films;
    private List<String> species;
    private List<String> vehicles;
    private List<String> starships;
    private String created;
    private String edited;
    private String url;

    public String getName() {
        return name;
    }

    public String getHeight() {
        return height;
    }

    public String getMass() {
        return mass;
    }

    public String getHair_color() {
        return hair_color;
    }

    public String getSkin_color() {
        return skin_color;
    }

    public String getEye_color() {
        return eye_color;
    }

    public String getBirth_year() {
        return birth_year;
    }

    public String getGender() {
        return gender;
    }

    public String getHomeworld() {
        return homeworld;
    }

    public List<String> getFilms() {
        return films;
    }

    public List<String> getSpecies() {
        return species;
    }

    public List<String> getVehicles() {
        return vehicles;
    }

    public List<String> getStarships() {
        return starships;
    }

    public String getCreated() {
        return created;
    }

    public String getEdited() {
        return edited;
    }

    public String getUrl() {
        return url;
    }

    public int getFilmsCount() {
        return films != null ? films.size() : 0;
    }

}

