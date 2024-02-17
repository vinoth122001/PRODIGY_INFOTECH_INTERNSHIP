package com.intern;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;

public class ScraperFrame {

    String relativeUrl="";
    int limit = 1;

    JLabel bgLabl,headLabl,dispLabl, infoLabl, limitLabl;
    JTextField baseUrlField, relativeUrlField, limitField;
    JButton scrapeBtn, resetBtn;
    JTextArea description;
    

    ScraperFrame(){

    //Labels
        bgLabl = new JLabel(new ImageIcon("Images/Background.jpg")); 
        bgLabl.setBounds(0,0,500,400);

        headLabl = new JLabel("Web Scraper");
        headLabl.setBounds(0,5,500,60);
        headLabl.setForeground(Color.gray);
        headLabl.setFont(new Font("Baskerville Old Face",Font.PLAIN,50));   
        headLabl.setHorizontalAlignment(JLabel.CENTER);

        dispLabl = new JLabel("Website to Scrape");
        dispLabl.setBounds(20,80,200,30);
        dispLabl.setFont( new Font("Comic Sans MS",Font.PLAIN,15));       

        infoLabl = new JLabel("Scrape Data by Categories");
        infoLabl.setBounds(20,130,200,30);
        infoLabl.setFont( new Font("Comic Sans MS",Font.PLAIN,15));

        limitLabl = new JLabel("ScrapePages Limit ");
        limitLabl.setBounds(20,180,200,30);
        limitLabl.setFont( new Font("Comic Sans MS",Font.PLAIN,15));

    //TextField
        baseUrlField = new JTextField("https://www.flipkart.com");
        baseUrlField.setHorizontalAlignment(JTextField.CENTER);
        baseUrlField.setForeground(Color.blue);
        baseUrlField.setBounds(250,80,200,30);
        baseUrlField.setFont( new Font("Comic Sans MS",Font.PLAIN,14));
        baseUrlField.setEditable(false);

        relativeUrlField = new JTextField("Enter type of Product (Start with '/')");
        relativeUrlField.setBounds(250,130,200,30);
        relativeUrlField.setFont( new Font("Comic Sans MS",Font.PLAIN,10));
        relativeUrlField.addFocusListener(new FocusListener() {                         //Used to act as PlaceHolder
            public void focusGained(FocusEvent e){
                if(relativeUrlField.getText().equals("Enter type of Product (Start with '/')")){
                    relativeUrlField.setText("");
                }
            }
            public void focusLost(FocusEvent e){
                if(relativeUrlField.getText().equals("")) 
                {
                    relativeUrlField.setText("Enter type of Product (Start with '/')");
                }
            }
        });

        limitField = new JTextField("Enter the No. of pages to Scrape");
        limitField.setBounds(250,180,200,30);
        limitField.setFont( new Font("Comic Sans MS",Font.BOLD,10));
        limitField.addFocusListener(new FocusListener() {           
            public void focusGained(FocusEvent e){
                if(limitField.getText().equals("Enter the No. of pages to Scrape")){
                    limitField.setText("");
                }
            }
            public void focusLost(FocusEvent e){
                if(limitField.getText().equals("")) 
                {
                    limitField.setText("Enter the No. of pages to Scrape");
                }
            }
        });


    //Buttons
        ImageIcon scrapeImg = new ImageIcon("Images/ScrapeBtn.png");
        scrapeBtn = new JButton("Scrape",scrapeImg);
        scrapeBtn.setBounds(120,240,100,25); 
        scrapeBtn.setBackground(new Color(238, 238, 238));  
        scrapeBtn.setForeground(Color.WHITE);
        scrapeBtn.setBorder(null);                                  // To remove the border 
        scrapeBtn.setContentAreaFilled(false);                          // Used to not show the Area While the button   
        scrapeBtn.setVerticalTextPosition(JButton.CENTER);
        scrapeBtn.setHorizontalTextPosition(JButton.CENTER);
        scrapeBtn.setFocusable(false);
        ActionListener scrape = new ActionListener(){
            public void actionPerformed(ActionEvent e){

                relativeUrl=relativeUrlField.getText();               //getString from relativeUrlField
                limit = Integer.parseInt(limitField.getText());      //getLimit

                //System.out.println(relativeUrl + limit);
                WebScraper.scrapePage(relativeUrl,limit);          //static method used to scrape                
            }
        };
        scrapeBtn.addActionListener(scrape);

        ImageIcon resetImg = new ImageIcon("Images/resetBtn.png");
        resetBtn = new JButton("Reset",resetImg);
        resetBtn.setBounds(280,240,100,25);
        resetBtn.setForeground(Color.WHITE);
        resetBtn.setBackground(new Color(238, 238, 238));   
        resetBtn.setBorder(null);                         
        resetBtn.setContentAreaFilled(false);                 
        resetBtn.setVerticalTextPosition(JButton.CENTER);
        resetBtn.setHorizontalTextPosition(JButton.CENTER);
        resetBtn.setFocusable(false);

        ActionListener reset = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                relativeUrlField.setText("Enter type of Product (Start with '/')");    
                limitField.setText("Enter the No. of pages to Scrape");
                limit=0;
            }
        };
        resetBtn.addActionListener(reset);


    //TextArea

        description = new JTextArea("Note: This Application is designed to extract publicly available data"+
        " from Flipkart's platform solely for educational exploration and demonstartion of web scraping techniques, not intendend for commercial or competitive purposes.");
        
        description.setBounds(40,300,400,60);
        description.setFont(new Font("Arial",Font.PLAIN,10));
        description.setForeground(Color.RED);
        description.setBackground(new Color(238,238,238));
        description.setLineWrap(true);                          //Used to Wrap the lines
        description.setWrapStyleWord(true);                    //Wrap the lines at Word boundaries(space)
        description.setEditable(false);

    //Frame
        JFrame f = new JFrame();   


    //Adding to Frame
        f.add(bgLabl);
        bgLabl.add(headLabl);
        bgLabl.add(dispLabl);
        bgLabl.add(baseUrlField);
        bgLabl.add(infoLabl);
        bgLabl.add(relativeUrlField);
        bgLabl.add(limitLabl);
        bgLabl.add(limitField);
        bgLabl.add(scrapeBtn);
        bgLabl.add(resetBtn);
        bgLabl.add(description);
    
    //Frame Setup
        ImageIcon IconImg = new ImageIcon("Images/Icon.png");
        f.setIconImage(IconImg.getImage());
        f.setTitle("Web Scraper");
        f.setSize(500,400);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Used to exit form the prgm while pressing Close Window
        f.setLayout(null);
        f.setVisible(true); 

    }
    
    public static void main(String[] args){

        new ScraperFrame();
    }
    
}