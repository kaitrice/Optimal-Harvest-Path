import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/* 
Name: Kaitlyn Rice
Class: CSCI 405
Assignment: Optimal path through the truffle feild
*/

public class truffleRoute {
    /*
    Function: optimalRoute
    Parameters: feild, 2D list

    Output: Max path sum

    Runtime: O(N*M)
    */
    public static Integer optimalRoute(List<List<Integer>> feild) {
		int N = feild.size();
		int M = feild.get(0).size();
        int truffles = 0;
        int mx;

        // update feild
		for (int i = 1; i < N; i++) {
            truffles = 0;
            for (int j = 0; j < M; j++) {
                mx = 0;
                // all paths
                if (j > 0 && j < M - 1) {
                    int down = feild.get(i - 1).get(j);
                    int left = feild.get(i - 1).get(j - 1);
                    int right = feild.get(i - 1).get(j + 1);
                    mx =  Math.max(down, Math.max(left, right));

                    feild.get(i).set(j, feild.get(i).get(j) + mx);
                } // no right diagonal
                else if (j > 0) {
                    int down = feild.get(i - 1).get(j);
                    int left = feild.get(i - 1).get(j - 1);
                    mx = Math.max(down, left);

                    feild.get(i).set(j, feild.get(i).get(j) + mx);
                } // no diagonal left 
                else if (j < M - 1) {
                    int down = feild.get(i - 1).get(j);
                    int right = feild.get(i - 1).get(j + 1);
                    mx = Math.max(down, right);

                    feild.get(i).set(j, feild.get(i).get(j) + mx);
                }
                // update max truffles
                truffles = Math.max(feild.get(i).get(j), truffles);
                
            }
        }
        printFeild(feild, N, M);
        return truffles;
	}

    /*
    Function: optimalRoute
    Parameters: feild, 2D list
                N, # rows
                M, # columns

    Output: Print max path
    Runtime: O(N)
    */
    public static void printFeild(List<List<Integer>> feild, int N, int M) {
        int[] path = new int[N];
        int mx = -1;
        int maxRow = 0;

        // last row O(M)
        for (int j = 1; j < M; j++) {
            if (feild.get(N - 1).get(j) == Math.max(feild.get(N - 1).get(j), feild.get(N - 1).get(maxRow))) 
                maxRow = j;
        }
        path[N - 1] = maxRow;

        // get path
        for (int i = N - 1; i > 0; i--) {
            int j = path[i];
            // all paths 
            if (j > 0 && j < M-1) {
                int up = feild.get(i - 1).get(j);
                int left = feild.get(i - 1).get(j - 1);
                int right = feild.get(i - 1).get(j + 1);
                mx = Math.max(up, Math.max(left, right));

                if (mx == up)
                    path[i - 1] = j;
                else if (mx == left) 
                    path[i - 1] = j - 1;
                else if (mx == right) 
                    path[i - 1] = j + 1;
            } // no right diagonal
            else if (j > 0) {
                int up = feild.get(i - 1).get(j);
                int left = feild.get(i - 1).get(j - 1);
                mx = Math.max(up, left);

                if (mx == up)
                    path[i - 1] = j;
                else if (mx == left) 
                    path[i - 1] = j - 1;
            } // no left diagonal
            else if (j < M-1) {
                int up = feild.get(i - 1).get(j);
                int right = feild.get(i - 1).get(j + 1);
                mx = Math.max(up, right);

                if (mx == up)
                    path[i - 1] = j;
                else if (mx == right) 
                    path[i - 1] = j + 1;
            }            
        }

        // print path
        System.out.println("[" + 1 + "," + (path[0] + 1) + "] - " + feild.get(0).get(path[0]));
        for (int i = 1; i < N; i++) {
            System.out.println("[" + (i + 1) + "," + (path[i] + 1) + "] - " + (feild.get(i).get(path[i])-feild.get(i-1).get(path[i-1])));
        }
    }

	public static void main(String[] args) {
		File input = new File(args[0]);
		try {
			Scanner sc = new Scanner(input);
            List<List<Integer>> feild = new ArrayList<List<Integer>>();
            int i = 0;

            while (sc.hasNextLine()) {
                List<Integer> row = new ArrayList<Integer>();
                String[] temp = sc.nextLine().split(" ");
                
                for (String str : temp) {
                    if (!str.isEmpty()) {
                        i = Integer.parseInt(str);
                        row.add(i);
                    }
                }
                feild.add(row);
            }

            if (!feild.isEmpty()) {
                System.out.print(optimalRoute(feild));
                System.out.println(" truffles");
            } else
                System.out.println("0 truffles");
            

			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error: File not found.");
		}
		
	}
}