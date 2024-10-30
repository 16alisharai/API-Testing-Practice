package testData;

import org.testng.annotations.DataProvider;

import java.util.Random;

public class LibraryAPITestData {

    Random rd = new Random();

    @DataProvider(name="lapiData")
    public Object[][] testDataLibraryAPI(){
        return new Object[][]{
                {"abcs", rd.nextInt(1000)}, {"dfgh", rd.nextInt(1000)}, {"@@##$$", rd.nextInt(1000)}

        };
    }
}
