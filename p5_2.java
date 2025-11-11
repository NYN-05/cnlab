
import java.io.IOException;
import java.net.*;
import java.util.*;

class SimpleReceiver {

    static final int TOTAL = 10;
    static final Random R = new Random();

    public static void main(String[] args) throws IOException {
        try (DatagramSocket s = new DatagramSocket(9876)) {
            int expect = 0;
            System.out.println("Receiver ready. Waiting for frames...\n");
            while (true) {
                var buf = new byte[128];
                var p = new DatagramPacket(buf, buf.length);
                s.receive(p);
                String msg = new String(p.getData(), 0, p.getLength()).trim();
                int f = Integer.parseInt(msg.split(" ")[1]);

                if (R.nextInt(10) < 8) {
                    if (f == expect) {
                        System.out.println("Received: " + msg);
                        expect++;
                    } else {
                        System.out.println("Out-of-order frame received: " + f);
                    }

                    var ack = ("ACK " + f).getBytes();
                    s.send(new DatagramPacket(ack, ack.length, p.getAddress(), p.getPort()));
                    System.out.println("Sent: ACK " + f + "\n");
                } else {
                    System.out.println("Simulated loss for Frame " + f + "\n");
                }

                if (f == TOTAL - 1) {
                    break;
                }
            }
            System.out.println("✅ All frames processed. Receiver closing.");
        }
    }
}
