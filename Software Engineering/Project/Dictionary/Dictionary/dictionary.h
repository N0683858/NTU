#include <iostream>
#include <string>

namespace Containers {
	template <typename Key, typename Item>
	class Dictionary
	{
	public:
		//using Key = std::string;// ... must support at least std::string
		//using Item = std::string;// ... must support at least std::string

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

		~Dictionary(); // Deconstructor

	private:
		struct Node;
		Node* root = nullptr;

		static Item* lookupRec(Key, Node*);
		static bool insertRec(Key, Node*);

		void removeNodeWorker(Key, Node*);
		void deepDelete(Node);
		static Node* deepCopy(Node*);
	};

	template <typename Key, typename Item>
	struct Dictionary<Key, Item>::Node
	{
		Key key;
		Item item;
		Node* child;

		Node(key k, item i)
		{
			key = k;
			item = i;

			child = nullptr;

		}
	};

	template<typename Key, typename Item>
	Dictionary<Key, Item>::Dictionary(const Dictionary&)
	{

	}


	//------- Copy Assignment Operator ----------//
	template<typename Key, typename Item>
	Dictionary<Key, Item>& Dictionary<Key, Item>::operator=(const Dictionary& original)
	{
		// TODO: insert return statement here
	}

	////-------- Move Constructor ---------//
	template<typename Key, typename Item>
	Dictionary<Key, Item>::Dictionary(Dictionary&&)
	{
	}

	//---------- Move Assignement Operator ----------//
	template<typename Key, typename Item>
	Dictionary<Key, Item>& Dictionary<Key, Item>::operator=(Dictionary&& original)
	{
		// TODO: insert return statement here
	}

	// insert() //
	template<typename Key, typename Item>
	bool Dictionary<Key, Item>::insert(Key, Item)
	{
		return false;
	}

	// lookup() //
	template<typename Key, typename Item>
	Item* Dictionary<Key, Item>::lookup(Key)
	{
		return NULL;
	}

	// remove() //
	template<typename Key, typename Item>
	bool Dictionary<Key, Item>::remove(Key)
	{
		return false;
	}

	// Deconstructor //
	template<typename Key, typename Item>
	Dictionary<Key, Item>::~Dictionary()
	{
	}

	template<typename Key, typename Item>
	Item* Dictionary<Key, Item>::lookupRec(Key, Node*)
	{
		return NULL;
	}

	template<typename Key, typename Item>
	bool Dictionary<Key, Item>::insertRec(Key, Node*)
	{
		return false;
	}

	template<typename Key, typename Item>
	void Dictionary<Key, Item>::removeNodeWorker(Key, Node*)
	{
	}

	template<typename Key, typename Item>
	void Dictionary<Key, Item>::deepDelete(Node)
	{
	}

	template<typename Key, typename Item>
	Dictionary<Key, Item>::Node* Dictionary<Key, Item>::deepCopy(Node*)
	{
		return NULL;
	}
}
