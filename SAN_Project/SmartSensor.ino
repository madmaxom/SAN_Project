#include "SpeedDriver.h"
#include "MessageBuilder.h"
#include "BluetoothDriver.h"
//#include <ThreadController.h>
//#include <Thread.h>
//#include <StaticThreadController.h>
#include "ObstacleDetection.h"
#include "TemperatureDriver.h"
#include "AccelerometerDriver.h"
#include "DisplayDriver.h"
#include "string.h"
#include <avr/wdt.h>

// Driver
TemperatureDriver temperature_driver;
AccelerometerDriver accelerometer_driver;
BluetoothDriver bluetooth_driver;
MessageBuilder message_builder;

// Fields
double gdata[3];
String inputString = "";
double temp;
int delayCount = 300;

int PIN_NOT_AVAILABLE = A7;
int LDR_Pin = A2; 
int frontLightPin1 = 7;
int frontLightPin2 = 8;
int backLightPin1 = 10;
int backLightPin2 = 11;
int ON = 1;
int OFF = 0;

// Method signatures
unsigned long string_to_hex(const String& string);
unsigned long hex_to_string(const String& string);

void handleMessage();
void sendData();
void turnLightsOn();
void dimLights(); 

void setup()
{
	Serial.begin(9600);

	temperature_driver = TemperatureDriver(6);
	accelerometer_driver = AccelerometerDriver(0, 1, PIN_NOT_AVAILABLE, 1);
	bluetooth_driver = BluetoothDriver(12, 13);
	message_builder = MessageBuilder();
}

void loop()
{
	if (bluetooth_driver.IsConnected() != true)
	{
		bluetooth_driver.Connect();
	}
	else
	{
		// Watchdog 
		// If after 4 seconds no response from smartphone --> reset arduino
		wdt_enable(WDTO_4S);
		sendData();

		// Reset watchdog so it does not reset arduino
		wdt_reset();
		delay(10);
	}
}

void sendData()
{
	delayCount++;
	char response[20];
	// ACC ***************************************************
	accelerometer_driver.GetGData(gdata);
	message_builder.BuildResponse(String(*gdata, 2) + ";" + String(*(gdata + 1), 2), ACC_COMMAND, response);
	Serial.println(response);
	bluetooth_driver.Send(response);

	// TEMP **************************************************
	if (delayCount > 20)
	{
		temp = temperature_driver.GetTemperature();
		message_builder.BuildResponse(String(temp, 2), TEMP_COMMAND, response);
		Serial.println(response);
		bluetooth_driver.Send(response);

	// LDR ***************************************************

		const int ldr_reading = analogRead(LDR_Pin);
		Serial.println(ldr_reading); 
		if (ldr_reading < 600) {
			turnLightsOn(); 
			message_builder.BuildResponse(String(1), LDR_COMMAND, response);
			Serial.println(response);
			bluetooth_driver.Send(response);
		}
		else
		{
			dimLights();
			message_builder.BuildResponse(String(0), LDR_COMMAND, response);
			Serial.println(response);
			bluetooth_driver.Send(response);
		}

		delayCount = 0;
	}
}

void turnLightsOn()
{
	digitalWrite(frontLightPin1, ON);
	digitalWrite(frontLightPin2, ON);
	digitalWrite(backLightPin1, ON);
	digitalWrite(backLightPin2, ON);
}

void dimLights()
{
	digitalWrite(frontLightPin1, OFF);
	digitalWrite(frontLightPin2, OFF);
	digitalWrite(backLightPin1, OFF);
	digitalWrite(backLightPin2, OFF);
}


unsigned long string_to_hex(const String& string)
{
	return strtoul(string.c_str(), nullptr, 16);
}

unsigned long hex_to_string(const String& string)
{
	return strtoul(string.c_str(), nullptr, 16);
}
