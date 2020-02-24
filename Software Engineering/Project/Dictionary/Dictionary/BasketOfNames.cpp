#include "BasketOfNames.h"
#include <iostream>
#include <string>
#include <unordered_map>
#include <fstream>
#include <sstream>
using namespace std;

BasketOfNames::BasketOfNames()
{
	readFile_intoMap("");
}

void BasketOfNames::insert(Name name, nameToWest westernName)
{
	listOfNames[name] = westernName;
}

BasketOfNames::nameToWest BasketOfNames::getPersonToWest(Name)
{
	return NULL;
}

void BasketOfNames::remove(Name)
{

}

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


