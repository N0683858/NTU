module Main where

import Caesar


main :: IO ()
main = do

    putStr "Input: "
    input <- getLine
    putStr "Key: "
    key <- getLine
    let k = (read key :: Int)
    
    print(encrypt input k)
    
