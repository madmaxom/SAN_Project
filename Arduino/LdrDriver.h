// LdrDriver.h

#ifndef _LDRDRIVER_h
#define _LDRDRIVER_h

#if defined(ARDUINO) && ARDUINO >= 100
	#include "arduino.h"
#else
	#include "WProgram.h"
#endif


class LdrDriver
{
public:
	LdrDriver();
	~LdrDriver();
	LdrDriver(int pin);
	int GetValue();
private:
	int pin;
};

#endif

