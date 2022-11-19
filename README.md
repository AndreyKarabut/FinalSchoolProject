# FinalSchoolProject

06.11.22 MVP - версия.

После запуска программы при запросах на http://localhost:8080 возможна следущая функциональность:
 - /getBalance/:id , где :id - идентификатор пользователя в бд. Возвращает значение "balance" 
 - /takeMoney/:id&:money где :id - идентификатор пользователя в бд ; money - количество средств. Если операция возможна, 
 отнимает значение 'money' от 'balance', возвращает значение "balance". в противном случае возвращает описание ошибки.
 - /putMoney/:id&:money - аналогично takeMoney, только изменяет значение в большую сторону.
 
 ![image](https://user-images.githubusercontent.com/75555989/200172349-4f6eeaa3-5ff4-4f7b-b677-2bed07886354.png)
 

13.11.22 добавлена функция "/getOperationList/:userId&date1&date2", где userId - обязательный параметр, date1 / date2 - дата в формате 20221113235959, либо "-" когда ограничение по времени не требуется. 
 
 ![image](https://user-images.githubusercontent.com/75555989/201518430-4b1cad5d-7b21-4bba-a329-877fc9de855b.png)
 
 19.11.22 добавлена функция /transfer_money/ с 3 параметрами через &, id отправителя, id принимающего, money количество средств;
 Переработана работа в рамках sql. Теперь записи в таблицу operationlist делаются не через триггеры.
 
![image](https://user-images.githubusercontent.com/75555989/202849378-24b65a42-e82b-4ba5-8f2b-37f9ae4ad44b.png)
