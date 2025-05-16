import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class LibraryGUI extends JFrame {
    private LibraryDAO dao = new LibraryDAO();

    private JTextField titleField = new JTextField(20);
    private JTextField authorField = new JTextField(20);
    private JTextField yearField = new JTextField(5);
    private JTextArea displayArea = new JTextArea(15, 50);

    public LibraryGUI() {
        setTitle("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Title:"));
        inputPanel.add(titleField);
        inputPanel.add(new JLabel("Author:"));
        inputPanel.add(authorField);
        inputPanel.add(new JLabel("Year:"));
        inputPanel.add(yearField);

        JButton addButton = new JButton("Add Book");
        addButton.addActionListener(e -> addBook());
        inputPanel.add(addButton);

        JButton viewButton = new JButton("View All Books");
        viewButton.addActionListener(e -> viewBooks());
        inputPanel.add(viewButton);

        add(inputPanel, BorderLayout.NORTH);

        // Display Area
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        setSize(600, 400);
        setVisible(true);
    }

    private void addBook() {
        try {
            String title = titleField.getText();
            String author = authorField.getText();
            int year = Integer.parseInt(yearField.getText());

            dao.addBook(new Book(title, author, year));
            displayArea.setText("Book added successfully!\n");
            clearFields();
        } catch (Exception e) {
            displayArea.setText("Error adding book: " + e.getMessage() + "\n");
        }
    }

    private void viewBooks() {
        try {
            List<Book> books = dao.getAllBooks();
            StringBuilder sb = new StringBuilder();
            for (Book b : books) {
                sb.append(b).append("\n");
            }
            displayArea.setText(sb.toString());
        } catch (SQLException e) {
            displayArea.setText("Error retrieving books: " + e.getMessage());
        }
    }

    private void clearFields() {
        titleField.setText("");
        authorField.setText("");
        yearField.setText("");
    }

    public static void main(String[] args) {
        new LibraryGUI();
    }
}