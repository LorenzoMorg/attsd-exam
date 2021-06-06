Feature: DressShop editing

	Scenario: Add and Edit a new dressShop
	Given The User is on HomePage
	When The User clicks to add a new dressShop
	And Enters a dressShop with name "Nike" and target price "30"
	Then The table must show the dressShop with name "Nike" and target price "30"
	And The buttons "Edit" and "Delete" are displayed
	When The User clicks to "edit" the dressShop
	And updates the target price to "25"
	Then The table must show the dressShop with name "Nike" and target price "25"
	
	Scenario: Add and Delete a dressShop
	Given The User is on HomePage
	When The User clicks to add a new dressShop
	And Enters a dressShop with name "Adidas" and target price "20"
	Then The table must show the dressShop with name "Adidas" and target price "20"
	And The buttons "Edit" and "Delete" are displayed
	When The User clicks to "delete" the dressShop
	Then The message "No dressShops" must be shown
	
	Scenario: Edit error
	Given The User is on HomePage
	When The User tries to "edit" a not existing dressShop
	Then The message "Error" must be shown
	
	Scenario: Delete error
	Given The User is on HomePage
	When The User tries to "delete" a not existing dressShop
	Then The message "Error" must be shown

