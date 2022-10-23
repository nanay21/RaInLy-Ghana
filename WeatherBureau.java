import java.io.*;
import java.util.*;
import student.*;
//-------------------------------------------------------------------------
/**
 * This class represents a weather service that keeps track of all the weather
 * stations. Internally, it should use a map to associate weather station IDs
 * with weather station objects.
 *
 *  @author Nana Yaw Barimah Oteng
 *  @version 2021.04.17
 */
public class WeatherBureau
{
    //~ Fields ................................................................
    private Map<String, WeatherStation> weatherMap;


 
    //~ Constructor ...........................................................

    // ----------------------------------------------------------
    /**
     * Initializes a newly created WeatherBureau object.
     */
    public WeatherBureau()
    {
        super();
        weatherMap = new TreeMap<String, WeatherStation>();
    }


    //~ Methods ...............................................................
    /**
     * Takes a single string representing a single one-line daily weather
     * summary for one day at one weather station (i.e., one line from a 
     * weather data input sources). If the rainfall amount in the text is -1, 
     * ignore the line (it is missing its rainfall data). Otherwise, record the 
     * rainfall from this daily summary in the corresponding weather station 
     * object in the bureau's map (create a new weather station object 
     * if the station ID doesn't correspond to one you've seen before).
     * @param summary is the one-line summary for one day 
     * at one weather station.
     */
    public void recordDailySummary(String summary)
    {
        Scanner inStream = new Scanner(summary);
        Scanner newStream = new Scanner(summary);
        String id = newStream.next();
        String line = "";
        for (int x = 0; x < 5; x++)
        {
            line = inStream.next();
        }
        int month = Integer.parseInt(line.substring(0, line.indexOf('/')));
        double rain = inStream.nextDouble();
        if (rain != -1.0)
        {
            if (weatherMap.containsKey(id))
            {
                weatherMap.get(id).recordDailyRain(month, rain);
            }
            else
            {
                weatherMap.put(id, new WeatherStation(id));
                weatherMap.get(id).recordDailyRain(month, rain);
            }
        }
    }
    
    /**
     * Takes a Scanner object as a parameter that represents an input data 
     * source, such as a file containing a series of daily summary records 
     * for one or more weather stations. Record all of the daily summaries 
     * from the input source.
     * @param inStream is the Scanner object that represents an input data file
     * source for a summary of multiple weather stations.
     */
    public void recordDailySummaries(Scanner inStream)
    {
        while (inStream.hasNextLine())
        {
            recordDailySummary(inStream.nextLine());
        }
        // record daily summary using maps, if rainfall isnt -1;
    }
    
    /**
     * Return the weather station object for the given weather station ID 
     * (or null, if the identifier doesn't match any weather station 
     * you've seen so far). 
     * @param id is the id of the weather station being returned.
     * @return returns the weather station object for the given weather 
     * station ID.
     */
    public WeatherStation getStation(String id)
    {
        return weatherMap.get(id);
    }
    
    /**
     * Returns the weather station that has the lowest average rainfall 
     * for the specified month (or null, if the weather bureau hasn't 
     * recorded any rainfall daily summaries for any station in the 
     * specified month).
     * @param month is the month being checked for the station with
     * the lowest avg rainfall in that month.
     * @return returns the station with lowest avg rainfall in that 
     * month.
     */
    public WeatherStation lowestStation(int month)
    {
        //return a weather Station Object with the lowest avg rainfall 
        //for the specified month
        double lowest = 10000000000.000; 
        WeatherStation station = null;
        for (String id: weatherMap.keySet())
        {
            System.out.println(getStation(id).getAvgForMonth(month));
            if (getStation(id).getCountForMonth(month) != 0 && 
                getStation(id).getAvgForMonth(month) < lowest)
            {
                lowest = getStation(id).getAvgForMonth(month); 
                station = getStation(id);
            }
        }
        return station;
    }
    
    /**
     * Returns the weather station that has the lowest average rainfall 
     * recorded for any month (1-12) (or null, if the weather bureau hasn't 
     * recorded any rainfall daily summaries for any station for any month). 
     * If multiple stations have the same lowest average rainfall amount, just 
     * return the first one you find (of those that tied for lowest). Remember, 
     * you can use the getLowestMonth() method on each station
     * to find out which 
     * month has its specific lowest average value.
     * @return returns the month with the station with 
     * lowest avg rainfall recorded
     * for any month.
     */ 
    public WeatherStation lowestStation()
    {
        // return a weather Station Object with the lowest avg rainfall
        // for all months.
        double lowest = 10000000000.0000; 
        WeatherStation station = null;
        for (String id: weatherMap.keySet())
        {
            int month = getStation(id).getLowestMonth();
            if (getStation(id).getAvgForMonth(month) < lowest)
            {
                lowest = getStation(id).getAvgForMonth(month); 
                station = getStation(id);
            }
        }
        return station;
    }
}
