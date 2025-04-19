import java.util.Scanner;

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

    public boolean getIsAccepted() {
        return  isAccepted;
    }

    public String getStatus(){
        String status;
        if (getIsAccepted()) {
            return status = "Прийняте";
        } else {
            return status = "Не прийняте";
        }
    }

    public void editOrder() {
        Scanner scanner = new Scanner(System.in);
        confirmOrder();
        if (!isAccepted) {
            System.out.print("Введіть новий опис (або натисніть Enter, щоб не змінювати): ");
            String newDescription = scanner.nextLine();
            if (!newDescription.isEmpty()) {
                this.description = newDescription;
                System.out.println("Змінено опис замовлення.");
            }

            System.out.print("Введіть нову ціну (або натисніть Enter, щоб не змінювати): ");
            String priceInput = scanner.nextLine();
            if (!priceInput.isEmpty()) {
                double newPrice = Double.parseDouble(priceInput);
                if (newPrice >= 0) {
                    this.price = newPrice;
                    System.out.println("Змінено ціну замовлення.");
                } else {
                    System.out.println("Ціна не може бути від’ємною.");
                }
            }

            System.out.print("Введіть нову адресу (або натисніть Enter, щоб не змінювати): ");
            String newAddress = scanner.nextLine();
            if (!newAddress.isEmpty()) {
                this.addresses = newAddress;
                System.out.println("Змінено адресу замовлення.");
            }

        } else {
            System.out.println("Замовлення вже прийняте. Редагування неможливе.");
        }
    }

    public void confirmOrder() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Підвердити прийняття замовлення? (y/n)");
        String choice = scanner.nextLine();
        if (choice.equalsIgnoreCase("y")) {
            if (!this.isAccepted) {
                this.acceptOrder();
                System.out.println("Замовлення '" + this.description + "' успішно прийнято.");
            } else {
                System.out.println("Замовлення '" + this.description + "' вже прийнято.");
            }
        } else {
            return;
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

    public String getAddresses() {
        return addresses;
    }
}
