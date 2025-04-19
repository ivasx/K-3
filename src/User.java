import java.util.ArrayList;


public class User {
    private static int nextId = 1;

    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private ArrayList<Order> orders;


    public User(String firstName, String lastName, int age){
        this.id = nextId++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.orders = new ArrayList<>();
    }

    public void addOrder(Order order) {
        if (order != null) {
            if (orders == null) {
                orders = new ArrayList<>();
            }
            this.orders.add(order);
            System.out.println("Замовлення додано для користувача " + getFullName());
        } else {
            System.out.println("Не можна додати замовлення: воно є null.");
        }
    }

    public void clearOrders() {
        if (!orders.isEmpty()) {
            this.orders.clear();
            System.out.println("Усі замовлення користувача " + getFirstName() + " були видалені.");
        } else {
            System.out.println("У користувача " + getFirstName() + " немає замовлень для видалення.");
        }
    }

    public void showUserInfo() {
        System.out.println("ID: " + getId());
        System.out.println(getFullName());
        System.out.println("Вік: " + getAge());
        System.out.println("Кількість замовлень: " + getOrderCount());
        System.out.println();
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public int getAge() {
        return age;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public int getOrderCount() {
        return orders.size();
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

}
