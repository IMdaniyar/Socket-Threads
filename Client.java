package SocketThreads2EX;
        import java.io.ObjectInputStream;
        import java.io.ObjectOutputStream;
        import java.net.Socket;
        import java.util.ArrayList;
        import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Your name:");
        String name = in.next();
        try {
            Socket socket = new Socket("127.0.0.1", 1234);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            while (true){
                System.out.println("Press [1] to List Books");
                System.out.println("Press [2] to Add Book");
                System.out.println("Press [0] to Disconnect");

                int choice = in.nextInt();
                if(choice == 1){
                    PackageData pd = new PackageData("LIST", null, null);
                    outputStream.writeObject(pd);
                    PackageData pack = (PackageData) inputStream.readObject();
                    ArrayList<Book> books = pack.getBooks();
                    for(Book b : books){
                        System.out.println(b.toString());
                    }
                } else if(choice == 2){
                    System.out.println("Insert id");
                    int id = in.nextInt();
                    System.out.println("Insert name");
                    String bookName = in.next();
                    System.out.println("Insert author");
                    String author = in.next();
                    Book book = new Book(id ,bookName, author);
                    PackageData pd = new PackageData("ADD",null,  book);
                    outputStream.writeObject(pd);
                } else if(choice == 0){
                    socket.close();
                    break;
                } else
                    System.out.println("Try Again!");
            }
        } catch (Exception e){

        }
    }
}