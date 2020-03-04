#include "BasketOfNames.h"
#include <iostream>
#include <string>
#include <unordered_map>
#include <fstream>
#include <sstream>
using namespace std;

BasketOfNames::BasketOfNames(string fileName)
{
	readFile_intoMap(fileName);
}


bool BasketOfNames::hasNeighbour(Name findName, unordered_map<string, string> mapToSearch)
{
	unordered_map<string, string>::const_iterator got = mapToSearch.find(findName);

	if (got == mapToSearch.end()) // not found
	{
		return false;
	}	
	else // value found
	{
		return true;
	}
}

BasketOfNames::Neighbour BasketOfNames::findNeighbour(Name findName, unordered_map<string, string> mapToSearch)
{
	unordered_map<string, string>::const_iterator got = mapToSearch.find(findName);

	if (got != mapToSearch.end())
	{
		return got->second;
	}
}

void BasketOfNames::readFile_intoMap(std::string file)
{
	Name tempName;
	Neighbour tempNeiName;
	string line;

	ifstream namesDataFile(file);
	if (namesDataFile.is_open())
	{
		while (getline(namesDataFile, line))
		{

			stringstream ss(line);
			getline(ss, tempName, ',');
			getline(ss, tempNeiName, ',');

			WesternNames.insert(make_pair(tempName, tempNeiName));// storing the name and then the western neighbour
			EasternNames.insert(make_pair(tempNeiName, tempName));// storing name and eastern neighbour



		}
		readResultData_intoList();
	}
	else
	{
		cout << "Error opening file!" << endl;
	}
	
}

void BasketOfNames::readResultData_intoList()
{
	Name tempName = WesternNames.begin()->first;
	resultData.push_back(tempName); // starting name

	for (auto& it : WesternNames)
	{
		if (hasNeighbour(tempName, WesternNames))
		{
			resultData.push_back(findNeighbour(tempName, WesternNames)); // western neighbour
			tempName = findNeighbour(tempName, WesternNames);
		}
	}

	for (auto& it : EasternNames)
	{
		if (hasNeighbour(tempName, EasternNames)) // has neighbour
		{
			tempName = findNeighbour(tempName, EasternNames);
		}
	}
	resultData.push_front(tempName);


	for (auto i : resultData)
	{
		std::cout << i << "\n";
	}
}

//
//int main(int argc, char** argv) {
//	string file;
//	cout << "Enter absolute file path:";
//	cin >> file;
//
//	//BasketOfNames test = BasketOfNames("C:/Basket_of_Names-test_data/20/input-papers-20.txt");
//	BasketOfNames test = BasketOfNames(file);
//
//	return 0;
//}

