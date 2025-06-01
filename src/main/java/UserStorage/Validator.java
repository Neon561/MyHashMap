package UserStorage;

import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class Validator {

    private static final Logger logger = LoggerFactory.getLogger(Validator.class);
    public String getValidFullName(Scanner scanner) {
        while (true) {
            System.out.println("Введите  ФИО. Например: Иванов Иван Иванович");
            String input = scanner.nextLine().trim();
            if (input.matches("^([A-Za-zА-Яа-яЁё-]{3,20})(\\s+[A-Za-zА-Яа-яЁё-]{3,20}){1,2}$")) {
                return input;
            }

        }
    }

    public String getValidEmail(Scanner scanner) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

        while (true) {
            System.out.println("Введите  email (в формате, example@mail.com).");
            String input = scanner.nextLine().trim();

            if (input.matches(emailRegex)) {
                return input;
            }
            logger.warn("Invalid email format: {}", input);

        }
    }

    public int getValidAge(Scanner scanner) {
        while (true) {
            System.out.println("Укажите возраст в пределах от 1 до 200 лет.");
            String input = scanner.nextLine().trim();
            try {
                int age = Integer.parseInt(input);
                if (age < 1 || age > 200) {
                    System.out.println("Укажите корректный возраст");
                } else {
                    return age;

                }
            } catch (NumberFormatException e) {
                System.out.println("Введите корректное число для возраста.");
            }
        }
    }

    public long getValidId(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                long id = Long.parseLong(input);
                if (id > 0) {
                    return id;
                } else {
                    System.out.println("ID должен быть положительным числом.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Введите корректное число для ID.");
            }
        }
    }
}

