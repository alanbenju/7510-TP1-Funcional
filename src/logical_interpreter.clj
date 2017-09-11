(ns logical-interpreter)
(use '[clojure.string])

(defn exist [data query]
  ;Chech if query exist in the list data
  (let [tupla (seq data)]
    (if tupla (if (= (first tupla) query) true (recur (rest tupla) query)) false)
  )
)

(defn getList [database]
  ;Convert database(String) to a list with no spaces or ugly things
(split (clojure.string/replace (clojure.string/replace (clojure.string/replace database "\n" "") "  " "") "\t" "") #"\.")
)

(defn isFact [x] (if (= (count (split x #":-")) 1) true false ))
(defn isRule [x] (if (not= (count (split x #":-")) 1) true false))

(defn getRuleName [query] (first (split query #"\(")) )
  
(defn prepareDic [rule query]
  ;prepare a map with the variables as key and values as values
  (def params (join "" (split (first (split rule #":-")) #" "))) 
  (def listParams (split (join "" (split (join "" (rest (split params #"\("))) #"\)")) #","))
  (def listParamsQuery (split (join "" (split (join "" (rest (split query #"\("))) #"\)")) #","))
  (zipmap (map keyword listParams) listParamsQuery) 
)

(defn getRuleMatch [rules query]
  ;;match the query and rule names and returns the rule
  (let [tupla (seq rules)]
    (if tupla (if (= (getRuleName query) (getRuleName (first tupla))) (first tupla) (recur (rest tupla) query)) nil))
)

(defn myReplace [init aKey value] (clojure.string/replace init (name aKey) value))

(defn continueRule [rule query] (reduce-kv myReplace (join "" (rest (split rule #":-"))) (prepareDic rule query)))

(defn checkRules [rules query]
  ;;Return a string if found the rule or nil in other case
  (def rule (getRuleMatch rules query))
  (cond (= rule nil) nil :else (continueRule rule query))
)

(defn deleteSpace [x] (not= x " "))

(defn checkAllFactsInDB [facts allFacts]
  ;check facts of Rule in allFacts and return the correct boolean
  (def noSpaceFacts (join "" (filter deleteSpace (split (join "" allFacts) #""))))
  (def factListReady (split (clojure.string/replace noSpaceFacts #"\)" ")-") #"-" ))
  (def result (map (fn [fact] (exist factListReady fact)) facts) )
  (every? true? result)
)

(defn keepSearching [toCheckInDB facts]
  ;prepare fact for search
  (def noSpaceFacts (join "" (filter deleteSpace (split toCheckInDB #""))))
  (def factListReady (split (clojure.string/replace noSpaceFacts #"\)," ")-") #"-"))  
  (checkAllFactsInDB factListReady facts)
)

(defn ruleWay [rules query facts]
  ;query is a rule, start correct
  (def toCheckInDB (checkRules rules query))
  (cond (= toCheckInDB nil) false :else (keepSearching toCheckInDB facts))
)

;Check if name of query exist in db
(defn existRuleInDB [database query]
    (let [tupla (seq database)]
    (if tupla (if (= (getRuleName query) (getRuleName (first tupla))) true (recur (rest tupla) query)) false)
  )
)

(defn start [allList query]
  ;Start looking for the veracity of the query
  (def facts (filter isFact allList)) ;facts from database
  (def rules (filter isRule allList)) ;rules from database
  (cond (exist facts query) true :else (ruleWay rules query facts))
)

;check if query is not well written
(defn correctQuery [query] (= (count  (split query #"\(")) 2))

;check if x is no well written or not a rule
(defn notFactOrRule [x] (and (= (correctQuery x) false) (= (isRule x) false)))

;check if list of filtered database is empty
(defn isIncomplete [database] (> (count (filter notFactOrRule database)) 0))

(defn evaluate-query [database query]
  (def allList (getList database)) ;database in a list
  (cond
    (= (isIncomplete allList) true) nil
    (= (correctQuery query) false) nil
    :else (start allList query)
    )
)