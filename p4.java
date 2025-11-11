
import java.util.*;

class SimpleCRC {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter number of data bits: ");
            int n = sc.nextInt();
            int[] data = new int[n];
            for (int i = 0; i < n; i++) {
                data[i] = sc.nextInt();
            }

            System.out.print("Enter number of bits in divisor: ");
            int m = sc.nextInt();
            int[] div = new int[m];
            for (int i = 0; i < m; i++) {
                div[i] = sc.nextInt();
            }

            int total = n + m - 1;
            int[] dividend = new int[total];
            System.arraycopy(data, 0, dividend, 0, n);

            int[] rem = divide(Arrays.copyOf(dividend, dividend.length), div);
            int[] crc = new int[total];
            for (int i = 0; i < total; i++) {
                crc[i] = dividend[i] ^ rem[i];
            }

            System.out.print("Transmitted CRC code: ");
            for (int b : crc) {
                System.out.print(b);
            }
            System.out.println("\nEnter received CRC code (" + total + " bits):");
            for (int i = 0; i < total; i++) {
                crc[i] = sc.nextInt();
            }

            int[] chk = divide(Arrays.copyOf(crc, crc.length), div);
            boolean ok = true;
            for (int x : chk) {
                if (x != 0) {
                    ok = false;
                    break;
                }
            }
            System.out.println(ok ? "No Error - Data received correctly." : "Error detected in received data!");
        }
    }

    static int[] divide(int[] a, int[] d) {
        int cur = 0;
        int n = a.length, m = d.length;
        while (n - cur >= m) {
            for (int i = 0; i < m; i++) {
                a[cur + i] ^= d[i];
            }
            while (cur < n && a[cur] == 0) {
                cur++;
            }
        }
        return a;
    }
}
