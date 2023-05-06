package example.beans;

public class Receiver {
    private final Integer n;
    private final String name;

    public Receiver(Integer n, String name) {
        this.n = n;
        this.name = name;
    }

    public Integer getN() {
        return n;
    }

    public String getName() {
        return name;
    }
}
