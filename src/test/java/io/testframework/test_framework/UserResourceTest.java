package io.testframework.test_framework;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.http.ContentType;
import io.restassured.response.ResponseBody;
import io.testframework.test_framework.utilities.DBconnector;
import io.testframework.test_framework.utilities.JsonResourceReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Connection;
import java.sql.SQLException;

import static io.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@ActiveProfiles("test")
public class UserResourceTest {

    DBconnector dbConnector;

    @BeforeEach
    public void init(@Autowired DBconnector dBconnector) throws SQLException {
        this.dbConnector = dBconnector;
    }

    @Test
    @Disabled
    public void getAllUsers() throws JsonProcessingException, SQLException {
        loadDBWithUsers();
        when()
                .get("/api/users")
                .then().contentType(ContentType.JSON)
                .body("id",hasItem("966e7d47-21c5-4b8d-9339-c62b5cdf5b89"));
    }

    @Test
    @Disabled
    public void getUserId() throws JsonProcessingException, SQLException {
        loadDBWithUsers();
        ResponseBody response = when().get("/api/users/966e7d47-21c5-4b8d-9339-c62b5cdf5b89").body();
        assertAll(
                () -> assertThat(response.path("firstname"),equalTo("Emil")),
                () -> assertThat(response.path("lastname"),equalTo("Jonsson")),
                () -> assertThat(response.path("email"),equalTo("fake@gmail.com")),
                () -> assertThat(response.path("userAssets.size()"),is(0))
        );

    }


    private void loadDBWithUsers() throws JsonProcessingException, SQLException {
        JsonResourceReader jrr = new JsonResourceReader();
        JsonNode user1 = jrr.getJsonFileAsJsonNode("./testdata/test_users.json").get("user1");
        String insert = "insert into public.user (id,date_created,firstname,last_updated,lastname,email,registred_at)";
        String values = String.format("values(%s,%s,%s,%s,%s,%s,%s)",
                user1.get("id"),
                user1.get("dateCreated"),
                user1.get("firstname"),
                user1.get("lastUpdated"),
                user1.get("lastname"),
                user1.get("email"),
                user1.get("registredAt")).replaceAll("\"","\'");
                String dbQuery = insert+values;

        Connection conn = dbConnector.getConnection();
        dbConnector.updateQuery(conn,dbQuery);
    }


    @AfterEach
    private void dbCleanUp() throws SQLException {
        String cleanUp = String.format("delete from public.user where id = '966e7d47-21c5-4b8d-9339-c62b5cdf5b89'");
        Connection conn = dbConnector.getConnection();
        dbConnector.updateQuery(conn,cleanUp);
        dbConnector.closeConnection(conn);
    }
}
