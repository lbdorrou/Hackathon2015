import requests
import os.path
import logging
from bs4 import BeautifulSoup

def Parse(url):
	global s
	jobData = s.get(url)
	jobContent = jobData.content
	soup = BeautifulSoup(jobContent)
	table = soup.find_all(class_="SearchResultsTable")[0]
	rows = table.find_all('tr')

	for row in rows:
		cells = row.find_all("td")
		if(len(cells) > 0):
			jobDescriptionURL = 'https://www3.apply2jobs.com/JBHunt/ProfExt/' + cells[0].find('a').get('href')
			jobDate = cells[1].get_text().encode('utf-8').strip()
			if(jobDescriptionURL is not None):
				result = 'Job.create('
				jobDesc = requests.get(jobDescriptionURL).content
				jobDescSoup = BeautifulSoup(jobDesc)
				jobDescTable = jobDescSoup.find_all(class_='JobDetailTable')[0]
				descRows = jobDescTable.find_all('tr')
				Id = descRows[0].find_all('td')[1].get_text().strip().encode('utf-8').replace("'", "")
				Title = descRows[1].find_all('td')[1].get_text().strip().encode('utf-8').replace("'", "")
				Department = descRows[2].find_all('td')[1].get_text().strip().encode('utf-8').replace("'", "")
				Category = descRows[3].find_all('td')[1].get_text().strip().encode('utf-8').replace("'", "")
				Country = descRows[4].find_all('td')[1].get_text().strip().encode('utf-8').replace("'", "")
				State = descRows[5].find_all('td')[1].get_text().strip().encode('utf-8').replace("'", "")
				City = descRows[6].find_all('td')[1].get_text().strip().encode('utf-8').replace("'", "")
				Time = descRows[7].find_all('td')[1].get_text().strip().encode('utf-8').replace("'", "")
				Shift = descRows[8].find_all('td')[1].get_text().strip().encode('utf-8').replace("'", "")
				Desc= descRows[10].find_all('td')[1].get_text().strip().encode('utf-8').replace("'", "")
				Qualifications = ""
				try: 
					Qualifications = descRows[11].find_all('td')[1].get_text().strip().encode('utf-8').replace("'", "")
				except: 
					pass

				isFullTime = False
				print Time
				if Time is 'Full Time':
					isFullTime = True
				print isFullTime

				result += "'jobId'=> '" + Id + "',"
				result += "'title'=> '" + Title + "',"
				result += "'department'=> '" + Department + "',"
				result += "'category'=> '" + Category + "',"
				result += "'siteID'=> '" + City + ", " + State + "',"
				result += "'postingDate'=> '" + jobDate + "',"
				# result += "'Country'=> '" + Country + "',"
				# result += "'State'=> '" + State + "',"
				# result += "'City'=> '" + City + "',"
				result += "'fullTime'=> '" + str(isFullTime) + "',"
				result += "'shift'=> '" + Shift + "',"
				result += "'description'=> '" + Desc + "',"
				result += "'qualifications'=> '" + Qualifications + "'"
				result += ')\n'
				# print descRows[0].find_all('td')[1].get_text()

				if result != 'Section.create(':
					with open("jobsSeed.rb", "a") as sectionFile:
					  sectionFile.write(result)



allUrl = 'https://www3.apply2jobs.com/JBHunt/ProfExt/index.cfm?fuseaction=mExternal.searchJobs'

s = requests.session()
Parse(allUrl)
data = requests.get(allUrl)
pageContent = data.content
soup = BeautifulSoup(pageContent)
pages = soup.find_all(class_="PagingNumberLink")
Parse('https://www3.apply2jobs.com/JBHunt/ProfExt/index.cfm?fuseaction=mExternal.returnToResults&CurrentPage=2')
for page in pages:
	temp = 'https://www3.apply2jobs.com/JBHunt/ProfExt/' + page.get('href')
	# print temp
	Parse(temp)

# rows = pages.find_all()1