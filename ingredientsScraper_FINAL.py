import requests
from bs4 import BeautifulSoup
from selenium import webdriver
from selenium.common.exceptions import TimeoutException
from selenium.webdriver.support.ui import Select
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
import re
import wordninja


# headers = {
#     "User-agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36"}
# facts = "+nutrition+facts"
# ingredient = "almond butter"
# googleUrl = "https://google.com/search?q=" + ingredient + facts
# select = 'tbsp'

nutrInfoGramsId = 263
nutrInfoPercId = 263

def retrieveInsertStatements(page_source, selectedOption, ingredientName):
    soup = BeautifulSoup(page_source, 'html.parser')

    nutritionTables = soup.find('table', class_='AYBNrd').findAllNext('tr')

    # print 'NUTRITION FACTS: '
    # for child in nutritionFacts2:
    #     trs = nutritionFacts2.findAllNext('tr')
    count = 0
    # nutritions_info_grams list
    # [calories, total_fat, saturated_fat, polyunsaturated_fat, monounsaturated_fat, trans_fat_regulation, cholesterol
    # sodium, potassium, total_carbohydrate, dietary_fiber, sugar, protein, caffeine]
    nutritionalInfoGrams = [0, 0, 0, 0, 0, 0, 0,
                            0, 0, 0, 0, 0, 0, 0]

    # nutritional_information_percentage list
    # no calories in this one
    # [total_fat, saturated_fat, polyunsaturated_fat, monounsaturated_fat, trans_fat_regulation, cholesterol
    # sodium, potassium, total_carbohydrate, dietary_fiber, sugar, protein, caffeine, vitamin_A, vitamin_C,
    # calcium, iron, vitamin_D, vitamin_B-6, cobalamin, magnesium]
    nutritionalInfoPercentage = [0, 0, 0, 0, 0, 0,
                                 0, 0, 0, 0, 0, 0, 0, 0, 0,
                                 0, 0, 0, 0, 0, 0]
    nutrIndexG = 1
    nutrIndexP = 0
    caffeineFound = False
    for tr in nutritionTables:
        # print str(count) + ": ",
        if (tr.has_attr('class')):
            # Nutrients
            if ('kno-nf-nr' in tr['class']):
                # Nutrient details
                spans = tr.findAll('span')
                for span in spans:
                    if (span.has_attr('class') and 'V6Ytv' in span['class']):
                        # Nutrient Title
                        if (nutrIndexG == 5 and not ('Trans fat regulation' in span.text)):
                            nutritionalInfoGrams[nutrIndexG] = '0 g'
                            nutritionalInfoPercentage[nutrIndexP] = '0%'
                            nutrIndexG += 1
                            nutrIndexP += 1
                            # print ('Trans Fat Regulation: 0 g, 0%')
                        if ('Caffeine' in span.text):
                            caffeineFound = True
                        # print span.text + ': ',
                    elif (span.has_attr('class') and 'abs' in span['class']):
                        # Nutrient grams
                        if (span.text == ''):
                            nutritionalInfoGrams[nutrIndexG] = '0 g'
                            # print '0 g, ',
                        else:
                            nutritionalInfoGrams[nutrIndexG] = span.text
                            # print span.text + ', ',
                        nutrIndexG += 1
                    elif (span.has_attr('class') and 'pdv' in span['class']):
                        # Nutrient percentages
                        if (span.text == ''):
                            nutritionalInfoPercentage[nutrIndexP] = '0%'
                            # print '0%'
                        else:
                            nutritionalInfoPercentage[nutrIndexP] = span.text
                            # print span.text
                        nutrIndexP += 1
                    # elif (not (span.has_attr('class'))):
                        # Nutrient title
                        # print span.text + ': ',
                # print 'Nutrient: ' + tr.text
            # else:
                # print tr.text
        else:
            # Vitamins
            if (nutrIndexP >= 12 and not (caffeineFound)):
                nutritionalInfoGrams[nutrIndexG] = '0 g'
                nutritionalInfoPercentage[nutrIndexP] = '0%'
                nutrIndexG += 1
                nutrIndexP += 1
                caffeineFound = True
                # print ('Caffeine: 0 g, 0%')
            tds = tr.findAll('td')
            for td in tds:
                if (td.has_attr('class') and 'fooDZe' in td['class']):
                    # Vitamin Percentage
                    nutritionalInfoPercentage[nutrIndexP] = td.text
                    # print td.text
                    nutrIndexP += 1
                # else:
                    # Vitamin Name
                    # print td.text + ": ",
        count += 1

    def listToStr(s):
        str1 = ""
        for ele in s:
            str1 += ele
        return str1

    # Finding Calories
    caloriesTable2 = [td.findAll('span') for td in soup.findAll('tr', class_="PZPZlf kno-nf-cq")]
    caloriesHeader = caloriesTable2[0][0].text
    caloriesValue = caloriesTable2[0][1].text
    # print (caloriesHeader + ': ' + caloriesValue)

    nutritionalInfoGrams[0] = caloriesValue

    # nutritions_info_grams table
    # [calories, total_fat, saturated_fat, polyunsaturated_fat, monounsaturated_fat, trans_fat_regulation, cholesterol
    # sodium, potassium, total_carbohydrates, dietary_fiber, sugar, protein, caffeine]

    insertNutrInfoGramsStatement = 'INSERT INTO nutritional_information_grams (id, calories, total_fat, saturated_fat, polyunsaturated_fat, monounsaturated_fat, trans_fat_regulation, cholesterol,' \
                                   'sodium, potassium, total_carbohydrates, dietary_fiber, sugars, protein, caffeine) ' \
                                   'VALUES (' + str(nutrInfoGramsId) + ', %s, \'%s\', \'%s\', ' \
                                                                       '\'%s\', \'%s\', \'%s\', ' \
                                                                       '\'%s\', \'%s\', \'%s\', ' \
                                                                       '\'%s\', \'%s\', \'%s\', ' \
                                                                       '\'%s\', \'%s\');' \
                                   % (nutritionalInfoGrams[0], nutritionalInfoGrams[1], nutritionalInfoGrams[2],
                                      nutritionalInfoGrams[3], nutritionalInfoGrams[4], nutritionalInfoGrams[5],
                                      nutritionalInfoGrams[6], nutritionalInfoGrams[7], nutritionalInfoGrams[8],
                                      nutritionalInfoGrams[9], nutritionalInfoGrams[10], nutritionalInfoGrams[11],
                                      nutritionalInfoGrams[12], nutritionalInfoGrams[13])

    print insertNutrInfoGramsStatement

    # nutritional_information_percentage list
    # [total_fat, saturated_fat, polyunsaturated_fat, monounsaturated_fat, trans_fat_regulation, cholesterol
    # sodium, potassium, total_carbohydrate, dietary_fiber, sugars, protein, caffeine, vitamin_A, vitamin_C,
    # calcium, iron, vitamin_D, vitamin_B-6, cobalamin, magnesium]
    insertNutriInfoPercentageStatement = \
        'INSERT INTO nutritional_information_percentage ' \
        '(id, total_fat, saturated_fat, polyunsaturated_fat, monounsaturated_fat,' \
        'trans_fat_regulation, cholesterol, sodium, potassium, total_carbohydrates,' \
        'dietary_fiber, sugars, protein, caffeine, vitamina, vitaminc, calcium,' \
        'iron, vitamind, vitaminb6, cobalamin, magnesium) ' \
        'VALUES (' + str(nutrInfoPercId) + ', \'%s\', \'%s\', \'%s\',' \
                                           '\'%s\', \'%s\', \'%s\',' \
                                           '\'%s\', \'%s\', \'%s\',' \
                                           '\'%s\', \'%s\', \'%s\',' \
                                           '\'%s\', \'%s\', \'%s\',' \
                                           '\'%s\', \'%s\', \'%s\',' \
                                           '\'%s\', \'%s\', \'%s\');' \
        % (nutritionalInfoPercentage[0], nutritionalInfoPercentage[1], nutritionalInfoPercentage[2],
           nutritionalInfoPercentage[3], nutritionalInfoPercentage[4], nutritionalInfoPercentage[5],
           nutritionalInfoPercentage[6], nutritionalInfoPercentage[7], nutritionalInfoPercentage[8],
           nutritionalInfoPercentage[9], nutritionalInfoPercentage[10], nutritionalInfoPercentage[11],
           nutritionalInfoPercentage[12], nutritionalInfoPercentage[13], nutritionalInfoPercentage[14],
           nutritionalInfoPercentage[15], nutritionalInfoPercentage[16], nutritionalInfoPercentage[17],
           nutritionalInfoPercentage[18], nutritionalInfoPercentage[19], nutritionalInfoPercentage[20],)

    print insertNutriInfoPercentageStatement

    insertIngredientStatement = 'INSERT INTO ingredient(name, quantity_type_and_value, nutritional_information_grams_id,' \
                                'nutritional_information_percentage_id, multiplier) ' \
                                'VALUES (\'' + ingredientName + '\', \'' + selectedOption + '\', ' + str(
        nutrInfoGramsId) + ', ' + str(nutrInfoPercId) + ', 1);'
    print insertIngredientStatement + "\n\n"


driver = webdriver.Chrome('/Users/abdullahmohamed/Downloads/chromedriver')


def searchGoogleAndRetrieveValues(ingr, type):
    facts = "+nutrition+facts"
    googleUrl2 = "https://google.com/search?q=" + ingr + facts
    driver.get(googleUrl2)
    select2 = Select(driver.find_element_by_css_selector('select[class="Ev4pye kno-nf-ss"]'))
    selects = select2.options
    for child in selects:
        # getting all values in select
        # print child.text
        value = child.get_attribute('value')
        # print 'value: ' + value
        try:
            # Until the program has selected the option specified to select, keep looking for the option to select
            while (not (child.text == select2.all_selected_options[0].text)):
                select2.select_by_value(value)
        except TimeoutException:
            print 'Loading took too much time'

        selectedOption = select2.all_selected_options[0].text
        # print 'SELECTED OPTION: ' + selectedOption

        retrieveInsertStatements(driver.page_source, selectedOption, ingr.strip())
        global nutrInfoGramsId
        nutrInfoGramsId += 1
        global nutrInfoPercId
        nutrInfoPercId += 1
        # Selecting only the specified type of ingredient
        # if re.search(type, str(child.text), re.IGNORECASE):
        #     print child.text
        #     value = child.get_attribute('value')
        #     print 'value: ' + value
        #     try:
        #         while (not (child.text == select2.all_selected_options[0].text)):
        #             select2.select_by_value(value)
        #     except TimeoutException:
        #         print 'Loading took too much time'
        #
        #     selectedOption = select2.all_selected_options[0].text
        #     print 'SELECTED OPTION: ' + selectedOption


quantityTypes = []
ingredients = []
f = open('ingredients2.txt', 'r')
content = f.read()

contentList = content.split('\n')
# print contentList

for child in contentList:
    ingr = child.split('(')[0]
    ingredients.append(ingr)

for child in contentList:
    # print str([child.find('(')+1:child.find(')')])
    quantityType = re.search(r'\((.*?)\)', child)
    if quantityType:
        quantityTypes.append(quantityType.group(1))

try:
    # for (ingr, type) in zip(ingredients, quantityTypes):
    #     print('INGREDIENT: ' + ingr + ' || ' + 'TYPE: ' + type)
    #     print('--------------------------')
    #     searchGoogleAndRetrieveValues(ingr, type)
    for x in range(61, 131):
        # nutrInfoGramsId = x+1
        # nutrInfoPercId = x+1
        ingr = ingredients[x]
        type = quantityTypes[x]
        # print('INGREDIENT: ' + ingr + ' || ' + 'TYPE: ' + type)
        # print('--------------------------')
        searchGoogleAndRetrieveValues(ingr, type)
finally:
    driver.close()
