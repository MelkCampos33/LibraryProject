package Libary;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Database {

    private ArrayList<User> users;
    private ArrayList<String> usernames;
    private ArrayList<Book> books;
    private ArrayList<String> booknames;
    private ArrayList<Order> orders;
    private ArrayList<Borrowing> borrowings;

    private File usersfile = new File("D:\\Libary Management System\\Data\\Users");
    private File booksfile = new File("D:\\Libary Management System\\Data\\Books");
    private File ordersfile = new File("D:\\Libary Management System\\Data\\Orders");
    private File borrowingsfile = new File("D:\\Libary Management System\\Data\\Borrowings");
    private File folder = new File("D:\\Libary Management System\\Data");


    public Database() {

        if (!folder.exists()) {
            folder.mkdirs();
        }

        if (!usersfile.exists()) {

            try {
                usersfile.createNewFile();
            } catch (Exception event) {
            }
        }


        if (!booksfile.exists()) {

            try {
                booksfile.createNewFile();
            } catch (Exception event) {

            }
        }


        if (!ordersfile.exists()) {

            try {
                ordersfile.createNewFile();
            } catch (Exception event) {
            }
        }


        if (!borrowingsfile.exists()) {

            try {
                borrowingsfile.createNewFile();
            } catch (Exception event) {
            }
        }

        users = new ArrayList<User>();
        usernames = new ArrayList<String>();
        books = new ArrayList<Book>();
        booknames = new ArrayList<String>();
        orders = new ArrayList<Order>();
        borrowings = new ArrayList<Borrowing>();

        getUsers();
        getBooks();
        getOrders();
        getBorrowings();
    }

    // adiciona novo usuário
    public void AddUser(User userValue) {

        users.add(userValue);
        usernames.add(userValue.getName());
        saveUsers();
    }

    // ------------------------------ login no sistema --------------------------------------------------------------------------------------
    public int login(String phonenumber, String email) {

        if(phonenumber == null || phonenumber.isEmpty() || email == null || email.isEmpty()) {
            System.err.println("O número ou email estão inválidos!");
            return -1;
        }

        for(int i = 0; i < users.size(); i++) {

            User userValue = users.get(i);

            // validação dos dados de login
            if(userValue.getPhonenumber().equals(phonenumber) && userValue.getEmail().equals(email)) {
                return i;
            }
        }

        System.out.println("Falha ao tentar acessar a conta: O usuário não existe");
        return -1;

    }


    public User getUser(int value) {
        return users.get(value);
    }

    public void AddBook(Book book) {

        books.add(book);
        booknames.add(book.getName());
        saveBooks();
    }


    private void getUsers() {

        String firstText = "";

        try {
            BufferedReader br_1 = new BufferedReader(new FileReader(usersfile));
            String string_1;

            while ((string_1 = br_1.readLine()) != null) {
                firstText += string_1 + "\n";
            }

            br_1.close();

        } catch (Exception event) {
            System.err.println("Erro na leitura do arquivo: " + event.toString());
        }

        if (!firstText.isEmpty()) {
            String[] arrayValue1 = firstText.split("<NewUser/>"); // passando a string para um array de usuarios

            for (String stringValue : arrayValue1) {
                String[] arrayValue2 = stringValue.split("<N/>");

               if(arrayValue2.length >= 4) {

                   if (arrayValue2[3].matches("Admin")) {

                       User user = new Admin(arrayValue2[0], arrayValue2[1], arrayValue2[2]);
                       users.add(user);
                       usernames.add(user.getName());


                   } else {

                       User user = new NormalUser(arrayValue2[0], arrayValue2[1], arrayValue2[2]);
                       users.add(user);
                       usernames.add(user.getName());
                   }

               } else {
                   System.err.println("Erro na leitura do arquivo: " + stringValue);
               }
            }
        } else {
            System.err.println("Os dados do usuário não foram encontrados");
        }
    }


// -----------------------------------------------------------------


    private void saveUsers() {

        String firstText = "";

        for (User user : users) {
            firstText = firstText + user.toString() + "<NewUser/>\n";
        }

        try {

            PrintWriter printwriter_value = new PrintWriter(usersfile);
            printwriter_value.print(firstText);
            printwriter_value.close();

        } catch (Exception event) {
            System.err.println(event.toString());
        }
    }

// -----------------------------------------------------------------

    private void saveBooks() {

        String firstText = "";

        for (Book book : books) {
            firstText = firstText + book.toString2() + "<NewBook/>\n";
        }

        try {
            PrintWriter printwriter_value = new PrintWriter(booksfile);
            printwriter_value.print(firstText);
            printwriter_value.close();

        } catch (Exception event) {
            System.out.println(event.toString());
        }
    }

// -----------------------------------------------------------------

    private void getBooks() {
        String firstText = "";

        try {
            BufferedReader bufferValue = new BufferedReader(new FileReader(booksfile));
            String stringValue;

            while ((stringValue = bufferValue.readLine()) != null) {
                firstText = firstText + stringValue;
            }

            bufferValue.close();

        } catch (Exception event) {
            System.err.println(event.toString());
        }

        if (!firstText.isEmpty()) {
            String[] bookArray = firstText.split("<NewBook/>");

            for (String stringValue : bookArray) {

                Book book = parseBook(stringValue);

                if(book != null) {
                    books.add(book);
                    booknames.add(book.getName());

                } else {
                    System.err.println("Formato inválido!");
                }
            }
        }
    }

    public Book parseBook(String stringValue) {

        String[] array = stringValue.split("N/");

        if(array.length >= 7) {

            Book book = new Book();
            book.setName(array[0]);
            book.setAuthor(array[1]);
            book.setPublisher(array[2]);
            book.setAdress(array[3]);

            book.setQty(Integer.parseInt(array[4]));
            book.setPrice(Double.parseDouble(array[5]));
            book.setBrwcopies(Integer.parseInt(array[6]));

            return book;
        } else {

            // se caso o array não tenha elementos suficiente
            System.err.println("Formato inválido.");
            return null;
        }
    }

    public ArrayList<Book> getAllBooks() {
        return books;
    }

    public int getBook(String bookname) {

        int i = -1;

        for (Book book : books) {
            if (book.getName().matches(bookname)) {
                i = books.indexOf(book);
            }
        }

        return i;
    }

    public Book getBook(int i) {
        return books.get(i);
    }

    public void deleteBook(int i) {

        books.remove(i);
        booknames.remove(i);
        saveBooks();
    }


    public void deleteAllData() {

        if (usersfile.exists()) {
            try {
                usersfile.delete();
            } catch (Exception event) {

            }

            if (booksfile.exists()) {

                try {
                    booksfile.delete();
                } catch (Exception event) {
                }
            }

            if (ordersfile.exists()) {

                try {
                    ordersfile.delete();
                } catch (Exception event) {
                }
            }

            if (borrowingsfile.exists()) {

                try {
                    borrowingsfile.delete();
                } catch (Exception event) {}
            }
        }
    }


    public void addOrder(Order order, Book book, int bookindex) {

        orders.add(order);
        books.set(bookindex, book);
        saveOrders();
        saveBooks();
    }

    private void saveOrders() {

        String firstText = "";

        for (Order order : orders) {

            firstText = firstText + order.toString2() + "<NewOrder/>\n";
        }

        try {
            PrintWriter printValue = new PrintWriter(ordersfile);
            printValue.print(firstText);
            printValue.close();

        } catch (Exception event) {
            System.out.println(event.toString());
        }
    }


    private void getOrders() {

        String firstText = "";

        try {
            BufferedReader bufferValue = new BufferedReader(new FileReader(ordersfile));
            String stringValue;

            while ((stringValue = bufferValue.readLine()) != null) {
                firstText = firstText + stringValue;
            }
            bufferValue.close();

        } catch (Exception event) {
            System.out.println(event.toString());
        }

        if (!firstText.matches("") || !firstText.isEmpty()) {
            String[] stringArray = firstText.split("<NewOrder/>");

            for (String stringValue : stringArray) {

                Order order = parseOrder(stringValue);
                orders.add(order);
            }
        }
    }

    public boolean userExists(String name) {
        boolean isTrue = false;

        for (User user : users) {
            if (user.getName().toLowerCase().matches(name.toLowerCase())) {
                isTrue = true;
                break;
            }
        }
        return isTrue;
    }

    private User getUserByName(String name) {

        for(User user : users) {
            if(user != null && user.getName().matches(name)) {
                return user;
            }
        }

        return null;
    }

    private Order parseOrder(String stringValue) {

        String[] arrayValue = stringValue.split("<N/>");
        Order order = new Order(

                books.get(getBook(arrayValue[0])),
                getUserByName(arrayValue[1]),
                Double.parseDouble(arrayValue[2]),
                Integer.parseInt(arrayValue[3])
        );

        return order;
    }

    public ArrayList<Order> getAllOrders() {
        return orders;
    }


    private void saveBorrowings() {
        String firstText = "";

        for (Borrowing borrowing : borrowings) {
            firstText = firstText + borrowing.toString2() + "<NewBorrowing/>\n";
        }

            try {
                PrintWriter printValue = new PrintWriter(borrowingsfile);
                printValue.print(firstText);
                printValue.close();

            } catch (Exception event) {
                System.out.println(event.toString());
            }
        }


        private void getBorrowings () {
            String firstText = "";

            try {
                BufferedReader bufferValue = new BufferedReader(new FileReader(borrowingsfile));
                String stringValue;

                while ((stringValue = bufferValue.readLine()) != null) {
                    firstText = firstText + stringValue;
                }
                bufferValue.close();

            } catch (Exception event) {
                System.out.println(event.toString());
            }

            if (!firstText.matches("") || !firstText.isEmpty()) {
                String[] arrayValue = firstText.split("<NewBorrowing/>");

                for (String stringValue : arrayValue) {
                    Borrowing borrowing = parseBorrowing(stringValue);
                    borrowings.add(borrowing);
                }
            }
        }

        // Descrção do aluguel do filme
        private Borrowing parseBorrowing (String stringBorrow){

            String[] arrayValue = stringBorrow.split("<N/>");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate start = LocalDate.parse(arrayValue[0], formatter);
            LocalDate finish = LocalDate.parse(arrayValue[1], formatter);
            Book book = getBook(getBook(arrayValue[3]));
            User user = getUserByName(arrayValue[4]);
            Borrowing borrowingValue = new Borrowing(start, finish, book, user);
            return borrowingValue;
        }


        public void borrowBook (Borrowing borrowingValue, Book book,int bookindex){

            borrowings.add(borrowingValue);
            books.set(bookindex, book);
            saveBorrowings();
            saveBooks();
        }


        public ArrayList<Borrowing> getBorrowingValue () {
            return borrowings;
        }

        public void returnBook (Borrowing btw, Book book, int bookindex){

            borrowings.remove(btw);
            books.set(bookindex, book);
            saveBorrowings();
            saveBooks();
        }
    }

