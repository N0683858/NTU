#include <iostream>
#include <string>

namespace Containers {
	template <typename key, typename item>
	class Dictionary
	{
	public:
		using Key = key;
		using Item = item;

		// Constructor //
		Dictionary() = default; // defult constructor
		// Copy Constructor //
		Dictionary(const Dictionary&); // Copy constructor 
		Dictionary& operator=(const Dictionary&); // Copy assignemnt constructor 
		// Move Constructor //
		Dictionary(Dictionary&&); // Move construvtor
		Dictionary& operator=(Dictionary&&); // Move assignment operator

		bool insert(Key, Item);
		Item* lookup(Key);
		bool remove(Key);
		template <typename F>
		void removeIf(F);


		~Dictionary(); // Deconstructor

	private:
		struct Node;
		Node* root = nullptr;

		static bool isLeaf(Node*); // Check if node is a Leaf or not
		static Item* lookupWorker(Key, Node*); // find a specific value using the key
		static bool insertWorker(Key, Item, Node*&); // insert data into dictionary

		bool removeNodeWorker(Key, Node*&); // remove worker function 
		void deepDelete(Node*); // deletes everything 
		static Node* deepCopy(Node*); // makes an identical copy

		template <typename F>
		void removeIfWorker(F, Node*&);
	};

//______________________________________Definition_____________________________________________//

//---------- Node ----------//
	template <typename Key, typename Item>
	struct Dictionary<Key, Item>::Node
	{
		Key key;
		Item item;
		Node* child;

		Node(Key k, Item i)
		{
			key = k;
			item = i;

			child = nullptr;
		}
	};

	//---------- Copy Constructor ----------//
	template<typename Key, typename Item>
	Dictionary<Key, Item>::Dictionary(const Dictionary& original)
	{
		this->root = deepCopy(original.root);
	}


	//---------- Copy Assignment Operator ----------//
	template<typename Key, typename Item>
	Dictionary<Key, Item>& Dictionary<Key, Item>::operator=(const Dictionary& original)
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

	//-------- Move Constructor ---------//
	template<typename Key, typename Item>
	Dictionary<Key, Item>::Dictionary(Dictionary&& original)
	{
		this->root = original.root;
		original.root = nullptr;
	}

	//---------- Move Assignement Operator ----------//
	template<typename Key, typename Item>
	Dictionary<Key, Item>& Dictionary<Key, Item>::operator=(Dictionary&& original)
	{
		if (this != &original)
		{
			deepDelete(this->root);
			this->root = original.root;
			original.root = nullptr;
		}

		return *this;
	}

	//---------- insert() ----------//
	template<typename Key, typename Item>
	bool Dictionary<Key, Item>::insert(Key k, Item i)
	{
		return insertWorker(k, i, root);
	}

	//---------- lookup() ----------//
	template<typename Key, typename Item>
	Item* Dictionary<Key, Item>::lookup(Key soughtKey)
	{
		return lookupWorker(soughtKey, root);
	}

	//---------- remove() ----------//
	template<typename Key, typename Item>
	bool Dictionary<Key, Item>::remove(Key k)
	{
		return removeNodeWorker(k, root);
	}

	//---------- Deconstructor ----------//
	template<typename Key, typename Item>
	Dictionary<Key, Item>::~Dictionary()
	{
		deepDelete(root);
	}

	//---------- isLeaf() ----------//
	template<typename Key, typename Item>
	bool Dictionary<Key, Item>::isLeaf(Node* n)
	{
		return n == nullptr;
	}

	//---------- lookup() worker ----------//
	template<typename Key, typename Item>
	Item* Dictionary<Key, Item>::lookupWorker(Key soughtKey, Node* currentNode)
	{
		if (isLeaf(currentNode))
		{
			return nullptr;
		}
		else
		{
			if (soughtKey != currentNode->key)
			{
				return lookupWorker(soughtKey, currentNode->child);
			}
			else
			{
				return &currentNode->item;
			}
		}
	}

	//---------- insert() worker ----------//
	template<typename Key, typename Item>
	bool Dictionary<Key, Item>::insertWorker(Key k, Item i, Node*& current)
	{
		if (isLeaf(current))
		{
			current = new Node(k, i);
			return true;
		}
		else if (k == current->key)
		{
			current->item = i;
		}
		else 
		{
			insertWorker(k, i, current->child);
		}
		return false;
	}

	//---------- remove() node worker ----------//
	template<typename Key, typename Item>
	bool Dictionary<Key, Item>::removeNodeWorker(Key k, Node*& current)
	{
		if (k != current->key)
		{
			if (isLeaf(current))
			{
				return false;
			}
			else
			{
				removeNodeWorker(k, current->child);
			}
		}
		else
		{
			if (isLeaf(current->child))
			{
				delete current;
				current = nullptr;
			}
			else
			{
				Node* newNode = current->child;
				delete current;
				current = newNode;
			}

			/*Node* newNode = detachNode(current->child);
			Node* newNode = current->child;
			delete current;
			current = newNode;*/
			return true;
		}
	}

	//---------- detachNode() ----------//
	/*template<typename Key, typename Item>
	Dictionary<Key, Item>::Node* Dictionary<Key, Item>::detachNode(Node*& node)
	{
		if (isLeaf(node->child))
		{
			Node* copy = new Node(node->key, node->item);
			removeNode(node->key);
			return copy;
		}
		else
		{
			detachNode(node->child);
		}
	}*/

	//---------- Deep delete ----------//
	template<typename Key, typename Item>
	void Dictionary<Key, Item>::deepDelete(Node* current)
	{
		if (!isLeaf(current))
		{
			deepDelete(current->child);
			delete current;
		}
	}

	//---------- Deep copy ----------//
	template<typename Key, typename Item>
	typename Dictionary<Key, Item>::Node* Dictionary<Key, Item>::deepCopy(Node* original)
	{
		if (!isLeaf(original))
		{
			Node* newNode = new Node(original->key, original->item);
			newNode->child = deepCopy(original->child);
			return newNode;
		}
		else
		{
			return nullptr;
		}
	}
	
	//---------- removeIf() ----------//
	template<typename Key, typename Item>
	template<typename F>
	void Dictionary<Key, Item>::removeIf(F function)
	{
		removeIf(function, root);
	}

	//---------- removeIf() worker ----------//
	template<typename Key, typename Item>
	template<typename F>
	void Dictionary<Key, Item>::removeIfWorker(F function, Node*& current)
	{
		if (!isLeaf(current))
		{
			if (function(current->key))
			{
				remove(current->key);
			}

			removeIfWorker(function, current->child);
		}
	}

}
