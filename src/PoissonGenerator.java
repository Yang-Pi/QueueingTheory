import java.security.SecureRandom;
import java.util.Random;

public class PoissonGenerator {
    private static Random randomizer = new SecureRandom();

    public static double generateNum(int intensity) {
        return -(1./intensity * Math.log(randomizer.nextDouble()));
    }
}
