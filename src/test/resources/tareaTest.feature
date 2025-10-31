@Clean
Feature: Project API
  Scenario: Como usuario quiero hacer el CRUD de un item por API

    Given i have access to Todo.ly

    # create
    When i send a POST request to "/api/items.json" with body
    """
    {
      "Content": "itemTest"
    }
    """
    Then response code is 200
    And the attribute string "Content" is "itemTest"
    And i save the value of "Id" in the variable "ID"

    # update
    When i send a PUT request to "/api/items/ID.json" with body
    """
    {
      "Content": "itemTestUpdate"
    }
    """
    Then response code is 200
    And the attribute string "Content" is "itemTestUpdate"

    # read
    When i send a GET request to "/api/items/ID.json" with body
    """
    """
    Then response code is 200
    And the attribute string "Content" is "itemTestUpdate"

    # delete
    When i send a DELETE request to "/api/items/ID.json" with body
    """
    """
    Then response code is 200
    And the attribute String "Content" is "itemTestUpdate"
    And the attribute boolean "Deleted" is "true"