package net.http.aeon;

import lombok.Getter;
import net.http.aeon.annotations.CommentedArgument;

@Getter
@CommentedArgument(comment = {"Das ist eine Beschreibung der Klasse", "a"})
public final class TestConfiguration {

    private final int coins;
    private final boolean state = false;

    public TestConfiguration(int coins) {
        this.coins = coins;
    }

}
