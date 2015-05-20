@LoginExample
Feature: Authenticate User
  A user must be able to authenticate herself

Scenario: Login
   Given my username is "adam"
     And my password is "eve"
    When I enter my credentials
     And press login
    Then I am authenticated
      