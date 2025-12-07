// Программа для имитации работы биржи труда
package Pac1;

import java.io.IOException;
import java.util.Scanner;

/**
 * Класс {@code Main}, основной класс приложения, содержащий точку входа ({@code main}) и выполняющий аутентификацию пользователя.
 */
public class Main {

    /**
     * Объект класса {@link UserLogger}, используемый для получения ID текущего  пользователя.
     */
    public static UserLogger UserLogger;

    /**
     * Точка входа программы. Выполняет процедуру ввода логина и пароля пользователя,
     * проверяя учетные данные до успешного входа.
     *
     * @throws IOException исключение ввода-вывода, которое может возникнуть при работе с потоками ввода-вывода.
     */
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        boolean authenticated = false;

        // цикл проверки логина и пароля до успешной аутентификации
        while (!authenticated) {
            System.out.println("Введите логин:");
            String usernameInput = in.nextLine();

            System.out.println("Введите пароль:");
            String passwordInput = in.nextLine();

            // инициализация списка зарегистрированных пользователей
            UserClass ua = new UserClass();
            ua.initializeUsers();

            // проверка введенных данных по каждому зарегистрированному пользователю
            for (int i = 0; i < UserClass.size(); i++) {
                User currentUser = UserClass.get(i); // получение текущего пользователя

                //  аутентификации и отображение соответствующего меню пользователю
                if (currentUser.authenticate(usernameInput, passwordInput)) {
                    System.out.println("Вход успешен.");

                    // создание экземпляра UserLogger для получения id пользователя
                    UserLogger logger = new UserLogger(currentUser.getId());

                    // вывод меню в зависимости от типа пользователя (администратор, пользователь)
                    currentUser.getMenu().show();

                    authenticated = true;
                    break;
                }
            }

            // сообщение об ошибке при попытке входа
            if (!authenticated) {
                System.out.println("Неверный логин или пароль. Повторите попытку.");
            }
        }
    }
}
