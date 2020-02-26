#include <iostream>
#include <string>
#include <unordered_map>
#include <fstream>
#include <sstream>

class BasketOfNames
{
public:
	using Name = std::string;
	using Neighbour = std::string;

	BasketOfNames(std::string);
	
private:
	std::unordered_map<Name, Neighbour> WesternNames;
	std::unordered_map<Name, Neighbour> EasternNames;

	std::list<std::string> resultData;

	void readResultData_intoList();
	//void insert(Name, Neighbour, std::unordered_map<Name, Neighbour>);
	bool hasNeighbour(Name, std::unordered_map<std::string, std::string>);
	Neighbour findNeighbour(Name, std::unordered_map<std::string, std::string>);
	void readFile_intoMap(std::string);
};
