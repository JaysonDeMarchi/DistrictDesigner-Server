import json
import sys
from shapely.geometry import Polygon,shape, MultiPolygon,LineString,MultiLineString,Point
from shapely.validation import explain_validity

class Precinct:
	def __init__(self,precinctId,center):
		self.precinctId = precinctId
		self.center = center


class District:
	def __init__(self,districtId,polygon):
		self.districtId = districtId
		self.polygon = polygon	

def findCoordByID(idd,searchRange):
	for item in searchRange:
		if item['properties']['GEOID10'] == idd:
			return item['geometry']

precincts = []
with open(sys.argv[1],'r') as f:
	data = json.load(f)['features']
	for item in data:
		precinctId = item['properties']['GEOID10']
		precinctCenter = [float(item['properties']['INTPTLON10']),float(item['properties']['INTPTLAT10'])]
		precincts.append(Precinct(precinctId,Point(precinctCenter)))
f.close()


districts = []
for i in range(1,int(sys.argv[4])+1):
	with open('./'+sys.argv[3]+'-'+str(i)+'/shape.geojson') as f:
		data=json.load(f)
		districts.append(District(data['properties']['Code'],shape(data['geometry'])))
f.close()

f = open(sys.argv[2],"w+")
f.write("[\n")
outOfBoundary=[]
for precinct in precincts:
	for district in districts:
		if district.polygon.contains(precinct.center):
			if precinct == precincts[-1]:
				f.write('{"id":"'+str(precinct.precinctId)+'","district":"'+str(district.districtId)+'"}')
			else:
				f.write('{"id":"'+str(precinct.precinctId)+'","district":"'+str(district.districtId)+'"},\n')
			break
		elif not district.polygon.contains(precinct.center) and district==districts[-1]:
			outOfBoundary.append(precinct)


if(outOfBoundary == []):
	f.write(']')
else:
	f.write(',\n')
	for precinct in outOfBoundary:
		disId = None
		minn = sys.maxsize
		for district in districts:
			distance = precinct.center.distance(district.polygon)
			if distance<minn:
				disId = district.districtId
				minn = distance
		if precinct == outOfBoundary[-1]:
			f.write('{"id":"'+str(precinct.precinctId)+'","district":"'+disId+'"}]\n')
		else:
			f.write('{"id":"'+str(precinct.precinctId)+'","district":"'+disId+'"},\n')


