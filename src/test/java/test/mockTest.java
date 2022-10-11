package test;

import com.relevantcodes.extentreports.LogStatus;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;
import service.StarwarsService;
import service.StarwarsActions;

public class mockTest extends InitTest {

    StarwarsService starwarsService = Mockito.mock(StarwarsService.class);

    StarwarsActions starwarsActions = new StarwarsActions(starwarsService);

    //Test to demonstrate mocks
    @Test
    public void testAverageStarwarsSeriesWatched(){

        test.log(LogStatus.INFO, "Mocking PeopleService methods ......");

        Mockito.when(starwarsService.getTotalPeople()).thenReturn(100);
        Mockito.when(starwarsService.getTotalCountFromStarWarsSeries()).thenReturn(1000);
        Assert.assertEquals(10, starwarsActions.getAverageStarwarsSeries());

        test.log(LogStatus.INFO, "Successfully Validated testAverageStarwarsSeriesWatched() ......");
    }

}
