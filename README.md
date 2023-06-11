# Financial Investment

This is a sample project to demonstrate the concept of OrderBooks in investing.

## System Description

- The system manages order books. An order book is an electronic list of orders for a specific financial instrument. A financial instrument is identified by unique instrument id.
- User can open and to close an order book for an instrument. If an order book is open, it should accept (add) orders for the given instrument. The accepted orders are immutable. If the user closes the book, no more orders are allowed to be added. 
- An order is defined with order quantity, entry date, instrument id and price. Price: there are market orders and limit orders. Limit order has a specified price value. Market order is a request for the best available price, the price value is not specified. 
- If the order book is closed, execution(s) can be added to the book. An execution has quantity and defined price. All executions will have the same price.

## Swagger

http://localhost:8080/swagger-ui/index.html
