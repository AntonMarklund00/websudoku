package helper;

import java.util.Random;

public final class Randomizer {
    public static final Randomizer INSTANCE = new Randomizer();
    private final Random generator = new Random(); 
    

    private int produceRandomValue(final int min, final int max) {
        int bounds = (max - min) + 1; 
        return generator.nextInt(bounds) + min;
    }

    public int getRandomValue(final int max) {
        return produceRandomValue(1, max);
    }

}
