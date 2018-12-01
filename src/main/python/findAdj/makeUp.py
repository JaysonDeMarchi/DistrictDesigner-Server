import json
from shapely.geometry import Polygon,shape, MultiPolygon,LineString,MultiLineString
from shapely.ops import nearest_points


def findCoordByID(idd,searchRange):
	for item in searchRange:
		if item['properties']['GEOID10'] == idd:
			return item['geometry']

def adjcentPolygon(adjIds,searchRange):
	returnList = []
	for adjId in adjIds:
		returnList.append(shape(findCoordByID(adjId,searchRange)))
	return returnList

def isInterlace(p1,p2):
	x,y = p1.exterior.coords.xy
	p1TopBotLR = [max(y),min(y),min(x),max(x)]
	x2,y2 = p2.exterior.coords.xy
	p2TopBotLR = [max(y2),min(y2),min(x2),max(x2)]
	if p1TopBotLR[0]<p2TopBotLR[1] or p2TopBotLR[0]<p1TopBotLR[1]:
		if p1TopBotLR[2]>=p2TopBotLR[3] or p2TopBotLR[2]>=p1TopBotLR[3]:
			return True
	if p1TopBotLR[2]<p2TopBotLR[3] or p2TopBotLR[2]<p1TopBotLR[3]:
		if p1TopBotLR[0]>=p2TopBotLR[1] or p2TopBotLR[0]>=p1TopBotLR[1]:
			return True
	return False


with open('test.json') as fres:
	results = json.load(fres)
	with open('temp.json') as frc:
		resources = json.load(frc)['features']
		for result in results:
			adjPrecincts = result['adjPrecincts']
			for resource in resources:
				if resource['properties']['GEOID10'] not in adjPrecincts and resource['properties']['GEOID10']!=result['id']: #not adjcent
					polygonA = shape(findCoordByID(result['id'],resources))  #from resutl.json
					polygonB = shape(resource['geometry'])					#from resource.json
					line = LineString(nearest_points(polygonA,polygonB))
					PolygonList = adjcentPolygon(adjPrecincts,resources)
					for polygon in PolygonList:
						if polygon.intersects(line):
							print(result['id'],resource['properties']['GEOID10'])
							print("intersects")
						else:
							print(result['id'],resource['properties']['GEOID10'])
							if isInterlace(polygonA,polygonB):
								print("has river,but still not ajdacent")
							else:
								print("has river,but ajdacent")








