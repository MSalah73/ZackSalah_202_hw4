import java.io.*;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;
///////////////////////////////////////////////////////////////////////////////////////
//Zack Salah
//Pizza app!
//Homework # 4-5
//Programming Systems #202
///////////////////////////////////////////////////////////////////////////////////////
//This file contains the implantation of the toppings class. It contains every function
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
//This file contain the Toppings class and its implantation. The toppings class is an base
// hub for the derived class to preform the concept of Dynamic Binding. I used the Dynamic
//In the program to reduce the amount the code without having to implement everything from
//scratch in the derived classes. This class also help to have multiple toppings in the Pizza
//object with having to change much in the code stricture. This class, other than been an
//abstract base class, is the class where the user can pick the toppings they want or remove a
//toppings they want. I have implemented in such a way that the user can only pick a toppings
//once.
//
//This file also contains the Node subclass. I decided to make a subclass because I only use
//The Node class in this abstract base class. its purpose is to store the toppings the user
//picks and remove them if desired.
/////////////////////////////////////////////////////////////////////////////////////////
//Hierarchy Connections:
//Outer class
//Toppings -"Has a"-> Node(a DLL node)
//Toppings -"Has a"-> String
//Inner class
//Node -"Has a"-> String
/////////////////////////////////////////////////////////////////////////////////////////
public abstract class Toppings{
    protected Node head = null;
    protected String type = null;
    protected int [] choices = null;
    protected String [] to_display = null;

    /**
     * This is the Node class - reason for existence is mentioned above
     */
    protected class Node{
        protected Node next = null;
        protected Node previous = null;
        protected String topping = null;
        protected float price = 0f;

        /**
         *Default Constrictor
         */
        public Node(){}
        /**
         *Constrictor
         * Step 1: copy the string to the Node data members
         * Step 2: copy the price to the Node data member
         */
        public Node(String to_copy, float price){
            topping = new String(to_copy);
            this.price = price;
        }
        /**
         *Copy Constrictor
         * Step 1: copy to_copy's string to the Node data members
         * Step 2: copy  to_copy's price to the Node data member
         */
        public Node(Node to_copy){
            topping = new String(to_copy.topping);
            price = to_copy.price;
        }
    }//done
    /**
     *Default Constrictor
     */
    public Toppings(){}//done
    /**
     *Constrictor
     * Step 1: copy to_copy's type to the toppings's type
     * Step 2: call copy function to copy to_copy's head to current head
     */
    public Toppings(Toppings to_copy){
        type = new String(to_copy.type);
        head = copy(to_copy.head, head, null);
    }//done
    /**
     *Abstract function
     */
    public abstract void display();
    /**
     * Step 1: check if head is null - return
     * Step 2: print the toppings
     * Step 3: call it self recursively with head.next
     */
    protected void display(Node head, int i){
        if(head == null)
            return;
        System.out.println(i +" - "+ head.topping);
        display(head.next, ++i);
    }
    /**
     * Step 1: allocate a temporary node with the copy constructor and pass in String and int
     * Step 2: connect temp's next to head's
     * Step 3: check if head is not null - connect head previous to temp
     * Step 4: update head
     */
    public void add_toppings(String to_copy, float price){
        Node temp = new Node(to_copy, price);
        temp.next = head;
        if(head != null)
            head.previous = temp;
        head = temp;
    }
    /**
     * Step 1: Allocate a float
     * Step 2: set head to the function call remove_toppings with head, float, to_remove passed in
     * Step 3: return float
     */
    public float remove_topping(int to_remove) {
        float[] i = new float[1];
        head = remove_topping(head, i, --to_remove);
        return i[0];
    }
    /**
     * Step 1: return the function call num_toppings with head passed in
     */
    public int num_toppings() {
        return num_toppings(head);
    }
    /**
     *Abstract function
     */
    public abstract float random_toppings(int random) throws IOException;
    /**
     *Abstract function
     */
    public abstract float read_toppings(Scanner read) throws IOException;
    /**
     *Abstract function
     */
    public abstract float set_toppings() throws IOException;
    /**
     * Step 1: allocate an int and set it to zero
     * Step 2: loop twice until to check every element of Current choice and to check if passed in choice are the same
     * Step 3: return the number of similarities
     */
    public int match(Toppings to_comp){
        int to_ret = 0;
        for (int i = 0; i < to_display.length; ++i){
            for (int j = 0; j < to_display.length; ++j){
                if (to_comp.choices[i] == 0)
                    j = to_display.length;
                else if( to_comp.choices[i] == choices[j])
                {
                    ++to_ret;
                    j = to_display.length;
                }
            }
        }
        return to_ret;
    }
    /**
     * Beginning
     * Step 1: set size to choices size
     * Step 2: allocate int add array to size
     * Ste[ 3: allocate scanner, price, done ,and array of string to_add
     *
     * Section 1 - User choice - only if random is zero and read is null
     * Step 1: loop until user choices to quit
     * step 2: prompt the user to pick a choice and display all the toppings
     * step 3: if user choice is equal to one of the toppings - call set choice to add
     * step 4: if not - display error message and loop again
     *
     * Section 2 - Read from file choice - only if random is zero and read is null
     * step 1: loop until size is reached
     * step 2: set add to the read toppings
     *
     * Section 3 - Random choice - only if random is zero and read is null
     * step 1: loop until size is reached
     * step 2: set add to the randomly pick choice
     * step 3: randomize the random value
     *
     * End
     * Step 1: loop until size is reached
     * step 2: split at char -
     * step 3: add to_add with index zero to DLL
     * step 4: call set price with to_add index 1 and set it to price
     * step 5: return price
     */
    protected float non_mandatory(int random, Scanner read) throws IOException {
        char done = 0;
        int size = choices.length;
        int[] add = new int[choices.length];
        float price = 0f;
        String[] to_add = null;
        Scanner user_input = new Scanner(System.in);
        if (random == 0 && read == null) {
            do {
                int choice = 0;
                System.out.println("Please pick a number associated with the desired choice");
                System.out.println("Please pick a desired choice - You can only pick one");
                for (int i = 0; i < size; ++i) {
                    System.out.println((i + 1) + " - " + to_display[i]);
                }
                System.out.println(size + 1 + " - Done with adding");
                System.out.print("Choice: #");
                choice = user_input.nextInt();
                user_input.nextLine();
                if ((choice < size + 2 && choice > 0)) {
                    if (choice < size + 1 && set_choices(size, choice, choices))
                        System.out.println("You already added " + to_display[choice - 1] + "!");
                    else if (choice != size + 1) {
                        set_choices(size, choice, add);
                        System.out.print("Would you like to add another topping? (Y/N)\nChoice: ");
                        done = user_input.next().toUpperCase().charAt(0);
                        user_input.nextLine();
                    } else
                        done = 'N';
                } else {
                    System.err.println("Invalid Choice!\n");
                    System.err.flush();
                }
                System.out.flush();
            } while (done != 'N');
        } else if (read != null){
            String stored = null;
            for(int i = 0; i < to_display.length+1 && read.hasNext() && !type.equals(stored);++i) {
                stored = read.next().replaceAll("\\r\\n","");
                if(stored != null) {
                    for(int j = 0; j < to_display.length; ++j) {
                        if(stored.equals((to_display[j]))){
                            if(!set_choices(to_display.length, j+1, choices)) {
                                set_choices(to_display.length, j+1, add);
                            }
                        }
                    }
                }
            }
        }else{
            Random to_pass = new Random();
            for (int i = random % size+1 ; i < size; ++i){
                random = random % size+1;
                if(!set_choices(size, random, choices))
                    set_choices(size,random, add);
                random = to_pass.nextInt(100) + 1;
            }
        }
        for(int i = 0; i < size; ++i) {
            if(add[i] != 0) {
                to_add = to_display[(add[i] - 1)].split(" - ");
                int len = to_add[0].length();
                float value = add_price(to_add[1]);
                add_toppings(to_add[0], value);
                price += value;
            }
        }
        return price;
    }
    /**
     * Step 1: loop until display's size is reached
     * step 2: allocate a string array
     * step 3: set the string array to to display with split function
     * step 4: check if passed in variable is the same as string array index zero
     * step 4a: loop and check if if any of the choice with index j is the same as indext i+1
     * step 4b: if found set choice to zero
     */
    private void remove(String to_remove){
       for(int i = 0; i < to_display.length; ++i)
       {
           String [] comp = to_display[i].split(" -");
           if(to_remove.equals(comp[0])){
               for (int j = 0; j < to_display.length; ++j){
                   if(choices[j] == i+1)
                       choices[j] = 0;
               }
           }
       }
    }
    /**
     * Step 1: allocate bool variable to zero
     * step 2: loop until size and check if  passed in choice is equal to choices
     * step 2a: set choice to zero and change bool value to true
     * step 3: check if bool value - return bool variable
     * step 4: loop until size
     * step 4a: check if any of choices is zero - set one of them to choice
     * step 5: return bool variable
     */
    private boolean set_choices(int size, int choice, int [] choices){
        boolean to_return = false;
        for (int i = 0; i < size; ++i) {
            if (choice == choices[i]) {
                choice = 0;
                i = size;
                to_return = true;
            }
        }
        if(to_return) {
                return to_return;
        }else{
            for (int i = 0; i < size; ++i) {
                if (choices[i] == 0) {
                    choices[i] = choice;
                    to_return = false;
                    i = size;
                }
            }
        }
        return to_return;
    }
    /**
     * Step 1: allocate scanner object
     * Step 2: set passed in variable to passed in variable with replace function
     * step 3: set the scanner to add variable
     * step 4: return scanner's next float
     */
    private float add_price(String to_add) {
        Scanner to_add_price;
        to_add = to_add.replace("$", "");
        to_add_price = new Scanner(to_add);
        return to_add_price.nextFloat();
    }
    /**
     * Step 1: check if head is null - return 0
     * Step 2: call it self recursively with head's next and return + 1;
     */
    public int num_toppings(Node head){
        if(head == null)
            return 0;
        return num_toppings(head.next)+1;
    }
    /**
     * step 1: allocate scanner read
     * step 2: in a try block allocate File with the file name
     * step 3: allocate read with file object
     * step 4: call use delimiter on read
     * Step 5: loops and set to_populate to the read' next
     * step 6: close file
     */
    protected void read_and_populate(String Filename, String[] to_populate, int size) throws IOException {
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
    protected int read_number_lines(String Filename) throws IOException {
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
    /**
     * Step 1: check if head is null - return current head
     * Step 2: check if passed in to remove is equal zero
     * Step 2a: call remove with head.toppings to remove from choice array
     * Step 2b: set to minus variable to the head price
     * Step 2c: check if head previous is null - set head to head.next and set previous to null and return head
     * Step 2d: set a temp to hold head' previous
     * step 2e: check if head's next is not null - set head previous' next to temp
     * step 2f: set hold's next to head's next and return objects head
     * step 3: call it self recursively and return
     */
    private Node remove_topping(Node head, float [] to_minues, int to_remove) {
        if(head == null)
            return this.head;

        if(to_remove == 0){
            remove(head.topping);
            to_minues[0] = head.price;
            if(head.previous == null)
            {
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
        return remove_topping(head.next, to_minues, --to_remove);
    }
    /**
     * Step 1: check if source is null - return dest
     * Step 2: allocate dest with source passed in
     * Step 3: check if previous is not null - connect previous's next to dest
     * Step 4: connect previous dest to prev
     * Step 5: check if source is null - return dest
     * Step 6: call and return it self recursively with returns' previous
     */
    protected Node copy(Node src, Node dest, Node prev){
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
