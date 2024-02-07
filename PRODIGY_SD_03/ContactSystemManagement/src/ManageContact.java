import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;


public class ManageContact{

    Contacts contact = new Contacts();      //Object Creation of Contacts Class-Helps to FillTable(refresh) Method
    
    String Name="";
    String Email="";
    String Phone ="";                   //Used to Store the Vales from the Labels and Sends these to Edit Block while Calling Constructor of Edit Class

    JLabel nameLabl,phnoLabl,emailLabl,callLablImg,chatLablImg,emailLablImg;
    JButton editBtn,delBtn;

    ManageContact(int num){

        int index = num;        //Used to Store the Index of the Table Row
        
    //Frame
        JFrame f = new JFrame();
        JOptionPane dialogPane = new JOptionPane();

    //labels
        nameLabl = new JLabel("Name");  
        nameLabl.setFont( new Font("Bookman Old Stye",Font.BOLD,24));
        nameLabl.setBounds(0,40,420,50);
        nameLabl.setHorizontalAlignment(JLabel.CENTER);
        

        phnoLabl = new JLabel("Phone"); 
        phnoLabl.setBounds(0,100,420,20);
        phnoLabl.setFont( new Font("Bookman Old Style",Font.PLAIN,16));
        phnoLabl.setHorizontalAlignment(JLabel.CENTER);
        
        emailLabl = new JLabel("Email");

        callLablImg = new JLabel(new ImageIcon("Images/phonecall.png")); 
        callLablImg.setBounds(90,150,50,50);

        chatLablImg = new JLabel(new ImageIcon("Images/Message.png")); 
        chatLablImg.setBounds(195,150,50,50);

        emailLablImg= new JLabel(new ImageIcon("Images/email.png")); 
        emailLablImg.setBounds(300,150,50,50);
        emailLablImg.addMouseListener(new MouseAdapter(){   //Used as a reference to opening of Mail Application 
            @Override
            public void mouseClicked(MouseEvent e){
                dialogPane.showMessageDialog(null,emailLabl,"MailApplication",JOptionPane.INFORMATION_MESSAGE);
            }
        });

    //Buttons
        
        editBtn = new JButton(new ImageIcon("Images/editimg.png"));
        editBtn.setBackground(new Color(238, 238, 238));   
        editBtn.setBorder(null); 
        editBtn.setFocusPainted(false);
        editBtn.setContentAreaFilled(false);
        editBtn.setBounds(80,250,100,30);
        editBtn.addActionListener(new ActionListener()
        {            @Override
            public void actionPerformed(ActionEvent e) {
                EditContact update = new EditContact(Name,Phone,Email); //String Values contains Data fetched from respected Labels
                f.dispose();                                            //Close this Frame ManageContact
            }

        });

        delBtn = new JButton(new ImageIcon("Images/deleteimg.png"));
        delBtn.setBackground(new Color(238, 238, 238));   
        delBtn.setBorder(null);
        delBtn.setContentAreaFilled(false);
        delBtn.setFocusPainted(false);
        delBtn.setBounds(260,250,100,30);
        delBtn.addActionListener(new ActionListener()
        { 
            @Override
            public void actionPerformed(ActionEvent e) {
                int op = dialogPane.showConfirmDialog(null, "Are You sure want to Delete this Contact ?","Delete",JOptionPane.YES_NO_CANCEL_OPTION);
                if(op==0){ 
                    Connection con = null;
                    try {       
                            con = DBConnection.getConnection();
                            //System.out.println("Connected");

                            PreparedStatement ps = con.prepareStatement("DELETE FROM contacts WHERE mobile = ?");
                            ps.setString(1,phnoLabl.getText());
                            int res = ps.executeUpdate();
                            if (res>=1) {
                                dialogPane.showMessageDialog(null, "Contact Deleted Successufully","Deleted",JOptionPane.INFORMATION_MESSAGE);
                                f.dispose();            //Close the frame
                                contact.fillTable();    //FillTable() method from Contact cls - used it as a refresh of Data
                                //System.out.println("Data deleted");
                            }
                            else {
                                dialogPane.showMessageDialog(null, "Restart and Try Again","Failed",JOptionPane.ERROR_MESSAGE);
                                System.out.println("Failed");
                            }
                
                        } catch (Exception ex) {
                                System.out.println("Exception Catch While Deletion :" + ex);
                        }finally{
                            if(con!=null){
                                DBConnection.closeConnection(con);
                                //System.out.println("Connection Closed");
                            }
                        }
                }else{
                    System.out.println("Canceled the Process of Deletion"); 
                }   
            }
        });        

    //Adding to Frame
        f.add(nameLabl);
        f.add(phnoLabl);
        f.add(emailLablImg);
        f.add(callLablImg);
        f.add(chatLablImg);
        f.add(editBtn);
        f.add(delBtn);

    //Method Call
        showdetails(index);  //Used to Dispay the Selected Row Data based on INDEX of the Row
        getdetails();        //Used to Collect those Data in a String to fill it in the Edit TextField.

    //Frame Setup
        f.setSize(450,400);
        f.setTitle("Manage Contact");
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Used to close without terminating the application while Pressing close
        f.setLayout(null);
        f.setVisible(true);  

    }   

    public void showdetails(int index){
        nameLabl.setText(Contacts.retriveData().get(index).getName());
        phnoLabl.setText(Contacts.retriveData().get(index).getMobile());
        emailLabl.setText(Contacts.retriveData().get(index).getEmail());
        
    }
    public void getdetails(){
         Name = nameLabl.getText();
         Phone= phnoLabl.getText();
         Email= emailLabl.getText();
    }


//!EditContact Class
class EditContact {

    JLabel headlabl,nameLabl,phnoLabl,emailLabl;
    JTextField nameField, phnoField, emailField;
    JButton saveBtn, cancelBtn;
    JOptionPane dialogBoxPane;

    String Name,Mobile,Email;   

    EditContact(String Name, String Mobile,String Email){

        this.Name = Name;
        this.Mobile = Mobile;
        this.Email = Email;

    //Frame
        JFrame f = new JFrame();
        dialogBoxPane = new JOptionPane();

    //labels
        headlabl = new JLabel("Edit Contact");
        headlabl.setFont(new Font("Bookman Old Style",Font.BOLD,20));
        headlabl.setBounds(80,35,200,50);

        nameLabl = new JLabel("Name");
        nameLabl.setBounds(20,100,60,30);

        phnoLabl = new JLabel("Phone Number");
        phnoLabl.setBounds(20,170,100,30);

        emailLabl = new JLabel("Email ID");
        emailLabl.setBounds(20,240,60,30);
    
    //TextField
        nameField = new JTextField(Name);
        nameField.setBounds(130,100,180,30);
        nameField.setFont(new Font("Bookman Old Style",Font.PLAIN,14)); 
        
        phnoField = new JTextField(Mobile);
        phnoField.setEditable(false);                       //Since Number is the Primary Key in DB, so itsn't editable.
        phnoField.setBounds(130,170,180,30);
        phnoField.setFont(new Font("Bookman Old Style",Font.PLAIN,14));
       
        emailField = new JTextField(Email);
        emailField.setBounds(130,240,180,30);
        emailField.setFont(new Font("Bookman Old Style",Font.PLAIN,14));

    //Buttons
        saveBtn = new JButton(new ImageIcon("Images/saveimg.png"));
        saveBtn.setBackground(new Color(238, 238, 238));   
        saveBtn.setBorder(null); 
        saveBtn.setContentAreaFilled(false);    
        saveBtn.setBounds(50,320,100,30);
        saveBtn.addActionListener(new ActionListener() 
        {
        @Override
            public void actionPerformed(ActionEvent e) {
                if(!nameField.getText().equals("") && !phnoField.getText().equals("")){ 
                    Connection con=null;
                    try {
                        con = DBConnection.getConnection();
                        //System.out.println("Connected");
                        PreparedStatement ps = con.prepareStatement("UPDATE contacts SET name=?, mailid =? WHERE mobile=? ");
                        ps.setString(1,nameField.getText());    //parameter Index represent the QuestionMark Place Numbers Order
                        ps.setString(2,emailField.getText());
                        ps.setString(3, phnoField.getText());
                            
                        int res = ps.executeUpdate();
                        if (res>=1) {
                            dialogBoxPane.showMessageDialog(null,"Data Changed Successfully","Updated",JOptionPane.INFORMATION_MESSAGE);
                            contact.fillTable();   //Used to Refresh 
                            f.dispose();                           
                        }
                        else {
                            dialogBoxPane.showMessageDialog(null,"check if Mobile Number wasn't changed or else Restart and Try Again ","Failed to Update",JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (Exception ex) {
                        System.out.println("Exception catch while Edit " + ex);
                    }finally{
                        if(con!=null){
                            DBConnection.closeConnection(con);
                            //System.out.println("Connection Closed");
                        }
                    }                        
                }
                else{
                    dialogBoxPane.showMessageDialog(null,"Check whether the fields are filled or not? ","Failed to Update",JOptionPane.ERROR_MESSAGE);
                }   
            }                        
        });

        cancelBtn = new JButton(new ImageIcon("Images/cancel.png")); 
        cancelBtn.setBounds(200,320,100,30);
        cancelBtn.setBackground(new Color(238, 238, 238));  
        cancelBtn.setBorder(null);
        cancelBtn.setContentAreaFilled(false);
        cancelBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();               
            }            
        });

    //Adding to Frame
        f.add(headlabl);
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
        f.setTitle("Edit Contact");
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Used to close without terminating the application
        f.setLayout(null);
        f.setVisible(true);  

    }
}
}