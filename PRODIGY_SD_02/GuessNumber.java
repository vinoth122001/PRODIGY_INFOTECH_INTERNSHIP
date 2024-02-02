
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Timer;  
import java.util.TimerTask;
import javax.swing.*; 

public class GuessNumber extends JFrame  
{
    int genVal,count=0;
    JButton startBtn, resetBtn, showBtn, checkBtn;
    JLabel bgImgLabl,imgLabl, headLabl, infoLabl, dispLabl;
    JTextField inpField,attemptField;

    GuessNumber(){  //Constructor
        
    //Images 
        ImageIcon imge = new ImageIcon("GuessNumImage.png");
        bgImgLabl = new JLabel(new ImageIcon("ImgBg.png")); 
        bgImgLabl.setBounds(0,0,600,500);

    //Labels
        headLabl = new JLabel(" Guessing Game !");
        headLabl.setBounds(10,30,450,50);
        headLabl.setForeground(Color.white);                        // Used to set Color for the foreground "Text"
        headLabl.setFont( new Font("MV Boli",Font.PLAIN,40));
        
        imgLabl = new JLabel();
        imgLabl.setIcon(imge);
        imgLabl.setBounds(50,100,200,100);

        infoLabl = new JLabel("Press START, Before Enter the Number to Guess");
        infoLabl.setBounds(50,220,450,30);
        infoLabl.setForeground(Color.black);
        infoLabl.setFont( new Font("MV Boli",Font.PLAIN,15));
        
        dispLabl = new JLabel("RESULT Will Display Here");    //Output Display Label
        dispLabl.setBounds(50,350,350,30);
        dispLabl.setForeground(Color.black);
        dispLabl.setFont( new Font("MV Boli",Font.PLAIN,15));


    //Buttons
        ImageIcon btnimg = new ImageIcon("RedButton.png");
        startBtn = new JButton("Start",btnimg);
        startBtn.setFont( new Font("MV Boli",Font.BOLD,15));
        startBtn.setBounds(280,100,100,100);
        startBtn.setBackground(new Color(238, 238, 238));   //Color matches with the GUI
        startBtn.setBorder(null);                         // To remove the border 
        startBtn.setContentAreaFilled(false);                 // Used to not show the Area While the button   
        startBtn.setVerticalTextPosition(JButton.CENTER);
        startBtn.setHorizontalTextPosition(JButton.CENTER);
        startBtn.setFocusable(false);               // To get rid of the foucs 
    
        ActionListener generate = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //Generation of Random Num b/w 1 to 1000
                genVal = (int) (Math.random() * 1000);
                infoLabl.setText("Number Generated ! Start Guessing ");                
            }

        };
        startBtn.addActionListener(generate);

        resetBtn = new JButton("Reset");
        resetBtn.setFont( new Font("MV Boli",Font.PLAIN,15));
        resetBtn.setBounds(400,110,100,30);
        resetBtn.setFocusable(true);
        resetBtn.setFocusPainted(false);            //used to only get rid of TextBox over the Text"reset"
        ActionListener reset = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                genVal = 0;
                infoLabl.setText("Press START, Before Enter the Number to Guess");
                inpField.setText("Enter Number Here (b/w 1 to 1000)");
                dispLabl.setText("RESULT Will Display Here");
                attemptField.setText(" NO OF ATTEMPTS ");
                count = 0;    
            }
        };
        resetBtn.addActionListener(reset);

        showBtn = new JButton("Reveal");
        showBtn.setFont( new Font("MV Boli",Font.PLAIN,15));
        showBtn.setBounds(400,160,100,30);
        showBtn.setFocusable(false);
        ActionListener show = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                dispLabl.setText("Number REVEALED ! The Number is : " + genVal);
            }
        };
        showBtn.addActionListener(show);

        checkBtn = new JButton("Check");
        checkBtn.setFont( new Font("MV Boli",Font.PLAIN,15));
        checkBtn.setBounds(400,280,100,30);
        checkBtn.setFocusable(false);        
        ActionListener check = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int inp = Integer.parseInt(inpField.getText());
                dispLabl.setText("Checking...");
                infoLabl.setText("Guess the Number");
                guessValidate(genVal,inp);   
            }
        };
        checkBtn.addActionListener(check);

    //TextField
        inpField = new JTextField("Enter Number Here (b/w 1 to 1000)");
        inpField.setFont( new Font("MV Boli",Font.PLAIN,10));
        inpField.setBounds(80,280,250,30);
        inpField.addFocusListener(new FocusListener() {           //Used to act as PlaceHolder
            public void focusGained(FocusEvent e){
                if(inpField.getText().equals("Enter Number Here (b/w 1 to 1000)")){
                    inpField.setText("");
                }
            }
            public void focusLost(FocusEvent e){
                if(inpField.getText().equals("")) 
                {
                    inpField.setText("Enter Number Here (b/w 1 to 1000)");
                }
            }
        });

        attemptField = new JTextField(" NO OF ATTEMPTS ");
        attemptField.setFont( new Font("MV Boli",Font.BOLD,11));
        attemptField.setBounds(390,350,120,30);
        attemptField.setEditable(false);                     // Used this textfield only to display

    //Adding to Frame
        add(bgImgLabl);
        bgImgLabl.add(headLabl);                            //adding components over the background
        bgImgLabl.add(imgLabl);
        bgImgLabl.add(infoLabl);
        bgImgLabl.add(dispLabl);
        bgImgLabl.add(startBtn);
        bgImgLabl.add(resetBtn);
        bgImgLabl.add(showBtn);
        bgImgLabl.add(checkBtn);
        bgImgLabl.add(inpField);
        bgImgLabl.add(attemptField);
    
    //Frame Setup
        setTitle("Guessing the Number");
        setSize(600,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Used to exit form the prgm while pressing Close Window
        setLayout(null);
        setVisible(true);    
    }

    
//Method to validate the guessed number is correct or not 
    void guessValidate (int randNum, int num)
    {        
        if(num>randNum){ 
                dispAfterDelay("Too High ! Try Again with some low values");
        // Used delay to show "Checking..." text before this Which makes the Difference before every attempts    
        }
        else if(num<randNum){
            dispAfterDelay("Too Low ! Try Again with some high valus");
        }
        else if(num == randNum){ 
        //In this, Avoids Delay coz, to appear both these Label at Same Time.
            dispLabl.setText("Hooray! Ur Guess was right." );  
            infoLabl.setText("Press RESET, Before Starting Another Game !");
            attemptField.setText("No.of Attempts: "+ (count+1)); 
        }
        count++;
    }

    Timer timer = new Timer();              //Used to make delay before setText() using Timer
    void dispAfterDelay(String text)
    {
            TimerTask task = new TimerTask() {
                public void run(){
                    dispLabl.setText(text);
                }
            };
        timer.schedule(task,500);
    }
    
    public static void main(String[] args){
         
        new GuessNumber(); 

    }
    
}
