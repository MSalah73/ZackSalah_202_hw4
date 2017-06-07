import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

///////////////////////////////////////////////////////////////////////////////////////
//Zack Salah
//Pizza app!
//Homework # 4-5
//Programming Systems #202
///////////////////////////////////////////////////////////////////////////////////////
//This file contains the implantation of the Protein class. It contains every function
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
//This file contain the protein class and its implantation. The class manges the protein
//toppings. I used the Dynamic Binding in here because with have similar functionality
//and interface, but for each child the some differences that the concept of Dynamic Binding
//helps reduce the amount of code and complexity in this file.
/////////////////////////////////////////////////////////////////////////////////////////
//Hierarchy Connections:
//Protein -"Is a"-> Toppings
/////////////////////////////////////////////////////////////////////////////////////////
public class Protein extends Toppings {
    /**
     * Default Constructor
     * Step 1: Set the type with class name
     * Step 2: Get the numbers of lines in the file
     * Step 3: Allocate Choices to the size
     * Step 4: Allocate to_display with the size
     * Step 5: Call populate on display
     *
     * The reason I have To_display as data members is to reduce the amounts of operations.
     * Since the file is not going to be changed. As for Choice to to keep track you the user's
     * Chosen toppings. This help us to prevent repetitive toppings.
     */
    public Protein() throws IOException {
        type = new String("Protein");
        int size = read_number_lines("Protein.txt");
        choices = new int[size];
        to_display = new String[size];
        read_and_populate("Protein.txt",to_display, size);
    }
    /**
     * Copy Constructor
     * Step 1: Call parent's Copy Constructor\
     * Step 2: Get the size of to_copy's to_display
     * Step 3: Allocate Choices to the size
     * Step 4: Allocate to_display with the size
     * Step 5: Set to display to copy's to_display
     * Step 6: Set to  Choices to copy's Choices
     */
    public Protein(Protein topping) {
        super(topping);
        int size = topping.to_display.length;
        choices = new int[size];
        to_display = new String[size];
        for (int i = 0; i < size; ++i){
           to_display[i] = new String(topping.to_display[i]);
           choices[i] = topping.choices[i];
        }
    }
    /**
     *Step 1: Display the type
     *Step 2: Check if head is null - Display a message
     *Step 3: Call parent's display
     */
    public void display(){
        System.out.println("----------"+type+"----------");
        if(head == null)
            System.out.println("No "+type+" toppings added");
        super.display(head, 1);
    }
    /**
     *Step 1: call and return the parent's function non mandatory with Scanner and random to null and random respectively.
     */
    @Override
    public float random_toppings(int random) throws IOException {
        return non_mandatory(random, null);
    }
    /**
     *Step 1: call and return the parent's function non mandatory with Scanner and random to read and 0 respectively.
     */
    @Override
    public float read_toppings(Scanner read) throws IOException {
        return non_mandatory(0,read);
    }
    /**
     *Step 1: call and return the parent's function non mandatory with Scanner and random to null and 0 respectively.
     */
    @Override
    public float set_toppings() throws IOException {
        return non_mandatory(0, null);
    }
}
