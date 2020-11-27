(ns valuehash.specs
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]
            [clojure.java.io :as io]))

(def byte-array-class (class (byte-array 0)))

(defn byte-array? [obj] (instance? byte-array-class obj))
(defn input-stream? [obj] (instance? java.io.InputStream obj))

(s/def ::digest-fn
  (s/fspec
   :args (s/cat :input-stream (s/spec input-stream? :gen #(->> (byte-array 0)
                                                               (io/input-stream)
                                                               (gen/return))))
   :ret byte-array?))

(s/fdef valuehash.api/digest
  :args (s/cat :digest-fn ::digest-fn, :obj any?)
  :ret byte-array?)

(s/fdef valuehash.api/mesagedigest-fn
  :args (s/cat :algorithm string?)
  :ret ::digest-fn)

(s/fdef valuehash.api/hex-str
  :args (s/cat :byte-array byte-array?)
  :ret string?)

