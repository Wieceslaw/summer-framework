package example.beans;

public class Sender {
    private final String ipAddress;
    private final Integer repeat;

    public Sender(String ipAddress, Integer repeat) {
        this.ipAddress = ipAddress;
        this.repeat = repeat;
    }

    public void sendMessage() {
        for (int i = 0; i < repeat; i++) {
            System.out.printf("Sending message to %s \n", ipAddress);
        }
    }
}
