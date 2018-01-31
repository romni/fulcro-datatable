# `fulcro-datatable`

`fulcro-datatable` is a `fulcro` component inspired by [DataTables](https://datatables.net/).

### Latest release

[![Clojars Project](https://clojars.org/fulcro-datatable/latest-version.svg)](https://clojars.org/fulcro-datatable)


## Usage

`fulcro-datatable` can be used like in the following devcard:

```clojure
(defcard-fulcro sample-table
                table/Table
                (prim/get-initial-state
                  table/Table
                  {:table/id      1                         ;; the table id can be any type (eg. string or keyword)
                   :table/data    [{                        ;; the initial data which is loaded into the table
                                    :name    "John"         ;; the keys in this map are used to order the data
                                    :phone   "1-234-567"    ;; by column
                                    :address "1 Street Dr"  ;; note that the data is supplied as an array of maps
                                    }                       ;; where each map corresponds to a row in the table
                                   {
                                    :name    "Hank"
                                    :phone   "1-345-678"
                                    :address "2 Street Dr"
                                    }]
                   :table/columns [{                          ;; the column configuration
                                    :column/name     "Name"   ;; this name is used as the column heading
                                    :column/label    :name    ;; this label corresponds to the key in a row
                                    :column/sortable false    ;; whether the column is sortable
                                    :column/type     :letter  ;; :column/type can be one of #{:letter, :number, :none}
                                                              ;; defaults to :none
                                    }
                                   {
                                    :column/name     "Phone Number"
                                    :column/label    :phone
                                    :column/sortable true
                                    :column/type     :number
                                    }
                                   {
                                    :column/name     "Address"
                                    :column/label    :address
                                    :column/sortable true
                                    }]
                   :table/sort     {                          ;; determines the sort order of the rows
                                    :sort/by    :name         ;; the label of the column to sort by
                                    :sort/order :ascending    ;; the sort order can be one of #{:ascending, :descending, :none}
                                                              ;; defaults to :none
                                    }}
                   :table/styles   #{:bordered :hover}        ;; defaults to #{:striped :bordered :hover}
                   :table/page     {                          ;; the pagination configuration
                                    :page/pagesize 2          ;; rows per page
                                    :page/total    2          ;; total rows in the dataset
                                    })
                {:inspect-data true})
```

`fulcro-datatable` expects that you have implemented a `defquery-entity` for `:table/by-id` (which
usually goes into `app.api.read`):

```clojure
(defquery-entity :table/by-id
                 "Answer the :table/by-id query. This is how a table is refreshed."
                 (value [env id {:keys [table/sort table/columns table/page] :as params}]
                        (prim/get-initial-state table/Table
                                                {:table/id      id       ;; retain the id of the table
                                                 :table/data    data     ;; compute the new data after sorting
                                                 :table/columns columns  ;; retain the supplied columns
                                                 :table/sort    sort     ;; retain the supplied sorting order
                                                 :table/page    page}))) ;; retain page settings, current page is updated
                                                                         ;; on page change
```

## License

`fulcro`:

Copyright (c) 2017, Fulcrologic, LLC The MIT License (MIT)

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

`fulcro-datatable`:

Copyright (c) 2017, Rimon Oz The MIT License (MIT)

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.


