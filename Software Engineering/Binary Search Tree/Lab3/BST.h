#pragma once
#include <iostream>
#include <cassert>
class BST
{
public:
	using Key = int;
	using Item = std::string;

	void insert(Key, Item);
	Item* lookup(Key);
	//////////
	BST() = default; //defult constructor
	BST(const BST &); // copy constructor
	BST& operator=(const BST&); //copy assignment operator
	/////////
	BST(BST &&);// Move constructor 
	BST& operator=(BST&&);// Move assignment Operator 
	//////////
	void displayEntries();
	void displayTree();
	void removeNode(Key);
	~BST(); //deconsructor
	

private:
	struct Node;
	Node* root = nullptr;

	static bool isLeaf(Node*);//check of node is leaf of not
	static Item* lookupRec(Key, Node*);//find a specific value using the key
	static void insertRec(Key, Item, Node*&);//insert data into tree
	///////////////
	void displayEntriesWorker(Node*);//display the data in the tree
	void displayTreeWorker(Node*, std::string);//display the whole tree
	//////////////
	void removeNodeWorker(Key, Node*&);//removes a certain node
	Node* detachMinimumNode(Node* &);//deteches the minimum node from tree (helps with removeNode)
	void deepDelete(Node*);//deletes everything 
	static Node* deepCopy(Node*);//makes an identical copy 
	/////////////////
	static void rotateRight(Node*&);//used when the tree has too many left children and is unbalanced
	static void rotateLeft(Node*&);//used to balance the tree when too many right childeren 

};
