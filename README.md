# District Designer

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

## Update Precincts

|Method|URL|
|--|--|
|GET|`/updatePrecincts/`|

- **Successful Response:**
	- Code: 200
	- Content: 
	```json
	[
            {
                precinctId: 234,
                oldDistrictId: 2,
                newDistrictId: 3,
                successStatus: true,
            },{
                precinctId: 123,
                oldDistrictId: 1,
                newDistrictId: 2,
                successStatus: false,
            },
            . . .
        ]
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
