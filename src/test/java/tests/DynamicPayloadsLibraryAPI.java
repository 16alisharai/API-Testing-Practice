package tests;

import files.Payloads;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import reusableMethods.Reusable;
import testData.LibraryAPITestData;

import static io.restassured.RestAssured.*;

public class DynamicPayloadsLibraryAPI {

    String isbnValue;
    int aisleValue;


    @BeforeMethod
    public void callBaseURI(){
        RestAssured.baseURI="http://216.10.245.166";

    }

    @Test(dataProvider = "lapiData", dataProviderClass = LibraryAPITestData.class)
    public void addBookAPIValidation(String isbn,int aisle){

        String response = given().log().all().header("Content_Type","application/json").body(Payloads.addBookAPIPayload(isbn, aisle))
                .when().post("/Library/Addbook.php")
                .then().assertThat().statusCode(200).extract().response().asString();

        System.out.println(response);

        JsonPath js = new JsonPath(response);
        String msg = js.get("Msg");
        String id = js.get("ID");

        Assert.assertEquals(msg,"successfully added");
        Assert.assertEquals(id,isbn+aisle);
        isbnValue=isbn;
        aisleValue=aisle;

    }

    @AfterMethod
    public void deleteAddedBook(){

        String response = given().queryParam("Content_Type", "application/json").body("{\"ID\":\""+isbnValue+aisleValue+"\"}")
                .when().post("/Library/DeleteBook.php").
                then().extract().response().asString();

        JsonPath js = Reusable.rawToJson(response);
        Assert.assertEquals(js.getString("msg"),"book is successfully deleted");

    }
}
