import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Manager manager = new Manager();
        addStartedUsers(manager);


        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1. Додати користувача");
            System.out.println("2. Видалити користувача");
            System.out.println("3. Переглянути список всіх користувачів");
            System.out.println("4. Переглянути замовлення конкретного користувача");
            System.out.println("5. Сортувати користувачів");
            System.out.println("6. Фільтрувати користувачів");
            System.out.println("7. Переглянути загальну кількість користувачів та замовлень");
            System.out.println("8. Вийти");

            System.out.print("Виберіть опцію: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: // Додавання користувача
                    System.out.print("Введіть ім'я користувача: ");
                    String firstName = scanner.nextLine();
                    System.out.print("Введіть прізвище користувача: ");
                    String lastName = scanner.nextLine();
                    System.out.print("Введіть вік користувача: ");
                    int age = scanner.nextInt();
                    manager.addUser(new User(firstName, lastName, age));
                    break;

                case 2: // Видалення користувача
                    System.out.print("Введіть ПІБ користувача для видалення: ");
                    scanner.nextLine();
                    String fullNameToDelete = scanner.nextLine();
                    manager.deleteUserById(fullNameToDelete);
                    break;

                case 3: // Перегляд списку користувачів
                    manager.showAllUsers();
                    break;

                case 4: // Перегляд замовлень конкретного користувача
                    System.out.print("Введіть ПІБ користувача для перегляду замовлень: ");
                    scanner.nextLine();
                    String fullNameToShowOrders = scanner.nextLine();
                    manager.viewOrdersForUser(fullNameToShowOrders);
                    break;

                case 5: // Сортування користувачів
                    System.out.println("1. Сортувати за ПІБ");
                    System.out.println("2. Сортувати за віком");
                    System.out.println("3. Сортувати за кількістю замовлень");
                    System.out.print("Виберіть опцію для сортування: ");
                    int sortChoice = scanner.nextInt();
                    switch (sortChoice) {
                        case 1:
                            manager.sortBy(Manager.SortType.FULLNAME);
                            System.out.println("Список користувачів:");
                            for (User user : manager.getUsers().values()) {
                                System.out.println(user.getFullName() + " (ID: " + user.getId() + ")");
                            }
                            break;
                        case 2:
                            manager.sortBy(Manager.SortType.AGE);
                            System.out.println("Список користувачів:");
                            for (User user : manager.getUsers().values()) {
                                System.out.println(user.getFullName() + " (ID: " + user.getId() + ")");
                            }
                            break;
                        case 3:
                            manager.sortBy(Manager.SortType.ORDERS);
                            System.out.println("Список користувачів:");
                            for (User user : manager.getUsers().values()) {
                                System.out.println(user.getFullName() + " (ID: " + user.getId() + ")");
                            }
                            break;
                        default:
                            System.out.println("Невірний вибір.");
                    }
                    break;

                case 6: // Фільтрація користувачів
                    System.out.println("1. Фільтрувати за конкретним віком");
                    System.out.println("2. Фільтрувати за діапазоном віків");
                    System.out.println("3. Фільтрувати за описом замовлення");
                    System.out.println("4. Фільтрувати за ціною замовлення");
                    System.out.print("Виберіть опцію для фільтрації: ");
                    int filterChoice = scanner.nextInt();
                    switch (filterChoice) {
                        case 1:
                            System.out.print("Введіть вік для фільтрації: ");
                            int exactAge = scanner.nextInt();
                            ArrayList<User> usersByExactAge = manager.filterByExactAge(exactAge);
                            for (User user : usersByExactAge) {
                                System.out.println(user.getFullName());
                            }
                            break;
                        case 2:
                            System.out.print("Введіть мінімальний вік: ");
                            int minAge = scanner.nextInt();
                            System.out.print("Введіть максимальний вік: ");
                            int maxAge = scanner.nextInt();
                            ArrayList<User> usersByAgeRange = manager.filterByAge(minAge, maxAge);
                            for (User user : usersByAgeRange) {
                                System.out.println(user.getFullName());
                            }
                            break;
                        case 3:
                            scanner.nextLine();
                            System.out.print("Введіть опис замовлення для фільтрації: ");
                            String description = scanner.nextLine();
                            ArrayList<User> usersByDescription = manager.filterByPurchaseDescription(description);
                            for (User user : usersByDescription) {
                                System.out.println(user.getFullName());
                            }
                            break;
                        case 4:
                            System.out.print("Введіть ціну для фільтрації: ");
                            double price = scanner.nextDouble();
                            ArrayList<User> usersByPrice = manager.filterByPurchasePrice(price);
                            for (User user : usersByPrice) {
                                System.out.println(user.getFullName());
                            }
                            break;
                        default:
                            System.out.println("Невірний вибір.");
                    }
                    break;

                case 7: // Перегляд кількості користувачів та замовлень
                    System.out.println("Загальна кількість користувачів: " + manager.getTotalUsers());
                    System.out.println("Загальна кількість замовлень: " + manager.getTotalOrders());
                    break;

                case 8: // Вихід
                    System.out.println("Вихід з програми.");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Невірний вибір, спробуйте ще раз.");
            }
        }
    }

    public static void addStartedUsers(Manager manager) {
        manager.addUser(new User("Івас", "Амброзяк", 18));
        manager.getUsers().get(1).addOrder(new Order("Товари для дому", 500, "вул. Шевченка, 1"));
        manager.getUsers().get(1).addOrder(new Order("Електроніка", 1500, "вул. Лесі Українки, 2"));

        manager.addUser(new User("Анна", "Коваль", 25));
        manager.getUsers().get(2).addOrder(new Order("Одяг", 300, "вул. Довженка, 10"));
        manager.getUsers().get(2).addOrder(new Order("Книги", 200, "вул. Хрещатик, 5"));

        manager.addUser(new User("Анна", "Коваль", 25));
        manager.getUsers().get(2).addOrder(new Order("Одяг", 300, "вул. Довженка, 10"));
        manager.getUsers().get(2).addOrder(new Order("Книги", 200, "вул. Хрещатик, 5"));

        manager.addUser(new User("Марія", "Левченко", 30));
        manager.getUsers().get(3).addOrder(new Order("Продукти", 700, "вул. Коцюбинського, 4"));
        manager.getUsers().get(3).addOrder(new Order("Іграшки", 400, "вул. Микільська, 8"));

        manager.addUser(new User("Олег", "Іванов", 28));
        manager.getUsers().get(4).addOrder(new Order("Меблі", 2500, "вул. Горького, 7"));
        manager.getUsers().get(4).addOrder(new Order("Камера", 1200, "вул. Леніна, 3"));

        manager.addUser(new User("Ірина", "Петренко", 35));
        manager.getUsers().get(5).addOrder(new Order("Комп'ютер", 6000, "вул. Шовковична, 12"));
        manager.getUsers().get(5).addOrder(new Order("Мобільний телефон", 4500, "вул. Різдвяна, 15"));

        manager.addUser(new User("Артем", "Савченко", 27));
        manager.getUsers().get(6).addOrder(new Order("Техніка", 3000, "вул. Дарницька, 2"));
        manager.getUsers().get(6).addOrder(new Order("Телевізор", 5500, "вул. Хмельницька, 9"));

        manager.addUser(new User("Світлана", "Гусак", 24));
        manager.getUsers().get(7).addOrder(new Order("Книга", 150, "вул. Гоголя, 14"));
        manager.getUsers().get(7).addOrder(new Order("Біжутерія", 350, "вул. Сагайдачного, 1"));

        manager.addUser(new User("Дмитро", "Петрик", 40));
        manager.getUsers().get(8).addOrder(new Order("Спортивне спорядження", 2000, "вул. Науки, 5"));
        manager.getUsers().get(8).addOrder(new Order("Телефон", 2000, "вул. Остромова, 11"));

        manager.addUser(new User("Катерина", "Шевченко", 22));
        manager.getUsers().get(9).addOrder(new Order("Меблі", 1200, "вул. Хрещатик, 8"));
        manager.getUsers().get(9).addOrder(new Order("Декор", 600, "вул. Вишневий, 7"));
    }
}
