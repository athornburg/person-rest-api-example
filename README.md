# person-rest-api-example
![alt tag](https://travis-ci.org/athornburg/person-rest-api-example.svg?branch=master)
###A Simple Rest API of People -- A programming exercise

## Building ##
	```
	./gradlew :bootrun
	```

## Running the Tests ##
	```
	./gradlew check
	```

## Endpoints ##
  * GET /family/{familyId}
  * POST /family/
  * DELETE /family/{familyId}
  * PUT /family/{familyId}
  * GET /family/findBySurname/{surname}
  * GET /family/findShortestGeneVarience/{familyId}
 		* Simply demonstrates the power of handling family as graph
 		* Probably needs more testing -- but cool
  * GET /person/{personId}
  * POST /person/
  * DELETE /person/{personId}
  * PUT /person/{personId}
  * GET /person/findByFirstName{}

  ### Person JSON Example ###

  ```
  {
  "id": 1,
  "firstName": "Test",
  "lastName": "Person",
  "age": 57,
  "sex": {
    "male": true,
    "female": false
  },
  "email": "testperson1@gmail.com",
  "phoneNumber": "740-526-6225",
  "address": {
    "id": 1,
    "person": null,
    "streetAddress": "555 North West",
    "country": "United States",
    "city": "Kanye Ave.",
    "zip": 60606,
    "people": null
  }
}
```

  ## Family JSON Example ###
 	```
 	   {
  "id": 1,
  "surname": "Person",
  "people": [
    {
      "id": 1,
      "origin": {
        "id": 2,
        "firstName": "Test2",
        "lastName": "Person",
        "age": 88,
        "sex": {
          "male": true,
          "female": false
        },
        "email": "testperson1@gmail.com",
        "phoneNumber": "740-526-6225",
        "address": {
          "id": 1,
          "person": null,
          "streetAddress": "555 North West",
          "country": "United States",
          "city": "Kanye Ave.",
          "zip": 60606,
          "people": null
        }
      },
      "relative": {
        "id": 1,
        "firstName": "Test",
        "lastName": "Person",
        "age": 57,
        "sex": {
          "male": true,
          "female": false
        },
        "email": "testperson1@gmail.com",
        "phoneNumber": "740-526-6225",
        "address": {
          "id": 1,
          "person": null,
          "streetAddress": "555 North West",
          "country": "United States",
          "city": "Kanye Ave.",
          "zip": 60606,
          "people": null
        }
      },
      "relName": "Father",
      "dnaDifference": 0.23
    }
  ]
}
```

### Kinship JSON Example ###
```
{
  "id": 1,
  "origin": {
    "id": 2,
    "firstName": "Test2",
    "lastName": "Person",
    "age": 88,
    "sex": {
      "male": true,
      "female": false
    },
    "email": "testperson1@gmail.com",
    "phoneNumber": "740-526-6225",
    "address": {
      "id": 1,
      "person": null,
      "streetAddress": "555 North West",
      "country": "United States",
      "city": "Kanye Ave.",
      "zip": 60606,
      "people": null
    }
  },
  "relative": {
    "id": 1,
    "firstName": "Test",
    "lastName": "Person",
    "age": 57,
    "sex": {
      "male": true,
      "female": false
    },
    "email": "testperson1@gmail.com",
    "phoneNumber": "740-526-6225",
    "address": {
      "id": 1,
      "person": null,
      "streetAddress": "555 North West",
      "country": "United States",
      "city": "Kanye Ave.",
      "zip": 60606,
      "people": null
    }
  },
  "relName": "Mother",
  "dnaDifference": 0.23
}
```
