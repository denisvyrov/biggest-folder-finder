import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ForkJoinPool;

public class Main {
    private static char[] sizeMultipliers = {'B', 'K', 'M', 'G', 'T'};

    public static void main(String[] args) {
        String folderPath = "H:/PrivateMediaArchive";
        File file = new File(folderPath);

        long start = System.currentTimeMillis();
        FolderSizeCalculator calculator = new FolderSizeCalculator(file);
        ForkJoinPool pool = new ForkJoinPool();
        long size = pool.invoke(calculator);
        System.out.println(getHumanReadableSize(size));
        long duration = (System.currentTimeMillis() - start);
        System.out.println(duration + " ms");
    }

    public static String getHumanReadableSize(long size) {
        for (int i = 0; i < sizeMultipliers.length; i++) {
            double number = size / Math.pow(1024, i);
            if (number < 1024) {
                return Math.round(number) + "" + sizeMultipliers[i] + (i > 0 ? "b" : "");
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

    public static HashMap<Character, Integer> getSizeMultipliers() {
        HashMap<Character, Integer> char2Multiplier = new HashMap<>();
        for (int i = 0; i < sizeMultipliers.length; i++) {
            char2Multiplier.put(sizeMultipliers[i], (int) Math.pow(1024, i));
        }
        return char2Multiplier;
    }

    public static long getFolderSize(File folder) {
        if(folder.isFile()) {
            return folder.length();
        }
        long sum = 0;
        File[] files = folder.listFiles();
        for (File file: files) {
            sum += getFolderSize(file);
        }
        return sum;
    }
}
