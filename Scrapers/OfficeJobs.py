import requests
import os.path
import logging
from bs4 import BeautifulSoup


url = 'https://www3.apply2jobs.com/JBHunt/ProfExt/index.cfm?fuseaction=mExternal.searchJobs'


jobData = requests.get(url)
jobContent = jobData.content
soup = BeautifulSoup(jobContent)
table = soup.find_all(class_="SearchResultsTable")[0]
rows = table.find_all('tr')

for row in rows:
	cells = row.find_all("td")
	if(len(cells) > 0):
		jobDescriptionURL = 'https://www3.apply2jobs.com/JBHunt/ProfExt/' + cells[0].find('a').get('href')
		if(jobDescriptionURL is not None):
			jobDesc = requests.get(url)
			jobDescContent = jobDesc.content