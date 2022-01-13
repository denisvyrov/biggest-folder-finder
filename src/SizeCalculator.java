import java.util.HashMap;

public class SizeCalculator {
    private static char[] sizeMultipliers = {'B', 'K', 'M', 'G', 'T'};
    private static HashMap<Character, Integer> char2Multiplier = new HashMap<>();

    public static String getHumanReadableSize(long size) {
        for (int i = 0; i < sizeMultipliers.length; i++) {
            double number = size / Math.pow(1024, i);
            if (number < 1024) {
                return Math.round(number * 100) / 100.0 + "" + sizeMultipliers[i] + (i > 0 ? "b" : "");
            }
        }
        return "";
    }

    public static long getSizeFromHumanReadable(String size) {
        HashMap<Character, Integer> char2multiplier = getSizeMultipliers();
        char sizeFactor = size.replaceAll("[0-9\\s+]+", "").charAt(0);
        int multiplier = char2multiplier.get(sizeFactor);
        return multiplier * Long.parseLong(size.replaceAll("[^0-9]", ""));
    }

    private static HashMap<Character, Integer> getSizeMultipliers() {
        for (int i = 0; i < sizeMultipliers.length; i++) {
            char2Multiplier.put(sizeMultipliers[i], (int) Math.pow(1024, i));
        }
        return char2Multiplier;
    }
}
