Feature: Checkout the Subscription Package
  user can subscribe Lite, Classic or Premium package and include add ons by paying extra amount


  Background:
    Given user has logged in to application

  @checkoutFlow
  Scenario Outline: verify user can checkout the subscribed package
    When user selects country "<country>"
    And choose and verify trial plan type "<plan>"
    And user enters account credentials
    And user includes the add ons "<discovery+>" "<sports>"
    Then verify checkout details are correct
    Examples:
      | country | plan    | discovery+ | sports |
      | Kuwait  | Lite    | true       | true   |
      | Kuwait  | Classic | true       | false  |
      | Kuwait  | Premium | false      | true   |
      | KSA     | Lite    | false      | false  |
      | KSA     | Classic | true       | true   |
      | KSA     | Premium | false      | false  |
      | Bahrain | Lite    | true       | false  |
      | Bahrain | Classic | false      | true   |
      | Bahrain | Premium | true       | false  |
#

#  Reading the json file into json object
#    modify the json
#    form a request using metadata
#      getResponse-> URL=> ReqeustSPecification=>
#       receive the response
#        assert for requirements