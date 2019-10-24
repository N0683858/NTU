#pragma once
#include <iostream>
class BST
{
public:
	using Key = int;
	using Item = std::string;

	void insert(Key, Item);
	Item* lookup(Key);
	BST() = default;
	void displayEntries();
	void displayTree();
	void removeNode(Key);
	

private:
	struct Node;
	Node* root = nullptr;

	static bool isLeaf(Node*);
	static Item* lookupRec(Key, Node*);
	static void insertRec(Key, Item, Node*&);
	void displayEntriesWorker(Node*);
	void displayTreeWorker(Node*, std::string);
	void removeNodeWorker(Key, Node*&);
};

