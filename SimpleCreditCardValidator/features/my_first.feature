Feature: Validación de tarjeta de crédito.
  Los números de tarjeta de crédito deben tener exactamente 16 caracteres.

  Scenario: El número de la tarjeta de crédito es demasiado largo
    Given I wait for the "MainActivity" screen to appear
    When I enter text "99999999999999999" into field with id "creditCardNumberText"
    And I press "validateButton"
    Then I should see "El número de la tarjeta de crédito es demasiado largo"
	
	Scenario: El número de la tarjeta de crédito es demasiado corto
		Given I wait for the "MainActivity" screen to appear
		When I enter text "9999" into field with id "creditCardNumberText"
		And I press "validateButton"
		Then I should see "El número de la tarjeta de crédito es demasiado corto"
	
	Scenario: El número de la tarjeta de crédito es válido
		Given I wait for the "MainActivity" screen to appear
		When I enter text "9999999999999999" into field with id "creditCardNumberText"
		And I press view with id "validateButton"
		Then I should see "El número de la tarjeta de crédito es válido"
	