import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class StudentForm extends JFrame {


    JTextField fullName = new JTextField(20);
    JTextField age = new JTextField(5);
    JTextField email = new JTextField(20);
    JTextField school = new JTextField(20);
    JTextField degree = new JTextField(20);
    JTextField year = new JTextField(5);

    JPanel cards;

    public StudentForm() {
        setTitle("Personal Information"); 

        cards = new JPanel(new CardLayout());
        cards.add(personalPanel(), "personal");
        cards.add(educationPanel(), "education");

        add(cards);

        setSize(450,250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    JPanel personalPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5,5,5,5);
        g.fill = GridBagConstraints.HORIZONTAL;

        g.gridx = 0; 
        g.gridy = 0;
        p.add(new JLabel("Full Name:"), g);
        g.gridx = 1;
        p.add(fullName, g);
        g.gridx = 0; 
        g.gridy = 1; 
        p.add(new JLabel("Age:"), g);
        g.gridx = 1; 
        p.add(age, g);
        g.gridx = 0; 
        g.gridy = 2; 
        p.add(new JLabel("Email:"), g);
        g.gridx = 1; 
        p.add(email, g);
        JButton next = new JButton("Next");
        next.addActionListener(e -> showCard("education", "Educational Information"));

        g.gridx = 1; 
        g.gridy = 3;
        g.anchor = GridBagConstraints.LINE_START;
        p.add(next, g);

        return p;
    }

    JPanel educationPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5,5,5,5);
        g.fill = GridBagConstraints.HORIZONTAL;

        g.gridx = 0; 
        g.gridy = 0;  
        p.add(new JLabel("School / University:"), g);
        g.gridx = 1;  
        p.add(school, g);
        g.gridx = 0; 
        g.gridy = 1;  
        p.add(new JLabel("Degree / Course:"), g);
        g.gridx = 1; 
        p.add(degree, g);
        g.gridx = 0;
        g.gridy = 2; 
        p.add(new JLabel("Year Graduated:"), g);
        g.gridx = 1; 
        p.add(year, g);
        JButton back = new JButton("Back");
        back.addActionListener(e -> showCard("personal", "Personal Information"));

        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> saveData());

        g.gridx = 0; 
        g.gridy = 3; p.add(back, g);
        g.gridx = 1; 
        p.add(submit, g);

        return p;
    }
    void showCard(String name, String title){
        CardLayout cl = (CardLayout) cards.getLayout();
        cl.show(cards, name);
        setTitle(title); 
    }

    void saveData() {
        try {
            Connection con = DriverManager.getConnection(
    "jdbc:mysql://localhost:4306/3rdyear", "root", ""
);

            String sql1 = "INSERT INTO personalinfo(fullname, age, email) VALUES(?,?,?)";
            PreparedStatement p1 = con.prepareStatement(sql1);
            p1.setString(1, fullName.getText());
            p1.setString(2, age.getText());
            p1.setString(3, email.getText());
            p1.executeUpdate();

 
            String sql2 = "INSERT INTO education(school, degree, year) VALUES(?,?,?)";
            PreparedStatement p2 = con.prepareStatement(sql2);
            p2.setString(1, school.getText());
            p2.setString(2, degree.getText());
            p2.setString(3, year.getText());
            p2.executeUpdate();

            JOptionPane.showMessageDialog(null, "Data Saved Successfully");
            con.close();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public static void main(String[] args){
        new StudentForm();
    }
}
