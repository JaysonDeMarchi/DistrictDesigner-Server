## Start Algorithm

Begin redistricting the provided state using the provided algorithm and the provided metrics

|Method|URL|Parameters|
|--|--|--|
|POST|`/startAlgorithm/`|`shortName=[String]algoType=[String] weights=[HashMap]`|

|param|Type|required|
|:--:|:--:|:--:|
|shortName|String|yes|
|algoType|String|yes|
|weights|HashMap|yes|
- **Successful Response:**
	- Code: 200
	- Content: 
	```json
	{
		algorithmStarted: true
	}
	```
