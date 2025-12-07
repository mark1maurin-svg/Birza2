package Pac1;

import Utils.InputUtils;
import Utils.OutputUtils;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import static Pac1.ResponseVacancy.*;
import static Pac1.UserLogger.userId;

/**
 * Класс MenuUser предназначен для реализации пользовательского интерфейса,
 * включающего операции по управлению резюме пользователя.
 */
public class MenuUser {

    /**
     * Статический внутренний класс, реализующий функциональность меню для пользователя.
     */
    static class UserMenuAdmin extends MenuAdmin {
        @Override
        public void show() throws IOException {
            boolean exit = false;
            while (!exit) {
                printMenu();
                int choice = InputUtils.getInt("Выберите действие (0-3): ");
                switch (choice) {
                    case 1:
                        resume();
                        break;
                    case 2:
                        ResumeReader.viewAllResume(userId);
                        break;
                    case 3:
                        viewVacancies();
                        break;
                    case 0:
                        exit = true;
                        break;
                    default:
                        OutputUtils.printError("Некорректный выбор. Пожалуйста, выберите от 0 до 3.");
                }
            }
        }

        /**
         * Метод служит для вывода на экран текстового меню с перечнем доступных пользователю команд или действий.
         */
        private void printMenu() {
            OutputUtils.printMessage("--- Главное меню ---");
            OutputUtils.printMessage("1. Добавить резюме");
            OutputUtils.printMessage("2. Просмотр резюме ");
            OutputUtils.printMessage("3. Откликнуться на вакансию");
            OutputUtils.printMessage("0. Выход");
        }

        /**
         * Метод для добавления или обновления резюме пользователя.
         * Производится проверка наличия предыдущего резюме с таким же ID и обновление или создание нового.
         *
         * @throws IOException если возникают ошибки ввода-вывода.
         */
        public static void resume() throws IOException {
            String nameResume = InputUtils.getString("Введите ФИО: ");
            String contactResume = InputUtils.getString("Введите номер телефона: ");
            String postResume = InputUtils.getString("Введите ранее занимаемую должность: ");
            String experienceResume = InputUtils.getString("Введите стаж работы в прежней должности: ");
            String dutiesResume = InputUtils.getString("Введите обязанности, выполняемые на прежней работе: ");

            if (nameResume.isEmpty() || contactResume.isEmpty() ||
                    postResume.isEmpty() || experienceResume.isEmpty() || dutiesResume.isEmpty()) {
                OutputUtils.printError("Поля резюме не могут быть пустыми.");
                return;
            }
            int currentId = userId;

            List<String[]> allResumes = viewAllResumesFile();

            boolean exists = false;
            for (String[] resume : allResumes) {
                if (Integer.parseInt(resume[0]) == currentId) {
                    exists = true;
                    break;
                }
            }

            if (exists) {
                removeResume(allResumes, currentId);
            }

            addNewResume(allResumes, currentId, nameResume, contactResume, postResume, experienceResume, dutiesResume);

            writeResumes(allResumes);

            OutputUtils.printMessage("Резюме успешно добавлено!");
        }
        /**
         * Удаляет резюме по-заданному ID из списка резюме.
         *
         * @param resumes список резюме.
         * @param id      ID резюме для удаления.
         */
        private static void removeResume(List<String[]> resumes, int id) {
            for (int i = 0; i < resumes.size(); i++) {
                if (Integer.parseInt(resumes.get(i)[0]) == id) {
                    resumes.remove(i);
                    break;
                }
            }
        }
        /**
         * Создает новое резюме и добавляет его в список резюме.
         *
         * @param resumes список резюме.
         * @param id      ID нового резюме.
         * @param fields  массив полей резюме.
         */
        private static void addNewResume(List<String[]> resumes, int id, String... fields) {
            String[] newResumeData = new String[fields.length + 1];
            newResumeData[0] = Integer.toString(id); // Преобразуем id в строку
            System.arraycopy(fields, 0, newResumeData, 1, fields.length);
            resumes.add(newResumeData);
        }
        /**
         * Возвращает список всех резюме из файла 'resume.txt'.
         *
         * @return список всех резюме в виде строкового массива.
         * @throws IOException если возникнут ошибки при доступе к файлу.
         */
        private static List<String[]> viewAllResumesFile() throws IOException {
            List<String[]> resumes = new ArrayList<>();
            File file = new File("resume.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            try (BufferedReader reader = new BufferedReader(new FileReader("resume.txt"))) {
                String line;
                List<String> tempResume = new ArrayList<>();
                while ((line = reader.readLine()) != null) {
                    if ("---".equals(line)) {
                        if (!tempResume.isEmpty()) {
                            resumes.add(tempResume.toArray(new String[0]));
                            tempResume.clear();
                        }
                    } else {
                        tempResume.add(line);
                    }
                }
                if (!tempResume.isEmpty()) {
                    resumes.add(tempResume.toArray(new String[0]));
                }
            }
            return resumes;
        }

        /**
         * Метод для сохранения списка резюме обратно в файл.
         *
         * @param resumes список резюме.
         * @throws IOException в случае проблем с записью в файл.
         */
        private static void writeResumes(List<String[]> resumes) throws IOException {
            try (PrintWriter writer = new PrintWriter(new FileWriter("resume.txt"))) {
                for (String[] resume : resumes) {
                    for (String field : resume) {
                        writer.println(field);
                    }
                    writer.println("---");
                }
            }
        }

        /**
         * Вспомогательный класс для чтения и отображения резюме конкретного пользователя.
         */
        public static class ResumeReader {
            /**
             * Метод читает все резюме из файла и выводит на экран резюме указанного пользователя.
             *
             * @param userId Уникальный идентификатор пользователя, чьё резюме нужно вывести.
             * @throws IOException Исключение, возникающее при проблемах с доступом к файлу.
             */
            static void viewAllResume(int userId) throws IOException { // Добавляем аргумент userId
                try (BufferedReader reader = new BufferedReader(new FileReader("resume.txt"))) {
                    String line;

                    while ((line = reader.readLine()) != null) {
                        if (line.trim().isEmpty()) {
                            continue;
                        }
                        try {
                            int currentId = Integer.parseInt(line.trim());
                            String fio = reader.readLine();
                            String phone = reader.readLine();
                            String position = reader.readLine();
                            String experience = reader.readLine();
                            String duties = reader.readLine();


                            if (currentId == userId) {
                                System.out.println("ID: " + currentId);
                                System.out.println("ФИО: " + fio);
                                System.out.println("Телефон: " + phone);
                                System.out.println("Должность: " + position);
                                System.out.println("Опыт работы: " + experience);
                                System.out.println("Обязанности: " + duties);
                                System.out.println();
                            }
                        } catch (NumberFormatException _) { //Игнорируем ошибку формата числа, если такое произошло

                        }
                    }
                }
            }
        }
    }
}


