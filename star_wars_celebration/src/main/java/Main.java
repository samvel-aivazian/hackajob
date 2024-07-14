import star_wars.Solution;

public class Main {

    public static void main(String[] args) {
        final String name = "Luke Skywalker";
        final String title = "The Force Awakens";
        final Solution solution = new Solution();
        final String starWarsCelebration = solution.run(title, name);

        System.out.println(starWarsCelebration);
    }

}
