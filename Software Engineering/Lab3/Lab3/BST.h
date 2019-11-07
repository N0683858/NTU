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
	BST(const BST &);
	BST& operator=(const BST&);
	void displayEntries();
	void displayTree();
	void removeNode(Key);
	~BST();
	

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

