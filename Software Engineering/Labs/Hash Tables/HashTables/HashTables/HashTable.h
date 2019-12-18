#pragma once
#include <iostream>
class HashTable
{
public:
	using Key = std::string;
	using Item = std::string;

	HashTable();

	void insert(Key, Item);
	Item* lookup(Key);
	void remove(Key);

private:
	static unsigned int hash(const Key&);//Hash the key to an integar hash value
};

