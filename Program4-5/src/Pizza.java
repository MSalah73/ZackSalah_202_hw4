import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

///////////////////////////////////////////////////////////////////////////////////////
//Zack Salah
//Pizza app!
//Homework # 4-5
//Programming Systems #202
///////////////////////////////////////////////////////////////////////////////////////
//This file contains the implantation of the Pizza class. It contains every function
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
//This file contain the pizza class and its implantation. The pizza class have all contains
//all teh information of amy pizza. The pizza have many types of toppings. and some toppings
//are mandatory. Even though, the toppings abstract base class handle optional toppings. it can
// handle non optional toppings like crust and sauce. that way this class has similar functionality
//because it need to handle mandatory toppings.
/////////////////////////////////////////////////////////////////////////////////////////
//Hierarchy Connections:
//Pizza -"Has a"-> Toppings
//Pizza -"Has a"-> String
/////////////////////////////////////////////////////////////////////////////////////////
public class Pizza {
    protected float price = 5f;
    protected String name = null;
    protected String Crust = null;
    protected String Sauce = null;
    protected Toppings [] toppings = null;
    /**
     *Default Constrictor
     */
    public Pizza() throws IOException {
        toppings = new Toppings[3];
        toppings[0] = new Cheese();
        toppings[1] = new Protein();
        toppings[2] = new Veggie();
    }
    /**
     *Constrictor
     * step 1: set current name to to_copy
     * step 2: allocate topping of size 3
     * step 3: set each toppings to teh current version - Up casting
     */
    public Pizza(String name) throws IOException {
        this.name = name;
        toppings = new Toppings[3];
        toppings[0] = new Cheese();
        toppings[1] = new Protein();
        toppings[2] = new Veggie();
    }
    /**
     * Copy Constrictor
     * step 1: in a try block - set current name, Crust, Sauce to to_copy's name, crust sauce respectively
     * step 2: allocate topping of size 3
     * step 3: set each toppings to the current version copy's toppings  - Up casting
     * step 4: set price to to_copy's
     */
    public Pizza(Pizza to_copy) throws Exception{
        try {
            name = new String(to_copy.name);
            Crust = new String(to_copy.Crust);
            Sauce = new String(to_copy.Sauce);
        }catch (Exception e){
            System.err.println("Can Not Copy Pizza - Empty");
        }
        toppings = new Toppings[3];
        if(to_copy.toppings != null){
            toppings[0] = new Cheese((Cheese) to_copy.toppings[0]);
            toppings[1] = new Protein((Protein) to_copy.toppings[1]);
            toppings[2] = new Veggie((Veggie) to_copy.toppings[2]);
        }
        price = to_copy.price;
    }
    /**
     * Step 1: allocate array of strings
     * step 2: set crust to read's next
     * step 3: set to add to crust with split on char -
     * step 4: set crust to add index 0
     * step 5: call add price and pass in to_add index 1
     * step 6: repeat step 2 to tep 5 with sauce
     * step 7: set price to toppings read functions with read passed
     */
    public void assemble_read(Scanner read){
        try {
            String [] to_add = null;
            Crust = read.next().replaceAll("\\r\\n","");
            to_add = Crust.split("-");
            Crust= to_add[0];
            add_price(to_add[1]);
            Sauce = read.next().replaceAll("\\r\\n","");
            to_add = Sauce.split("-");
            Sauce = to_add[0];
            add_price(to_add[1]);
            price += toppings[0].read_toppings(read);
            price += toppings[1].read_toppings(read);
            price += toppings[2].read_toppings(read);
        }
        catch(Exception e){
        }
    }
    /**
     * Step 1: allocate random variable
     * step 2: set name today's special pizza
     * step 2: allocate Crust by calling mandatory with random passed
     * step 2: allocate Sauce by calling mandatory with random passed
     * step 7: set price to toppings read functions with random passed in
     */
    public void assemble_randomly() throws IOException {
        Random to_pass;
        to_pass = new Random();
        name = new String("Today's Special");
        Crust = new String(mandatory("Crust.txt", (to_pass.nextInt(100)+1)));
        Sauce = new String(mandatory("Sauce.txt", (to_pass.nextInt(100)+1)));
        price += toppings[0].random_toppings(to_pass.nextInt(100)+1);
        price += toppings[1].random_toppings(to_pass.nextInt(100)+1);
        price += toppings[2].random_toppings(to_pass.nextInt(100)+1);
    }
    /**
     * step 1: set name to passed in name if not null
     * step 2: if null set name to build your own
     * step 3: set Crust by calling mandatory with file name crust.txt
     * step 4: set Sauce by calling mandatory with file name sauce.txt
     * step 7: set price to toppings by calling set toppings
     */
    public void assemble_pizza(String to_name) throws IOException {
        if(to_name != null)
            name = new String(to_name);
        else
            name = new String("Build your own");
        Crust = new String(mandatory("Crust.txt", 0));
        Sauce = new String(mandatory("Sauce.txt", 0));
        price += toppings[0].set_toppings();
        price += toppings[1].set_toppings();
        price += toppings[2].set_toppings();
    }
    /**
     * step 1: ask the user which to modify
     * step 2: modify teh choice and update the price
     * step 3: asks the user if they want to modify and thing else
     */
    public void modify() throws IOException {
        char done = 0;
        int choice = 0;
        int choice2 = 0;
        Scanner read = new Scanner(System.in);
        do {
            do {
                System.out.println("Please pick a number associated with a desired choice?");
                System.out.println("What would you like to change?");
                System.out.println("1 - Crust type");
                System.out.println("2 - Sauce");
                System.out.println("3 - Cheese toppings");
                System.out.println("4 - Protein toppings");
                System.out.println("5 - Veggie toppings");
                choice = read.nextInt();
                if(!(choice < 6 && choice > 0)){
                    System.err.println("Invalid choice!!");
                }
            } while (!(choice < 6 && choice > 0));
            if(choice == 1)
                Crust = new String(mandatory("Crust.txt", 0));
            else if(choice == 2)
                Sauce = new String(mandatory("Sauce.txt", 0));
            else {
                do {
                    System.out.println("Please pick a number associated with a desired choice?");
                    System.out.println("What would you like to do?");
                    System.out.println("1 - Add toppings");
                    System.out.println("2 - Remove a topping");
                    System.out.println("3 - Previous menu");
                    choice2 = read.nextInt();
                    if (!(choice2 < 4 && choice2 > 0)) {
                        System.err.println("Invalid choice!!");
                    }
                } while (!(choice2 < 4 && choice2 > 0));
                if (choice2 == 1) {
                    price += toppings[choice - 3].set_toppings();
                } else if (choice2 == 2) {
                    int limit = toppings[choice - 3].num_toppings();
                    do {
                        System.out.println("Please pick a number associated with a desired choice?");
                        System.out.println("What would you like to remove?");
                        toppings[choice - 3].display();
                        System.out.println(limit +1 + " - Cancel removal");
                        choice2 = read.nextInt();
                        if (!(choice2 < limit+2 && choice2 > 0))
                            System.err.println("Invalid choice!!");
                    } while (!(choice2 < limit+2 && choice2 > 0));
                    if(choice2 != limit+2)
                        price -= toppings[choice - 3].remove_topping(choice2);
                }
            }
            if(choice2 != 3) {
                System.out.println("Do you still want to modify it? (Y/N)");
                done = read.next().toUpperCase().charAt(0);
            }
        } while (done != 'N');
    }
    /**
     *Step 1: display price nad name
     * step 2: print sauce and crust
     * step 3: call display for each toppings
     */
    public void display() {
        System.out.printf("Price: $%.2f\nPizza name: %s\n", price, name);
        System.out.println("Crust: "+Crust);
        System.out.println("Sauce: "+Sauce);
        toppings[0].display();
        toppings[1].display();
        toppings[2].display();
        System.out.println();
    }
    /**
     *step 1: set to minus to function call remove toppings
     * step 2: check if minus is equal zero - display a message
     * step 3: minus the price with to_minus
     */
    public void remove_topping(int to_remove, int type){
        float to_minus =  toppings[type - 1].remove_topping(to_remove);
        if(to_minus == 0f)
            System.err.println("\nRequested topping is not added to remove");
        else
            price -= to_minus;
        System.err.flush();
    }
    /**
     * step 1: set to ret to zero
     * step 2: check if passed in argus is equal to current's crust - add 1 to ret
     * step 3: check if passed in argus is equal to current's sauce - add 1 to ret
     * step 4: set to return to toppings match function  - for all toppings
     * step 5: return to ret
     */
    public int match(Pizza to_comp) {
        int to_ret = 0;
        if(to_comp.Crust.equals(Crust))
            ++to_ret;
        if(to_comp.Sauce.equals(Sauce))
            ++to_ret;
        to_ret += toppings[0].match(to_comp.toppings[0]);
        to_ret += toppings[1].match(to_comp.toppings[1]);
        to_ret += toppings[2].match(to_comp.toppings[2]);
        return  to_ret;
    }
    /**
     *step 1: read the file name and place its contained  to a variable
     * step 2: prompt the user to pick one choice
     * step 3: add its price the total and return the string
     */
    private String mandatory(String Filename, int random) throws IOException {
        int choice = 0;
        int size = read_number_lines(Filename);
        String [] to_display  = new String[size];
        String [] to_add;
        Scanner user_input = new Scanner(System.in);
        read_and_populate(Filename, to_display, size);
        if(Filename.equals("Crust.txt") && Crust != null) {
            minus_price(Crust, to_display);
        }else if (Filename.equals("Sauce.txt") && Sauce != null) {
            minus_price(Crust, to_display);
        }

        if(random == 0) {
            do {
                System.out.println("Please pick a number associated with the desired choice");
                System.out.println("Please pick a desired choice - You can only pick one");
                for (int i = 0; i < size; ++i) {
                    System.out.println((i + 1) + " - " + to_display[i]);
                }
                System.out.print("Choice: #");
                choice = user_input.nextInt();
                user_input.nextLine();
                if(!(choice < size +1 && choice > 0)){
                    System.err.println("Invalid Choice!\n");
                }
            } while (!(choice < size +1 && choice > 0));
        }else{
            choice = random % size;
            ++choice;
        }

        to_add = to_display[(choice - 1)].split("-");
        add_price(to_add[1]);
        return to_add[0];
    }
    /**
     * step 1: split the data and return the price
     */
    private void add_price(String to_add) {
        Scanner to_add_price;
        to_add = to_add.replace("$", "");
        to_add_price = new Scanner(to_add);
        price += to_add_price.nextFloat();
    }
    /**
     * step 1: allocate split to null
     * step 2: split current data to set it to split
     * step 3:  minus the price
     */
    private void minus_price(String to_minus, String [] to_split) {
        String [] split = null;
        for (int i =0; i < to_split.length; ++i) {
            split = to_split[i].split(" - ");
            if(split[0].equals(to_minus)) {
               i  = to_split.length;
            }
        }
        Scanner to_add_price;
        split[1] = split[1].replace("$", "");
        to_add_price = new Scanner(split[1]);
        price -= to_add_price.nextFloat();
    }
    /**
     * step 1: allocate scanner read
     * step 2: in a try block allocate File with the file name
     * step 3: allocate read with file object
     * step 4: call use delimiter on read
     * Step 5: loops and set to_populate to the read' next
     * step 6: close file
     */
    private void read_and_populate(String Filename, String[] to_populate, int size) throws IOException {
        Scanner read;
        try {
            File load= new File(Filename);
            read = new Scanner(load);
            read.useDelimiter(";");
            for(int i = 0; i < size; ++i){
                to_populate[i] = read.next().replaceAll("\\r\\n","");
            }
            read.close();
        }
        catch(Exception e){
            System.err.println("File Can Not Be Opened!!!");
        }
    }
    /**
     * step 1: allocate int and set it to zero
     * step 2: in a try block allocate Buffer read with the file name
     * step 3: loop until next line is null and increment num
     * step 4 close the file
     * step 5: return num
     */
    private int read_number_lines(String Filename) throws IOException {
        int num = 0;
        try {
            BufferedReader fin = new BufferedReader(new FileReader(Filename));
            while(fin.readLine() != null)
                ++num;
            fin.close();
        }
        catch (IOException e) {
            System.err.println("File Can Not Be Opened!!!");
        }
        return num;
    }
}
