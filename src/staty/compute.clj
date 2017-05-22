(ns staty.compute
  (:require [hiccup.core :as h]))

(def mysql-db {:dbtype "mysql" :dbname "quiz" :user "quiz" :password "quiz"}

 (defn get-column-metadata [table] (map #(select-keys % [:column_name :is_nullable :type_name :column_size]) (j/with-db-metadata [md mysql-db] (j/metadata-result (.getColumns md nil nil table nil))))))

(defn compute-stats [username password schema table]
  (let [ db-spec {:dbtype "mysql" :dbname schema :user user :password password}
        stats (get-table-stats db-spec table)]
    (h/html
      [:h1 "Stats for " schema " " table]
      [:div "Row Count " (:row-count stats)])))
