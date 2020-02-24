#include <iostream>
#include <string>
#include <unordered_map>
#include <fstream>
#include <sstream>

class BasketOfNames
{
public:
	using Name = std::string;
	using neighbour = std::string;

	BasketOfNames();
	void insert(Name, neighbour);
	neighbour getPersonToWest(Name);
	//void remove(Name);
	void readFile_intoMap(std::string);
	void readResultData_intoList();
	
private:
	std::unordered_map<Name, neighbour> listOfNames;
	std::list<std::string> resultData;
};
