package UserStorage;


import UserStorage.entity.User;
import UserStorage.entity.dao.UserDao;
import UserStorage.entity.dao.impl.UserDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final Validator validator = new Validator();
    private static final UserDao userDao = new UserDaoImpl();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {


        while (true) {
            System.out.println("\n1. Создать User");
            System.out.println("2. Показать всех Users");
            System.out.println("3. Найти по  ID");
            System.out.println("4. Обновить ");
            System.out.println("5. Удалить");
            System.out.println("6. Выход");
            System.out.print("Выберете пункт: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    createUser();
                    logger.debug("User selected: Create User");
                }
                case 2 -> {
                    viewAllUsers();
                    logger.debug("User selected: view All Users");
                }
                case 3 -> {
                    viewUserById();
                    logger.debug("User selected: view User By Id");
                }

                case 4 -> {
                    updateUser();
                    logger.debug("User selected: update User()");
                }

                case 5 -> {
                    deleteUser();
                    logger.debug("User selected: deleteUser");
                }

                case 6 -> System.exit(0);
                default -> {
                    System.out.println("Invalid option!");
                    logger.debug("User selected: incorrect item: " + choice);
                }

            }
        }
    }

    private static void createUser() {

        String name = validator.getValidFullName(scanner);

        String email = validator.getValidEmail(scanner);

        int age = validator.getValidAge(scanner);

        User user = new User(name, email, age);
        userDao.save(user);
        System.out.println("Пользователь сохранен");
    }

    private static void viewAllUsers() {
        List<User> users = userDao.findAll();
        if (users.isEmpty()) {
            System.out.println("Пользователей нет.");
        } else {
            users.forEach(System.out::println);
        }
    }

    private static void viewUserById() {
        System.out.print("Введите ID пользователя: ");
        long id = validator.getValidId(scanner);
        User user = userDao.findById(id);
        if (user != null) {
            System.out.println(user);

        } else {
            System.out.println("Пользователь с ID " + id + " не найден.");
        }

    }

    private static void updateUser() {
        System.out.print("Введите ID пользователя для обновления: ");
        long id = validator.getValidId(scanner);
        User user = userDao.findById(id);
        if (user == null) {
            System.out.println("Пользователь не найден.");
            return;
        }

        System.out.println("Введите новые ФИО ");

        user.setName(validator.getValidFullName(scanner));

        System.out.println("Введите новый email");
        System.out.print("Можно указать старый Email : (" + user.getEmail() + "): ");
        String newEmail = scanner.nextLine().trim();
        if (!newEmail.isEmpty()) {
            user.setEmail(validator.getValidEmail(scanner));
        }
        System.out.println("Введите новый возраст");
        System.out.print("предыдущее значение : (" + user.getAge() + "): ");

        user.setAge(validator.getValidAge(scanner));


        userDao.update(user);
        System.out.println("Пользователь обновлён.");
    }

    private static void deleteUser() {
        System.out.print("Введите ID пользователя для удаления: ");
        User deleting = userDao.findById(validator.getValidId(scanner));
        if (deleting == null) {
            System.out.println("Пользователь не найден или уже удален");
            return;
        }
        System.out.println("Пользователь удален");
        userDao.delete(deleting);
    }
}