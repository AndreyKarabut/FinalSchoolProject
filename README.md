# FinalSchoolProject

06.11.22 MVP - версия.

После запуска программы при запросах на http://localhost:8080 возможна следущая функциональность:
 - /getBalance/:id , где :id - идентификатор пользователя в бд. Возвращает значение "balance" 
 - /takeMoney/:id&:money где :id - идентификатор пользователя в бд, ;money - количество средств для списания. Если операция возможна, 
 отнимает значение 'money' от 'balance', возвращает значение "balance". в противном случае возвращает описание ошибки.
 - /putMoney/:id&:money - аналогично takeMoney.
 
 
![image](https://user-images.githubusercontent.com/75555989/200172349-4f6eeaa3-5ff4-4f7b-b677-2bed07886354.png)

