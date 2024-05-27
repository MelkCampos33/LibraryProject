package Libary;

import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ViewBooks implements IOOperation{

    @Override
    public void oper(Database database, User user) {

        int rows = database.getAllBooks().size() + 1;
        int height = rows * 60 + 100;
        JFrame frame = Main.frame(1000, height);

        JLabel title = Main.title("Visualizar Livros");
        frame.getContentPane().add(title, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(rows, 7, 0, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));
        panel.setBackground(null);

        String[] row1 = new String[] {
                "Nome", "Autor", "Editora", "Local de Coleta", "Quantidade", "Preço", "Copias para alugar"
        };

        for(String stringValue : row1) {
            JLabel label = label(stringValue);
            panel.add(label);
        }

       for(Book bookValue : database.getAllBooks()) {

           JLabel label1 = label(bookValue.getName());
           JLabel label2 = label(bookValue.getAuthor());
           JLabel label3 = label(bookValue.getPublisher());
           JLabel label4 = label(bookValue.getAdress());
           JLabel label5 = label(String.valueOf(bookValue.getQty()));
           JLabel label6 = label(String.valueOf(bookValue.getPrice()));
           JLabel label7 = label(String.valueOf(bookValue.getBrwcopies()));

           panel.add(label1);
           panel.add(label2);
           panel.add(label3);
           panel.add(label4);
           panel.add(label5);
           panel.add(label6);
           panel.add(label7);
       }

       frame.getContentPane().add(panel, BorderLayout.CENTER);
       frame.setVisible(true);
    }

    private JLabel label(String text) {

        JLabel label = Main.label(text);
        label.setBackground(Color.white);
        label.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        label.setOpaque(true);

        return label;
    }
}





