import star_wars.Solution;

public class Main {

    public static void main(String[] args) {
        final String name = "Luke Skywalker";
        final Solution solution = new Solution();
        final int totalFilms = solution.run(name);

        System.out.println("Total Films for the Character '" + name + "': " + totalFilms);
    }

}
