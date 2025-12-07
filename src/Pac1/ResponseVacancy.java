package Pac1;

import Utils.InputUtils;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static Pac1.UserLogger.userId;

/**
 * Класс ResponseVacancy обрабатывает взаимодействие пользователя с процессом отклика на вакансии.
 * Включает методы для отображения доступных вакансий и отправки откликов на выбранные вакансии.
 */
public class ResponseVacancy {
    /**
     * Счётчик уникальных номеров для новых откликов на вакансии.
     */
    private static int counter = 0;

    /**
     * Метод для отображения доступных вакансий из файла vacancy.txt.
     * Предлагает пользователю ввести ID вакансии, на которую он хочет откликнуться.
     *
     * @throws IOException в случае возникновения проблем с доступом к файлу.
     */
    public static void viewVacancies() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("vacancy.txt"))) {
            List<String[]> vacancies = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                int id = Integer.parseInt(line.trim());
                String title = reader.readLine();       // Название вакансии
                String schedule = reader.readLine();    // График работы
                String description = reader.readLine(); // Описание вакансии
                String salary = reader.readLine();      // Зарплата

                vacancies.add(new String[]{String.valueOf(id), title, schedule, description, salary});
            }

            System.out.println("\nДоступные вакансии:");
            for (String[] v : vacancies) {
                System.out.println("ID: " + v[0]);
                System.out.println("Название вакансии: " + v[1]);
                System.out.println("График работы: " + v[2]);
                System.out.println("Описание вакансии: " + v[3]);
                System.out.println("Зарплата: " + v[4]);
                System.out.println("--------------------");
            }

            String inputIdStr = InputUtils.getString("Введите ID вакансии: ");
            int inputId = Integer.parseInt(inputIdStr);

            Optional<String[]> foundVacancy = vacancies.stream()
                    .filter(v -> Integer.parseInt(v[0]) == inputId)
                    .findFirst();

            if (foundVacancy.isPresent()) {
                saveVacancyFile(foundVacancy.get());
            } else {
                System.out.println("Вакансия с указанным ID не найдена.");
            }
        }
    }

    /**
     * Сохраняет информацию о выбранной вакансии вместе с информацией кандидата в отдельный файл.
            *
            * @param vacancy Массив строк, содержащий информацию о вакансии (ID, название, график, описание, зарплата).
            * @throws IOException Если произошла ошибка при создании или записи файла.
     */
    private static void saveVacancyFile(String[] vacancy) throws IOException {
        Path folderPath = Paths.get("ResponseVacancy");
        Files.createDirectories(folderPath);

        boolean uniqueFileFound;
        String fileName;

        do {
            fileName = folderPath.resolve("vacancy_" + ++counter + ".txt").toString();
            uniqueFileFound = !Files.exists(Paths.get(fileName));
        } while (!uniqueFileFound);

        String candidateInfo = addResumeCandidate(userId);

        try (PrintWriter writer = new PrintWriter(fileName)) {
            writer.println("ID вакансии: " + vacancy[0]);
            writer.println("Название вакансии: " + vacancy[1]);
            writer.println("График работы: " + vacancy[2]);
            writer.println("Описание вакансии: " + vacancy[3]);
            writer.println("Зарплата: " + vacancy[4]);
            writer.println("----------------");
            writer.println(candidateInfo);
        }
        System.out.println("Вакансия с ID " + vacancy[0] + " сохранена в файл " + fileName);
    }

    /**
     * Метод ищет и возвращает информацию о резюме пользователя по его идентификатору.
     *
     * @param userId идентификатор пользователя, чей профиль мы ищем.
     * @return Строка с информацией о пользователе или пустая строка, если не найдено подходящего профиля.
     */
    static String addResumeCandidate(int userId) {
        try (BufferedReader reader = new BufferedReader(new FileReader("resume.txt"))) {
            StringBuilder result = new StringBuilder();
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

                        result.append("ID пользователя: ");
                        result.append(currentId);
                        result.append("\n");
                        result.append("ФИО: ");
                        result.append(fio);
                        result.append("\n");
                        result.append("Телефон: ");
                        result.append(phone);
                        result.append("\n");
                        result.append("Должность: ");
                        result.append(position);
                        result.append("\n");
                        result.append("Опыт работы: ");
                        result.append(experience);
                        result.append("\n");
                        result.append("Обязанности: ");
                        result.append(duties);
                        result.append("\n");
                        result.append("----------------");
                    }
                } catch (NumberFormatException ignored) {
                }
            }
            return result.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}







