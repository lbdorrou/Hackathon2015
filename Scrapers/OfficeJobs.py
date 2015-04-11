import requests
import os.path
import logging
from bs4 import BeautifulSoup


url = 'https://www3.apply2jobs.com/JBHunt/ProfExt/index.cfm?fuseaction=mExternal.searchJobs'


# jobData = requests.get(url)
# jobContent = jobData.content
# soup = BeautifulSoup(jobContent)
# table = soup.find_all(class_="SearchResultsTable")[0]
# rows = table.find_all('tr')

tempUrl = 'https://www3.apply2jobs.com/JBHunt/ProfExt/index.cfm?fuseaction=mExternal.showJob&RID=12594&CurrentPage=1'
jobDesc = requests.get(tempUrl).content
jobDescSoup = BeautifulSoup(jobDesc)
jobDescTable = jobDescSoup.find_all(class_='JobDetailTable')[0]
descRows = jobDescTable.find_all('tr')
jobId = descRows[0].find_all('td')[1].get_text().strip().encode('utf-8')
jobTitle = descRows[1].find_all('td')[1].get_text().strip().encode('utf-8')
jobDepartment = descRows[2].find_all('td')[1].get_text().strip().encode('utf-8')
jobCategory = descRows[3].find_all('td')[1].get_text().strip().encode('utf-8')
jobCountry = descRows[4].find_all('td')[1].get_text().strip().encode('utf-8')
jobState = descRows[5].find_all('td')[1].get_text().strip().encode('utf-8')
jobCity = descRows[6].find_all('td')[1].get_text().strip().encode('utf-8')
jobTime = descRows[7].find_all('td')[1].get_text().strip().encode('utf-8')
jobShift = descRows[8].find_all('td')[1].get_text().strip().encode('utf-8')
jobDesc= descRows[10].find_all('td')[1].get_text().strip().encode('utf-8')
jobQualifications = descRows[11].find_all('td')[1].get_text().strip().encode('utf-8')


# for row in rows:
# 	cells = row.find_all("td")
# 	if(len(cells) > 0):
# 		jobDescriptionURL = 'https://www3.apply2jobs.com/JBHunt/ProfExt/' + cells[0].find('a').get('href')
# 		if(jobDescriptionURL is not None):
# 			jobDesc = requests.get(tempUrl).content
# 			jobDescSoup = BeautifulSoup(jobDesc)
# 			jobDescTable = jobDescSoup.find_all(class_='JobDetailTable')[0]
# 			descRows = jobDescTable.find_all('tr')
# 			print descRows[0].find_all('td')[1].get_text()
				