import csv
from functools import reduce
from sys import argv


region_types = {
  'CITY': '- C',
  'TOWN': '- T',
  'VILLAGE': '- V'
}

def getHeadersAndPrecincts(filename):
  headers = []
  precincts = []

  with open(filename, 'r') as f:
    reader = csv.reader(f, delimiter=';')
    for line in reader:
      if (headers == []):
        headers = line[0].split(',')
      elif (line != []):
        precincts.append(line[0].split(','))
    f.close()
  return headers,precincts

def precinctNameIsParseable(precinct_name):
  name_attributes = precinct_name.split()

  region_type = name_attributes[0]
  if region_type not in region_types:
    print(region_type)
    return False
  return True

def convertPrecinct(precinct, headers):
  name_index = headers.index('precinct')
  converted_precincts = []
  converted_name = convertPrecinctName(precinct[name_index]).split()
  ward_num = converted_name[-1]

  # if ('-' in ward_num):
  #   start = int(''.join(ward_num.split('-')))
  #   end = int(''.join(ward_num.split('-')))
  #   for i in range(start,end):
  #     precinct[name_index] = converted_name[:-1] + i
  # else:
  precinct[name_index] = converted_name
  converted_precincts.append(precinct)
  return converted_precincts

def convertPrecinctName(precinct_name):
  name_attributes = precinct_name.replace('"','').split()
  region_type, precinct_name, ward_num = name_attributes[0], name_attributes[2:-2], name_attributes[-1]
  converted_name = reduce(lambda x,y: x + ' ' + y,precinct_name) + ' ' + region_types[region_type.upper()] + ' ' + ward_num
  return converted_name

if __name__ == '__main__':
  filenames = argv[1:]
  for filename in filenames:
    headers, precincts = getHeadersAndPrecincts(filename)

    print(headers)
    name_index = headers.index('precinct')

    with open('resources/2016/standardized/senate/' + 'WI' +  '.csv', 'w') as out:
      csv_out = csv.writer(out)
      csv_out.writerow(['precinct'])
      for precinct in precincts:
        map(lambda row: csv_out.writerow([row[name_index]]), convertPrecinct(precinct,headers))
      out.close()
