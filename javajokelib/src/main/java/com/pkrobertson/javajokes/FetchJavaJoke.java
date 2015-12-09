package com.pkrobertson.javajokes;

/**
 * FetchJavaJoke -- simple class that returns a random joke. Jokes were derived from a family-
 *     friendly website that offered "sharable" jokes.
 */

public class FetchJavaJoke {
    private static int lastIndex = -1;

    private static String[] randomJokes = {
        "Mountain man definition of HARD DRIVE: Gettin home in the winter time.",
        "Mountain man definition of BYTE: Whut them dang flys do.",
        "Mountain man definition of DOT MATRIX: Old Dan Matrix's wife.",
        "Mountain man definition of PORT: Fancy Flatlander wine",
        "Mountain man definition of RANDOM ACCESS MEMORY: Wen ya cain't 'member whut ya paid fer the rifle when yore wife asks.",
        "You might be a redneck Jedi if your Jedi robe is camouflage.",
        "You might be a redneck Jedi if at least one wing of your X-Wings is primer colored.",
        "You might be a redneck Jedi if you can easily describe the taste of an Ewok.",
        "You might be a redneck Jedi if you have ever had a land-speeder up on blocks in your yard.",
        "You might be a redneck Jedi if the worst part of spending time on Dagobah is the dadgum skeeters.",
        "You might be a redneck Jedi if Wookies are offended by your B.O.",
        "You might be a redneck Jedi if you have ever used the force in conjunction with fishing/bowling.",
        "You might be a redneck Jedi if you have a cousin who bears a strong resemblance to Chewbacca."
    };

    /**
     * fetchRandomJoke -- return one of the above joke strings, the while loop is used to make sure
     *     the same joke is not returned on successive calls.
     * @return String containing the "random" joke
     */
    public static String fetchRandomJoke () {
        int index;
        do {
            index = (int) Math.floor(Math.random() * randomJokes.length);
            if (index >= randomJokes.length) {
                index = randomJokes.length - 1;
            }
        } while (index != lastIndex);

        lastIndex = index;
        return (randomJokes[index]);
    }
}
