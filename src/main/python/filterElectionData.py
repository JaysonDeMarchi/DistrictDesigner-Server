import csv

office_name = 'state'

if __name__ == '__main__':
  filename = 'resources/2016-precinct-' + office_name + '.csv'
  with open(filename, 'r', encoding='ISO-8859-1') as f:
    reader = csv.reader(f, delimiter=';')
    headers = []
    state_index = 0
    states = {}

    for line in reader:
      if (headers == []):
        headers = line[0].split(',')
        state_index = headers.index('state_postal')
      else:
        precinct = line[0].split(',')
        if precinct[state_index] not in states:
          states[precinct[state_index]] = [precinct]
        else:
          states[precinct[state_index]].append(precinct)

    for state_name in states:
      print('File: ' + state_name + '.py created.')
      with open('resources/2016/' + office_name + '/' + state_name + '.csv', 'w') as out:
        csv_out = csv.writer(out)
        csv_out.writerow(headers)
        for precinct in states[state_name]:
          csv_out.writerow(precinct) 
        out.close()
    f.close()
