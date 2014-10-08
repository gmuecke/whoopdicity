@Example
Feature: Search courses
  Courses should be searchable by topic
  Search results should provide the course code

Scenario: Search by topic
    Given there are 240 courses which do not have the topic "biology"
    And there are 2 courses, A001 and B205, that each have "biology" as one of the topics
    When I search for "biology"
    Then I should see the following courses:
      | Course code |
      | A001        |
      | B205        |