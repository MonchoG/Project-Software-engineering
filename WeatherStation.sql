drop database weatherStation;
create database weatherStation;
use weatherStation;

# table which holds the measurements from the station
create table measurements (

	#measurementID int not null auto_increment,  								# measurementID, primary key for the table, each new measurement has different ID
    timeAndDate timestamp not null default current_timestamp,					# timestamp format year-month-day hours:minutes:seconds
    temperature double not null,												# double value for the temperature
	light double not null,																			
    pressure double not null,													# double value for the pressure
    Constraint pk_measurements primary key (timeAndDate)

);

insert into measurements (temperature, light, pressure)
values (10.6, 500, 250);

insert into measurements (temperature, light, pressure)
values (11, 550, 250);
insert into measurements (temperature, light, pressure)
values (12, 560, 250);
insert into measurements (temperature, light, pressure)
values (11.5, 540, 240);
insert into measurements (temperature, light, pressure)
values (10.6, 500, 250);