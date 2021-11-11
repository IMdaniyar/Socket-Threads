package SocketThreads2EX;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread {
    private Socket socket;
    private static ArrayList<Book> books;

    public ClientHandler(Socket socket, ArrayList<Book> books){
        this.socket = socket;
        this.books = books;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    @Override
    public void run() {

        try {
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            PackageData pd = null;
            while ((pd = (PackageData) inputStream.readObject()) != null){
                if(pd.getOperationType().equals("LIST")){
                    PackageData pack = new PackageData("LIST", (ArrayList<Book>) books.clone(), null);
                    outputStream.writeObject(pack);
                } else if (pd.getOperationType().equals("ADD")){
                    books.add(pd.getBook());
                    setBooks(books);
                    System.out.println("book added!");
                }
            }
        } catch (Exception e){

        }
    }
}
