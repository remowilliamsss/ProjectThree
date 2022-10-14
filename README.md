# ProjectThree

Задача:
Создать REST API сервис, который будет принимать данные от
"сенсора".

Описывается адрес API, данные которые передаются при запросе на
этот адрес и тот функционал, который должен предоставляться в
результате запроса на этот адрес:

1. POST-запрос на /sensors/registration

{
    "name": "Sensor name"
}
   
Регистрирует новый сенсор в системе. Другими словами, просто добавляет новый
сенсор в таблицу сенсоров в БД. У сенсоров есть только одно
поле - название.
Вы должны использовать DTO для входящего объекта - сенсора.
Также, вы должны валидировать то, что сенсора с таким названием еще нет в БД. Если сенсор с таким названием есть в
БД - возвращать клиенту сообщение с ошибкой.
Также, если название сенсора пустое или содержит менее 3 или более 30 символов,
клиенту должно возвращаться сообщение с ошибкой.

2. POST-запрос на /measurements/add

{
    "value": 24.7,
    "raining": false,
    "sensor": {
        "name": "Sensor name"
    }
}
   
Добавляет новое измерение. Это тот адрес, куда настоящий сенсор посылал бы свои данные.
Вещественное поле "value" содержит значение температуры воздуха, булево поле "raining" содержит
значение true/false в зависимости от того, зарегистрировал ли сенсор дождь или нет. Помимо этого, в
этом запросе передается сам объект сенсора, который получил и отправляет эти "измерения".
Значения температуры воздуха, дождя должны сохранятся в таблице в БД. Также, в каждой строке этой
таблицы должно содержаться название того сенсора, который прислал эти измерения. То есть
сущность "Измерение" имеет связь с сущностью "Сенсор".
Все поля у измерения должны валидироваться.
Значение "value" должно быть не пустым и находиться в диапазоне от -100 до 100.
Значение "raining" должно быть не пустым.
Значение "sensor" должно быть не пустым. При этом, название сенсора должно валидироваться в БД.
Сенсор с таким названием должен быть зарегистрирован в системе (должен быть в БД).
Если такого сенсора нет в БД - выдавать ошибку. Также, не забывайте про DTO.
На сервере, у измерения должно выставляться текущее время, оно должно сохраняться в БД.

3. GET-запрос на /measurements

Возвращает все измерения из БД

4. GET-запрос на /measurements/rainyDaysCounts

Возвращает количество дождливых дней из БД