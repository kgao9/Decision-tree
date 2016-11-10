import java.util.*;

class Politician
{
    private String name;
    private String p_aff;
    Hashtable <String, Boolean> features = new Hashtable <String, Boolean> ();
    ArrayList <String> keys = new ArrayList <String> ();

    public Politician(ArrayList <String> keys, boolean [] features, String p_aff, String name)
    {
        //validate p_aff
        if(!p_aff.equals("democrat") && !p_aff.equals("republican"))
        {
            throw new IllegalArgumentException();
        }

        this.p_aff = p_aff;

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
        returnString += ": p_aff " + this.p_aff;

        for(int i = 0; i < keys.size(); i++)
        {
             String key = keys.get(i);
             String feature = String.valueOf(features.get(key));

             returnString += ", " + key + " " + feature;
        } 

        return returnString;
    }
}
