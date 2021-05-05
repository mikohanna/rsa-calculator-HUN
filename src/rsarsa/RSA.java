package rsarsa;

import java.util.HashSet;
import java.math.BigInteger;

public class RSA {

    private long p;
    private long q;
    private long n;
    private long phi_n;
    private long e;
    private long d;

    public RSA(long p, long q, long e) {
        this.p = p;
        this.q = q;
        n = p * q;
        phi_n = (p - 1) * (q - 1);
        this.e = e;
        d = setValueOf_d(e, phi_n);
    }

    public long getD() {
        return d;
    }

    private static long setValueOf_d(long num, long mod) {
        long answer = 1;
        while ((num * answer) % mod != 1) {
            answer++;
        }
        return answer;
    }

    public long encrypt(long m) {
        return calculateModularExponent(m, e, n);
    }

    public long decrypt(long c) {
        return calculateModularExponent(c, d, n);
    }

    public long calculateModularExponent(long num, long power, long mod) {
        int maxPower = countMaxPower(power);
        HashSet<Integer> powersOf2 = getNsPowersOf2(power, new HashSet<>());
        long answer = 1;
        BigInteger finalAnswer = new BigInteger("1");
        for (int i = 0; i <= maxPower; i++) {
            if (i != 0) {
                answer = (long) Math.pow(answer, 2) % mod;
            } else {
                answer = (long) Math.pow(num, Math.pow(2, 0)) % mod;
            }
            if (powersOf2.contains(i)) {
                finalAnswer = finalAnswer.multiply(BigInteger.valueOf(answer));
            }
        }
        finalAnswer = finalAnswer.mod(BigInteger.valueOf(mod));
        return finalAnswer.longValue();
    }

    private static HashSet<Integer> getNsPowersOf2(long n, HashSet<Integer> powersOfTwo) {
        if (n < 1) {
            return powersOfTwo;
        }
        int maxPower = countMaxPower(n);
        powersOfTwo.add(maxPower);
        return getNsPowersOf2(n - (int) Math.pow(2, maxPower), powersOfTwo);
    }

    private static int countMaxPower(long n) {
        int maxPower = 0;
        while (n >= Math.pow(2, maxPower)) {
            maxPower++;
        }
        return --maxPower;
    }

}
