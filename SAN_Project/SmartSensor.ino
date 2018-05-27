#include <SoftwareSerial.h>
#include "LdrDriver.h"
#include "MessageBuilder.h"
#include "BluetoothDriver.h"
#include <ThreadController.h>
#include <Thread.h>
#include <StaticThreadController.h>
#include "TemperatureDriver.h"
#include "AccelerometerDriver.h"
#include <avr/wdt.h>

#pragma region FIELDS
TemperatureDriver temperature_driver;
AccelerometerDriver accelerometer_driver;
BluetoothDriver bluetooth_driver;
MessageBuilder message_builder;
LdrDriver ldr_driver;

ThreadController thread_controller = ThreadController();
Thread tfront = Thread();
Thread tback_m = Thread();
Thread tback_l = Thread();
Thread tback_r = Thread();
Thread tPiezo = Thread();
Thread tBacklight = Thread();

double gdata[3];
double recent_g = 0;

int distance_front = 150;
int distance_back_m = 150;
int distance_back_l = 150;
int distance_back_r = 150;

int sendCounter = 0;

const int distance_3 = 30;
const int distance_2 = 15;
const int distance_1 = 5;
#pragma endregion

#pragma region PIN DEFINITIONS
const int PIN_X = A4;
const int PIN_Y = A3;
const int PIN_Z = A2;

const int PIN_LDR = A0;
const int PIN_TEMP = A1;

const int PIN_FRONTLIGHT_LEFT = 50;
const int PIN_FRONTLIGHT_RIGHT = 51;

const int PIN_BACKLIGHT_LEFT = 48;
const int PIN_BACKLIGHT_RIGHT = 49;

const int PIN_BT_RX = 52;
const int PIN_BT_TX = 53;

const int PIN_DISTANCE_FRONT_TRIG = 12;
const int PIN_DISTANCE_FRONT_ECHO = 11;

const int PIN_DISTANCE_BACK_M_TRIG = 10;
const int PIN_DISTANCE_BACK_M_ECHO = 9;

const int PIN_DISTANCE_BACK_L_TRIG = 8;
const int PIN_DISTANCE_BACK_L_ECHO = 7;

const int PIN_DISTANCE_BACK_R_TRIG = 6;
const int PIN_DISTANCE_BACK_R_ECHO = 5;

const int PIN_PIEZO = 2;
#pragma endregion

#pragma region METHOD SIGNATURES
unsigned long string_to_hex(const String& string);
unsigned long hex_to_string(const String& string);

void handleMessage();
void sendData();
void turnLightsOn();
void dimLights();
void ldr();
void ultrasonicFront();
void ultrasonicBackM();
void ultrasonicBackL();
void ultrasonicBackR();
void checkPiezo();
void turnBacklightOn(); 
#pragma endregion

void setup()
{
	Serial.begin(9600);

	temperature_driver = TemperatureDriver(PIN_TEMP);
	accelerometer_driver = AccelerometerDriver(PIN_X, PIN_Y, PIN_Z, 1);
	bluetooth_driver = BluetoothDriver(PIN_BT_RX, PIN_BT_TX);
	ldr_driver = LdrDriver(PIN_LDR);
	message_builder = MessageBuilder();

	// Pin Modes Leds
	pinMode(PIN_FRONTLIGHT_LEFT, OUTPUT);
	pinMode(PIN_FRONTLIGHT_RIGHT, OUTPUT);
	pinMode(PIN_BACKLIGHT_LEFT, OUTPUT);
	pinMode(PIN_BACKLIGHT_RIGHT, OUTPUT);

	// Pin Modes Ultrasonic
	pinMode(PIN_DISTANCE_FRONT_TRIG, OUTPUT);
	pinMode(PIN_DISTANCE_FRONT_ECHO, INPUT);

	pinMode(PIN_DISTANCE_BACK_M_TRIG, OUTPUT);
	pinMode(PIN_DISTANCE_BACK_M_ECHO, INPUT);

	pinMode(PIN_DISTANCE_BACK_L_TRIG, OUTPUT);
	pinMode(PIN_DISTANCE_BACK_L_ECHO, INPUT);

	pinMode(PIN_DISTANCE_BACK_R_TRIG, OUTPUT);
	pinMode(PIN_DISTANCE_BACK_R_ECHO, INPUT);

	// Threads
	tfront.onRun(ultrasonicFront);
	tfront.setInterval(1);
	tback_m.onRun(ultrasonicBackM);
	tback_m.setInterval(1);
	tback_r.onRun(ultrasonicBackR);
	tback_r.setInterval(1);
	tback_l.onRun(ultrasonicBackL);
	tback_l.setInterval(1);
	tPiezo.onRun(checkPiezo);
	tPiezo.setInterval(100);

	tBacklight.onRun(turnBacklightOn);
	tBacklight.setInterval(20); 

	thread_controller.add(&tfront);
	thread_controller.add(&tback_m);
	thread_controller.add(&tback_l);
	thread_controller.add(&tback_r);
	thread_controller.add(&tPiezo);
}

void loop()
{
	if (bluetooth_driver.IsConnected() != true)
	{
		bluetooth_driver.Connect();
	}
	else
	{
		wdt_enable(WDTO_4S); // Watchdog: Reset Arduino if no answere from smartphone
		if (thread_controller.shouldRun())
		{
			thread_controller.run();
		}
		sendData();
		wdt_reset();
	}
}


void sendData()
{
	char response[20];
	// ACC ***************************************************
	accelerometer_driver.GetGData(gdata);
	message_builder.BuildResponse(
		String(*gdata, 1) + SEPERATOR + String(*(gdata + 1), 1) + SEPERATOR + String(*(gdata + 2), 1), ACC_COMMAND, response);
	Serial.println(response);
	bluetooth_driver.Send(response);

	Serial.println(recent_g); 
	Serial.println(*(gdata + 1));
	if (fabs(recent_g - *(gdata + 1)) > 0.5)
	{
		if(tBacklight.shouldRun())
		{
			tBacklight.run();
		}
	}
	recent_g = *(gdata + 1);

	// TEMP **************************************************
	if (sendCounter > 15)
	{
		const auto temp = temperature_driver.GetTemperature();
		message_builder.BuildResponse(String(temp, 2), TEMP_COMMAND, response);
		Serial.println(response);
		bluetooth_driver.Send(response);

		// LDR ***************************************************
		ldr();

		sendCounter = 0;
	}

	// DISTANCE **********************************************
	message_builder.BuildResponse(
		String(distance_front) + SEPERATOR + String(distance_back_m) + SEPERATOR + String(distance_back_l) +
		SEPERATOR + String(distance_back_r), DIST_COMMAND, response);
	Serial.println(response);
	bluetooth_driver.Send(response);

	sendCounter++;
}

void turnBacklightOn()
{
	digitalWrite(PIN_BACKLIGHT_LEFT, HIGH);
	digitalWrite(PIN_BACKLIGHT_RIGHT, HIGH);
	delay(2000);
	digitalWrite(PIN_BACKLIGHT_LEFT, LOW);
	digitalWrite(PIN_BACKLIGHT_RIGHT, LOW);
}

void checkPiezo()
{
	Serial.println(distance_front);
	if (distance_front < distance_3 && distance_front > distance_2
		|| distance_back_l < distance_3 && distance_back_l > distance_2
		|| distance_back_m < distance_3 && distance_back_m > distance_2
		|| distance_back_r < distance_3 && distance_back_r > distance_2)
	{
		analogWrite(PIN_PIEZO, 50);
		delay(150);
		analogWrite(PIN_PIEZO, 0);
		delay(150);
	}
	else if (distance_front < distance_2 && distance_front > distance_1
		|| distance_back_l < distance_2 && distance_back_l > distance_1
		|| distance_back_m < distance_2 && distance_back_m > distance_1
		|| distance_back_r < distance_2 && distance_back_r > distance_1)
	{
		analogWrite(PIN_PIEZO, 50);
		delay(100);
		analogWrite(PIN_PIEZO, 0);
		delay(100);
	}
	else if (distance_front < distance_1 && distance_front > 0
		|| (distance_back_l < distance_1 && distance_back_l > 0)
		|| (distance_back_m < distance_1 && distance_back_m > 0)
		|| (distance_back_r < distance_1 && distance_back_r > 0))
	{
		analogWrite(PIN_PIEZO, 50);
	}
}

void ldr()
{
	char response[20];
	const auto ldr_reading = ldr_driver.GetValue();
	if (ldr_reading < 600)
	{
		dimLights();
		message_builder.BuildResponse(String(1), LDR_COMMAND, response);
		Serial.println(response);
		bluetooth_driver.Send(response);
	}
	else
	{
		turnLightsOn();
		message_builder.BuildResponse(String(0), LDR_COMMAND, response);
		Serial.println(response);
		bluetooth_driver.Send(response);
	}
}


void ultrasonicFront()
{
	digitalWrite(PIN_DISTANCE_FRONT_TRIG, LOW);
	delayMicroseconds(5);
	// Sets the trigPin on HIGH state for 10 micro seconds
	digitalWrite(PIN_DISTANCE_FRONT_TRIG, HIGH);
	delayMicroseconds(10);
	digitalWrite(PIN_DISTANCE_FRONT_TRIG, LOW);
	// Reads the echoPin, returns the sound wave travel time in microseconds
	const int duration = pulseIn(PIN_DISTANCE_FRONT_ECHO, HIGH);
	const int distance = duration / 2 / 29.1;
	if (distance >= 0 && distance <= 150)
	{
		distance_front = distance;
	}
}

void ultrasonicBackM()
{
	digitalWrite(PIN_DISTANCE_BACK_M_TRIG, LOW);
	delayMicroseconds(5);
	// Sets the trigPin on HIGH state for 10 micro seconds
	digitalWrite(PIN_DISTANCE_BACK_M_TRIG, HIGH);
	delayMicroseconds(10);
	digitalWrite(PIN_DISTANCE_BACK_M_TRIG, LOW);
	// Reads the echoPin, returns the sound wave travel time in microseconds
	const int duration = pulseIn(PIN_DISTANCE_BACK_M_ECHO, HIGH);
	const int distance = duration / 2 / 29.1;
	if (distance >= 0 && distance <= 150)
	{
		distance_back_m = distance;
	}
}

void ultrasonicBackL()
{
	digitalWrite(PIN_DISTANCE_BACK_L_TRIG, LOW);
	delayMicroseconds(5);
	// Sets the trigPin on HIGH state for 10 micro seconds
	digitalWrite(PIN_DISTANCE_BACK_L_TRIG, HIGH);
	delayMicroseconds(10);
	digitalWrite(PIN_DISTANCE_BACK_L_TRIG, LOW);
	// Reads the echoPin, returns the sound wave travel time in microseconds
	const int duration = pulseIn(PIN_DISTANCE_BACK_L_ECHO, HIGH);
	const int distance = duration / 2 / 29.1;
	if (distance >= 0 && distance <= 150)
	{
		distance_back_l = distance;
	}
}

void ultrasonicBackR()
{
	digitalWrite(PIN_DISTANCE_BACK_R_TRIG, LOW);
	delayMicroseconds(5);
	// Sets the trigPin on HIGH state for 10 micro seconds
	digitalWrite(PIN_DISTANCE_BACK_R_TRIG, HIGH);
	delayMicroseconds(10);
	digitalWrite(PIN_DISTANCE_BACK_R_TRIG, LOW);
	// Reads the echoPin, returns the sound wave travel time in microseconds
	const int duration = pulseIn(PIN_DISTANCE_BACK_R_ECHO, HIGH);
	const int distance = duration / 2 / 29.1;
	if (distance >= 0 && distance <= 150)
	{
		distance_back_r = distance;
	}
}

void turnLightsOn()
{
	digitalWrite(PIN_FRONTLIGHT_LEFT, HIGH);
	digitalWrite(PIN_FRONTLIGHT_RIGHT, HIGH);
}

void dimLights()
{
	digitalWrite(PIN_FRONTLIGHT_LEFT, LOW);
	digitalWrite(PIN_FRONTLIGHT_RIGHT, LOW);
}


unsigned long string_to_hex(const String& string)
{
	return strtoul(string.c_str(), nullptr, 16);
}

unsigned long hex_to_string(const String& string)
{
	return strtoul(string.c_str(), nullptr, 16);
}
