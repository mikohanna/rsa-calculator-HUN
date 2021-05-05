package rsarsa;

import java.util.Scanner;
import java.util.HashSet;

public class Main {

    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        String anotherRound = "";
        System.out.println("");
        System.out.println("*****RSA kalkulátor*****");
        do {
            System.out.print("p = ");
            long p = numberCheckPrime();
            System.out.print("q = ");
            long q = numberCheckPrime();
            HashSet<Long> possibleValuesOfE = properValuesOfE((q - 1) * (p - 1));
            System.out.println("e lehetséges értékei:\n" + possibleValuesOfE.toString());
            System.out.print("e = ");
            long e = numberCheckE(possibleValuesOfE);
            RSA rsa = new RSA(p, q, e);
            System.out.println("mod: " + rsa.calculateModularExponent(194, 39259, 47053));
            System.out.println("d = " + rsa.getD() + "\ntitkosítani vagy visszafejteni kellene?");
            boolean isDecrypt = scan.nextLine().toLowerCase().contains("vissza");
            if (isDecrypt) {
                System.out.print("a titkosított üzenet: ");
                long c = numberCheck();
                System.out.println("a visszafejtett üzenet: " + rsa.decrypt(c));
            } else {
                System.out.print("a titkosítandó üzenet: ");
                long m = numberCheck();
                System.out.println("a titkosított üzenet: " + rsa.encrypt(m));
            }
            System.out.print("Még egy kör? -->");
            anotherRound = scan.nextLine();
        } while (anotherRound.toLowerCase().contains("igen"));

    }

    public static long numberCheckPrime() {
        long num = numberCheck();
        if (!isPrime(num)) {
            System.out.print("Csak prímszámot lehet megadni!--> ");
            return numberCheckPrime();
        }
        return num;
    }

    public static long numberCheckE(HashSet<Long> valuesOfE) {
        long num = numberCheck();
        if (!valuesOfE.contains(num)) {
            System.out.print("Csak a megadott listából lehet számot választani!--> ");
            return numberCheckE(valuesOfE);
        }
        return num;
    }

    public static long numberCheck() {
        long num = -1;
        while (num == -1) {
            String input = scan.nextLine();
            try {
                num = Long.parseLong(input);
            } catch (Exception e) {
                System.out.print("Csak számot lehet megadni!--> ");
            }
        }
        return num;
    }

    public static boolean isPrime(long n) {
        if (n < 2) {
            return false;
        }
        for (long i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static HashSet<Long> properValuesOfE(long phi) {
        HashSet<Long> values = new HashSet<>();
        for (long i = 2; i < phi; i++) {
            long num = findCoprimes(phi, i);
            if (num == 1) {
                values.add(i);
            }
        }
        return values;
    }

    public static long findCoprimes(long num1, long num2) {
        if (num1 % num2 == 0) {
            return num2;
        }
        return findCoprimes(num2, num1 % num2);
    }

}
