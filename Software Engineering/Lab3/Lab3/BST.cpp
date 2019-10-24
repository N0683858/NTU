#include "BST.h"
#include <iostream>
#include <string>

struct BST::Node
{
	Key key;
	Item item;
	Node* leftChild;
	Node* rightChild;

	Node(Key k, Item i) {
		key = k;
		item = i;

		leftChild = nullptr;
		rightChild = nullptr;
	}
};

void BST::insert(Key k, Item i)
{
	/*Node* currentNode = root;
	Node* prevNode;

	while (currentNode->key != k)
	{
		if (currentNode->key < k)
		{
			if (isLeaf(currentNode)) {
				currentNode->rightChild = new Node(k, i);
			}
			currentNode = currentNode->rightChild;
		}
		else {
			if (isLeaf(currentNode)) {
				currentNode->leftChild = new Node(k, i);
			}
			currentNode = currentNode->leftChild;
		}

		prevNode = currentNode;
	}

	//currentNode->key = k;
	currentNode->item = i; */

	insertRec(k, i, root); //worker function 
}

void BST::insertRec(Key k, Item i, Node*& current)
{
	// worker definition goes here
	if (isLeaf(current)) {
		current = new Node(k, i);
	}
	else if (k == current->key) {
		current->item = i;
	}
	else {

		if (current->key < k)
		{
			insertRec(k, i, current->rightChild);
		}
		else {
			insertRec(k, i, current->leftChild);
		}
	}
	
}



bool BST::isLeaf(Node* n)
{
	// Define function here
	return n == nullptr;
}

BST::Item* BST::lookup(Key soughtKey)
{
	// Implementation goes here (wrapper)

	/*Node* current = root;

	while (soughtKey != current->key)
	{
		if (isLeaf(current)) {
			return nullptr;
		}
		else if (current->key < soughtKey)
		{
			current = current->rightChild;
		}
		else {
			current = current->leftChild;
		}
	}

	return &current->item; */

	return lookupRec(soughtKey, root); //wrapper
}

BST::Item* BST::lookupRec(Key soughtKey, Node* currentNode)
{
	// worker definition goes here

	if (soughtKey != currentNode->key)
	{
		if (isLeaf(currentNode)) {
			return nullptr;
		}
		else if (currentNode->key < soughtKey)
		{
			return lookupRec(soughtKey, currentNode->rightChild);
		}
		else {
			return lookupRec(soughtKey, currentNode->leftChild);
		}
	}
	return &currentNode->item;
}

void BST::displayEntries()
{
	//this is wrapper function
	displayEntriesWorker(root);
}

void BST::displayTree() {
	displayTreeWorker(root,"");
}

void BST::displayEntriesWorker(Node* current)
{

	if (current != nullptr)
	{
		displayEntriesWorker(current->leftChild);
		std::cout << current->key << "\t" << current->item << std::endl;
		displayEntriesWorker(current->rightChild);

	}

}

void BST::displayTreeWorker(Node* current, std::string indent) {

	if (isLeaf(current)) 
	{
		std::cout << indent + "*" << std::endl;
	}
	else 
	{
		std::cout << indent + std::to_string(current->key) << std::endl;
		displayTreeWorker(current->leftChild, indent + "  ");
		displayTreeWorker(current->rightChild, indent + "  ");
	}
}

void BST::removeNode(Key k)
{
	removeNodeWorker(k, root); 
}


void BST::removeNodeWorker(Key k, Node* &current)
{
	if (k != current->key)
	{
		if (isLeaf(current)) {
			std::cout << "Node does not exist" << std::endl;
		}
		else if (current->key < k)
		{
			removeNodeWorker(k, current->rightChild);
		}
		else {
			removeNodeWorker(k, current->leftChild);
		}
	}
	else
	{
		if (isLeaf(current->leftChild) && isLeaf(current->rightChild))
		{
			delete current;
			current = nullptr;
		}
		else if (isLeaf(current->leftChild) && !isLeaf(current->rightChild))
		{
			Node* newNode = current->rightChild;
			delete current;
			current = newNode;
		}
		else if (!isLeaf(current->leftChild) && isLeaf(current->rightChild))
		{
			Node* newNode = current->leftChild;
			delete current;
			current = newNode;
		}
	}
	
}
