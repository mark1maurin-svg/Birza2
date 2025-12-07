package Pac1;

import Utils.InputUtils;
import Utils.OutputUtils;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * Класс {@code MenuAdmin}, представляющий административное меню системы управления вакансиями.
 * Содержит методы для вывода меню администратору и реализации основных операций над вакансиями.
 */
public class MenuAdmin {

    /**
     * Основной метод для показа административного меню и обработки выбора пользователя.
     * Осуществляет управление действиями администратора: добавляет, удаляет и просматривает вакансии.
     *
     * @throws IOException если возникает ошибка ввода-вывода при чтении или записи файлов.
     */
    public void show() throws IOException {}

    /**
     * Внутренний класс, реализующий интерфейс меню администратора.
     * Отвечает за отображение пунктов меню и обработку действий администратора.
     */
    static class AdminMenuAdmin extends MenuAdmin {
        @Override
        public void show() throws IOException {
            boolean exit = false;
            while (!exit) {
                printMenu();
                int choice = InputUtils.getInt("Выберите действие (0-4): ");
                switch (choice) {
                    case 1:
                        vacancy();
                        break;
                    case 2:
                        VacancyReader.viewAllVacancies();
                        break;
                    case 3:
                        deleteVacancy();
                        break;
                    case 4:
                        wievResponseVacancies.wievResponseVacanciesDirectory();
                        break;
                    case 0:
                        exit = true;
                        break;
                    default:
                        OutputUtils.printError("Некорректный выбор. Пожалуйста, выберите от 0 до 4.");
                }
            }
        }

        /**
         * Метод выводит пункты главного меню администратора.
         */
        private void printMenu() {
            OutputUtils.printMessage("--- Главное меню ---");
            OutputUtils.printMessage("1. Добавить вакансию");
            OutputUtils.printMessage("2. Просмотр всех вакансий");
            OutputUtils.printMessage("3. Удалить вакансию");
            OutputUtils.printMessage("4. Просмотр откликов на вакансии");
            OutputUtils.printMessage("0. Выход");
        }
    }

    /**
     * Статическое поле, хранящее следующий уникальный ID для создаваемых вакансий.
     */
    private static int nextId = 1;

    /**
     * Метод создает новую вакансию, запрашивая у пользователя необходимые поля и записывая их в файл.
     *
     * @throws IOException если возникла ошибка при записи в файл.
     */
    static void vacancy() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter("vacancy.txt", true))) {
            String titleVacancy = InputUtils.getString("Введите название вакансии: ");
            String chartVacancy = InputUtils.getString("Введите график работы: ");
            String descriptionVacancy = InputUtils.getString("Введите описание вакансии: ");
            String salaryVacancy = InputUtils.getString("Введите заработную плату вакансии: ");

            if (titleVacancy.isEmpty() || chartVacancy.isEmpty()
                    || descriptionVacancy.isEmpty() || salaryVacancy.isEmpty()) {
                OutputUtils.printError("Поля вакансии не могут быть пустыми.");
                return;
            }

            int currentId = nextId++;

            writer.write(currentId + "");
            writer.write(System.lineSeparator());

            // Запись остальных полей вакансии
            writer.write(titleVacancy);
            writer.write(System.lineSeparator());
            writer.write(chartVacancy);
            writer.write(System.lineSeparator());
            writer.write(descriptionVacancy);
            writer.write(System.lineSeparator());
            writer.write(salaryVacancy);
            writer.write(System.lineSeparator() + "\n");

            OutputUtils.printMessage("Вакансия успешно добавлена!");
        }
    }
// Класс для чтения и отображения всех вакансий из файла

    /**
     * Вспомогательный статический класс для отображения содержимого файла вакансий.
     */
    public static class VacancyReader {
        /**
         * Читает содержимое файла вакансий и показывает каждую запись на экране.
         *
         * @throws IOException Если возникли проблемы с чтением файла.
         */
        public static void viewAllVacancies() throws IOException {
            try (BufferedReader reader = new BufferedReader(new FileReader("vacancy.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty()) {
                        continue;
                    }
                    int currentId = Integer.parseInt(line.trim());
                    System.out.println("ID: " + currentId);
                    System.out.println("Название вакансии: " + reader.readLine());
                    System.out.println("График работы: " + reader.readLine());
                    System.out.println("Описание вакансии: " + reader.readLine());
                    System.out.println("Зарплата: " + reader.readLine());
                    System.out.println("--------------------");
                }
            }
        }
    }

// Класс для удаления вакансий по указанному ID

    /**
     * Метод удаляет вакансию из файла по её уникальному ID.
     */
    public static void deleteVacancy() {
        try (
                BufferedReader reader = new BufferedReader(new FileReader("vacancy.txt"));
                BufferedWriter writer = new BufferedWriter(new FileWriter("TempFile.txt"))
        ) {
            String inputIdStr = InputUtils.getString("Введите ID вакансии для удаления: ");
            int inputId = Integer.parseInt(inputIdStr);

            String currentLine;
            boolean found = false;
            while ((currentLine = reader.readLine()) != null) {
                List<String> blockLines = new ArrayList<>();
                for (int i = 0; i < 5 && currentLine != null; i++) {
                    blockLines.add(currentLine);
                    currentLine = reader.readLine();
                }
                if (!blockLines.isEmpty() && !blockLines.getFirst().isBlank()) {
                    int idFromBlock = Integer.parseInt(blockLines.getFirst());
                    if (idFromBlock != inputId) {
                        for (String line : blockLines) {
                            writer.write(line + System.lineSeparator());
                        }
                    } else {
                        found = true;
                    }
                }
            }

            if (!found) {
                OutputUtils.printError("Вакансия с указанным ID не найдена.");
                return;
            }

            reader.close();
            writer.flush();
            writer.close();

            File oldFile = new File("vacancy.txt");
            File tempFile = new File("TempFile.txt");
            if (oldFile.delete()) {
                boolean success = tempFile.renameTo(oldFile);
                if (!success) {
                    throw new IOException("Ошибка при замене файлов.");
                }
            } else {
                throw new IOException("Невозможно удалить старый файл.");
            }

        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

// Класс для просмотра откликнувшихся кандидатов на вакансии

    /**
     * Вспомогательный статический класс для отображения содержания папки с откликами на вакансии.
     */
    public static class wievResponseVacancies {
        /**
         * Показывает содержание всех текстовых файлов, находящихся в директории откликов на вакансии.
         *
         * @throws IOException Если возникли проблемы с доступом к файлам.
         */
        static void wievResponseVacanciesDirectory() throws IOException {
            Path folderPath = Paths.get("ResponseVacancy");
            List<Path> files = Files.walk(folderPath)
                    .filter(p -> p.toString().endsWith(".txt"))
                    .toList();

            for (Path file : files) {
                String filename = file.getFileName().toString();
                String content = Files.readString(file);
                System.out.println(filename + "\n" + content);
            }
        }
    }
}
