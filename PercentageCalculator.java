import java.util.Scanner;
public class PercentageCalculator {
    public static void main(String[] args){
        Scanner sObj=new Scanner(System.in);

        System.out.println("Welcome to Grade calculator");
        String again;
        do{
            int numOfSub;
            System.out.println("To start , Please Enter the Number of subjects");

        // check for valid input for number of subjects 
        while (true) {
                if (sObj.hasNextInt()) {
                    numOfSub = sObj.nextInt();
                    if (numOfSub > 0) {
                        break; 
                    } else {
                        System.out.println("Invalid input. Please enter a valid number greater than 0 for subjects.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid integer for the number of subjects.");
                    sObj.next(); 
                }
            }

        float[] marksArray=new float[numOfSub];
        float totalSum=0;
        

        // Take input of marks obtained in each subject
        for (int i = 0; i < numOfSub; i++) {
                System.out.println("Enter the marks of " + (i + 1) + " subject (0 to 100):");
            while (true) {
                float marks = sObj.nextFloat();
        
                if (marks >= 0 && marks <= 100) {
                    marksArray[i] = marks;
                    break;
                } else {
                    System.out.println("Invalid marks. Please enter a value between 0 and 100.");
                }
            }
        }
        
        // total marks sum 
        for(float mark:marksArray){
           totalSum+= mark;
        }
        float avgPercentage=totalSum/numOfSub;

        String status ="";
        if(avgPercentage>=60){
            status="pass";
        }else{
            status="fail";
        }
        String gradeStatus=GradeCalculator(avgPercentage);
        DisplayResults(totalSum, avgPercentage,status,gradeStatus);
        System.out.println("Do you want to calculate again? (y/n)");
         again=sObj.next();
         
         if (!again.equalsIgnoreCase("y") && !again.equalsIgnoreCase("n")) {
            System.out.println("Invalid input. Exiting the program.");
            System.exit(0);
        }
    }while(again.equalsIgnoreCase("y"));
        sObj.close();
        System.out.println("Thank you for using Grade Calculator , exiting the program");
    }
    // method to assign grades according to percentage
    public static String GradeCalculator(float avgPercentage){
        if( avgPercentage>=90){
            return "O";
        }else if( avgPercentage>=80){
            return "A+";
        }else if( avgPercentage>=70){
            return  "A";
        }else if( avgPercentage >=60){
            return "B+";
            
        }else {
            return "N/A";
            
        }
    }
      
    // method to display results 
    public static void DisplayResults(float totalSum,float averagePercentage,String status ,String grade){
    System.out.println("Results :");
    System.out.println("The total marks you have Scored are :"+totalSum);
    System.out.println("The average Percentage of your Score is :"+averagePercentage);
    System.out.println("Status:"+status);
    System.out.println("Grade :"+grade);   
}
    }

