package stepdefination;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.jayway.restassured.response.Response;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import utilities.APIutils.ApiRequests;
import utilities.userDetailsPojo.Todo;
import utilities.userDetailsPojo.User;


import java.io.IOException;
import java.util.List;

public class StepDefination {

    String firstPerson ;
    String secondPerson ;
    String firstPersonUUID ;
    String secondPersonUUID ;

    String name ;
    String uuid ;
    String uuidForMessage ;

    Faker faker ;
    ApiRequests requestUtils ;
    List<User> users ;
    int userId ;

    public StepDefination() throws IOException {
        faker = new Faker();
        requestUtils = new ApiRequests();
    }


    @Given("User has the todo tasks")
    public void User_has_the_todo_tasks(){
        Response response = requestUtils.getRequestforUserDetails();
        users = response.jsonPath().getList("", User.class);
    }

    @And("User belongs to the city FanCode")
    public void User_belongs_to_the_city_FanCode(){
        for (User user : users) {
            double lat = Double.parseDouble(user.getAddress().getGeo().getLat());
            double lng = Double.parseDouble(user.getAddress().getGeo().getLng());
            if (lat > -40 && lat < 5 && lng > 5 && lng < 100) {
                userId = user.getId();
            }
        }
    }


    @Then("User Completed task percentage should be greater than (\\d+) percentage")
    public void validating_task_completion_percentage(int percentage) throws Exception {
        Response response = requestUtils.getRequestForTodoList(userId);
        ObjectMapper mapper = new ObjectMapper();
        List<Todo> todoList = mapper.readValue((JsonParser) response, new TypeReference<List<Todo>>() {});
           int totalTasks = todoList.size();
           long completedCount = todoList.stream()
                   .filter(Todo::isCompleted)  // Filter tasks that are completed
                   .count();
           double percentageCompleted = (double) completedCount / totalTasks * 100;
           if (percentageCompleted >= 50) {
               System.out.println("Validation Passed: At least 50% tasks are completed.");
           } else {
              throw new Exception("precentage is less than 50%");
           }
    }

}
