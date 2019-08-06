(ns quilo.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [quilo.times-tables :refer :all]))

(defn setup []
  ; Set frame rate to 30 frames per second.
  (q/frame-rate 30)
  ; Set color mode to HSB (HSV) instead of default RGB.
  (q/color-mode :hsb)
  ; setup function returns initial state. It contains
  ; circle color and position.
  {:modp 200
   :mf 2
   :radius 240
   :mf-inc 0.01
   :cX 0
   :cY 0})

(defn update-state [state]
  ; Update sketch state
  (update state :mf #(+ % (:mf-inc state))))

(defn draw-state [{:keys [cX cY radius modp mf]}]
  (let [coords (fn [x]
                 (let [hAngle (/ (* 2.0 Math/PI x) modp)]
                   [(+ cX (* radius (Math/sin hAngle)))
                    (- cY (* radius (Math/cos hAngle)))]))]
    ; Clear the sketch by filling it with light-grey color.
    (q/background 255)
    ; Move origin point to the center of the sketch.
    (q/with-translation [(/ (q/width) 2)
                         (/ (q/height) 2)]
                        ; Draw the lines.
                        (doseq [x (range 0 modp)]
                          (q/line (coords x) (coords (mod (* x mf) modp)))))))


(q/defsketch quilo
  :title "You spin my circle right round"
  :size [500 500]
  ; setup function called only once, during sketch initialization.
  :setup setup
  ; update-state is called on each iteration before draw-state.
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  ; This sketch uses functional-mode middleware.
  ; Check quil wiki for more info about middlewares and particularly
  ; fun-mode.
  :middleware [m/fun-mode])
