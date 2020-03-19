#include "BasketOfNames_2.h"
#include <iostream>
#include <string>
#include <map>
#include <unordered_map>
#include <fstream>
#include <sstream>
using namespace std;

BasketOfNames_2::BasketOfNames_2(string fileName)
{
	readFile_intoMap(fileName);
}

bool BasketOfNames_2::hasNeighbour(Name findName, map<string, string> mapToSearch)
{
	map<string, string>::const_iterator got = mapToSearch.find(findName);

	if (got == mapToSearch.end()) // not found
	{
		return false;
	}
	else // value found
	{
		return true;
	}
}

BasketOfNames_2::Neighbour BasketOfNames_2::findNeighbour(Name findName, map<string, string> mapToSearch)
{
	map<string, string>::const_iterator got = mapToSearch.find(findName);

	if (got != mapToSearch.end())
	{
		return got->second;
	}
}

void BasketOfNames_2::readFile_intoMap(std::string file)
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

void BasketOfNames_2::readResultData_intoList()
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
			resultData.push_front(findNeighbour(tempName, EasternNames)); // eastern neighbour
			tempName = findNeighbour(tempName, EasternNames);
		}
	}


	for (auto i : resultData)
	{
		std::cout << i << "\n";
	}
}


int main(int argc, char** argv) {

	if (argc == 2)
	{
		//steady_clock::time_point startTime = steady_clock::now();
		BasketOfNames_2 test = BasketOfNames_2(argv[1]);
		//	steady_clock::time_point finishTime = steady_clock::now();
		//	std::cout << "Time taken: ", finishTime - startTime;
	}

	return 0;
}

