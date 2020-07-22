import bs4
from urllib.request import urlopen as uReq
from bs4 import BeautifulSoup as soup
import pyrebase

firebase = pyrebase.initialize_app(config)

db = firebase.database()
db.child("sections")

# from package import library as alias

# Url to be scraped
my_url = "https://guides.codepath.org/android" 

# Downloads the site as a client
# opening up connection, grabbing the page
uClient = uReq(my_url)

# Dumps everything, store before dumping
page_html = uClient.read()
# closes the connection to the site
uClient.close()
# html parsing
page_soup = soup(page_html,"html.parser")

#goes to the div where all the info is stored
page_of_info = page_soup.find('div', {"class": "markdown-body col-xs-6 col-sm-9 col-md-12 col-lg-12"})

#finds all unordered lists
ul = page_of_info.findAll("ul")
# finds all headers
headers = page_of_info.findAll(["h1", "h2", "h3"])
# to complete the href link that is given 
AddtoLink = "https://guides.codepath.org"

# to save each section
sections = []
# to seperate info within each section
s = []
# iterates through the whole website
for text in page_of_info:
    # if its a header itll save the previous section and add a new section
    if text in headers:
        sections.append(s)
        # clears it to create a new setion
        s = []
        # only saves the text
        s.append(text.text)
    # there are empty places which will cause errors
    elif text in ul:
        for li in text:
            a = li.find('a')
            # line above will return -1 for spaces which we don't need
            if a != -1:
                # only saves the text
                s.append(a.text)
                # if its a link it wont at the other part to it
                if 'http' in a['href']:
                    s.append(a['href'])
                # if its only part of the link itll add the rest
                else:
                    s.append(AddtoLink + a['href'])
    # it will sometimes be empty lines which it should ignore
    elif text == '\n':
        continue
    else:
        # only saves the text
        s.append(text.text)
# first place is emtpy
del sections[0]

db = firebase.database()

# data = {"name": "Mortimer 'Morty' Smith"}
# db.child("users").child("Morty").set(data)

# to print it
# counter = 0
# links = []
# for s in sections:
#     c = 0
#     #print (s[0])
#     for i in s:
#         if i != "":
#             if c != 0:
#                 if "https" in i:
#                     data = {"link" : i}
#                     db.child("Guides").child(f"section{counter}").push(data)
#                 else:
#                     data = {"desc" : i}
#                     db.child("Guides").child(f"section{counter}").push(data)
#             else:
#                 data = {f"Header" : i}
#                 db.child("Guides").child(f"section{counter}").push(data)
#         c += 1
#     counter += 1

counter = 0
for s in sections:
    for i in range(len(s)):
        if s[i] != "":
            if i != 0 and i+1 < len(s):
                if "http" in s[i+1]:
                    data = {"desc" : s[i], "link": s[i+1]}
                    db.child("Guides").child(f"section{counter}").push(data)
                    # i += 1
                elif "http" not in s[i]:
                    data = {"desc" : s[i]}
                    # print(data)
                    db.child("Guides").child(f"section{counter}").push(data)
            elif i == 0:
                data = {f"Header" : s[i]}
                db.child("Guides").child(f"section{counter}").set(data)
            else:
                if "https" not in s[i]:
                    data = {"desc": s[i]}
                    db.child("Guides").child(f"section{counter}").push(data)
    counter += 1

# for s in sections:
#     for i in range(len(s)):
#         if i != 0:
            
#         else:
#             header = s[i]
#             # data = {"Header" : header}
#             # db.child("testing").child(f"section{counter}").set(data)

"""print("")
    for i in s:
        print(i, end=None)"""

# to get links and text from list items in un ordered lists & saves to dict
"""codepath_resources = {"headers" : [], "links" : []}
index = 0
for ul in ul:
    codepath_resources["links"].append([]) #to add new section
    for li in ul:
        a = li.find("a") #to narrow down to link and text
        if a != -1: # we get a -1 for spaces
            print(a.text)
            print(a['href'])
            codepath_resources["links"][index].append(a)
    index += 1"""


