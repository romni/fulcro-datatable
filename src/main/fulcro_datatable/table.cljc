(ns fulcro-datatable.table
  (:require
    [fulcro.client.primitives :as prim :refer [defsc]]
    [fulcro.client.dom :as dom]
    [fulcro.ui.bootstrap3 :as b]
    [fulcro.client.data-fetch :as df]))

(defsc TableRow [this {:keys [row/id row/data row/order]}]
       {:initial-state (fn [{:keys [id data order] :as params}]
                         {:row/id id :row/data data :row/order order})
        :query         [:row/id :row/data :row/order]}
       (dom/tr #js {:key id}
               (map #(dom/td #js {:key (str id "-" (first %))} (second %))
                    (conj {} (select-keys data order)))))

(def ui-table-row (prim/factory TableRow {:keyfn :row/id}))

(defn create-sorting-glyph
  [that colname coltype table-id sort columns page]
  (let [new-order (case (:sort/order sort)
                    :ascending :descending
                    :descending :none
                    :ascending)]
    (dom/a #js {:onClick #(df/refresh! that
                                       {:params {:table/sort    (assoc sort :sort/order new-order
                                                                            :sort/by colname)
                                                 :table/columns columns
                                                 :table/page    (assoc page :page/current 1)}})}
           (b/glyphicon {}
                        (if (= (:sort/by sort) colname)
                          (case coltype
                            :number (case (:sort/order sort)
                                      :ascending :sort-by-attributes
                                      :descending :sort-by-attributes-alt
                                      :sort)
                            :letter (case (:sort/order sort)
                                      :ascending :sort-by-alphabet
                                      :descending :sort-by-alphabet-alt
                                      :sort)
                            (case (:sort/order sort)
                              :ascending :sort-by-order
                              :descending :sort-by-order-alt
                              :sort))
                          :sort)))))

(defsc Table [this {:keys [table/id table/data table/columns table/sort table/page table/styles ui/react-key]}]
       {:initial-state (fn [{:keys [table/id table/data table/columns table/sort table/page table/styles] :as params}]
                         {:table/id      id
                          :table/data    (vec (map-indexed
                                                (fn [index item]
                                                  (prim/get-initial-state
                                                    TableRow
                                                    {:id    index
                                                     :order (mapv :column/label columns)
                                                     :data  item}))
                                                data))
                          :table/columns columns
                          :table/sort    sort
                          :table/page    ((fnil #(assoc % :page/current 1) {:page/pagesize (count data)
                                                                            :page/total    (count data)})
                                           page)
                          :table/styles  (or styles #{:striped :bordered :hover})})
        :ident         [:table/by-id :table/id]
        :query         [:table/id {:table/data (prim/get-query TableRow)} :table/columns :table/sort :table/page :table/styles :ui/react-key]}
       (dom/div #js {:key react-key}
                (b/table {:styles styles}
                         (dom/thead #js {}
                                    (dom/tr #js {}
                                            (map #(dom/th #js {:key (:column/name %)}
                                                          (:column/name %) " "
                                                          (when (:column/sortable %)
                                                            (create-sorting-glyph
                                                              this
                                                              (:column/label %)
                                                              (:column/type %)
                                                              id
                                                              sort
                                                              columns
                                                              page)))
                                                 columns)))
                         (dom/tbody #js {} (map ui-table-row data)))
                (let [total-pages (Math/ceil (/ (:page/total page) (:page/pagesize page)))]
                  (if (> total-pages 1)
                    (dom/div #js {:className b/text-center}
                             (map (fn [current-page]
                                    (b/button {:onClick #(df/refresh! this
                                                                      {:params {:table/sort    sort
                                                                                :table/columns columns
                                                                                :table/page    (assoc page :page/current (inc current-page))}})
                                               :kind    (if (= (inc current-page) (:page/current page)) :primary)
                                               :key     (str id "-page-button-" (inc current-page))}
                                              (let
                                                [first-row (inc (* current-page (:page/pagesize page)))
                                                 last-row (Math/min (:page/total page) (dec (+ first-row (:page/pagesize page))))]
                                                (if (= first-row last-row)
                                                  first-row
                                                  (str first-row "-" last-row)))))
                                  (range 0 total-pages)))))))

(def ui-table (prim/factory Table))
