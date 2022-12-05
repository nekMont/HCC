from selenium import webdriver
import time
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
import csv


CHROMEDRIVER_PATH = r"./driver/chromedriver.exe"
options = webdriver.ChromeOptions()
options.add_argument('--disable-blink-features=AutomationControlled')
options.add_argument("--disable-extensions")
options.add_experimental_option('useAutomationExtension', False)
options.add_experimental_option("excludeSwitches", ["enable-automation"])
driver = webdriver.Chrome(CHROMEDRIVER_PATH,options=options)
driver.execute_script("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})")

num_of_pages = 2
ch = ' '
baseurl = 'https://leetcode.com/problems/'
e = []
print_header = False

usr = "CS482HCI"
pssd = "U5zveQ_#G2QvYVu"


def sign_in():
    driver.get("https://leetcode.com/problemset/all/")
    button = driver.find_element(By.XPATH, '//*[@id="__next"]/div/nav/div[1]/div/div[3]/div/a[2]')
    button.click()
    time.sleep(2)

    username_field = driver.find_element(By.ID, "id_login")
    password_field = driver.find_element(By.ID, "id_password")
    username_field.send_keys(usr)
    password_field.send_keys(pssd)

    time.sleep(2)

    button1 = driver.find_element(By.XPATH, '//*[@id="signin_btn"]/div')
    button1.click()

    time.sleep(2)


#######################################sign in#################################################
driver.get("https://leetcode.com/problemset/all/")
button = driver.find_element(By.XPATH, '//*[@id="__next"]/div/nav/div[1]/div/div[3]/div/a[2]')
button.click()
time.sleep(2)

username_field = driver.find_element(By.ID, "id_login")
password_field = driver.find_element(By.ID, "id_password")
username_field.send_keys(usr)
password_field.send_keys(pssd)

time.sleep(2)

button1 = driver.find_element(By.XPATH, '//*[@id="signin_btn"]/div')
button1.click()

time.sleep(2)

##############################################################################################

#do this for desired page numbers
for pagenum in range(1,num_of_pages):
    #appends \n to the start of the next page info so i can split it later
    if(pagenum != 1):
        e.append("\n")
        # open page wait one second and get information correlated to xpath 
        # I have a delay becasue if i take it off the pages open too fast and data does not save correctly
    pageurl = "https://leetcode.com/problemset/all/?page=" + str(pagenum)
    driver.get(pageurl)
    time.sleep(1)
    questions = driver.find_elements(By.XPATH, '//*[@id="__next"]/div/div/div[1]/div[1]/div[6]/div[2]/div/div/div[2]')
    e.append(questions[0].text)

question_link = []
question_likes = []
question_dislikes = []
question_tittle = []
question_acceptance = []
question_difficulty = []
question_companies = []
formated_tittle = []
count = 0

#split the inforamtion for page
#infroamtion saves as one string so i had to split it in parts
s = ''.join(str(x) for x in e)
h = s.split("\n")
#separate data every time counter reaches to 2 that is a new question
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

num_of_questions = len(question_tittle)

####Formating of question title to question link

for x in range(0,num_of_questions):
    #remove everything before the first space ex. (1. Two Sum -> Two Sum)
    listOfWords = question_tittle[x].split(ch, 1)
    #Remove all special characters
    listOfWords[1] = listOfWords[1].replace("(", "")
    listOfWords[1] = listOfWords[1].replace(")", "")
    listOfWords[1] = listOfWords[1].replace(",", "")
    #trun every character to lower case
    listOfWords[1] = listOfWords[1].lower()
    #chnage white space to - ex. (two sum -> two-sum) 
    #this is how the link is foramted for each question
    formated_tittle.append(listOfWords[1].replace(" ", "-"))


for x in range(0,num_of_questions):

    questionurl = baseurl + str(formated_tittle[x])
    question_link.append(questionurl)
    driver.get(questionurl)
    time.sleep(3)
    #open url and find xpath infromation, again I have a delay becvasue data doesnt load propely if i dont
    like = driver.find_elements(By.XPATH, '//*[@id="qd-content"]/div[1]/div/div/div/div[2]/div/div/div[1]/div/div[2]/div[2]/div/div[1]/div[2]')
    #print(like)
    dislike = driver.find_elements(By.XPATH, '//*[@id="qd-content"]/div[1]/div/div/div/div[2]/div/div/div[1]/div/div[2]/div[2]/div/div[2]/div[2]')
    #print(dislike)
    company = driver.find_elements(By.XPATH, '//*[@id="qd-content"]/div[1]/div/div/div/div[2]/div/div/div[2]/div/div')
    #print(company)
    question_likes.append(like[0].text)
    question_dislikes.append(dislike[0].text)
    question_companies.append(company[0].text)
    print("question " + str(x) + " Success")
    time.sleep(2)
    

question_companies = [sub.replace('\n', ',') for sub in question_companies]
#print(question_companies)
    
####################################################################################################
#csv file

header = ['question name', 'acceptance', 'difficulty', 'likes', 'dislikes', 'link', 'companies']

with open ("LeetCodeRatio.csv",'w') as f:       
    f.truncate()                     
    writer = csv.writer(f)
    if(print_header == False):
        writer.writerow(header)
        print_header = True
    writer.writerows(zip(question_tittle, question_acceptance, question_difficulty, question_likes,
    question_dislikes, question_link, question_companies)) 

def main():
    print()

if  __name__ == "__main__":
    main()