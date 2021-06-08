Feature: listing and editing
	
	Scenario: List no dressShops
  Given The database is empty
  When The User is on HomePage
	Then The message "No dressShops" must be shown
	
	Scenario: List current dressShop
  Given Some dressShops are in the database
  When The User is on HomePage
  Then The table must show the dressShop with name "ds1" and target price "20"
  And The table must show the dressShop with name "ds2" and target price "30"