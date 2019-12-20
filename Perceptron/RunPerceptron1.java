
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class RunPerceptron1{
    //Mention the Training File here.
    public static String trainFile="data/table2";
    
    //Mention the Test File here.
    public static String testFile="data/table2";
    
    float rate=(float) 1;
    
    public static boolean crossValidation=false;
    
    public static float accuracy;

    int trainRow=0, trainCol=0;
    int testRow=0, testCol=0;
    int depth=0;
    boolean firstIter=true;
    String[][] trainTemp;
    String [][] testTemp;
    int[][] train;
    int[][] test;
    int weightSize=0;
    float weights[];
    float bias=0;
    int mistakeCounter=0;


    RunPerceptron1() throws FileNotFoundException {
        Scanner trainIn = new Scanner (new File (trainFile));
        Scanner testIn = new Scanner (new File (testFile));
        
        int v=0;

        while (trainIn.hasNextLine()){
            trainIn.nextLine();
            trainRow++;
        }
        trainTemp = new String[trainRow][];
        Scanner trainIn1 = new Scanner (new File (trainFile));        
        while (trainIn1.hasNextLine()){
            trainTemp[v++]=trainIn1.nextLine().split("\\s+");
        }
        
        v=0;
        while (testIn.hasNextLine()){
            testIn.nextLine();
            testRow++;
        }
        testTemp = new String[testRow][];
        Scanner testIn1 = new Scanner (new File (testFile));        
        while (testIn1.hasNextLine()){
            testTemp[v++]=testIn1.nextLine().split("\\s+");
        }
    }
    
    void startPerceptron(){
        preProcess();
        trainPerceptron();
    }
    
    void preProcess(){
        int max=0;
        int val=0;
        String [] cellVal=new String[2];
        
        for(int i=0;i<trainRow;i++){
            for(int j=1;j<trainTemp[i].length;j++){
                cellVal=trainTemp[i][j].split(":");
                if((val=(Integer.parseInt(cellVal[0])))>max){
                    max=val;
                }
            }
        }
                
        trainCol=max+2;
        train=new int[trainRow][trainCol];
        
        for(int i=0;i<trainRow;i++){
            for(int j=0;j<trainCol;j++){
                train[i][j]=0;
            } 
        }
        
        for(int i=0;i<trainRow;i++){
            for(int j=0;j<trainTemp[i].length;j++){
                if(j==0){
                    train[i][j]=Integer.parseInt(trainTemp[i][j]);
                }
                else{
                    cellVal=trainTemp[i][j].split(":");
                    val=Integer.parseInt(cellVal[0]);
                    train[i][val+1]=Integer.parseInt(cellVal[1]);     
                }        
            }
        }
        
        weightSize=max+2;
        weights=new float[weightSize];
        for(int i=0;i<weightSize;i++){
            weights[i]=0;
        }
        
        
        max=0;
        val=0;
        for(int i=0;i<testRow;i++){
            for(int j=1;j<testTemp[i].length;j++){
                cellVal=testTemp[i][j].split(":");
                if((val=(Integer.parseInt(cellVal[0])))>max){
                    max=val;
                }
            }
        }
       
        testCol=max+2;
        test=new int[testRow][testCol];
        

        for(int i=0;i<testRow;i++){
            for(int j=0;j<testCol;j++){
                test[i][j]=0;
            } 
        }
        
        for(int i=0;i<testRow;i++){
            for(int j=0;j<testTemp[i].length;j++){
                if(j==0){
                    test[i][j]=Integer.parseInt(testTemp[i][j]);
                }
                else{
                    cellVal=testTemp[i][j].split(":");
                    val=Integer.parseInt(cellVal[0]);
                    test[i][val+1]=Integer.parseInt(cellVal[1]);     
                }        
            }
        }
    }
    
    void trainPerceptron(){
        mistakeCounter=0;
        for(int i=0;i<trainRow;i++){
            float yp=0;
            for(int j=1;j<trainCol;j++){
                yp=yp+(weights[j]*train[i][j]);
            }
            if((train[i][0]*(yp+bias))<=0){
                mistakeCounter++;
                for(int w=1;w<trainCol;w++){
                    weights[w]=weights[w]+(rate*train[i][0]*train[i][w]);
                }
                bias=bias+(rate*train[i][0]);
            }
        }
        printWeights();
    }
    
    int sgn(float n){
        if((n)<0)
            return (-1);
        else
            return (+1);
    }
    
    void printWeights(){
        System.out.print("********Perceptron Sanity Check**********");
        System.out.print("\nNumber of mistakes made = "+mistakeCounter);
        System.out.print("\n\nWeight vector(w):\n");
        for(int i=0;i<weightSize;i++){
            System.out.print(weights[i]+"  ");
        }
        System.out.println("\n\nBias(b) = "+bias);
    }
}
