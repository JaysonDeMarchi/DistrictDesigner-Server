import json
import sys
from shapely.geometry import shape

class Precinct:
	def __init__(self,precinctId,polygon):
		self.precinctId = precinctId
		self.polygon = polygon
		self.adjPrecincts = []

def updateAdjPrecincts(a,b):
	if a==b or b.precinctId in a.adjPrecincts:
		return
	if a.polygon.intersects(b.polygon):
		a.adjPrecincts.append(b.precinctId)
		b.adjPrecincts.append(a.precinctId)
	return

inputFile = sys.argv[1]
outputFile = sys.argv[2]
with open(inputFile,'r') as fPrecinct:
	data = json.load(fPrecinct)['features']
	precincts = list(map(lambda item : Precinct(item['properties']['GEOID10'],shape(item['geometry'])),data))
fPrecinct.close()

with open(outputFile,'w') as fOutput:
	jsonStrings = []
	for precinctA in precincts:     #precinctA is the precinct needs to find adjPrecinct
		for precinctB in precincts:	#precinctB is a precinct in the whole precincct list
			updateAdjPrecincts(precinctA,precinctB)
		jsonStrings.append({"id":precinctA.precinctId,"adjPrecincts":str(precinctA.adjPrecincts)})
	fOutput.write(json.dumps(jsonStrings,indent=0))
fOutput.close()


