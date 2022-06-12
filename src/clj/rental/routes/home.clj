(ns rental.routes.home
  (:require
   [rental.layout :as layout]
   [rental.db.core :as db]
   [clojure.java.io :as io]
   [rental.middleware :as middleware]
   [ring.util.response]
   [ring.util.http-response :as response]
   [selmer.parser :as selmer]
   [struct.core :as st]))

(defn home-page [request]
  (layout/render request "home.html" {:docs (-> "docs/docs.md" io/resource slurp)}))

(defn about-page [request]
  (layout/render request "about.html"))

(defn bulmatest [req]
  (layout/render req "bulmatest.html" {}))

(defn rental [req]
  (layout/render req "rental.html" {}))

(defn getvalues [req]

  (let [hyra (Float/parseFloat (get-in req [:params :hyra] "61000"))
        tarea (Float/parseFloat (get-in req [:params :tarea] "980"))
        parea (Float/parseFloat (get-in req [:params :parea] "167"))
        barea (Float/parseFloat (get-in req [:params :barea] "187")) 
        omk (Float/parseFloat (get-in req [:params :omk] "53000"))
        hkv (+ (/ hyra  tarea) (/ omk tarea))
        pb (+ parea barea)
        totk (float (/ (/ (* hkv pb) 15) 10))
        tid  (Float/parseFloat (get-in req [:params :tid] "8"))]
  
    {:name (get-in req [:params :name] "noname")
     :org (get-in req [:params :org] "999")        
     :parea parea
     :barea barea
     :omk omk
     :tid tid
     :start (get-in req [:params :start] "1")
     :stop (get-in req [:params :stop] "2")
     :notes (get-in req [:params :notes] "notes here")
     :pb pb
     :tk (+ hyra omk)
     :hyra hyra
     :tarea tarea
     :hkv hkv
     :kost totk ;; (/ (/ (* 114.68 359.48) 15) 10) extra org: 10 siffror med -
     :kost2 (float (* totk tid))
     :test (float (+ parea 0))
     :block (get-in req [:path-params :block] "stora salen")

     :area1 (get-in req [:path-params :area1] "167")
     :area2 (get-in req [:path-params :area2] "187")}))
  
(defn btable [req]
  (let [latest (getvalues req)
    old (db/getrows)]
    (layout/render req "btable.html" {:latest latest :old old})))

(defn bookings [req]
  (layout/render req "bookings.html" {:pages "2"}))

(defn btable2 [req]
  (selmer/render req "btable.html" {}))

(defn add [req]
  (layout/render req "test.html" {:var1 "test"}))

(defn save [{:keys [seq]}]
  (db/addb! (assoc seq :name "w" :org "212" :parea 1221 :barea 1221 :tarea 3233 :hyra 22233 :omk 3233 :tid 23 :start 2332 :stop 233 :notes "sd" :kost 232 :kost 2343)))

(defn save4 [req]
  ;; INSERT INTO rent(name,org,parea,barea,tarea,hyra,omk,tid,start,stop,notes,kost,kost2) VALUES ("jonas", "sdf", 23,34,65,323232,43433434,23,43,56,"notes",65,34)
 (db/addb! {:name "jonas" :org "sdf" :parea 23 :barea 34 :tarea 65 :hyra 323232 :omk 43433434 :tid 23 :start 43 :stop 56 :notes "notes" :kost 65 :kost2 34}))
 ;; (db/addb (getvalues(req))))

(defn delete [req]
  (let [id (get-in req [:params :id] 10)]
;;    [id2 [{keys [id]}]]
    (db/delrow {:id id}))
  (btable req))

(defn par [{:keys [params]}]    ;; [{:keys [params] :as m}]
;;  (try (db/addb! params)
  ;;       (catch Exception e (throw (ex-info (keys params) {:in m})))))
  (db/addb! params))
  ;; (layout/render latest "test.html" {:data latest :data2 (keys latest)})) ;; {:name (get-in req [:params :name] "noname")

(defn home-routes []
  [ "" 
   ;; {:middleware [middleware/wrap-csrf
   ;;               middleware/wrap-formats]}
   ["/" {:get home-page}]
   ["/about" {:get about-page}]
   ["/bulmatest" bulmatest]
   ["/rental" rental]
   ["/jan" rental]
   ["/bookings" bookings]
   ["/btable/:block/:area1/:area2" btable]
   ["/add" add]
   ["/save2" (fn [req] (db/addb! (getvalues req)))]
   ["/save" {:post save}]
   ["/delete" delete]
   ["/test" par]
   ["/test2" (fn [req] (db/addb! {:name "jonas" :org "sdf" :parea 23 :barea 34 :tarea 65 :hyra 3232 :omk 4343 :tid 23 :start 43 :stop 56 :notes "notes" :kost 65 :kost2 34}))]])

;; (defn btable3 [req]
;;   (let [block (get-in req [:path-params :block] "stora salen")
;;         area1 (get-in req [:path-params :area1] 167)
;;         area2 (get-in req [:path-params :area2] 187)
;;         name (get-in req [:params :name] "noname")
;;         org (get-in req [:params :org] "999")        
;;         parea (Float/parseFloat (get-in req [:params :parea] "167")) 
;;         barea (Float/parseFloat (get-in req [:params :barea] "187")) 
;;         tarea (Float/parseFloat (if (get-in req [:params :tarea]) (get-in req [:params :tarea]) "980"))
;;         hyra (Float/parseFloat (if (get-in req [:params :hyra]) (get-in req [:params :hyra]) "61000")) 
;;         omk (Float/parseFloat (if (get-in req [:params :omk]) (get-in req [:params :omk]) "53000")) 
;;         tid (Float/parseFloat (if (get-in req [:params :tid]) (get-in req [:params :tid]) "8"))
;;         start (get-in req [:params :start] "1")
;;         stop (get-in req [:params :stop] "2")
;;         notes (get-in req [:params :notes] "nothing yet")
;;         pb (+ parea barea)
;;         tk (+ hyra omk)
;;         hkv (+ (/ hyra tarea) (/ omk tarea))
;;         totk (float (/ (/ (* hkv pb) 15) 10))   ;; (/ (/ (* 114.68 359.48) 15) 10) extra org: 10 siffror med -
;;         totk2 (float (* totk tid))
;;         test(float (+ parea 0))
;;         latest {:block block :org org :name name :parea parea :barea barea :hyra hyra :omk omk :tid tid :start start :stop stop :kost totk :kost2 totk2 :notes notes}
;; ;;        add (db/addb! {:id 999 :org org :name name :parea parea :barea barea :tarea tarea :hyra hyra :omk omk :tid tid :start start :stop stop :kost totk;;                      :kost2 totk2 :notes notes}) 
;;         old (db/getrows)]

;;     (layout/render req "btable.html" {:id "sen" :latest latest :old old :area1 area1 :area2 area2})))



