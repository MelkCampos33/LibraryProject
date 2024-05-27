package Libary;

import javax.swing.JFrame;

public class NormalUser extends User{

    public NormalUser(String name) {

        super(name);
        this.operations = new IOOperation[] { // atos relacionados a permissão do usuário

                new ViewBooks(),
                new Search(),
                new PlaceOrder(),
                new BorrowBook(),
                //new CalculateFine(),
                //new ReturnBook(),
                new Exit()
        };
    }

    public NormalUser(String name, String email, String phonenumber) {

        super(name, email, phonenumber);

        this.operations = new IOOperation[] {

                new ViewBooks(),
                new Search(),
                new PlaceOrder(),
                new BorrowBook(),
                new CalculateFine(),
                new ReturnBook(),
                new Exit()
        };
    }

    @Override
    public void menu(Database database, User user) {

                  String[] data = new String[7]; // array de opções do menu inicial

                data[0] =  "Visualizar Livros";
                data[1] =  "Procurar";
                data[2] =  "Realizar Pedido";
                data[3] =  "Alugar Livro";
                data[4] =  "Taxa de Atraso de Entrega";
                data[5] =  "Devolver Livro";
                data[6] =  "Sair";

                JFrame frame = this.frame(data, database, user);
                frame.setVisible(true);
    }

    public String toString() {
        return " Nome do usuário: " + name + "\n E-mail: " + email + "\n Telefone: " + phonenumber + "\n Cargo: Usuário padrão";
    }
}
















