import bs4
from urllib.request import urlopen as uReq
from bs4 import BeautifulSoup as soup
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
        s.append(text)
    # there are empty places which will cause errors
    elif text == '\n':
        continue
    else:
        s.append(text)
# first place is emtpy
del sections[0]

# to print it
for s in sections:
    print("")
    for i in s:
        print(i.text, end=None)


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


