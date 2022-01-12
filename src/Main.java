import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        System.out.println(getSizeFromHumanReadable("235K"));
        System.exit(0);
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
        int index = -1;
        char[] chars = {'K', 'M', 'G', 'T'};
        if (size < 1024) {
            return size + "B";
        }
        double number = size;
        while (number >= 1024) {
            number /= 1024;
            index++;
        }
        return String.format("%d%s", Math.round(number), chars[index] + "b");
    }

    public static long getSizeFromHumanReadable(String size) {
        HashMap<Character, Integer> char2multiplier = getMultipliers();
        char sizeFactor = size.replaceAll("[0-9\\s+]+", "").charAt(0);
        int multiplier = char2multiplier.get(sizeFactor);
        return multiplier * Long.parseLong(size.replaceAll("[^0-9]", ""));
    }

    public static HashMap<Character, Integer> getMultipliers() {
        char[] multipliers = {'B', 'K', 'M', 'G', 'T'};
        HashMap<Character, Integer> char2Multiplier = new HashMap<>();
        for (int i = 0; i < multipliers.length; i++) {
            char2Multiplier.put(multipliers[i], (int) Math.pow(1024, i));
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
