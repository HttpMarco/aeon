package net.http.aeon.io;

public abstract class DistanceElement {

    private int distance = 0;

    public String space() {
        return " ".repeat(distance * 3);
    }

    public void expand() {
        distance++;
    }

    public void reduce() {
        distance--;
    }

    public String nextLine() {
        return "\n";
    }

    public void blockSet(Runnable runnable) {
        this.expand();
        runnable.run();;
        this.reduce();
    }
}
