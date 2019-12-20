/**
 *
 * @author Vipin Jose
 */
import java.io.*;
import java.util.*;

public class Perceptron {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) throws FileNotFoundException {     
        RunPerceptron1 rp1=new RunPerceptron1();
        
/* 3.3.1 Runs basic perceptron using the "table2" input  */
        rp1.startPerceptron();
    
/* 3.3.2 Runs basic perceptron and margin perceptron using the "adult" data   */
        startPerceptron2();
    
/* 3.3.3 Runs with number of epochs   */
        startPerceptron3();
        
/* 3.3.3 Runs perceptron with aggressive update   */
        startPerceptron4();
    }
    

    
    
    public static void startPerceptron4() throws FileNotFoundException{
    //(running multiple times to take the average accuracy due to random weight initialization)
        System.out.println("*************Executing Aggresive Perceptron*****************");
        float [][] runData=new float[1000][8];
        int count=0;
        for(double y=0;y<=5;y=y+1){
            float[] allAccuracyTest,allAccuracyTrain,allMistakes;
            RunPerceptron4.avgAccuracy=0;RunPerceptron4.avgAccuracyTrain=0;
            RunPerceptron4.sd=0;RunPerceptron4.sdTrain=0;RunPerceptron4.avgMistakes=0;
            int counter=10;
            RunPerceptron4.u=(float) y;
            allAccuracyTest = new float[counter];
            allAccuracyTrain = new float[counter];
            allMistakes = new float[counter];
            for(int i=0;i<counter;i++){
                RunPerceptron4 rp4=new RunPerceptron4();
                rp4.startPerceptron();
                allAccuracyTest[i]=RunPerceptron4.accuracy;
                allAccuracyTrain[i]=RunPerceptron4.accuracyTrain;
                allMistakes[i]=RunPerceptron4.mistakesTrain;
            }

            for(int k=0;k<counter;k++){
                RunPerceptron4.avgAccuracy+=allAccuracyTest[k];
                RunPerceptron4.avgAccuracyTrain+=allAccuracyTrain[k];
                RunPerceptron4.avgMistakes+=allMistakes[k];
            }
            RunPerceptron4.avgAccuracy=RunPerceptron4.avgAccuracy/counter;
            RunPerceptron4.avgAccuracyTrain=RunPerceptron4.avgAccuracyTrain/counter;
            RunPerceptron4.avgMistakes=RunPerceptron4.avgMistakes/counter;

            for(int k=0;k<counter;k++){
                RunPerceptron4.sd+=((allAccuracyTest[k]-RunPerceptron4.avgAccuracy)*(allAccuracyTest[k]-RunPerceptron4.avgAccuracy));
                RunPerceptron4.sdTrain+=((allAccuracyTrain[k]-RunPerceptron4.avgAccuracyTrain)*(allAccuracyTrain[k]-RunPerceptron4.avgAccuracyTrain));
            }
            RunPerceptron4.sd=RunPerceptron4.sd/counter;
            RunPerceptron4.sd=(float) Math.sqrt(RunPerceptron4.sd); 
            RunPerceptron4.sdTrain=RunPerceptron4.sdTrain/counter;
            RunPerceptron4.sdTrain=(float) Math.sqrt(RunPerceptron4.sdTrain);

            System.out.println("\n\nRun Number\tAccuracy on Train\tAccuracy of Test\tNumber of Mistakes");
            for(int k=0;k<counter;k++){
                System.out.println((k+1)+"\t\t"+allAccuracyTrain[k]*100+" %\t\t"+allAccuracyTest[k]*100+" %\t\t"+allMistakes[k]);
            }


            System.out.println("Margin Used(u) = "+RunPerceptron4.u);
            System.out.println("Number of Epochs = "+RunPerceptron4.epochs);

            System.out.println("Number of mistakes during training = "+RunPerceptron4.avgMistakes);

            System.out.println("\nAverage accuracy on Train Data = "+RunPerceptron4.avgAccuracyTrain*100+" %");
            System.out.println("Standard deviation on Train Data = "+RunPerceptron4.sdTrain);

            System.out.println("\nAverage accuracy on Test Data = "+RunPerceptron4.avgAccuracy*100+" %");
            System.out.println("Standard deviation on Test Data = "+RunPerceptron4.sd); 
            runData[count][0]=count;
            runData[count][1]=(float) RunPerceptron4.rate;
            runData[count][2]=(float) y;
            runData[count][3]=(float) RunPerceptron4.avgAccuracyTrain;
            runData[count][4]=(float) RunPerceptron4.sdTrain;
            runData[count][5]=(float) RunPerceptron4.avgAccuracy;
            runData[count][6]=(float) RunPerceptron4.sd;
            runData[count][7]=(float) RunPerceptron4.avgMistakes;
            count++;
        }    
        System.out.println("*************SUMMARY for Aggresive Perceptron*****************");
        System.out.println("S.No.\t\tFinal Rate\t\tMargin\t\tTrain Accuracy\t\tTraining SD\t\tTest Accuracy\t\tTest SD\t\tAverage Number of Mistakes");
        for (int p=0;p<count;p++){
            System.out.println(runData[p][0]+"\t\t"+runData[p][1]+"\t\t"+runData[p][2]+"\t\t"+runData[p][3]+"\t\t"+runData[p][4]+"\t\t"+runData[p][5]+"\t\t"+runData[p][6]+"\t\t"+runData[p][7]);
        }
    }
       
       

    public static void startPerceptron3() throws FileNotFoundException{
    //(running multiple times to take the average accuracy due to random weight initialization)
        System.out.println("\n*************Executing Batch Setting(Using Epochs)*****************");
        float [][] runData=new float[1000][8];
        int count=0;
        for(double r=0.1;r<=1;r=r+0.2){
            for(double y=0;y<=5;y=y+1){
                float[] allAccuracyTest,allAccuracyTrain,allMistakes;
                RunPerceptron3.avgAccuracy=0;RunPerceptron3.avgAccuracyTrain=0;
                RunPerceptron3.sd=0;RunPerceptron3.sdTrain=0;RunPerceptron3.avgMistakes=0;
                int counter=10;
                RunPerceptron3.rate=(float) r;
                RunPerceptron3.u=(float) y;
                allAccuracyTest = new float[counter];
                allAccuracyTrain = new float[counter];
                allMistakes = new float[counter];
                for(int i=0;i<counter;i++){
                    RunPerceptron3 rp3=new RunPerceptron3();
                    rp3.startPerceptron();
                    allAccuracyTest[i]=RunPerceptron3.accuracy;
                    allAccuracyTrain[i]=RunPerceptron3.accuracyTrain;
                    allMistakes[i]=RunPerceptron3.mistakesTrain;
                }

                for(int k=0;k<counter;k++){
                    RunPerceptron3.avgAccuracy+=allAccuracyTest[k];
                    RunPerceptron3.avgAccuracyTrain+=allAccuracyTrain[k];
                    RunPerceptron3.avgMistakes+=allMistakes[k];
                }
                RunPerceptron3.avgAccuracy=RunPerceptron3.avgAccuracy/counter;
                RunPerceptron3.avgAccuracyTrain=RunPerceptron3.avgAccuracyTrain/counter;
                RunPerceptron3.avgMistakes=RunPerceptron3.avgMistakes/counter;

                for(int k=0;k<counter;k++){
                    RunPerceptron3.sd+=((allAccuracyTest[k]-RunPerceptron3.avgAccuracy)*(allAccuracyTest[k]-RunPerceptron3.avgAccuracy));
                    RunPerceptron3.sdTrain+=((allAccuracyTrain[k]-RunPerceptron3.avgAccuracyTrain)*(allAccuracyTrain[k]-RunPerceptron3.avgAccuracyTrain));
                }
                RunPerceptron3.sd=RunPerceptron3.sd/counter;
                RunPerceptron3.sd=(float) Math.sqrt(RunPerceptron3.sd); 
                RunPerceptron3.sdTrain=RunPerceptron3.sdTrain/counter;
                RunPerceptron3.sdTrain=(float) Math.sqrt(RunPerceptron3.sdTrain);

                System.out.println("\n\nRun Number\tAccuracy on Train\tAccuracy of Test\tNumber of Mistakes");
                for(int k=0;k<counter;k++){
                    System.out.println((k+1)+"\t\t"+allAccuracyTrain[k]*100+" %\t\t"+allAccuracyTest[k]*100+" %\t\t"+allMistakes[k]);
                }

                System.out.println("\nRate Used(r) = "+RunPerceptron3.rate);
                System.out.println("Margin Used(u) = "+RunPerceptron3.u);
                System.out.println("Number of Epochs = "+RunPerceptron3.epochs);

                System.out.println("Number of mistakes during training = "+RunPerceptron3.avgMistakes);

                System.out.println("\nAverage accuracy on Train Data = "+RunPerceptron3.avgAccuracyTrain*100+" %");
                System.out.println("Standard deviation on Train Data = "+RunPerceptron3.sdTrain);

                System.out.println("\nAverage accuracy on Test Data = "+RunPerceptron3.avgAccuracy*100+" %");
                System.out.println("Standard deviation on Test Data = "+RunPerceptron3.sd); 
                runData[count][0]=count;
                runData[count][1]=(float) r;
                runData[count][2]=(float) y;
                runData[count][3]=(float) RunPerceptron3.avgAccuracyTrain;
                runData[count][4]=(float) RunPerceptron3.sdTrain;
                runData[count][5]=(float) RunPerceptron3.avgAccuracy;
                runData[count][6]=(float) RunPerceptron3.sd;
                runData[count][7]=(float) RunPerceptron3.avgMistakes;
                count++;
            }    
        }
        System.out.println("*************SUMMARY for Batch Setting(Using Epochs)*****************");
        System.out.println("S.No.\t\tRate\t\tMargin\t\tTrain Accuracy\t\tTraining SD\t\tTest Accuracy\t\tTest SD\t\tAverage Number of Mistakes");
        for (int p=0;p<count;p++){
            System.out.println(runData[p][0]+"\t\t"+runData[p][1]+"\t\t"+runData[p][2]+"\t\t"+runData[p][3]+"\t\t"+runData[p][4]+"\t\t"+runData[p][5]+"\t\t"+runData[p][6]+"\t\t"+runData[p][7]);
        }
    }
    
    
    
    
    public static void startPerceptron2() throws FileNotFoundException{
    //(running multiple times to take the average accuracy due to random weight initialization)
        System.out.println("\n*************Executing Online Setting(Normal and Margin Perceptron)*****************");
        float [][] runData=new float[1000][8];
        int count=0;
        for(double r=0.1;r<=1;r=r+0.2){
            for(double y=0;y<=5;y=y+1){
                float[] allAccuracyTest,allAccuracyTrain,allMistakes;
                RunPerceptron2.avgAccuracy=0;RunPerceptron2.avgAccuracyTrain=0;
                RunPerceptron2.sd=0;RunPerceptron2.sdTrain=0;RunPerceptron2.avgMistakes=0;
                int counter=10;
                RunPerceptron2.rate=(float) r;
                RunPerceptron2.u=(float) y;
                allAccuracyTest = new float[counter];
                allAccuracyTrain = new float[counter];
                allMistakes = new float[counter];
                for(int i=0;i<counter;i++){
                    RunPerceptron2 rp2=new RunPerceptron2();
                    rp2.startPerceptron();
                    allAccuracyTest[i]=RunPerceptron2.accuracy;
                    allAccuracyTrain[i]=RunPerceptron2.accuracyTrain;
                    allMistakes[i]=RunPerceptron2.mistakesTrain;
                }

                for(int k=0;k<counter;k++){
                    RunPerceptron2.avgAccuracy+=allAccuracyTest[k];
                    RunPerceptron2.avgAccuracyTrain+=allAccuracyTrain[k];
                    RunPerceptron2.avgMistakes+=allMistakes[k];
                }
                RunPerceptron2.avgAccuracy=RunPerceptron2.avgAccuracy/counter;
                RunPerceptron2.avgAccuracyTrain=RunPerceptron2.avgAccuracyTrain/counter;
                RunPerceptron2.avgMistakes=RunPerceptron2.avgMistakes/counter;

                for(int k=0;k<counter;k++){
                    RunPerceptron2.sd+=((allAccuracyTest[k]-RunPerceptron2.avgAccuracy)*(allAccuracyTest[k]-RunPerceptron2.avgAccuracy));
                    RunPerceptron2.sdTrain+=((allAccuracyTrain[k]-RunPerceptron2.avgAccuracyTrain)*(allAccuracyTrain[k]-RunPerceptron2.avgAccuracyTrain));
                }
                RunPerceptron2.sd=RunPerceptron2.sd/counter;
                RunPerceptron2.sd=(float) Math.sqrt(RunPerceptron2.sd); 
                RunPerceptron2.sdTrain=RunPerceptron2.sdTrain/counter;
                RunPerceptron2.sdTrain=(float) Math.sqrt(RunPerceptron2.sdTrain);

                System.out.println("\n\nRun Number\tAccuracy on Train\tAccuracy of Test\tNumber of Mistakes");
                for(int k=0;k<counter;k++){
                    System.out.println((k+1)+"\t\t"+allAccuracyTrain[k]*100+" %\t\t"+allAccuracyTest[k]*100+" %\t\t"+allMistakes[k]);
                }

                System.out.println("\nRate Used(r) = "+RunPerceptron2.rate);
                System.out.println("Margin Used(u) = "+RunPerceptron2.u);

                System.out.println("Number of mistakes during training = "+RunPerceptron2.avgMistakes);

                System.out.println("\nAverage accuracy on Train Data = "+RunPerceptron2.avgAccuracyTrain*100+" %");
                System.out.println("Standard deviation on Train Data = "+RunPerceptron2.sdTrain);

                System.out.println("\nAverage accuracy on Test Data = "+RunPerceptron2.avgAccuracy*100+" %");
                System.out.println("Standard deviation on Test Data = "+RunPerceptron2.sd); 
                runData[count][0]=count;
                runData[count][1]=(float) r;
                runData[count][2]=(float) y;
                runData[count][3]=(float) RunPerceptron2.avgAccuracyTrain;
                runData[count][4]=(float) RunPerceptron2.sdTrain;
                runData[count][5]=(float) RunPerceptron2.avgAccuracy;
                runData[count][6]=(float) RunPerceptron2.sd;
                runData[count][7]=(float) RunPerceptron2.avgMistakes;
                count++;
            }    
        }
        System.out.println("*************SUMMARY for Online Setting(Normal and Margin Perceptron)*****************");
        System.out.println("S.No.\t\tRate\t\tMargin\t\tTrain Accuracy\t\tTraining SD\t\tTest Accuracy\t\tTest SD\t\tAverage Number of Mistakes");
        for (int p=0;p<count;p++){
            System.out.println(runData[p][0]+"\t\t"+runData[p][1]+"\t\t"+runData[p][2]+"\t\t"+runData[p][3]+"\t\t"+runData[p][4]+"\t\t"+runData[p][5]+"\t\t"+runData[p][6]+"\t\t"+runData[p][7]);
        }
    }
}



