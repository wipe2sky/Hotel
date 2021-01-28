package task31;

public class Solution {
    public static void main(String[] args) {
        int number;
        while (true) {
            number = new java.util.Random().nextInt(999);
            if (number >= 100)
                break;
        }
        System.out.println(number);

        int sum = number % 10 + (number % 100) / 10 + number / 100;
        System.out.println(sum);
    }
}
