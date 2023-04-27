{-# LANGUAGE TemplateHaskell #-}
module Main where

import Caesar
import Data.Char
import Test.QuickCheck


-- check if a char or space
isACharOrSpace :: Char -> Bool
isACharOrSpace c = (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || c == ' '


-- property-based tests


-- check encrypted char is a char or space
prop_encryptedIsCharOrSpace :: Char -> Int -> Bool
prop_encryptedIsCharOrSpace c n = isACharOrSpace(encryptChar n c)

-- check decrypted char is a char or space
prop_decryptedIsCharOrSpace :: LetterWithKey -> Bool
prop_decryptedIsCharOrSpace c n = isACharOrSpace(encryptChar (-n) c)

-- check encrypted message length is same as original length of input
prop_isEncryptSameLength :: String -> Int -> Bool
prop_isEncryptSameLength input key = length(input) == length(encrypt input key)

-- check decrypted message length is same as original length of input
prop_isDecryptSameLength :: String -> Int -> Bool
prop_isDecryptSameLength input key = length(input) == length(decrypt input key)

-- check encrypting changes input unless an empty input
prop_encryptingChangesInput :: Char -> Int -> Bool
prop_encryptingChangesInput input key = length(input) == 0 || input /= encrypt input key

-- check decrypting changes input unless an empty input
prop_decryptingChangesInput :: Char -> Int -> Bool
prop_decryptingChangesInput input key = length(input) == 0 || input /= decrypt input key                                       

-- check encrypting and then decrypting gives the original input
prop_encryptingThenDecryptingEqual :: Char -> Int -> Bool
prop_encryptingThenDecryptingEqual input key = decrypt(encrypt message key) key == input



return []
main = $quickCheckAll
