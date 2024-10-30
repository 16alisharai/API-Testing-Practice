package tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.*;

public class CreateABugOnJiraTest {

//    Adding files:
//    Manual:
//    form parameter - body - form-data- (text/file)- choose file - key and value - key(file) and value - upload file.
//    Automation:
//    Multipart(file, file object (path))


    @BeforeMethod
    public void accessJiraApi(){
        RestAssured.baseURI="https://alisharai1627.atlassian.net/";
    }

    @Test
    public void createAnIssueOnJira(){

        String response = given().header("Content-Type","application/json")
                .header("Authorization","Basic YWxpc2hhcmFpMTYyN0BnbWFpbC5jb206QVRBVFQzeEZmR0YwNlA2Ul9oYVQ1RkhyMW43Z0lCMkhTOVk0dko5ZEJpdUduRHZydUljdjl4ODdHVkpLR2MtdzJpWVVjelJMQnBQRFk4ZUdCZnpVRjNDS093Q1dBWHoxRzgzRDFCYjBITWZUbmpTZWs3NFo0U1ZwWDVWYTl0THczc0RkNFV1dDRuSGZMbndGRXFKbVBqc2hTbVJjNGwwOTltelRZeXY2Qi1MeEhEc213WllwMVdVPTU4N0YzN0JC")
                .body("{\n" +
                        "    \"fields\": {\n" +
                        "       \"project\":\n" +
                        "       {\n" +
                        "          \"key\": \"AR\"\n" +
                        "       },\n" +
                        "       \"summary\": \"Test Bug2\",\n" +
                        "       \"issuetype\": {\n" +
                        "          \"name\": \"Bug\"\n" +
                        "       }\n" +
                        "   }\n" +
                        "}")
                .when().post("rest/api/3/issue")
                .then().log().all().assertThat().statusCode(201).extract().response().asString();

        JsonPath js = new JsonPath(response);
        String id = js.get("id");

        given().pathParams("key",id)
                .header("X-Atlassian-Token","no-check")
                .header("Authorization","Basic YWxpc2hhcmFpMTYyN0BnbWFpbC5jb206QVRBVFQzeEZmR0YwNlA2Ul9oYVQ1RkhyMW43Z0lCMkhTOVk0dko5ZEJpdUduRHZydUljdjl4ODdHVkpLR2MtdzJpWVVjelJMQnBQRFk4ZUdCZnpVRjNDS093Q1dBWHoxRzgzRDFCYjBITWZUbmpTZWs3NFo0U1ZwWDVWYTl0THczc0RkNFV1dDRuSGZMbndGRXFKbVBqc2hTbVJjNGwwOTltelRZeXY2Qi1MeEhEc213WllwMVdVPTU4N0YzN0JC")
                .multiPart("file",new File("/Users/alisharaizada/Downloads/APITestingPractice/src/test/Screenshots/bug.png"))
                .when().post("rest/api/3/issue/{key}/attachments")
                .then().assertThat().statusCode(200);


    }



}
