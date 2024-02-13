//Solver Logic
public class SudokuSolver {

    static int N = 9;    //represents the number used in Loops for a 9x9 grid sudoku
    
//Backtracking Method 
    public static Boolean solveSudoku(int sudokuArr[][]){
        for(int row=0; row<N;row++){
            for(int col=0; col<N; col++){
                if(sudokuArr[row][col]==0){
                    for(int num=1; num<=N;num++){
                        if(isNumValid(sudokuArr,row,col,num)){      //Fn Call

                            sudokuArr[row][col]=num;

                            if(solveSudoku(sudokuArr)){           //Recursion
                                return true;
                            }

                            else{
                                sudokuArr[row][col]=0;
                            }

                        }
                    }return false;
                }
            }
        }return true;
    }  


    public static Boolean isNumValid(int[][] sudokuArr, int rowNo, int colNo,int num){

        if(!isNuminRow(sudokuArr, rowNo, num) && !isNuminCol(sudokuArr, colNo, num) && !isNuminSubGrid(sudokuArr,rowNo,colNo,num)){
            return true;
        }
        return false;
    }


    public static Boolean isNuminRow(int [][] sudokuArr,int rowNo, int num){
        for(int i=0;i<N;i++){
            if(sudokuArr[rowNo][i]==num){
                return true;
            }
        }
        return false;
    }
    
    public static Boolean isNuminCol(int [][] sudokuArr,int colNo, int num){
        for(int i=0;i<N;i++){
            if(sudokuArr[i][colNo]==num){
                return true;
            }
        }
        return false;
    }

    public static Boolean isNuminSubGrid(int [][] sudokuArr,int rowNo,int colNo,int num){

        int sqr = (int)Math.sqrt(sudokuArr.length);
        
        int row = rowNo-rowNo%sqr;     //Or simply Use int row = rowNo-rowNo%3 - for a 9x9grid
        int col = colNo-colNo%sqr;
        //System.out.println(row+" "+col);

        for(int i=row;i<row+3;i++){
            for(int j=col; j<col+3;j++){
                if(sudokuArr[i][j]==num){   
                    return true;
                }
            }
        }
        return false;
    }

    
}
    




    

