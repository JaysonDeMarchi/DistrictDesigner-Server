import json
import sys
import math
from shapely.geometry import shape,Point


class Precinct:
	def __init__(self,precinctId,polygon):
		self.precinctId = precinctId
		self.polygon = polygon
		self.population = 0

class Ward:
	def __init__(self,population,polygon):
		self.population = population
		self.polygon = polygon	


precincts = []
with open('wi.json','r') as fPrecinct:
	data = json.load(fPrecinct)['features']
	precincts = list(map(lambda item : Precinct(item['properties']['GEOID10']
		,shape(item['geometry'])),data))
fPrecinct.close()

wards = []
with open('wi_2016.json','r') as fward:
	data=json.load(fward)['features']
	wards =list(map(lambda item : Ward(item['properties']['PERSONS'],shape(item['geometry'])),data))
fward.close()


for ward in wards:
	overlapPrecincts = []
	for precinct in precincts:
		if(ward.polygon.overlaps(precinct.polygon)):
			overlapPrecincts.append(precinct)
	for precinct in overlapPrecincts:
		precinct.population += math.floor(ward.population / len(overlapPrecincts))


jsonStrings = []
with open('wi_population',"w") as fOutput:
	for precinct in precincts:
		jsonStrings.append({"id":precinct.precinctId,"population":precinct.population})
	fOutput.write(json.dumps(jsonStrings,indent=0))
fOutput.close()
