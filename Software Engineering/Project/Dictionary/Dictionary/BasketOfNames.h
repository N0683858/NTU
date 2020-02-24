#include <iostream>
#include <string>
#include <unordered_map>
#include <fstream>
#include <sstream>

class BasketOfNames
{
public:
	using Name = std::string;
	using nameToWest = std::string;

	BasketOfNames();
	void insert(Name, nameToWest);
	nameToWest getPersonToWest(Name);
	//void remove(Name);
	void readFile_intoMap(std::string);
	void readResultData_intoList();
	
private:
	std::unordered_map<Name, nameToWest> listOfNames;
	std::list<std::string> resultData;
};

//BasketOfNames<Name, nameToWest>::BasketOfNames()
//{
//	ReadFile_intoMap("");
//}
//
//template<typename Name, typename nameToWest>
//void BasketOfNames<Name, nameToWest>::insert(Name name, nameToWest westernName)
//{
//	listOfNames[name] = westernName;
//}
//
//template<typename Name, typename nameToWest>
//nameToWest BasketOfNames<Name, nameToWest>::getPersonToWest(Name)
//{
//	return NULL;
//}
//
//template<typename Name, typename nameToWest>
//void BasketOfNames<Name, nameToWest>::remove(Name)
//{
//
//}
//
//template<typename Name, typename nameToWest>
//void BasketOfNames<Name, nameToWest>::ReadFile_intoMap(std::string file)
//{
//Name tempName;
//nameToWest tempWestName;
//string line;
//
//ifstream namesDataFile(file);
//if (namesDataFile.is_open())
//{
//	while (getline(namesDataFile, line))
//	{
//		stringstream ss(line);
//		getline(ss, tempName, ',');
//		getline(ss, tempWestName, ',');
//
//		insert(tempName, tempWestName);
//	}
//}
//else
//{
//	cout << "Error opening file!" << endl;
//}
//
//	
//}
