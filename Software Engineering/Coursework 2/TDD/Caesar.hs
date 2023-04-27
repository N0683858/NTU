module Caesar where

import Data.Char

intToLowerChar :: Int -> Char
intToLowerChar n = chr (ord 'a' + n)


lowerCharToInt :: Char -> Int
lowerCharToInt c = ord c - ord 'a'


intToUpperChar :: Int -> Char
intToUpperChar n = chr (ord 'A' + n)


upperCharToInt :: Char -> Int
upperCharToInt c = ord c - ord 'A'


encryptChar :: Int -> Char -> Char
encryptChar n c | isLower c = intToLowerChar (((lowerCharToInt c) + n) `mod` 26)
                | isUpper c = intToUpperChar (((upperCharToInt c) + n) `mod` 26)
                | otherwise = c


encrypt :: String -> Int -> String
encrypt s n = map (encryptChar n) s


decrypt :: String -> Int -> String
decrypt s n = map (encryptChar (-n)) s
