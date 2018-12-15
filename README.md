# District Designer

## Account Creation

User sends a username and password to create an account.

|Method|URL|
|--|--|
|POST|`/CreateAccount/`|

|param|Type|required|
|:--:|:--:|:--:|
|`username`|`String`|yes|
|`password`|`String`|yes|
- **Successful Response:**
	- Code: 200
	- Content:
	```json
	{
            "USER_CREATED": true
	}
	```

- **Failed Response:**
	- Code: 200
	- Content:
	```json
	{
            "USER_CREATED": false,
            "ERROR_MESSAGE": "Error: User could not be created"
        }
	```

## Constitution Texts and Information

Returns the constitution texts specifically involving redistricting associated with the provided state.

|Method|URL|
|--|--|
|GET|`/Constitution?shortName=<shortName>`|

- **Successful Response:**
	- Code: 200
	- Content:
	```json
	{
            CONSTITUTION_REQUIREMENTS: {
                shortName:"WI",
                equalPopulation:"0.1",
                compact:"PREFERED",
                countyLine:"PREFERED",
                contiguous:"REQUIRED",
                preserveCommunitiesIncumbants:"OPTIONAL"
            },
            CONSTITUTION_TEXT: [
                {
                    jurisdiction:"state",
                    shortName:"WI",
                    document:"State Constitution",
                    office:"house",
                    article:"IV",
                    section:"4",
                    body:". . .",
                    notes:"NA"
                },{
                    jurisdiction:"federal",
                    shortName:"USA",
                    document:"US Constitution",
                    office:"all",
                    article:"V",
                    section:"2",
                    body:". . .",
                    notes:"NA"
                }
            ]
        }
        ```

## Start Algorithm

Begin redistricting the provided state using the provided algorithm and the provided metrics

|Method|URL|
|--|--|
|POST|`/StartAlgorithm/`|

|param|Type|required|
|:--:|:--:|:--:|
|`shortName`|`String`|yes|
|`algoType`|`String`|yes|
|`weights`|`Map<Metric,Float>`|yes|
- **Successful Response:**
	- Code: 200
	- Content:
	```json
	{
		algorithmStarted: true
	}
	```

## Stop Algorithm

Stop the currently running algorithm. Discards the algorithm state on the server side.

|Method|URL|
|--|--|
|GET|`/StopAlgorithm/`|

- **Successful Response:**
	- Code: 200
	- Content:
	```json
	{
		algorithmStopped: true
	}
	```
