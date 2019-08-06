// 
// 
// 

#include "LdrDriver.h"


LdrDriver::LdrDriver()
{
	pin = PIN_A1; 
}

LdrDriver::~LdrDriver()
{
}

LdrDriver::LdrDriver(int input)
{
	pin = input; 
}

int LdrDriver::GetValue()
{
	const int ldr_reading = analogRead(pin);
	return ldr_reading; 
}
