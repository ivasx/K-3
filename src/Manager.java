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
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> ids = new ArrayList<>();
        for (User user : users.values()) {
            if (user.getFullName().equals(fullName)) {
                System.out.println("ID: " + user.getId() + " " + user.getFullName());
                ids.add(user.getId());
            }
        }
        if (ids.isEmpty()){
            System.out.println("Користувача з таким ПІБ не знайдено.");
        } else if (ids.size() == 1) {
            System.out.println("Видалено користувача " + users.get(ids.get(0)).getFullName() + " з ID: "
                    + users.get(ids.get(0)).getId());
            users.remove(ids.get(0));
        } else {
            System.out.println("Уточніть ID користувача, якого хочете видалити: ");
            int removeId = scanner.nextInt();
            scanner.nextLine();
            if (users.keySet().contains(removeId)){
                System.out.println("Видалено користувача " + users.get(ids.get(0)).getFullName() + " з ID: "
                        + users.get(ids.get(0)).getId());
                users.remove(removeId);
            } else {
                System.out.println("Введено неправильне ID.");
            }
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
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> ids = new ArrayList<>();

        for (User user : users.values()) {
            if (user.getFullName().equals(fullName)) {
                System.out.println("ID: " + user.getId() + " " + user.getFullName());
                ids.add(user.getId());
            }
        }

        if (ids.isEmpty()){
            System.out.println("Користувача з таким ПІБ не знайдено.");
        } else if (ids.size() == 1) {
            System.out.println("Замовлення користувача " + users.get(ids.get(0)).getFullName() + " з ID: " + users.get(ids.get(0)).getId() + ": ");
            System.out.println(users.get(ids.get(0)).getOrders());
        } else {
            System.out.println("Уточніть ID користувача, замовлення якого хочете переглянути: ");
            int ordersId = scanner.nextInt();
            scanner.nextLine();
            if (users.containsKey(ordersId)) {
                System.out.println("Замовлення користувача " + users.get(ordersId).getFullName() + " з ID: " + ordersId + ": ");
                if (users.get(ordersId).getOrders().isEmpty()) {
                    System.out.println("У користувача немає замовлень.");
                } else {
                    for (Order order : users.get(ordersId).getOrders()) {
                        System.out.println("Опис: " + order.getDescription() + ", Ціна: " + order.getPrice());
                    }
                }
            } else {
                System.out.println("Введено неправильне ID.");
            }
        }
    }

    public HashMap<Integer, User> getUsers() {
        return users;
    }
}
