import java.util.*;

public class Manager {
    private HashMap<Integer, User> users;

    public  Manager (){
        this.users = new HashMap<>();
    }

    public void addUser(User user) {
        if (!users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            System.out.println("Корситувача " + user.getFirstName() + " успішно додано!");
        } else {
            System.out.println("Користувача не додано!");
        }
    }

    public void deleteUserById(String fullName) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<User> matchedUsers  = new ArrayList<>();

        for (User user : users.values()) {
            if (user.getFullName().equalsIgnoreCase(fullName)) {
                matchedUsers.add(user);
            }
        }

        if (matchedUsers.isEmpty()) {
            System.out.println("Користувача з таким ПІБ не знайдено.");
            return;
        }

        if (matchedUsers.size() == 1) {
            users.remove(matchedUsers.get(0).getId());
            System.out.println("Видалено користувача " + matchedUsers.get(0).getFullName() +
                    " з ID: " + matchedUsers.get(0).getId());
            return;
        }

        System.out.println("Знайдено декілька користувачів:");
        for (User user : matchedUsers) {
            System.out.println("ID: " + user.getId() + "  " + user.getFullName());
        }

        System.out.print("Уточніть ID користувача, якого хочете видалити: ");
        int removeId = scanner.nextInt();
        scanner.nextLine();

        for (User user : matchedUsers) {
            if (user.getId() == removeId) {
                users.remove(removeId);
                System.out.println("Видалено користувача " + user.getFullName() + " з ID: " + removeId);
                user.clearOrders();
                return;
            }
        }

        System.out.println("Введено неправильне ID.");
    }

    public void filterByAge(int age) {      // Фільтрування за конкретним віком
        ArrayList<User> filteredUsers = new ArrayList<>();
        for (User user : users.values()) {
            if (user.getAge() == age) {
                filteredUsers.add(user);
            }
        }
        showAllUsers(filteredUsers);

    }

    public void filterByAge(int minAge, int maxAge) {    // Фільтрування за діапазоном віків
        ArrayList<User> filteredUsers = new ArrayList<>();
        for (User user : users.values()) {
            if (user.getAge() >= minAge && user.getAge() <= maxAge) {
                filteredUsers.add(user);
            }
        }
        showAllUsers(filteredUsers);
    }

    public void filterByPurchasePrice(double minPrice, double maxPrice) {
        ArrayList<User> filteredUsers = new ArrayList<>();
        for (User user : users.values()) {
            for (Order order : user.getOrders()) {
                if (order.getPrice() >= minPrice && order.getPrice() <= maxPrice) {
                    filteredUsers.add(user);
                    break;
                }
            }
        }
        showAllUsers(filteredUsers);
    }

    public void filterByPurchaseCount(int count) {
        ArrayList<User> filteredUsers = new ArrayList<>();
        for (User user : users.values()) {
            if (user.getOrderCount() == count) {
                filteredUsers.add(user);
            }
        }
        showAllUsers(filteredUsers);
    }

    public void sortBy(String type) {
        ArrayList<User> sortedUsers = new ArrayList<>(users.values());
        switch (type){
            case "name":
                sortedUsers.sort(Comparator.comparing(User::getFullName));
                showAllUsers(sortedUsers);
                break;
            case "age":
                sortedUsers.sort(Comparator.comparingInt(User::getAge));
                showAllUsers(sortedUsers);
                break;
            case "orders":
                sortedUsers.sort(Comparator.comparingInt(User::getOrderCount));
                showAllUsers(sortedUsers);
                break;
            default:
                System.out.println("Непривльний тип сортування.");
        }
    }

    public int getTotalUsers() {
        return users.size();
    }

    public int getTotalOrders() {
        int totalOrders = 0;
        for (User user : users.values()) {
            totalOrders += user.getOrders().size();
        }
        return totalOrders;
    }

    public double averageAge() {
        int average = 0;
        for (User user : users.values()) {
            average += user.getAge();
        }
        average = average / users.size();
        return average;
    }

    public void showAllUsers() {
        showAllUsers(users.values());
    }

    public void showAllUsers(Collection<User> userList) {
        if (userList.isEmpty()) {
            System.out.println("Користувачів не знайдено.");
            return;
        }
        System.out.println("Список користувачів:");
        for (User user : userList) {
            user.showUserInfo();
        }
    }

    public void viewOrdersForUser(String fullName) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<User> matchedUsers = new ArrayList<>();

        for (User user : users.values()) {
            if (user.getFullName().equalsIgnoreCase(fullName)) {
                matchedUsers.add(user);
            }
        }

        if (matchedUsers.isEmpty()) {
            System.out.println("Користувача з таким ПІБ не знайдено.");
            return;
        }

        if (matchedUsers.size() == 1) {
            User user = matchedUsers.get(0);
            System.out.println("Замовлення користувача " + user.getFullName() +
                    " з ID: " + user.getId() + ":");
            if (user.getOrders().isEmpty()) {
                System.out.println("У користувача немає замовлень.");
            } else {
                displayOrders(user);

                System.out.println("Редагувати замовлення користувача? (y/n) (Щоб видалити введіть d):");
                String answer = scanner.nextLine();

                if (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("d")) {
                    changeOrder(user, answer);
                }
            }
        } else {
            // Якщо знайдено кілька користувачів
            System.out.println("Знайдено кількох користувачів:");
            for (User user : matchedUsers) {
                System.out.println("ID: " + user.getId() + "   " + user.getFullName());
            }
            System.out.println("Уточніть ID користувача, замовлення якого хочете переглянути: ");
            int ordersId = scanner.nextInt();
            scanner.nextLine();

            boolean userFound = false;
            for (User user : matchedUsers) {
                if (user.getId() == ordersId) {
                    System.out.println("Замовлення користувача " + user.getFullName() + " з ID: " + user.getId() + ":");
                    if (user.getOrders().isEmpty()) {
                        System.out.println("У користувача немає замовлень.");
                    } else {
                        displayOrders(user);

                        System.out.println("Редагувати замовлення користувача? (y/n) (Щоб видалити введіть d):");
                        String answer = scanner.nextLine();
                        if (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("d")) {
                            changeOrder(user, answer);
                        }
                    }
                    userFound = true;
                    break;
                }
            }

            if (!userFound) {
                System.out.println("Введено неправильне ID.");
            }
        }
    }

    private void displayOrders(User user) {
        for (Order order : user.getOrders()) {
            System.out.println("ID замовлення: " + order.getId());
            System.out.println("Опис: " + order.getDescription());
            System.out.println("Ціна: " + order.getPrice());
            System.out.println("Статус: " + order.getStatus());
            System.out.println("Адреса замовленння: " + order.getAddresses());
            System.out.println();
        }
    }

    private void changeOrder(User user, String action) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введіть ID замовлення, яке хочете змінити: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        boolean found = false;

        for (Order order : user.getOrders()) {
            if (order.getId() == id) {
                if (action.equalsIgnoreCase("y")) {
                    order.editOrder();
                    System.out.println("Замовлення змінено.");
                } else if (action.equalsIgnoreCase("d")) {
                    user.getOrders().remove(order);
                    System.out.println("Замовлення видалено.");
                }
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Введено некоректне ID замовлення.");
        }
    }

    public HashMap<Integer, User> getUsers() {
        return users;
    }
}
