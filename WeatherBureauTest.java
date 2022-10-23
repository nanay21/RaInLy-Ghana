import student.micro.*;
import static org.assertj.core.api.Assertions.*;
//import java.util.Scanner;
import java.io.*;
//import java.io.PrintWriter;
//import java.util.*;
// -------------------------------------------------------------------------
/**
 *  This testclass checks all the methods in the WeatherBureau class to ensure
 *  they work as expected.
 *
 *  @author Nana Yaw Barimah Oteng
 *  @version 2021.04.17
 */
public class WeatherBureauTest
    extends TestCase
{
    //~ Fields ................................................................


    //~ Constructor ...........................................................

    // ----------------------------------------------------------
    /**
     * Creates a new WeatherBureauTest test object.
     */
    public WeatherBureauTest()
    {
        // The constructor is usually empty in unit tests, since it runs
        // once for the whole class, not once for each test method.
        // Per-test initialization should be placed in setUp() instead.
    }


    //~ Methods ...............................................................

    // ----------------------------------------------------------
    /**
     * Sets up the test fixture.
     * Called before every test case method.
     */
    public void setUp()
    {
        /*# Insert your own setup code here */
    }


    // ----------------------------------------------------------
    /*# Insert your own test methods here */
    /**
     * Checks that recordDailySummary() records a line, when
     * the rainfall is not -1. It also checks that a new WeatherStation is 
     * created if the StationId, is a new one.
     */
    public void testRecordDailySummary()
    {
        String line = "KE000063612 3.117 35.617 515 2/10/16 0.04 87 98 73";
        WeatherBureau bureau = new WeatherBureau();
        
        bureau.recordDailySummary(line);
        double rainfall = bureau.getStation("KE000063612").getAvgForMonth(2);
        
        assertThat(rainfall).isEqualTo(0.04, within(0.001));
    }
    
    /**
     * Checks that recordDailySummary() records a line, when
     * the rainfall is not -1. It also checks that a new WeatherStation is 
     * only recorded, if it already existed.
     */
    public void testRecordDailySummaryOldId()
    {
        String line = "KE000063612 3.117 35.617 515 2/10/16 0.04 87 98 73";
        String line1 = "KE000063612 3.117 35.617 515 2/10/16 1.04 87 98 73";
        
        WeatherBureau bureau = new WeatherBureau();
        bureau.recordDailySummary(line);
        bureau.recordDailySummary(line1);
        double rainfall = bureau.getStation("KE000063612").getAvgForMonth(2);
        
        assertThat(rainfall).isEqualTo(0.54, within(0.001));
    }
    
    /**
     * Checks that recordDailySummary() does not record a line, when
     * the rainfall is -1.
     */
    public void testRecordDailySummaryNeg1()
    {
        String line = "KE000063612 3.117 35.617 515 2/10/16 -1.0 87 98 73";
        WeatherBureau bureau = new WeatherBureau();
        bureau.recordDailySummary(line);
        //double rainfall = bureau.getStation("KE000063612").getAvgForMonth(2);
        
        assertThat(bureau.getStation("KE000063612")).isEqualTo(null);
    }
    
    /**
     * Checks that recordDailySummaries() records all summaries at once
     * when the rainfall is not -1.
     */
    public void testRecordDailySummaries()
    {
        WeatherBureau bureau = new WeatherBureau();
        setIn(
            "KE000063612 3.117 35.617 515 2/10/16 0.04 87 98 73",
            "KE000063613 3.117 35.617 515 2/10/16 -1.0 87 98 73",
            "KE000063615 3.117 35.617 515 2/10/16 1.00 87 98 73",
            "KE000063615 3.117 35.617 515 1/10/16 3.00 87 98 73",
            "KE000063615 3.117 35.617 515 1/10/16 2.00 87 98 73"
        );
        bureau.recordDailySummaries(in());
        
        double rainfall = bureau.getStation("KE000063612").getAvgForMonth(2);
        double rainfall1 = bureau.getStation("KE000063615").getAvgForMonth(2);
        double rainfall1a = bureau.getStation("KE000063615").getAvgForMonth(1);
        assertThat(rainfall).isEqualTo(0.04, within(0.001));
        assertThat(rainfall1).isEqualTo(1.00, within(0.001));
        assertThat(rainfall1a).isEqualTo(2.50, within(0.001));
    }
    
    /**
     * Checks that getStation() returns the Weather Object of the
     * specified id using the map.
     */
    public void testGetStation()
    {
        WeatherBureau bureau = new WeatherBureau();
        String line = "KE000063612 3.117 35.617 515 2/10/16 0.04 87 98 73";
        bureau.recordDailySummary(line);
        assertThat(bureau.getStation("KE000063612").getId()).isEqualTo(
            "KE000063612");
        assertThat(bureau.getStation("KE000063613")).isEqualTo(null);
    }
    
    /**
     * Checks that lowestStation(month) returns the lowest Station
     * in the month of January 
     */
    public void testLowestStationinMonth()
    {
        WeatherBureau bureau = new WeatherBureau();
        setIn(
            "KE000063612 3.117 35.617 515 1/10/16 -1.00 87 98 73",
            "KE000063613 3.117 35.617 515 1/10/16 4.00 87 98 73",
            "KE000063614 3.117 35.617 515 1/10/16 -1.00 87 98 73",
            "KE000063615 3.117 35.617 515 1/10/16 2.50 87 98 73",
            "KE000063616 3.117 35.617 515 1/10/16 0.01 87 98 73",
            "KE000063617 3.117 35.617 515 1/10/16 2.01 87 98 73"
        );
        bureau.recordDailySummaries(in());
        assertThat(bureau.lowestStation(1).getId()).isEqualTo("KE000063616");
    }
    
    /**
     * Checks that lowestStation(month) returns Null when
     * all summaries for that month are -1.
     */
    public void testLowestStationinMonthNull()
    {
        WeatherBureau bureau = new WeatherBureau();
        setIn(
            "KE000063612 3.117 35.617 515 1/10/16 -1.00 87 98 73",
            "KE000063613 3.117 35.617 515 1/10/16 -1.00 87 98 73",
            "KE000063614 3.117 35.617 515 1/10/16 -1.00 87 98 73"
        );
        bureau.recordDailySummaries(in());
        assertThat(bureau.lowestStation(1)).isEqualTo(null);
    }
    
    /**
     * Checks that lowestStation(month) returns Null when there are
     * no summaries for that month.
     */
    public void testLowestStationinMonthNeg1()
    {
        WeatherBureau bureau = new WeatherBureau();
        setIn(
            "KE000063612 3.117 35.617 515 2/10/16 -1.0 87 98 73",
            "KE000063613 3.117 35.617 515 2/10/16 -1.0 87 98 73",
            "KE000063614 3.117 35.617 515 2/10/16 -1.00 87 98 73"
        );
         
        bureau.recordDailySummaries(in());
        assertThat(bureau.lowestStation(1)).isEqualTo(null);
    }
    
    /**
     * Checks that lowestStation(month) returns Null when there are
     * no summaries for that month.
     */
    public void testLowestStationinMonthNull1()
    {
        WeatherBureau bureau = new WeatherBureau();
        assertThat(bureau.lowestStation(1)).isEqualTo(null);
    }
    
    /**
     * Checks that lowestStation() returns the Weather Object of the 
     * Station with the lowest average rainfall in the year. This test
     * should return January as the month(1).
     */
    public void testLowestStationinYear()
    {
        WeatherBureau bureau = new WeatherBureau();
        setIn(
            "KE000063612 3.117 35.617 515 10/10/16 1.54 87 98 73",
            "KE000063613 3.117 35.617 515 2/10/16 1.0 87 98 73",
            "KE000063614 3.117 35.617 515 5/10/16 1.00 87 98 73",
            "KE000063615 3.117 35.617 515 4/10/16 3.00 87 98 73",
            "KE000063616 3.117 35.617 515 6/10/16 2.00 87 98 73",
            "KE000063617 3.117 35.617 515 7/10/16 0.04 87 98 73",
            "KE000063618 3.117 35.617 515 1/10/16 1.04 87 98 73",
            "KE000063619 3.117 35.617 515 3/10/16 2.04 87 98 73",
            "KE000063620 3.117 35.617 515 8/10/16 3.04 87 98 73",
            "KE000063621 3.117 35.617 515 9/10/16 4.04 87 98 73",
            "KE000063622 3.117 35.617 515 11/10/16 5.04 87 98 73",
            "KE000063623 3.117 35.617 515 12/10/16 6.04 87 98 73"
        );
        bureau.recordDailySummaries(in());
        assertThat(bureau.lowestStation().getId()).isEqualTo("KE000063617");
    }
}
