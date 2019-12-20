import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

class RunPerceptron4{
    //Mention the Training File here.
    public static String trainFile="data/a5a.train";
    
    //Mention the Test File here.
    public static String testFile="data/a5a.test";
    
    public static float rate=(float) 0.1;
    
    public static float u=(float) 5;
 
    public static int epochs=3;
    
    public static float accuracy, accuracyTrain;
    public static float avgAccuracy,avgAccuracyTrain;
    public static float sd,sdTrain;
    public static int mistakesTrain;
    public static float avgMistakes;
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
    int prediction[];


    RunPerceptron4() throws FileNotFoundException {
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
        predictPerceptrononTrain();
        predictPerceptrononTest();
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
        
        /*Initializing the weights and bias randomly*/
        weightSize=max+2;
        weights=new float[weightSize];
        for(int i=1;i<weightSize;i++){
            weights[i]=(float) ((-1) + (Math.random() * (((+1) - (-1) + 1))));
        }
        bias=(float) ((-1) + (Math.random() * (((+1) - (-1) + 1))));
        
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
        prediction=new int[testRow];
    }
    
    void trainPerceptron(){
        mistakeCounter=0;
        for(int e=0;e<epochs;e++){
            shuffle();
            for(int i=0;i<trainRow;i++){
                float yp=0;
                float xdot=0;
                for(int j=1;j<trainCol;j++){
                    yp=yp+(weights[j]*train[i][j]);
                    xdot=xdot+(train[i][j]*train[i][j]);
                }
                xdot=xdot+1;
                if((train[i][0]*(yp+bias))<u){
                    mistakeCounter++;
                    rate=(u-(train[i][0]*(yp+bias)));
                    rate=rate/xdot;
                    for(int w=1;w<trainCol;w++){
                        weights[w]=weights[w]+(rate*train[i][0]*train[i][w]);
                    }
                    bias=bias+(rate*train[i][0]);
                }
            }
        }
        mistakesTrain=mistakeCounter;
    //    printTrainStats();
    }

    
    void predictPerceptrononTest(){
        mistakeCounter=0;
        int yp;
        for(int i=0;i<testRow;i++){
            float ytemp=0;
            for(int j=1;j<weightSize;j++){
                ytemp=ytemp+(weights[j]*test[i][j]);
            }
            yp=sgn(ytemp+bias);
            prediction[i]=yp;
            if(yp!=test[i][0]){
                mistakeCounter++;
            }
        }
        accuracy=((testRow-mistakeCounter)/(float)testRow);
        mistakesTrain=mistakeCounter;
    //    printTestStatsonTest();
    }
    
    void predictPerceptrononTrain(){
        mistakeCounter=0;
        int yp;
        for(int i=0;i<trainRow;i++){
            float ytemp=0;
            for(int j=1;j<weightSize;j++){
                ytemp=ytemp+(weights[j]*train[i][j]);
            }
            yp=sgn(ytemp+bias);
            prediction[i]=yp;
            if(yp!=train[i][0]){
                mistakeCounter++;
            }
        }
        accuracyTrain=((trainRow-mistakeCounter)/(float)trainRow);
    //    printTestStatsonTrain();
    }
    
    void printTrainStats(){
        System.out.print("\n\n***** Training on data : "+trainFile+" *****");
        System.out.print("\nNumber of Epochs = "+epochs);
        System.out.print("\nRate used(r) = "+rate);
        System.out.print("\nMargin used(u) = "+u);
        System.out.print("\nWeight vector(w):\n");
        for(int i=0;i<weightSize;i++){
            System.out.print(weights[i]+"  ");
        }
        System.out.print("\nBias(b) = "+bias);
        System.out.print("\nNumber of mistakes made in training = "+mistakeCounter);
        System.out.print("\nTotal input = "+trainRow);
        System.out.println("\nAccuracy = "+((((trainRow*epochs)-mistakeCounter)/((float)trainRow*epochs)))*100+" %");
    }    
    
    void printTestStatsonTest(){    
        System.out.print("\n***** Testing on Test data : "+testFile+" *****");
        System.out.print("\nNumber of incorrect predictions = "+mistakeCounter);
        System.out.print("\nTotal input = "+testRow);
        System.out.println("\nAccuracy = "+accuracy*100+" %");
        
    }
    
    void printTestStatsonTrain(){    
        System.out.print("\n***** Testing on Train data : "+trainFile+" *****");
        System.out.print("\nNumber of incorrect predictions = "+mistakeCounter);
        System.out.print("\nTotal input = "+trainRow);
        System.out.println("\nAccuracy = "+accuracyTrain*100+" %");        
    }
    
    
    int sgn(float n){
        if((n)<0)
            return (-1);
        else
            return (+1);
    }
    
    void shuffle(){
        int temp;

        for (int i=0; i<trainRow; i++) {
            Random rnum = new Random();
            int ranPos = rnum.nextInt(trainRow);
            for(int j=0;j<trainCol;j++){
                temp = train[i][j];
                train[i][j] = train[ranPos][j];
                train[ranPos][j] = temp;
            }
        }
    }
}
