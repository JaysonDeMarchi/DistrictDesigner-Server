import json
import sys
from shapely.geometry import shape,Point

class Precinct:
	def __init__(self,precinctId,center):
		self.precinctId = precinctId
		self.center = center

class District:
	def __init__(self,districtId,polygon):
		self.districtId = districtId
		self.polygon = polygon	

inputFile = sys.argv[1]
outputFile = sys.argv[2]
state = sys.argv[3].upper()+'-'
numOfDistricts = int(sys.argv[4])

precincts = []
with open(inputFile,'r') as fPrecinct:
	data = json.load(fPrecinct)['features']
	precincts = list(map(lambda item : Precinct(item['properties']['GEOID10'],
				Point([float(item['properties']['INTPTLON10']),float(item['properties']['INTPTLAT10'])])),data))
fPrecinct.close()

districts = []
for i in range(1,numOfDistricts+1):
	with open(state+str(i)+'/shape.geojson') as fDistrict:
		data=json.load(fDistrict)
		districts.append(District(data['properties']['Code'],shape(data['geometry'])))
fDistrict.close()

jsonStrings = []
with open(outputFile,"w") as fOutput:
	for precinct in precincts:
		for district in districts:
			if district.polygon.contains(precinct.center): #find a district
				jsonStrings.append(json.dumps({"id":precinct.precinctId,"district":district.districtId}))
				break
	fOutput.write('[')
	fOutput.write(",\n".join(jsonStrings))
	fOutput.write(']')
fOutput.close()

