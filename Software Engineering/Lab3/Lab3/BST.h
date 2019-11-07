#pragma once
#include <iostream>
class BST
{
public:
	using Key = int;
	using Item = std::string;

	void insert(Key, Item);
	Item* lookup(Key);
	BST() = default; //defult constructor
	BST(const BST &); // copy constructor
	BST& operator=(const BST&); //copy assignment operator
	BST(BST &&);
	void displayEntries();
	void displayTree();
	void removeNode(Key);
	~BST(); //deconsructor
	

private:
	struct Node;
	Node* root = nullptr;

	static bool isLeaf(Node*);
	static Item* lookupRec(Key, Node*);
	static void insertRec(Key, Item, Node*&);
	void displayEntriesWorker(Node*);
	void displayTreeWorker(Node*, std::string);
	void removeNodeWorker(Key, Node*&);
	Node* detachMinimumNode(Node* &);
	void deepDelete(Node*);
	static Node* deepCopy(Node*);
};

