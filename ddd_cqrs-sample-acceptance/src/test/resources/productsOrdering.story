Customer orders products

Narrative:
In order to buy interesting products
As a customer
I want to browse the available products, pick the ones that I like and order them

Scenario: client puts some products into baskets and checks out
Given I am on home page
When I add some product to basket
And Wait
Then Basket should have 1 item
When I checkout
Then I should be on the order confirmation page with 1 product
When I confirm the order
Then I should be on the home page

Scenario: client filters products before adding them to basket
Given I am on home page
When I search for products with 'electronic' keyword
Then All visible products should have 'electronic' in their name

Scenario: client can sort products by price
Given I am on home page
When I sort products by name
Then All visible products should be sorted ascending by name
