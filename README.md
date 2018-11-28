## Start Algorithm

Begin redistricting the provided state using the provided algorithm and the provided metrics

|Method|URL|
|--|--|
|POST|`/startAlgorithm/`|

|param|Type|required|
|:--:|:--:|:--:|
|`shortName`|`String`|yes|
|`algoType`|`String`|yes|
|`weights`|`HashMap<Metric,Float>`|yes|
- **Successful Response:**
	- Code: 200
	- Content: 
	```json
	{
		algorithmStarted: true
	}
	```
