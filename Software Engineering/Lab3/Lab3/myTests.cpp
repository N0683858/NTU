#define BOOST_TEST_MODULE MyModuleName
#include <boost/test/included/unit_test.hpp>
#include "BST.h"


BOOST_AUTO_TEST_CASE(test_look_up)
{
	// CHECK IF YOU LOOKUP FUNCTION WORKS
	BST newTree;
	BOOST_CHECK_EQUAL(newTree.lookup(2), nullptr);
}

BOOST_AUTO_TEST_CASE(test_instert_and_lookup)
{
	// CHECK IF YOU LOOKUP FUNCTION WORKS
	BST newTree;
	BOOST_CHECK_NO_THROW(newTree.insert(20, "Jerry")); //check to see if it insters the data
	BOOST_CHECK_EQUAL(newTree.lookup(2), nullptr); // check to see if lookup points to nullpointer as the data doesnt exist
	BOOST_REQUIRE_NE(newTree.lookup(20), nullptr); // chech to see if lookup doesnt give nullpointer when data DOES exist
	BOOST_CHECK_EQUAL(*newTree.lookup(20), "Jerry"); // check to see if lookup finds the data which should be jerry
}


BOOST_AUTO_TEST_CASE(insert_data_into_tree)
{
	BST newTree;

	BOOST_CHECK_NO_THROW(newTree.insert(20, "Jerry"));
	BOOST_CHECK_NO_THROW(newTree.insert(22, "Marry"));
	BOOST_CHECK_NO_THROW(newTree.insert(60, "Andy"));
	BOOST_CHECK_NO_THROW(newTree.insert(9, "Edward"));
	BOOST_CHECK_NO_THROW(newTree.insert(10, "Mars"));
	BOOST_CHECK_NO_THROW(newTree.insert(2, "Bob"));
	BOOST_CHECK_NO_THROW(newTree.insert(28, "Tom"));
	BOOST_CHECK_NO_THROW(newTree.insert(46, "Anne"));
}

BOOST_AUTO_TEST_CASE(display_tree)
{
	BST newTree;

	BOOST_CHECK_NO_THROW(newTree.insert(20, "Jerry"));
	BOOST_CHECK_NO_THROW(newTree.insert(22, "Marry"));
	BOOST_CHECK_NO_THROW(newTree.insert(60, "Andy"));
	BOOST_CHECK_NO_THROW(newTree.insert(9, "Edward"));
	BOOST_CHECK_NO_THROW(newTree.insert(10, "Mars"));
	BOOST_CHECK_NO_THROW(newTree.insert(2, "Bob"));
	BOOST_CHECK_NO_THROW(newTree.insert(28, "Tom"));
	BOOST_CHECK_NO_THROW(newTree.insert(46, "Anne"));

	BOOST_CHECK_NO_THROW(newTree.displayTree());
}

BOOST_AUTO_TEST_CASE(delloate_removed_nodes)
{
	BST newTree;

	BOOST_CHECK_NO_THROW(newTree.insert(20, "Jerry"));
	BOOST_CHECK_NO_THROW(newTree.insert(22, "Marry"));
	BOOST_CHECK_NO_THROW(newTree.insert(60, "Andy"));
	BOOST_CHECK_NO_THROW(newTree.insert(9, "Edward"));
	BOOST_CHECK_NO_THROW(newTree.insert(10, "Mars"));
	BOOST_CHECK_NO_THROW(newTree.insert(2, "Bob"));
	BOOST_CHECK_NO_THROW(newTree.insert(28, "Tom"));
	BOOST_CHECK_NO_THROW(newTree.insert(46, "Anne"));

	BOOST_CHECK_NO_THROW(newTree.removeNode(9));
	BOOST_CHECK_EQUAL(newTree.lookup(9), nullptr);
}

BOOST_AUTO_TEST_CASE(deep_deleteing_shallow_copy)
{
	BST t1;
	t1.insert(2, "Will");
	BST t2 = t1;
	t2.insert(3, "Mary");

	BOOST_CHECK_NE(t1.lookup(3), nullptr);
	BOOST_CHECK_EQUAL(*t1.lookup(3), "Mary");
}


