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

if __name__ == '__main__':
  filenames = argv[1:]
  for filename in filenames:
    headers, precincts = getHeadersAndPrecincts(filename)

    print(headers)
    name_index = headers.index('precinct')

    with open('resources/2016/standardized/state/' + 'UT' +  '.csv', 'w') as out:
      csv_out = csv.writer(out)
      csv_out.writerow(headers)
      for precinct in precincts:
        precinct[name_index] = precinct[name_index].replace("'","")
        csv_out.writerow(precinct)
      out.close()
