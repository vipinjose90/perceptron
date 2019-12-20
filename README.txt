To execute the program, just run “perceptron.sh”.



NOTE
Please note that each unique combination of the hyper-parameters in the following questions have been run over 50 times and taken an average of due to the random initialization of weights. These execution would take several minutes. Therefore, for the code submitted, I have reduced the number of iterations, and also the number of values considered for hyper-parameters have been reduced.

For the exhaustive data for each run, please refer to the following folder-"Outputs/OutputData".


In order to run all exhaustive cases, make the following changes:

1. In Perceptron.java, initialize all counter variable to 50, instead of 10.
2. Change all “for(double r=0.1;r<=1;r=r+0.2)” to “for(double r=0.1;r<=1;r=r+0.1)”
3. Change all for(double y=0;y<=5;y=y+0.5)
4. In order to shuffle/ not shuffle the data, comment or uncomment “shuffle();” in each class.
5. To change the number of epochs change value in “public static int epochs=5” in each class.