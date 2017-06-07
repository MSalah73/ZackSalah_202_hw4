import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

///////////////////////////////////////////////////////////////////////////////////////
//Zack Salah
//Pizza app!
//Homework # 4-5
//Programming Systems #202
///////////////////////////////////////////////////////////////////////////////////////
//This file contains the implantation of the user class. It contains every function
// that the programs would need. The comments in the file will describe the functionality
// step by step to ensure the grader's understanding of all the functions.
//
//As I implanted all of the classes, I have returned and added and fixed some
//of the functions in this file. Because most of the function have functions from
//outside of the class. I encourage opening all the files to ensure all of the
//connections that has been made.
//
//Algorithms
//
//This file contain the user class and its implantation. This class acts as the user's manager
//they can order from it as they please. They can see previous orders, delete previous order if
//wish to do so. Once the user login a special pizza is allocated randomly . if the user
//desires it, he may add as many as they wants to his order list. the user may also retrieve
//a previous order. In program five i will add other functionality that connect the user to the
//restaurant or login as an admin to modify the restaurant's pizzas or add a restaurant
//
//This file also contains the Node subclass. I decided to make a subclass because I only use
//The Node class in this abstract base class. its purpose is to store the pizzas order the user
//assembled and remove them if desired.
/////////////////////////////////////////////////////////////////////////////////////////
//Hierarchy Connections:
//User -"Has a"-> Node(a DLL node)
//Node -"Has a"-> Pizza
//Node -"Has a"-> String
/////////////////////////////////////////////////////////////////////////////////////////
public class User {
    protected Node head = null;
    protected class Node {
        protected Node next = null;
        protected Node previous = null;
        protected Pizza[] pizzas = null;
        protected float total = 0f;
        protected String date = null;
        /**
         *Default Constrictor
         */
        public Node() {
        }
        /**
         *Constructor
         * step 1: set size to passed in size
         * step 2: allocate pizza size
         * step 3: loops until size and copy all pizza to current pizza
         */
        public Node(Pizza[] to_copy) throws Exception {
            int size = to_copy.length;
            pizzas = new Pizza[size];
            for (int i = 0; i < size; ++i) {
                pizzas[i] = new Pizza(to_copy[i]);
            }
        }
        /**
         *Default Constrictor
         * step 1: set size to passed in size
         * step 2: allocate pizza size
         * step 3: loops until size and copy all pizza to current pizza
         */
        public Node(Node to_copy) throws Exception {
            int size = to_copy.pizzas.length;
            pizzas = new Pizza[size];
            for (int i = 0; i < size; ++i) {
                pizzas[i] = new Pizza(to_copy.pizzas[i]);
            }
        }
    }
    /**
     *Default Constrictor
     */
    public User() {
    }
    /**
     *Copy Constrictor
     * step 1: set head to copy function with to_copy's head
     */
    public User(User to_copy) throws Exception {
        head = copy(to_copy.head,head, null);
    }
    /**
     * step 1: prompt the user to pick a choice
     * step 2: call the correct function depending on their choice
     */
    public void login() throws Exception {
        Scanner read = new Scanner(System.in);
        int choice;
        System.out.println("Welcome to pizza ordering app");
        do {
            System.out.println("Please pick a number associated with the desired choice");
            System.out.println("Please pick a desired choice - You can only pick one");
            System.out.println("1 - Make an order");
            System.out.println("2 - Display most recent order");
            System.out.println("3 - Retrieve an order");
            System.out.println("4 - Delete an order");
            System.out.println("5 - Quit");
            choice = read.nextInt();
            read.nextLine();
            if(choice == 1){
                make_an_order();
            }else if(choice == 2){
                if(display(head))
                    System.err.println("No previous order were made to display");
            }else if(choice == 3){
                String to_retieve = null;
                System.out.println("Please enter the date of the order you would like to retrieve - Format YYYY/MM/DD");
                to_retieve = read.nextLine();
                if(retrieve(to_retieve))
                    System.err.println("Request not found");
            }else if(choice == 4){
                String to_remove = null;
                System.out.println("Please enter the date of the order you would like to remove - Format YYYY/MM/DD");
                to_remove = read.nextLine();
                remove_order(to_remove);
            }else if(choice == 5){
                System.out.println("Thank you for using our app!");
            }else
                System.err.println("Invalid choice!!");
        }while(choice != 5);
    }
    /**
     * step 1: prompt the user pizza number
     * step 2: prompt the user if they want to see today's special
     * step 2a: call special pizza function
     * step 3: call build your own pizzas
     * step 4: prompt the user if want to place their order or modify it
     * step 4 modify: call modify function
     * step 5: print a message
     */
    private void make_an_order() throws Exception {
        Scanner read = new Scanner(System.in);
        System.out.println("How many pizza would you like to order?");
        int num_pizzas = read.nextInt();
        read.nextLine();
        int Choice = 0;
        char choice;
        Node temp = new Node();
        set_date(temp);

        num_pizzas =special_pizza(num_pizzas, temp);
        if (temp.pizzas == null)
            temp.pizzas = new Pizza[num_pizzas];
        buildyourown(num_pizzas,temp);
        do{
            System.out.println("Please pick a number associated with the desired choice");
            System.out.println("Please pick a desired choice - You can only pick one");
            System.out.println("1 - Place your order");
            System.out.println("2 - Modify your order");
            Choice = read.nextInt();
            read.nextLine();
            if (!(Choice < 3 && Choice > 0))
                System.err.println("Invalid choice!!");
            if (Choice == 2) {
                Choice = modify_prompt(temp);
            }
        }while (Choice != 1);
        add_order(temp);
        update_total(head);
        System.out.println("Your order is placed!");
        System.out.println("Hers's a your order detail's");
        display(head);
        System.out.println("Thank you for using our app!");
    }
    /**
     * step 1: loop until user end it
     * step 2: ask the user if what to modify
     * step 3: call modify on the user choice
     */
    private int modify_prompt(Node temp) throws IOException {
        int Choice;
        int len = temp.pizzas.length;
        Scanner read = new Scanner(System.in);
        do {
            System.out.println("Please pick a number associated with the desired choice");
            System.out.println("Please pick a desired choice - You can only pick one");
            System.out.println("Which order would you like to modify?");
            for (int i = 0; i < len; ++i) {
                System.out.println((i+1) +" - Pizza #" + (i + 1) + " " + temp.pizzas[i].name);
            }
            System.out.println((len+1)+" - Place your order");
            Choice = read.nextInt();
            read.nextLine();
            if (!(Choice < len+2 && Choice > 0))
                System.err.println("Invalid choice!!");
            else if(Choice == len+1)
                Choice = 1;
            else {
                temp.pizzas[Choice - 1].modify();
                Choice = 0;
            }
        }while (Choice != 1);
        return Choice;
    }
    /**
     *step 1: ask the user if they wnat to name the pizza
     * step 2: call pizza manual assumable
     */
    private void buildyourown(int num_pizzas, Node temp) throws Exception {
        Scanner read = new Scanner(System.in);
        Pizza[] to_add = new Pizza[1];
        char choice;
        String name = null;
        for (int i = 0; i < num_pizzas; ++i) {
            System.out.println("What do you like on pizza #" + (num_pizzas+ i) + "\n");
            to_add[0] = new Pizza();
            System.out.println("Would you like to give your pizza a name? (Y/N)");
            choice = read.next().toUpperCase().charAt(0);
            read.nextLine();
            if(choice == 'Y') {
                System.out.println("What would you like to call it?");
                name = read.next();
                read.nextLine();
            }
            to_add[0].assemble_pizza(name);
            add_pizza(to_add[0], temp);
            name = null;
        }
    }
    /**
     *Step 1: ask the user if they want to add it to their order
     * step 2: ask the user how many - if exceed the limit ask to up data the limit
     * step 3: add the pizza to the user order
     * step 4: return the limit
     */
    private int special_pizza(int num_pizzas, Node temp) throws Exception {
        int Choice;
        Scanner read= new Scanner(System.in);
        System.out.println("Would you like to see today's special pizza? (Y/N)");
        char choice = read.next().toUpperCase().charAt(0);
        read.nextLine();
        if (choice == 'Y') {
            Pizza[] special = new Pizza[1];
            special[0] = new Pizza();
            special[0].assemble_randomly();
            special[0].display();
            System.out.println("Would you like to add the special pizza to user order? (Y/N)");
            choice = read.next().toUpperCase().charAt(0);
            read.nextLine();
            if (choice == 'Y') {
                do {
                    System.out.println("How many orders of Special Pizza would you like?");
                    Choice = read.nextInt();
                    read.nextLine();
                    if (Choice > num_pizzas) {
                        System.out.println("I thought you said at the beginning want " + num_pizzas + " pizzas to orders?");
                        System.out.println("How many pizza would you like to order?");
                        num_pizzas = read.nextInt();
                        read.nextLine();
                        Choice = 0;
                    } else if (!(Choice <= num_pizzas && Choice > 0))
                        System.err.println("Invalid choice!!");
                } while (!(Choice <= num_pizzas && Choice > 0));
                temp.pizzas = new Pizza[num_pizzas];
                for (int i = 0; i < Choice; ++i) {
                    add_pizza(special[0], temp);
                    --num_pizzas;
                }
            }
        }
        return num_pizzas;
    }
    /**
     *step 1: loop until size is reached
     * step 2: set the current price to passed variable total price
     */
    private void update_total(Node to_update){
        int len = to_update.pizzas.length;
        for(int i = 0; i < len; ++i) {
            to_update.total += to_update.pizzas[i].price;
        }
    }
    /**
     * step 1: allocate date time formatter and set it to the current date
     * step 2: set head's date to the current day
     */
    private void set_date(Node head){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY/MM/dd");
        LocalDate localDate = LocalDate.now();
        head.date = new String(dtf.format(localDate));
    }
    /**
     * Step 1: loop until the size of passed in temp
     * step 2: if check if ant of the temp pizza is empty
     * step 3: allocate the pizza in temp and pass in to_add
     * step 4: end the loop by setting i to the size
     */
    private void add_pizza(Pizza to_add, Node temp) throws Exception {
        for(int i = 0; i < temp.pizzas.length; ++i){
            if(temp.pizzas[i] == null) {
                temp.pizzas[i] = new Pizza(to_add);
                i = temp.pizzas.length;
            }
        }
    }
    /**
     *step 1: set to_add's next to head
     * step 2: check if head is null - set head previous to to_add
     * step 3: update head;
     */
    private void add_order(Node to_add){
        to_add.next = head;
        if(head != null)
            head.previous = to_add;
        head = to_add;
    }
    /**
     *Step 1: set head to remove_order with head nad to remove passed in
     */
    private void remove_order(String to_remove){
        head = remove_order(head,to_remove);
    }
    /**
     * Step 1: check if head is null - return current head
     * Step 2: check if passed in to remove is equal to head's date
     * Step 2a: check if head previous is null - set head to head.next and set previous to null and return head
     * Step 2b: set a temp to hold head' previous
     * step 2c: check if head's next is not null - set head previous' next to temp
     * step 2d: set hold's next to head's next and return objects head
     * step 3: call it self recursively and return
     */
    private Node remove_order(Node head, String to_remove){
        if(head == null) {
            System.err.println("Request not found");
            return this.head;
        }
        if(to_remove.equals(head.date)){
            if(head.previous == null) {
                head = head.next;
                if(head != null)
                    head.previous = null;
                return head;
            }
            Node hold = head.previous;
            if(head.next != null)
                head.next.previous = hold;
            hold.next = head.next;
            head = hold;
            return this.head;
        }
        return remove_order(head.next, to_remove);
    }
    /**
     *step 1: check if head is null - return
     * step 2: print total price
     * step 3: display each pizza in order
     */
    private void display_order(Node head) {
        if(head == null)
            return;
        int size = head.pizzas.length;
        System.out.printf("Total price $%.2f\n", head.total);
        for(int i = 0; i < size; ++i){
            head.pizzas[i].display();
        }
    }
    /**
     *step 1: check if head is null - return true
     * step 2: print date
     * step 3: call diaply on head's data
     * step 4: call it self recursively  and return
     */
    private boolean display(Node head){
        if(head == null)
            return true;
        System.out.println("Date: "+head.date);
        display_order(head);
        return false && display(head.next);
    }
    /**
     * step 1: call retrieve function with head and to_retrieve passed in and return
     */
    private boolean retrieve(String to_retrieve){
        return retrieve(head, to_retrieve);
    }
    /**
     *Step 1: check if head is null
     * step 2: check if to retrieve is equal passed variable - call display on head and return false
     * step 3: cal it self recursively
     */
    private boolean retrieve(Node head, String to_retrieve){
        if(head == null) {
            return true;
        }
        if(to_retrieve.equals(head.date)){
            display_order(head);
            return false;
        }
        return retrieve(head.next, to_retrieve);
    }
    /**
     * Step 1: check if source is null - return dest
     * Step 2: allocate dest with source passed in
     * Step 3: check if previous is not null - connect previous's next to dest
     * Step 4: connect previous dest to prev
     * Step 5: check if source is null - return dest
     * Step 6: call and return it self recursively with returns' previous
     */
    private Node copy(Node src, Node dest, Node prev) throws Exception {
        if(src == null)
            return dest;
        dest = new Node(src);
        if(prev != null) {
           prev.next = dest;
        }
        dest.previous = prev;
        if(src.next == null)
            return dest;
        return copy(src.next, dest.next, dest).previous;
    }//done
}
