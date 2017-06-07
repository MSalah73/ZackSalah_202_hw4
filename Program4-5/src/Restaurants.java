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
//This file contains the implantation of the Restaurants class. It contains every function
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
//Toppings -"Has a"-> Node(a DLL node)
//Toppings -"Has a"-> String
/////////////////////////////////////////////////////////////////////////////////////////
public class Restaurants {
    protected Node root = null;
    public Restaurants() throws Exception {
        File load = new File("Restaurants.txt");
        Scanner read = new Scanner(load);
        read.useDelimiter(";");
        String to_pass = null;
        do {
            to_pass = read.next().replaceAll("\\r\\n", "");
            Node temp = new Node(to_pass, read);
            insert(temp);
            read.next();
        }while(read.hasNext());
    }
    public void insert(Node to_add)
    {
        root = insert(root,to_add);
    }
    private Node insert(Node root, Node to_add){
        if(root == null)
        {
            root = to_add;
            return root;
        }
        if(root.zip.compareTo(to_add.zip) < 0)
            root.right = insert(root.right, to_add);
        else
            root.left = insert(root.left, to_add);
        return root;
    }
    public void delete(String to_remove) {
        root = delete(root, to_remove);
    }
    private Node delete(Node root, String to_remove){
        if(root == null) {
            return root;
        }
        if(root.zip.equals(to_remove))
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
              Node lhold = root.left;
              Node rhold = root.right;
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
        else if(root.zip.compareTo(to_remove) < 0)
            root.right = delete(root.right, to_remove);
        else
            root.left = delete(root.left, to_remove);
        return root;
    }
    public void delete_all(int remove) {
        root = null;
    }
    private Node Seccessor(Node find){
        if(find == null || find.left == null)
            return find;
        return Seccessor(find.left);
    }
    public void retrieve(Pizza to_retrieve, String zip_comp) throws IOException {
      Node to_display = retrieve(root, to_retrieve, zip_comp);
      if(to_display != null)
          to_display.display();
      else
         System.err.println("No match is found");
    }
    private Node retrieve(Node root, Pizza to_retrieve, String zip_comp){
        if(root == null) {
            return root;
        }
        if(root.zip.equals(to_retrieve)) {
            root.display();
            return root;
        }
        else if(root.zip.compareTo(zip_comp) < 0)
            retrieve(root.right, to_retrieve, zip_comp);
        else
            retrieve(root.left, to_retrieve, zip_comp);
        return root;
    }

    public void retrieve_all(String to_retrieve)
    {
        retrieve_all(root, to_retrieve);
    }
    private void retrieve_all(Node root, String to_retrieve){
        if(root == null) {
            return;
        }
        if(root.zip.equals(to_retrieve)) {
            root.display();
        }
        else if(root.zip.compareTo(to_retrieve) < 0)
            retrieve_all(root.right, to_retrieve);
        else
            retrieve_all(root.left, to_retrieve);
    }
    public void display(){
        display(root);
    }
    private void display(Node root){
        if(root == null)
            return;
        display(root.right);
        root.display();
        display(root.left);
    }
    public static void main(String[] args) throws Exception {
        Restaurants a = new Restaurants();
        //a.retrieve("lol");
       // a.display();
    }
}
