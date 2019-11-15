#include "HashTable.h"


HashTable::HashTable()
{

}

void HashTable::insert(Key, Item)
{


}

HashTable::Item* HashTable::lookup(Key)
{

	
	
	return nullptr;
}

void HashTable::remove(Key)
{

}

unsigned int HashTable::hash(const Key& word)
{
	//hash the key to integar hash value 
	int wordLength = word.length();
	int sum = 0;
	//Apply modulo operation to the hash value to get an array index
	for (int i = 0; i < wordLength; i++)
	{
		sum =+ word[i];
	}

	return sum;
}
