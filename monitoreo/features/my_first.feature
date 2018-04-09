Feature: Login validation.
  Login Validate.

  Scenario: Login Validate Success
    Given I wait for the "SignInActivity" screen to appear
    When I enter text "ges@com.pe" into field with id "emailEditText"
    When I enter text "123456" into field with id "passwordEditText"
    And I press "signInButton"
    Then I should see "Login Validate Success"