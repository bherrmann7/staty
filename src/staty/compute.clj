(ns staty.compute
  (:require [hiccup.core :as h]
            [clojure.set]
            [clojure.java.jdbc :as j]
            [clojure.edn :as edn]))

(def stats-types {:nnc      #(str "sum(case when " % " is null then 0 else 1 end)")
                  :max      #(str "max(" % ")")
                  :min      #(str "min(" % ")")
                  :distinct #(str "count(distinct(" % "))")})

(defn col-stats-sql-frag [column-metadata]
  (let [cname (:column_name column-metadata)]
    (map #(if (and (= :nnc (key %)) (.endsWith (:type column-metadata) "NOT NULL")) nil (str ((val %) cname) " " cname "_" (name (key %)))) stats-types)))

(defn make-single-type-column [col]
  (assoc (dissoc col :type_name :column_size :is_nullable)
         :type (str (:type_name col) "(" (:column_size col) ")" (if (= "NO" (:is_nullable col)) " NOT NULL" ""))))

(defn get-column-metadata [db-spec table]
  (map #(make-single-type-column
         (select-keys % [:column_name :is_nullable :type_name :column_size]))
       (j/with-db-metadata [md db-spec] (j/metadata-result (.getColumns md nil (:user db-spec) table nil)))))

(defn merge-stats [single-column-metadata db-stats-results stats-names]
  (if (empty? stats-names)
    single-column-metadata
    (let [col-name (:column_name single-column-metadata)
          stat-name (first stats-names)
          stat-pretty-name (if (= :nnc stat-name) :notnull_count stat-name)
          result-name (.toLowerCase (str col-name "_" (name stat-name)))
          result-value (get db-stats-results (keyword result-name))]
      (recur (assoc single-column-metadata stat-pretty-name result-value) db-stats-results (rest stats-names)))))

(defn merge-metadata-and-stats [columns-metadata db-stats-results]
  (map #(merge-stats % db-stats-results (keys stats-types)) columns-metadata))

(defn get-table-stats [db-spec table]
  (let [columns-metadata (get-column-metadata db-spec table)
        query-columns (into ["count(*) all_count"] (remove nil? (flatten (map col-stats-sql-frag columns-metadata))))
        sql-query (str "select " (clojure.string/join ", " query-columns) " from " table)
        db-stats-results (first (j/query db-spec sql-query))
        columns-metadata-and-stats (merge-metadata-and-stats columns-metadata db-stats-results)]
    {:table_name table :all_count (:all_count db-stats-results) :cols columns-metadata-and-stats}))

(defn stats-to-text [stats]
  (with-out-str
    (println "\nTable: " (:table_name stats) "   Rows: " (format "%,d" (:all_count stats)))
    (clojure.pprint/print-table (:cols stats))))

(defn compute-stats [username password schema table]
  (let [db-spec (assoc (edn/read-string (System/getenv "STATY_DATABASE_SPEC"))
                       :user username
                       :password password)
        stats (get-table-stats db-spec table)]
    (h/html
     [:pre (stats-to-text stats)])))

