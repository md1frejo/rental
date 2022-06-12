(ns rental.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [rental.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[rental started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[rental has shut down successfully]=-"))
   :middleware wrap-dev})
