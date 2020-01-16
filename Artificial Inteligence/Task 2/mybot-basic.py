# -*- coding: utf-8 -*-
"""
Basic chatbot design --- for your own modifications
"""
from nltk.corpus import stopwords
import numpy as np
from sklearn.metrics.pairwise import cosine_similarity
from sklearn.feature_extraction.text import TfidfVectorizer

#######################################################
# Tensor flow / Keras
#######################################################
from tensorflow.keras import models
from cv2 import cv2
model = models.load_model('model.h5')
class_names = ['Champion','Item','Passive','Spell']

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
        userInput = input("> ").replace('.','/')
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
        elif cmd == 3:
            image = cv2.imread(params[1].replace('/','.'), cv2.IMREAD_UNCHANGED)
            image = cv2.resize(np.float32(image),(80,80))
            image = image.reshape(1,80,80,3)
            p = model.predict(image)
            print('That is a',class_names[np.argmax(p[0])])
        elif cmd == 4:
            image = params[1].replace('/jpg','.jpg/').replace('/png','.png/').split('/')[0]
            image = cv2.imread(image, cv2.IMREAD_UNCHANGED)
            image = cv2.resize(np.float32(image),(80,80))
            image = image.reshape(1,80,80,3)
            p = model.predict(image)
            if (class_names[np.argmax(p[0])] == class_names[0]):
                print('Yes it is a champion.')
            else:
                print('No, that is a',class_names[np.argmax(p[0])])
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
