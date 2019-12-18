#include <iostream>

namespace Containers {
template <typename Key, typename Item>
	class Dictionary
	{
	public:
		//using Key = std::string;// ... must support at least std::string
		//using Item = std::string;// ... must support at least std::string
			Dictionary();
		bool insert(Key, Item);
		Item* lookup(Key);
		bool remove(Key);
	};
}