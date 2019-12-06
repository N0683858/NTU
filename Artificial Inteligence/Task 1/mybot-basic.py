# -*- coding: utf-8 -*-
"""
Basic chatbot design --- for your own modifications
"""
from nltk.corpus import stopwords
import numpy as np
from sklearn.metrics.pairwise import cosine_similarity
from sklearn.feature_extraction.text import TfidfVectorizer

#######################################################
# Initialise Wikipedia agent
#######################################################
import wikipediaapi
wiki_wiki = wikipediaapi.Wikipedia('en')
wikipediaapi.log.setLevel(level=wikipediaapi.logging.ERROR)

#######################################################
# Initialise weather agent
#######################################################
import json, requests
APIkey = "" #insert your personal OpenWeathermap API key here if you have one, and want to use this feature


#######################################################
#  Initialise AIML agent
#######################################################
import aiml
# Create a Kernel object. No string encoding (all I/O is unicode)
kern = aiml.Kernel()
kern.setTextEncoding(None)
# Use the Kernel's bootstrap() method to initialize the Kernel. The
# optional learnFiles argument is a file (or list of files) to load.
# The optional commands argument is a command (or list of commands)
# to run after the files are loaded.
# The optional brainFile argument specifies a brain file to load.
kern.bootstrap(learnFiles="mybot-basic.xml")
#######################################################
#  Similarity
#######################################################
def SimilarityArray(string, searchArray):
    array = [string] 
    array_2 = array + searchArray
    tfidf = TfidfVectorizer(stop_words='english').fit_transform(array_2)
    similarityArray = cosine_similarity(tfidf[0:1], tfidf)
    similarityArray = np.delete(similarityArray, 0)

    return similarityArray

#######################################################
#  Reading CVS file
#######################################################
import csv

def ReadingCSV(file):
    with open(file) as csvfile:
        readFile = csv.reader(csvfile, delimiter=',')
        questions = []
        answers = []
        for row in readFile:
            Q = row[0]
            A = row[1]

            questions.append(Q)
            answers.append(A)
            
          
        return questions, answers
#######################################################
# Welcome user
#######################################################
print("Welcome to the League of Legends chat bot. Please feel free to ask questions about",
      "Champions, Tiers and Devisions, or about the game terms in general.")
#######################################################
# Main loop
#######################################################
while True:
    #get user input
    try:
        userInput = input("> ")
    except (KeyboardInterrupt, EOFError) as e:
        print("Bye!")
        break
    #pre-process user input and determine response agent (if needed)
    responseAgent = 'aiml'
    #activate selected response agent
    if responseAgent == 'aiml':
        answer = kern.respond(userInput)
    #post-process the answer for commands
    if answer[0] == '#':
        params = answer[1:].split('$')
        cmd = int(params[0])
        if cmd == 0:
            print(params[1])
            break
        elif cmd == 1:
            wpage = wiki_wiki.page(params[1])
            if wpage.exists():
                print(wpage.summary)
                print("Learn more at", wpage.canonicalurl)
            else:
                print("Sorry, I don't know what that is.")
        # elif cmd == 2:
        #     succeeded = False
        #     api_url = r"http://api.openweathermap.org/data/2.5/weather?q="
        #     response = requests.get(api_url + params[1] + r"&units=metric&APPID="+APIkey)
        #     if response.status_code == 200:
        #         response_json = json.loads(response.content)
        #         if response_json:
        #             t = response_json['main']['temp']
        #             tmi = response_json['main']['temp_min']
        #             tma = response_json['main']['temp_max']
        #             hum = response_json['main']['humidity']
        #             wsp = response_json['wind']['speed']
        #             wdir = response_json['wind']['deg']
        #             conditions = response_json['weather'][0]['description']
        #             print("The temperature is", t, "°C, varying between", tmi, "and", tma, "at the moment, humidity is", hum, "%, wind speed ", wsp, "m/s,", conditions)
        #             succeeded = True
        #     if not succeeded:
        #         print("Sorry, I could not resolve the location you gave me.")
        elif cmd == 99:
            # print("I did not get that, please try again.")
            Question, Answer = ReadingCSV('Q&A_Pairs.txt')
            arr = SimilarityArray(userInput, Question)
            #get index of heighest value in arr
            maxElement = np.argmax(arr)
            #print out whatever is in the index of the answer
            print(Answer[maxElement])
            
    else:
        print(answer)
