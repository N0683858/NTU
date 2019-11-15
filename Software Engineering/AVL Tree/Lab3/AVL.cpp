#include "AVL.h"
#include <iostream>
#include <string>

struct AVL::Node
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

void AVL::insert(Key k, Item i)
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

void AVL::insertRec(Key k, Item i, Node*& current)
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



bool AVL::isLeaf(Node* n)
{
	// Define function here
	return n == nullptr;
}

AVL::Item* AVL::lookup(Key soughtKey)
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

AVL::Item* AVL::lookupRec(Key soughtKey, Node* currentNode)
{
	// worker definition goes here

	if (isLeaf(currentNode))
	{
		return nullptr;
	}
	else
	{
		if (soughtKey != currentNode->key)
		{
			 if (currentNode->key < soughtKey)
			 {
				 return lookupRec(soughtKey, currentNode->rightChild);
			 }
			else 
			 {
				return lookupRec(soughtKey, currentNode->leftChild);
			 }
		}
		else 
		{
			return &currentNode->item;
		}
		
	}
	
}

void AVL::displayEntries()
{
	//this is wrapper function
	displayEntriesWorker(root);
}

void AVL::displayTree() {
	displayTreeWorker(root,"");
}

void AVL::displayEntriesWorker(Node* current)
{

	if (current != nullptr)
	{
		displayEntriesWorker(current->leftChild);
		std::cout << current->key << "\t" << current->item << std::endl;
		displayEntriesWorker(current->rightChild);

	}

}

void AVL::displayTreeWorker(Node* current, std::string indent) {

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
/////////////////////////
void AVL::removeNode(Key k)
{
	removeNodeWorker(k, root); 
}

//////////////////////////
AVL::AVL(const AVL & original) 
{
	this->root = deepCopy(original.root);
}

/////////////////////////////////
AVL & AVL::operator=(const AVL& original)
{
	if (this == &original)
	{
		return *this;
	}
	else
	{
		this->deepDelete(root);
		this->root = deepCopy(original.root);
		return *this;
	}
	
}
///////////////////////////
AVL::AVL(AVL && original)
{
	this->root = original.root;
	original.root = nullptr;
}
////////////////////////////
AVL& AVL::operator=(AVL&& original)
{
	//steal the nodes from original tree
	if (this != &original) 
	{
		deepDelete(this->root);
		this->root = original.root;
		original.root = nullptr;
	}

	return *this;
}


///////////////////////////
AVL::~AVL()
{
	deepDelete(root);
}
////////////////////////////
void AVL::removeNodeWorker(Key k, Node* &current)
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
		else
		{
			Node* newNode = detachMinimumNode(current->rightChild);
			newNode->leftChild = current->leftChild;
			newNode->leftChild = current->rightChild;
					   
			delete current;
			current = newNode;
		}
	}
}
////////////////////////////////////////
AVL::Node* AVL::detachMinimumNode(Node*& node)
{
	if (isLeaf(node->leftChild))
	{
		Node* copy = new Node(node->key, node->item);
		removeNode(node->key);
		node = nullptr;
		return copy;
	}
	else
	{
		detachMinimumNode(node->leftChild);
	}
}
///////////////////////////////////////
void AVL::deepDelete(Node* current)
{
	if (!isLeaf(current)) 
	{
		deepDelete(current->leftChild);
		deepDelete(current->rightChild);
		delete current;
	}
}
////////////////////////////////////
AVL::Node* AVL::deepCopy(Node* original)
{
	if (!isLeaf(original))
	{
		Node* newNode = new Node(original->key,original->item);
		newNode->leftChild = deepCopy(original->leftChild);
		newNode->rightChild = deepCopy(original->rightChild);
		return newNode;
	}
	else
	{
		return nullptr;
	}
}
///////////////////////////////////
void AVL::rotateRight(Node* & localRoot)
{
	Node* b = localRoot;
	Node* a = b->leftChild;
	/*Node* alpha = a->leftChild;*/
	Node* beta = a->rightChild;
	/*Node* gamma = b->rightChild;*/

	localRoot = a;
	assert(!isLeaf(b));
	b->leftChild = beta;
	assert(!isLeaf(a));
	a->rightChild = b;
}

void AVL::rotateLeft(Node* &localRoot)
{
	Node* a = localRoot;
	Node* b = a->rightChild;
	//Node* alpha = a->leftChild;
	Node* beta = b->leftChild;
	//Node* gamma = b->rightChild;

	localRoot = b;
	assert(!isLeaf(b));
	b->leftChild = a;
	assert(!isLeaf(a));
	a->rightChild = beta;
}
