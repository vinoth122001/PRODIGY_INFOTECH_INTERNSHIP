import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*; 

public class SudokuFrame extends SudokuSolver {
     
    static int[][] sudokuArr=new int[9][9];
    static int[][] gridArr=new int[9][9];
    
    JButton solveBtn, resetBtn; 
    JLabel bgLabl,imgLabl, headLabl, infoLabl; 
    static JTextField [][] gridField;
    JTextArea noteArea;

    SudokuFrame(){  
        
    //Images 
        ImageIcon IconImge = new ImageIcon("Images/Icon.jpg");
        ImageIcon imge = new ImageIcon("Images/sudokuImage.png");
        ImageIcon solveImg = new ImageIcon("Images/Solve.png");
        ImageIcon resetImg = new ImageIcon("Images/Reset.png");

    //Labels
        bgLabl = new JLabel(new ImageIcon("Images/bgImg.jpg")); 
        bgLabl.setBounds(0,0,700,700);

        imgLabl = new JLabel();
        imgLabl.setIcon(imge); 
        imgLabl.setBounds(10,20,460,130);

        headLabl = new JLabel("Solver !");      
        headLabl.setBounds(80,105,200,50);
        headLabl.setForeground(new Color(12,63,155));        // Used to set Color for the foreground "Text"
        headLabl.setFont( new Font("Imprint MT Shadow",Font.BOLD,50));
        
        infoLabl = new JLabel("Enter the Sudoku Puzzle to Solve");
        infoLabl.setBounds(50,160,450,30);
        infoLabl.setHorizontalAlignment(JLabel.CENTER);
        infoLabl.setForeground(Color.BLACK);
        infoLabl.setFont( new Font("MV Boli",Font.PLAIN,15));
    
    //Buttons
        solveBtn = new JButton(solveImg);         
        solveBtn.setBackground(new Color(238,238,238));     //Color Matches with Frame GUI
        solveBtn.setBorder(null);
        solveBtn.setBounds(540,510,135,40);
        solveBtn.setContentAreaFilled(false);             // Used to not show the Area While the button is Clicked
        solveBtn.setFocusable(false);                    // To avoid foucs
    
        ActionListener solve = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                infoLabl.setText("Solving...Sudoku puzzle");
                if(isValidpuzzle()){   
                    if(solveSudoku(sudokuArr)){
                        fillSudoku();
                        infoLabl.setText("Puzzle Solved !");
                        infoLabl.setForeground(Color.GREEN);  
                    }
                    else{
                        infoLabl.setText("No Solution ! Sorry ");
                        infoLabl.setForeground(Color.RED); 
                    }
                }else{
                    infoLabl.setText("InValid Puzzle ! Check the entered puzzle ");
                    infoLabl.setForeground(Color.RED); 
                }             
            }

        };
        solveBtn.addActionListener(solve);

        resetBtn = new JButton(resetImg);
        resetBtn.setBackground(new Color(238,238,238));
        resetBtn.setBorder(null);
        resetBtn.setBounds(540,570,135,40);
        resetBtn.setFocusable(true);

        ActionListener reset = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                infoLabl.setText("Enter the Sudoku Puzzle to Solve");
                infoLabl.setForeground(Color.BLACK);
                for(int i=0;i<N;i++){
                    for(int j=0; j<N; j++){
                        sudokuArr[i][j]=0;
                        gridArr[i][j]=0;
                        gridField[i][j].setEditable(true);
                        gridField[i][j].setForeground(Color.BLUE);
                        gridField[i][j].setText("");
                    }
                }
            }
        };
        resetBtn.addActionListener(reset);



    //Panel
        JPanel gridPanel = new JPanel();
        gridPanel.setBounds(60,200,450,450);
        gridPanel.setLayout(new GridLayout(9,9));      //represents 9x9 grid

    //TextField - SudokuGrid
        gridField = new JTextField[9][9];       
        for(int i=0;i<N;i++){
            for(int j=0; j<N; j++){

                gridField[i][j] = new JTextField("");
                gridField[i][j].setFont(new Font("Arial",Font.PLAIN,18));
                gridField[i][j].setHorizontalAlignment(JTextField.CENTER);
                gridField[i][j].setForeground(Color.BLUE);

                gridPanel.add(gridField[i][j]);

                if((i>=3 && i<=5) && (j<=2 || j>=6) ){
                    gridField[i][j].setBackground(new Color(232,232,232));                    
                }
                else if((j>=3 && j<=5) && (i<=2 ||i>=6)) {
                    gridField[i][j].setBackground(new Color(232,232,232));
                }
                else{
                    gridField[i][j].setBackground(Color.WHITE);
                }
            }
        }

    //TextArea
        noteArea = new JTextArea("Sudoku\n\n        Sudoku is a  logic    based   number placement puzzle, filling a 9x9 grid without repeating any in a row,column and   3x3 subgrid\n\n  Rules are based upon the sudoku, So use Numbers from  1-9\n\n  Enter a Valid Sudoku Puzzle to Solve\n\n  Use Reset to clear the grid");
        noteArea.setBounds(530,117,160,650);
      
        noteArea.setBackground(new Color(232,232,232)); 
        noteArea.setForeground(new Color(1,173,157));  
        noteArea.setLineWrap(true);                    //Used to wrap the lines
        noteArea.setWrapStyleWord(true);              //Wrap the lines at word boundaries (whitespace)
        noteArea.setFocusable(false);
        noteArea.setEditable(false);                    //Since it has to be used only as General Note
        noteArea.setFont(new Font("TimesNewRoman",Font.PLAIN,14));
         
    //Frame
        JFrame f = new JFrame();
    
    //Adding to Frame
        f.add(bgLabl);
        bgLabl.add(headLabl);                          //adding components over the background
        bgLabl.add(imgLabl);
        bgLabl.add(infoLabl);
        bgLabl.add(solveBtn);
        bgLabl.add(resetBtn);
        bgLabl.add(gridPanel);
        bgLabl.add(noteArea);
    
    //Frame Setup
        f.setTitle("Sudoku Solver");
        f.setIconImage(IconImge.getImage());
        f.setSize(700,705);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Used to exit form the prgm while pressing Close Window
        f.setLayout(null);
        f.setVisible(true);    
    }

    public static boolean isValidpuzzle(){      //Check whether the puzzle is valid or not !
        int isdoubleDigit=0;
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                if(gridField[i][j].getText().length() > 1){
                    isdoubleDigit=1;
                    System.out.println("Double Digits not allowed");
                    break;
                }
                if(gridField[i][j].getText().equals("")){
                    gridArr[i][j]=0;        //Used to get copy of the TextField puzzle which used when filling the grids
                    sudokuArr[i][j]=0;
                }
                else{
                    gridArr[i][j]=Integer.parseInt(gridField[i][j].getText());
                    sudokuArr[i][j]=Integer.parseInt(gridField[i][j].getText());
                }
            }
            if(isdoubleDigit==1)
                break;
        }
        if(isdoubleDigit==1){
            return false;
        }
        else
            return true;
    }


    public static void fillSudoku(){                            //Used to Fill the Grid with Solved puzzle
        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++ ){
                if(gridArr[i][j]==sudokuArr[i][j]){     //Compare with the reference grid to know the grid location to set values

                }
                else{
                    gridField[i][j].setForeground(Color.RED);
                    gridField[i][j].setText(""+sudokuArr[i][j]);
                }
                gridField[i][j].setEditable(false);   //To make sure the solved puzzle not to be changed
            }
        }
    }


}
