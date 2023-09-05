import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class NicknameGeneration {
    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    static AtomicInteger beautifulWords3 = new AtomicInteger(0);
    static AtomicInteger beautifulWords4 = new AtomicInteger(0);
    static AtomicInteger beautifulWords5 = new AtomicInteger(0);

    public static void main(String[] args) {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread thread1 = new Thread(() -> {
            for (String text : texts) {
                if (isBeautifulPalindrome(text)) {
                    incrementBeautifulWords(text.length());
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            for (String text : texts) {
                if (isBeautifulRepeating(text)) {
                    incrementBeautifulWords(text.length());
                }
            }
        });

        Thread thread3 = new Thread(() -> {
            for (String text : texts) {
                if (isBeautifulOrdered(text)) {
                    incrementBeautifulWords(text.length());
                }
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Красивых слов с длиной 3: " + beautifulWords3 + " шт!");
        System.out.println("Красивых слов с длиной 4: " + beautifulWords4 + " шт!");
        System.out.println("Красивых слов с длиной 5: " + beautifulWords5 + " шт!");
    }

    public static boolean isBeautifulPalindrome(String word) {
        int length = word.length();
        int left = 0;
        int right = length - 1;

        while (left < right) {
            if (word.charAt(left) != word.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }

    public static boolean isBeautifulRepeating(String word) {
        char firstChar = word.charAt(0);

        for (int i = 1; i < word.length(); i++) {
            if (word.charAt(i) != firstChar) {
                return false;
            }
        }

        return true;
    }

    public static boolean isBeautifulOrdered(String word) {
        for (int i = 1; i < word.length(); i++) {
            if (word.charAt(i) < word.charAt(i - 1)) {
                return false;
            }
        }

        return true;
    }

    public static void incrementBeautifulWords(int length) {
        if (length == 3) {
            beautifulWords3.incrementAndGet();
        } else if (length == 4) {
            beautifulWords4.incrementAndGet();
        } else if (length == 5) {
            beautifulWords5.incrementAndGet();
        }
    }
}
