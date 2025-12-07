package Pac1;

import java.util.ArrayList;

/**
 * Класс UserClass управляет коллекцией пользователей и предоставляет инструменты для работы с ними.
 */
public class UserClass {
    // Коллекция пользователей
    private static ArrayList<User> users;

    /**
     * Конструктор UserClass, создающий пустой список пользователей.
     */
    public UserClass() {
        users = new ArrayList<>();
    }

    /**
     * Возвращает количество пользователей в коллекции.
     *
     * @return размер коллекции пользователей.
     */
    public static int size() {
        return users.size();
    }

    /**
     * Получает пользователя по индексу.
     *
     * @param index Индекс пользователя в списке.
     * @return Пользователь, соответствующий данному индексу.
     * @throws IndexOutOfBoundsException проверка если индекс выходит за пределы диапазона.
     */
    public static User get(int index) throws IndexOutOfBoundsException {
        return users.get(index);

    }

    /**
     * Инициализирует коллекцию с заранее определёнными пользователями.
     */
    public void initializeUsers() {
        User user = new User("user1", "123", false, 1);
        User user2 = new User("user2", "321", false, 2);
        User user3 = new User("user3", "12", false, 3);
        User admin = new Admin("admin", "777", true);

        users.add(user);
        users.add(user2);
        users.add(user3);
        users.add(admin);
    }
}

/**
 * Базовый класс пользователя, содержащий общую информацию о каждом пользователе.
 */
class User {
    /**
     * The Username.
     */
    protected String username;   // Имя пользователя
    /**
     * The Password.
     */
    protected String password;   // Пароль пользователя
    /**
     * The Is admin.
     */
    protected boolean isAdmin;   // Признак является ли пользователь администратором
    private int id;              // Уникальный идентификатор пользователя

    /**
     * Конструктор User с параметрами имени пользователя, пароля, статуса администратора и идентификатора.
     *
     * @param username имя пользователя.
     * @param password пароль пользователя.
     * @param isAdmin  признак, является ли пользователь администратором.
     * @param id       уникальный идентификатор пользователя.
     */
    public User(String username, String password, boolean isAdmin, int id) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.id = id;
    }

    /**
     * Возвращает объект меню, соответствующий статусу пользователя (администратор / обычный пользователь).
     *
     * @return Экземпляр MenuAdmin, зависящий от роли пользователя.
     */
    public MenuAdmin getMenu() { // Возвращаем меню, соответствующее типу пользователя
        return isAdmin ? new MenuAdmin.AdminMenuAdmin() : new MenuUser.UserMenuAdmin();
    }

    /**
     * Проверяет правильность введённых учётных данных.
     *
     * @param inputUsername введённое имя пользователя.
     * @param inputPassword введённый пароль.
     * @return True, если учётные данные совпадают с хранимыми значениями, иначе False.
     */
    public boolean authenticate(String inputUsername, String inputPassword) {
        return this.username.equals(inputUsername) && this.password.equals(inputPassword);
    }

    /**
     * Геттер для получения имени пользователя.
     *
     * @return Имя пользователя.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Сеттер для установки имени пользователя.
     *
     * @param username Новое имя пользователя.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Геттер для получения пароля пользователя.
     *
     * @return Пароль пользователя.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Сеттер для установки пароля пользователя.
     *
     * @param password Новый пароль пользователя.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Геттер для проверки статуса администратора.
     *
     * @return True, если пользователь является администратором, иначе False.
     */
    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * Сеттер для смены статуса администратора.
     *
     * @param admin Новый статус администратора.
     */
    public void setIsAdmin(boolean admin) {
        this.isAdmin = admin;
    }

    /**
     * Геттер для получения идентификатора пользователя.
     *
     * @return Идентификатор пользователя.
     */
    public int getId() {
        return id;
    }

    /**
     * Сеттер для установки идентификатора пользователя.
     *
     * @param id Новый идентификатор пользователя.
     */
    public void setId(int id) {
        this.id = id;

    }
}

/**
 * Подкласс администратора, расширяющий базовый класс User дополнительными возможностями администратора.
 */
class Admin extends User {
    /**
     * Конструктор Admin, создающий экземпляр администратора.
     *
     * @param username имя пользователя.
     * @param password пароль пользователя.
     * @param isAdmin  признак, является ли пользователь администратором.
     */
    public Admin(String username, String password, boolean isAdmin) {
            super(username, password, isAdmin,-1);{

        }
}
     /**
     * Возвращает объект меню администратора.
     *
     * @return Экземпляр AdminMenuAdmin.
     */
        @Override
        public MenuAdmin getMenu() {
            return new MenuAdmin.AdminMenuAdmin();
        }
    }

/**
 * Класс UserLogger сохраняет идентификатор последнего вошедшего пользователя.
 */
class UserLogger {
    /**
     * The User id.
     */
    static int userId; // Хранит идентификатор последнего вошедшего пользователя

    /**
     * Геттер для получения идентификатора пользователя.
     *
     * @return Текущий идентификатор пользователя.
     */
    public int getId() {
        return userId;
    }

    /**
     * Конструктор UserLogger устанавливает глобальное значение идентификатора пользователя.
     *
     * @param userId Значение идентификатора пользователя.
     */
    public UserLogger(int userId) {
        UserLogger.userId = userId;
    }
}
