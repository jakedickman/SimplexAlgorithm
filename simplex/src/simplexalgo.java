import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Scanner;

public class simplexalgo {

    public static int[] create_obj_fn(int vars)
    {
        int[] objective_fn = new int[vars];
        Scanner readInput = new Scanner(System.in);
        System.out.println("Great!, next give objective function.");
        for (int i = 0; i<vars; i++) //Stores objective function coefficients in an array
        {
            System.out.print("c"+ (i+1) + " = ");
            objective_fn[i] = readInput.nextInt();
        }
        System.out.print("Confirmed! Your objective function is: Maximize z = "); //Minimization will be supported soon
        for (int i = 0; i<vars; i++) //Prints the objective function variables
        {
            if (objective_fn[i] == 1){
                System.out.print("x_" + (i+1));
            } 
            else{
                System.out.print(objective_fn[i] + "x_" + (i+1));
            }
            if (i != vars-1)
            {
                System.out.print(" + ");
            }
        }
        return objective_fn;
    }

    public static int[][] create_constraints(int cons, int vars)
    {
        int[][] pre_constraints = new int[cons][vars]; //Constrains matrix before slack/artificial variables added for left side of constraints
        int[] pre_const_right = new int[cons]; //Holds the value for the right side of each constraint
        int[] eq_arr = new int[cons]; //Holds the value of the equality for each constraint
        int slack_count = 0; //Tracks how many slack variables are added
        Scanner readInput = new Scanner(System.in);
        for (int i = 0; i<cons; i++) //Stores left side of the constraints into a matrix
        {
            System.out.println("For constraint " + (i+1) + ":");
            for (int j = 0; j<vars; j++)
            {
                System.out.print("c" + (j+1) + " = ");
                pre_constraints[i][j] = readInput.nextInt();
            }
            System.out.println("Next, pick the kind of equality that constraint " + (i+1) + " will have.\nType 1 for >=, 2 for =, or 3 for <=");
            {
                int pick = readInput.nextInt();
                if (pick == 1 || pick == 2)
                {
                    System.out.println("These options are not supported yet...");
                }
                else if (pick == 3)
                {
                    System.out.println("You selected <=\nAdding slack variables...");
                    eq_arr[i] = 3;
                    slack_count++;
                }
                else
                {
                    System.out.println("Invalid input. Program terminating...");
                    System.exit(0);
                }
            }
            System.out.println("Now pick the right side of the constraint");
            System.out.print("X_b" + (i+1) +" = ");
            pre_const_right[i] = readInput.nextInt();
        }

        int[][] fin_constraints = new int[cons][vars+slack_count+1]; //array of final constraints with slack variables and right side included
        for (int i = 0; i<cons; i++) //fill the left side of constraints in before adding the slack
        {
            for (int j = 0; j<vars; j++)
            {
                fin_constraints[i][j] = pre_constraints[i][j];
            }
        }
        int added_slack_count = 0;
        for (int i = 0; i<cons; i++) //adds slack variables to the constraints and adds right side
        {
            if (eq_arr[i] == 1)
            {
                System.out.println("not supported yet");
                System.exit(0);
            }
            if (eq_arr[i] == 3)
            {
                fin_constraints[i][vars+added_slack_count] = 1;
                added_slack_count++;
            }
            for (int j = vars; j<vars+slack_count; j++)
            {
                if (fin_constraints[i][j] != 1 && fin_constraints[i][j] != -1)
                {
                    fin_constraints[i][j] = 0;
                }
            }
            fin_constraints[i][vars+slack_count] = pre_const_right[i];
        }
        for (int i = 0; i<cons; i++) //prints constraints
        {
            for (int j = 0; j<vars+slack_count; j++)
            {
                System.out.print(fin_constraints[i][j] + "x_" +(j+1));
                if (j != (vars+slack_count)-1)
                {
                    System.out.print(" + ");
                }
            }
            System.out.println(" = " + fin_constraints[i][vars+slack_count]);
        }
        return fin_constraints;
    }

    public static int[][] create_tableau(int rows, int cols, int[] obj, int[][] cons)
    {
        int[][] tab = new int[rows][cols];
        return tab;
    }

    public static void run_simplex(int[][] tab)
    {
        return;
    }

    public static void main(String[] args) throws Exception {
        Scanner readInput = new Scanner(System.in);
        System.out.println("Welcome to the simplex algorithm calculator!\nBegin by inputting how many variables we will be dealing with: ");
        int numvars = readInput.nextInt();
        if (numvars <= 0)
        {
            System.out.println("Invalid input. Program terminating...");
            return;
        }
        int[] obj_fn = create_obj_fn(numvars);
        System.out.println("\nNext, how many constraints are there?");
        int num_const = readInput.nextInt();
        if (num_const <= 0)
        {
            System.out.println("Invalid input. Program terminating...");
            return;
        }
        int[][] constraints = create_constraints(num_const, numvars);
        System.out.println("Creating tableau now...");
        int[][] tableau = create_tableau(num_const+1, constraints[0].length, obj_fn, constraints);
        System.out.println("Finding optimal solution...");
        run_simplex(tableau);
    }
}
