package tests;

import files.Payloads;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import reusableMethods.Reusable;

public class ParseComplexJsonPayload {

    JsonPath js;
    int purAmount;

    @BeforeMethod
    public void parseComplexJson(){
        js = Reusable.rawToJson(Payloads.complexJsonPayload());
        purAmount = js.getInt("dashboard.purchaseAmount");
    }



    @Test
    public void printNumberOfCourses(){

        Assert.assertEquals(js.getInt("courses.size()"),3);
        //size is applied only on array

    }

    @Test
    public void printPurchaseAmount(){
        Assert.assertEquals(js.getInt("dashboard.purchaseAmount"),910);
    }

    @Test
    public void printTitleOfTheFirstCourse(){
        Assert.assertEquals(js.getString("courses[0].title"),"Selenium Python");
    }
    @Test
    public void printAllCourseTitlesAndTheirPrices(){
        for(int i=0; i<js.getInt("courses.size()"); i++){
            System.out.println(js.getString("courses["+i+"].title"));
            System.out.println(js.getInt("courses["+i+"].price"));
        }
    }

    @Test
    public void printNumberOfCopiesSoldByRPACourse(){
        for(int i=0; i<js.getInt("courses.size()"); i++){
            if(js.getString("courses["+i+"].title").equals("RPA")){
                System.out.println(js.getInt("courses["+i+"].copies"));
                break;
            }
        }
    }

    @Test
    public void sumOfAllCoursePricesMatchesPurchaseAmount(){
        int sum =0;
        for(int i=0; i<js.getInt("courses.size()"); i++){

            sum = sum + ((js.getInt("courses["+i+"].price"))*(js.getInt("courses["+i+"].copies")));

        }
        Assert.assertEquals(sum, purAmount);
    }


}
