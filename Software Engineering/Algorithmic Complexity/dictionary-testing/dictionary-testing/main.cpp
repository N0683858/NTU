#include <iostream>
#include <chrono>
#include <utility>
#include <map>

#include "keyitemgenerator.h"

using std::string;
using std::cout;
using std::endl;
using std::map;
using std::chrono::steady_clock;
using std::chrono::milliseconds;
using std::chrono::duration_cast;


/* timingTestMap(n) creates a std::map<int,string>, and then
 * performs n random insertions followed by n random lookups.
 * The time taken (in milliseconds) for the lookups is returned.
 */
milliseconds timingTest_map(unsigned long long int n, unsigned int dicSize)
{
   KeyItemGenerator gen = KeyItemGenerator();
   /*  If no seed value is given, each KeyItemGenerator object will
    *  produce the same sequence of keys/items.
    *  If we want different pseudo-random keys/items from each object
    *  (and each program run), then we should provide a seed value.
    *  For example:
    *
    *   unsigned int seed = steady_clock::now().time_since_epoch().count();
    *   KeyItemGenerator gen = KeyItemGenerator(seed);
   */

   map<int,string> dict = {};

   for (unsigned long long int i = 0; i < dicSize; ++i)
   {
       dict.insert(make_pair(gen.randomKey(),gen.randomItem()));
       /* Note: std::map::insert() differs slightly from BST::insert(),
        * in that it does not overwrite the item if the key already exists.
        */
   }

   steady_clock::time_point startTime = steady_clock::now();

   for (unsigned long long int i = 0; i < n; ++i)
   {
       dict.find(gen.randomKey());
       /* Note: std::map::find() differs slightly from BST::lookup(),
        * in that it returns an *iterator* to the key/item pair.
        */
   }

   steady_clock::time_point finishTime = steady_clock::now();

   return duration_cast<milliseconds>(finishTime - startTime);
}


int main()
{
    const unsigned long long int n = 1500000;
	int dicSize = 1000000;

    milliseconds timeTaken = timingTest_map(n, dicSize);

    cout << "Data structure: std::map (red-black tree)" << endl;
    cout << "Testing " << n << " random lookups in a dictionary containing " << dicSize << " random entries." << endl;
    cout << "Time taken: " << timeTaken.count() << " milliseconds." << endl;

    return 0;
}
