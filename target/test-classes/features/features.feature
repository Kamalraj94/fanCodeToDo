Feature: Validation of task completion by FanCode City


  @SmokeTest
    Scenario: To verify whether All the users of City `FanCode` should have more than half of their todos task completed.
      Given User has the todo tasks
      And User belongs to the city FanCode
      Then User Completed task percentage should be greater than 50 percentage