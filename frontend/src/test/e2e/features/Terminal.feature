Feature: Terminal functions

Background:
    When I login as user "1" and password "1"

Scenario: Buy items
    When I add an item with id "1"
    Then I click the button "buy"
    And a notification appears