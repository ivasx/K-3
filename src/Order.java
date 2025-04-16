public class Order {
    private static int nextId = 1;

    private int id;
    private String description;
    private double price;
    private String addresses;
    public boolean isAccepted;

    public Order(String description, double price, String addresses) {
        this.id = nextId++;
        this.description = description;
        this.price = price;
        this.addresses = addresses;
        this.isAccepted = false;
    }

    public void editOrder(String newDescription, Double newPrice, String newAddresses) {
        if (!isAccepted) {
            if (newDescription != null) {
                this.description = newDescription;
                System.out.println("Змінено опис замовлення.");
            }
            if (newPrice != null && newPrice >= 0) {
                this.price = newPrice;
                System.out.println("Змінено ціну замовлення.");
            }
            if (newAddresses != null) {
                this.addresses = newAddresses;
                System.out.println("Змінено адресу замовлення.");
            }

        } else {
            System.out.println("Замовлення не змінено.");;
        }
    }

    public void confirmOrder() {
        if (!this.isAccepted) {
            this.acceptOrder();
            System.out.println("Замовлення '" + this.description + "' успішно підтверджено.");
        } else {
            System.out.println("Замовлення '" + this.description + "' вже підтверджено.");
        }
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void acceptOrder() {
        this.isAccepted = true;
    }
}