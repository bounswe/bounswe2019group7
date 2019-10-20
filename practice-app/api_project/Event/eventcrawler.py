from bs4 import BeautifulSoup
import requests
import datetime
import sys, os
import unicodedata

class Eventcrawler:
	def __init__(self):
		self.base_url = 'http://us.econoday.com/byday.asp?'
		self.header = {'User-Agent': 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko)'+ 'Chrome/43.0.2357.132 Safari/537.36'}
		self.information = []

	def crawl(self):
		current_date=datetime.datetime.now()

		for i in range(7): #last 7 days
			self.getNextDaysEvents(current_date, self.information)
			current_date -= datetime.timedelta(days=1)

		return self.information

	def getNextDaysEvents(self, current_date, information):
		curr_url = self.base_url + 'day=' + str(current_date.day) + \
		           '&month=' + str(current_date.month) + \
		           '&year=' + str(current_date.year)

		req = requests.get(curr_url, headers=self.header)
		soup = BeautifulSoup(req.text, 'html.parser')
		evtDescRows = soup.find_all('tr', class_="dailyeventtext")

		for row in evtDescRows:

		    if (row.find_all('td')[0].get_text().find(':') != -1):
		        evtTime = row.find_all('td')[0].get_text()
		        if evtTime.index(':') == 1: evtTime = '0' + evtTime

		        evtTime12H_ET = int(evtTime[0:2])

		        evtTime24H_ET = evtTime12H_ET
		        if evtTime.find('PM') != -1 and evtTime.find('12:') == -1:
		            evtTime24H_ET = evtTime12H_ET + 12

		        evtTime24H_CT = evtTime24H_ET - 1

		        evtName = row.find_all('td')[2].find_all('a')[0].get_text()
		        evtDT = str(datetime.datetime(current_date.year, current_date.month, current_date.day, evtTime24H_CT, int(evtTime[3:5]), 0, 0))
		        information.append([evtName, evtDT])

