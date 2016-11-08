import java.util.*;

public class Car
{
    String mpg;
    int cylinders;
    int displacement;
    int horsepower;
    int weight;
    double acceleration;
    int modelyear;
    String maker;
    
    public Car(String mpg, int cyl, int dis, int hp, int wt, double accel, int year, String maker)
    {
         this.mpg = mpg;
         this.cylinders = cyl;
         this.displacement = dis;
         this.horsepower = hp;
         this.weight = wt;
         this.acceleration = accel;
         this.modelyear = year;
         this.maker = maker;
    }

    public String getMpg()
    {
        return this.mpg;
    }

    public int getCylinders()
    {
         return this.cylinders;
    }

    public int getDisplacement()
    {
        return this.displacement;
    }

    public int getHorsePower()
    {
        return this.horsepower;
    }   

    public int getWeight()
    {
        return this.weight;
    }

    public int getAcceleration()
    {
        return this.acceleration;
    }

    public int getModelYear()
    {
        return this.modelyear;
    }

    public int getMaker()
    {
        return this.maker;
    }
}
