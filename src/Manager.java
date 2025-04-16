import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

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
        ArrayList<User> matchingUsers = new ArrayList<>();
        fullName = fullName.trim().toLowerCase();

        for (User user : users.values()) {
            if (user.getFullName().trim().toLowerCase().equals(fullName)) {
                matchingUsers.add(user);
            }
        }
        // Виводимо знайдених користувачів для перевірки
        System.out.println("Знайдені користувачі:");
        for (User user : matchingUsers) {
            System.out.println("ID: " + user.getId() + ", ПІБ: " + user.getFullName());
        }

        // Якщо не знайшли користувачів
        if (matchingUsers.isEmpty()) {
            System.out.println("Користувача з таким ПІБ не знайдено.");
            return;
        }

        // Якщо знайдено лише одного користувача
        if (matchingUsers.size() == 1) {
            User userToDelete = matchingUsers.get(0);
            users.remove(userToDelete.getId());
            System.out.println("Корситувача " + userToDelete.getFullName() + " успішно видалено!");
            return;
        }

        // Якщо знайдено кілька користувачів
        System.out.println("Знайдено кілька користувачів з таким ПІБ:");
        for (User user : matchingUsers) {
            System.out.println("ID: " + user.getId() + ", ПІБ: " + user.getFullName());
        }

        // Запитуємо у користувача ID для видалення
        System.out.print("Виберіть ID користувача для видалення: ");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        if (users.containsKey(id)) {
            System.out.println("Корситувача " + users.get(id).getFirstName() + " успішно видалено!");
            users.remove(id);
        } else {
            System.out.println("Користувача з таким ID не існує!");
        }
    }

    public ArrayList<User> filterByExactAge(int age) {      // Фільтрування за конкретним віком
        ArrayList<User> filteredUsers = new ArrayList<>();
        for (User user : users.values()) {
            if (user.getAge() == age) {
                filteredUsers.add(user);
            }
        }
        return filteredUsers;
    }

    public ArrayList<User> filterByAge(int minAge, int maxAge) {    // Фільтрування за діапазоном віків
        ArrayList<User> filteredUsers = new ArrayList<>();
        for (User user : users.values()) {
            if (user.getAge() >= minAge && user.getAge() <= maxAge) {
                filteredUsers.add(user);
            }
        }
        return filteredUsers;
    }

    public ArrayList<User> filterByPurchaseDescription(String description) {
        ArrayList<User> filteredUsers = new ArrayList<>();
        for (User user : users.values()) {
            for (Order order : user.getOrders()) {
                if (order.getDescription().equalsIgnoreCase(description)) {
                    filteredUsers.add(user);
                    break;
                }
            }
        }
        return filteredUsers;
    }

    public ArrayList<User> filterByPurchasePrice(double price) {
        ArrayList<User> filteredUsers = new ArrayList<>();
        for (User user : users.values()) {
            for (Order order : user.getOrders()) {
                if (order.getPrice() == price) {
                    filteredUsers.add(user);
                    break;
                }
            }
        }
        return filteredUsers;
    }

    public ArrayList<User> sortBy(SortType type) {
        ArrayList<User> sortedUsers = new ArrayList<>(users.values());

        switch (type){
            case FULLNAME:
                sortedUsers.sort(Comparator.comparing(User::getFullName));
                break;
            case AGE:
                sortedUsers.sort(Comparator.comparingInt(User::getAge));
                break;
            case ORDERS:
                sortedUsers.sort(Comparator.comparingInt(User::getOrderCount));
                break;
            default:
                System.out.println("Непривльний тип сортування.");
        }
        return sortedUsers;
    }

    public enum SortType {
        FULLNAME("ПІБ"),
        AGE("ВІКОМ"),
        ORDERS("ЗАМОВЛЕННЯМИ");

        private String type;

        SortType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
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

    public void showAllUsers() {
        System.out.println("Список користувачів:");
        for (User user : users.values()) {
            System.out.println(user.getFullName() + " (ID: " + user.getId() + ")");
        }
    }

    public void viewOrdersForUser(String fullName) {
        ArrayList<User> matchingUsers = new ArrayList<>();

        for (User user : users.values()) {
            if (user.getFullName().toLowerCase().contains(fullName.toLowerCase())) {
                matchingUsers.add(user);
            }
        }

        if (matchingUsers.isEmpty()) {
            System.out.println("Користувача з таким ПІБ не знайдено.");
            return;
        }

        if (matchingUsers.size() == 1) {
            User user = matchingUsers.get(0);
            System.out.println("Замовлення користувача " + user.getFullName() + ":");
            if (user.getOrders().isEmpty()) {
                System.out.println("У користувача немає замовлень.");
            } else {
                for (Order order : user.getOrders()) {
                    System.out.println("Опис: " + order.getDescription() + ", Ціна: " + order.getPrice());
                }
            }
            return;
        }

        System.out.println("Знайдено кілька користувачів з таким ПІБ:");
        for (User user : matchingUsers) {
            System.out.println("ID: " + user.getId() + ", ПІБ: " + user.getFullName());
        }

        System.out.print("Виберіть ID користувача для перегляду замовлень: ");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        if (users.containsKey(id)) {
            User selectedUser = users.get(id);
            System.out.println("Замовлення користувача " + selectedUser.getFullName() + ":");
            if (selectedUser.getOrders().isEmpty()) {
                System.out.println("У користувача немає замовлень.");
            } else {
                for (Order order : selectedUser.getOrders()) {
                    System.out.println("Опис: " + order.getDescription() + ", Ціна: " + order.getPrice());
                }
            }
        } else {
            System.out.println("Користувача з таким ID не існує!");
        }
    }

    public HashMap<Integer, User> getUsers() {
        return users;
    }
}
