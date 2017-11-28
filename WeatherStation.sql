drop database weatherStation;
create database weatherStation;
use weatherStation;

# table which holds the measurements from the station
create table measurements (

	#measurementID int not null auto_increment,  								# measurementID, primary key for the table, each new measurement has different ID
    timeAndDate timestamp not null default current_timestamp,					# timestamp format year-month-day hours:minutes:seconds
    temperature double not null,												# double value for the temperature
    windSpeed double not null,													# double value for the wind speed
    humidity double not null,													# double value for the humidity
    pressure double not null,													# double value for the pressure
    Constraint pk_measurements primary key (timeAndDate)

);

insert into measurements (temperature, windSpeed, humidity, pressure)
values (10.6, 5, 20.889, 250);
insert into measurements (temperature, windSpeed, humidity, pressure)
values (11.0, 3, 20.819, 240);
insert into measurements (temperature, windSpeed, humidity, pressure)
values (11.6, 4, 20.789, 230);
insert into measurements (temperature, windSpeed, humidity, pressure)
values (9.6, 5, 20.659, 222);
insert into measurements (temperature, windSpeed, humidity, pressure)
values (8.2, 3, 20.999, 250);
insert into measurements (temperature, windSpeed, humidity, pressure)
values (7.0, 4, 21.105, 250);

insert into measurements (temperature, windSpeed, humidity, pressure)
values (9, 3, 20.999, 250);
insert into measurements (temperature, windSpeed, humidity, pressure)
values (15, 4, 21.105, 250);


insert into measurements (temperature, windSpeed, humidity, pressure)
values (1, 2, 12.105, 125);
insert into measurements (temperature, windSpeed, humidity, pressure)
values (14, 3.4, 23.005, 654.44);

insert into measurements (temperature, windSpeed, humidity, pressure)
values (666, 3.4, 23.005, 654.44);