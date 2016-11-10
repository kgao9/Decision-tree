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
    * The purpose of this class is to get the best question
    * @param questions - array list of questions
    * @param examples - array list of cars
    * @return the best question to ask
    */

    public static String bestQuestion(ArrayList <String> questions, ArrayList <Politician> examples)
    {
        return "";
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
            System.out.println(header);
        }

        headersList.remove("Name");
        headersList.remove("p_aff");

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
 
            System.out.println(myPol.toString());
        }
    }
}
