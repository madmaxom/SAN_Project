#include "ObstacleDetection.h"
#include "TemperatureDriver.h"
#include "AccelerometerDriver.h"
#include "DisplayDriver.h"
#include "string.h"

TemperatureDriver temperature_driver;
DisplayDriver display_driver;
AccelerometerDriver accelerometer_driver;


// Method signatures
unsigned long string_to_hex(const String& string);
unsigned long hex_to_string(const String& strin);

void setup()
{
	Serial.begin(9600);
	pinMode(7, OUTPUT); // Acc Alarm LED
	pinMode(8, OUTPUT); // Temp Alarm LED
	display_driver = DisplayDriver();
	temperature_driver = TemperatureDriver(6);
	accelerometer_driver = AccelerometerDriver(0, 1, 2, 1);
	delay(500); // wait for display to boot up

	
}

/*
int lightPin = 0;  //define a pin for Photo resistor
int ledPin=11;     //define a pin for LED

void setup()
{
    Serial.begin(9600);  //Begin serial communcation
    pinMode( ledPin, OUTPUT );
}

void loop()
{
    Serial.println(analogRead(lightPin)); //Write the value of the photoresistor to the serial monitor.
    analogWrite(ledPin, analogRead(lightPin)/4);  //send the value to the ledPin. Depending on value of resistor 
                                                //you have  to divide the value. for example, 
                                                //with a 10k resistor divide the value by 2, for 100k resistor divide by 4.
   delay(10); //short delay for faster response to light.
}
*/


void loop()
{


	
}


unsigned long string_to_hex(const String& string)
{
	return strtoul(string.c_str(), nullptr, 16);
}

unsigned long hex_to_string(const String& hexstring)
{
	return strtoul(hexstring.c_str(), nullptr, 16);
}
