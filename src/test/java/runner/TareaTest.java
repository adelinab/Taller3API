package runner;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class TareaTest {

    JSONObject body;
    @BeforeEach
    public void init(){
        body = new JSONObject();
        body.put("Content","Item "+new Date().getTime());
    }

    @Test
    public void verifyCRUDItemTest(){

        // create
        Response response = given()
                .auth()
                .preemptive()
                .basic("aabravoc@gmail.com","12345")
                .body(body.toString())
                .log()
                .all()
                .when()
                .post("https://todo.ly/api/items.json");
        response.then()
                .log()
                .all()
                .statusCode(200)
                .body("Content",equalTo(body.get("Content")));

        int id=response.then().extract().path("Id");

        // read
        given()
                .auth()
                .preemptive()
                .basic("aabravoc@gmail.com","12345")
                .log()
                .all()
        .when()
                .get("https://todo.ly/api/items/"+id+".json")
        .then()
                .log()
                .all()
                .statusCode(200)
                .body("Content",equalTo(body.get("Content")));

        // update
        body.put("Content","UPDATED"+new Date().getTime());
        body.put("Checked",true);

        given()
                .auth()
                .preemptive()
                .basic("aabravoc@gmail.com","12345")
                .body(body.toString())
                .log()
                .all()
        .when()
                .put("https://todo.ly/api/items/"+id+".json")
        .then()
                .log()
                .all()
                .statusCode(200)
                .body("Checked",equalTo(body.get("Checked")))
                .body("Content",equalTo(body.get("Content")));

/*
        // delete

        given()
                .auth()
                .preemptive()
                .basic("aabravoc@gmail.com","12345")
                .log()
                .all()
        .when()
                .delete("https://todo.ly/api/items/"+id+".json")
        .then()
                .log()
                .all()
                .statusCode(200)
                .body("Content",equalTo(body.get("Content")))
                .body("Deleted",equalTo(true));
        */
    }
}
