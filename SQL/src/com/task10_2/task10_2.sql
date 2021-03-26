USE mydb;
/*Task-1
Найдите номер модели, скорость и размер жесткого диска для всех ПК стоимостью менее 500 дол. Вывести: model, speed и hd
*/
SELECT model, speed, hd 
FROM pc
WHERE price < 500;
/*Task-2
Найдите производителей принтеров. Вывести: maker
*/
SELECT DISTINCT maker 
FROM product 
WHERE type = 'Printer';
/*Task-3
Найдите номер модели, объем памяти и размеры экранов ПК-блокнотов, цена которых превышает 1000 дол.
*/
SELECT model, ram, screen 
FROM laptop
WHERE price > 1000;
/*Task-4
Найдите все записи таблицы Printer для цветных принтеров.
*/
SELECT * 
FROM printer
WHERE color = 'y';
/*Task-5
Найдите номер модели, скорость и размер жесткого диска ПК, имеющих 12x или 24x CD и цену менее 600 дол.
*/
SELECT model, speed, hd 
FROM pc
WHERE cd IN (12, 24)
AND price < 600;
/*Task-6
Укажите производителя и скорость для тех ПК-блокнотов, которые имеют жесткий диск объемом не менее 100 Гбайт.
*/
SELECT maker, speed 
FROM laptop
LEFT JOIN product USING (model)
WHERE hd > 100;  
/*Task-7
Найдите номера моделей и цены всех продуктов (любого типа), выпущенных производителем B (латинская буква).
*/
SELECT * 
FROM (SELECT model, price 
 FROM pc
 UNION SELECT model, price 
 FROM laptop
 UNION SELECT model, price 
 FROM printer
 ) AS tmp
WHERE tmp.model IN (SELECT model 
 FROM mydb.product
 WHERE maker = 'B'
 );
/*Task-8
Найдите производителя, выпускающего ПК, но не ПК-блокноты.
*/
SELECT DISTINCT maker
FROM product
WHERE type IN ('PC')
AND maker NOT IN (SELECT maker 
FROM product 
WHERE type IN ('Laptop'));
-- SELECT maker
-- FROM product
-- GROUP BY maker
-- HAVING SUM(type = 'PC') <> 0
-- AND SUM(type = "Laptop") = 0;
/*Task-9
Найдите производителей ПК с процессором не менее 450 Мгц. Вывести: Maker
*/
SELECT DISTINCT maker AS Maker
FROM product
JOIN pc USING(model)
WHERE speed > 450
AND type IN ('PC');
/*Task-10
Найдите принтеры, имеющие самую высокую цену. Вывести: model, price
*/
SELECT model, price
FROM printer
WHERE price = (SELECT MAX(price)
FROM printer);
/*Task-11
Найдите среднюю скорость ПК.
*/
SELECT ROUND(AVG(speed)) AS Average_speed
FROM pc;
/*Task-12
Найдите среднюю скорость ПК-блокнотов, цена которых превышает 1000 дол.
*/
SELECT ROUND(AVG(speed)) AS Average_speed
FROM laptop
WHERE price > 1000;
/*Task-13
Найдите среднюю скорость ПК, выпущенных производителем A.
*/
SELECT ROUND(AVG(speed)) AS Average_speed
FROM pc
WHERE model IN (SELECT model
FROM product
WHERE maker = 'A');
/*Task-14
Для каждого значения скорости найдите среднюю стоимость ПК с такой же скоростью процессора. Вывести: скорость, средняя цена
*/
SELECT speed, ROUND(AVG(price),2) 
FROM pc
GROUP BY speed;
/*Task-15
Найдите размеры жестких дисков, совпадающих у двух и более PC. Вывести: HD
*/
SELECT hd AS HD
FROM pc
GROUP BY hd
HAVING COUNT(model) > 1;
/*Task-16
Найдите пары моделей PC, имеющих одинаковые скорость и RAM. 
В результате каждая пара указывается только один раз, т.е. (i,j), но не (j,i), 
Порядок вывода: модель с большим номером, модель с меньшим номером, скорость и RAM.
*/
SELECT B.model AS model, A.model AS model, A.speed, A.ram 
FROM pc AS A, pc AS B 
WHERE A.speed = B.speed 
AND A.ram = B.ram 
AND A.model < B.model 
/*Task-17
Найдите модели ПК-блокнотов, скорость которых меньше скорости любого из ПК.
Вывести: type, model, speed
*/
SELECT type, laptop.model, speed
FROM laptop
JOIN product USING(model)
WHERE speed < (SELECT MIN(speed)
FROM pc);
/*Task-18
Найдите производителей самых дешевых цветных принтеров. Вывести: maker, price
*/
SELECT maker, price
FROM product
JOIN printer USING(model)
WHERE color = 'y'
AND price = (SELECT MIN(price)
FROM printer
WHERE color = 'y');
/*Task-19
Для каждого производителя найдите средний размер экрана выпускаемых им ПК-блокнотов. 
Вывести: maker, средний размер экрана.
*/
SELECT maker, ROUND(AVG(screen),2)
FROM product
JOIN laptop USING(model)
WHERE type IN ('Laptop')
GROUP BY maker;
/*Task-20
Найдите производителей, выпускающих по меньшей мере три различных модели ПК. 
Вывести: Maker, число моделей
*/
SELECT maker, COUNT(pc.model)
FROM product
JOIN pc USING(model)
GROUP BY maker
HAVING COUNT(pc.model) > 2;
/*Task-21
Найдите максимальную цену ПК, выпускаемых каждым производителем. 
Вывести: maker, максимальная цена.
*/
SELECT maker, MAX(price)
FROM product
JOIN pc USING(model)
WHERE type = 'PC'
GROUP By maker;
/*Task-22
Для каждого значения скорости ПК, превышающего 600 МГц, определите среднюю цену ПК с такой же скоростью. 
Вывести: speed, средняя цена.
*/
SELECT speed, ROUND(AVG(price),2) AS 'средняя цена'
FROM pc
WHERE speed > 600
GROUP BY speed;
/*Task-23
Найдите производителей, которые производили бы как ПК со скоростью не менее 750 МГц, 
так и ПК-блокноты со скоростью не менее 750 МГц. Вывести: Maker
*/
SELECT DISTINCT maker AS Maker
FROM pc
JOIN product USING(model)
WHERE speed >= 750
AND maker IN(SELECT maker
FROM laptop
JOIN product USING(model)
WHERE speed >= 750);
/*Task-24
Перечислите номера моделей любых типов, 
имеющих самую высокую цену по всей имеющейся в базе данных продукции.
*/
SELECT model
FROM(SELECT model, price
FROM pc
UNION SELECT model, price
FROM laptop
UNION SELECT model, price
FROM printer) AS tmp
WHERE price = (SELECT MAX(price)
FROM (SELECT price
FROM pc
UNION SELECT price
FROM laptop
UNION SELECT price
FROM printer) AS max_price);
/*Task-25
Найдите производителей принтеров, которые производят ПК с наименьшим объемом RAM 
и с самым быстрым процессором среди всех ПК, имеющих наименьший объем RAM. 
Вывести: Maker
*/
SELECT DISTINCT maker AS Maker
FROM product
WHERE model IN (SELECT model
FROM pc
WHERE ram = (
  SELECT MIN(ram)
  FROM pc)
AND speed = (
  SELECT MAX(speed)
  FROM pc
  WHERE ram = (
   SELECT MIN(ram)
   FROM pc
   )))
AND maker IN (SELECT maker
FROM product
WHERE type = 'Printer');