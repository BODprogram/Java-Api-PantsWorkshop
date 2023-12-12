
Получение детальной информации о всех текущих товарах на складе 
java -jar target/pantsworkshop-1.0-SNAPSHOT-jar-with-dependencies.jar XML getDetailedCheck
Пример вывода:
2023-03-27 14:21:15 INFO  TestingClient:88 - SUCCESS
2023-03-27 14:21:15 INFO  TestingClient:89 - information about all current goods in the warehouse is read

Получение стоимости джинс
java -jar target/pantsworkshop-1.0-SNAPSHOT-jar-with-dependencies.jar JDBC specialOrder 37 1 950 blackCat
Пример вывода:
2023-03-27 14:22:07 INFO  TestingClient:104 - SUCCESS
2023-03-27 14:22:07 INFO  TestingClient:105 - jeans price
2023-03-27 14:22:07 INFO  TestingClient:106 - 950

Получение времени на создание джинс
java -jar target/pantsworkshop-1.0-SNAPSHOT-jar-with-dependencies.jar XML getProductionTime 40 1 303 indigo normal none denim 8
Пример вывода:
2023-03-27 14:22:59 INFO  TestingClient:125 - SUCCESS
2023-03-27 14:22:59 INFO  TestingClient:126 - time to make jeans
2023-03-27 14:22:59 INFO  TestingClient:127 - 8

Получение цены всех текущих компонентов 
java -jar target/pantsworkshop-1.0-SNAPSHOT-jar-with-dependencies.jar JDBC getComponentPrice
Пример вывода:
2023-03-27 14:25:53 INFO  TestingClient:137 - SUCCESS
2023-03-27 14:25:53 INFO  TestingClient:138 - the price of components in stock is calculated
2023-03-27 14:25:53 INFO  TestingClient:139 - 138

Получение разницы между затраченными на товары деньгами и полученной прибылью
java -jar target/pantsworkshop-1.0-SNAPSHOT-jar-with-dependencies.jar CSV getProfit
Пример вывода:
2023-03-27 14:27:24 INFO  TestingClient:149 - SUCCESS
2023-03-27 14:27:24 INFO  TestingClient:150 - profit calculated
2023-03-27 14:27:24 INFO  TestingClient:151 - 3525

Высчитывание стоимости создаваемых на заказ джинс
java -jar target/pantsworkshop-1.0-SNAPSHOT-jar-with-dependencies.jar CSV calculateJeansCost 40 1 303 indigo normal none denim 8
Пример вывода:
2023-03-27 14:29:32 INFO  TestingClient:170 - SUCCESS
2023-03-27 14:29:32 INFO  TestingClient:171 - jeans price
2023-03-27 14:29:32 INFO  TestingClient:172 - 4888

Высчитывание стоимости всех компонентов для этого экземпляра джинс
java -jar target/pantsworkshop-1.0-SNAPSHOT-jar-with-dependencies.jar XML getComponentCost 40 1 303 indigo normal none denim 8
Пример вывода:
2023-03-27 14:30:04 INFO  TestingClient:191 - SUCCESS
2023-03-27 14:30:04 INFO  TestingClient:192 - the cost of all components for jeans
2023-03-27 14:30:04 INFO  TestingClient:193 - 44

Высчитывание прибыли 
java -jar target/pantsworkshop-1.0-SNAPSHOT-jar-with-dependencies.jar CSV summariseAllProfit
Пример вывода:
2023-03-27 14:30:22 INFO  TestingClient:203 - SUCCESS
2023-03-27 14:30:22 INFO  TestingClient:204 - Profit
2023-03-27 14:30:22 INFO  TestingClient:205 - 3787




