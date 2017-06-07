import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
///////////////////////////////////////////////////////////////////////////////////////
//NOTE: Please disregard this file as it its still under developments
//Thank You
///////////////////////////////////////////////////////////////////////////////////////
//Zack Salah
//Pizza app!
//Homework # 4-5
//Programming Systems #202
///////////////////////////////////////////////////////////////////////////////////////
//This file contains the implantation of the Node class. It contains every function
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
/////////////////////////////////////////////////////////////////////////////////////////
//Hierarchy Connections:
//Outer class
//Node -"Has a"-> node(a BST node)
//Node -"Has a"-> String
//inner class
//node -"Is a"-> Pizza
/////////////////////////////////////////////////////////////////////////////////////////
public class Node{
   protected Node right = null;
   protected Node left = null;
   protected node root = null;
   protected String name = null;
   protected String zip = null;
   protected class node extends Pizza{
      protected node right = null;
      protected node left = null;
      public node(node to_copy) throws Exception {
         super(to_copy);
      }
      public node(String to_copy) throws Exception {
         super(to_copy);
      }
      public node() throws IOException {
          super();
      }
   }

   public Node() {
   }
   public Node(String name, Scanner read) throws Exception {
      this.name = name;
      this.zip = read.next().replaceAll("\\r\\n","");
      String to_pass = null;
      do {
         to_pass = read.next().replaceAll("\\r\\n","");
         if(!to_pass.equals("Restaurant")) {
            node to_add = new node(to_pass);
            to_add.assemble_read(read);
            insert(to_add);
         }
      }while(!to_pass.equals("Restaurant"));
   }
   public void insert(node to_add) throws Exception {
      root = insert(root, to_add);
   }
   private node insert(node root, node to_add) throws Exception {
      if(root == null)
      {
         root = new node(to_add);
         return root;
      }
      if(root.name.compareTo(to_add.name) < 0)
         root.right = insert(root.right, to_add);
      else
         root.left = insert(root.left, to_add);
      return root;
   }
   public void delete(String to_remove)
   {
      root = delete(root, to_remove);
   }
   private node delete(node root, String to_remove){
      if(root == null) {
         return root;
      }
      if(root.name.equals(to_remove))
      {
         if(root.left == null && root.right == null){
            root = null;
         }else if(root.left == null)
         {
            root = root.right;
         }else if(root.right == null)
         {
            root = root.left;
         }else{
            node lhold = root.left;
            node rhold = root.right;
            root = null;
            root = Seccessor(rhold);
            if(root != rhold)
            {
               rhold.left = root.right;
               root.right = rhold;
            }
            root.left = lhold;
         }
         return root;
      }
      else if(root.name.compareTo(to_remove) < 0)
         root.right = delete(root.right, to_remove);
      else
         root.left = delete(root.left, to_remove);
      return root;
   }
   public void delete_all()
   {
      root = null;
   }
   private node Seccessor(node find){
      if(find == null || find.left == null)
         return find;
      return Seccessor(find.left);
   }
   public void retrieve(Pizza to_retrieve) {
      node to_display = retrieve(root,null, to_retrieve, 0);
      if(to_display != null)
          to_display.display();
      else
         System.err.println("No match is found");
   }
   private node retrieve(node root,node to_return, Pizza to_retrieve, int found){
      if(root == null) {
         return to_return;
      }
      int match = root.match(to_retrieve);
      if(match == found)
      {
         int Choice = 0;
         Scanner read = new Scanner(System.in);
         do {
            System.out.println("Please pick a number associated with the desired choice");
            System.out.println("Pizzas below have the same number of matches! \nPlease pick a desired choice - You can only pick one");
            System.out.println("\033[31mChoice 1:\033[m");
            root.display();
            System.out.println("\033[31mChoice 2:\033[m");
            to_return.display();
            System.out.println("\033[31mNote: Scroll up to see the choices!\033[m");
            System.out.print("Choice #");
            Choice = read.nextInt();
            read.nextLine();
            if (Choice == 1)
               to_return = root;
            if (Choice == 2);
            if(!(Choice > 0 && Choice < 3))
                System.out.println("\033[31mInvalid choice!\033[m");
         }while(!(Choice > 0 && Choice < 3));
      }
      if(match > found) {
         found = match;
         to_return = root;
      }
      node lhold, rhold;
      lhold = retrieve(root.left,to_return, to_retrieve, found);
      rhold = retrieve(root.right,to_return, to_retrieve, found);
      if(to_return == lhold && to_return == rhold)
          return to_return;
      if(to_return == lhold)
         return rhold;
      return lhold;
   }
   public void retrieve_all(Pizza to_retrieve)
   {
      retrieve_all(root, to_retrieve, best_match(root, to_retrieve , 0));
   }
   private int best_match(node root, Pizza to_retrieve, int found){
       if(root == null) {
         return found;
      }
      int match = root.match(to_retrieve);
      if(match > found) {
         found = match;
      }
      int lhold, rhold;
      lhold = best_match(root.left, to_retrieve, found);
      rhold = best_match(root.right, to_retrieve, found);
      if(found > lhold && found > rhold)
         return found;
      else if(lhold > rhold)
         return lhold;
      return rhold;
   }
   private void retrieve_all(node root, Pizza to_retrieve, int found){
      if(root == null) {
         return;
      }
      int match = root.match(to_retrieve);
      if(match == found) {
         root.display();
      }
      retrieve_all(root.left, to_retrieve, found);
      retrieve_all(root.right, to_retrieve, found);
   }
   public void display(){
      System.out.println("----------"+name+"----------");
      System.out.println("Zipcode: "+ zip);
      System.out.println();
      display(root);
   }
   private void display(node root){
      if(root == null)
         return;
      display(root.right);
      root.display();
      display(root.left);
   }
}