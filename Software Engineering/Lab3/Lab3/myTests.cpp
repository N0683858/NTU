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
	BOOST_CHECK_NO_THROW(newTree.insert(60, "Jerry"));
	BOOST_CHECK_NO_THROW(newTree.insert(9, "Edward"));
	BOOST_CHECK_NO_THROW(newTree.insert(10, "Jerry"));
	BOOST_CHECK_NO_THROW(newTree.insert(2, "Jerry"));
	BOOST_CHECK_NO_THROW(newTree.insert(28, "Jerry"));
	BOOST_CHECK_NO_THROW(newTree.insert(46, "Jerry"));
}

BOOST_AUTO_TEST_CASE(display_tree)
{
	BST newTree;

	BOOST_CHECK_NO_THROW(newTree.insert(20, "Jerry"));
	BOOST_CHECK_NO_THROW(newTree.insert(22, "Marry"));
	BOOST_CHECK_NO_THROW(newTree.insert(60, "Jerry"));
	BOOST_CHECK_NO_THROW(newTree.insert(9, "Edward"));
	BOOST_CHECK_NO_THROW(newTree.insert(10, "Jerry"));
	BOOST_CHECK_NO_THROW(newTree.insert(2, "Jerry"));
	BOOST_CHECK_NO_THROW(newTree.insert(28, "Jerry"));
	BOOST_CHECK_NO_THROW(newTree.insert(46, "Jerry"));

	BOOST_CHECK_NO_THROW(newTree.displayTree());
}
