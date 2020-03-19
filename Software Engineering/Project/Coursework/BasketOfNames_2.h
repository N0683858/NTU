#include <iostream>
#include <string>
#include <map>
#include <fstream>
#include <sstream>
#include <chrono>
#include <list>

using std::chrono::steady_clock;
using std::chrono::milliseconds;
using std::chrono::duration_cast;

class BasketOfNames_2
{
public:
	using Name = std::string;
	using Neighbour = std::string;

	BasketOfNames_2(std::string);

private:
	std::map<Name, Neighbour> WesternNames;
	std::map<Name, Neighbour> EasternNames;

	std::list<std::string> resultData;

	void readResultData_intoList();
	//void insert(Name, Neighbour, std::unordered_map<Name, Neighbour>);
	bool hasNeighbour(Name, std::map<std::string, std::string>);
	Neighbour findNeighbour(Name, std::map<std::string, std::string>);
	void readFile_intoMap(std::string);
};

