import React from "react";
import {Product} from "./Product";

export function ProductList({products = [], onAddClick}) {
  return (
      <React.Fragment>
        <h5 className="flex-grow-0"><b>상품 목록</b></h5>
        <ul className="list-group products">
          {products.map(
              p =>
                  <li key={p.productId} className="list-group-item d-flex mt-3">
                    <Product {...p} onAddClick={onAddClick}/>
                  </li>
          )}
        </ul>
      </React.Fragment>
  )
}