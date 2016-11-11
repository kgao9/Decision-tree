import java.util.*;

class testPolitician
{
    private String name;
    Hashtable <String, Boolean> features = new Hashtable <String, Boolean> ();
    ArrayList <String> keys = new ArrayList <String> ();

    public testPolitician(ArrayList <String> keys, boolean [] features, String name)
    {
        this.name = name;

        if(keys.size() != features.length)
        {
            throw new IllegalArgumentException();
        }

        for(int i = 0; i < keys.size(); i++)
        {
            this.keys.add(keys.get(i));

            this.features.put(keys.get(i), features[i]);
        }
    }

    public String toString()
    {
        String returnString = name;
        for(int i = 0; i < keys.size(); i++)
        {
             String key = keys.get(i);
             boolean feature = features.get(key);

             if(feature)
                 returnString += " y";
             
             else
                 returnString += " n";

             //returnString += ", " + key + " " + feature;
        } 

        return returnString;
    }

    public String getName()
    {
        return this.name;
    }

    public Hashtable <String, Boolean> getFeatures()
    {
        return this.features;
    }

    public ArrayList <String> getKeys()
    {
        return this.keys;
    }
}
