import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class Contacts{          

    JLabel headLabl,imgLabl;
    JTextField searchField;
    JButton addBtn;
    JPanel panel;
    JTable table;
    JScrollPane tableScroll;
    
    Contacts(){

    //Frame
        JFrame f = new JFrame();
        
        panel = new JPanel(new BorderLayout());
        panel.setBounds(20,160,345,285);        

    //Label
        headLabl = new JLabel("CONTACTS");
        headLabl.setBounds(0,30,380,50);
        headLabl.setFont(new Font("Bookman Old Style",Font.BOLD,28));
        headLabl.setForeground(Color.BLUE);
        headLabl.setHorizontalAlignment(JLabel.CENTER);

        imgLabl = new JLabel(new ImageIcon("Images/ContactImg.png"));  
        imgLabl.setBounds(15,80,60,60);


    //Button
        addBtn = new JButton(new ImageIcon("Images/addicon.png"));
        addBtn.setBackground(new Color(238, 238, 238));                //Color Matches with Frame
        addBtn.setContentAreaFilled(false);                           //Used to hide the Selection Area while pressing Btn
        addBtn.setBorder(null);
        addBtn.setBounds(310,80,60,60);
        addBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                InsertContact insert = new InsertContact();
                f.dispose();                                      //Dispose the Current Frame,coz to avoid opening the same this Frame while refreshing the Table
            }
        });

    //TextField
        searchField = new JTextField("Search Here");
        searchField.addFocusListener(new FocusListener() {     //Used to act as PlaceHolder
            public void focusGained(FocusEvent e){
                if(searchField.getText().equals("Search Here")){
                    searchField.setText("");
                }
            }
            public void focusLost(FocusEvent e){
                if(searchField.getText().equals("")) 
                {
                    searchField.setText("Search Here");
                }
            }
        });
        searchField.setBounds(90,100,200,30);
        searchField.addKeyListener(new KeyAdapter() {       
            @Override
            public void keyReleased(KeyEvent e) {

                ArrayList<ContactsBean> arr1=null;
                arr1 = new ArrayList<ContactsBean>();
                String inp = searchField.getText();
                String query = "SELECT * FROM contacts WHERE name like'%"+inp+"%'";
                Connection con = null;        
                try {
                    con = DBConnection.getConnection();     //Connection Method-helps to Secure the Login Credentials
                    //System.out.println("Connected");
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery(query);
                    ContactsBean contactsInfoBean;

                    while (rs.next()) {
                        contactsInfoBean = new ContactsBean(rs.getString("name"), rs.getString(2), rs.getString(3));
                        arr1.add(contactsInfoBean);
                    }

                    DefaultTableModel model = (DefaultTableModel)table.getModel();
                    model.setRowCount(0);
                    Object [] row = new Object[3];
                    for(int i =0; i < arr1.size(); i++){
                        row[0] = arr1.get(i).getName();
                        //row[1] = arr1.get(i).getMobile();  ! Commented these coz I had planned to show only the Names on the JTable
                        //row[2] = arr1.get(i).getEmail();
                        model.addRow(row);
                    }
                } catch (SQLException ex) {
                    System.out.println("Exception Catch while search" + ex);
                }finally{
                    if(con!=null){
                        DBConnection.closeConnection(con);
                        //System.out.println("Connection Closed");
                    }
                }
            }
        });

    //Table
        DefaultTableModel myTableModel = new DefaultTableModel();
        myTableModel.addColumn("Contact List");
        table = new JTable(myTableModel);
        fillTable();                                  //fillltablele - Used to Fill the Table with the Data From DB (also I Used it as Refresh)
        table.setVisible(true);
        table.setSize(new Dimension(200,200));
        table.setRowHeight(40);
        table.setFont(new Font("Bookman Old Style",Font.PLAIN,16));

        JScrollPane tableScroll = new JScrollPane(table);

        table.addMouseListener(new MouseAdapter() {  //since I need only one method (mouse click) to override, so used MouseAdapter instead of MouseListeners

            @Override
            public void mouseClicked(MouseEvent e) {
                int index = table.getSelectedRow();
                //System.out.println(index);
                new ManageContact(index);          //Used to Know the Row index of JTable which helps in retrival of Data
                f.dispose();
            }
        });
        
    //Frame Setup
        ImageIcon ApplicationImge = new ImageIcon("Images/IconImage.png");    //Icon Image
        f.setTitle("Contacts");
        f.setIconImage(ApplicationImge.getImage()); 
        f.setSize(400,500);
        f.setLayout(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

    //Adding to Frame
        f.add(headLabl);
        f.add(imgLabl);
        f.add(searchField);
        f.add(addBtn);
        f.add(panel);
        panel.add(tableScroll,BorderLayout.CENTER);


    } 

//FillTable Method                                    //Fetch the Data that stored on the Arraylist Using Retrive Method
    public void fillTable(){
        ArrayList<ContactsBean> arr1 = retriveData();
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        model.setRowCount(0);
        Object [] row = new Object[3];
        for(int i =0; i < arr1.size(); i++){
            row[0] = arr1.get(i).getName();
            //row[1] = arr1.get(i).getMobile();
            //row[2] = arr1.get(i).getEmail();
            model.addRow(row);
        }
    }

   
//retrive Method:
    public static ArrayList<ContactsBean> retriveData(){        //This Method is Used to Get Data From DB to an ArrayList
        ArrayList<ContactsBean> arr1=null;
        arr1 = new ArrayList<ContactsBean>();

        String query = "SELECT * FROM contacts";
        Connection con=null;        
        try {
            con = DBConnection.getConnection(); 
            //System.out.println("Connected");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            ContactsBean contactsInfoBean;
            while (rs.next()) {
                contactsInfoBean = new ContactsBean(rs.getString("name"), rs.getString(2), rs.getString(3));
                arr1.add(contactsInfoBean);
            }
        } catch (SQLException e) {
            System.out.println("Exception Catch in Retrive Method" + e);
        }finally{
            if(con!=null){
                DBConnection.closeConnection(con);
                //System.out.println("Connection Closed");
            }
        }
        return arr1;    
    }
    

}

