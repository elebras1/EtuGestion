import json
import re
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.firefox.options import Options
from selenium.webdriver.firefox.service import Service

options = Options()
options.headless = True
service = Service(log_path='geckodriver.log',executable_path='/usr/local/bin/geckodriver')
options.binary_location = '/usr/bin/firefox'
driver = webdriver.Firefox(options=options, service=service)
driver.get('https://formations.univ-brest.fr')

Formations = []

def wait(form,waiting) :
    return WebDriverWait(driver, 10).until(
        EC.presence_of_element_located((form, waiting))
    )

def First_Page() :
    wrapper=wait(By.CLASS_NAME,"art-button-wrapper")
    submit_button = wrapper.find_element(By.TAG_NAME, "input")
    submit_button.click()
    return wait(By.TAG_NAME,"body")

def Page_Suivant(page) :
    suivant_list = page.find_element(By.CLASS_NAME, "next")
    suivant_button= suivant_list.find_element(By.CLASS_NAME, "art-button")
    suivant_button.click()
    return wait(By.TAG_NAME,"body")

def extract_ue_data(row, optionnel=False):
    td = row.find_elements(By.TAG_NAME, "td")
    if len(td) < 3:
        return None
    return {
        "name": td[0].text,
        "ects": re.sub(r"\D", "", td[1].text),
        "hours": re.sub(r"\D", "", td[2].text),
        "capacity": 30,
        "isMandatory": optionnel
    }

def Page_UES(formation,li) :
    a = li.find_element(By.TAG_NAME, "a")
    a.click()
    page_programme=wait(By.TAG_NAME,"body")
    navbar=page_programme.find_element(By.CLASS_NAME, "r-tabs-nav")
    li=navbar.find_elements(By.TAG_NAME, "li")
    for i in range(len(li)) :
        if li[i].text == "Programme" :
            li[i].click()
            break

    page_ue=wait(By.TAG_NAME,"body")
    programme_tables=page_ue.find_elements(By.CLASS_NAME, "programTable")
    Ues=[]
    for programme_table in programme_tables:
        lignes_programme=programme_table.find_elements(By.TAG_NAME, "tr")
        formation["OptionsNumber"]=0
        for i in range(len(lignes_programme)):
            if lignes_programme[i].get_attribute("class") == "level2" :
                if (i+1<len(lignes_programme)) and (lignes_programme[i+1].get_attribute("class") == "level2") :
                    UE=extract_ue_data(lignes_programme[i])
                    Ues.append(UE)
                else :
                    n=i+1
                    while (n<len(lignes_programme)) and (lignes_programme[n].get_attribute("class") == "level3") :
                        UE = extract_ue_data(lignes_programme[n])
                        Ues.append(UE)
                        n += 1

            elif lignes_programme[i].get_attribute("class") == "level4" :
                if (i+1<len(lignes_programme)) and (lignes_programme[i+1].get_attribute("class") == "level3") :
                    UE=extract_ue_data(lignes_programme[i+1], True)
                    formation["OptionsNumber"]+=1
                    Ues.append(UE)
                else :
                    UE=extract_ue_data(lignes_programme[i], True)
                    formation["OptionsNumber"]+=1
                    Ues.append(UE)

    formation["teachingUnits"]=Ues
    driver.back()

    return wait(By.TAG_NAME,"body")

try:
    page=First_Page()
    suivant_button=0
    while suivant_button != None :
        try :
            result=page.find_element(By.CLASS_NAME, "results")
            list_items = result.find_elements(By.TAG_NAME, "li")
            for i in range(len(list_items)) :
                result=page.find_element(By.CLASS_NAME, "results")
                list_items = result.find_elements(By.TAG_NAME, "li")
                Formation={}
                Formation["name"]=list_items[i].text
                Formation["tdSize"]=30
                Formation["tpSize"]=25
                page=Page_UES(Formation,list_items[i])
                Formations.append(Formation)
                print(json.dumps(Formation, indent=4))
                print("-----")
            page=Page_Suivant(page)
        except Exception as e :
            print(f"Erreur : {e}")
            suivant_button = None


except Exception as e:
    print(f"Erreur : {e}")

with open("Formations.json", "w", encoding="utf-8") as fichier:
    json.dump(Formations, fichier, ensure_ascii=False, indent=4)

driver.quit()

