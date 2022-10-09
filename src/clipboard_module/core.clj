(ns clipboard-module.core
  (:require [clojure.tools.cli :refer [parse-opts]]
            [seesaw.core :refer [native!
                                 frame
                                 input
                                 pack!
                                 show!
                                 #_display
                                 text
                                 config
                                 config!]]
            [seesaw.timer :refer [timer]]
            [seesaw.border :refer [line-border]]
            [seesaw.dnd :as dnd]
            [seesaw.clipboard :refer [contents contents! system]])
  (:import [java.security MessageDigest]))

(defn -main [& args] (println "hello world"))

;; look at https://yomguithereal.github.io/clj-fuzzy/clojure.html
;; for fuzzy match alogrithms

;; Seesaw Blog: https://nathanwilliams.github.io/2013/05/15/seesaw-gui-programming-the-clojure-way/


(defn md5
  [^String s]
  (->> s
       .getBytes
       (.digest (MessageDigest/getInstance "MD5"))
       (BigInteger. 1)
       (format "%032x")))

(defn add-to-history [current-val]
  (prn current-val))


(defn check-clipboard [state]
  (let [current-val (contents)]
    (cond
      (nil? current-val) state
      (not= current-val state) (do (add-to-history current-val)
                                   current-val)
      :else state)))


(defn clipboard-poller []
  (let [tmr (timer
             check-clipboard
             :initial-value "")]
    tmr))


(defn mk-frame []
  (let [f   (frame :title "CLIP-UTIL")
        ops {:show        (fn [] (-> f pack! show!))
             :set-content (fn [content]
                            (config! f :content content)
                            content)}]
    (fn [operation & args] (-> (ops operation) (apply args)))))




(comment
  *e

  (def main-frame (mk-frame))

  (main-frame :show)
  (main-frame :set-content (text :listen [:document #(prn (bean %))]))

  (md5 "abcdefk")

  (time (md5 "abcdefkdkdkdkdkkkdkdk"))

  (count (md5 "kdkdkdkdk"))


  (def poller (clipboard-poller))

  (.start poller)
  (.stop poller)

  (not= 12 1)
  (def s-atom (atom ""))

  (deref s-atom)

  (reset! s-atom "absbbs")



  (class 1.0)

  (.hashCode "8675309")

  (class (.hashCode "8675309"))


  (def bonus (* 160000.0 0.090))
  bonus
  (- bonus (* 0.22 bonus))

  *out*

  ;;
  )
