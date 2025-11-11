
import java.io.IOException;
import java.net.*;

class SimpleSender {

    static final int W = 4, TOTAL = 10;

    public static void main(String[] args) throws IOException {
        try (DatagramSocket s = new DatagramSocket()) {
            InetAddress addr = InetAddress.getLocalHost();
            int nextSend = 0, nextAck = 0;
            boolean[] ack = new boolean[TOTAL];

            while (nextAck < TOTAL) {
                for (int i = 0; i < W && nextSend < TOTAL; i++) {
                    if (!ack[nextSend]) {
                        var msg = ("Frame " + nextSend).getBytes();
                        s.send(new DatagramPacket(msg, msg.length, addr, 9876));
                        System.out.println("Sent: Frame " + nextSend);
                        nextSend++;
                    } else {
                        nextSend++;
                    }
                }

                s.setSoTimeout(2000);
                try {
                    var buf = new byte[128];
                    var p = new DatagramPacket(buf, buf.length);
                    s.receive(p);
                    int a = Integer.parseInt(new String(p.getData(), 0, p.getLength()).trim().split(" ")[1]);
                    System.out.println("Received ACK for Frame " + a);
                    if (a >= 0 && a < TOTAL) {
                        ack[a] = true;
                    }
                    while (nextAck < TOTAL && ack[nextAck]) {
                        nextAck++;
                    }
                } catch (SocketTimeoutException e) {
                    System.out.println("Timeout! Resending from Frame " + nextAck);
                    nextSend = nextAck;
                }
            }
            System.out.println(" All frames sent and acknowledged successfully!");
        }
    }
}
