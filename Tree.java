import java.util.*;
import java.lang.*;
import java.io.*;

/**
* Best practice to use a lot of documentation
* @author Kenny Gao
* @version 11/8/2016
*
* @class cs 540
*/

public class Tree
{
    /**
    * The purpose of this class is to print out the decision tree
    * @param node
    * no return
    */

    public static void printTree(Node n)
    {
        System.out.println(n.toString());

        ArrayList <Node> succ = n.getSucc();

        if(succ.size() != 0)
        {
            for(Node successor: succ)
            {
                printTree(successor);
            }
        }
    }

    public static String get_p_aff(testPolitician test, Node n)
    {
        if(n.getSucc().size() == 0)
        {
            return n.majority();
        }

        else
        {
            return get_p_aff(test, n.scan(test));
        }
    }

    public static void main(String [] args) throws FileNotFoundException
    {
        //prepare to read in the file
        File file = new File("train_house_votes_1984.csv");

        //create new scanner
        Scanner scanner = new Scanner(file);

        //no lines means empty file
        if(!scanner.hasNextLine())
        {
            System.out.println("invalid file format found... Closing");
            System.exit(0);
        }

        //get the header of the CSV and format it
        String [] headers = scanner.nextLine().split(",");

        ArrayList <String> headersList = new ArrayList <String> ();

        for(String header: headers)
        {
            headersList.add(header);
        }

        headersList.remove("Name");
        headersList.remove("p_aff");

        ArrayList <Politician> dataset = new ArrayList <Politician> ();

        //get all feature data
        while(scanner.hasNextLine())
        {
            //get line
            String getLine = scanner.nextLine();
            
            String [] attr = getLine.split(",");

            //invalid politician
            if(attr.length != headersList.size() + 2)
            {
                System.out.println("here");
                System.out.println("invalid file format found... Closing");
                System.exit(0);
            }

            boolean [] features = new boolean [headersList.size()];

            //get name
            String name = attr[0];
           
            //get political affiliation
            String p_aff = attr[1];

            for(int i = 0; i < features.length; i++)
            {
                if(!attr[i+2].equals('y') && attr[i+2].equals('n'))
                {
                    System.out.println("invalid file format found... Closing");
                    System.exit(0);
                }

                features[i] = attr[i + 2].equals("y");
            }

            Politician myPol = new Politician(headersList, features, p_aff, name);
 
            dataset.add(myPol);
        }

        //create the decision tree
        Node root = new Node(dataset, new ArrayList <String> ());
        //System.out.println(root.toString());
        //printTree(root);

        //prepare to read in the file
        File file1 = new File("test.csv");

        //create new scanner
        Scanner scanner1 = new Scanner(file1);

         //get all feature data
        while(scanner1.hasNextLine())
        {
            //get line
            String getLine = scanner1.nextLine();

            String [] attr = getLine.split(",");

            //invalid politician
            if(attr.length != headersList.size() + 1)
            {
                System.out.println("here");
                System.out.println("invalid file format found... Closing");
                System.exit(0);
            }

            boolean [] features = new boolean [headersList.size()];

            //get name
            String name = attr[0];

            for(int i = 0; i < features.length; i++)
            {
                if(!attr[i+1].equals('y') && attr[i+1].equals('n'))
                {
                    System.out.println("invalid file format found... Closing");
                    System.exit(0);
                }

                features[i] = attr[i + 1].equals("y");
            }

            testPolitician myPol = new testPolitician(headersList, features, name);

            System.out.println(get_p_aff(myPol,root));
        }

        //printTree(root);
    }
}
