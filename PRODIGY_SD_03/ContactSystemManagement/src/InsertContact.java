import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;


public class InsertContact {                //This Class related to Creation of Contact, Contains the Query to Update the DB 

    Contacts contact = new Contacts();      //Used to Access Methods from Contacts Class
    
    JLabel headLabl,nameLabl,phnoLabl,emailLabl;
    JTextField nameField, phnoField, emailField;
    JButton saveBtn, cancelBtn;
    JOptionPane dialogBoxPane;

    InsertContact(){     

    //Frame
        JFrame f = new JFrame();

    //labels
        headLabl = new JLabel("Create Contact");
        headLabl.setBounds(80,35,200,50);
        headLabl.setFont( new Font("Bookman Old Style",Font.BOLD,18));

        nameLabl = new JLabel("Name");
        nameLabl.setBounds(20,100,60,30);

        phnoLabl = new JLabel("Phone Number");
        phnoLabl.setBounds(20,170,100,30);

        emailLabl = new JLabel("Email ID");
        emailLabl.setBounds(20,240,60,30);
    
    //TextField
        nameField = new JTextField(""); 
        nameField.setBounds(130,100,180,30);
        nameField.setFont( new Font("Bookman Old Style",Font.PLAIN,14));           
        
        phnoField = new JTextField("");
        phnoField.setBounds(130,170,180,30);
        phnoField.setFont( new Font("Bookman Old Style",Font.PLAIN,14));

        emailField = new JTextField("");
        emailField.setBounds(130,240,180,30);
        emailField.setFont( new Font("Bookman Old Style",Font.PLAIN,14));

    //Buttons
        saveBtn = new JButton(new ImageIcon("Images/saveimg.png"));                    
        saveBtn.setBounds(50,320,100,30);
        saveBtn.setBackground(new Color(238, 238, 238));
        saveBtn.setContentAreaFilled(false);
        saveBtn.setBorder(null); 
        saveBtn.setFocusPainted(false);          //Used to get rid of the box around the text in button, but not needed Since i used image over button
        saveBtn.addActionListener(new ActionListener(){
         @Override
        public void actionPerformed(ActionEvent e) {
            if(!nameField.getText().equals("") && !phnoField.getText().equals("")){   
                Connection con=null;   
            try {
                con = DBConnection.getConnection(); 
                //System.out.println("Connected");
                PreparedStatement ps = con.prepareStatement("INSERT INTO contacts VALUES (?,?,?);");
                ps.setString(1,nameField.getText());
                ps.setString(2,phnoField.getText());
                ps.setString(3,emailField.getText());
                    
                int res = ps.executeUpdate();
                if (res>=1) {
                    dialogBoxPane.showMessageDialog(null,"New Contact Added","Contact Created",JOptionPane.INFORMATION_MESSAGE); 
                    f.dispose();
                    contact.fillTable();
                }
                else {
                    dialogBoxPane.showMessageDialog(null,"Restart and Try Again","Failed",JOptionPane.ERROR_MESSAGE);
                }
                } catch (Exception ex) {
                    System.out.println("Exception Catch while Insert New Contact" + ex);
                }finally{
                    if(con!=null){
                        DBConnection.closeConnection(con);
                        //System.out.println("Connection Closed");
                    }
                }
            }
            else{
                dialogBoxPane.showMessageDialog(null,"Check whether all Fields are filled or the Number is already Entered","Error",JOptionPane.ERROR_MESSAGE);
            }   
        }
        });

        cancelBtn = new JButton(new ImageIcon("Images/cancel.png"));         
        cancelBtn.setBounds(200,320,100,30);
        cancelBtn.setBackground(new Color(238, 238, 238)); 
        cancelBtn.setContentAreaFilled(false);
        cancelBtn.setBorder(null); 
        cancelBtn.setFocusPainted(false);
        cancelBtn.addActionListener(new ActionListener(){
          @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
            }      
        });

        
    
        //Adding to Frame
        f.add(headLabl);
        f.add(nameLabl);
        f.add(phnoLabl);
        f.add(emailLabl);
        f.add(nameField);
        f.add(phnoField);
        f.add(emailField);
        f.add(saveBtn);
        f.add(cancelBtn);

    //Frame Setup
        f.setSize(350,450);
        f.setTitle("New Contact");
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Used to close without terminating the application
        f.setLayout(null);
        f.setVisible(true); 

    }

    
}
