import java.util.*;

class Node
{
       ArrayList <Politician> dataset;
       ArrayList <String> ignoreStrings;

       public Node(ArrayList <Politician> dataset, ArrayList <String> ignoreStrings)
       {
           this.dataset = dataset;
           this.ignoreStrings = ignoreStrings;
       }

       public String toString()
       {
           ArrayList <Politician> republicans = new ArrayList <Politician> ();
           ArrayList <Politician> democrat = new ArrayList <Politician> ();

           //divide based on political affiliation
           for(int i = 0; i < dataset.size(); i++)
           {
               if(dataset.get(i).getP_aff().equals("democrat"))
                   democrat.add(dataset.get(i));

               else
                   republicans.add(dataset.get(i));
           }

           //print out what is in current dataset
           String returnString = "Republicans: " + String.valueOf(republicans.size());
           returnString += "\nDemocrats: " + String.valueOf(democrat.size());

           if(bestQuestion().equals(""))
               returnString += "\nLeaf Node";

           else
               returnString += "\nBest Question: " + bestQuestion();

           return returnString;
       }

       private double h_y(ArrayList <Politician> data)
       {
           //get size of data
           double numberOfPeople = data.size();

           //if dataset is empty, we want it to be extremely negative
           //this is because of lim x * log (1/x) as x -> infinity
           // = -inf
           if(numberOfPeople == 0)
           {
               return -Double.MAX_VALUE;
           }

           ArrayList <Politician> republicans = new ArrayList <Politician> ();
           ArrayList <Politician> democrat = new ArrayList <Politician> ();

           //filter by political affiliation
           for(int i = 0; i < data.size(); i++)
           {
               if(data.get(i).getP_aff().equals("democrat"))
               {
                   democrat.add(data.get(i));
               }

               else
                   republicans.add(data.get(i));
           }

           //get probabilities
           double prepub = (double)(republicans.size()) / (double)(numberOfPeople);
           double pdemocrat = (double)(democrat.size()) / (double)(numberOfPeople);

           //lim xlogx x -> 0 = 0
           if(prepub == 0 && pdemocrat == 0)
           {
               return 0;
           }           

           else if (pdemocrat == 0)
           {
               return -prepub * Math.log(prepub) / Math.log(2);
           }

           else if(prepub == 0)
           {
                return -pdemocrat * Math.log(pdemocrat) / Math.log(2);
           }

           //return normally
           else
           {
               return -pdemocrat * Math.log(pdemocrat) / Math.log(2) + -prepub * Math.log(prepub) / Math.log(2);
           }
       }

       private double h_y_given_x(String key)
       {
           double numberOfPeople = this.dataset.size();

           //if dataset is empty, we want it to be extremely negative
           //this is because of lim x * log (1/x) as x -> infinity
           // = -inf
           if(numberOfPeople == 0)
           {
               return -Double.MAX_VALUE;
           }

           //split them based upon features
           ArrayList <Politician> yesData = new ArrayList <Politician> ();
           ArrayList <Politician> noData = new ArrayList <Politician> ();

           for(Politician inDataSet: this.dataset)
           {
               //if true, it's a yes instance
               if(inDataSet.getFeatures().get(key))
               {
                   yesData.add(inDataSet);
               }
 
               //else a no instance
               else
                   noData.add(inDataSet);     
           }

           double prxY = (double)(yesData.size()) / numberOfPeople;
           double prxN = (double)(noData.size()) / numberOfPeople;
          
           double h1 = h_y(yesData);

           double h2 = h_y(noData);

           return prxY * h1 + prxN * h2;
       }

       private String bestQuestion()
       {
           //we don't want to calculate H(x) for a zero current
           //dataset size
           if(noSplit())
               return "";

           //get the current h_y
           double currentHY = h_y(this.dataset);

           //get keys
           ArrayList <String> keys = this.dataset.get(0).getKeys();

           //if in ignore keys, remove keys
           for(int i = 0; i < keys.size(); i++)
           {
               if(ignoreStrings.contains(keys.get(i)))
               {
                   keys.remove(i);
                   i--;
               }
           }

           double max = -Double.MAX_VALUE;
           String returnString = "d";

           //for each key, calculate information gain
           for(int i = 0; i < keys.size(); i++)
           {
               double infGain = currentHY - h_y_given_x(keys.get(i));
               
               if(infGain > max)
               {
                   max = infGain;

                   returnString = keys.get(i); 
               }
           }

           return returnString;
       }

       private boolean noSplit()
       {
           //see if a split is needed
           //if not, this is a leaf node
           ArrayList <Politician> republicans = new ArrayList <Politician> ();
           ArrayList <Politician> democrat = new ArrayList <Politician> ();

           //divide based on political affiliation
           for(int i = 0; i < dataset.size(); i++)
           {
               if(dataset.get(i).getP_aff().equals("democrat"))
                   democrat.add(dataset.get(i));

               else
                   republicans.add(dataset.get(i));
           }

           return republicans.size() == 0 || democrat.size() == 0;
       }

       public ArrayList <Node> getSucc()
       {
           ArrayList <Node> successors = new ArrayList <Node> ();

           //no successors - leaf node
           if(noSplit())
               return successors;

           //get best question to split on
           String key = bestQuestion();

           //split them based upon features
           ArrayList <Politician> yesData = new ArrayList <Politician> ();
           ArrayList <Politician> noData = new ArrayList <Politician> ();

           for(Politician inDataSet: this.dataset)
           {
               //if true, it's a yes instance
               if(inDataSet.getFeatures().get(key))
               {
                   yesData.add(inDataSet);
               }

               //else a no instance
               else
                   noData.add(inDataSet);
           }

           //keys we already used
           ArrayList <String> copyIgnoreStrings = new ArrayList <String> ();

           for(String ignore: ignoreStrings)
           {
               copyIgnoreStrings.add(ignore);
           }

           //used this key
           copyIgnoreStrings.add(key);

           //create nodes
           Node yesNode = new Node(yesData, copyIgnoreStrings);

           Node noNode = new Node(noData, copyIgnoreStrings);

           //return
           successors.add(yesNode);
           successors.add(noNode);

           return successors;
       }
}
