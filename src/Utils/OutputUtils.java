package Utils;

/**
 * Вспомогательный класс для вывода сообщений и ошибок.
 * Содержит методы, позволяющие выводить информацию и сообщать об ошибках в приложении.
 */
public class OutputUtils {

    /**
     * Вывод обычного сообщения в стандартный поток вывода.
     *
     * @param message сообщение, которое нужно вывести.
     */
    public static void printMessage(String message) {
        System.out.println(message);
    }//: для вывода обычных сообщений.

    /**
     * Вывод сообщения об ошибке в стандартный поток ошибок.
     *
     * @param errorMessage сообщение об ошибке, которое нужно вывести.
     */
    public static void printError(String errorMessage) {// для вывода сообщений об ошибках
        System.err.println("Ошибка: " + errorMessage);
    }

    }


