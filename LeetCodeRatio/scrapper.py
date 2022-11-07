from selenium import webdriver
import time
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
import csv



CHROMEDRIVER_PATH = r"./driver/chromedriver.exe"

driver = webdriver.Chrome(CHROMEDRIVER_PATH)

num_of_pages = 2
ch = ' '

baseurl = 'https://leetcode.com/problems/'

#questions = driver.find_elements(By.XPATH, '//*[@id="__next"]/div/div/div[1]/div[1]/div[6]/div[2]/div/div/div[2]')

likes_list = []
dislikes_list = []
e = []

#//*[@id="__next"]/div/div/div[1]/div[1]/div[6]/div[2]/div/div/div[2]/div[3]/div[1]

for pagenum in range(1,num_of_pages):
    if(pagenum != 1):
        e.append("\n")
    pageurl = "https://leetcode.com/problemset/all/?page=" + str(pagenum)
    driver.get(pageurl)
    time.sleep(1)
    questions = driver.find_elements(By.XPATH, '//*[@id="__next"]/div/div/div[1]/div[1]/div[6]/div[2]/div/div/div[2]')
    e.append(questions[0].text)

question_likes = []
question_dislikes = []
question_tittle = []
question_acceptance = []
question_difficulty = []
formated_tittle = []
count = 0


s = ''.join(str(x) for x in e)
h = s.split("\n")

for i in range(0,len(h)):
    if count == 0:
        question_tittle.append(h[i]) 
    if count == 1:
        question_acceptance.append(h[i])  
    if count == 2:
        question_difficulty.append(h[i])
        count = -1
    count += 1

    
#for x in range(0,len(question_tittle)):
    #print(question_tittle[x])
    #print(question_acceptance[x])
    #print(question_difficulty[x])

for x in range(0,len(question_tittle)):
    listOfWords = question_tittle[x].split(ch, 1)
    listOfWords[1] = listOfWords[1].replace("(", "")
    listOfWords[1] = listOfWords[1].replace(")", "")
    listOfWords[1] = listOfWords[1].replace(",", "")
    listOfWords[1] = listOfWords[1].lower()
    formated_tittle.append(listOfWords[1].replace(" ", "-"))


for x in range(0,len(formated_tittle)):

    questionurl = baseurl + str(formated_tittle[x])
    driver.get(questionurl)
    time.sleep(2)
    like = driver.find_elements(By.XPATH, '//*[@id="app"]/div/div[2]/div[1]/div/div[1]/div/div[1]/div[1]/div/div[2]/div/div[1]/div[2]/button[1]/span')
    dislike = driver.find_elements(By.XPATH, '//*[@id="app"]/div/div[2]/div[1]/div/div[1]/div/div[1]/div[1]/div/div[2]/div/div[1]/div[2]/button[2]/span')
    question_likes.append(like[0].text)
    question_dislikes.append(dislike[0].text)
    print("question " + str(x) + " Success")

    

####################################################################################################
#csv file


header = ['question name', 'acceptance', 'difficulty', 'likes', 'dislikes']

with open ("LeetCodeRatio.csv",'a') as f:                            
    writer = csv.writer(f)
    writer.writerow(question_tittle) 
    writer.writerow(question_acceptance) 
    writer.writerow(question_difficulty) 
    writer.writerow(question_likes) 
    writer.writerow(question_dislikes) 
