package runner;

import factoryRequest.FactoryRequest;
import factoryRequest.RequestInformation;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import util.Config;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;

public class TareaTestUpgraded {

    Response response;
    Map<String,String> variables = new HashMap<>();
    RequestInformation info;

    @Given("i have access to Todo.ly")
    public void iHaveAccessToTodoLy() {
        info = new RequestInformation();
        String credentials = Config.user+":"+Config.password;
        info.setHeader("Authorization", Base64.getEncoder().encodeToString(credentials.getBytes()));
    }

    @When("i send a {word} request to {string} with body")
    public void iSendAPOSTRequestToWithBody(String method, String url, String body) {
        info.setUrl(Config.host+replaceVar(url)).setBody(body);
        response = FactoryRequest.make(method.toLowerCase()).send(info);
    }

    @Then("response code is {int}")
    public void responseCodeIs(int expectedResponseCode) {
        response.then()
                .statusCode(expectedResponseCode);
    }

    @And("the attribute {word} {string} is {string}")
    public void theAttributeStringIs(String type,String attribute, String expectedResult) {
        System.out.println("Expected Result: "+expectedResult);
        if (type.toLowerCase().equals("int"))
            response.then().body(attribute,equalTo(Integer.parseInt(expectedResult)));
        if (type.toLowerCase().equals("boolean"))
            response.then().body(attribute,equalTo(Boolean.parseBoolean(expectedResult)));
        if (type.toLowerCase().equals("string"))
            response.then().body(attribute,equalTo(expectedResult));
    }

    @And("i save the value of {string} in the variable {string}")
    public void iSaveTheValueOfInTheVariable(String attribute, String nameVariable) {
        variables.put(nameVariable,response.then().extract().path(attribute).toString());
        System.out.println("Variable "+nameVariable+" = "+variables.get(nameVariable));
    }

    private String replaceVar(String value){
        for (String key: variables.keySet())
            value=value.replace(key,variables.get(key));
        return value;
    }
}
