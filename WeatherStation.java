//-------------------------------------------------------------------------
/**
 * This class represents the basic statistics collected by one weather 
 * observation station.
 *
 *  @author Nana Yaw Barimah Oteng
 *  @version 2021.04.17
 */
public class WeatherStation
{
    //~ Fields ................................................................
    private double[] rainTot; 
    private int[] dailyRec;
    private String stationID; 



    //~ Constructor ...........................................................

    // ----------------------------------------------------------
    /**
     * Initializes a newly created WeatherStation object.
     * @param id is the weather ID for the weather station.
     */
    public WeatherStation(String id)
    {
        super();
        stationID = id;
        rainTot = new double[13];
        dailyRec = new int[13];
        /*# Do any work to initialize your class here. */
    }


    //~ Methods ...............................................................
    /**
     * Returns the weather station ID for this weather station.
     * @return returns the id of a weatherStation.
     */
    public String getId() 
    {
        return stationID; 
    }
    
    /**
     * Record the information from one daily summary line in a 
     * data file, which adds the rainfall (a double) to the month 
     * (an integer from 1-12 indicating the month of the daily report).
     * @param month is the month of rainfall.
     * @param rainfall is the amount of rainfall being added to the 
     * total rainfall.
     */
    public void recordDailyRain(int month, double rainfall) 
    {
        rainTot[month] = rainTot[month] + rainfall;
        dailyRec[month]++;
        /* adds to the total rainfall in the specified month
         of the rainTot[] array. */
    }
    
    /**
     * Returns the number of daily rainfall values that have been recorded 
     * for the specified month (a number 1-12). Return zero when no values 
     * have been recorded for the specified month.
     * @param month is the month of rainfall that is being checked.
     * @return returns the number of daily rainfall values that have 
     * been recorded for the specified month.
     */
    public int getCountForMonth(int month) 
    {
        return dailyRec[month];
        /* returns the total daily values of rainfall that have been 
         * recorded for each month from the dailyRec[] array.
         */
    }
    
    /**
     * Returns the average daily rainfall for the specified month 
     * (a number 1-12). This is the total rainfall across all reported 
     * daily values for that month, divided by the number of daily 
     * values that have been recorded for that month. 
     * Return -1 if no rainfall amounts have been recorded 
     * for the specified month.
     * @param month is the month of rainfall that is being checked.
     * @return returns tthe avg daily rainfall for the specified month.
     */
    public double getAvgForMonth(int month) 
    {
        if (dailyRec[month] != 0)
        {
            return rainTot[month] / dailyRec[month];
        }
        return -1.0;
    }
    
    /**
     * Returns the number of the month (a number 1-12) indicating the
     * month that had the lowest average rainfall recorded at this station. 
     * If multiple months have the same lowest rainfall average, return the 
     * earliest one (the lowest month number). If no rainfall records have 
     * been entered for any month, return the earliest month as well (1).
     * @return returns the month with the lowest avg rainfall.
     */
    public int getLowestMonth() 
    {
        int month = 1;
        double monthRain = 100000000.000;
        for (int x = 1; x < 13; x++)
        {
            if (getAvgForMonth(x) != -1.0 && getAvgForMonth(x) < monthRain)
            {
                monthRain = getAvgForMonth(x);
                month = x;
            }
        }
        return month;
    }
}
