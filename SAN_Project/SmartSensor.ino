#include "MessageBuilder.h"
#include "BluetoothDriver.h"
#include <ThreadController.h>
#include <Thread.h>
#include <StaticThreadController.h>
#include "ObstacleDetection.h"
#include "TemperatureDriver.h"
#include "AccelerometerDriver.h"
#include "DisplayDriver.h"
#include "string.h"

// Driver
TemperatureDriver temperature_driver;
AccelerometerDriver accelerometer_driver;
BluetoothDriver bluetooth_driver; 

// Fields
double gdata[3];
String inputString = "";

// Method signatures
unsigned long string_to_hex(const String& string);
unsigned long hex_to_string(const String& string);

void setup()
{
	Serial.begin(9600);

	temperature_driver = TemperatureDriver(6);
	accelerometer_driver = AccelerometerDriver(0, 1, 2, 1);
	bluetooth_driver = BluetoothDriver(12, 13); 
}

void loop()
{
	if(bluetooth_driver.IsConnected() != true)
	{
		bluetooth_driver.Connect();
	}
	else
	{
		accelerometer_driver.GetGData(gdata);
		Serial.println(String(*gdata, 2));
		delay(1000);
	}
}

unsigned long string_to_hex(const String& string)
{
	return strtoul(string.c_str(), nullptr, 16);
}

unsigned long hex_to_string(const String& string)
{
	return strtoul(string.c_str(), nullptr, 16);
}
