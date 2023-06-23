public class TwinPrimes {
    public static int slowCountTwinPrimes(int upTo) {
        int count = 0;
        for (int i = 3; i <= upTo-2; i++) {
            boolean areTwins = true;
            for (int j = 2; j < i; j++) {
                if (i % j == 0 || (i + 2) % j == 0) {
                    areTwins = false;
                    break;
                }
            }
            if (areTwins) {
                count++;
            }
        }
        return count;
    }
}
