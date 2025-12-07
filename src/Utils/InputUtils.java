package Utils;

import java.util.Scanner;

/**
 * Класс для обработки пользовательского ввода.
 * Предоставляет методы для безопасного считывания строковых значений и целых чисел.
 */
public class InputUtils {
    private static final Scanner scanner;

    /**
     * Метод получает строку от пользователя с заданным приглашением.
     *
     * @param prompt приглашение, которое выводится перед ожиданием ввода.
     * @return строка, введённая пользователем.
     */
    public static String getString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    /**
     * Метод запрашивает у пользователя положительное целое число с указанным приглашением.
     * Обеспечивает повторный запрос в случае некорректного ввода.
     *
     * @param prompt приглашение, которое выводится перед ожиданием ввода.
     * @return положительное целое число, введённое пользователем.
     */
    public static int getInt(String prompt) {
        while(true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int number = Integer.parseInt(input);
                if (number >= 0) {
                    return number;
                }

                System.out.println("номер ID должен быть представлен в виде числа.");
            } catch (NumberFormatException var4) {
                System.out.println("Некорректный ввод. Пожалуйста, введите целое число.");
            }
        }
    }

    static {
        scanner = new Scanner(System.in);
    }
}

