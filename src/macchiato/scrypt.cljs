(ns macchiato.scrypt
  (:require [cljs.nodejs :as node]))

(def scrypt (node/require "scrypt"))

(def default-opts {:N 1 :r 1 :p 1})

(defn encrypt
  "encrypts the password, uses 0.1 for max-time as the default
  should be wrapped with try/catch"
  ([raw] (encrypt raw 0.1))
  ([raw max-time]
   (scrypt.kdfSync raw (scrypt.paramsSync max-time))))

(defn check
  "compares the raw password with the hash, returns a boolean"
  [raw hash]
  (scrypt.verifyKdfSync hash raw))

(defn encrypt-async
  "accepts raw password, map with options, and a callback
  the callback receives the following parameters: err, result"
  ([raw opts] (encrypt-async raw opts nil))
  ([raw opts cb]
   (if cb
     (scrypt.kdf raw (clj->js opts) cb)
     (scrypt.kdf raw (clj->js opts)))))

(defn check-async
  "checks the raw password against the hash
  the callback receives the following parameters: err, result
  result is a boolean"
  ([raw hash]
   (scrypt.verifyKdf hash raw))
  ([raw hash cb]
   (scrypt.verifyKdf hash raw cb)))