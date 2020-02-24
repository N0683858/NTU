#include "BasketOfNames.h"
#include <iostream>
#include <string>
#include <unordered_map>
#include <fstream>
#include <sstream>
using namespace std;

BasketOfNames::BasketOfNames()
{
	readFile_intoMap("");//path to text file
}

void BasketOfNames::insert(Name name, nameToWest westernName)
{
	listOfNames[name] = westernName;
}

BasketOfNames::nameToWest BasketOfNames::getPersonToWest(Name findName)
{
	unordered_map<string, string>::const_iterator got = listOfNames.find(findName);

	if (got == listOfNames.end()) // not found
	{
		return NULL;
	}	
	else
	{
		return got->second; //get value
	}
	
}

//void BasketOfNames::remove(Name)
//{
//
//}

void BasketOfNames::readFile_intoMap(std::string file)
{
	Name tempName;
	nameToWest tempWestName;
	string line;

	ifstream namesDataFile(file);
	if (namesDataFile.is_open())
	{
		while (getline(namesDataFile, line))
		{
			stringstream ss(line);
			getline(ss, tempName, ',');
			getline(ss, tempWestName, ',');

			insert(tempName, tempWestName);
		}
	}
	else
	{
		cout << "Error opening file!" << endl;
	}
}

void BasketOfNames::readResultData_intoList()
{
	Name startingName = listOfNames.begin()->first;
	nameToWest startingNameValue = listOfNames.begin()->second;
	
}


