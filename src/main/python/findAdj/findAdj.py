import json
import sys
from shapely.geometry import shape
from shapely.validation import explain_validity


class Precinct:
	def __init__(self,precintId,polygon):
		self.precintId = precintId
		self.polygon = polygon
		self.adjPrecincts = []

def updateAdjPrecincts(a,b):
	if a==b or b.precintId in a.adjPrecincts:
		return

	if a.polygon.intersects(b.polygon):
		a.adjPrecincts.append(b.precintId)
		b.adjPrecincts.append(a.precintId)
	return 

totalPrecinct = 0 
with open(sys.argv[1],'r') as f:
	data = json.load(f)['features']
	totalPrecinct = len(data)
	precincts = []
	for item in data:
		precintId = item['properties']['GEOID10']
		polygon = shape(item['geometry'])

		precincts.append(Precinct(precintId,polygon))
f.close()

completed = 1.0
f = open(sys.argv[2],"w+")
f.write("[\n")
for precinctA in precincts:
	for precinctB in precincts:
		updateAdjPrecincts(precinctA,precinctB)
	completed += 1
	print(completed/totalPrecinct)
	if precinctA == precincts[-1]:
		dic = '{"id":"'+str(precinctA.precintId)+'","adjPrecincts":'+str(precinctA.adjPrecincts).replace("'",'"')+'}\n]'
	else:
		dic = '{"id":"'+str(precinctA.precintId)+'","adjPrecincts":'+str(precinctA.adjPrecincts).replace("'",'"')+'},\n'
	f.write(dic)
f.close()



