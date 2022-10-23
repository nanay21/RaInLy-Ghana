import student.micro.*;
import static org.assertj.core.api.Assertions.*;
 
// -------------------------------------------------------------------------
/**
 *  Ensures that all the methods in the WeatherStation Class
 *  are functioning as expected. 
 *
 *  @author Nana Yaw Barimah Oteng 
 *  @version 2021.04.17
 */
public class WeatherStationTest
    extends TestCase
{
    //~ Fields ................................................................


    //~ Constructor ...........................................................

    // ----------------------------------------------------------
    /**
     * Creates a new WeatherStationTest test object.
     */
    public WeatherStationTest()
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
    /**
     * Tests GetID to see if a getID() returns the appropriate String
     * value.
     * 
     */
    public void testGetId()
    {
        WeatherStation newY = new WeatherStation("AAC904846");
        assertThat(newY.getId()).isEqualTo("AAC904846");
    }
    
    /**
     * Tests that getAvgForMonth() returns 400.00 for January, 
     * when the daily recorded value is 3 and the total rainfall
     * is 1200. It also ensures that the method returns -1, when no
     * rainfall values have been recorded.
     * 
     */
    public void testGetAvgForMonth()
    {
        WeatherStation newY = new WeatherStation("AAC904846");
        newY.recordDailyRain(1, 200.0);
        newY.recordDailyRain(1, 200.0);
        newY.recordDailyRain(1, 800.0);
        assertThat(newY.getAvgForMonth(1)).isEqualTo(400.00, within(0.001));
        assertThat(newY.getAvgForMonth(2)).isEqualTo(-1.0, within(0.001));
    }
    
    /**
     * Tests that getCountForMonth(month) returns 6 for the count 
     * of daily recorded values for January. 
     * 
     */
    public void testGetCountForMonth()
    {
        WeatherStation newY = new WeatherStation("AAC904846");
        newY.recordDailyRain(1, 200.0);
        newY.recordDailyRain(1, 200.0);
        newY.recordDailyRain(1, 800.0);
        newY.recordDailyRain(1, 200.0);
        newY.recordDailyRain(1, 200.0);
        newY.recordDailyRain(1, 800.0);
        assertThat(newY.getCountForMonth(1)).isEqualTo(6);
    }
    
    /**
     * Tests that getLowestMonth() returns the month
     * with the lowest average rainfall, when the avg rainfall isn't 
     * for -1.
     * 
     */
    public void testGetLowestMonth()
    {
        WeatherStation newY = new WeatherStation("AAC904846");
        newY.recordDailyRain(1, 200.0);
        newY.recordDailyRain(2, 300.0);
        newY.recordDailyRain(3, 800.0);
        newY.recordDailyRain(4, 200.0);
        newY.recordDailyRain(5, 100.0);
        newY.recordDailyRain(6, 800.0);
        assertThat(newY.getLowestMonth()).isEqualTo(5);
    }
}
