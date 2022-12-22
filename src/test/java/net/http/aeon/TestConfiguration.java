package net.http.aeon;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TestConfiguration {

    @Getter
    private int coins;

    public TestConfiguration(int coins) {
        this.coins = coins;
    }
}
